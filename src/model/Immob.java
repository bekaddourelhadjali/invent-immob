/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javafx.scene.control.TextField;
import tools.connectDb;

public class Immob {

    public Immob(String code, String libelle, String compte_act, String actif, String date_acq, String code_adm, int user_id, Date date, TextField quantite, TextField remarque, TextField local) {
        this.code = code;
        this.libelle = libelle;
        this.compte_act = compte_act;
        this.actif = actif;
        this.date_acq = date_acq;
        this.code_adm = code_adm;
        this.user_id = user_id;
        this.date = date;
        this.quantite = quantite;
        this.remarque = remarque;
        this.local = local;
    }

    public Immob(int id, String code, String libelle, String compte_act, String actif, String date_acq, String code_adm, int user_id, Date date, TextField quantite, TextField remarque, TextField local) {
        this.id = id;
        this.code = code;
        this.libelle = libelle;
        this.compte_act = compte_act;
        this.actif = actif;
        this.date_acq = date_acq;
        this.code_adm = code_adm;
        this.user_id = user_id;
        this.date = date;
        this.quantite = quantite;
        this.remarque = remarque;
        this.local = local;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUser_id() {
        return user_id;
    }

    public Date getDate() {
        return date;
    }

    public static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void clear() throws ClassNotFoundException, SQLException, IOException {
        con = connectDb.connecterDB();

        String q = "insert into Historique_mat(Code,libelle,compt,date_achat,code_adm,actif,quantite,observation,localisation,date)   select Code,libelle,compt,date_achat,code_adm,actif,quantite,observation,localisation,date from materiels  ";

        stmt = con.createStatement();
        stmt.executeUpdate(q);
          q = "delete  from materiels "  ;

        stmt = con.createStatement();
        stmt.executeUpdate(q);
    }

    private int id;
    private String code, libelle, compte_act;

    private String actif;
    private String date_acq;
    private String code_adm;
    private int user_id;
    private Date date;

     

    public void setCode_adm(String code_adm) {
        this.code_adm = code_adm;
    }

    public String getCode_adm() {
        return code_adm;
    }
    private TextField quantite,remarque, local;

    public void setRemarque(TextField remarque) {
        this.remarque = remarque;
    }

    public void setLocal(TextField local) {
        this.local = local;
    }

    public void setQuantite(TextField quantite) {
        this.quantite.setText( quantite.getText());
    }

    public TextField getRemarque() {
        return remarque;
    }

    public TextField getLocal() {
        return local;
    }

    public TextField getQuantite() {
        return quantite;
    }

     

     

     

     

    public void setCode(String code) {
        this.code = code;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public void setCompte_act(String compte_act) {
        this.compte_act = compte_act;
    }

    public void setActif(String actif) {
        this.actif = actif;
    }

    public void setDate_acq(String date_acq) {
        this.date_acq = date_acq;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getCompte_act() {
        return compte_act;
    }

    public String getActif() {
        return actif;
    }

    public String getDate_acq() {
        return date_acq;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

     

    public void addImmobs(String sql) throws SQLException, ClassNotFoundException, IOException {

        con = connectDb.connecterDB();
        stmt = con.createStatement();
        stmt.executeUpdate(sql);

    }

    public static Immob getImmob(String code) throws SQLException, ClassNotFoundException, IOException {

        con = connectDb.connecterDB();
        Immob a = new Immob();
        String q = "select * from materiels where Code =" + code;
        stmt = con.createStatement();
        rs = stmt.executeQuery(q);
        while (rs.next()) {
            a = new Immob(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"), rs.getString("date_achat"),rs.getString("code_adm"),rs.getInt("user_id"),rs.getDate("date"),new TextField(rs.getInt("quantite")+""),new TextField(rs.getString("observation")),new TextField(rs.getString("localisation")));
        }
        return a;
    }

    public Immob() {
        this.quantite=new TextField();
         this.local=new TextField();
         this.remarque=new TextField();
    }
    
    public static ArrayList<Immob> getImmobs() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Immob> b = new ArrayList<Immob>();
        con = connectDb.connecterDB();
        String q = "select * from materiels order by code desc";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Immob(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"), rs.getString("date_achat"),rs.getString("code_adm"),rs.getInt("user_id"),rs.getDate("date"),new TextField(rs.getInt("quantite")+""),new TextField(rs.getString("observation")),new TextField(rs.getString("localisation"))));
        }

        return b;
    }

    public void editImmobQuantite() throws SQLException, ClassNotFoundException, IOException {
        con = connectDb.connecterDB();
        
            SimpleDateFormat  datef3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String q = "update  materiels set  quantite= " + this.quantite.getText() + "   , date='" + datef3.format(new Date())+ "' , user_id=" + this.getUser_id() + " where code='" + this.code + "'";
        stmt = con.createStatement();
        stmt.executeUpdate(q);
    }
    public void editImmobLocal() throws SQLException, ClassNotFoundException, IOException {
        con = connectDb.connecterDB();
        
            SimpleDateFormat  datef3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String q = "update  materiels set    localisation='" + this.local.getText() + "'   , date='" + datef3.format(new Date())+ "' , user_id=" + this.getUser_id() + " where code='" + this.code + "'";
        stmt = con.createStatement();
        stmt.executeUpdate(q);
    }
    public void editImmobObs() throws SQLException, ClassNotFoundException, IOException {
        con = connectDb.connecterDB();
        
            SimpleDateFormat  datef3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String q = "update  materiels set    observation='" + this.remarque.getText() + "' , date='" + datef3.format(new Date())+ "' , user_id=" + this.getUser_id() + " where code='" + this.code + "'";
        stmt = con.createStatement();
        stmt.executeUpdate(q);
    }
    public void deleteImmob() throws SQLException, ClassNotFoundException, IOException {
        con = connectDb.connecterDB();
        String q = "delete from materiels where Code=" + this.code;

        stmt = con.createStatement();
        stmt.executeUpdate(q);
    }

    public static ArrayList<Immob> getsearchImmobs(String search) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Immob> b = new ArrayList<Immob>();
        con = connectDb.connecterDB();

        String q = " select * from materiels where code like '%" + search + "%' or libelle  like '%" + search + "%' or compt  like '%" + search + "%' or date_achat  like '%" + search + "%' or actif  like '%" + search+ "%' or code_adm  like '%" + search + "%' or quantite  like '%" + search + "%' or localisation  like '%" + search+ "%' or observation  like '%" + search+ "%' order by code desc ";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Immob(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"), rs.getString("date_achat"),rs.getString("code_adm"),rs.getInt("user_id"),rs.getDate("date"),new TextField(rs.getInt("quantite")+""),new TextField(rs.getString("observation")),new TextField(rs.getString("localisation"))));
        }

        return b;
    }
    public static ArrayList<Immob> getLocalmmobs(String search) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Immob> b = new ArrayList<Immob>();
        con = connectDb.connecterDB();

        String q = " select * from materiels where  localisation  = '" + search+ "' order by code desc ";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Immob(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"), rs.getString("date_achat"),rs.getString("code_adm"),rs.getInt("user_id"),rs.getDate("date"),new TextField(rs.getInt("quantite")+""),new TextField(rs.getString("observation")),new TextField(rs.getString("localisation"))));
        }

        return b;
    }
    public static ArrayList<String> getLocals() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<String> b = new ArrayList<String>();
        con = connectDb.connecterDB();

        String q = " select distinct localisation from materiels where localisation is not null";
        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(rs.getString("localisation"));
        }

        return b;
    }
    public static ArrayList<Immob> getImmobsEcarts( ) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Immob> b = new ArrayList<Immob>();
        con = connectDb.connecterDB();

        String q = " select * from materiels where  quantite=0 or quantite is null  order by code desc ";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Immob(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"), rs.getString("date_achat"),rs.getString("code_adm"),rs.getInt("user_id"),rs.getDate("date"),new TextField(rs.getInt("quantite")+""),new TextField(rs.getString("observation")),new TextField(rs.getString("localisation"))));
        }

        return b;
    }
}
