/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Habitacion;
import Modelo.Reservacion;
import Modelo.Tarifa;
import Modelo.Tarifario;
import Modelo.Tipo;
import Modelo.dbconecction.CRUD;
import Vista.FrmLogin;
import Vista.FrmMenuPrincipal;
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
    private int nroconfirmacion;
    private List<Tarifa> listaTarifas;
    private Cliente cl=new Cliente();
    private Date fechaEntrada=new Date();  
    private Date auxFecha=new Date();  
    private Date fechaSalida=new Date();  
    private int idRecepcionista=0;
    private String strFechaEntrada;
    private String strFechaSalida;
    private Reservacion nueva;
    private Habitacion seleccionada;
    public ControladorReservacion(FrmReservacion vistaNuevaReserva,int idRecepcionista) {
        
        this.vistaNuevaReserva=vistaNuevaReserva;
        this.idRecepcionista=idRecepcionista;
        try{
            ResultSet rs;
        rs = consulta.select("SELECT MAX(NroConfirmacion) FROM reservacion");
                    rs.next();
                    nroconfirmacion= rs.getInt(1) + 1;
        }catch(Exception ex){
            nroconfirmacion=1;
        }
        InsertarImagenes();
        InsertarValoresCB();
        InsertarEventos();
    }
    public ControladorReservacion(FrmReservacion vistaNuevaReserva,int idRecepcionista,int reservaActual, ControladorReservaciones controladorReservaciones) {
         
             this.vistaNuevaReserva=vistaNuevaReserva;
             this.idRecepcionista=idRecepcionista;
             //Llenar los campos con los de reservacion
             
             InsertarImagenes();
             InsertarValoresCB();
             InsertarEventos();
             InsertarDatosReservaActual(reservaActual);
             vistaNuevaReserva.setVisible(true);
         
    }
    public void InsertarDatosReservaActual(int reservaActual){
         Reservacion actual = null;
         try {
        ResultSet rs;
        rs = consulta.select("select * from reservacion where NroConfirmacion = \""+reservaActual+"\"");
        rs.next();
        actual = new Reservacion(
                rs.getInt(1), 
                new java.util.Date(rs.getDate(2).getTime()),
                new java.util.Date(rs.getDate(3).getTime()),
                rs.getInt(4),
                rs.getInt(5),
                rs.getInt(6), 
                rs.getInt(7),
                rs.getInt(8),
                rs.getInt(9));
        rs=consulta.select("select * from cliente where idCliente = "+actual.getIdCliente());
        rs.next();
        cl = new Cliente(
            rs.getInt(1),
            rs.getString(2),
            rs.getString(3),
            (new java.util.Date(rs.getDate(4).getTime())),
            rs.getString(5),
            rs.getString(6),
            rs.getInt(7),
            rs.getString(8)
        );
        rs= consulta.select("select * from habitacion where nrohabitacion="+actual.getIdHabitacion());
        rs.next();
        seleccionada= new Habitacion(
                rs.getInt(1),
                rs.getInt(2),
                true, true,rs.getInt(5),
                rs.getInt(6));
        
        } catch (SQLException ex) {
             Logger.getLogger(ControladorReservacion.class.getName()).log(Level.SEVERE, null, ex);
         }
        
        vistaNuevaReserva.txtperfil.setText(cl.getNombres()+" "+cl.getApellidos());
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");  
        strFechaEntrada = formatter.format(actual.getFechaLlegada());
        System.out.println(strFechaEntrada);
        strFechaSalida = formatter.format(actual.getFechaSalida());
        vistaNuevaReserva.txtfechaentrada.setText(strFechaEntrada);
        vistaNuevaReserva.txtfechasalida.setText(strFechaSalida);
        vistaNuevaReserva.cbtipo.setSelectedIndex(seleccionada.getIdTipoHabitacion()-1);
        vistaNuevaReserva.cbMetodoPago.setSelectedIndex(actual.getTipoPago()-1);
        vistaNuevaReserva.txtNroHabitacion.setText(actual.getIdHabitacion()+"");
        vistaNuevaReserva.spnCantidad.setValue(actual.getCantPersonas());   
        refrescarPrecio();
        nueva=actual;
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
               refrescarPrecio();
            }
        });
        this.vistaNuevaReserva.btnBuscarPerfil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmPerfiles vistaP= new FrmPerfiles();
                ControladorPerfiles contrP= new ControladorPerfiles(vistaP, cl,ControladorReservacion.this);
            }
        });
        this.vistaNuevaReserva.btnvermas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                FrmVerTarifas vistaVT=new FrmVerTarifas(vistaNuevaReserva, true);
                ControladorVerTarifas controladorVT= new ControladorVerTarifas(vistaVT, listaTarifas);
                }catch(Exception ex){
                     JOptionPane.showMessageDialog(null, "Por favor ingrese las fechas de forma correcta");
                }
            }
        });
        this.vistaNuevaReserva.btnaceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(datosCompletosyValidados()){
                    int nrohab;
                    
                    System.out.println((Integer) vistaNuevaReserva.spnCantidad.getValue()+"");
                    if( vistaNuevaReserva.txtNroHabitacion.getText().compareTo("")==0){
                         nueva= new Reservacion(nroconfirmacion, fechaEntrada, fechaSalida, (vistaNuevaReserva.cbMetodoPago.getSelectedIndex() + 1) , 1  ,
                          (Integer) vistaNuevaReserva.spnCantidad.getValue()  , idRecepcionista, cl.getIdCliente());
                    }
                    else{
                        nrohab = Integer.parseInt(vistaNuevaReserva.txtNroHabitacion.getText());
                        nueva= new Reservacion(nroconfirmacion, fechaEntrada, fechaSalida, (vistaNuevaReserva.cbMetodoPago.getSelectedIndex() + 1) , 1 ,nrohab ,
                          (Integer) vistaNuevaReserva.spnCantidad.getValue()  , idRecepcionista, cl.getIdCliente());
                    }
                    nueva.InsertarRevervacion();
                    vistaNuevaReserva.dispose();
                }
                else{
                     JOptionPane.showMessageDialog(null, "Por favor Rellene todos los datos");
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
                nueva.CheckIn();
                nueva.UpdateReservacion();
                vistaNuevaReserva.dispose();
            }
        });
        
        this.vistaNuevaReserva.btnCheckOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                nueva.CheckOut();
                nueva.UpdateReservacion();
                vistaNuevaReserva.dispose();
            }
        });
        this.vistaNuevaReserva.btnBalance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //
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
    public void refrescarPrecio(){
               // TOMAR EN CUENTA EL REFRESCAR PRECIO DEBE GUIARSE DE LOS TARIFARIOS, PERO SI LA RESERVACION TIENE UNA TARIFA PERSONALIZADA 
               // SE TIENE QUE MOSTRAR LA PERSONALIZADA NO LA DEL TARIFARIO
                try {
                    fechaEntrada = new SimpleDateFormat("dd-MM-yyyy").parse(vistaNuevaReserva.txtfechaentrada.getText());
                    auxFecha = new SimpleDateFormat("dd-MM-yyyy").parse(vistaNuevaReserva.txtfechaentrada.getText());               
                    fechaSalida = new SimpleDateFormat("dd-MM-yyyy").parse(vistaNuevaReserva.txtfechasalida.getText());
                } catch (ParseException ex) {
                    System.out.println(fechaEntrada.toString());
                    System.out.println(fechaSalida.toString());
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
                 strFechaEntrada = formatter.format(fechaEntrada);
                //System.out.println(strFechaEntrada);
                 strFechaSalida = formatter.format(fechaSalida);
               // System.out.println(strFechaSalida);
                int idTipo= listaTipos.get(vistaNuevaReserva.cbtipo.getSelectedIndex()).getCodigoTipo();
                String qery="select * from tarifario where Tipo_idTipoHabitacion=" + idTipo + " and fecha>=\""+strFechaEntrada+"\" and fecha<\""+strFechaSalida+"\"";
                System.out.println(qery);
                ResultSet rs = consulta.select(qery);
               // System.out.println("salio bien");
                List<Tarifario> listaTarifarios=new ArrayList<>();
                listaTarifas=new ArrayList<>();
                try {
                    while(rs.next()){
                        Tarifario nuevo=new Tarifario(rs.getInt(1), new java.util.Date(rs.getDate(2).getTime()), rs.getFloat(3));
                        listaTarifarios.add(nuevo);
                     
                        /*Calendar c = Calendar.getInstance(); 
                        c.setTime(fechaEntrada); 
                        c.add(Calendar.DATE, 1);
                        fechaEntrada = c.getTime();*/
                    }
                    
                } catch (SQLException ex) {
                  
                }
                try{
                                    ResultSet rs2 = consulta.select("select * from tarifariodefecto where Tipo_idTipoHabitacion ="+ idTipo);
                                        rs2.next();
                                    int id=rs2.getInt(1);
                                    float precio = rs2.getFloat(2);
                                    
                                    do{
                                         
                                        Tarifario nuevo= new Tarifario(id,auxFecha,precio);
                                        listaTarifarios.add(nuevo);
                                        System.out.println("se agrego la fecha " + auxFecha);
                                        Calendar c = Calendar.getInstance(); 
                                        c.setTime(auxFecha); 
                                        c.add(Calendar.DATE, 1);
                                        auxFecha = c.getTime();
                                    }while(auxFecha.getTime()<auxFecha.getTime());
                                    
                                    

                            
                    }catch(SQLException ex2){
                            System.out.println("no se pudo conseguir tarifariodefecto");
                     }
                float preciototal=0;
                for(Tarifario t:listaTarifarios){
                    Tarifa nuevo= new Tarifa(t.getIdTipoHabitacion(), nroconfirmacion, t.getFecha(), t.getPrecio());
                    System.out.println(nuevo.getFecha().toString());
                    listaTarifas.add(nuevo);
                    preciototal+=nuevo.getPrecio();
                }
                vistaNuevaReserva.txtpreciototal.setText(preciototal+"");
    }
    public Boolean datosCompletosyValidados(){
        if(vistaNuevaReserva.txtperfil.getText().compareTo("")==0 ||
           vistaNuevaReserva.txtfechaentrada.getText().compareTo("")==0 ||
           vistaNuevaReserva.txtfechasalida.getText().compareTo("")==0 ||
           vistaNuevaReserva.txtpreciototal.getText().compareTo("")==0 ||
           vistaNuevaReserva.txtNroHabitacion.getText().compareTo("")==0
                ){
            return false;
        }
        else return true;
    }
}
