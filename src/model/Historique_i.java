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
import java.util.logging.Logger;
import static model.Historique_m.con;
import tools.connectDb;

/**
 *
 * @author bekal
 */
public class Historique_i {
    
    private String code, libelle, compte_act;

    private String actif;
    private String date_acq;
    private String code_adm; 
    
    private Date date;
     
    private String quantite,remarque, local;

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
     
      static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public String getQuantite() {
        return quantite;
    }

    public String getRemarque() {
        return remarque;
    }

    public String getLocal() {
        return local;
    }

    public void setQuantite(String quantite) {
        this.quantite = quantite;
    }

    public void setRemarque(String remarque) {
        this.remarque = remarque;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public Historique_i(String code, String libelle, String compte_act, String actif, String date_acq, String code_adm, Date date, String quantite, String remarque, String local) {
        this.code = code;
        this.libelle = libelle;
        this.compte_act = compte_act;
        this.actif = actif;
        this.date_acq = date_acq;
        this.code_adm = code_adm;
        this.date = date;
        this.quantite = quantite;
        this.remarque = remarque;
        this.local = local;
    }
     
    public static ArrayList<Historique_i> getHistorique_ins(String year) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Historique_i> b = new ArrayList<Historique_i>();
        con = connectDb.connecterDB();
        String q = "select * from Historique_ins where year(date)='"+year+"' order by code desc";

        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Historique_i(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"),rs.getString("date_achat"), rs.getString("code_adm") , rs.getDate("date"),rs.getString("quantite"),rs.getString("observation"),rs.getString("localisation")));
           
        }

        return b;
    }
    public static ArrayList<Historique_i> getsearchHistorique_is(String search,String year) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Historique_i> b = new ArrayList<Historique_i>();
        con = connectDb.connecterDB();

        String q = " select * from Historique_ins where (code like '%" + search + "%' or libelle  like '%" + search + "%' or compt  like '%" + search + "%' or date_achat  like '%" + search + "%' or actif  like '%" + search+ "%' or code_adm  like '%" + search + "%' or quantite  like '%" + search + "%' or localisation  like '%" + search+ "%' or observation  like '%" + search+ "%') and year(date)='"+year+"' order by code desc ";

        stmt = con.createStatement();
 
        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(new Historique_i(rs.getString("Code"), rs.getString("Libelle"), rs.getString("compt"), rs.getString("actif"), rs.getString("date_achat"),rs.getString("code_adm") ,rs.getDate("date"),rs.getInt("quantite")+"",rs.getString("observation"), rs.getString("localisation") ));
        }

        return b;
    }
    public static ArrayList<String> getYears() throws ClassNotFoundException, SQLException, IOException{
        ArrayList<String> b = new ArrayList<String>();
        con = connectDb.connecterDB();

        String q = "   select distinct year(date) as years from Historique_ins order by years desc";
        stmt = con.createStatement();

        rs = stmt.executeQuery(q);
        while (rs.next()) {
            b.add(rs.getString("years"));
        }

        return b;
    }
     
}
