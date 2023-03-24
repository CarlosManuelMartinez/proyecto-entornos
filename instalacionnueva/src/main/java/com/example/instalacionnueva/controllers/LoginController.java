package com.example.instalacionnueva.controllers;

import com.example.instalacionnueva.data.*;
import com.example.instalacionnueva.controllers.AdminController;
import com.example.instalacionnueva.controllers.PilotController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LoginController implements Initializable {

    private  List<User> userList = new ArrayList<>();

    @FXML
    private TextField TFUser;
    @FXML
    private PasswordField TFPassword;
    @FXML
    private Button BTLogin;
    @FXML
    public Label welcomeText;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList = FilesIO.UserListFromTxt("Users.txt","#");
    }

    public void toAdminMenu(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();

        User loggedUser = userIsCorrect(userList);
        if (loggedUser != null ) {
            stage.close();
            if(loggedUser.isAdmin()) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin.fxml"));
                Scene scene = new Scene(loader.load());
                AdminController adminController = loader.getController();
                adminController.initData(userList, loggedUser);
                stage.setScene(scene);
                stage.show();
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Pilot.fxml"));
                Scene scene = new Scene(loader.load());
                PilotController pilotController = loader.getController();
                pilotController.initData(userList,loggedUser);
                stage.setScene(scene);
                stage.show();
            }
        } else {
            Tools.alert("Login or password incorrect!!");
        }

    }
    public User userIsCorrect(List<User> list) {
        User loggedUser = null;
        String login = TFUser.getText();
        String password = TFPassword.getText();
        for (User user : list) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                User userLogged = user;
                loggedUser = user;
            }
        }
        return loggedUser;
    }
}