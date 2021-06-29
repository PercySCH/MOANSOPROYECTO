/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Empleado;
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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    public Empleado Recepcionista;
    private DefaultTableModel DTM;
    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
    public ControladorReservaciones(FrmReservaciones vistaReservaciones,Empleado Recepcionista) {
        this.vistaReservaciones=vistaReservaciones;
        this.Recepcionista=Recepcionista;
        popularTabla();
        insertarEventos();
        insertarImagenes();
        vistaReservaciones.setVisible(true);
    }
    public void popularTabla(){
        DTM = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
		return false;
	    }
	};
	String[] headers = {"NroConfirmacion","Cliente", "fechaLlegada", "fechaSalida", "tipoPago", "estado","Habitacion", "cantPersonas", "idRecepcionista"};
	for (String i : headers) {
	    DTM.addColumn(i);
	}
        listaReservaciones = new ArrayList<>();
	ResultSet rs = consulta.select("SELECT nroConfirmacion FROM reservacion");
	try {
	    while (rs.next()) {
               Reservacion nueva = new Reservacion();
               nueva.LeerReservacion(rs.getInt(1));
               listaReservaciones.add(nueva);
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error:\n" + ex);
	}  
        
        for(int i=0;i<listaReservaciones.size();i++){
            String[] datos= new String[9];
            datos[0] = listaReservaciones.get(i).getIdReservacion()+"";
            datos[2] = dateFormat.format( listaReservaciones.get(i).getFechaLlegada() );
            datos[3] = dateFormat.format( listaReservaciones.get(i).getFechaSalida());
            datos[4] = listaReservaciones.get(i).getTipoPago()+"";
            
            
            if(listaReservaciones.get(i).getEstado()==1){
                 datos[5] = "EN ESPERA";
            }
            else if(listaReservaciones.get(i).getEstado()==2){
                datos[5] = "ADENTRO";
            }
            else{
                datos[5] = "TERMINO";
            }
            datos[6] = listaReservaciones.get(i).getIdHabitacion()+"";
            
            datos[7] = listaReservaciones.get(i).getCantPersonas()+"";
            
            datos[8] = listaReservaciones.get(i).getIdRecepcionista()+"";
            rs = consulta.select("select * from cliente where idCliente="+ listaReservaciones.get(i).getIdCliente());
            try{
            rs.next();
            datos[1] = rs.getString(2)+" "+rs.getString(3);
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
                ControladorReservacion contrRs= new ControladorReservacion(vistaReservacion,Recepcionista,idR , ControladorReservaciones.this);
                
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
                ControladorReservacion contrR=new ControladorReservacion(vistaNR, Recepcionista);
                vistaNR.setVisible(true);

            }
        });
        
        vistaReservaciones.jTextField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
                
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                 DTM= new DefaultTableModel();
                vistaReservaciones.jTable1.removeAll();
                String[] headers = {"NroConfirmacion","Cliente", "fechaLlegada", "fechaSalida", "tipoPago", "estado","Habitacion", "cantPersonas", "idRecepcionista"};
                for (String i : headers) {
                    DTM.addColumn(i);
                }
                for(int i=0;i<listaReservaciones.size();i++){
                    if(listaReservaciones.get(i).obtenerNombreCliente().toUpperCase().contains(vistaReservaciones.jTextField1.getText().toUpperCase())){
                    String[] datos= new String[9];
                    datos[0] = listaReservaciones.get(i).getIdReservacion()+"";
                    datos[2] = dateFormat.format( listaReservaciones.get(i).getFechaLlegada() );
                    datos[3] = dateFormat.format( listaReservaciones.get(i).getFechaSalida());
                    datos[4] = listaReservaciones.get(i).getTipoPago()+"";
                    if(listaReservaciones.get(i).getEstado()==1){
                            datos[5] = "EN ESPERA";
                       }
                       else if(listaReservaciones.get(i).getEstado()==2){
                           datos[5] = "ADENTRO";
                       }
                       else{
                           datos[5] = "TERMINO";
                       }
                       datos[6] = listaReservaciones.get(i).getIdHabitacion()+"";

                       datos[7] = listaReservaciones.get(i).getCantPersonas()+"";

                       datos[8] = listaReservaciones.get(i).getIdRecepcionista()+"";
                       datos[1] = listaReservaciones.get(i).obtenerNombreCliente();
                        DTM.addRow(datos);
                    }
                }
                vistaReservaciones.jTable1.setModel(DTM);
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
