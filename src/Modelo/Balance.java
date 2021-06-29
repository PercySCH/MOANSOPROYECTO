/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Percy
 */
public class Balance {
    private int idBalance;
    private int nroConfirmacion;
    
    CRUD consulta= CRUD.getInstance();
    
    Balance(){
        
    }

    public Balance(int idBalance, int nroConfirmacion) {
        this.idBalance = idBalance;
        this.nroConfirmacion = nroConfirmacion;
    }
    public void createBalance(){
        consulta.insert("insert into Balance values(" 
                +idBalance +","
                + nroConfirmacion+")"
                );
    }
    public void leerBalance(int idBalance){
        try {
            ResultSet rs = consulta.select("select * from Balance where idBalance="+ this.idBalance);
            rs.next();
            this.idBalance=rs.getInt(1);
            this.nroConfirmacion=rs.getInt(2);
        } catch (SQLException ex) {
            Logger.getLogger(Balance.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public int getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(int idBalance) {
        this.idBalance = idBalance;
    }

    public int getNroConfirmacion() {
        return nroConfirmacion;
    }

    public void setNroConfirmacion(int nroConfirmacion) {
        this.nroConfirmacion = nroConfirmacion;
    }

    public CRUD getConsulta() {
        return consulta;
    }

    public void setConsulta(CRUD consulta) {
        this.consulta = consulta;
    }
    
}
