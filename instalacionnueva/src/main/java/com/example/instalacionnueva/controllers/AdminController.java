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


public class AdminController implements Initializable {

    public TextField TFcompName;
    private List<User> userList;
    private User loggedUser;
    private ObservableList<Competition> competitions;
    private ObservableList<Pilot> pilots;
    private Pilot selectedPilot;
    private Competition selectedComp;

    //FX controls
    public Label LBloggedUser;
    public Button BTlogOut;
    public Button BTDelete;
    public Button BTsaveChanges;
    public Button BTcreatecomp;

    public Button BTsavePilotPoints;
    public TextField TFcompNam;
    public TextField TFcompCity;
    public TextField TFcompMaxPart;
    public TextField TFpilotPoints;
    public Button BTlogoutadmin;
    public ListView<Competition> LVadmin;
    public ListView<Pilot> LVpilots;
    public TextField TFtype;
    public Label LBpilotNick;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        competitions = FXCollections.observableArrayList(loadCompetitions());
        loadCompetitionsSerialized();
        LVadmin.setItems(competitions);
    }

    public void initData(List<User> userList, User loggedUser) {
        this.userList = userList;
        this.loggedUser = loggedUser;
        LBloggedUser.setText(loggedUser.getLogin());
    }

    public void saveChanges(ActionEvent event) {
        CompetitionType competitionType = null;
        Competition competition = LVadmin.getSelectionModel().getSelectedItem();
        String name = TFcompNam.getText();
        String city = TFcompCity.getText();
        String type = TFtype.getText();

        int maxParticipants = 0;
        try {
            maxParticipants = Integer.parseInt(TFcompMaxPart.getText());
        } catch (Exception e) {
            Tools.alert("Type an integer");
        }

        if (type.equalsIgnoreCase("freestyle")) {
            competitionType = CompetitionType.FREESTYLE;
        } else if (type.equalsIgnoreCase("race")) {
            competitionType = CompetitionType.RACE;
        } else {
            Tools.alert("Type between RACE or FREESTYLE");
        }

        competition.setName(name);
        competition.setCity(city);
        competition.setCompetitionType(competitionType);
        competition.setMaxParticipants(maxParticipants);

        disableFields(true);
        LVadmin.setItems(competitions);
        BTsaveChanges.setVisible(false);
    }

    public void addComp(ActionEvent actionEvent) {
        CompetitionType competitionType = null;
        String name = TFcompNam.getText();
        String city = TFcompCity.getText();
        String type = TFtype.getText();
        int maxParticipants = 0;
        try {
            maxParticipants = Integer.parseInt(TFcompMaxPart.getText());
        } catch (Exception e) {
            Tools.alert("Type an integer");
        }

        if (type.equalsIgnoreCase("freestyle")) {
            competitionType = CompetitionType.FREESTYLE;
        } else if (type.equalsIgnoreCase("race")) {
            competitionType = CompetitionType.RACE;
        } else {
            Tools.alert("Type between RACE or FREESTYLE");
        }

        if (searchComp(name) || name.equals("") || competitionType == null || maxParticipants == 0) {
            Tools.alert("This competition already exist or any field are " + "incorrect");
        } else {

            Competition temp = new Competition(name, city, competitionType, maxParticipants);
            competitions.add(temp);
        }

        clearFields();
        LVadmin.setItems(competitions);

    }

    public void editCompetition() {
        BTsaveChanges.setVisible(true);
        disableFields(false);
    }

    public void deleteCompetition() {
        Competition competition = LVadmin.getSelectionModel().getSelectedItem();

        if (competition != null) {
            competitions.remove(competition);
        } else {
            Tools.alert("Any competition selected");
        }
        clearFields();
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

    public void savePointsToSelectedPilot() {
        if (this.selectedComp.getCompetitionType().equals(CompetitionType.RACE)) {
            this.selectedPilot.setPointsRace(Integer.parseInt(TFpilotPoints.getText()));
        } else {
            this.selectedPilot.setPointsFree(Integer.parseInt(TFpilotPoints.getText()));
        }
    }

    public void showPilotClicked() {
        this.selectedPilot = LVpilots.getSelectionModel().getSelectedItem();
        if (selectedPilot != null) {
            showPilotFields(selectedPilot);
        } else {
            Tools.alert("No Pilot selected");
        }
    }

    public void logOut(ActionEvent event) throws IOException {
        //saveCompetitions();
        saveCompSerialized();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    public void createCompetition(ActionEvent event) throws IOException {
        saveCompSerialized();
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(
                "/CreateCompetition" +
                ".fxml"));
        Scene scene = new Scene(loader.load());
        CreateController createController = loader.getController();
        createController.initData(competitions,loggedUser);
        stage.setScene(scene);
        stage.show();
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
            Tools.alert("Exception IO");
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

    private void disableFields(boolean state) {
        TFcompNam.setDisable(state);
        TFcompCity.setDisable(state);
        TFcompMaxPart.setDisable(state);
        TFtype.setDisable(state);
    }

    private void showPilotFields(Pilot pilot) {
        LBpilotNick.setText(pilot.getNick());
        String raceType = TFtype.getText();
        String points = "";
        if ("Race".equalsIgnoreCase(raceType)) {
            points = String.valueOf(pilot.getPointsRace());
        } else {
            points = String.valueOf(pilot.getPointsFree());
        }
        TFpilotPoints.setText(points);
    }

    private List<Competition> loadCompetitions() {
        BufferedReader fileToRead = null;
        List<Competition> competitions = new ArrayList<>();
        try {
            fileToRead = new BufferedReader(new FileReader(new File("competitions.txt")));
            String lineToRead = fileToRead.readLine();
            while (lineToRead != null) {
                competitions.add(LineParseCompetition(lineToRead, "_"));
                lineToRead = fileToRead.readLine();
            }
        } catch (IOException e) {
            Tools.alert("Read file error");
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

    private void clearFields() {
        TFcompNam.setText("");
        TFcompCity.setText("");
        TFcompMaxPart.setText("");
        TFtype.setText("");
    }

    private boolean searchComp(String name) {
        boolean exist = false;
        for (Competition competition : competitions) {
            if (competition.getName().equals(name)) {
                exist = true;
            }
        }
        return exist;
    }


}
