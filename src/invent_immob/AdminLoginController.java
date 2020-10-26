/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invent_immob;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;

/**
 * FXML Controller class
 *
 * @author bekal
 */
public class AdminLoginController implements Initializable {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;
    @FXML
    void settings(MouseEvent event) {
        try {
            Parent insertPaiemenet = FXMLLoader.load(getClass().getResource("Setting.fxml"));

            Scene Command;
            Command = new Scene(insertPaiemenet);
            Stage appStage = new Stage();
            appStage.setScene(Command);
            appStage.setResizable(false);
            appStage.initStyle(StageStyle.UNDECORATED);
            appStage.show();
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void login(MouseEvent event) { 
        User user = new User();
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            user.username = username.getText();
            user.password = password.getText(); 
            if (user.password.length() < 8) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("Votre mot de passe doit contenir au moins 8 caractères");
                a2.setHeaderText(null);
                a2.showAndWait();
            } else if (user.password.equals(user.username)) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("Votre mot de passe doit être différent de votre nom d'utilisateur");
                a2.setHeaderText(null);
                a2.showAndWait();
            } else {
                user.type = "superAdmin";
                try {
                    user.addUser(); 
                    Parent insertPaiemenet = FXMLLoader.load(getClass().getResource("login.fxml"));

                    Scene Command;
                    Command = new Scene(insertPaiemenet);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.hide();
                    appStage.setScene(Command);
                    appStage.setResizable(false);
                    appStage.show();
                } catch (SQLException | IOException | ClassNotFoundException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }
            }
        } else {

            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText("Tous les champs sont requis");
            a2.setHeaderText(null);
            a2.showAndWait();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { 
    }

    @FXML
    public void log(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {

            User user = new User();
            if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
                user.username = username.getText();
                user.password = password.getText();

                if (user.password.length() < 8) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText("Votre mot de passe doit contenir au moins 8 caractères");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } else if (user.password.equals(user.username)) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText("Votre mot de passe doit être différent de votre nom d'utilisateur");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } else {
                    user.type = "superAdmin";
                    try {
                        user.addUser();
                        Parent insertPaiemenet = FXMLLoader.load(getClass().getResource("login.fxml"));

                        Scene Command;
                        Command = new Scene(insertPaiemenet);
                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.hide();
                        appStage.setScene(Command);
                        appStage.setResizable(false);
                        appStage.show();
                    } catch (SQLException | IOException | ClassNotFoundException ex) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText(ex.toString());
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }
                }
            } else {

                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("Tous les champs sont requis");
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        }
    }
     @FXML
    void close(MouseEvent event) { 
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.close();
    }
     @FXML
    void min(MouseEvent event) { 
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.setIconified(true);
    }
    
}
