/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.HistorialCaja;
import Modelo.dbconecction.CRUD;
import Vista.DialogAbrirCaja;
import Vista.DialogCerrarCaja;
import Vista.FrmLogin;
import Vista.FrmMenuPrincipal;
import Vista.FrmReservacion;
import Vista.FrmPerfiles;
import Vista.FrmReservaciones;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author Percy
 */
public class ControladorMenuPrincipal{
 
    protected ImageIcon rutaImagenAmostrar;
    protected FrmMenuPrincipal vistaMenuPrincipal;
    private Empleado Recepcionista;
    CRUD consulta = CRUD.getInstance();
 
    ControladorMenuPrincipal(FrmMenuPrincipal vistaMenuPrincipal,int idRecepcionista){        
        this.vistaMenuPrincipal=vistaMenuPrincipal;
        this.Recepcionista=new Empleado();
        Recepcionista.leerEmpleado(idRecepcionista);
        this.vistaMenuPrincipal.txtUsuarioActivo.setText("Bienvenid@ "+ Recepcionista.getNombre()+" "+Recepcionista.getApellido());
        ActivarEventos();
        InsertarImagenes();
    }

    public void ActivarEventos(){
        ActionListener acPerfiles=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
          //  JOptionPane.showMessageDialog(null, "Ingresaste a Perfiles");
            FrmPerfiles vistaPerfiles= new FrmPerfiles();
            Cliente nada=new Cliente();
            ControladorPerfiles coPerfiles=new ControladorPerfiles(vistaPerfiles,Recepcionista);
        }
        };
        ActionListener acReservaciones=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

               FrmReservaciones vistaReservaciones = new FrmReservaciones();
               ControladorReservaciones coReservaciones = new ControladorReservaciones(vistaReservaciones,Recepcionista);
               
            }
        };
        ActionListener acNuevaReservacion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmReservacion nuevaReserva = new FrmReservacion();
                ControladorReservacion coNuevaReserva=new ControladorReservacion(nuevaReserva,Recepcionista);
                nuevaReserva.setVisible(true);
               // JOptionPane.showMessageDialog(null, "Ingresaste a NuevaReservacion");
            }
        };
        ActionListener acCaja=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(Recepcionista.isCashierOpen()){
                    DialogCerrarCaja vistaCC= new DialogCerrarCaja(vistaMenuPrincipal, true);
                    ControladorCerrarCaja coCC = new ControladorCerrarCaja(vistaCC, Recepcionista,ControladorMenuPrincipal.this);
                }
                else{
                    DialogAbrirCaja vistaAC=new DialogAbrirCaja(vistaMenuPrincipal, true);
                    ControladorAbrirCaja coAC= new ControladorAbrirCaja(vistaAC, Recepcionista,ControladorMenuPrincipal.this);
                    
                }
            }
        };
        this.vistaMenuPrincipal.btnPerfiles.addActionListener(acPerfiles);
        this.vistaMenuPrincipal.btnResevaciones.addActionListener(acReservaciones);
        this.vistaMenuPrincipal.btnNuevaReservacion.addActionListener(acNuevaReservacion);
        this.vistaMenuPrincipal.btncaja.addActionListener(acCaja);
        this.vistaMenuPrincipal.lblAtras.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                FrmLogin vistaLogin = new FrmLogin();
                ControladorLogin clogin = new ControladorLogin(vistaLogin);
                vistaLogin.setVisible(true);
                vistaMenuPrincipal.dispose();
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
    public void InsertarImagenes(){
        
        // PASAR A UN MODELO EN VEZ DE REPERTIR TODO ESTO VARIAS VECES
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/usuario.png");
        Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.lblPerfil.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reception-bell.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.lblNuevaReserva.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reservaciones.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.lblReservaciones.setIcon(rutaImagenAmostrar);
       
       rutaImagenAmostrar = new ImageIcon("src/IMAGENES/logout.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaMenuPrincipal.lblAtras.getWidth(), vistaMenuPrincipal.lblAtras.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.lblAtras.setIcon(rutaImagenAmostrar);
       
       if(Recepcionista.isCashierOpen()) rutaImagenAmostrar = new ImageIcon("src/IMAGENES/cash-register-green.png");
       else  rutaImagenAmostrar = new ImageIcon("src/IMAGENES/cash-register-red.png");
       Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaMenuPrincipal.btncaja.getWidth(), vistaMenuPrincipal.btncaja.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.btncaja.setIcon(rutaImagenAmostrar);
       
    }
}
