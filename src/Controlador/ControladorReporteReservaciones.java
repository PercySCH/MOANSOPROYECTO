/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Convertidor;
import Modelo.Empleado;
import Modelo.ReporteReservacion;
import Modelo.dbconecction.CRUD;
import Vista.FrmReportesReservaciones;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorReporteReservaciones {
    FrmReportesReservaciones vistaReporteReserv;
    List<ReporteReservacion> listaReportes; 
    
    public ControladorReporteReservaciones(FrmReportesReservaciones vistaReporteReserv) {
        this.vistaReporteReserv = vistaReporteReserv;
        insertarEventos();
    }

    private void insertarEventos() {
        vistaReporteReserv.btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Date fechaMinima = Convertidor.StrToDate(vistaReporteReserv.txtfechaMinima.getText());
                Date fechaMaxim = Convertidor.StrToDate(vistaReporteReserv.txtfechaMaxima.getText());
                listaReportes= new ArrayList<>();
                
                CRUD consulta= CRUD.getInstance();
                  DefaultTableModel DTM= new DefaultTableModel();
                    String[] headers = {"NroConfirmacion", "Descripcion", "Cliente", "Recepcionista", "Fecha"};
                    for (String i : headers) {
                        DTM.addColumn(i);
                    }
                try{
                ResultSet rs=consulta.select("select * from ReporteReservaciones where fecha>=\""+Convertidor.DateToStrMySql(fechaMinima)+"\" and fecha<=\""+Convertidor.DateToStrMySql(fechaMaxim)+"\"");
                while(rs.next()){  
                    Cliente cl=new Cliente(); cl.leerCliente(rs.getInt(4));
                    Empleado recep=new Empleado(); recep.leerEmpleado(rs.getInt(5));
                    String[] datos= new String[5];
                    datos[0] = rs.getInt(2)+"";
                    datos[1] = rs.getString(3);
                    datos[2] = cl.getNombres()+"";
                    datos[3] = recep.getNombre()+"";
                    datos[4] = Convertidor.DateToFullString(rs.getDate(6));
                    DTM.addRow(datos);
                }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(null, "Fecha inicial o final errónea" );
                }
                ResultSet rs;
                try{
                rs=consulta.select("select count(*) from Reservacion where fechaLlegada>=\""+Convertidor.DateToStrMySql(fechaMinima)+"\" and fechaLlegada<=\""+Convertidor.DateToStrMySql(fechaMaxim)+"\"");
                
                rs.next();
                vistaReporteReserv.txtReservacionesRealizadas.setText(rs.getInt(1)+"");
                
                }catch(Exception ex){}
                try{
                rs=consulta.select("select sum(precio) from tarifa where fecha>=\""+Convertidor.DateToStrMySql(fechaMinima)+"\" and fecha<=\""+Convertidor.DateToStrMySql(fechaMaxim)+"\"");
                
                rs.next();
                vistaReporteReserv.txtMontoAcumulado.setText(rs.getFloat(1)+"");
                }catch(Exception ex){}
                        vistaReporteReserv.tblReporteReservaciones.setModel(DTM);

            }
        });
    }
}
