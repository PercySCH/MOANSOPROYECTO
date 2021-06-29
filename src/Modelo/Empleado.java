/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Percy
 */
public class Empleado {
    private int idRecepcionista;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private String telefono;
    private int genero;
    private String direccion;
    private String usuario;
    private String contrasenia;
    private int idRol;
    
    private CRUD consulta=CRUD.getInstance();
    
    public Empleado() {
    }

    public Empleado(int idRecepcionista, String nombre, String apellido, Date fechaNac,int genero, String telefono, String direccion, String usuario, String contrasenia, int idRol) {
        this.idRecepcionista = idRecepcionista;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.genero=genero;
        this.telefono = telefono;
        this.direccion = direccion;
        this.usuario = usuario;
        this.contrasenia = contrasenia;
        this.idRol = idRol;
    }
    public void leerEmpleado(int idRecepcionista){
        try {
            ResultSet rs = consulta.select("select * from Empleado where idEmpleado=" + idRecepcionista);
            rs.next();
            this.idRecepcionista = rs.getInt(1);
            this.nombre =rs.getString(2);
            this.apellido = rs.getString(3);
            this.fechaNac = new Date(rs.getDate(4).getTime());
            this.genero= rs.getInt(5);
            this.telefono = rs.getString(6);
            this.direccion = rs.getString(7);
            this.usuario = rs.getString(8);
            this.contrasenia = rs.getString(9);
            this.idRol = rs.getInt(10);
        } catch (SQLException ex) {
           
        }
    }
    public void createEmpleado(){
        consulta.insert("INSERT INTO empleado VALUES ("+getStatement()+")");
    }
    public void updateEmpleado(){
        
    }
    public HistorialCaja getHistorialCajaEnUso(){
        HistorialCaja HistorialcCajaEnUso=new HistorialCaja();
        ResultSet rs= consulta.select("Select * from HistorialCaja where fechaHoraCierre is null and empleado_idEmpleado="+ getIdRecepcionista());
                try{
                if(rs.next()){
                    int idHistorialCaja=rs.getInt(1);
                    HistorialcCajaEnUso=new HistorialCaja();
                    HistorialcCajaEnUso.leerHistorialCaja(idHistorialCaja);
                }
                }catch(Exception ex){
                    HistorialcCajaEnUso=new HistorialCaja();
                }
        return HistorialcCajaEnUso;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }
      
    
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getIdRecepcionista() {
        return idRecepcionista;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setIdRecepcionista(int idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    
    public String getStatement() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
        String strDate = dateFormat.format(fechaNac); 
        return idRecepcionista + ",\"" + nombre + "\",\"" + apellido + "\",\"" + strDate + "\",\"" + genero + "\",\"" + telefono + "\",\"" + direccion + "\",\"" + usuario + "\",\""
                + contrasenia + "\"," + idRol;
    }

    public boolean isCashierOpen() {
        boolean isOpen = false;
        ResultSet rs= consulta.select("Select * from HistorialCaja where fechaHoraCierre is null and empleado_idEmpleado="+ idRecepcionista);
        try{
        if(rs.next()) {
            isOpen=true;
        }
        }catch(Exception ex){
            isOpen=false;
        }
        return isOpen;
    }
    public void OpenCashier(int idCaja,float montoinicial){
        int idHistorialCaja; 
        ResultSet rs;
        try{
            rs = consulta.select("SELECT MAX(idHistorialCaja) FROM HistorialCaja");
            rs.next();
            idHistorialCaja= rs.getInt(1) + 1;
        }catch(Exception ex){
            idHistorialCaja=1;
        }
        //Crear un nuevo Historial 
        HistorialCaja cajaEmpleado = new HistorialCaja(idHistorialCaja,idCaja,getIdRecepcionista(), new Date(),montoinicial);
        cajaEmpleado.crearHistorialCaja();
    }
    public void DoMovement(float monto){
        HistorialCaja cajaEmpleado = getHistorialCajaEnUso();
        cajaEmpleado.realizarMovimiento(monto);
        cajaEmpleado.updateHistorialCaja();
    }
    public void CloseCashier(){
        HistorialCaja cajaEmpleado= getHistorialCajaEnUso();
        cajaEmpleado.cerrarHistorialCaja();
        cajaEmpleado.updateHistorialCaja();
    }
    
    
}
