/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Convertidor;
import Modelo.Empleado;
import Modelo.HistorialCaja;
import Modelo.dbconecction.CRUD;
import Vista.FrmReportesCajas;
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
public class ControladorReporteCajas {
    private FrmReportesCajas vistaRC;

    public ControladorReporteCajas(FrmReportesCajas vistaRC) {
        this.vistaRC = vistaRC;
        insertarEventos();
    }
    private void insertarEventos() {
        vistaRC.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date fechaMinima = Convertidor.StrToDate(vistaRC.txtfechaMinima.getText());
                Date fechaMaxim = Convertidor.StrToDate(vistaRC.txtfechaMaxima.getText());
                
                CRUD consulta= CRUD.getInstance();
                  DefaultTableModel DTM= new DefaultTableModel();
                    String[] headers = {"NroCaja", "Descripcion","Recepcionista", "Montos"};
                    for (String i : headers) {
                        DTM.addColumn(i);
                    }
                try{
                ResultSet rs=consulta.select("select * from HistorialCaja where montoCerrar is not null and fechaHoraApertura>=\""+Convertidor.DateToStrMySql(fechaMinima)+"\" and fechaHoraApertura<=\""+Convertidor.DateToStrMySql(fechaMaxim)+"\"");
                while(rs.next()){  
                    Empleado recep=new Empleado(); recep.leerEmpleado(rs.getInt(3));
                    String[] datos= new String[4];
                    datos[0] = rs.getInt(2)+"";
                    datos[1] = "Se abrio la caja a las "+ Convertidor.DateToFullString(new Date(rs.getDate(4).getTime()))+" y se cerro a las "+Convertidor.DateToFullString(new Date(rs.getDate(5).getTime()));
                    datos[2] = recep.getNombre();
                    datos[3] = "ABRIO: S/"+rs.getFloat(6)+" CERRO: "+rs.getFloat(8);
                    DTM.addRow(datos);
                }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Fecha inicial o final errónea" );
                }
                        vistaRC.tblReporteCajas.setModel(DTM);

            }
        });
    }
    
    
}
