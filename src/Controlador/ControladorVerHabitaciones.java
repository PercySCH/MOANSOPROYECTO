/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Habitacion;
import Modelo.dbconecction.CRUD;
import Vista.FrmVerHabitaciones;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorVerHabitaciones {
    public FrmVerHabitaciones vistaVerHabitaciones;
    private Habitacion retornar;
    private DefaultTableModel DTM= new DefaultTableModel();
    private CRUD consulta = CRUD.getInstance();
    private ControladorReservacion controladorNR;
    public ControladorVerHabitaciones(FrmVerHabitaciones vistaVerHabitaciones,int tipoHabitacion,Habitacion retornar,ControladorReservacion controladorNR) {
        this.vistaVerHabitaciones=vistaVerHabitaciones;
        this.retornar= retornar;
        this.controladorNR=controladorNR;
        poblarTabla(tipoHabitacion);
        InsertarEventoRetornar();
        InsertarImagenes();
        vistaVerHabitaciones.setVisible(true);
    }
    private void poblarTabla(int tipoHabitacion) {
        //Llenar la tabla de habitaciones del tipo buscado y mostrar solo las disponibles
        //consultamos la tabla habitacion y buscamos las habitaciones disponibles
        List<Habitacion> listahabitaciones = new ArrayList<>();
        String[] headers = {"NroHabitacion", "Escalera", "Piscina", "Estado", "tipo Habitacion"};
	for (String i : headers) {
	    DTM.addColumn(i);
	}
        ResultSet rs = consulta.select("SELECT * FROM habitacion where Tipo_idTipoHabitacion="+tipoHabitacion+" and estado=1");
	try {
	    while (rs.next()) {
                Habitacion nuevaHabitacion = new Habitacion(rs.getInt(1),rs.getInt(2),rs.getBoolean(3),rs.getBoolean(4) ,rs.getInt(5),rs.getInt(6));
                listahabitaciones.add(nuevaHabitacion);
            }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error:\n" + ex);
	}  
        for(int i=0;i<listahabitaciones.size();i++){
            String[] datos= new String[5];
            datos[0] = String.valueOf(listahabitaciones.get(i).getIdHabitacion());
            if(listahabitaciones.get(i).isCercaEscalera()){
                datos[1]="X";
            }else datos[1]=" ";
            if(listahabitaciones.get(i).isCercaPisciona()){
                datos[2]="X";
            }else datos[2]=" ";
            if(listahabitaciones.get(i).getEstado() == 1) datos[3]="Disponible";
            else if(listahabitaciones.get(i).getEstado() == 2) datos[3]="Ocupado";
            else if(listahabitaciones.get(i).getEstado() == 3) datos[3]="Sucio";
            datos[4]= listahabitaciones.get(i).getIdTipoHabitacion()+"";
            DTM.addRow(datos);
        }
        vistaVerHabitaciones.jTable1.setModel(DTM);
        
    }
    private void InsertarEventoRetornar() {
        this.vistaVerHabitaciones.jTable1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
               //Aparesca
               int id= Integer.parseInt((String) vistaVerHabitaciones.jTable1.getValueAt( vistaVerHabitaciones.jTable1.getSelectedRow(), 0));
               controladorNR.vistaNuevaReserva.txtNroHabitacion.setText(id+"");
               vistaVerHabitaciones.dispose();
            }

            @Override
            public void mousePressed(MouseEvent me) {
               
            }

            @Override
            public void mouseReleased(MouseEvent me) {
               
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                
            }

            @Override
            public void mouseExited(MouseEvent me) {
                
            }
            
        });
    }

    private void InsertarImagenes() {
            
    }
    
}
