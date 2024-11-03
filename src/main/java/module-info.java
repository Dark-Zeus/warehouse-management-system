module com.warehouse {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.warehouse to javafx.fxml;
    opens com.warehouse.controller to javafx.fxml;
    exports com.warehouse;
    exports com.warehouse.controller;
}
