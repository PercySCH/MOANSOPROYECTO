/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Recepcionista;
import Vista.FrmLogin;
import Vista.FrmMenuPrincipal;
import Vista.FrmNuevaReservacion;
import Vista.FrmPerfiles;
import Vista.FrmReservaciones;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 *
 * @author Percy
 */
public class ControladorMenuPrincipal{
    int idUsuariActivo;
    protected ImageIcon rutaImagenAmostrar;
    private int option;
    protected FrmMenuPrincipal vistaMenuPrincipal;
    private int boton;
    private Recepcionista activo;
    ControladorMenuPrincipal(FrmMenuPrincipal vistaMenuPrincipal,Recepcionista activo){
        
        this.vistaMenuPrincipal=vistaMenuPrincipal;
        this.activo=activo;
        this.vistaMenuPrincipal.txtUsuarioActivo.setText("Bienvenid@ "+ activo.getNombre()+" "+activo.getApellido());
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
            ControladorPerfiles coPerfiles=new ControladorPerfiles(vistaPerfiles);
        }
        };
        ActionListener acReservaciones=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

               FrmReservaciones vistaReservaciones = new FrmReservaciones();
               ControladorReservaciones coReservaciones = new ControladorReservaciones(vistaReservaciones);
               
            }
        };
        ActionListener acNuevaReservacion=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmNuevaReservacion nuevaReserva = new FrmNuevaReservacion();
                ControladorNuevaReservacion coNuevaReserva=new ControladorNuevaReservacion(nuevaReserva,activo.getIdRecepcionista());
                nuevaReserva.setVisible(true);
               // JOptionPane.showMessageDialog(null, "Ingresaste a NuevaReservacion");
            }
        };
        this.vistaMenuPrincipal.btnPerfiles.addActionListener(acPerfiles);
        this.vistaMenuPrincipal.btnResevaciones.addActionListener(acReservaciones);
        this.vistaMenuPrincipal.btnNuevaReservacion.addActionListener(acNuevaReservacion);
        
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
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/recepcion.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.lblNuevaReserva.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/lista.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(168, 168, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.lblReservaciones.setIcon(rutaImagenAmostrar);
       
       rutaImagenAmostrar = new ImageIcon("src/IMAGENES/atras.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(115, 57, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaMenuPrincipal.lblAtras.setIcon(rutaImagenAmostrar);
    }
}
