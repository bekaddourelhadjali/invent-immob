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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.User;

/**
 * FXML Controller class
 *
 * @author bekal
 */
public class CompteSettingController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField news;

    @FXML
    private JFXPasswordField act;

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

    @FXML
    void save_pass(MouseEvent event) {
        if (!news.getText().isEmpty() && !act.getText().isEmpty()) {
            try {
                if (User.MD5(act.getText()).equals(LoginController.user.getPassword())) {
                    if (User.MD5(news.getText()).equals(LoginController.user.getPassword())) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText("C'est votre mot de passe actuelle");
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    } else if (news.getText().length() < 8) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText("Le mot de passe doit contenir au moins 8 caractères");
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    } else if (news.getText().equals(LoginController.user.getUsername())) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText("Votre mot de passe doit être différent de votre nom d'utilisateur");
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    } else {

                        
                        LoginController.user.password=User.MD5(news.getText());
                        LoginController.user.editUser();
                        Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                        a2.setTitle("Information");
                        a2.setContentText("votre mot de passe est enregistré avec succès ");
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }
                } else {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText("Mot de passe incorrect");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        }
    }

    @FXML
    void save_user(MouseEvent event) {
         
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            try {
                if (User.MD5(password.getText()).equals(LoginController.user.getPassword()) && !User.test(username.getText())) {
                     
                    LoginController.user.username = username.getText();
                    LoginController.user.editUser();

                    Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                    a2.setTitle("Information");
                    a2.setContentText("votre nom d'utilisateur est enregistré avec succès ");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } else if (User.test(username.getText())) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText("Nom d'utilisateur déja utilisé");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } else if (!User.MD5(password.getText()).equals(LoginController.user.getPassword())) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText("Mot de passe incorrect");
                    a2.setHeaderText(null);
                    a2.showAndWait();

                }

            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}
