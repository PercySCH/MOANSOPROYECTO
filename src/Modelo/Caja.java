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
public class Caja {
    private int idCaja;
    private String ubicacion;
    
    CRUD consulta =  CRUD.getInstance();
    public Caja(){
        
    }
    
    public Caja(int idCaja, String ubicacion) {
        this.idCaja = idCaja;
        this.ubicacion = ubicacion;
    }
    public void leerCaja(int idCaja){
        try {
            ResultSet rs=consulta.select("select * from Caja where idCaja="+ idCaja);
            rs.next();
            this.idCaja = rs.getInt(1);
            this.ubicacion = rs.getString(2);
        } catch (SQLException ex) {
            Logger.getLogger(Caja.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
