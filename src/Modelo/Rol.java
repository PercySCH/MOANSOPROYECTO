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
public class Rol {
    private int rol;
    private String nombre_rol;

    public Rol() {
    }

    public Rol(int rol, String nombre_rol) {
        this.rol = rol;
        this.nombre_rol = nombre_rol;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public String getNombre_rol() {
        return nombre_rol;
    }

    public void setNombre_rol(String nombre_rol) {
        this.nombre_rol = nombre_rol;
    }
    
}
