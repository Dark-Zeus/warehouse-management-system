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
import com.warehouse.util.autosql.annotation.Table;

/**
 * Utility class to generate SQL queries for tables and records
 * @author M A S Madanperuma (Dark-Zeus)
 */
public class AutoSQL {

    // ANSI color codes
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_RESET = "\u001B[0m";

    /**
     * Generate a table creation query for a given model
     * @param Database {@code String} - The name of the database
     * @param models   {@code Model} - All the models for the needed tables
     * @return
     */
    public static String generateTables(String database, Class<? extends Model>... models) {
        // Query generation
        String query = "CREATE DATABASE IF NOT EXISTS " + database + ";\n";

        // Output info
        System.out.println("\n[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN
                + "Generating database " + ANSI_YELLOW + database + ANSI_RESET);

        query += "USE " + database + ";\n\n";
        System.out.println("[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN + "Generating tables"
                + ANSI_RESET);

        // Generate table queries for each model
        for (Class<? extends Model> model : models) {
            query += generateTableQuery(model);
        }

        // Output info
        System.out.println(query);
        return query;
    }

    /**
     * Generate a record insertion query for a given model
     * @param object {@code Model} - The model object to generate the record for
     * @param ignore {@code Boolean} - Whether to ignore duplicates
     * @return {@code String} - The generated query
     */
    public static String generateRecord(Model object, Boolean ignore) {
        // Output info
        System.out.println("\n[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN
                + "Generating record for " + ANSI_YELLOW + object.getClass().getSimpleName() + ANSI_RESET);

        // Get fields (class variables) of the model (column names)
        Field[] fields = object.getClass().getDeclaredFields();

        // Query generation
        String query = "INSERT";
        if (ignore) {
            query += " IGNORE";
        }
        query += " INTO `" + object.getClass().getSimpleName().toLowerCase() + "` (";
        String values = "VALUES (";

        // Generate query for each field
        for (Field field : fields) {
            query += field.getName() + ", ";

            System.out.print("\t" + field.getName() + " - " + ANSI_CYAN + "_Adding value_ - " + ANSI_RESET);
           
            field.setAccessible(true); // Access the field
            try {
                Object value = field.get(object); // Get the value of the field
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

        query = query.substring(0, query.length() - 2) + ") "; // Remove the last comma and space
        values = values.substring(0, values.length() - 2) + ");"; // Remove the last comma and space

        query += values; // Combine the query and values

        System.out.println(query); // Output the query

        return query;
    }

    /**
     * Execute a sequence of queries
     * @param connection {@code Connection} - The connection to execute the queries on
     * @param query      {@code String} - The queries to execute
     */
    public static void executeInSequence(Connection connection, String query) {
        // Split the queries by the semicolon (JDBC does not support multiple queries)
        String[] queries = query.split(";");

        // Execute each query separately in the sequence
        for (String q : queries) {
            // Skip empty queries
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

    /**
     * Generate a table creation query for a given model
     * @param modelClass {@code Class<? extends Model>} - The model class to generate
     * @return {@code String} - The generated query
     */
    private static String generateTableQuery(Class<? extends Model> modelClass) {
        // Get the table name from the annotation or the class name
        String tableName = modelClass.getSimpleName().toLowerCase();

        if(modelClass.getAnnotation(Table.class) != null) {
            tableName = modelClass.getAnnotation(Table.class).value();
        }

        // Output info
        System.out.println("\t[" + ANSI_BLUE + ANSI_BOLD + "INFO" + ANSI_RESET + "] " + ANSI_GREEN
                + "Generating table for " + ANSI_YELLOW + modelClass.getSimpleName() + "(" + tableName + ")" + ANSI_RESET);

        Model model;
        try {
            model = modelClass.getDeclaredConstructor().newInstance(); // Instantiate the model to get the fields
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate model class", e);
        }

        Field[] fields = model.getClass().getDeclaredFields(); // Get the fields of the model
        List<String> primaryKeys = new ArrayList<>(); // List to hold primary keys

        Map<String, List<Map<String, String>>> foreignKeys = new HashMap<>(); // Map to hold foreign keys

        // Query generation
        String query = "CREATE TABLE IF NOT EXISTS `" + tableName + "` (\n";
        for (Field field : fields) {
            // Get the SQL type from the annotation or default to VARCHAR(255)
            if (field.isAnnotationPresent(SQLType.class)) {
                SQLType sqlType = field.getAnnotation(SQLType.class);
                System.out.print("\t\t" + field.getName() + " - " + ANSI_CYAN + sqlType.value() + ANSI_RESET);
                query += "`" + field.getName() + "` " + sqlType.value();
            } else {
                System.out.print(
                        "\t\t" + field.getName() + " - " + ANSI_CYAN + "VARCHAR(255)" + ANSI_RESET + "(defaulted)");
                query += field.getName() + " VARCHAR(255)";
            }

            // Get the constraints from the annotation (unique, not null, default, auto increment)
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

            // Get the primary key annotations and add to the list (using annotation to get the field name)
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                System.out.print(" - " + ANSI_PURPLE + "PRIMARY KEY" + ANSI_RESET);
                primaryKeys.add(field.getName());
            }

            // Get the foreign key annotations and add to the map (using annotation to get the table and column)
            if (field.isAnnotationPresent(ForiegnKey.class)) {

                ForiegnKey fk = field.getAnnotation(ForiegnKey.class); // Get the annotation
                System.out.print(" - " + ANSI_PURPLE + "FOREIGN KEY (" + ANSI_YELLOW + fk.references().getSimpleName()
                        + ANSI_RESET + "." + ANSI_BOLD + fk.referencedColumn() + ANSI_PURPLE + ")" + ANSI_RESET);

                Map<String, String> columnRelation = new HashMap<>(); // Map to hold the column relation
                columnRelation.put(field.getName(), fk.referencedColumn()); // Add the column relation to the map

                // Add the column relation to the map
                if (!foreignKeys.containsKey(fk.references().getSimpleName().toLowerCase())) {
                    foreignKeys.put(fk.references().getSimpleName().toLowerCase(), new ArrayList<>());
                }

                foreignKeys.get(fk.references().getSimpleName().toLowerCase()).add(columnRelation);
            }

            System.out.println();
            query += ",\n";
        }

        query = query.substring(0, query.length() - 2); // Remove the last comma and space

        // Add primary keys to the query
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

        // Add foreign keys to the query
        if (foreignKeys.size() > 0) {
            for (String table : foreignKeys.keySet()) {
                query += "\nFOREIGN KEY (";
                for (Map<String, String> column : foreignKeys.get(table)) {
                    for (String key : column.keySet()) {
                        query += "`" + key + "`, ";
                    }
                }

                query = query.substring(0, query.length() - 2); // Remove the last comma and space

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
