/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleado;
import Modelo.HistorialCaja;
import Modelo.dbconecction.CRUD;
import Vista.DialogCerrarCaja;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.ImageIcon;

/**
 *
 * @author Percy
 */
public class ControladorCerrarCaja {
    DialogCerrarCaja vistaCerrarCaja;
    int idHistorialCaja;
    Empleado Recepcionista;
    ControladorMenuPrincipal coMenuPrincipal;
    CRUD consulta= CRUD.getInstance();
    
    public ControladorCerrarCaja(DialogCerrarCaja vistaCerrarCaja,Empleado Recepcionista,ControladorMenuPrincipal coMenuPrincipal) {
        this.vistaCerrarCaja = vistaCerrarCaja;
        this.coMenuPrincipal = coMenuPrincipal;
        System.out.println(Recepcionista.getHistorialCajaEnUso().toString());
        this.Recepcionista=Recepcionista;        
        HistorialCaja enUso= Recepcionista.getHistorialCajaEnUso();
        this.vistaCerrarCaja.lblMontoCierre.setText("S/."+enUso.getMontoActual()+"");
        insertarEventos();
        
        this.vistaCerrarCaja.setVisible(true);
    }
    
    public void insertarEventos(){
        ActionListener acConfirmar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Recepcionista.CloseCashier();
                coMenuPrincipal.rutaImagenAmostrar = new ImageIcon("src/IMAGENES/cash-register-red.png");
                Image Imagen = coMenuPrincipal.rutaImagenAmostrar.getImage();
                //Remidencionamos
                Image ImagenModificada= Imagen.getScaledInstance(coMenuPrincipal.vistaMenuPrincipal.btncaja.getWidth(), coMenuPrincipal.vistaMenuPrincipal.btncaja.getHeight(), java.awt.Image.SCALE_SMOOTH);
                //Mostramos
                ImageIcon rutaImagenAmostrar= new ImageIcon(ImagenModificada);
                coMenuPrincipal.vistaMenuPrincipal.btncaja.setIcon(rutaImagenAmostrar);
                vistaCerrarCaja.dispose();
            }
        };
        this.vistaCerrarCaja.btnConfirmar.addActionListener(acConfirmar);
    }
    
    
}
