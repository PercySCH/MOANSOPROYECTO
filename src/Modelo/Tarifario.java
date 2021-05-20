/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.util.Date;

/**
 *
 * @author Percy
 */
public class Tarifario {
 
    private int idTipoHabitacion;
    private Date fecha;
    private float precio;

    public Tarifario(int idTipoHabitacion, Date fecha, float precio) {
        this.idTipoHabitacion = idTipoHabitacion;
        this.fecha = fecha;
        this.precio = precio;
    }

    

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    
}
