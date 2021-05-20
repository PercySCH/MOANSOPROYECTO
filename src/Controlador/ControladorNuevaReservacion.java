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
import Vista.FrmNuevaReservacion;
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
public class ControladorNuevaReservacion {
     FrmNuevaReservacion vistaNuevaReserva;
    private ImageIcon rutaImagenAmostrar;
    CRUD consulta= CRUD.getInstance();
    List<Tipo> listaTipos= new ArrayList<>();
    private int nroconfirmacion;
    private List<Tarifa> listaTarifas;
    private Cliente cl=new Cliente();
    private Date fechaEntrada=new Date();  
    private Date fechaSalida=new Date();  
    private int idRecepcionista=0;
    private String strFechaEntrada;
    private String strFechaSalida;
    
    public ControladorNuevaReservacion(FrmNuevaReservacion vistaNuevaReserva,int idRecepcionista) {
        
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
    public void InsertarEventos(){
        this.vistaNuevaReserva.lblAtras4.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                //FrmMenuPrincipal vistaMenuPrincipal= new FrmMenuPrincipal();
                //ControladorMenuPrincipal coMenuPrincipal = new ControladorMenuPrincipal(vistaMenuPrincipal);
               // vistaMenuPrincipal.setVisible(true);
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
                ControladorPerfiles contrP= new ControladorPerfiles(vistaP, cl,ControladorNuevaReservacion.this);
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
                    Reservacion nueva;
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

                    vistaNuevaReserva.dispose();
                    String pattern = "yyyy-MM-dd";
                    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
                    consulta.insert("INSERT INTO reservacion VALUES("+
                            nueva.getIdReservacion()+",\""+
                            strFechaEntrada+"\", \""+
                            strFechaSalida+"\","+
                            nueva.getTipoPago()+","+
                            nueva.getEstado()+","+
                            nueva.getIdHabitacion()+","+
                            nueva.getCantPersonas()+","+
                            nueva.getIdRecepcionista()+","+
                            nueva.getIdCliente()
                            +")");
                    consulta.update("UPDATE habitacion SET estado= 2 WHERE NroHabitacion ="+ nueva.getIdHabitacion());

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
                        hab, ControladorNuevaReservacion.this);
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
               
                try {
                    fechaEntrada = new SimpleDateFormat("dd/MM/yyyy").parse(vistaNuevaReserva.txtfechaentrada.getText());
                    fechaSalida = new SimpleDateFormat("dd/MM/yyyy").parse(vistaNuevaReserva.txtfechasalida.getText());
                } catch (ParseException ex) {
                    System.out.println(fechaEntrada.toString());
                    System.out.println(fechaSalida.toString());
                }
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
                 strFechaEntrada = formatter.format(fechaEntrada);
                System.out.println(strFechaEntrada);
                 strFechaSalida = formatter.format(fechaSalida);
                System.out.println(strFechaSalida);
                int idTipo= listaTipos.get(vistaNuevaReserva.cbtipo.getSelectedIndex()).getCodigoTipo();
                String qery="select * from tarifario where Tipo_idTipoHabitacion=\"" + idTipo + "\" and fecha>=\""+strFechaEntrada+"\" and fecha<\""+strFechaSalida+"\"";
                System.out.println(qery);
                ResultSet rs = consulta.select(qery);
                System.out.println("salio bien");
                List<Tarifario> listaTarifarios=new ArrayList<>();
                listaTarifas=new ArrayList<>();
                try {
                    while(rs.next()){
                        Tarifario nuevo=new Tarifario(rs.getInt(1), new java.util.Date(rs.getDate(2).getTime()), rs.getFloat(3));
                        listaTarifarios.add(nuevo);
                    }
                } catch (SQLException ex) {
                    System.out.println("No se obtuvo sql");
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
