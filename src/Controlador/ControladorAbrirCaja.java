/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleado;
import Modelo.HistorialCaja;
import Modelo.dbconecction.CRUD;
import Vista.DialogAbrirCaja;
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
public class ControladorAbrirCaja {
    Empleado activo;
    DialogAbrirCaja vistaAbrirCaja;
    CRUD consulta=CRUD.getInstance();
    ControladorMenuPrincipal coMenuPrincipal;
    HistorialCaja cajaEmpleado;
    public ControladorAbrirCaja(DialogAbrirCaja vistaAbrirCaja,Empleado activo) {
        this.vistaAbrirCaja = vistaAbrirCaja;
        this.activo= activo;
        this.coMenuPrincipal=coMenuPrincipal;
        this.cajaEmpleado=cajaEmpleado;
        //Eventos de Botones
        insertarElementos();
        insertarEventos1();
        vistaAbrirCaja.setVisible(true);
    }
    public ControladorAbrirCaja(DialogAbrirCaja vistaAbrirCaja,Empleado activo,ControladorMenuPrincipal coMenuPrincipal) {
        this.vistaAbrirCaja = vistaAbrirCaja;
        this.activo= activo;
        this.coMenuPrincipal=coMenuPrincipal;
        this.cajaEmpleado=cajaEmpleado;
        //Eventos de Botones
        insertarElementos();
        insertarEventos();
        vistaAbrirCaja.setVisible(true);
    }
    public void insertarElementos(){
        //Insertar los elementos del comboBox
        ResultSet rs = consulta.select("SELECT * FROM caja");
        try{
        while(rs.next()){
            this.vistaAbrirCaja.cbcaja.addItem( rs.getString(2));
        }
        }catch(Exception ex){
        }
    }
    public void insertarEventos(){
        ActionListener evAbrirC = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                activo.OpenCashier(vistaAbrirCaja.cbcaja.getSelectedIndex()+1,Float.parseFloat( vistaAbrirCaja.txtMontoInicial.getText()));
                coMenuPrincipal.rutaImagenAmostrar = new ImageIcon("src/IMAGENES/cash-register-green.png");
                Image Imagen = coMenuPrincipal.rutaImagenAmostrar.getImage();
                //Remidencionamos
                Image ImagenModificada= Imagen.getScaledInstance(coMenuPrincipal.vistaMenuPrincipal.btncaja.getWidth(), coMenuPrincipal.vistaMenuPrincipal.btncaja.getHeight(), java.awt.Image.SCALE_SMOOTH);
                //Mostramos
                ImageIcon rutaImagenAmostrar= new ImageIcon(ImagenModificada);
                coMenuPrincipal.vistaMenuPrincipal.btncaja.setIcon(rutaImagenAmostrar);
                vistaAbrirCaja.dispose();
            }
        };
        this.vistaAbrirCaja.btnaceptar.addActionListener(evAbrirC);
    }

    private void insertarEventos1() {
        ActionListener evAbrirC = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                activo.OpenCashier(vistaAbrirCaja.cbcaja.getSelectedIndex()+1,Float.parseFloat( vistaAbrirCaja.txtMontoInicial.getText()));
                
                vistaAbrirCaja.dispose();
            }
        };
        this.vistaAbrirCaja.btnaceptar.addActionListener(evAbrirC);
    }
    
}
