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
import java.text.DateFormat;
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
    
    protected FrmVerPerfil vistaVerPerfil;
    protected ControladorPerfiles contrPerfiles;
    protected CRUD consulta = CRUD.getInstance();
    protected ResultSet rs;
    private Cliente nuevoCliente;
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
                try{
                nuevoCliente.setNombres(vistaVerPerfil.txtnombre.getText());
                nuevoCliente.setApellidos(vistaVerPerfil.txtapellidos.getText());
                nuevoCliente.setFechaNac( new SimpleDateFormat("dd-MM-yyyy").parse(vistaVerPerfil.txtfechanac.getText()));
                nuevoCliente.setDireccion(vistaVerPerfil.txtdireccion.getText());
                nuevoCliente.setTelefono(vistaVerPerfil.txttelefono.getText());
                int genero;
                    if(vistaVerPerfil.rbMasculino.isSelected()){
                        genero=1;
                    }else genero=2;
          
                nuevoCliente.setGenero(genero);
                nuevoCliente.setDNI(vistaVerPerfil.txtdni.getText());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
                String strFechaNac = formatter.format(nuevoCliente.getFechaNac());
                
                
                consulta.update("UPDATE CLIENTE SET " +
                "CLIENTE.nombres = " + "'"+nuevoCliente.getNombres()+"' ," +
                "CLIENTE.apellidos = " + "'"+nuevoCliente.getApellidos()+"' ," + 
                "CLIENTE.fechaNac = " + "'"+strFechaNac+"' ," + 
                "CLIENTE.direccion = " + "'"+nuevoCliente.getDireccion()+"' ," + 
                "CLIENTE.telefono = " + "'"+nuevoCliente.getTelefono()+"' ," + 
                "CLIENTE.dni = " + "'"+nuevoCliente.getDNI()+"'" + 
                        " WHERE idCliente="+ nuevoCliente.getIdCliente()
                );
                contrPerfiles.vistaPerfiles.jTable1.setModel( contrPerfiles.getTablaClientes());
                vistaVerPerfil.dispose();
                }catch(Exception ex){
                     JOptionPane.showMessageDialog(null, "Por favor ingrese su fecha de forma correcta");
                }
            }
        });
        vistaVerPerfil.btnCancelar.addActionListener((ActionEvent ae) -> {
            vistaVerPerfil.dispose();
        });
    }
    public void DatosCliente(int idCliente){
         DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");  
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
                String dni=rs.getString(8);
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
        vistaVerPerfil.txtfechanac.setText(dateFormat.format(nuevoCliente.getFechaNac())); 
        vistaVerPerfil.txttelefono.setText(nuevoCliente.getTelefono());
        if(nuevoCliente.getGenero()==1){
            vistaVerPerfil.rbMasculino.setSelected(true);
        }else{
            vistaVerPerfil.rbFemenino.setSelected(true);
        }
    }
    
}
