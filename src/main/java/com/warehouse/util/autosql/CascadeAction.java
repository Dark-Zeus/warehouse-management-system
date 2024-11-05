package com.warehouse.util.autosql;

public enum CascadeAction {
    CASCADE, // Automatically update/delete dependent rows
    SET_NULL, // Set the foreign key column to NULL if parent is deleted/updated
    SET_DEFAULT, // Set to default value if specified
    RESTRICT, // Prevent deletion/update if there are dependencies
    NO_ACTION // No action on delete/update
}
