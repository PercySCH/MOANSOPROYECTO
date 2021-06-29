/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Percy
 */
public class Reservacion {
    private int nroConfirmacion;
    private Date fechaLlegada;
    private Date fechaSalida;
    private int idTipoHabitacion;
    private int tipoPago;
    private int estado;
    private int idHabitacion;
    private int cantPersonas;
    private int idRecepcionista;
    private int idCliente;
    private List<Tarifa> tarifas;
    
    CRUD consulta = CRUD.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
    public Reservacion(){
    }
    public Reservacion(int nroConfirmacion, Date fechaLlegada, Date fechaSalida,int idtipoHabitacion, int tipoPago, int estado, int cantPersonas, int idRecepcionista, int idCliente) {
        this.nroConfirmacion = nroConfirmacion;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.idTipoHabitacion=idtipoHabitacion;
        this.tipoPago = tipoPago;
        this.estado = estado;
        this.cantPersonas = cantPersonas;
        this.idRecepcionista = idRecepcionista;
        this.idCliente = idCliente;
        obtenerTarifas();
    }
    public Reservacion(int idReservacion, Date fechaLlegada, Date fechaSalida,int idtipoHabitacion, int tipoPago, int estado, int idHabitacion, int cantPersonas, int idRecepcionista, int idCliente) {
        this.nroConfirmacion = idReservacion;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
        this.idTipoHabitacion=idtipoHabitacion;
        this.tipoPago = tipoPago;
        this.estado = estado;
        this.idHabitacion = idHabitacion;
        this.cantPersonas = cantPersonas;
        this.idRecepcionista = idRecepcionista;
        this.idCliente = idCliente;
        obtenerTarifas();
    }
    public void obtenerTarifas(){
        ResultSet rs;
        try{
            this.tarifas=new ArrayList<>();
            List<Tarifario> listaTarifarios=new ArrayList<>();
                
                Date auxFecha =Convertidor.StrToDate( Convertidor.DateToString(fechaLlegada) );
                String qery="select * from tarifario where Tipo_idTipoHabitacion=" + idTipoHabitacion + " and fecha>=\""+Convertidor.DateToStrMySql(fechaLlegada)+"\" and fecha<\""+Convertidor.DateToStrMySql(fechaSalida)+"\"";
                System.out.println(qery);
                rs = consulta.select(qery);
                try {
                    while(rs.next()){
                        Tarifario nuevo=new Tarifario(rs.getInt(1), new java.util.Date(rs.getDate(2).getTime()), rs.getFloat(3));
                        listaTarifarios.add(nuevo);
                    }
                    
                } catch (SQLException ex) {
                }
                try{
                                    ResultSet rs2 = consulta.select("select * from tarifariodefecto where Tipo_idTipoHabitacion ="+ this.idTipoHabitacion);
                                    rs2.next();
                                    int id=rs2.getInt(1);
                                    float precio = rs2.getFloat(2);
                                    
                                    do{
                                        
                                        Tarifario nuevo= new Tarifario(id,auxFecha,precio);
                                        listaTarifarios.add(nuevo);
                                        System.out.println("se agrego la fecha " + auxFecha);
                                        Calendar c = Calendar.getInstance(); 
                                        c.setTime(auxFecha); 
                                        c.add(Calendar.DATE, 1);
                                        auxFecha = c.getTime();
                                    }while(auxFecha.getTime()<fechaSalida.getTime());            
                    }catch(SQLException ex2){
                            System.out.println("no se pudo conseguir tarifariodefecto");
                     }
                float preciototal=0;
                for(Tarifario t:listaTarifarios){
                    Tarifa nuevo= new Tarifa(nroConfirmacion, t.getFecha(), t.getPrecio());
                    System.out.println(nuevo.getFecha().toString());
                    tarifas.add(nuevo);
                    preciototal+=nuevo.getPrecio();
                }
        }catch(Exception ex){
            System.out.println("Hubo un error");
        }
    }
    public void LeerReservacion(int nroConfirmacion){
        try{
        ResultSet rs;
        rs = consulta.select("select * from reservacion where NroConfirmacion = \""+nroConfirmacion+"\"");
        rs.next();
        this.nroConfirmacion=rs.getInt(1);
        this.fechaLlegada=new java.util.Date(rs.getDate(2).getTime());
        this.fechaSalida=new java.util.Date(rs.getDate(3).getTime());
        this.idTipoHabitacion=rs.getInt(4);
        this.tipoPago=rs.getInt(5);
        this.estado=rs.getInt(6);
        this.idHabitacion=rs.getInt(7);
        this.cantPersonas=rs.getInt(8);
        this.idRecepcionista=rs.getInt(9);
        this.idCliente=rs.getInt(10);
        //consulta para tarifa
        this.tarifas=new ArrayList<>();
            rs = consulta.select("select * from tarifa where nroConfirmacion="+this.nroConfirmacion +" and fecha>=\""+Convertidor.DateToStrMySql(fechaLlegada)+"\" and fecha<\""+Convertidor.DateToStrMySql(fechaSalida)+"\" order by fecha DESC");
            while(rs.next()){
                 Tarifa nuevo= new Tarifa(nroConfirmacion,new java.util.Date(rs.getDate(2).getTime()) ,rs.getFloat(3));
                 tarifas.add(nuevo);
            }
        }
        catch(Exception ex){
            System.out.println("Hubo un error");
        }
    }

