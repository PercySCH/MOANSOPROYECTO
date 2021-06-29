/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.sql.ResultSet;

/**
 *
 * @author Percy
 */
public class Habitacion {
    private int idHabitacion;
    private int piso;
    private boolean cercaEscalera;
    private boolean cercaPisciona;
    private int estado;
    private int idTipoHabitacion;
    
    private CRUD consulta=CRUD.getInstance();

    public Habitacion(int idHabitacion, int piso, boolean cercaEscalera, boolean cercaPisciona, int estado, int idTipoHabitacion) {
        this.idHabitacion = idHabitacion;
        this.piso = piso;
        this.cercaEscalera = cercaEscalera;
        this.cercaPisciona = cercaPisciona;
        this.estado = estado;
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public Habitacion() {
        
    }
    public void createHabitacion(){
        consulta.insert("INSERT INTO habitacion VALUES ("+getStatemenet()+")");
                            
    }
    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public boolean isCercaEscalera() {
        return cercaEscalera;
    }

    public void setCercaEscalera(boolean cercaEscalera) {
        this.cercaEscalera = cercaEscalera;
    }

    public boolean isCercaPisciona() {
        return cercaPisciona;
    }

    public void setCercaPisciona(boolean cercaPisciona) {
        this.cercaPisciona = cercaPisciona;
    }

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    
    public String getStatemenet() {
        return idHabitacion + "," + piso + "," + cercaEscalera + "," + cercaPisciona + "," + estado + "," + idTipoHabitacion ;
    }

    public void leerHabitacion(int idHabitacion) {
        try{
        ResultSet rs= consulta.select("select * from habitacion where nrohabitacion="+idHabitacion);
        rs.next();
        this.idHabitacion = rs.getInt(1);
        this.piso = rs.getInt(2);
        this.cercaEscalera = rs.getBoolean(3);
        this.cercaPisciona = rs.getBoolean(4);
        this.estado = rs.getInt(5);
        this.idTipoHabitacion = rs.getInt(6);
        }catch(Exception ex){
            System.out.println("Ay no, otra vez. Sorry");
        }   
        
    }
    public boolean isoccupied(){
        if(this.estado==2){
            return true;
        }
        else return false;
    }
    
    
    
}
