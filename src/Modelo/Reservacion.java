/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Percy
 */
public class Reservacion {
    private int nroConfirmacion;
    private Date fechaLlegada;
    private Date fechaSalida;
    private int tipoPago;
    private int estado;
    private int idHabitacion;
    private int cantPersonas;
    private int idRecepcionista;
    private int idCliente;
    CRUD consulta = CRUD.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
    public Reservacion(int nroConfirmacion, Date fechaLlegada, Date fechaSalida, int tipoPago, int estado, int cantPersonas, int idRecepcionista, int idCliente) {
        this.nroConfirmacion = nroConfirmacion;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.tipoPago = tipoPago;
        this.estado = estado;
        this.cantPersonas = cantPersonas;
        this.idRecepcionista = idRecepcionista;
        this.idCliente = idCliente;
        
    }
    public Reservacion(int idReservacion, Date fechaLlegada, Date fechaSalida, int tipoPago, int estado, int idHabitacion, int cantPersonas, int idRecepcionista, int idCliente) {
        this.nroConfirmacion = idReservacion;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.tipoPago = tipoPago;
        this.estado = estado;
        this.idHabitacion = idHabitacion;
        this.cantPersonas = cantPersonas;
        this.idRecepcionista = idRecepcionista;
        this.idCliente = idCliente;             
    }

    public int getIdReservacion() {
        return nroConfirmacion;
    }

    public void setIdReservacion(int idReservacion) {
        this.nroConfirmacion = idReservacion;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public Date getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getIdHabitacion() {
        return idHabitacion;
    }

    public void setIdHabitacion(int idHabitacion) {
        this.idHabitacion = idHabitacion;
    }

    public int getCantPersonas() {
        return cantPersonas;
    }

    public void setCantPersonas(int cantPersonas) {
        this.cantPersonas = cantPersonas;
    }

    public int getIdRecepcionista() {
        return idRecepcionista;
    }

    public void setIdRecepcionista(int idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    public void CheckIn(){
        this.estado = 2;
    }
    public void CheckOut(){
        this.estado = 3;
        consulta.update("UPDATE HABITACION SET estado="+1+" WHERE nrohabitacion= "+this.idHabitacion);
    }
    public void InsertarRevervacion(){
        
        String strFechaEntrada = formatter.format(this.fechaLlegada);
        System.out.println(strFechaEntrada);
        String strFechaSalida = formatter.format(this.fechaSalida);
        System.out.println(strFechaSalida);
        
        consulta.insert("INSERT INTO reservacion VALUES("+
                            nroConfirmacion+",\""+
                            strFechaEntrada+"\", \""+
                            strFechaSalida+"\","+
                            tipoPago+","+
                            estado+","+
                            idHabitacion+","+
                            cantPersonas+","+
                            idRecepcionista+","+
                            idCliente
                            +")");
                    consulta.update("UPDATE habitacion SET estado= 2 WHERE NroHabitacion ="+ getIdHabitacion());
    }
    public void UpdateReservacion(){
        
                 String strFechaEntrada = formatter.format(this.fechaLlegada);
                System.out.println(strFechaEntrada);
                 String strFechaSalida = formatter.format(this.fechaSalida);
                System.out.println(strFechaSalida);
        
        consulta.update("UPDATE reservacion"
                + " SET `fechaLlegada` = '"+strFechaEntrada+"',"
                + " `fechaSalida` = '"+strFechaSalida+"',"
                + " `tipoPago` = '"+this.tipoPago+"'"
                + ", `estado` = '"+this.estado+"',"
                + " `cantPersonas` = '"+this.cantPersonas+"',"
                + " `Recepcionista_idRecepcionista` = '"+this.idRecepcionista+"',"
                + " `Cliente_idCliente` = '"+ this.idCliente+"'"
                + " WHERE (`NroConfirmacion` = '"+this.nroConfirmacion+"')");
    }
    
    
}
