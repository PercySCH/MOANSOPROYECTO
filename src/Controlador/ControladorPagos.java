/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Cliente;
import Modelo.Convertidor;
import Modelo.Empleado;
import Modelo.Movimiento;
import Modelo.Reservacion;
import Modelo.dbconecction.CRUD;
import Vista.FrmPagos2;
import Vista.FrmRealizarPago2;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorPagos {
    FrmPagos2 vistaPagos;
    private int coReserva;
    private Empleado Recepcionista;
    private Reservacion Reserva;
    CRUD consulta= CRUD.getInstance();
    public ControladorPagos(FrmPagos2 vistaPagos,Reservacion Reserva, Empleado Recepcionista) {
        this.vistaPagos = vistaPagos;
        this.Reserva=  Reserva;
        this.Recepcionista=Recepcionista;
        InicializarDatos();
        ActivarEventos();
    }

    private void ActivarEventos() {
        vistaPagos.btnRealizarPago.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                FrmRealizarPago2 vistaRP= new FrmRealizarPago2(); 
                ControladorRealizarPago contrRP= new ControladorRealizarPago(vistaRP, ControladorPagos.this,Reserva,Recepcionista);
                vistaRP.setVisible(true);
            }
        });
    }

    public void InicializarDatos() {
       vistaPagos.txtMontoaPagar.setText("S/. "+Reserva.precioTotal());
       vistaPagos.txtbalance.setText("S/. "+Reserva.balance());
       vistaPagos.txtcliente.setText(Reserva.obtenerNombreCliente());
       vistaPagos.txtnroReserv.setText(Reserva.getIdReservacion()+"");
       
       DefaultTableModel DTM = new DefaultTableModel();
	String[] headers = {"tipo", "monto", "fecha", "descripcion"};
	for (String i : headers) {
	    DTM.addColumn(i);
	}
        List<Movimiento> Movimientos = new ArrayList<>();
	ResultSet rs = consulta.select("SELECT * FROM Movimiento where balance_idbalance="+Reserva.getIdBalance());
	try {
	    while (rs.next()) {  
		Movimiento unMovimiento = new Movimiento(rs.getInt(1),rs.getFloat(2) , new Date( rs.getDate(3).getTime()),rs.getString(4) , rs.getInt(5), rs.getInt(6), rs.getInt(7));
                Movimientos.add(unMovimiento);
	    }
	} catch (SQLException ex) {
	    JOptionPane.showMessageDialog(null, "Error:\n" + ex);
	}  
        for(int i=0;i<Movimientos.size();i++){
            String[] datos= new String[4];
            switch (Movimientos.get(i).getTipoPago()) {
                case 1:
                    datos[0] ="EFECTIVO";
                    break;
                case 2:
                    datos[0] = "TARJETA";
                    break;
                case 3:
                    datos[0] = "YAPE";
                    break;
                case 4:
                    datos[0] = "TUNKI";
                    break;
                case 21:
                    datos[0] = "VISA";
                    break;
                case 22:
                    datos[0] = "MASTERCARD";
                    break;
                case 23:
                    datos[0] = "DINERSCLUB";
                    break;
                case 24:
                    datos[0] = "AMERICAN EXPRESS";
                    break;
                default:
                    break;
            }
            datos[1] = Movimientos.get(i).getMonto()+"";
            datos[2] = Convertidor.DateToString( Movimientos.get(i).getFecha());
            datos[3] = Movimientos.get(i).getDescripcion();
                   
            DTM.addRow(datos);           
        }
        vistaPagos.jTable1.setModel(DTM);
    }
    
}
