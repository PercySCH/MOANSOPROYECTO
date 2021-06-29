/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.sql.ResultSet;
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
    
    public CRUD consulta= CRUD.getInstance();
    
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
    public void createCliente(){
        consulta.insert("INSERT INTO CLIENTE VALUES ("+statementAgregar()+")");                           
    }
    public void UpdateCliente(){
        consulta.update("UPDATE CLIENTE SET " +
                "CLIENTE.nombres = " + "'"+getNombres()+"' ," +
                "CLIENTE.apellidos = " + "'"+getApellidos()+"' ," + 
                "CLIENTE.fechaNac = " + "'"+Convertidor.DateToStrMySql(fechaNac)+"' ," + 
                "CLIENTE.direccion = " + "'"+getDireccion()+"' ," + 
                "CLIENTE.telefono = " + "'"+getTelefono()+"' ," + 
                "CLIENTE.dni = " + "'"+getDNI()+"'" +   
                        " WHERE idCliente="+getIdCliente()
                );
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

    public void leerCliente(int codigoCliente) {
        try{
        ResultSet rs=consulta.select("select * from cliente where idCliente = "+codigoCliente);
        rs.next();
        this.idCliente = rs.getInt(1);
        this.nombres = rs.getString(2);
        this.apellidos = rs.getString(3);
        this.fechaNac = (new java.util.Date(rs.getDate(4).getTime()));
        this.direccion = rs.getString(5);
        this.telefono = rs.getString(6);
        this.genero = rs.getInt(7);
        this.DNI = rs.getString(8);
        }catch(Exception ex){
            System.out.println("Ay noo, algo salio mal insertememedoge*");
        }
    }
}
