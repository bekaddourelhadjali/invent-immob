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
import java.util.ArrayList;
import java.util.Date;
import tools.connectDb;

/**
 *
 * @author bekal
 */
public class Historique_m {
      static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    private String code, libelle, compte_act;

    private String actif;
    private String date_acq;
    private String code_adm; 

     
    
    private String quantite,remarque, local;

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public Historique_m(String code, String libelle, String compte_act, String actif, String date_acq, String code_adm, String quantite, String remarque, String local, Date date) {
        this.code = code;
        this.libelle = libelle;
        this.compte_act = compte_act;
        this.actif = actif;
        this.date_acq = date_acq;
        this.code_adm = code_adm;
        this.quantite = quantite;
        this.remarque = remarque;
        this.local = local;
        this.date = date;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getQuantite() {
        return quantite;
    }

    public String getRemarque() {
        return remarque;
    }

    public String getLocal() {
        return local;
    }
    private Date date;
    
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

    public void setCode_adm(String code_adm) {
        this.code_adm = code_adm;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getCode_adm() {
        return code_adm;
    }

    public Date getDate() {
        return date;
    }
    public static ArrayList<Historique_m> getHistorique_m(String year) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Historique_m> b = new ArrayList<Historique_m>();
        con = connectDb.connecterDB();
        String q = "select * from Historique_mat where year(date)='"+year+"' order by code desc";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Historique_m(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"),rs.getString("date_achat"), rs.getString("code_adm") ,rs.getString("quantite"),rs.getString("observation"),rs.getString("localisation"), rs.getDate("date")));
           
        }

        return b;
    }
    public static ArrayList<Historique_m> getsearchHistorique_ms(String search,String year) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Historique_m> b = new ArrayList<Historique_m>();
        con = connectDb.connecterDB();

        String q = " select * from Historique_mat where  (code like '%" + search + "%' or libelle  like '%" + search + "%' or compt  like '%" + search + "%' or date_achat  like '%" + search + "%' or actif  like '%" + search+ "%' or code_adm  like '%" + search + "%' or quantite  like '%" + search + "%' or localisation  like '%" + search+ "%' or observation  like '%" + search+ "%') and year(date)='"+year+"' order by code desc ";
         
        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Historique_m(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"), rs.getString("date_achat"),rs.getString("code_adm") ,rs.getInt("quantite")+"",rs.getString("observation"), rs.getString("localisation"),rs.getDate("date") ));
        }

        return b;
    }
    public static ArrayList<String> getYears() throws ClassNotFoundException, SQLException, IOException{
        ArrayList<String> b = new ArrayList<String>();
        con = connectDb.connecterDB();

        String q = "  select distinct year(date) as years from Historique_mat order by years desc";
        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(rs.getString("years"));
        }

        return b;
    }
    
    
}
