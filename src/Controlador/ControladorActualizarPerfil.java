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
public class ControladorActualizarPerfil {
    
    FrmVerPerfil vistaVerPerfil;
    ControladorPerfiles contrPerfiles;
    CRUD consulta = CRUD.getInstance();
    ResultSet rs;
    Cliente nuevoCliente;
    public ControladorActualizarPerfil(FrmVerPerfil vistaVerPerfil,ControladorPerfiles contrPerfiles,int idCliente) {
        this.vistaVerPerfil=vistaVerPerfil;
        this.contrPerfiles=contrPerfiles;
        DatosCliente(idCliente);
        InsertarEventos();
    }
    public void InsertarEventos(){
        vistaVerPerfil.btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                nuevoCliente.setNombres(vistaVerPerfil.txtnombre.getText());
                consulta.update("UPDATE CLIENTE SET " +
                "CLIENTE.nombres = " + "'"+nuevoCliente.getNombres()+"'" + " WHERE idCliente="+ nuevoCliente.getIdCliente()
                );
                contrPerfiles.vistaPerfiles.jTable1.setModel( contrPerfiles.getTablaClientes());
                vistaVerPerfil.dispose();
            }
        });
        vistaVerPerfil.btnCancelar.addActionListener((ActionEvent ae) -> {
            vistaVerPerfil.dispose();
        });
    }
    public void DatosCliente(int idCliente){
        rs = consulta.select("SELECT * FROM CLIENTE WHERE CLIENTE.idCliente =" + idCliente);
        try {
	    while (rs.next()) {
               
		int auxId = rs.getInt(1);
		String nombres = rs.getString(2);
		String apellidos = rs.getString(3);
                java.util.Date utilDate = new java.util.Date(rs.getDate(4).getTime());
                System.out.println(utilDate);
                String direccion=rs.getString(5);
                String tel=rs.getString(6);
                int gen=rs.getInt(7);
                int dni=rs.getInt(8);
                nuevoCliente = new Cliente(auxId, nombres, apellidos, utilDate, direccion, tel, gen, dni);
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error:\n" + ex);
	}  
        vistaVerPerfil.txtcodigo.setText(nuevoCliente.getIdCliente()+"");
        vistaVerPerfil.txtnombre.setText(nuevoCliente.getNombres());
        vistaVerPerfil.txtapellidos.setText(nuevoCliente.getApellidos());
        vistaVerPerfil.txtdireccion.setText(nuevoCliente.getDireccion());
        vistaVerPerfil.txtdni.setText(nuevoCliente.getDNI()+"");
        vistaVerPerfil.txttelefono.setText(nuevoCliente.getTelefono());
    }
    
}
