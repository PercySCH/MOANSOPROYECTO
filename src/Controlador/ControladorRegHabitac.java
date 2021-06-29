/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleado;
import Modelo.Habitacion;
import Modelo.Tipo;
import Modelo.dbconecction.CRUD;
import Vista.PnlRegHabitac;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class ControladorRegHabitac {
    private PnlRegHabitac pnlRH;
    private ControladorAdministrarHabitaciones contrAdmHab;
    private ResultSet rs;
    private CRUD consulta= CRUD.getInstance();
    private int id=0;
    
    ControladorRegHabitac(PnlRegHabitac pnlRH,ControladorAdministrarHabitaciones contrAdmHab){
        this.contrAdmHab=contrAdmHab;
        this.pnlRH=pnlRH;
        InsertarComponenteComboBox();
        InsertarEventos();
    }

    private void InsertarEventos(){
        pnlRH.btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(datosCompletos()){
                    try {
                    boolean cercaEscalera=pnlRH.chbcercaesc.isContentAreaFilled();
                    boolean cercaPiscina=pnlRH.chbcercapisc.isContentAreaFilled();
                        Habitacion nuevaHabitacion = new Habitacion(
                            Integer.parseInt(pnlRH.txtnumerohab.getText()),
                            Integer.parseInt(pnlRH.txtpiso.getText()),
                            cercaEscalera,
                            cercaPiscina,
                            1,
                            //2 
                            pnlRH.cbtipo.getSelectedIndex()+1
                            );
                            nuevaHabitacion.createHabitacion();
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
        
            contrAdmHab.vistaAdministrarHabitaciones.pnlRegHabitac1.btnCancelar.addActionListener((ActionEvent ae) -> {
            contrAdmHab.vistaAdministrarHabitaciones.dispose();
        });
    }
    Boolean datosCompletos(){
        return true;
    }

    private void InsertarComponenteComboBox() {
        try {
            rs= consulta.select("select * from tipo");
            while(rs.next()){
                
                    Tipo nuevoTp=new Tipo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4));
                    pnlRH.cbtipo.addItem(nuevoTp.getNombre());
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControladorRegHabitac.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
