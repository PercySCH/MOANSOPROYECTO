/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Reservacion;
import Modelo.dbconecction.CRUD;
import Vista.FrmReservaciones;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorReservaciones {
    
    List<Reservacion> listaReservaciones; 
    CRUD consulta= CRUD.getInstance();
    public FrmReservaciones vistaReservaciones;
    public ControladorReservaciones(FrmReservaciones vistaReservaciones) {
        this.vistaReservaciones=vistaReservaciones;
        popularTabla();
        vistaReservaciones.setVisible(true);
    }
    public void popularTabla(){
        DefaultTableModel DTM = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
		return false;
	    }
	};
	String[] headers = {"NroConfirmacion", "fechaLlegada", "fechaSalida", "tipoPago", "estado","Habitacion", "cantPersonas", "idRecepcionista", "Cliente"};
	for (String i : headers) {
	    DTM.addColumn(i);
	}
        listaReservaciones = new ArrayList<>();
	ResultSet rs = consulta.select("SELECT * FROM reservacion");
	try {
	    while (rs.next()) {
               Reservacion nueva =new Reservacion(rs.getInt(1),
                       new java.util.Date(rs.getDate(2).getTime()), 
                       new java.util.Date(rs.getDate(3).getTime()), 
                       rs.getInt(4),
                       rs.getInt(5),
                       rs.getInt(6),
                       rs.getInt(7),
                       rs.getInt(8),
                       rs.getInt(9)
                       );
               listaReservaciones.add(nueva);
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error:\n" + ex);
	}  
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
        for(int i=0;i<listaReservaciones.size();i++){
            String[] datos= new String[9];
            datos[0] = listaReservaciones.get(i).getIdReservacion()+"";
            datos[1] = dateFormat.format( listaReservaciones.get(i).getFechaLlegada() );
            datos[2] = dateFormat.format( listaReservaciones.get(i).getFechaSalida());
            datos[3] = listaReservaciones.get(i).getTipoPago()+"";
            datos[4] = listaReservaciones.get(i).getEstado()+"";
            datos[5] = listaReservaciones.get(i).getIdHabitacion()+"";
            
            datos[6] = listaReservaciones.get(i).getCantPersonas()+"";
            
            datos[7] = listaReservaciones.get(i).getIdRecepcionista()+"";
            datos[8] = listaReservaciones.get(i).getIdCliente()+"";
            
            
            DTM.addRow(datos);
            System.out.println(datos[2]);
        }
        vistaReservaciones.jTable1.setModel(DTM);
    }
}
