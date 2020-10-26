/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invent_immob;

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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
public class LoginController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    public static User user = new User();
    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    public void log(KeyEvent event) throws Exception {

        if (event.getCode() == KeyCode.ENTER) {
            user.username = username.getText();
            user.password = password.getText();
            user.type = "";

            try {
                user = user.log();
                if (user.status.equals("suc")) {
                    String dir = "";
                    if (!user.type.equals("user")) {
                        dir = "mainApp.fxml";
                    } else {
                        //user interface
                        dir = "userApp.fxml";
                    }
                    Parent insertPaiemenet = FXMLLoader.load(getClass().getResource(dir));

                    Scene Command;
                    Command = new Scene(insertPaiemenet);
                    Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    appStage.hide();
                    appStage.setScene(Command);
                    appStage.setResizable(false);
                    appStage.show();
                } else if (user.status.equals("username")) {
                    if (User.getAllUsers().isEmpty() && username.getText().equals("admin") && password.getText().equals("admin")) {
                        Parent insertPaiemenet = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));

                        Scene Command;
                        Command = new Scene(insertPaiemenet);
                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.hide();
                        appStage.setScene(Command);
                        appStage.setResizable(false);
                        appStage.show();
                    } else {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText("Ce nom d'utilisateur n'existe pas");
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }
                } else if (user.status.equals("password")) {

                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText("Mot de passe incorrect");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }

            } catch (ClassNotFoundException | SQLException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();
            }

        }

    }

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
        user.username = username.getText();
        user.password = password.getText();
        user.type = "";
        try {
            if (!User.getAllUsers().isEmpty()) {

                user.id = -1;
                try {
                    user = user.log();
                    if (user.status.equals("suc")) {
                        String dir = "";
                        if (!user.type.equals("user")) {
                            dir = "mainApp.fxml";
                        } else {
                            //user interface
                            dir = "userApp.fxml";
                        }
                        Parent insertPaiemenet = FXMLLoader.load(getClass().getResource(dir));

                        Scene Command;
                        Command = new Scene(insertPaiemenet);
                        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        appStage.hide();
                        appStage.setScene(Command);
                        appStage.setResizable(false);
                        appStage.show();
                    } else if (user.status.equals("username")) {

                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText("Ce nom d'utilisateur n'existe pas");
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    } else if (user.status.equals("password")) {

                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText("Mot de passe incorrect");
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }

                } catch (ClassNotFoundException | SQLException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }
            } else if (user.username.equals("admin") && user.password.equals("admin")) {
                Parent insertPaiemenet = FXMLLoader.load(getClass().getResource("adminLogin.fxml"));

                Scene Command;
                Command = new Scene(insertPaiemenet);
                Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                appStage.hide();
                appStage.setScene(Command);
                appStage.setResizable(false);
                appStage.show();
            }

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
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
