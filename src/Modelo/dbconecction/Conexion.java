package Modelo.dbconecction;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class Conexion {

    Connection conexion;
   
    public Conexion() {
    }
    
    public Connection getConexion() {
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            String root= "root";
            String pass= "admin";
            String url="jdbc:mysql://localhost:3306/hoteleriadb";
            
            this.conexion=DriverManager.getConnection(url,root,pass);
            
           // JOptionPane.showMessageDialog(null, "Conexión Exitosa\n" );
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Conexión fallida\n" + ex);
        }
        return conexion;
    }
   
}
