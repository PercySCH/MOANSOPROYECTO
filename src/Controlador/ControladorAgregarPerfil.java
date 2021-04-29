/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.dbconecction.CRUD;
import Vista.FrmAgregarPerfil;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Percy
 */
public class ControladorAgregarPerfil {
    
    FrmAgregarPerfil vistaAgregarPerfil;
    CRUD consulta = CRUD.getInstance();
    public ControladorAgregarPerfil(FrmAgregarPerfil vistaAgregarPerfil) {
        this.vistaAgregarPerfil=vistaAgregarPerfil;
        InsertarEventos();
    }
    public void InsertarEventos(){
        vistaAgregarPerfil.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    ResultSet rs = consulta.select("SELECT MAX(idCliente) FROM cliente");
                    rs.next();
                    int id= rs.getInt(1) + 1;
                    int genero;
                    if(vistaAgregarPerfil.rbMasculino.isSelected()){
                        genero=1;
                    }else genero=2;
                    Cliente nuevoCliente = new Cliente(id,
                            vistaAgregarPerfil.txtnombre.getText(),
                            vistaAgregarPerfil.txtapellidos.getText(),
                            new SimpleDateFormat("dd/mm/yyyy").parse(vistaAgregarPerfil.txtfechanac.getText()),
                            vistaAgregarPerfil.txtdireccion.getText(),
                            vistaAgregarPerfil.txttelefono.getText(),
                            genero,
                            Integer.parseInt(vistaAgregarPerfil.txtdni.getText()));
                            consulta.insert("INSERT INTO CLIENTE VALUES ("+nuevoCliente.statementAgregar()+")");
                            vistaAgregarPerfil.dispose();
                                    } catch (ParseException ex) {
                    Logger.getLogger(ControladorAgregarPerfil.class.getName()).log(Level.SEVERE, null, ex);
                    
                } catch (SQLException ex) {
                    Logger.getLogger(ControladorAgregarPerfil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        vistaAgregarPerfil.btnCancelar.addActionListener((ActionEvent ae) -> {
            vistaAgregarPerfil.dispose();
        });
    }
    
}
