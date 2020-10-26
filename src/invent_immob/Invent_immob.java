/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package invent_immob;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author bekal
 */
public class Invent_immob extends Application {
    Stage stag=new Stage();
    @Override
    public void start(Stage stage) throws Exception {
        
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        
        Scene scene = new Scene(root);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
 
        stage.setScene(scene);
        stage.show();
        
    }
    public static void main(String[] args) throws FileNotFoundException, IOException { 
         launch(args);
        
        
    }
      public void start(String a) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(a));
        Scene scene =new Scene(root);
     
        stag.setScene(scene);
        stag.setResizable(false);    
        stag.show();
        
       
       }
      public void hide(String a) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(a));
        Scene scene =new Scene(root);
     
        stag.setScene(scene);
        stag.setResizable(false);
        stag.hide();
       
     
     }
}
