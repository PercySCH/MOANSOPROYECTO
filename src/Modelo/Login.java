/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import Modelo.dbconecction.Conexion;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class Login {

    private String usuario;
    private String contrasenia;

    public Login(String usuario, String contrasenia) {
        this.usuario = usuario;
        this.contrasenia = contrasenia;
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
    public Recepcionista IniciarSesion(){
        // preguntar a la base de datos si ese usuario existe, sino nos botara un 0 
        Recepcionista activo=new Recepcionista();
        CRUD consulta = CRUD.getInstance();
        try{
        ResultSet rs= consulta.select("select * from recepcionista where usuario=\'"+ usuario + "\' and contrasenia=\'"+ contrasenia + "\'");
           
            if(rs.next()){
            activo.setIdRecepcionista(rs.getInt(1));
            System.out.println(activo.getIdRecepcionista());
            activo.setNombre(rs.getString(2));
            System.out.println(activo.getNombre());
            activo.setApellido(rs.getString(3));
            System.out.println(activo.getApellido());
            }
            else{
                JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrecta.");
                activo.setIdRecepcionista(0);
                return activo;
            }
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, "Usuario y/o contraseña incorrecta." + ex);
            activo.setIdRecepcionista(0);
            return activo;
        }
        return activo;
    }
    
    
    
   
}
