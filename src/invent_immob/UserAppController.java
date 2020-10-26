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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Immob;
import model.Installation;
import model.User;

/**
 * FXML Controller class
 *
 * @author bekal
 */
public class UserAppController implements Initializable {

    @FXML
    private TableView<Installation> tabimmob;

    @FXML
    private TextField searchi;

    @FXML
    private TableView<Immob> tabimmobM;

    @FXML
    private TextField searchm;

    @FXML
    void settings(MouseEvent event) {
        try {
            Parent insertPaiemenet = FXMLLoader.load(getClass().getResource("CompteSetting.fxml"));

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
    void search_inst(KeyEvent event) {
        try {
            if (searchi.getText().trim().isEmpty()) {

                tabimmob.getItems().clear();
                tabimmob.getItems().addAll(Installation.getInstallations());

            } else {
                tabimmob.getItems().clear();
                tabimmob.getItems().addAll(Installation.getsearchInstallations(searchi.getText().trim()));
            }
            addImmobEvents();
            addInstallationEvents();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
        }
    }

    @FXML
    void search_mat(KeyEvent event) {

        try {
            if (searchm.getText().trim().isEmpty()) {

                tabimmobM.getItems().clear();
                tabimmobM.getItems().addAll(Immob.getImmobs());

            } else {
                tabimmobM.getItems().clear();
                tabimmobM.getItems().addAll(Immob.getsearchImmobs(searchm.getText().trim()));
            }
            addImmobEvents();
            addInstallationEvents();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {

            tabimmob.getItems().addAll(Installation.getInstallations());

            tabimmobM.getItems().addAll(Immob.getImmobs());

        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
        }

        addImmobEvents();
        addInstallationEvents();
    }

    @FXML
    void makeDeco(MouseEvent event) throws IOException {
        LoginController.user = new User();
        Parent q = FXMLLoader.load(getClass().getResource("login.fxml"));

        Scene Command;
        Command = new Scene(q);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        appStage.hide();
        appStage.setScene(Command);
        appStage.setResizable(false);
        appStage.show();

    }

    public void addInstallationEvents() {
        tabimmob.getItems().forEach((a) -> {
            //  System.out.println(new BigDecimal(a.getCompte_act()).toPlainString());
            a.getQuantite().setOnKeyReleased((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        a.setUser_id(LoginController.user.getId());
                        a.editInstallationQuantite();
                    } catch (SQLException | ClassNotFoundException | IOException ex) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText(ex.toString());
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }
                }
            });
            a.getLocal().setOnKeyReleased((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        a.setUser_id(LoginController.user.getId());
                        a.editInstallationLocal();
                    } catch (SQLException | ClassNotFoundException | IOException ex) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText(ex.toString());
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }
                }
            });
            a.getRemarque().setOnKeyReleased((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        a.setUser_id(LoginController.user.getId());
                        a.editInstallationObs();
                    } catch (SQLException | ClassNotFoundException | IOException ex) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText(ex.toString());
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }
                }
            });

        });
    }

    public void addImmobEvents() {
        tabimmobM.getItems().forEach((a) -> {
            //  System.out.println(new BigDecimal(a.getCompte_act()).toPlainString());
            a.getQuantite().setOnKeyReleased((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        a.setUser_id(LoginController.user.getId());
                        a.editImmobQuantite();
                    } catch (SQLException | ClassNotFoundException ex) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText(ex.toString());
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    } catch (IOException ex) {
                        Alert a2 = new Alert(Alert.AlertType.ERROR);
                        a2.setTitle("Erreur");
                        a2.setContentText(ex.toString());
                        a2.setHeaderText(null);
                        a2.showAndWait();
                    }
                }
            });
            a.getLocal().setOnKeyReleased((KeyEvent event) -> {
                try {
                    a.setUser_id(LoginController.user.getId());
                    a.editImmobLocal();
                } catch (SQLException | ClassNotFoundException | IOException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }
            });
            a.getRemarque().setOnKeyReleased((KeyEvent event) -> {
                try {
                    a.setUser_id(LoginController.user.getId());
                    a.editImmobObs();
                } catch (SQLException | ClassNotFoundException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } catch (IOException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }
            });

        });
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
