/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author Percy
 */
// comentariosdad sa
public class Cliente {
    private int idCliente;
    private String nombres;
    private String apellidos;
    private Date fechaNac;
    private String direccion;
    private String telefono;
    private int genero;
    private String DNI;
    public Cliente(){
        
    }

    public Cliente(int idCliente, String nombres, String apellidos, Date fechaNac, String direccion, String telefono, int genero, String DNI) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNac = fechaNac;
        this.direccion = direccion;
        this.telefono = telefono;
        this.genero = genero;
        this.DNI = DNI;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }
    

    public int getIdCliente() {
        return idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {  
        return apellidos;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public int getGenero() {
        return genero;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    
    public String statementAgregar(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        String strDate = dateFormat.format(fechaNac); 
        return idCliente +"," + "\"" + nombres +"\"" + "," + "\"" + apellidos +"\"" + "," + "\'"+strDate +"\'"+","
                + "\"" + direccion +"\"" + "," + "\"" + telefono +"\"" + "," +genero + "," + DNI ;
    }
}
