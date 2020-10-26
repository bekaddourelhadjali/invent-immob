/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.*;

public class connectDb {

    public static Connection con;

    public static Connection connecterDB() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {

        InetAddress inetAddress = InetAddress.getLocalHost();
        Class.forName("com.mysql.jdbc.Driver");
        
                
        con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/inv_immob", "root_inv1", "");
//Properties props = new Properties();
//        props.load(new FileInputStream("db.properties"));
//        String adresse =props.getProperty("db.adresse");
//        String port =props.getProperty("db.port");
//        String user =props.getProperty("db.user");
//        String password =props.getProperty("db.password");
//        String db =props.getProperty("db.db");
//
//        Class.forName("com.mysql.jdbc.Driver");
//         
//        ResultSet rs = null;
//         
//        Connection con = DriverManager.getConnection("jdbc:mysql://"+adresse.trim()+":"+port.trim()+"/"+db.trim(),user.trim()
//                ,password.trim());

        return con;
    }

//    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException, SQLException {
//        Properties props = new Properties();
//        props.load(new FileInputStream("db.properties"));
//        String adresse =props.getProperty("db.adresse");
//        String port =props.getProperty("db.port");
//        String user =props.getProperty("db.user");
//        String password =props.getProperty("db.password");
//        String db =props.getProperty("db.db");
//
//        Class.forName("com.mysql.jdbc.Driver");
//         
//        ResultSet rs = null;
//         
//        Connection con = DriverManager.getConnection("jdbc:mysql://"+adresse.trim()+":"+port.trim()+"/"+db.trim(),user.trim()
//                ,password.trim());
//        PreparedStatement stm = con.prepareStatement("select * from users");
//        rs = stm.executeQuery();
//        while (rs.next()) {
//            System.out.println(rs.getString("username"));
//        }
//        
//    }
}
