/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Vista.FrmAdministrar;
import Vista.FrmAdministrarHabitaciones;
import Vista.FrmLogin;
import Vista.FrmNuevoEmpleado;
import Vista.FrmPerfiles;
import Vista.FrmReportes;
import Vista.FrmReservacion;
import Vista.FrmReservaciones;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
    
/**
 *
 * @author Percy
 */
public class ControladorAdministrar {
    private FrmAdministrar vistaAdministrar;
    public ControladorAdministrar(FrmAdministrar vistaAdministrar) {
        this.vistaAdministrar=vistaAdministrar;
        vistaAdministrar.setVisible(true);
        InsertarImagenes();
        activarEventos();
    }
    private void activarEventos() {
        MouseListener acAtras=new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                vistaAdministrar.dispose()  ;
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
        };
                
        ActionListener acReportes=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmReportes vistaReportes= new FrmReportes();
                ControladorReportes contrReportes= new ControladorReportes(vistaReportes);
                vistaReportes.setVisible(true);
            }
        };
                
        ActionListener acAdmHabitaciones=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmAdministrarHabitaciones frmAdHa = new FrmAdministrarHabitaciones();
                ControladorAdministrarHabitaciones coAdmHab=new ControladorAdministrarHabitaciones(frmAdHa);
                
               // JOptionPane.showMessageDialog(null, "Ingresaste a NuevaReservacion");
            }
        };
        ActionListener AdmUsuarios= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmNuevoEmpleado frmNE= new FrmNuevoEmpleado();
                ControladorAgregarEmpleado cAE = new ControladorAgregarEmpleado(frmNE);
                
            }
        };
        this.vistaAdministrar.btnReportes.addActionListener(acReportes);
        this.vistaAdministrar.txtAtras.addMouseListener(acAtras);
        this.vistaAdministrar.btnAdmHabitaciones.addActionListener(acAdmHabitaciones);
        this.vistaAdministrar.btnAdmUsuarios.addActionListener(AdmUsuarios);
   //     this.vistaAdministrar.btnReportes.addActionListener(acAdministrar);
    }
    private void InsertarImagenes() {
         // PASAR A UN MODELO EN VEZ DE REPERTIR TODO ESTO VARIAS VECES
        ImageIcon rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reporte.png");
        Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(vistaAdministrar.imgReportes.getWidth(), vistaAdministrar.imgReportes.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaAdministrar.imgReportes.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/emplado.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaAdministrar.imgAdmUsuarios.getWidth(), vistaAdministrar.imgAdmUsuarios.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaAdministrar.imgAdmUsuarios.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/habitacion.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaAdministrar.imgAdmHabitaciones.getWidth(),vistaAdministrar.imgAdmHabitaciones.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaAdministrar.imgAdmHabitaciones.setIcon(rutaImagenAmostrar);

       rutaImagenAmostrar = new ImageIcon("src/IMAGENES/atras.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaAdministrar.txtAtras.getWidth(), vistaAdministrar.txtAtras.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaAdministrar.txtAtras.setIcon(rutaImagenAmostrar);
    }
}
