/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import tools.connectDb;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.apache.poi.ss.usermodel.BorderStyle;
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
 *
 * @author bekal
 */
public class User {

    /*
     * To change this license header, choose License Headers in Project Properties.
     * To change this template file, choose Tools | Templates
     * and open the template in the editor.
     */
    

    public void setStatus(String status) {
        this.status = status;
    }

    static Connection con = null;
 
    static Statement stmt = null;
    static ResultSet rs = null;
    public String password, username, type;
    public int id;
    public  String status;
    
    public User() {
        this.password = this.username = this.type =this.status= "";
        this.id = 0;
        
        
    }

    
    

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public int getId() {
        return id;
    }
    
    public User(String a, String s, String n, int i) {
        this.username = a;
        this.password = s;
        this.type = n;
        this.id = i;
        this.status="";
         
    }
     public User(String a, String s, String n) {
        this.username = a;
        this.password = s;
        this.type = n;
        this.status="";
       
      
    }

    public  void addUser() throws SQLException, ClassNotFoundException, IOException {

        con = connectDb.connecterDB();
        
        String q = "insert into users(Username,Password,Type) Values ('" + this.username + "','" + MD5(this.password) + "','" + this.type + "')";

        stmt = con.createStatement();
        stmt.executeUpdate(q);

    }

    public static User getUser(int i) throws SQLException, ClassNotFoundException, IOException {

        con = connectDb.connecterDB();
        User a = new User();
        String q = "select * from Users where ID =" + i;
        stmt = con.createStatement();
        rs = stmt.executeQuery(q);
        while (rs.next()) {
            a = new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Type"), rs.getInt("ID"));
        }
        return a;
    }
    

  
    public static boolean test( String t) throws SQLException, ClassNotFoundException, IOException {

        con = connectDb.connecterDB();
        boolean v = false;
        String q = "select * from Users where username ='" + t + "'";
        stmt = con.createStatement();
        rs = stmt.executeQuery(q);
        while (rs.next()) {
            v = true;
        }
        return v;
    }

    public static ArrayList<User> getUsers(String type) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<User> b = new ArrayList<User>();
        con = connectDb.connecterDB();
        String q = "select * from Users where type ='" + type + "'";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Type"), rs.getInt("ID")));
        }

        return b;
    }
    public static ArrayList<User> getAllUsers() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<User> b = new ArrayList();
        con = connectDb.connecterDB();
        String q = "select * from Users ";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Type"), rs.getInt("ID")));
        }

        return b;
    }

    public  void deleteUser() throws SQLException, ClassNotFoundException, IOException {
        con = connectDb.connecterDB();
        String q = "delete from Users where ID=" + this.id ;

        stmt = con.createStatement();
        stmt.executeUpdate(q);
    }
     public  void editUser() throws SQLException, ClassNotFoundException, IOException {
        con = connectDb.connecterDB();
        String q = "update  Users set username='"+this.username+"' , password='"+this.password+"' , type='"+this.type+"' where ID=" + this.id ;
        stmt = con.createStatement();
        stmt.executeUpdate(q);
    }
     public static ArrayList<User> searchUsers(String search,String type) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<User> b = new ArrayList<User>();
        con = connectDb.connecterDB();
         
        String q = " select * from users where username like '%"+search+"%'";
        if (type.equals("Admin")){
            q+=" and type='user'";
        }else if(type.equals("superAdmin")){
           q+="and type!='superAdmin'";
        }
        stmt = con.createStatement();
            
        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new User(rs.getString("Username"), rs.getString("Password"), rs.getString("Type"), rs.getInt("ID")));
        }

        return b;
    }
     public User log() throws ClassNotFoundException, SQLException, IOException {
        User x = new User();
        con = connectDb.connecterDB();
        stmt = con.createStatement();
        x.status="username";
        String s = "select * from users where username='" + this.username + "'";
        
        rs = stmt.executeQuery(s);
        while(rs.next()){
        if(!rs.getString("password").equals(MD5(this.password))){
            
            x.status="password";
             
        }else{
            x.id=rs.getInt("id");
            x.username= this.username;
            x.password=MD5(this.password);
            x.type=rs.getString("type");
            x.status="suc";
        }
        }
        
        return x;
    }
    public static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    } 
            }
