/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Empleado;
import Vista.FrmLogin;
import Vista.FrmMenuPrincipal;
import Vista.FrmMenuPrincipalAdmin;
import Vista.FrmPerfiles;
import Vista.FrmReservacion;
import Vista.FrmReservaciones;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;

/**
 *
 * @author Percy
 */
public class ControladorMenuPrincipalAdmin {
    int idUsuariActivo;
    protected ImageIcon rutaImagenAmostrar;
    private int option;
    protected FrmMenuPrincipalAdmin vistaMenuPrincipalAdmin;
    private int boton;
    private Empleado activo;
    ControladorMenuPrincipalAdmin(FrmMenuPrincipalAdmin vistaMenuPrincipalAdmin,Empleado activo){
        
        this.vistaMenuPrincipalAdmin=vistaMenuPrincipalAdmin;
        this.activo=activo;
        this.vistaMenuPrincipalAdmin.txtUsuarioActivo.setText("Bienvenid@ Admin "+ activo.getNombre()+" "+activo.getApellido());
        ActivarEventos();
        InsertarImagenes();
    }

    private void InsertarImagenes() {
        ActionListener acPerfiles=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
          //  JOptionPane.showMessageDialog(null, "Ingresaste a Perfiles");
            FrmPerfiles vistaPerfiles= new FrmPerfiles();
            Cliente nada=new Cliente();
            ControladorPerfiles coPerfiles=new ControladorPerfiles(vistaPerfiles);
        }
        };
        ActionListener acReservaciones=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

               FrmReservaciones vistaReservaciones = new FrmReservaciones();
               ControladorReservaciones coReservaciones = new ControladorReservaciones(vistaReservaciones,activo.getIdRecepcionista());
               
            }
        };
        ActionListener acNuevaReservacion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmReservacion nuevaReserva = new FrmReservacion();
                ControladorReservacion coNuevaReserva=new ControladorReservacion(nuevaReserva,activo.getIdRecepcionista());
                nuevaReserva.setVisible(true);
               // JOptionPane.showMessageDialog(null, "Ingresaste a NuevaReservacion");
            }
        };
        ActionListener acAtras= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmLogin vistaLogin = new FrmLogin();
                ControladorLogin clogin = new ControladorLogin(vistaLogin);
                vistaLogin.setVisible(true);
                vistaMenuPrincipalAdmin.dispose();
            }
        };
        this.vistaMenuPrincipalAdmin.btnPerfiles.addActionListener(acPerfiles);
        this.vistaMenuPrincipalAdmin.btnReservaciones.addActionListener(acReservaciones);
        this.vistaMenuPrincipalAdmin.btnNuevaReservacion.addActionListener(acNuevaReservacion);
        this.vistaMenuPrincipalAdmin.btnAtras.addActionListener(acAtras);
    }

    private void ActivarEventos() {
         // PASAR A UN MODELO EN VEZ DE REPERTIR TODO ESTO VARIAS VECES
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/usuario.png");
        Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipalAdmin.imgPerfiles.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reception-bell.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipalAdmin.imgNuevaReservacion.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reservaciones.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipalAdmin.imgReservaciones.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/admin.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipalAdmin.imgAdministrador.setIcon(rutaImagenAmostrar);
       
       rutaImagenAmostrar = new ImageIcon("src/IMAGENES/logout.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaMenuPrincipalAdmin.btnAtras.getWidth(), vistaMenuPrincipalAdmin.btnAtras.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipalAdmin.btnAtras.setIcon(rutaImagenAmostrar);
    }
}