    public int getNroConfirmacion() {
        return nroConfirmacion;
    }

    public void setNroConfirmacion(int nroConfirmacion) {
        this.nroConfirmacion = nroConfirmacion;
    }

    public List<Tarifa> getTarifas() {
        return tarifas;
    }

    public void setTarifas(List<Tarifa> tarifas) {
        this.tarifas = tarifas;
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
        Habitacion aux=new Habitacion();
        aux.leerHabitacion(this.idHabitacion);
        if(!aux.isoccupied()){
            this.estado = 2;
            consulta.update("UPDATE HABITACION SET estado="+2+" WHERE nrohabitacion= "+this.idHabitacion);
        }
        else{
            JOptionPane.showMessageDialog(null, "El cuarto esta ocupado, por favor cambie de habitacion");
        }
        
    }
    public void CheckOut(){
        this.estado = 3;
        consulta.update("UPDATE HABITACION SET estado="+1+" WHERE nrohabitacion= "+this.idHabitacion);
    }

    public int getIdTipoHabitacion() {
        return idTipoHabitacion;
    }

    public void setIdTipoHabitacion(int idTipoHabitacion) {
        this.idTipoHabitacion = idTipoHabitacion;
    }
    public void CreateReservacion(){    
        consulta.insert("INSERT INTO reservacion VALUES("+
                            nroConfirmacion+",\""+
                            Convertidor.DateToStrMySql(fechaLlegada)+"\", \""+
                            Convertidor.DateToStrMySql(fechaSalida)+"\","+
                            idTipoHabitacion+","+
                            tipoPago+","+
                            estado+","+
                            idHabitacion+","+
                            cantPersonas+","+
                            idRecepcionista+","+
                            idCliente
                            +")");
                    //consulta.update("UPDATE habitacion SET estado= 2 WHERE NroHabitacion ="+ getIdHabitacion());
                    consulta=CRUD.getInstance();
      // confirmar las tarifas
        for(Tarifa t:tarifas){
            consulta.insert("Insert into tarifa values("+
                    nroConfirmacion+", \""+
                    Convertidor.DateToStrMySql(t.getFecha())+"\","+
                    t.getPrecio()
                    + ")" );
        }
      // crear un Balance
        int idBalance=1;
         try{
            ResultSet rs;
        rs = consulta.select("SELECT MAX(idBalance) FROM Balance");
                    rs.next();
                    idBalance= rs.getInt(1) + 1;
        }catch(Exception ex){
            idBalance=1;
        }
        Balance nbalance = new Balance(idBalance, nroConfirmacion);
        nbalance.createBalance();
    }
    public void UpdateReservacion(){  
        consulta.update("UPDATE reservacion"
                + " SET fechaLlegada= \""+Convertidor.DateToStrMySql(getFechaLlegada())+"\","
                + " fechaSalida= \""+Convertidor.DateToStrMySql(getFechaSalida())+"\","
                + " TipoHabitacion= "+this.idTipoHabitacion+","
                + " tipoPago= "+this.tipoPago+","
                + " estado= "+this.estado+","
                + " Habitacion_idHabitacion= "+this.idHabitacion+","
                + " cantPersonas= "+this.cantPersonas+","
                + " Recepcionista_idRecepcionista= "+this.idRecepcionista+","
                + " Cliente_idCliente= "+ this.idCliente
                + " WHERE (NroConfirmacion = "+this.nroConfirmacion+")");
        for(Tarifa t:tarifas){
            consulta.insert("UPDATE tarifa "
                    +"SET precio ="+t.getPrecio()
                    +" WHERE NroConfirmacion ="+t.getNroConfirmacion() + " and fecha = '" + Convertidor.DateToStrMySql(t.getFecha())+"'"
                    );
        }
        ResultSet rs = consulta.select("select MAX(fecha) from tarifa where nroConfirmacion="+this.nroConfirmacion +" and fecha>=\""+Convertidor.DateToStrMySql(fechaLlegada)+"\" and fecha<\""+Convertidor.DateToStrMySql(fechaSalida)+"\" order by fecha DESC");
        try{   
        if(rs.next()){
                if(getFechaSalida().getTime() > new Date(rs.getDate(1).getTime()).getTime() ){
                    Date auxFecha =Convertidor.StrToDate( Convertidor.DateToString(new Date(rs.getDate(1).getTime())) );
                    Calendar c = Calendar.getInstance(); 
                    c.setTime(auxFecha); 
                    c.add(Calendar.DATE, 1);
                    auxFecha = c.getTime(); //corremos un dia
                    for(Tarifa t: tarifas){
                        if(t.getFecha().getTime()>=auxFecha.getTime()){
                            consulta.insert("Insert into tarifa values("+
                            nroConfirmacion+", \""+
                            Convertidor.DateToStrMySql(t.getFecha())+"\","+
                            t.getPrecio()
                            + ")" );
                        }
                    }
                }
            }
        }catch(Exception ex){
            
        }
    }
    public float precioTotal(){
        float total=0;
        for(Tarifa t:tarifas){
            total+=t.getPrecio();
        }
        return total;
    }
    public String obtenerNombreCliente(){
        ResultSet rs=consulta.select("SELECT nombres,apellidos from cliente where idCliente="+this.idCliente);
        try{
            rs.next();
            
        return rs.getString(1)+" "+rs.getString(2);
        }catch(Exception ex){
        
        }
        return " ";
    }

