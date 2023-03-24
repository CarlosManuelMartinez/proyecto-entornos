package com.example.instalacionnueva.controllers;

import com.example.instalacionnueva.data.Competition;
import com.example.instalacionnueva.data.Pilot;
import com.example.instalacionnueva.data.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;

public class RankingController implements Initializable {
    private List<Competition> competitions;
    public Label LBrankig;
    public Button BTrankingRace;
    public ListView<Pilot> LVranking;
    public Button BTX;

    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    public void initData(List<Competition> competitions) {
        this.competitions = competitions;
        showRankingFree();
    }
    public void showRankingRace() {
        LBrankig.setText("Race Ranking");
        List<Pilot> pilots = obtainPilotsWithPoints();
        pilots.sort(Comparator.comparingInt(Pilot::getPointsRace).reversed());
        LVranking.setItems(FXCollections.observableList(pilots));

    }
    public void showRankingFree(){
        LBrankig.setText("Freestyle Ranking");
        List<Pilot> pilots = obtainPilotsWithPoints();
        pilots.sort(Comparator.comparingInt(Pilot::getPointsFree).reversed());
        LVranking.setItems(FXCollections.observableList(pilots));
    }

    public void returnPilotMenu(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pilot.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }


    //Private methods
    private List<User> pilotsUser(List<User> users){
        List<User> pilotsUserSorted = new ArrayList<>();
        List<User> pilotsUser = new ArrayList<>();
        for (User user:users) {
            if(!user.isAdmin()){
                pilotsUser.add(user);
            }
        }

        //pilotsUserSorted =
        return pilotsUserSorted;
    }

    private List<Pilot> obtainPilotsWithPoints() {
        List<Pilot> pilotsWithPoints = new ArrayList<>();
        for (Competition comp : competitions) {
            for (Pilot competitionPilot : comp.getParticipants()) {
                Pilot tempPilot = findPilot(competitionPilot.getLogin(), pilotsWithPoints);
                if (tempPilot != null) {
                    tempPilot.setPointsRace(tempPilot.getPointsRace() + competitionPilot.getPointsRace());
                    tempPilot.setPointsFree(tempPilot.getPointsFree() + competitionPilot.getPointsFree());
                } else {
                    Pilot pilotToBeAdded = new Pilot(competitionPilot.getNick(), competitionPilot.getNameSurname(), competitionPilot.getLogin(), competitionPilot.getPassword(), competitionPilot.isAdmin());
                    pilotToBeAdded.setPointsRace(competitionPilot.getPointsRace());
                    pilotToBeAdded.setPointsFree(competitionPilot.getPointsFree());
                    pilotsWithPoints.add(pilotToBeAdded);
                }
            }
        }
        return pilotsWithPoints;
    }

    private Pilot findPilot(String login, List<Pilot> pilotList) {
        Pilot foundPilot = null;
        for (Pilot pilot : pilotList) {
            if (pilot.getLogin().equals(login)) {
                foundPilot = pilot;
                break;
            }
        }
        return foundPilot;
    }

}
