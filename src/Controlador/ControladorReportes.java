/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.FrmReportes;
import Vista.FrmReportesCajas;
import Vista.FrmReportesRecepcionistas;
import Vista.FrmReportesReservaciones;
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
public class ControladorReportes {
    private FrmReportes vistaReportes;

    public ControladorReportes(FrmReportes vistaReportes) {
        this.vistaReportes = vistaReportes;
        insertarEventos();
        insertarImagenes();
    }

    private void insertarEventos() {
        vistaReportes.btnReservaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmReportesReservaciones vistaRR=new FrmReportesReservaciones();
                ControladorReporteReservaciones contrRR=new ControladorReporteReservaciones(vistaRR);
                vistaRR.setVisible(true);
            }
        });
        vistaReportes.btnCajas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmReportesCajas vistaRC=new FrmReportesCajas();
                ControladorReporteCajas contrRC=new ControladorReporteCajas(vistaRC);
                vistaRC.setVisible(true);
            }
        });
        vistaReportes.btnRecepcionistas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmReportesRecepcionistas vistaRC=new FrmReportesRecepcionistas();
                ControladorReportesRecepcionistas contrRC=new ControladorReportesRecepcionistas(vistaRC);
                vistaRC.setVisible(true);
            }
        });
        vistaReportes.txtAtras.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                vistaReportes.dispose();
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

    private void insertarImagenes() {
         // PASAR A UN MODELO EN VEZ DE REPERTIR TODO ESTO VARIAS VECES
        ImageIcon rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reservaciones.png");
        Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(vistaReportes.lblReservaciones.getWidth(), vistaReportes.lblReservaciones.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaReportes.lblReservaciones.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/cash-register.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaReportes.lblCajas.getWidth(), vistaReportes.lblCajas.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaReportes.lblCajas.setIcon(rutaImagenAmostrar);
       
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/reception-bell.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaReportes.lblRecepcionistas.getWidth(),vistaReportes.lblRecepcionistas.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaReportes.lblRecepcionistas.setIcon(rutaImagenAmostrar);
       
       rutaImagenAmostrar = new ImageIcon("src/IMAGENES/atras.png");
         Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        ImagenModificada= Imagen.getScaledInstance(vistaReportes.txtAtras.getWidth(), vistaReportes.txtAtras.getHeight(), java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaReportes.txtAtras.setIcon(rutaImagenAmostrar);
    }
    
}   
