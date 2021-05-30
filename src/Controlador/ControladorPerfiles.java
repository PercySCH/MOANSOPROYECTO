/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.dbconecction.CRUD;
import Vista.FrmVerPerfil;
import Vista.FrmLogin;
import Vista.FrmPerfiles;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
    protected FrmPerfiles vistaPerfiles;
    private ImageIcon rutaImagenAmostrar;
    private CRUD consulta = CRUD.getInstance();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private ArrayList<Cliente> clientes;
    private DefaultTableModel tabla;
    //private Cliente retornar;
    private ControladorReservacion controladorNR;
    public ControladorPerfiles(FrmPerfiles vistaFrmPerfiles) {
        
        this.vistaPerfiles=vistaFrmPerfiles;
        tabla=getTablaClientes();
        this.vistaPerfiles.jTable1.setModel(tabla);
        //this.retornar=retornar;
        InsertarEventos();
        InsertarEventoEditar();  
        InsertarImagenes();
        this.vistaPerfiles.setVisible(true);
    }
    public ControladorPerfiles(FrmPerfiles vistaFrmPerfiles,Cliente retornar,ControladorReservacion controladorNR){
        this.vistaPerfiles=vistaFrmPerfiles;
        this.controladorNR=controladorNR;
        tabla=getTablaClientes();
        this.vistaPerfiles.jTable1.setModel(tabla);
       // this.retornar=retornar;
        InsertarEventos();
        InsertarEventoRetornar(retornar);
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
        clientes = new ArrayList<>();
	ResultSet rs = consulta.select("SELECT * FROM CLIENTE");
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
            datos[3] = dateFormat.format( clientes.get(i).getFechaNac() );
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
                FrmVerPerfil vistaAgregarPerfil=new FrmVerPerfil();
                ControladorAgregarPerfil controlador=new ControladorAgregarPerfil(vistaAgregarPerfil);
                vistaAgregarPerfil.setVisible(true);
            }
        });
        
        this.vistaPerfiles.jTextField1.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
              
                
            }

            @Override
            public void keyPressed(KeyEvent ke) {
               
            }

            @Override
            public void keyReleased(KeyEvent ke) {
                  tabla= new DefaultTableModel();
                vistaPerfiles.jTable1.removeAll();
                String[] headers = {"ID", "Nombres", "Apellidos", "fecha Nacimiento", "Direccion", "Teléfono","genero", "DNI"};
                for (String i : headers) {
                    tabla.addColumn(i);
                }
                for(int i=0;i<clientes.size();i++){
                    if(clientes.get(i).getNombres().toUpperCase().contains(vistaPerfiles.jTextField1.getText().toUpperCase())){
                    String[] datos= new String[8];
                    datos[0] = String.valueOf(clientes.get(i).getIdCliente());
                    datos[1] = clientes.get(i).getNombres();
                    datos[2] = clientes.get(i).getApellidos();
                    datos[3] = dateFormat.format( clientes.get(i).getFechaNac() );
                    datos[4] = clientes.get(i).getDireccion();
                    datos[5] = clientes.get(i).getTelefono();

                    datos[6] = String.valueOf(clientes.get(i).getGenero());

                    datos[7] = String.valueOf(clientes.get(i).getDNI());


                    tabla.addRow(datos);
                    }
                }
                vistaPerfiles.jTable1.setModel(tabla);
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

    private void InsertarEventoEditar() {
        this.vistaPerfiles.jTable1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
               //Aparesca
               int id= Integer.parseInt((String) vistaPerfiles.jTable1.getValueAt( vistaPerfiles.jTable1.getSelectedRow(), 0));
               FrmVerPerfil vistaVP = new FrmVerPerfil();
               ControladorActualizarPerfil  contrAP= new ControladorActualizarPerfil(vistaVP,ControladorPerfiles.this,id);
               vistaVP.setVisible(true);
                       
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

    private void InsertarEventoRetornar(Cliente retornar) {
        this.vistaPerfiles.jTable1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
               //Aparesca
               int id= Integer.parseInt((String) vistaPerfiles.jTable1.getValueAt( vistaPerfiles.jTable1.getSelectedRow(), 0));
               String nombre= (String) vistaPerfiles.jTable1.getValueAt(vistaPerfiles.jTable1.getSelectedRow(), 1);
               String apellido= (String) vistaPerfiles.jTable1.getValueAt(vistaPerfiles.jTable1.getSelectedRow(), 2);
              
               retornar.setIdCliente(id);
               retornar.setNombres(nombre);
               retornar.setApellidos(apellido);
               controladorNR.vistaNuevaReserva.txtperfil.setText(nombre+" "+apellido);
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
    }
}
