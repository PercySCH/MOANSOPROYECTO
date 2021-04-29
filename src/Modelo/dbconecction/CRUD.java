package Modelo.dbconecction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class CRUD extends Conexion {
    
    private CRUD crud;
     static CRUD instance=null;
    Statement stmt = null;
    ResultSet rs = null;

    private CRUD() {
        getConexion();
    }
    public CRUD getCRUD() {
        return crud;  
    }
    
    public static CRUD getInstance(){
        if(CRUD.instance==null){
            CRUD.instance = new CRUD();
        }
        return CRUD.instance;
    }
    public boolean insert(String query) {
        
        try {
            return stmt.executeUpdate(query) > 0;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error en la inserción:\n" + ex);
        } 
	return false;
    }
    
    public ResultSet select(String query) {
       
        try {
            stmt = conexion.createStatement();
            rs = conexion.createStatement().executeQuery(query);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:\n" + ex);
        }
        return rs;
    }

    public boolean update(String query) {
	return this.insert(query);
    }

    public boolean delete(String query) {
	return this.insert(query);
    }
}
