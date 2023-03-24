package com.example.instalacionnueva.data;


import javafx.scene.control.Alert;



public class Tools {
    // Method to create an alert with any text.
    public static void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void message(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("OK");
        alert.setContentText(message);
        alert.showAndWait();
    }

}
