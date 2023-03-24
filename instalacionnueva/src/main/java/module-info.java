module com.example.instalacionnueva {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.instalacionnueva to javafx.fxml;
    exports com.example.instalacionnueva;
    exports com.example.instalacionnueva.controllers;
    opens com.example.instalacionnueva.controllers to javafx.fxml;
}