/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invent_immob;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Historique_i;
import model.Historique_m;
import model.Immob;
import model.Installation;
import model.User;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author bekal
 */
public class MainAppController implements Initializable {

    static User usere = new User();
    /**
     * Initializes the controller class.
     */
    @FXML
    private JFXTextField search_h;
    int code_t = -1, libelle_t = -1, date_t = -1, actif_t = -1, compt_t = -1, code_adm = -1;
    @FXML
    private TableView<User> table;

    @FXML
    private Button okB;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField text;

    @FXML
    private JFXTextField searchimmobT;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXComboBox<String> type;

    @FXML
    private JFXButton importer;

    @FXML
    private JFXTextField searchuserT;

    @FXML
    private JFXButton adduserB;

    @FXML
    private JFXButton edituserB;

    @FXML
    private JFXButton deluserB;
    @FXML
    private JFXComboBox<String> htype;

    @FXML
    private JFXComboBox<String> hyear;

    @FXML
    void adduser(ActionEvent event) throws IOException, Exception {

        User user = new User(username.getText(), password.getText(), type.getSelectionModel().getSelectedItem());
        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {

            if (password.getText().length() >= 8) {
                if (password.getText().equals(username.getText())) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText("Votre mot de passe doit être différent de votre nom d'utilisateur");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } else if (user.test(username.getText())) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(" Ce utilisateur existe déjà");
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } else {
                    user.addUser();
                    clear();
                    table.getItems().clear();
                    if (LoginController.user.type.equals("superAdmin")) {
                        table.getItems().addAll(User.getUsers("Admin"));
                        table.getItems().addAll(User.getUsers("user"));

                    } else if (LoginController.user.type.equals("Admin")) {
                        table.getItems().addAll(User.getUsers("user"));

                    }
                }
            } else {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("Le mot de passe doit contenir au moins 8 caractères");
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } else {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText("Tous les champs sont requis");
            a2.setHeaderText(null);
            a2.showAndWait();
        }
    }

    @FXML
    void deluser(ActionEvent event) {
        if (!table.getSelectionModel().isEmpty()) {
            usere.id = table.getSelectionModel().getSelectedItem().getId();
            try {
                usere.deleteUser();
                clear();
                table.getItems().clear();
                if (LoginController.user.type.equals("superAdmin")) {
                    table.getItems().addAll(User.getUsers("Admin"));
                    table.getItems().addAll(User.getUsers("user"));

                } else if (LoginController.user.type.equals("Admin")) {
                    table.getItems().addAll(User.getUsers("user"));

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
    void affiche(MouseEvent event) {
        if (!table.getSelectionModel().isEmpty()) {
            username.setText(table.getSelectionModel().getSelectedItem().getUsername());
            type.setValue(table.getSelectionModel().getSelectedItem().getType());

        }
    }

    public void clear() {
        username.setText("");
        password.setText("");
        if (LoginController.user.type.equals("superAdmin")) {
            type.setValue("Admin");
        } else if (LoginController.user.type.equals("Admin")) {
            type.setValue("user");
        }
    }

    @FXML
    void edituser(ActionEvent event) throws Exception {
        if (!table.getSelectionModel().isEmpty()) {
            if (!username.getText().isEmpty()) {
                usere.id = table.getSelectionModel().getSelectedItem().getId();

                usere.password = User.getUser(usere.id).getPassword();
                usere.username = username.getText();
                usere.type = type.getSelectionModel().getSelectedItem();
                usere.editUser();
                clear();
                table.getItems().clear();
                if (LoginController.user.type.equals("superAdmin")) {
                    table.getItems().addAll(User.getUsers("Admin"));
                    table.getItems().addAll(User.getUsers("user"));

                } else if (LoginController.user.type.equals("Admin")) {
                    table.getItems().addAll(User.getUsers("user"));

                }

            } else {
                Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                a2.setTitle("Erreur");
                a2.setContentText("Entrez un nom d'utilisateur valide");
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } else {
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Erreur");
            a2.setContentText("aucun utilisateur est selectionné");
            a2.setHeaderText(null);
            a2.showAndWait();
        }

    }

    @FXML
    void searchuser(KeyEvent event) {
        try {
            if (searchuserT.getText().trim().isEmpty()) {
                table.getItems().clear();
                if (LoginController.user.type.equals("superAdmin")) {
                    table.getItems().addAll(User.getUsers("Admin"));
                    table.getItems().addAll(User.getUsers("user"));

                } else if (LoginController.user.type.equals("Admin")) {
                    table.getItems().addAll(User.getUsers("user"));

                }
            } else {
                table.getItems().clear();
                if (LoginController.user.type.equals("superAdmin")) {
                    table.getItems().addAll(User.searchUsers(searchuserT.getText().trim(), "superAdmin"));

                } else if (LoginController.user.type.equals("Admin")) {
                    table.getItems().addAll(User.searchUsers(searchuserT.getText().trim(), "Admin"));

                }
            }

        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
        }
    }
    InputStream ExcelFileToRead;
    @FXML
    private TableView tabhist;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        htype.getItems().addAll("Mobiliers & Materiels", "Installations");
        htype.setValue("Installations");
        try {
            if (!Historique_i.getYears().isEmpty()) {
                hyear.getItems().addAll(Historique_i.getYears());
                hyear.setValue(hyear.getItems().get(0));
            }
        } catch (ClassNotFoundException | SQLException | IOException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
        }

        try {
            tabhist.getItems().addAll(Historique_i.getHistorique_ins(hyear.getValue()));
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
        }
        try {

            if (!Immob.getImmobs().isEmpty()) {
                tab_mat.getItems().addAll(Immob.getImmobs());
            }
            if (!Installation.getInstallations().isEmpty()) {
                tab_ins.getItems().addAll(Installation.getInstallations());
            }
            if (LoginController.user.type.equals("superAdmin")) {
                table.getItems().addAll(User.getUsers("Admin"));
                table.getItems().addAll(User.getUsers("user"));
                type.getItems().addAll("Admin", "user");
                type.setValue("Admin");

            } else if (LoginController.user.type.equals("Admin")) {
                table.getItems().addAll(User.getUsers("user"));
                type.getItems().addAll("user");
                type.setValue("user");
            }
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

    public void addImmobEvents() {
        tab_mat.getItems().forEach((a) -> {

            a.getQuantite().setOnKeyReleased((KeyEvent event) -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        a.setUser_id(LoginController.user.getId());
                        a.editImmobQuantite();
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
                        a.editImmobLocal();
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
                        a.editImmobObs();
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

    public void addInstallationEvents() {
        tab_ins.getItems().forEach((a) -> {

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

        });
    }

    @FXML
    void makeDeco(MouseEvent event) {
        try {
            LoginController.user = new User();
            Parent q = FXMLLoader.load(getClass().getResource("login.fxml"));

            Scene Command;
            Command = new Scene(q);
            Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            appStage.hide();
            appStage.setScene(Command);
            appStage.setResizable(false);
            appStage.show();
        } catch (IOException ex) {

            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();
        }

    }

    @FXML
    private JFXTextField search_ins;
    @FXML
    private JFXTextField search_ma;

    @FXML
    private TableView<Immob> tab_mat;
    @FXML
    private TableView<Installation> tab_ins;

    @FXML
    void importer_ins(ActionEvent event) {
        String path = null;
        File pic = null;
        Stage s = new Stage();
        FileChooser picchooser = new FileChooser();

        picchooser.setTitle("Ouvrir un fichier");

        pic = picchooser.showOpenDialog(s);
        if (pic != null) {

            try {

                path = pic.getAbsolutePath();

                ExcelFileToRead = new FileInputStream(path);

                XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

                XSSFWorkbook test = new XSSFWorkbook();

                XSSFSheet sheet = wb.getSheetAt(0);

                XSSFRow row;
                XSSFCell cell;

                Iterator rows = sheet.rowIterator();
                int i = 0;
                int cel = -1;
                while (rows.hasNext() && cel == -1) {

                    row = (XSSFRow) rows.next();
                    Iterator<Cell> cells = row.cellIterator();
                    int j = 0;
                    while (cells.hasNext()) {
                        cell = (XSSFCell) cells.next();

                        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            if (cell.getStringCellValue().toLowerCase().equals("code")) {
                                code_t = j;
                                cel = i + 3;
                            } else if (cell.getStringCellValue().toLowerCase().equals("libelle")) {
                                libelle_t = j;
                            } else if (cell.getStringCellValue().toLowerCase().equals("compte#actif")) {
                                compt_t = j;
                            } else if (cell.getStringCellValue().toLowerCase().equals("date#acquisition")) {
                                date_t = j;

                            } else if (cell.getStringCellValue().toLowerCase().equals("actif")) {
                                actif_t = j;
                            } else if (cell.getStringCellValue().toLowerCase().equals("code#adm")) {
                                code_adm = j;
                            }

                        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            System.out.print(cell.getNumericCellValue() + " ");
                        } else {
                            //U Can Handel Boolean, Formula, Errors
                        }
//                    System.out.print(";");
                        j++;
                    }
//             if(row.getCell(1)!=null)
//                System.out.println(row.getCell(1).getStringCellValue());
                    i++;
                }
                Installation ins = new Installation();

                rows = sheet.rowIterator();
                try {
                    Installation.clear();
                } catch (ClassNotFoundException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } catch (SQLException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }
                String sql = "insert into Installations(Code,libelle,compt,actif,date_achat,code_adm,date,user_id) Values ";
                SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
                for (int ii = cel; ii < sheet.getLastRowNum(); ii++) {
                    row = sheet.getRow(ii);
                    SimpleDateFormat datef3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    if (row != null) {
                        if (row.getCell(code_t) != null && row.getCell(libelle_t) != null && row.getCell(actif_t) != null && row.getCell(compt_t) != null && row.getCell(date_t) != null && row.getCell(code_adm) != null) {

                            sql += "('" + row.getCell(code_t).getStringCellValue().trim() + "','" + row.getCell(libelle_t).getStringCellValue().replace("'", "''") + "','" + new BigDecimal(row.getCell(compt_t).getNumericCellValue()).toPlainString() + "','" + row.getCell(actif_t).getNumericCellValue() + "','" + datef.format(row.getCell(date_t).getDateCellValue()) + "','" + row.getCell(code_adm).getStringCellValue() + "','" + datef3.format(new Date()) + "'," + LoginController.user.id + "),";

                        }
                    }
                }

                sql = sql.substring(0, sql.length() - 1);

                try {
                    ins.addInstallations(sql);

                } catch (SQLException | ClassNotFoundException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();

                }

            } catch (IOException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();
            }

            tab_ins.getItems().clear();
            try {
                tab_ins.getItems().addAll(Installation.getInstallations());
                addInstallationEvents();
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
    void importer_mat(ActionEvent event) throws IOException {

        String path = null;
        File pic = null;
        Stage s = new Stage();
        FileChooser picchooser = new FileChooser();

        picchooser.setTitle("Ouvrir un fichier");

        pic = picchooser.showOpenDialog(s);
        if (pic != null) {

            try {

                path = pic.getAbsolutePath();

                ExcelFileToRead = new FileInputStream(path);

                XSSFWorkbook wb = new XSSFWorkbook(ExcelFileToRead);

                XSSFWorkbook test = new XSSFWorkbook();

                XSSFSheet sheet = wb.getSheetAt(0);

                XSSFRow row;
                XSSFCell cell;

                Iterator rows = sheet.rowIterator();
                int i = 0;
                int cel = -1;
                while (rows.hasNext() && cel == -1) {

                    row = (XSSFRow) rows.next();
                    Iterator<Cell> cells = row.cellIterator();
                    int j = 0;
                    while (cells.hasNext()) {
                        cell = (XSSFCell) cells.next();

                        if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
                            if (cell.getStringCellValue().toLowerCase().equals("code")) {
                                code_t = j;
                                cel = i + 3;
                            } else if (cell.getStringCellValue().toLowerCase().equals("libelle")) {
                                libelle_t = j;
                            } else if (cell.getStringCellValue().toLowerCase().equals("compte#actif")) {
                                compt_t = j;
                            } else if (cell.getStringCellValue().toLowerCase().equals("date#acquisition")) {
                                date_t = j;

                            } else if (cell.getStringCellValue().toLowerCase().equals("actif")) {
                                actif_t = j;
                            } else if (cell.getStringCellValue().toLowerCase().equals("code#adm")) {
                                code_adm = j;
                            }

                        } else if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
                            System.out.print(cell.getNumericCellValue() + " ");
                        } else {
                            //U Can Handel Boolean, Formula, Errors
                        }
//                    System.out.print(";");
                        j++;
                    }
//             if(row.getCell(1)!=null)
//                System.out.println(row.getCell(1).getStringCellValue());
                    i++;
                }
                Immob imm = new Immob();

                rows = sheet.rowIterator();
                try {
                    Immob.clear();
                } catch (ClassNotFoundException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                } catch (SQLException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();
                }
                String sql = "insert into materiels(Code,libelle,compt,actif,date_achat,code_adm,date,user_id) Values ";
                SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
                for (int ii = cel; ii < sheet.getLastRowNum(); ii++) {
                    row = sheet.getRow(ii);
                    SimpleDateFormat datef3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    if (row != null) {
                        if (row.getCell(code_t) != null && row.getCell(libelle_t) != null && row.getCell(actif_t) != null && row.getCell(compt_t) != null && row.getCell(date_t) != null && row.getCell(code_adm) != null) {

                            sql += "('" + row.getCell(code_t).getStringCellValue().trim() + "','" + row.getCell(libelle_t).getStringCellValue().replace("'", "''") + "','" + new BigDecimal(row.getCell(compt_t).getNumericCellValue()).toPlainString() + "','" + row.getCell(actif_t).getNumericCellValue() + "','" + datef.format(row.getCell(date_t).getDateCellValue()) + "','" + row.getCell(code_adm).getStringCellValue() + "','" + datef3.format(new Date()) + "'," + LoginController.user.id + "),";

                        }
                    }
                }

                sql = sql.substring(0, sql.length() - 1);

                try {
                    imm.addImmobs(sql);

                } catch (SQLException | ClassNotFoundException ex) {
                    Alert a2 = new Alert(Alert.AlertType.ERROR);
                    a2.setTitle("Erreur");
                    a2.setContentText(ex.toString());
                    a2.setHeaderText(null);
                    a2.showAndWait();

                }

            } catch (FileNotFoundException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();
            }

            tab_mat.getItems().clear();
            try {
                tab_mat.getItems().addAll(Immob.getImmobs());
                addImmobEvents();
            } catch (SQLException | ClassNotFoundException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();

            }
        }
    }

    @FXML
    void search_his(KeyEvent event) {
        if (htype.getValue().equals("Mobiliers & Materiels")) {
            tabhist.getItems().clear();
            try {
                tabhist.getItems().addAll(Historique_m.getsearchHistorique_ms(search_h.getText(), hyear.getValue()));
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } else if (htype.getValue().equals("Installations")) {
            tabhist.getItems().clear();
            try {
                tabhist.getItems().addAll(Historique_i.getsearchHistorique_is(search_h.getText(), hyear.getValue()));
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
    void search_inst(KeyEvent event) {
        try {
            if (search_ins.getText().trim().isEmpty()) {

                tab_ins.getItems().clear();
                tab_ins.getItems().addAll(Installation.getInstallations());
                addInstallationEvents();

            } else {
                tab_ins.getItems().clear();
                tab_ins.getItems().addAll(Installation.getsearchInstallations(search_ins.getText().trim()));
                addInstallationEvents();
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
    void search_mat(KeyEvent event) {
        try {
            if (search_ma.getText().trim().isEmpty()) {

                tab_mat.getItems().clear();
                tab_mat.getItems().addAll(Immob.getImmobs());
                addImmobEvents();

            } else {
                tab_mat.getItems().clear();
                tab_mat.getItems().addAll(Immob.getsearchImmobs(search_ma.getText().trim()));
                addImmobEvents();
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
    void filtrer(MouseEvent event) {

        if (htype.getValue().equals("Mobiliers & Materiels")) {

            tabhist.getItems().clear();
            try {
                String htyp = "";
                if (!hyear.getValue().isEmpty()) {
                    htyp = hyear.getValue();

                    hyear.getItems().clear();
                    hyear.getItems().addAll(Historique_m.getYears());

                    hyear.setValue(htyp);

                    tabhist.getItems().addAll(Historique_m.getHistorique_m(htyp));
                }
            } catch (SQLException | ClassNotFoundException | IOException ex) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText(ex.toString());
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } else if (htype.getValue().equals("Installations")) {
            tabhist.getItems().clear();
            try {
                String htyp = "";
                if (!hyear.getValue().isEmpty()) {
                    htyp = hyear.getValue();

                    hyear.getItems().clear();
                    hyear.getItems().addAll(Historique_i.getYears());

                    hyear.setValue(htyp);

                    tabhist.getItems().addAll(Historique_i.getHistorique_ins(htyp));
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
    void print_ins(MouseEvent event) {
        try {

            for (String loca : Installation.getLocals()) {

                String sheetName = "BUREAU N " + loca + " Installations";//name of sheet

                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet(sheetName);
                XSSFRow row1 = sheet.createRow(0);
                CellStyle cellStyleCenter = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle cellStyleCenter1 = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle fontstyle = row1.getSheet().getWorkbook().createCellStyle();
                cellStyleCenter.setBorderBottom(BorderStyle.THIN);
                cellStyleCenter.setBorderLeft(BorderStyle.THIN);
                cellStyleCenter.setBorderRight(BorderStyle.THIN);
                cellStyleCenter.setBorderTop(BorderStyle.THIN);
                cellStyleCenter1.setBorderBottom(BorderStyle.THICK);
                cellStyleCenter1.setBorderLeft(BorderStyle.THICK);
                cellStyleCenter1.setBorderRight(BorderStyle.THICK);
                cellStyleCenter1.setBorderTop(BorderStyle.THICK);
                cellStyleCenter.setWrapText(true);
                cellStyleCenter1.setWrapText(true);
                Font underlineFont = wb.createFont();
                Font boldfont = wb.createFont();
                boldfont.setBold(true);
                //cellStyleCenter1.setFont(boldfont);
                underlineFont.setUnderline(XSSFFont.U_SINGLE);
                underlineFont.setBold(true);
                Font bigFont = wb.createFont();
                bigFont.setBold(true);
                bigFont.setFontHeight((short) 250);
                CellStyle BigCellStyle = row1.getSheet().getWorkbook().createCellStyle();
                BigCellStyle.setFont(bigFont);

                fontstyle.setFont(underlineFont);
                XSSFCell cel = row1.createCell(0);
                cel.setCellValue("ALFAPIPE GHARDAIA");
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(1);
                cel = row1.createCell(0);
                cel.setCellValue("BUREAU: " + loca);
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(2);
                cel = row1.createCell(0);
                SimpleDateFormat form = new SimpleDateFormat("YYYY");
                cel.setCellValue("INVENTAIRE: " + form.format(new Date()));
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(3);
                cel = row1.createCell(2);
                cel.setCellValue("BUREAU N°: " + loca);
                cel.setCellStyle(BigCellStyle);
                row1 = sheet.createRow(5);
                cel = row1.createCell(1);
                cel.setCellValue("DESIGNATION");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(4);
                cel.setCellValue("DAT_ACQUI");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(5);
                cel.setCellValue("BUREAU");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(6);
                cel.setCellValue("OBS");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(2);
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(3);
                cel.setCellStyle(cellStyleCenter1);
                CellRangeAddress r = new CellRangeAddress(5, 5, 1, 3);
                sheet.addMergedRegion(r);

                RegionUtil.setBorderBottom(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, r, sheet);
                //iteraing r number of rows
                int i = 6;

                for (Installation ins : Installation.getLocalInstallations(loca)) {
                    XSSFRow row = sheet.createRow(i);

                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(ins.getCode());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(1);
                    cell.setCellValue(ins.getLibelle());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(4);
                    cell.setCellValue(ins.getDate_acq().substring(8) + "/" + ins.getDate_acq().substring(5, 7) + "/" + ins.getDate_acq().substring(0, 4));
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(5);
                    cell.setCellValue(ins.getLocal().getText());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(6);
                    cell.setCellValue(ins.getRemarque().getText());
                    cell.setCellStyle(cellStyleCenter);

                    cell = row.createCell(2);
                    cell.setCellStyle(cellStyleCenter1);
                    cell = row.createCell(3);
                    cell.setCellStyle(cellStyleCenter1);
                    row1.getCell(1).setCellStyle(cellStyleCenter);
                    CellRangeAddress r1 = new CellRangeAddress(i, i, 1, 3);
                    sheet.addMergedRegion(r1);
                    row.getCell(1).setCellStyle(cellStyleCenter);

                    if (i > 6) {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THIN, r1, sheet);
                    } else {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THICK, r1, sheet);
                    }
                    i++;

                }
                RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                row1 = sheet.createRow(i);
                cel = row1.createCell(0);
                cel.setCellValue("Le responsable");
                cel.setCellStyle(fontstyle);
                cel = row1.createCell(2);
                cel.setCellValue("Nom et visa de l'inventoriste");
                cel.setCellStyle(fontstyle);
                cel = row1.createCell(5);
                cel.setCellValue("Le responsable du materiel");
                cel.setCellStyle(fontstyle);
                row1 = sheet.getRow(5);
                row1.createCell(0);
                row1.getCell(0).setCellValue("CODE");
                row1.getCell(0).setCellStyle(cellStyleCenter1);

                sheet.setColumnWidth(1, 5600);

                sheet.setColumnWidth(0, 2700);

                sheet.setColumnWidth(4, 3000);

                sheet.setColumnWidth(6, 3400);
                XSSFPrintSetup printSetup = (XSSFPrintSetup) sheet.getPrintSetup();
                printSetup.setHeaderMargin(0.5D);
                printSetup.setFooterMargin(0.5D);
                FileOutputStream fileOut = new FileOutputStream("app.xlsx");

                //write this workbook to an Outputstream.
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
                Desktop.getDesktop().print(new File("app.xlsx"));
                // }

            }
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Information");
            a2.setContentText("Operation terminé sans erreurs");
            a2.setHeaderText(null);
            a2.showAndWait();
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();

        }
    }

    @FXML
    void export_ins(MouseEvent event) {
        try {
//            folder choosing
            Stage stage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);

            if (selectedDirectory == null) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("Selectionner un fichier ");
                a2.setHeaderText(null);
                a2.showAndWait();
            } else {

                for (String loca : Installation.getLocals()) {
                    String excelFileName = selectedDirectory.getAbsolutePath() + "\\Bureau n " + loca + "_installations.xlsx";//name of excel file

                    String sheetName = "BUREAU N " + loca;//name of sheet

                    XSSFWorkbook wb = new XSSFWorkbook();
                    XSSFSheet sheet = wb.createSheet(sheetName);
                    XSSFRow row1 = sheet.createRow(0);
                    CellStyle cellStyleCenter = row1.getSheet().getWorkbook().createCellStyle();
                    CellStyle cellStyleCenter1 = row1.getSheet().getWorkbook().createCellStyle();
                    CellStyle fontstyle = row1.getSheet().getWorkbook().createCellStyle();
                    cellStyleCenter.setBorderBottom(BorderStyle.THIN);
                    cellStyleCenter.setBorderLeft(BorderStyle.THIN);
                    cellStyleCenter.setBorderRight(BorderStyle.THIN);
                    cellStyleCenter.setBorderTop(BorderStyle.THIN);
                    cellStyleCenter1.setBorderBottom(BorderStyle.THICK);
                    cellStyleCenter1.setBorderLeft(BorderStyle.THICK);
                    cellStyleCenter1.setBorderRight(BorderStyle.THICK);
                    cellStyleCenter1.setBorderTop(BorderStyle.THICK);
                    cellStyleCenter.setWrapText(true);
                    cellStyleCenter1.setWrapText(true);
                    Font underlineFont = wb.createFont();
                    Font boldfont = wb.createFont();
                    //boldfont.setBold(true);
                    cellStyleCenter1.setFont(boldfont);
                    underlineFont.setUnderline(XSSFFont.U_SINGLE);
                    underlineFont.setBold(true);
                    Font bigFont = wb.createFont();
                    bigFont.setBold(true);
                    bigFont.setFontHeight((short) 250);
                    CellStyle BigCellStyle = row1.getSheet().getWorkbook().createCellStyle();
                    BigCellStyle.setFont(bigFont);

                    fontstyle.setFont(underlineFont);
                    XSSFCell cel = row1.createCell(0);
                    cel.setCellValue("ALFAPIPE GHARDAIA");
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.createRow(1);
                    cel = row1.createCell(0);
                    cel.setCellValue("BUREAU: " + loca);
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.createRow(2);
                    cel = row1.createCell(0);
                    SimpleDateFormat form = new SimpleDateFormat("YYYY");
                    cel.setCellValue("INVENTAIRE: " + form.format(new Date()));
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.createRow(3);
                    cel = row1.createCell(2);
                    cel.setCellValue("BUREAU N°: " + loca);
                    cel.setCellStyle(BigCellStyle);
                    row1 = sheet.createRow(5);

                    cel = row1.createCell(1);
                    cel.setCellValue("DESIGNATION");
                    cel.setCellStyle(cellStyleCenter1);

                    cel = row1.createCell(3);
                    cel.setCellValue("");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(2);
                    cel.setCellValue("");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(4);
                    cel.setCellValue("DAT_ACQUI");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(5);
                    cel.setCellValue("BUREAU");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(6);
                    cel.setCellValue("OBS");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(2);
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(3);
                    cel.setCellStyle(cellStyleCenter1);
                    CellRangeAddress r = new CellRangeAddress(5, 5, 1, 3);

                    cel = row1.createCell(3);
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(2);
                    cel.setCellStyle(cellStyleCenter1);
                    sheet.addMergedRegion(r);
                    cel = row1.createCell(3);
                    cel.setCellStyle(cellStyleCenter);
                    cel = row1.createCell(2);
                    cel.setCellStyle(cellStyleCenter);
                    RegionUtil.setBorderBottom(BorderStyle.THICK, r, sheet);
                    RegionUtil.setBorderLeft(BorderStyle.THICK, r, sheet);
                    RegionUtil.setBorderRight(BorderStyle.THICK, r, sheet);
                    RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                    //iteraing r number of rows
                    int i = 6;

                    for (Installation ins : Installation.getLocalInstallations(loca)) {
                        XSSFRow row = sheet.createRow(i);

                        XSSFCell cell = row.createCell(0);
                        cell.setCellValue(ins.getCode());
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(1);
                        cell.setCellValue(ins.getLibelle());
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(4);
                        cell.setCellValue(ins.getDate_acq().substring(8) + "/" + ins.getDate_acq().substring(5, 7) + "/" + ins.getDate_acq().substring(0, 4));
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(5);
                        cell.setCellValue(ins.getLocal().getText());
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(6);
                        cell.setCellValue(ins.getRemarque().getText());
                        cell.setCellStyle(cellStyleCenter);

                        cell = row.createCell(2);
                        cell.setCellStyle(cellStyleCenter1);
                        cell = row.createCell(3);
                        cell.setCellStyle(cellStyleCenter1);
                        row1.getCell(1).setCellStyle(cellStyleCenter);
                        CellRangeAddress r1 = new CellRangeAddress(i, i, 1, 3);
                        sheet.addMergedRegion(r1);
                        row.getCell(1).setCellStyle(cellStyleCenter);
                        if (i > 6) {
                            RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderTop(BorderStyle.THIN, r1, sheet);
                        } else {
                            RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderTop(BorderStyle.THICK, r1, sheet);
                        }
                        i++;

                    }
                    RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                    row1 = sheet.createRow(i);
                    cel = row1.createCell(0);
                    cel.setCellValue("Le responsable");
                    cel.setCellStyle(fontstyle);
                    cel = row1.createCell(2);
                    cel.setCellValue("Nom et visa de l'inventoriste");
                    cel.setCellStyle(fontstyle);
                    cel = row1.createCell(5);
                    cel.setCellValue("Le responsable du materiel");
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.getRow(5);
                    row1.createCell(0);
                    row1.getCell(0).setCellValue("CODE");
                    row1.getCell(0).setCellStyle(cellStyleCenter1);

                    sheet.setColumnWidth(1, 5600);
                    sheet.setColumnWidth(0, 2700);

                    sheet.setColumnWidth(4, 3000);

                    sheet.setColumnWidth(6, 3400);
                    FileOutputStream fileOut = new FileOutputStream(excelFileName);
                    XSSFPrintSetup printSetup = (XSSFPrintSetup) sheet.getPrintSetup();
                    printSetup.setHeaderMargin(0.5D);
                    printSetup.setFooterMargin(0.5D);
                    //write this workbook to an Outputstream.
                    wb.write(fileOut);
                    fileOut.flush();
                    fileOut.close();

                }
                Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                a2.setTitle("Information");
                a2.setContentText("Les fichiers sont enregistrés dans " + selectedDirectory.getAbsolutePath());

                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();

        }
    }

    @FXML
    void export_mat(MouseEvent event) {
        try {
//            folder choosing
            Stage stage = new Stage();
            DirectoryChooser directoryChooser = new DirectoryChooser();
            File selectedDirectory = directoryChooser.showDialog(stage);

            if (selectedDirectory == null) {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("Selectionner un fichier ");
                a2.setHeaderText(null);
                a2.showAndWait();
            } else {

                for (String loca : Immob.getLocals()) {
                    String excelFileName = selectedDirectory.getAbsolutePath() + "\\Bureau n " + loca + "_Materiel.xlsx";//name of excel file

                    String sheetName = "BUREAU N " + loca;//name of sheet

                    XSSFWorkbook wb = new XSSFWorkbook();
                    XSSFSheet sheet = wb.createSheet(sheetName);
                    XSSFRow row1 = sheet.createRow(0);
                    CellStyle cellStyleCenter = row1.getSheet().getWorkbook().createCellStyle();
                    CellStyle cellStyleCenter1 = row1.getSheet().getWorkbook().createCellStyle();
                    CellStyle fontstyle = row1.getSheet().getWorkbook().createCellStyle();
                    cellStyleCenter.setBorderBottom(BorderStyle.THIN);
                    cellStyleCenter.setBorderLeft(BorderStyle.THIN);
                    cellStyleCenter.setBorderRight(BorderStyle.THIN);
                    cellStyleCenter.setBorderTop(BorderStyle.THIN);
                    cellStyleCenter1.setBorderBottom(BorderStyle.THICK);
                    cellStyleCenter1.setBorderLeft(BorderStyle.THICK);
                    cellStyleCenter1.setBorderRight(BorderStyle.THICK);
                    cellStyleCenter1.setBorderTop(BorderStyle.THICK);
                    cellStyleCenter.setWrapText(true);
                    cellStyleCenter1.setWrapText(true);
                    Font underlineFont = wb.createFont();
                    Font boldfont = wb.createFont();
                    boldfont.setBold(true);
                    //cellStyleCenter1.setFont(boldfont);
                    underlineFont.setUnderline(XSSFFont.U_SINGLE);
                    underlineFont.setBold(true);
                    Font bigFont = wb.createFont();
                    bigFont.setBold(true);
                    bigFont.setFontHeight((short) 250);
                    CellStyle BigCellStyle = row1.getSheet().getWorkbook().createCellStyle();
                    BigCellStyle.setFont(bigFont);

                    fontstyle.setFont(underlineFont);
                    XSSFCell cel = row1.createCell(0);
                    cel.setCellValue("ALFAPIPE GHARDAIA");
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.createRow(1);
                    cel = row1.createCell(0);
                    cel.setCellValue("BUREAU: " + loca);
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.createRow(2);
                    cel = row1.createCell(0);
                    SimpleDateFormat form = new SimpleDateFormat("YYYY");
                    cel.setCellValue("INVENTAIRE: " + form.format(new Date()));
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.createRow(3);
                    cel = row1.createCell(2);
                    cel.setCellValue("BUREAU N°: " + loca);
                    cel.setCellStyle(BigCellStyle);
                    row1 = sheet.createRow(5);

                    cel = row1.createCell(1);
                    cel.setCellValue("DESIGNATION");
                    cel.setCellStyle(cellStyleCenter1);

                    cel = row1.createCell(3);
                    cel.setCellValue("");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(2);
                    cel.setCellValue("");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(4);
                    cel.setCellValue("DAT_ACQUI");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(5);
                    cel.setCellValue("BUREAU");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(6);
                    cel.setCellValue("OBS");
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(2);
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(3);
                    cel.setCellStyle(cellStyleCenter1);
                    CellRangeAddress r = new CellRangeAddress(5, 5, 1, 3);

                    cel = row1.createCell(3);
                    cel.setCellStyle(cellStyleCenter1);
                    cel = row1.createCell(2);
                    cel.setCellStyle(cellStyleCenter1);
                    sheet.addMergedRegion(r);
                    cel = row1.createCell(3);
                    cel.setCellStyle(cellStyleCenter);
                    cel = row1.createCell(2);
                    cel.setCellStyle(cellStyleCenter);
                    RegionUtil.setBorderBottom(BorderStyle.THICK, r, sheet);
                    RegionUtil.setBorderLeft(BorderStyle.THICK, r, sheet);
                    RegionUtil.setBorderRight(BorderStyle.THICK, r, sheet);
                    RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                    //iteraing r number of rows
                    int i = 6;

                    for (Immob ins : Immob.getLocalmmobs(loca)) {
                        XSSFRow row = sheet.createRow(i);

                        XSSFCell cell = row.createCell(0);
                        cell.setCellValue(ins.getCode());
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(1);
                        cell.setCellValue(ins.getLibelle());
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(4);
                        cell.setCellValue(ins.getDate_acq().substring(8) + "/" + ins.getDate_acq().substring(5, 7) + "/" + ins.getDate_acq().substring(0, 4));
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(5);
                        cell.setCellValue(ins.getLocal().getText());
                        cell.setCellStyle(cellStyleCenter);
                        cell = row.createCell(6);
                        cell.setCellValue(ins.getRemarque().getText());
                        cell.setCellStyle(cellStyleCenter);

                        cell = row.createCell(2);
                        cell.setCellStyle(cellStyleCenter1);
                        cell = row.createCell(3);
                        cell.setCellStyle(cellStyleCenter1);
                        row1.getCell(1).setCellStyle(cellStyleCenter);
                        CellRangeAddress r1 = new CellRangeAddress(i, i, 1, 3);
                        sheet.addMergedRegion(r1);
                        row.getCell(1).setCellStyle(cellStyleCenter);
                        if (i > 6) {
                            RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderTop(BorderStyle.THIN, r1, sheet);
                        } else {
                            RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                            RegionUtil.setBorderTop(BorderStyle.THICK, r1, sheet);
                        }
                        i++;

                    }
                    RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                    row1 = sheet.createRow(i);
                    cel = row1.createCell(0);
                    cel.setCellValue("Le responsable");
                    cel.setCellStyle(fontstyle);
                    cel = row1.createCell(2);
                    cel.setCellValue("Nom et visa de l'inventoriste");
                    cel.setCellStyle(fontstyle);
                    cel = row1.createCell(5);
                    cel.setCellValue("Le responsable du materiel");
                    cel.setCellStyle(fontstyle);
                    row1 = sheet.getRow(5);
                    row1.createCell(0);
                    row1.getCell(0).setCellValue("CODE");
                    row1.getCell(0).setCellStyle(cellStyleCenter1);

                    sheet.setColumnWidth(1, 5600);
                    sheet.setColumnWidth(0, 2700);

                    sheet.setColumnWidth(4, 3000);

                    sheet.setColumnWidth(6, 3400);
                    FileOutputStream fileOut = new FileOutputStream(excelFileName);
                    XSSFPrintSetup printSetup = (XSSFPrintSetup) sheet.getPrintSetup();
                    printSetup.setHeaderMargin(0.5D);
                    printSetup.setFooterMargin(0.5D);
                    //write this workbook to an Outputstream.
                    wb.write(fileOut);
                    fileOut.flush();
                    fileOut.close();

                }
                Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                a2.setTitle("Information");
                a2.setContentText("Les fichiers sont enregistrés dans " + selectedDirectory.getAbsolutePath());

                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();

        }
    }

    @FXML
    void print_mat(MouseEvent event) {
        try {
            for (String loca : Immob.getLocals()) {

                String sheetName = "BUREAU N " + loca + " Materiel";//name of sheet

                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet(sheetName);
                XSSFRow row1 = sheet.createRow(0);
                CellStyle cellStyleCenter = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle cellStyleCenter1 = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle fontstyle = row1.getSheet().getWorkbook().createCellStyle();
                cellStyleCenter.setBorderBottom(BorderStyle.THIN);
                cellStyleCenter.setBorderLeft(BorderStyle.THIN);
                cellStyleCenter.setBorderRight(BorderStyle.THIN);
                cellStyleCenter.setBorderTop(BorderStyle.THIN);
                cellStyleCenter1.setBorderBottom(BorderStyle.THICK);
                cellStyleCenter1.setBorderLeft(BorderStyle.THICK);
                cellStyleCenter1.setBorderRight(BorderStyle.THICK);
                cellStyleCenter1.setBorderTop(BorderStyle.THICK);
                cellStyleCenter.setWrapText(true);
                cellStyleCenter1.setWrapText(true);
                Font underlineFont = wb.createFont();
                Font boldfont = wb.createFont();
                boldfont.setBold(true);
                //cellStyleCenter1.setFont(boldfont);
                underlineFont.setUnderline(XSSFFont.U_SINGLE);
                underlineFont.setBold(true);
                Font bigFont = wb.createFont();
                bigFont.setBold(true);
                bigFont.setFontHeight((short) 250);
                CellStyle BigCellStyle = row1.getSheet().getWorkbook().createCellStyle();
                BigCellStyle.setFont(bigFont);

                fontstyle.setFont(underlineFont);
                XSSFCell cel = row1.createCell(0);
                cel.setCellValue("ALFAPIPE GHARDAIA");
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(1);
                cel = row1.createCell(0);
                cel.setCellValue("BUREAU: " + loca);
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(2);
                cel = row1.createCell(0);
                SimpleDateFormat form = new SimpleDateFormat("YYYY");
                cel.setCellValue("INVENTAIRE: " + form.format(new Date()));
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(3);
                cel = row1.createCell(2);
                cel.setCellValue("BUREAU N°: " + loca);
                cel.setCellStyle(BigCellStyle);
                row1 = sheet.createRow(5);
                cel = row1.createCell(1);
                cel.setCellValue("DESIGNATION");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(4);
                cel.setCellValue("DAT_ACQUI");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(5);
                cel.setCellValue("BUREAU");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(6);
                cel.setCellValue("OBS");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(2);
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(3);
                cel.setCellStyle(cellStyleCenter1);
                CellRangeAddress r = new CellRangeAddress(5, 5, 1, 3);
                sheet.addMergedRegion(r);

                RegionUtil.setBorderBottom(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, r, sheet);
                //iteraing r number of rows
                int i = 6;

                for (Immob ins : Immob.getLocalmmobs(loca)) {
                    XSSFRow row = sheet.createRow(i);

                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(ins.getCode());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(1);
                    cell.setCellValue(ins.getLibelle());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(4);
                    cell.setCellValue(ins.getDate_acq().substring(8) + "/" + ins.getDate_acq().substring(5, 7) + "/" + ins.getDate_acq().substring(0, 4));
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(5);
                    cell.setCellValue(ins.getLocal().getText());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(6);
                    cell.setCellValue(ins.getRemarque().getText());
                    cell.setCellStyle(cellStyleCenter);

                    cell = row.createCell(2);
                    cell.setCellStyle(cellStyleCenter1);
                    cell = row.createCell(3);
                    cell.setCellStyle(cellStyleCenter1);
                    row1.getCell(1).setCellStyle(cellStyleCenter);
                    CellRangeAddress r1 = new CellRangeAddress(i, i, 1, 3);
                    sheet.addMergedRegion(r1);
                    row.getCell(1).setCellStyle(cellStyleCenter);
                    if (i > 6) {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THIN, r1, sheet);
                    } else {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THICK, r1, sheet);
                    }
                    i++;

                }
                RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                row1 = sheet.createRow(i);
                cel = row1.createCell(0);
                cel.setCellValue("Le responsable");
                cel.setCellStyle(fontstyle);
                cel = row1.createCell(2);
                cel.setCellValue("Nom et visa de l'inventoriste");
                cel.setCellStyle(fontstyle);
                cel = row1.createCell(5);
                cel.setCellValue("Le responsable du materiel");
                cel.setCellStyle(fontstyle);
                row1 = sheet.getRow(5);
                row1.createCell(0);
                row1.getCell(0).setCellValue("CODE");
                row1.getCell(0).setCellStyle(cellStyleCenter1);

                sheet.setColumnWidth(1, 5600);

                sheet.setColumnWidth(0, 2700);

                sheet.setColumnWidth(4, 3000);

                sheet.setColumnWidth(6, 3400);
                FileOutputStream fileOut = new FileOutputStream("app.xlsx");
                XSSFPrintSetup printSetup = (XSSFPrintSetup) sheet.getPrintSetup();
                printSetup.setHeaderMargin(0.5D);
                printSetup.setFooterMargin(0.5D);
                //write this workbook to an Outputstream.
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
                Desktop.getDesktop().print(new File("app.xlsx"));
                // }

            }
            Alert a2 = new Alert(Alert.AlertType.INFORMATION);
            a2.setTitle("Information");
            a2.setContentText("Operation terminé sans erreurs");
            a2.setHeaderText(null);
            a2.showAndWait();
        } catch (SQLException | IOException | ClassNotFoundException ex) {
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

    @FXML
    void ecart_ins(MouseEvent event) {
        try {
            if (!tab_ins.getItems().isEmpty()) {

                String sheetName = "ECARTS INSTALLATIONS";//name of sheet

                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet(sheetName);
                XSSFRow row1 = sheet.createRow(0);
                CellStyle cellStyleCenter = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle cellStyleCenter1 = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle fontstyle = row1.getSheet().getWorkbook().createCellStyle();
                cellStyleCenter.setBorderBottom(BorderStyle.THIN);
                cellStyleCenter.setBorderLeft(BorderStyle.THIN);
                cellStyleCenter.setBorderRight(BorderStyle.THIN);
                cellStyleCenter.setBorderTop(BorderStyle.THIN);
                cellStyleCenter1.setBorderBottom(BorderStyle.THICK);
                cellStyleCenter1.setBorderLeft(BorderStyle.THICK);
                cellStyleCenter1.setBorderRight(BorderStyle.THICK);
                cellStyleCenter1.setBorderTop(BorderStyle.THICK);
                cellStyleCenter.setWrapText(true);
                cellStyleCenter1.setWrapText(true);
                Font underlineFont = wb.createFont();
                Font boldfont = wb.createFont();
                boldfont.setBold(true);
                //cellStyleCenter1.setFont(boldfont);
                underlineFont.setUnderline(XSSFFont.U_SINGLE);
                underlineFont.setBold(true);
                Font bigFont = wb.createFont();
                bigFont.setBold(true);
                bigFont.setFontHeight((short) 250);
                CellStyle BigCellStyle = row1.getSheet().getWorkbook().createCellStyle();
                BigCellStyle.setFont(bigFont);

                fontstyle.setFont(underlineFont);
                XSSFCell cel = row1.createCell(0);
                cel.setCellValue("ALFAPIPE GHARDAIA");
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(1);
                cel = row1.createCell(0);
                cel.setCellValue("ECARTS : INSTALLATIONS");
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(2);
                cel = row1.createCell(0);
                SimpleDateFormat form = new SimpleDateFormat("YYYY");
                cel.setCellValue("INVENTAIRE: " + form.format(new Date()));
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(3);
                cel = row1.createCell(2);
                cel.setCellValue("ECARTS : INSTALLATIONS");
                cel.setCellStyle(BigCellStyle);
                row1 = sheet.createRow(5);
                cel = row1.createCell(1);
                cel.setCellValue("DESIGNATION");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(4);
                cel.setCellValue("DAT_ACQUI");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(5);
                cel.setCellValue("BUREAU");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(6);
                cel.setCellValue("OBS");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(2);
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(3);
                cel.setCellStyle(cellStyleCenter1);
                CellRangeAddress r = new CellRangeAddress(5, 5, 1, 3);
                sheet.addMergedRegion(r);

                RegionUtil.setBorderBottom(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, r, sheet);
                //iteraing r number of rows
                int i = 6;

                for (Installation ins : Installation.getInstallationsEcarts()) {
                    XSSFRow row = sheet.createRow(i);

                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(ins.getCode());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(1);
                    cell.setCellValue(ins.getLibelle());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(4);
                    cell.setCellValue(ins.getDate_acq().substring(8) + "/" + ins.getDate_acq().substring(5, 7) + "/" + ins.getDate_acq().substring(0, 4));
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(5);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(6);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyleCenter);

                    cell = row.createCell(2);
                    cell.setCellStyle(cellStyleCenter1);
                    cell = row.createCell(3);
                    cell.setCellStyle(cellStyleCenter1);
                    row1.getCell(1).setCellStyle(cellStyleCenter);
                    CellRangeAddress r1 = new CellRangeAddress(i, i, 1, 3);
                    sheet.addMergedRegion(r1);
                    row.getCell(1).setCellStyle(cellStyleCenter);
                    if (i > 6) {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THIN, r1, sheet);
                    } else {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THICK, r1, sheet);
                    }
                    i++;

                }
                RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                 
                row1.createCell(0);
                row1.getCell(0).setCellValue("CODE");
                row1.getCell(0).setCellStyle(cellStyleCenter1);

                sheet.setColumnWidth(1, 5600);

                sheet.setColumnWidth(0, 2700);

                sheet.setColumnWidth(4, 3000);

                sheet.setColumnWidth(6, 3400);
                FileOutputStream fileOut = new FileOutputStream("app.xlsx");
                XSSFPrintSetup printSetup = (XSSFPrintSetup) sheet.getPrintSetup();
                printSetup.setHeaderMargin(0.5D);
                printSetup.setFooterMargin(0.5D);
                //write this workbook to an Outputstream.
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
                Desktop.getDesktop().print(new File("app.xlsx"));
                // }

                Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                a2.setTitle("Information");
                a2.setContentText("Operation terminé sans erreurs");
                a2.setHeaderText(null);
                a2.showAndWait();
            } else {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("tableau vide");
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();

        }
    }

    @FXML
    void ecart_mat(MouseEvent event) {
        try {
            if (!tab_mat.getItems().isEmpty()) {

                String sheetName = "ECARTS MOBILIER & MATERIEL DE BUREAU";//name of sheet

                XSSFWorkbook wb = new XSSFWorkbook();
                XSSFSheet sheet = wb.createSheet(sheetName);
                XSSFRow row1 = sheet.createRow(0);
                CellStyle cellStyleCenter = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle cellStyleCenter1 = row1.getSheet().getWorkbook().createCellStyle();
                CellStyle fontstyle = row1.getSheet().getWorkbook().createCellStyle();
                cellStyleCenter.setBorderBottom(BorderStyle.THIN);
                cellStyleCenter.setBorderLeft(BorderStyle.THIN);
                cellStyleCenter.setBorderRight(BorderStyle.THIN);
                cellStyleCenter.setBorderTop(BorderStyle.THIN);
                cellStyleCenter1.setBorderBottom(BorderStyle.THICK);
                cellStyleCenter1.setBorderLeft(BorderStyle.THICK);
                cellStyleCenter1.setBorderRight(BorderStyle.THICK);
                cellStyleCenter1.setBorderTop(BorderStyle.THICK);
                cellStyleCenter.setWrapText(true);
                cellStyleCenter1.setWrapText(true);
                Font underlineFont = wb.createFont();
                Font boldfont = wb.createFont();
                boldfont.setBold(true);
                //cellStyleCenter1.setFont(boldfont);
                underlineFont.setUnderline(XSSFFont.U_SINGLE);
                underlineFont.setBold(true);
                Font bigFont = wb.createFont();
                bigFont.setBold(true);
                bigFont.setFontHeight((short) 250);
                CellStyle BigCellStyle = row1.getSheet().getWorkbook().createCellStyle();
                BigCellStyle.setFont(bigFont);

                fontstyle.setFont(underlineFont);
                XSSFCell cel = row1.createCell(0);
                cel.setCellValue("ALFAPIPE GHARDAIA");
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(3);
                cel = row1.createCell(1);
                cel.setCellValue("ECARTS : MOBILIER & MATERIEL DE BUREAU");
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(2);
                cel = row1.createCell(0);
                SimpleDateFormat form = new SimpleDateFormat("YYYY");
                cel.setCellValue("INVENTAIRE: " + form.format(new Date()));
                cel.setCellStyle(fontstyle);
                row1 = sheet.createRow(3);
                cel = row1.createCell(1);
                cel.setCellValue("ECARTS : MOBILIER & MATERIEL DE BUREAU");
                cel.setCellStyle(BigCellStyle);
                row1 = sheet.createRow(5);
                cel = row1.createCell(1);
                cel.setCellValue("DESIGNATION");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(4);
                cel.setCellValue("DAT_ACQUI");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(5);
                cel.setCellValue("BUREAU");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(6);
                cel.setCellValue("OBS");
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(2);
                cel.setCellStyle(cellStyleCenter1);
                cel = row1.createCell(3);
                cel.setCellStyle(cellStyleCenter1);
                CellRangeAddress r = new CellRangeAddress(5, 5, 1, 3);
                sheet.addMergedRegion(r);

                RegionUtil.setBorderBottom(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderLeft(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderRight(BorderStyle.THIN, r, sheet);
                RegionUtil.setBorderTop(BorderStyle.THIN, r, sheet);
                //iteraing r number of rows
                int i = 6;

                for (Immob ins : Immob.getImmobsEcarts()) {
                    XSSFRow row = sheet.createRow(i);

                    XSSFCell cell = row.createCell(0);
                    cell.setCellValue(ins.getCode());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(1);
                    cell.setCellValue(ins.getLibelle());
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(4);
                    cell.setCellValue(ins.getDate_acq().substring(8) + "/" + ins.getDate_acq().substring(5, 7) + "/" + ins.getDate_acq().substring(0, 4));
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(5);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyleCenter);
                    cell = row.createCell(6);
                    cell.setCellValue("");
                    cell.setCellStyle(cellStyleCenter);

                    cell = row.createCell(2);
                    cell.setCellStyle(cellStyleCenter1);
                    cell = row.createCell(3);
                    cell.setCellStyle(cellStyleCenter1);
                    row1.getCell(1).setCellStyle(cellStyleCenter);
                    CellRangeAddress r1 = new CellRangeAddress(i, i, 1, 3);
                    sheet.addMergedRegion(r1);
                    row.getCell(1).setCellStyle(cellStyleCenter);
                    if (i > 6) {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THIN, r1, sheet);
                    } else {
                        RegionUtil.setBorderBottom(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderLeft(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderRight(BorderStyle.THIN, r1, sheet);
                        RegionUtil.setBorderTop(BorderStyle.THICK, r1, sheet);
                    }
                    i++;

                }
                RegionUtil.setBorderTop(BorderStyle.THICK, r, sheet);
                 
                row1.createCell(0);
                row1.getCell(0).setCellValue("CODE");
                row1.getCell(0).setCellStyle(cellStyleCenter1);

                sheet.setColumnWidth(1, 5600);

                sheet.setColumnWidth(0, 2700);

                sheet.setColumnWidth(4, 3000);

                sheet.setColumnWidth(6, 3400);
                FileOutputStream fileOut = new FileOutputStream("app.xlsx");
                XSSFPrintSetup printSetup = (XSSFPrintSetup) sheet.getPrintSetup();
                printSetup.setHeaderMargin(0.5D);
                printSetup.setFooterMargin(0.5D);
                //write this workbook to an Outputstream.
                wb.write(fileOut);
                fileOut.flush();
                fileOut.close();
                Desktop.getDesktop().print(new File("app.xlsx"));
                // }

                Alert a2 = new Alert(Alert.AlertType.INFORMATION);
                a2.setTitle("Information");
                a2.setContentText("Operation terminé sans erreurs");
                a2.setHeaderText(null);
                a2.showAndWait();
            } else {
                Alert a2 = new Alert(Alert.AlertType.ERROR);
                a2.setTitle("Erreur");
                a2.setContentText("tableau vide");
                a2.setHeaderText(null);
                a2.showAndWait();
            }
        } catch (SQLException | IOException | ClassNotFoundException ex) {
            Alert a2 = new Alert(Alert.AlertType.ERROR);
            a2.setTitle("Erreur");
            a2.setContentText(ex.toString());
            a2.setHeaderText(null);
            a2.showAndWait();

        }
    }
}
//Environment.GetFolderPath(Environment.SpecialFolder.DesktopDirectory)