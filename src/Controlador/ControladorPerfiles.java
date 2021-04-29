/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.dbconecction.CRUD;
import Vista.FrmAgregarPerfil;
import Vista.FrmLogin;
import Vista.FrmPerfiles;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorPerfiles {
    FrmPerfiles vistaPerfiles;
    ImageIcon rutaImagenAmostrar;
    CRUD consulta = CRUD.getInstance();
    public ControladorPerfiles(FrmPerfiles vistaFrmPerfiles) {
        
        this.vistaPerfiles=vistaFrmPerfiles;
        this.vistaPerfiles.jTable1.setModel(getTablaClientes());
        InsertarEventos();
        InsertarImagenes();
        this.vistaPerfiles.setVisible(true);
    }
    public DefaultTableModel getTablaClientes() {
	DefaultTableModel DTM = new DefaultTableModel() {
	    @Override
	    public boolean isCellEditable(int row, int column) {
		return false;
	    }
	};
	String[] headers = {"ID", "Nombres", "Apellidos", "fecha Nacimiento", "Direccion", "Teléfono","genero", "DNI"};
	for (String i : headers) {
	    DTM.addColumn(i);
	}
        ArrayList<Cliente> clientes = new ArrayList<>();
	ResultSet rs = consulta.select("SELECT * FROM CLIENTE");
	try {
	    while (rs.next()) {
               
		int auxId = rs.getInt(1);
		String nombres = rs.getString(2);
		String apellidos = rs.getString(3);
                java.util.Date utilDate = new java.util.Date(rs.getDate(4).getTime());
                String direccion=rs.getString(5);
                String tel=rs.getString(6);
                int gen=rs.getInt(7);
                int dni=rs.getInt(8);
                Cliente nuevoCliente = new Cliente(auxId, nombres, apellidos, utilDate, direccion, tel, gen, dni);
                clientes.add(nuevoCliente);
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error:\n" + ex);
	} 
        for(int i=0;i<clientes.size();i++){
            String[] datos= new String[8];
            datos[0] = String.valueOf(clientes.get(i).getIdCliente());
            datos[1] = clientes.get(i).getNombres();
            datos[2] = clientes.get(i).getApellidos();
            datos[3] = "adsa";
            datos[4] = clientes.get(i).getDireccion();
            datos[5] = clientes.get(i).getTelefono();
            datos[6] = String.valueOf(clientes.get(i).getGenero());
            datos[7] = String.valueOf(clientes.get(i).getDNI());
            
            
            DTM.addRow(datos);
            System.out.println(datos[2]);
        }
	return DTM;
    }
    public void InsertarEventos(){
        this.vistaPerfiles.lblAtras.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                vistaPerfiles.dispose();
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
        vistaPerfiles.btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmAgregarPerfil vistaAgregarPerfil=new FrmAgregarPerfil();
                ControladorAgregarPerfil controlador=new ControladorAgregarPerfil(vistaAgregarPerfil);
                vistaAgregarPerfil.setVisible(true);
            }
        });
    }
    public void InsertarImagenes(){
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/atras.png");
        Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
        Image ImagenModificada= Imagen.getScaledInstance(115, 57, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaPerfiles.lblAtras.setIcon(rutaImagenAmostrar);
    }
}
