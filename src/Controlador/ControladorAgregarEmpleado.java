/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Empleado;
import Modelo.dbconecction.CRUD;
import Vista.FrmNuevoEmpleado;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class ControladorAgregarEmpleado {
    private FrmNuevoEmpleado vistaNE;
    private ResultSet rs;
    private CRUD consulta= CRUD.getInstance();
    private int id=0;
    public ControladorAgregarEmpleado() {
    }

    public ControladorAgregarEmpleado(FrmNuevoEmpleado vistaNE) {
        this.vistaNE = vistaNE;
        
        try{
        rs = consulta.select("SELECT MAX(idEmpleado) FROM empleado");
                    rs.next();
                    id= rs.getInt(1) + 1;
        }catch(Exception ex){
            id=1;
        }
        
        InsertarEventos();
        insertarImagen();
        vistaNE.setVisible(true);
    }
    
    public void InsertarEventos(){
        vistaNE.btnaceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(datosCompletos()){
                    try {
                    int genero;
                    Empleado nuevoEmpleado = new Empleado(id,
                    vistaNE.txtnombre.getText(),
                    vistaNE.txtapellido.getText(),
                    new SimpleDateFormat("dd-mm-yyyy").parse(vistaNE.txtfechanac.getText()),
                    vistaNE.cbgenero.getSelectedIndex()+1,
                    vistaNE.txttelef.getText(),
                    vistaNE.txtdireccion.getText(),                              
                    vistaNE.txtusuario.getText(),
                    vistaNE.txtcontrasena.getText(), 
                    2 // empleado recepcionista
                    );
                    nuevoEmpleado.createEmpleado();
                    vistaNE.dispose();             
                    
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese la fecha de forma correcta");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor ingrese todos los campos");
                }
                
            }
        });
        
        vistaNE.btncancelar.addActionListener((ActionEvent ae) -> {
            vistaNE.dispose();
        });
    }
    public Boolean datosCompletos(){
        return true;
        /*if(vistaAgregarPerfil.txtapellidos.getText().compareTo("")==0 ||
           vistaAgregarPerfil.txtcodigo.getText().compareTo("")==0 ||
           vistaAgregarPerfil.txtdireccion.getText().compareTo("")==0||
           vistaAgregarPerfil.txtdni.getText().compareTo("")==0||
           vistaAgregarPerfil.txtfechanac.getText().compareTo("")==0||
           vistaAgregarPerfil.txtnombre.getText().compareTo("")==0||
           vistaAgregarPerfil.txttelefono.getText().compareTo("")==0){
           return false; 
        }
        return true;*/
    }
    
    void insertarImagen(){
       ImageIcon rutaImagenAmostrar = new ImageIcon("src/IMAGENES/emplado.png");
       Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(vistaNE.imgNuevo.getWidth()-10,vistaNE.imgNuevo.getHeight()-10, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vistaNE.imgNuevo.setIcon(rutaImagenAmostrar);
    }
    
}
