/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Habitacion;
import Modelo.dbconecction.CRUD;
import Vista.pnlHabitaciones;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorHabitaciones {
    private pnlHabitaciones vistaHabitaciones;
    private ControladorAdministrarHabitaciones contrAdmHab;
    private DefaultTableModel DTM;
    private List<Habitacion> listaHabitaciones;
    private CRUD consulta= CRUD.getInstance();
    ControladorHabitaciones(pnlHabitaciones vistaHabitaciones,ControladorAdministrarHabitaciones contrAdmHab){
        this.vistaHabitaciones=vistaHabitaciones;
        this.contrAdmHab=contrAdmHab;
        poblarTabla();
        insertarEventos();
        
        vistaHabitaciones.setVisible(true);
    }
    void poblarTabla(){
        DTM = new DefaultTableModel();
	String[] headers = {"Nro", "Piso", "cercaEscalera", "cercaPiscina", "estado", "Tipo"};
	for (String i : headers) {
	    DTM.addColumn(i);
	}
        listaHabitaciones = new ArrayList<>();
	ResultSet rs = consulta.select("SELECT * FROM habitacion");
	try {
	    while (rs.next()) {
                Habitacion nuevaHabitacion = new Habitacion(rs.getInt(1), rs.getInt(2),rs.getBoolean(3), rs.getBoolean(4),rs.getInt(5), rs.getInt(6));
                listaHabitaciones.add(nuevaHabitacion);
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error:\n" + ex);
	}  
        for(int i=0;i<listaHabitaciones.size();i++){
            String[] datos= new String[6];
            datos[0] = listaHabitaciones.get(i).getIdHabitacion() + "";
            datos[1] = listaHabitaciones.get(i).getPiso() + "";
            
            if(listaHabitaciones.get(i).isCercaEscalera()){
                datos[2] = "Si";
            }else datos[2] = "No";
            if(listaHabitaciones.get(i).isCercaPisciona()){
                datos[3] = "Si";
            }else datos[3] = "No";
            
            if(listaHabitaciones.get(i).getEstado()==1){
                datos[4] = "LIBRE";
            }else datos[4]= "OCUPADO";
            
            rs=consulta.select("select nombre from tipo where idTipoHabitacion ="+listaHabitaciones.get(i).getIdTipoHabitacion());
            try{
                rs.next();
                 datos[5]= rs.getString(1);
            }catch(Exception ex){
            }
            DTM.addRow(datos);
           
        }
         vistaHabitaciones.tblHabitaciones.setModel(DTM);
    }
    void insertarEventos(){
        
    }
    
    
}
