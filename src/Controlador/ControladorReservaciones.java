/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Reservacion;
import Modelo.dbconecction.CRUD;
import Vista.FrmReservacion;
import Vista.FrmReservaciones;
import Vista.FrmVerPerfil;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Action;
import javax.swing.ImageIcon;
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
    public int idRecepcionista;
    public ControladorReservaciones(FrmReservaciones vistaReservaciones,int idRecepcionista) {
        this.vistaReservaciones=vistaReservaciones;
        this.idRecepcionista=idRecepcionista;
        popularTabla();
        insertarEventos();
        insertarImagenes();
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
            
            
            if(listaReservaciones.get(i).getEstado()==1){
                 datos[4] = "EN ESPERA";
            }
            else if(listaReservaciones.get(i).getEstado()==2){
                datos[4] = "ADENTRO";
            }
            else{
                datos[4] = "TERMINO";
            }
            datos[5] = listaReservaciones.get(i).getIdHabitacion()+"";
            
            datos[6] = listaReservaciones.get(i).getCantPersonas()+"";
            
            datos[7] = listaReservaciones.get(i).getIdRecepcionista()+"";
            rs = consulta.select("select * from cliente where idCliente="+ listaReservaciones.get(i).getIdCliente());
            try{
            rs.next();
            datos[8] = rs.getString(2)+" "+rs.getString(3);
            }catch(SQLException ex){
                
            }           
            
            DTM.addRow(datos);
            System.out.println(datos[2]);
        }
        vistaReservaciones.jTable1.setModel(DTM);
    }
    public void insertarEventos(){
        vistaReservaciones.jTable1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                /*int id= Integer.parseInt((String) vistaPerfiles.jTable1.getValueAt( vistaPerfiles.jTable1.getSelectedRow(), 0));
               FrmVerPerfil vistaVP = new FrmVerPerfil();
               ControladorActualizarPerfil  contrAP= new ControladorActualizarPerfil(vistaVP,ControladorPerfiles.this,id);
               vistaVP.setVisible(true);*/
                int idR = Integer.parseInt((String) vistaReservaciones.jTable1.getValueAt(vistaReservaciones.jTable1.getSelectedRow(), 0));
                FrmReservacion vistaReservacion = new FrmReservacion();
                ControladorReservacion contrRs= new ControladorReservacion(vistaReservacion,idRecepcionista,idR , ControladorReservaciones.this);
                
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
        vistaReservaciones.lblAtras.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                vistaReservaciones.dispose();
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
        
        vistaReservaciones.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmReservacion vistaNR= new FrmReservacion();
                ControladorReservacion contrR=new ControladorReservacion(vistaNR, idRecepcionista);
                vistaNR.setVisible(true);

            }
        });
    }

    private void insertarImagenes() {
        ImageIcon rutaImagenAmostrar = new ImageIcon("src/IMAGENES/atras.png");
        Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        Image ImagenModificada= Imagen.getScaledInstance(vistaReservaciones.lblAtras.getWidth(), vistaReservaciones.lblAtras.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaReservaciones.lblAtras.setIcon(rutaImagenAmostrar);
    }
}
