/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.ReporteRecepcionista;
import Modelo.ReporteReservacion;
import Modelo.dbconecction.CRUD;
import Vista.FrmVerPerfil;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class ControladorAgregarPerfil {
    
    protected FrmVerPerfil vistaAgregarPerfil;
    protected CRUD consulta = CRUD.getInstance();
    protected ResultSet rs;
    protected Empleado Recepcionista;
    private int id;
    
    public ControladorAgregarPerfil(FrmVerPerfil vistaAgregarPerfil,Empleado Recepcionista){
        this.vistaAgregarPerfil=vistaAgregarPerfil;
        this.Recepcionista=Recepcionista;
        try{
        rs = consulta.select("SELECT MAX(idCliente) FROM cliente");
                    rs.next();
                    id= rs.getInt(1) + 1;
        }catch(Exception ex){
            id=1;
        }
        vistaAgregarPerfil.txtcodigo.setText(id+"");
        InsertarEventos();
        insertarImagen();
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
                            
                            nuevoCliente.createCliente();
                            int idReporteRecepcionista=1;
                            try{
                                    ResultSet rs=consulta.select("select MAX(idReporteRecepcionistas) from reporterecepcionistas");
                                rs.next();
                                idReporteRecepcionista=rs.getInt(1)+1;
                            }catch(Exception ex){
                                idReporteRecepcionista=1;
                            }
                            ReporteRecepcionista nuevoReporte= new ReporteRecepcionista(idReporteRecepcionista, Recepcionista.getIdRecepcionista(),"Registro al nuevo Perfil:" + nuevoCliente.getNombres()+" "+nuevoCliente.getApellidos(),new Date());
                            nuevoReporte.createReporteRecepcionista();

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

    private void insertarImagen() {
       ImageIcon rutaImagenAmostrar = new ImageIcon("src/IMAGENES/usuario.png");
       Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(vistaAgregarPerfil.imgPerfil.getWidth()-10,vistaAgregarPerfil.imgPerfil.getHeight()-10, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaAgregarPerfil.imgPerfil.setIcon(rutaImagenAmostrar);
    }
}
