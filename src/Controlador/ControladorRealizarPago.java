/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Empleado;
import Modelo.Movimiento;
import Modelo.ReporteReservacion;
import Modelo.Reservacion;
import Modelo.dbconecction.CRUD;
import Vista.DialogAbrirCaja;

import Vista.FrmRealizarPago2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class ControladorRealizarPago {
    private FrmRealizarPago2 vistaRP;
    private ControladorPagos contrPagos;
    private Reservacion Reserva;
    private Empleado Recepcionista;
    CRUD consulta= CRUD.getInstance();
    
    public ControladorRealizarPago(FrmRealizarPago2 vistaRP, ControladorPagos contrPagos,Reservacion Reserva,Empleado Recepcionista) {
        this.vistaRP = vistaRP;
        this.contrPagos = contrPagos;
        this.Reserva=Reserva;
        this.Recepcionista=Recepcionista;
        
        InicializarEventos();
    }

    private void InicializarEventos() {
        vistaRP.btnRealizarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(!Recepcionista.isCashierOpen()){
                    DialogAbrirCaja vistaACaja=new DialogAbrirCaja(vistaRP, true);
                    ControladorAbrirCaja contrAc=new ControladorAbrirCaja(vistaACaja, Recepcionista);
                }
                int idMovimiento=0;
                try{
                ResultSet rs= consulta.select("select MAX(idMovimiento) from Movimiento");
                rs.next();
                idMovimiento= rs.getInt(1) + 1;
                }catch(Exception ex){
                    idMovimiento=1;
                }
                int TipoPago=vistaRP.cbTipoPago.getSelectedIndex()+1;
                if(TipoPago == 2){
                    TipoPago*=10;
                    TipoPago+=vistaRP.cbTipoTarjeta.getSelectedIndex()+1;
                }
                try{
                Movimiento nuevoMovimiento =  new Movimiento(idMovimiento,Float.parseFloat(vistaRP.txtpago.getText()),new Date(),vistaRP.txtdescripcion.getText(),
                        TipoPago, Reserva.getIdBalance(), Recepcionista.getHistorialCajaEnUso().getIdHistorialCaja());
                nuevoMovimiento.CreateMovimiento();
                if(nuevoMovimiento.getTipoPago()==1){
                    Recepcionista.DoMovement(Float.parseFloat(vistaRP.txtpago.getText()));
                    
                }
                
                int idReporteReservaciones=1;
                    try{
                        ResultSet rs=consulta.select("select MAX(idReporteReservaciones) from ReporteReservaciones");
                        rs.next();
                        idReporteReservaciones=rs.getInt(1)+1;
                    }catch(Exception ex){
                        idReporteReservaciones=1;
                    }
                    ReporteReservacion nuevoReporte= new ReporteReservacion(idReporteReservaciones, Reserva.getNroConfirmacion(),"Realizo un pago en " +vistaRP.cbTipoPago.getItemAt(vistaRP.cbTipoPago.getSelectedIndex()) +" de "+Float.parseFloat(vistaRP.txtpago.getText())
                            , Reserva.getIdCliente(),Recepcionista.getIdRecepcionista(), new Date());
                    nuevoReporte.createReporteReservacion();
                contrPagos.InicializarDatos();
                vistaRP.dispose();
                }catch(Exception ex){
                     JOptionPane.showMessageDialog(null, "Datos Erroneos, intente denuevo." );
                }
            }
        });
    }
    
}
