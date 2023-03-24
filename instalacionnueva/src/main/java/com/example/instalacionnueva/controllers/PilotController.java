package com.example.instalacionnueva.controllers;

import com.example.instalacionnueva.data.*;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class PilotController implements Initializable {


    public Button BTrankingRace;
    private List<User> userList;
    private User loggedUser;
    private ObservableList<Competition> competitions;
    private ObservableList<Pilot> pilots;
    private Pilot selectedPilot;
    private Competition selectedComp;

    //FX controls
    public TextField TFregistrationNick;
    public Label LBloggedUser;
    public Button BTlogOut;
    public TextField TFcompNam;
    public TextField TFcompCity;
    public TextField TFcompMaxPart;
    public ListView<Competition> LVadmin;
    public ListView<Pilot> LVpilots;
    public TextField TFtype;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCompetitionsSerialized();
        LVadmin.setItems(competitions);
    }

    public void initData(List<User> userList, User loggedUser) {
        this.userList = userList;
        this.loggedUser = loggedUser;
        LBloggedUser.setText(loggedUser.getLogin());
    }

    public void goToRanking(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/Ranking.fxml"));
        Scene scene = new Scene(loader.load());
        RankingController rankingController = loader.getController();

        rankingController.initData(competitions);
        stage.setScene(scene);
        stage.show();
    }
    public void showClicked() {
        this.selectedComp = LVadmin.getSelectionModel().getSelectedItem();
        if (selectedComp != null) {
            showCompetitionFields(selectedComp);
            pilots = FXCollections.observableArrayList(selectedComp.getParticipants());
            LVpilots.setItems(pilots);
        } else {
            Tools.alert("Any competition selected");
        }
    }


    public void logOut(ActionEvent event) throws IOException {
        saveCompetitions();

        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        saveCompSerialized();
    }

    public void registerOnCompetition(ActionEvent event) {
        boolean okRegister = false;
        int pilotMatched = 0;
        if (selectedComp.getMaxParticipants() > //Hay plazas
                selectedComp.getParticipants().size()) {
            okRegister = true;
        }else{
                Tools.alert("Full quota");
        }

        if(!TFregistrationNick.getText().equals("")){ //Comprobar si se ha
            // escrito un nick
            okRegister = true;
        }else{
            Tools.alert("Please enter nickname");
        }


        if(selectedComp.getParticipants().size() != 0){
            for (Pilot pilot: selectedComp.getParticipants()) {
                if(pilot.getNameSurname().equals(loggedUser.getNameSurname())){
                    pilotMatched++;
                }
            }
            if(pilotMatched > 0){
                okRegister = false;
                Tools.alert("You are already registered in this competition");
            }

        }


        if(okRegister){
            Pilot pilotParticipant = new Pilot(TFregistrationNick.getText(),
                    loggedUser.getNameSurname(), loggedUser.getLogin(), loggedUser.getPassword(), loggedUser.isAdmin());
            selectedComp.addParticipant(pilotParticipant);
            saveCompSerialized();
            Tools.message("Registration successfully!!!");
        }

    }

    //Private methods
    private void saveCompSerialized() {
        try {
            FileOutputStream fos = new FileOutputStream(
                    "serializedCompetitions.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(new ArrayList<Competition>(competitions));
            oos.close();
        } catch (FileNotFoundException e) {
            Tools.alert("Exception not found");
        }catch (IOException e){
            Tools.alert("Exception IO");
        }
    }

    private void loadCompetitionsSerialized() {
        try {
            FileInputStream fis = new FileInputStream("serializedCompetitions" +
                    ".ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            List<Competition> list = (List<Competition>) ois.readObject();
            competitions = FXCollections.observableArrayList(list);
            ois.close();
        } catch (FileNotFoundException e) {
            Tools.alert("Exception not found");
        }catch (IOException e){
            Tools.alert("Carlos eres un inutil");
        } catch (ClassNotFoundException e) {
            Tools.alert("Class not found");
        }
    }
    private void saveCompetitions() {
        try {
            PrintWriter printWriter = new PrintWriter("competitions.txt");
            for (Competition competition : competitions) {
                printWriter.println(competition.toFormat());
            }
            printWriter.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    private List<Competition> loadCompetitions() {
        BufferedReader fileToRead = null;
        List<Competition> competitions = new ArrayList<>();
        try {
            fileToRead =
                    new BufferedReader(new FileReader(new File("competitions.txt")));
            String lineToRead = fileToRead.readLine();
            while (lineToRead != null) {
                competitions.add(LineParseCompetition(lineToRead, "_"));
                lineToRead = fileToRead.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return competitions;
    }

    private Competition LineParseCompetition(String line, String symbol) {
        CompetitionType type = null;
        String[] splitLine = line.split(symbol);
        String typeString = splitLine[3];
        LocalDate date = LocalDate.parse(splitLine[5]);
        if (typeString.equals("RACE")) {
            type = CompetitionType.RACE;
        } else {
            type = CompetitionType.FREESTYLE;
        }
        return new Competition(splitLine[1], splitLine[2], type, Integer.parseInt(splitLine[4]), date);
    }

    private void showCompetitionFields(Competition competition) {

        TFcompNam.setText(competition.getName());
        TFcompCity.setText((competition.getCity()));
        TFcompMaxPart.setText(String.valueOf(competition.getMaxParticipants()));
        String type = "";
        if (competition.getCompetitionType() == CompetitionType.RACE) {
            type = "Race";
        } else if (competition.getCompetitionType() == CompetitionType.FREESTYLE) {
            type = "Freestyle";
        }
        TFtype.setText(type);
    }


}
