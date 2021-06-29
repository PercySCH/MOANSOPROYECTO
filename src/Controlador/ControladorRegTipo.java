/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleado;
import Modelo.Tipo;
import Modelo.dbconecction.CRUD;
import Vista.PnlRegTipo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class ControladorRegTipo {
    private PnlRegTipo pnlRT;
    private ControladorAdministrarHabitaciones contrAdmHab;
    private ResultSet rs;
    private CRUD consulta= CRUD.getInstance();
    private int id=0;
    
    public ControladorRegTipo(PnlRegTipo pnlRT,ControladorAdministrarHabitaciones contrAdmHab) {
        this.contrAdmHab=contrAdmHab;
        this.pnlRT=pnlRT;
        
        try{
        rs = consulta.select("SELECT MAX(idTipoHabitacion) FROM tipo");
                    rs.next();
                    id= rs.getInt(1) + 1;
        }catch(Exception ex){
            id=1;
        }
        
        InsertarEventos();
    }

    private void InsertarEventos() {
        contrAdmHab.vistaAdministrarHabitaciones.pnlRegTipo1.btnaceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(datosCompletos()){
                    try {
                    int genero;
                        Tipo nuevotipo= new Tipo(id,
                            pnlRT.txtNombre .getText(),
                            pnlRT.txtcaracteristica.getText(),
                            pnlRT.txtcomentario.getText()
                            );
                            nuevotipo.createTipo();
                            contrAdmHab.vistaAdministrarHabitaciones.dispose();             
                    
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Por favor ingrese la fecha de forma correcta");
                    }
                }
                else{
                    JOptionPane.showMessageDialog(null, "Por favor ingrese todos los campos");
                }
                
            }
        });
        
            pnlRT.btncancelar.addActionListener((ActionEvent ae) -> {
            contrAdmHab.vistaAdministrarHabitaciones.dispose();
        });
    }
    Boolean datosCompletos(){
        return true;
    }
}
