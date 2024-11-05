package com.warehouse.util.autosql;

import java.lang.Thread.State;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.warehouse.util.autosql.annotation.ColumnConstraints;
import com.warehouse.util.autosql.annotation.ForiegnKey;
import com.warehouse.util.autosql.annotation.PrimaryKey;
import com.warehouse.util.autosql.annotation.SQLType;

public class AutoSQL {
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * 
     * @param Database {@code String} - The name of the database
     * @param models   {@code Model} - All the models for the needed tables
     * @return
     */
    public static String generateTables(String database, Class<? extends Model>... models) {
        String query = "CREATE DATABASE IF NOT EXISTS " + database + ";\n";

        System.out.println("\n[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN
                + "Generating database " + ANSI_YELLOW + database + ANSI_RESET);

        query += "USE " + database + ";\n\n";
        System.out.println("[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN + "Generating tables"
                + ANSI_RESET);

        for (Class<? extends Model> model : models) {
            query += generateTableQuery(model);
        }

        System.out.println(query);
        return query;
    }

    public static String generateRecord(Model object, Boolean ignore) {
        System.out.println("\n[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN
                + "Generating record for " + ANSI_YELLOW + object.getClass().getSimpleName() + ANSI_RESET);
        Field[] fields = object.getClass().getDeclaredFields();
        String query = "INSERT";
        if (ignore) {
            query += " IGNORE";
        }
        query += " INTO `" + object.getClass().getSimpleName().toLowerCase() + "` (";
        String values = "VALUES (";

        for (Field field : fields) {
            query += field.getName() + ", ";

            System.out.print("\t" + field.getName() + " - " + ANSI_CYAN + "_Adding value_ - " + ANSI_RESET);

            field.setAccessible(true);
            try {
                Object value = field.get(object);
                if (value instanceof String) {
                    System.out.println(ANSI_YELLOW + value + ANSI_RESET);
                    values += "'" + value + "', ";
                } else {
                    System.out.println(ANSI_YELLOW + value + ANSI_RESET);
                    values += value + ", ";
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to get field value", e);
            }
        }

        query = query.substring(0, query.length() - 2) + ") ";
        values = values.substring(0, values.length() - 2) + ");";

        query += values;

        System.out.println(query);

        return query;
    }

    public static void executeInSequence(Connection connection, String query) {
        String[] queries = query.split(";");

        for (String q : queries) {
            if (q.trim().isEmpty()) {
                continue;
            }

            try {
                connection.createStatement().execute(q);
            } catch (Exception e) {
                System.out.println("[" + ANSI_RED + ANSI_BOLD + "ERROR" + ANSI_RESET + "] " + ANSI_RED
                        + "Failed to execute query: " + ANSI_YELLOW + q + ANSI_RESET);
                e.printStackTrace();
            }
        }
    }

    private static String generateTableQuery(Class<? extends Model> modelClass) {
        System.out.println("\t[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN
                + "Generating table for " + ANSI_YELLOW + modelClass.getSimpleName() + ANSI_RESET);

        Model model;
        try {
            model = modelClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate model class", e);
        }

        Field[] fields = model.getClass().getDeclaredFields();
        List<String> primaryKeys = new ArrayList<>();

        // create map for foreign keys, Map<table_name, <local_column,
        // referenced_column>>

        Map<String, List<Map<String, String>>> foreignKeys = new HashMap<>();

        String query = "CREATE TABLE IF NOT EXISTS `" + model.getClass().getSimpleName().toLowerCase() + "` (\n";
        for (Field field : fields) {
            if (field.isAnnotationPresent(SQLType.class)) {
                SQLType sqlType = field.getAnnotation(SQLType.class);
                System.out.print("\t\t" + field.getName() + " - " + ANSI_CYAN + sqlType.value() + ANSI_RESET);
                query += "`" + field.getName() + "` " + sqlType.value();
            } else {
                System.out.print(
                        "\t\t" + field.getName() + " - " + ANSI_CYAN + "VARCHAR(255)" + ANSI_RESET + "(defaulted)");
                query += field.getName() + " VARCHAR(255)";
            }

            if (field.isAnnotationPresent(ColumnConstraints.class)) {
                ColumnConstraints constraints = field.getAnnotation(ColumnConstraints.class);
                if (constraints.unique()) {
                    System.out.print(" - " + ANSI_PURPLE + "UNIQUE" + ANSI_RESET);
                    query += " UNIQUE";
                }
                if (constraints.notNull()) {
                    System.out.print(" - " + ANSI_PURPLE + "NOT NULL" + ANSI_RESET);
                    query += " NOT NULL";
                }
                if (!constraints.defaultValue().isEmpty()) {
                    System.out.print(" - " + ANSI_PURPLE + "DEFAULT " + constraints.defaultValue() + ANSI_RESET);
                    query += " DEFAULT " + constraints.defaultValue();
                }
                if (constraints.autoIncrement()) {
                    System.out.print(" - " + ANSI_PURPLE + "AUTO_INCREMENT" + ANSI_RESET);
                    query += " AUTO_INCREMENT";
                }
            }

            if (field.isAnnotationPresent(PrimaryKey.class)) {
                System.out.print(" - " + ANSI_PURPLE + "PRIMARY KEY" + ANSI_RESET);
                primaryKeys.add(field.getName());
            }

            if (field.isAnnotationPresent(ForiegnKey.class)) {
                ForiegnKey fk = field.getAnnotation(ForiegnKey.class);
                System.out.print(" - " + ANSI_PURPLE + "FOREIGN KEY (" + ANSI_YELLOW + fk.references().getSimpleName()
                        + ANSI_RESET + "." + ANSI_BOLD + fk.referencedColumn() + ANSI_PURPLE + ")" + ANSI_RESET);
                Map<String, String> columnRelation = new HashMap<>();
                columnRelation.put(field.getName(), fk.referencedColumn());

                if (!foreignKeys.containsKey(fk.references().getSimpleName().toLowerCase())) {
                    foreignKeys.put(fk.references().getSimpleName().toLowerCase(), new ArrayList<>());
                }

                foreignKeys.get(fk.references().getSimpleName().toLowerCase()).add(columnRelation);
            }

            System.out.println();
            query += ",\n";
        }

        query = query.substring(0, query.length() - 2);

        if (primaryKeys.size() > 0) {
            query += ",\nPRIMARY KEY (";
            for (String key : primaryKeys) {
                query += "`" + key + "`, ";
            }
            query = query.substring(0, query.length() - 2);
            query += "), ";
        } else {
            System.out.println("\t\t" + ANSI_RED + "No primary key found" + ANSI_RESET);
        }

        if (foreignKeys.size() > 0) {
            for (String table : foreignKeys.keySet()) {
                query += "\nFOREIGN KEY (";
                for (Map<String, String> column : foreignKeys.get(table)) {
                    for (String key : column.keySet()) {
                        query += "`" + key + "`, ";
                    }
                }

                query = query.substring(0, query.length() - 2);

                query += ") REFERENCES `" + table + "`(";
                for (Map<String, String> column : foreignKeys.get(table)) {
                    for (String key : column.keySet()) {
                        query += "`" + column.get(key) + "`, ";
                    }
                }

                query = query.substring(0, query.length() - 2);
                query += "), ";
            }
        }

        query = query.substring(0, query.length() - 2);

        query += "\n);\n\n";

        return query;
    }
}
