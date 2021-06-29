/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Convertidor;
import Modelo.Empleado;
import Modelo.dbconecction.CRUD;
import Vista.FrmReportesRecepcionistas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorReportesRecepcionistas {
    FrmReportesRecepcionistas vistaRRec;

    public ControladorReportesRecepcionistas(FrmReportesRecepcionistas vistaRRec) {
        this.vistaRRec = vistaRRec;
        insertarEventos();
    }
    private void insertarEventos() {
        vistaRRec.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date fechaMinima = Convertidor.StrToDate(vistaRRec.txtfechaMinima.getText());
                Date fechaMaxim = Convertidor.StrToDate(vistaRRec.txtfechaMaxima.getText());
                
                CRUD consulta= CRUD.getInstance();
                  DefaultTableModel DTM= new DefaultTableModel();
                    String[] headers = {"Recepcionista", "Descripcion", "Fecha"};
                    for (String i : headers) {
                        DTM.addColumn(i);
                    }
                try{
                ResultSet rs=consulta.select("select * from reporterecepcionistas where fecha>=\""+Convertidor.DateToStrMySql(fechaMinima)+"\" and fecha<=\""+Convertidor.DateToStrMySql(fechaMaxim)+"\"");
                while(rs.next()){  
                    
                    Empleado recep=new Empleado(); recep.leerEmpleado(rs.getInt(2));
                    String[] datos= new String[3];
                    datos[0] = recep.getNombre();
                    datos[1] = rs.getString(3);
                    datos[2] = Convertidor.DateToFullString( new Date(rs.getDate(4).getTime()) );
                    DTM.addRow(datos);
                }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Fecha inicial o final errónea" );
                }
                        vistaRRec.tblReporteRecepcionistas.setModel(DTM);

            }
        });
    }
    
}
