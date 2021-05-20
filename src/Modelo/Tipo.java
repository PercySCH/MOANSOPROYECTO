/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Percy
 */
public class Tipo {
    private int idTipoHabitacion;
    private String nombre;
    private String caracteristica;
    private String comentario;

    public Tipo(int codigoTipo, String nombre, String caracteristica, String comentario) {
        this.idTipoHabitacion = codigoTipo;
        this.nombre = nombre;
        this.caracteristica = caracteristica;
        this.comentario = comentario;
    }

    public int getCodigoTipo() {
        return idTipoHabitacion;
    }

    public void setCodigoTipo(int codigoTipo) {
        this.idTipoHabitacion = codigoTipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
    
    
}
