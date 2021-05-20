/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.dbconecction.CRUD;
import Vista.FrmVerPerfil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class ControladorAgregarPerfil {
    
    protected FrmVerPerfil vistaAgregarPerfil;
    protected CRUD consulta = CRUD.getInstance();
    protected ResultSet rs;
    private int id;
    
    public ControladorAgregarPerfil(FrmVerPerfil vistaAgregarPerfil){
        this.vistaAgregarPerfil=vistaAgregarPerfil;
        try{
        rs = consulta.select("SELECT MAX(idCliente) FROM cliente");
                    rs.next();
                    id= rs.getInt(1) + 1;
        }catch(Exception ex){
        }
        vistaAgregarPerfil.txtcodigo.setText(id+"");
        InsertarEventos();
    }
    public void InsertarEventos(){
        vistaAgregarPerfil.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(datosCompletos()){
                    try {
                    int genero;
                    if(vistaAgregarPerfil.rbMasculino.isSelected()){
                        genero=1;
                    }else genero=2;
                    Cliente nuevoCliente = new Cliente(id,
                            vistaAgregarPerfil.txtnombre.getText(),
                            vistaAgregarPerfil.txtapellidos.getText(),
                            new SimpleDateFormat("dd-mm-yyyy").parse(vistaAgregarPerfil.txtfechanac.getText()),
                            vistaAgregarPerfil.txtdireccion.getText(),
                            vistaAgregarPerfil.txttelefono.getText(),
                            genero,
                            vistaAgregarPerfil.txtdni.getText());
                            consulta.insert("INSERT INTO CLIENTE VALUES ("+nuevoCliente.statementAgregar()+")");
                            vistaAgregarPerfil.dispose();             
                    
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese la fecha de forma correcta");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor ingrese todos los campos");
                }
                
            }
        });
        
        vistaAgregarPerfil.btnCancelar.addActionListener((ActionEvent ae) -> {
            vistaAgregarPerfil.dispose();
        });
    }
    public Boolean datosCompletos(){
        if(vistaAgregarPerfil.txtapellidos.getText().compareTo("")==0 ||
           vistaAgregarPerfil.txtcodigo.getText().compareTo("")==0 ||
           vistaAgregarPerfil.txtdireccion.getText().compareTo("")==0||
           vistaAgregarPerfil.txtdni.getText().compareTo("")==0||
           vistaAgregarPerfil.txtfechanac.getText().compareTo("")==0||
           vistaAgregarPerfil.txtnombre.getText().compareTo("")==0||
           vistaAgregarPerfil.txttelefono.getText().compareTo("")==0){
           return false; 
        }
        return true;
    }
}
