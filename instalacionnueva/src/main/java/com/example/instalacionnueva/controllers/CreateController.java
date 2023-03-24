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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CreateController implements Initializable {
    private ObservableList<Competition> competitions;
    private User loggedUser;
    //private List<User> userList;
    //FXML
    public TextField TFcompCity;
    public TextField TFcompMaxPart;
    public TextField TFtype;
    public Label LTName;
    public Label LBCity;
    public Label LBType;
    public Label LBmaxParticipants;
    public Button BTCreate;
    public TextField TFcompNam;
    public Button BTback;



    public void addComp(ActionEvent event) throws IOException {
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
        saveCompSerialized();

        clearFields();


    }
    public void initialize(URL url, ResourceBundle resourceBundle) {
        competitions = FXCollections.observableArrayList();
    }
    public void initData(ObservableList<Competition> competitions, User loggedUser) {
        this.competitions = competitions;
        this.loggedUser = loggedUser;

    }
    public void exitScene(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }
    //Private methods
    private boolean searchComp(String name) {
        boolean exist = false;
        for (Competition competition : competitions) {
            if (competition.getName().equals(name)) {
                exist = true;
            }
        }
        return exist;
    }
    private void clearFields() {
        TFcompNam.setText("");
        TFcompCity.setText("");
        TFcompMaxPart.setText("");
        TFtype.setText("");
    }
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
}
