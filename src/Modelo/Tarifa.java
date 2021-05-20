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
public class Tarifa {
    private int idTarifa;
    private int nroConfirmacion;
    private Date fecha;
    private float precio;

    public Tarifa(int idTarifa, int nroConfirmacion, Date fecha, float precio) {
        this.idTarifa = idTarifa;
        this.nroConfirmacion = nroConfirmacion;
        this.fecha = fecha;
        this.precio = precio;
    }
    
    public int getIdTarifa() {
        return idTarifa;
    }

    public void setIdTarifa(int idTarifa) {
        this.idTarifa = idTarifa;
    }

    public int getNroConfirmacion() {
        return nroConfirmacion;
    }

    public void setNroConfirmacion(int nroConfirmacion) {
        this.nroConfirmacion = nroConfirmacion;
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