    @Override
    public String toString() {
        return "Reservacion{" + "nroConfirmacion=" + nroConfirmacion + ", fechaLlegada=" + fechaLlegada + ", fechaSalida=" + fechaSalida + ", idTipoHabitacion=" + idTipoHabitacion + ", tipoPago=" + tipoPago + ", estado=" + estado + ", idHabitacion=" + idHabitacion + ", cantPersonas=" + cantPersonas + ", idRecepcionista=" + idRecepcionista + ", idCliente=" + idCliente + ", tarifas=" + tarifas + ", consulta=" + consulta + ", formatter=" + formatter + '}';
    }

    public float balance() {
        float montopagado=0;
        try{
        ResultSet rs= consulta.select("select * from Movimiento where balance_idBalance="+getIdBalance());
        while(rs.next()){
            float monto=rs.getFloat(2);
            montopagado+=monto;
        }
        }catch(Exception ex){
            System.out.println("Este tipo nos debe dinero, NO PAGO NADA");
        }
        float montoapagar=precioTotal();
        return montoapagar-montopagado;
    }

    public int getIdBalance() {
        int idBalance=0;
        try {
            ResultSet rs= consulta.select("select * from Balance where reservacion_NroConfirmacion="+ this.nroConfirmacion);
            rs.next();    
            idBalance=rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(Reservacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idBalance;
    }
    
    
}
