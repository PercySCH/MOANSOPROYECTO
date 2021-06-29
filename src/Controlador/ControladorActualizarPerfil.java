/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Convertidor;
import Modelo.dbconecction.CRUD;
import Vista.FrmVerPerfil;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
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
        insertarImagen();
    }
    public void InsertarEventos(){
        vistaVerPerfil.btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try{
                nuevoCliente.setNombres(vistaVerPerfil.txtnombre.getText());
                nuevoCliente.setApellidos(vistaVerPerfil.txtapellidos.getText());
                nuevoCliente.setFechaNac( Convertidor.StrToDate(vistaVerPerfil.txtfechanac.getText()));
                nuevoCliente.setDireccion(vistaVerPerfil.txtdireccion.getText());
                nuevoCliente.setTelefono(vistaVerPerfil.txttelefono.getText());
                int genero;
                if(vistaVerPerfil.rbMasculino.isSelected()){
                    genero=1;
                }else genero=2;
                nuevoCliente.setGenero(genero);
                nuevoCliente.setDNI(vistaVerPerfil.txtdni.getText());
                nuevoCliente.UpdateCliente();
                
                
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
        vistaVerPerfil.txtfechanac.setText(Convertidor.DateToString(nuevoCliente.getFechaNac())); 
        vistaVerPerfil.txttelefono.setText(nuevoCliente.getTelefono());
        
        if(nuevoCliente.getGenero()==1){
            vistaVerPerfil.rbMasculino.setSelected(true);
        }else{
            vistaVerPerfil.rbFemenino.setSelected(true);
        }
    }
     private void insertarImagen() {
       ImageIcon rutaImagenAmostrar = new ImageIcon("src/IMAGENES/usuario.png");
       Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(vistaVerPerfil.imgPerfil.getWidth()-10,vistaVerPerfil.imgPerfil.getHeight()-10, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaVerPerfil.imgPerfil.setIcon(rutaImagenAmostrar);
    }
}
