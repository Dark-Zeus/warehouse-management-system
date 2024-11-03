module com.warehouse {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.warehouse to javafx.fxml;
    exports com.warehouse;
    exports com.warehouse.controller;
}
