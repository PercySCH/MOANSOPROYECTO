/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Convertidor;
import Modelo.Empleado;
import Modelo.Habitacion;
import Modelo.ReporteReservacion;
import Modelo.Reservacion;
import Modelo.Tarifa;
import Modelo.Tarifario;
import Modelo.Tipo;
import Modelo.dbconecction.CRUD;
import Vista.FrmLogin;
import Vista.FrmMenuPrincipal;
import Vista.FrmPagos2;
import Vista.FrmReservacion;
import Vista.FrmPerfiles;
import Vista.FrmVerHabitaciones;
import Vista.FrmVerTarifas;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class ControladorReservacion {
     FrmReservacion vistaNuevaReserva;
    private ImageIcon rutaImagenAmostrar;
    CRUD consulta= CRUD.getInstance();
    List<Tipo> listaTipos= new ArrayList<>();
 
   // private List<Tarifa> listaTarifas;
    private Cliente cl=new Cliente();
    private Date fechaEntrada=new Date();  
    private Date auxFecha=new Date();  
    private Date fechaSalida=new Date();  
    private Empleado Recepcionista;
    private String strFechaEntrada;
    private String strFechaSalida;
    private Reservacion nueva;
    private int idTipo;
    private Habitacion seleccionada;
    private int nroconfirmacion;
    ControladorReservaciones controladorReservaciones;
    public ControladorReservacion(FrmReservacion vistaNuevaReserva,Empleado Recepcionista) {
        
        this.vistaNuevaReserva=vistaNuevaReserva;
        this.Recepcionista=Recepcionista;
        
        try{
            ResultSet rs;
        rs = consulta.select("SELECT MAX(NroConfirmacion) FROM reservacion");
                    rs.next();
                    nroconfirmacion= rs.getInt(1) + 1;
        }catch(Exception ex){
            nroconfirmacion=0;
        }
        nueva= new Reservacion();
        nueva.setIdReservacion(Recepcionista.getIdRecepcionista());
        InsertarImagenes();
        InsertarValoresCB();
        InsertarEventos();
        InsertarEventoAceptarCreate();
    }
    public ControladorReservacion(FrmReservacion vistaNuevaReserva,Empleado idRecepcionista,int reservaActual, ControladorReservaciones controladorReservaciones) {
         
             this.vistaNuevaReserva=vistaNuevaReserva;
             this.Recepcionista=idRecepcionista;
             this.controladorReservaciones= controladorReservaciones;
             //Llenar los campos con los de reservacion
             
             InsertarImagenes();
             InsertarValoresCB();
             InsertarEventos();
             InsertarDatosReservaActual(reservaActual);
             InsertarEventoAceptarUpdate();
             vistaNuevaReserva.setVisible(true);
         
    }
    public void InsertarDatosReservaActual(int reservaActual){
        
        nueva=new Reservacion();
        nueva.LeerReservacion(reservaActual);
        cl = new Cliente();
        cl.leerCliente(nueva.getIdCliente());
        vistaNuevaReserva.txtperfil.setText(cl.getNombres()+" "+cl.getApellidos());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
        strFechaEntrada = formatter.format(nueva.getFechaLlegada());
        System.out.println(strFechaEntrada);
        strFechaSalida = formatter.format(nueva.getFechaSalida());
        vistaNuevaReserva.txtfechaentrada.setText(strFechaEntrada);
        vistaNuevaReserva.txtfechasalida.setText(strFechaSalida);
        vistaNuevaReserva.cbtipo.setSelectedIndex(nueva.getIdTipoHabitacion()-1);
        vistaNuevaReserva.cbMetodoPago.setSelectedIndex(nueva.getTipoPago()-1);
        vistaNuevaReserva.txtpreciototal.setText(nueva.precioTotal()+"");
        vistaNuevaReserva.txtNroHabitacion.setText(nueva.getIdHabitacion()+"");
        vistaNuevaReserva.spnCantidad.setValue(nueva.getCantPersonas());   
    }
    public void InsertarEventos(){
        this.vistaNuevaReserva.lblAtras4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                vistaNuevaReserva.dispose();
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
        this.vistaNuevaReserva.cbtipo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
               
               nueva.setFechaLlegada(Convertidor.StrToDate(vistaNuevaReserva.txtfechaentrada.getText()));
               nueva.setFechaSalida(Convertidor.StrToDate(vistaNuevaReserva.txtfechasalida.getText()));
               nueva.setIdTipoHabitacion(vistaNuevaReserva.cbtipo.getSelectedIndex()+1);
               nueva.obtenerTarifas();
               vistaNuevaReserva.txtpreciototal.setText(nueva.precioTotal()+"");
            }
        });
        this.vistaNuevaReserva.cbMetodoPago.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent ie) {
                nueva.setTipoPago(vistaNuevaReserva.cbMetodoPago.getSelectedIndex() +1);
            }
        });
        this.vistaNuevaReserva.btnBuscarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmPerfiles vistaP= new FrmPerfiles();
                ControladorPerfiles contrP= new ControladorPerfiles(vistaP, cl,ControladorReservacion.this,Recepcionista);
            }
        });
        this.vistaNuevaReserva.btnvermas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                FrmVerTarifas vistaVT=new FrmVerTarifas(vistaNuevaReserva, true);
                ControladorVerTarifas controladorVT= new ControladorVerTarifas(vistaVT, nueva.getTarifas());
                }catch(Exception ex){
                     JOptionPane.showMessageDialog(null, "Por favor ingrese las fechas de forma correcta");
                }
            }
        });    
        this.vistaNuevaReserva.btnVerHabitaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmVerHabitaciones vistaVH = new FrmVerHabitaciones();
                Habitacion hab = new Habitacion();
                ControladorVerHabitaciones controladorVH = new ControladorVerHabitaciones(vistaVH,
                listaTipos.get(vistaNuevaReserva.cbtipo.getSelectedIndex()).getCodigoTipo(),
                        hab, ControladorReservacion.this);
            }
        });
        
        this.vistaNuevaReserva.btnCheckIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                nueva.setTipoPago(vistaNuevaReserva.cbMetodoPago.getSelectedIndex()+1);
                nueva.setCantPersonas((int)vistaNuevaReserva.spnCantidad.getValue());
                nueva.setIdCliente(cl.getIdCliente());
                nueva.setIdHabitacion( Integer.parseInt(vistaNuevaReserva.txtNroHabitacion.getText()) );
                nueva.CheckIn();
                nueva.UpdateReservacion();
                controladorReservaciones.popularTabla();
                int idReporteReservaciones=1;
                    try{
                        ResultSet rs=consulta.select("select MAX(idReporteReservaciones) from ReporteReservaciones");
                        rs.next();
                        idReporteReservaciones=rs.getInt(1)+1;
                    }catch(Exception ex){
                        idReporteReservaciones=1;
                    }
                    ReporteReservacion nuevoReporte= new ReporteReservacion(idReporteReservaciones, nueva.getNroConfirmacion(),"Realizo Check In" 
                            , cl.getIdCliente(),Recepcionista.getIdRecepcionista(), new Date());
                    nuevoReporte.createReporteReservacion();
                vistaNuevaReserva.dispose();
            }
        });
        
        this.vistaNuevaReserva.btnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                nueva.CheckOut();
                nueva.UpdateReservacion();
                vistaNuevaReserva.dispose();
                controladorReservaciones.popularTabla();
                int idReporteReservaciones=1;
                    try{
                        ResultSet rs=consulta.select("select MAX(idReporteReservaciones) from ReporteReservaciones");
                        rs.next();
                        idReporteReservaciones=rs.getInt(1)+1;
                    }catch(Exception ex){
                        idReporteReservaciones=1;
                    }
                    ReporteReservacion nuevoReporte= new ReporteReservacion(idReporteReservaciones, nueva.getNroConfirmacion(),"Realizo Check Out" 
                            , cl.getIdCliente(),Recepcionista.getIdRecepcionista(), new Date());
                    nuevoReporte.createReporteReservacion();
            }
        });
        this.vistaNuevaReserva.btnBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmPagos2 vistaPagos= new FrmPagos2();
                ControladorPagos controllerPagos=  new ControladorPagos(vistaPagos,nueva, Recepcionista);
                vistaPagos.setVisible(true);
            }
        });
    }
    public void InsertarImagenes(){

       rutaImagenAmostrar = new ImageIcon("src/IMAGENES/atras.png");
       Image Imagen;
        Image ImagenModificada;
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(115, 57, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaNuevaReserva.lblAtras4.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reservaciones.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaNuevaReserva.imgReservacion.getHeight(), vistaNuevaReserva.imgReservacion.getWidth(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaNuevaReserva.imgReservacion.setIcon(rutaImagenAmostrar);
       
    }
    public void InsertarValoresCB(){
        ResultSet rs;
        rs= consulta.select("select * from tipo");
        try{
        while(rs.next()){
            Tipo nuevo= new Tipo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
            listaTipos.add(nuevo);
        }
        }catch(SQLException ex){
            
        }
        for(Tipo t:listaTipos){
            this.vistaNuevaReserva.cbtipo.addItem(t.getNombre());
        }
    }
    public Boolean datosCompletosyValidados(){
        if(vistaNuevaReserva.txtperfil.getText().compareTo("")==0 ||
           vistaNuevaReserva.txtfechaentrada.getText().compareTo("")==0 ||
           vistaNuevaReserva.txtfechasalida.getText().compareTo("")==0 ||
           vistaNuevaReserva.txtpreciototal.getText().compareTo("")==0 
          /* vistaNuevaReserva.txtNroHabitacion.getText().compareTo("")==0*/
                ){
            return false;
        }
        else return true;
    }

    private void InsertarEventoAceptarCreate() {
         this.vistaNuevaReserva.btnaceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(datosCompletosyValidados()){
                    nueva.setIdRecepcionista(Recepcionista.getIdRecepcionista());
                    nueva.setTipoPago(vistaNuevaReserva.cbMetodoPago.getSelectedIndex()+1);
                    nueva.setNroConfirmacion(nroconfirmacion);
                    nueva.setCantPersonas((int)vistaNuevaReserva.spnCantidad.getValue());
                    nueva.setIdCliente(cl.getIdCliente());
                    nueva.setIdHabitacion( Integer.parseInt(vistaNuevaReserva.txtNroHabitacion.getText()) );
                    nueva.setEstado(1);
                    nueva.CreateReservacion();
                    int idReporteReservaciones=1;
                    try{
                        ResultSet rs=consulta.select("select MAX(idReporteReservaciones) from ReporteReservaciones");
                        rs.next();
                        idReporteReservaciones=rs.getInt(1)+1;
                    }catch(Exception ex){
                        idReporteReservaciones=1;
                    }
                    ReporteReservacion nuevoReporte= new ReporteReservacion(idReporteReservaciones, nroconfirmacion,"Realizo una reservacion", nueva.getIdCliente(),Recepcionista.getIdRecepcionista(), new Date());
                    nuevoReporte.createReporteReservacion();
                    vistaNuevaReserva.dispose();
                        
                }
                else{
                     JOptionPane.showMessageDialog(null, "Por favor Rellene todos los datos");
                }     
            }
        });
    }

    private void InsertarEventoAceptarUpdate() {
         this.vistaNuevaReserva.btnaceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(datosCompletosyValidados()){
                    nueva.setFechaLlegada(Convertidor.StrToDate( vistaNuevaReserva.txtfechaentrada.getText() ));
                    nueva.setFechaSalida(Convertidor.StrToDate( vistaNuevaReserva.txtfechasalida.getText() ));
                    nueva.setIdTipoHabitacion(vistaNuevaReserva.cbtipo.getSelectedIndex()+1);
                    nueva.setTipoPago(vistaNuevaReserva.cbMetodoPago.getSelectedIndex()+1);
                    nueva.setCantPersonas((int)vistaNuevaReserva.spnCantidad.getValue());
                    nueva.setIdCliente(cl.getIdCliente());
                    nueva.setIdHabitacion( Integer.parseInt(vistaNuevaReserva.txtNroHabitacion.getText()) );
                    nueva.UpdateReservacion();
                    controladorReservaciones.popularTabla();
                    int idReporteReservaciones=1;
                    try{
                        ResultSet rs=consulta.select("select MAX(idReporteReservaciones) from ReporteReservaciones");
                        rs.next();
                        idReporteReservaciones=rs.getInt(1)+1;
                    }catch(Exception ex){
                        idReporteReservaciones=1;
                    }
                    ReporteReservacion nuevoReporte= new ReporteReservacion(idReporteReservaciones, nueva.getNroConfirmacion(),"Realizo una actualizacion a la reserva", nueva.getIdCliente(),Recepcionista.getIdRecepcionista(), new Date());
                    nuevoReporte.createReporteReservacion();
                    
                    vistaNuevaReserva.dispose();
                }
                else{
                     JOptionPane.showMessageDialog(null, "Por favor Rellene todos los datos");
                }     
            }
        });
    }
}
