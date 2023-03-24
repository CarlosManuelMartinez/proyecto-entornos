package com.example.instalacionnueva.utils;

import com.example.instalacionnueva.data.Competition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.List;

public class Tools {
    // Method to create an alert with any text.
    public static void alert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ERROR");
        alert.setContentText(message);
        alert.showAndWait();
    }
    // Method to fill an observable list from list.
    public static ObservableList<Competition> listToObservable(List<Competition> listComp){
       ObservableList<Competition> competitionO =
               FXCollections.observableArrayList();
        for (Competition competition : listComp) {
            competitionO.add(competition);
        }
        return competitionO;
    }
}
