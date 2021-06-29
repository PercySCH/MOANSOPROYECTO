/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.sql.ResultSet;
import java.util.Date;

/**
 *
 * @author Percy
 */
public class HistorialCaja {
    private int idHistorialCaja;
    private int cajaId;
    private int idEmpleado;
    private Date fechaHoraApertura;
    private Date fechaHoraCierre;
    private float montoInicial;
    private float montoActual;
    private float montoCerrar;

    private CRUD consulta=  CRUD.getInstance();
    
    public HistorialCaja() {
    }

    public HistorialCaja(int idHistorialCaja, int cajaId, int idEmpleado, Date fechaHoraApertura, float montoInicial) {
        this.idHistorialCaja= idHistorialCaja;
        this.cajaId = cajaId;
        this.idEmpleado = idEmpleado;
        this.fechaHoraApertura = fechaHoraApertura;
        this.montoInicial = montoInicial;
        this.montoActual=montoInicial;
    }
    public void crearHistorialCaja(){
       consulta.insert("Insert into HistorialCaja values ("+
               idHistorialCaja+ ","+
               cajaId+ ","+
               idEmpleado + ", \""+
               Convertidor.DateToStrFullMySql(fechaHoraApertura)+"\" , "+
               "null"+","+
               montoInicial+ ","+
               montoActual+ ","+
               "null"
               + ")");
    }
    public void leerHistorialCaja(int idHistorialCaja){
        try{
            ResultSet rs= consulta.select("select * from HistorialCaja where idHistorialCaja="+idHistorialCaja);
            rs.next();
            this.idHistorialCaja= rs.getInt(1);
            this.cajaId = rs.getInt(2);
            this.idEmpleado = rs.getInt(3);
            this.fechaHoraApertura = new Date(rs.getDate(4).getTime());
             this.montoInicial = rs.getFloat(6);
            this.montoActual= rs.getFloat(7);
            this.montoCerrar= rs.getFloat(8);
            this.fechaHoraCierre = new Date(rs.getDate(5).getTime());
           
        }catch(Exception ex){
            
        }
        System.out.println(toString());
    }
    public void cerrarHistorialCaja() {
        this.fechaHoraCierre=new Date();
        this.montoCerrar=montoActual;
    }

    public void updateHistorialCaja() {
        if(getFechaHoraCierre()==null){
                consulta.update("UPDATE HistorialCaja SET " +
                "montoActual="+getMontoActual()+
                        " WHERE idHistorialCaja="+getIdHistorialCaja()
                );
        }
        else{
                consulta.update("UPDATE HistorialCaja SET " +
                "montoActual="+getMontoActual()+","+
                "fechaHoraCierre = " + "\""+ Convertidor.DateToStrFullMySql(getFechaHoraCierre()) + "\" ," + 
                "montoCerrar =  "+getMontoCerrar()+   
                        " WHERE idHistorialCaja="+getIdHistorialCaja()
                );
        }
    }
    public void realizarMovimiento(float monto){
        this.montoActual+=monto;
    }

    public float getMontoActual() {
        return montoActual;
    }

    public void setMontoActual(float montoActual) {
        this.montoActual = montoActual;
    }
    
    public int getIdHistorialCaja() {
        return idHistorialCaja;
    }

    public void setIdHistorialCaja(int idHistorialCaja) {
        this.idHistorialCaja = idHistorialCaja;
    }

    public int getCajaId() {
        return cajaId;
    }

    public void setCajaId(int cajaId) {
        this.cajaId = cajaId;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Date getFechaHoraApertura() {
        return fechaHoraApertura;
    }

    public void setFechaHoraApertura(Date fechaHoraApertura) {
        this.fechaHoraApertura = fechaHoraApertura;
    }

    public Date getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraCierre(Date fechaHoraCierre) {
        this.fechaHoraCierre = fechaHoraCierre;
    }

    public float getMontoInicial() {
        return montoInicial;
    }

    public void setMontoInicial(float montoInicial) {
        this.montoInicial = montoInicial;
    }

    public float getMontoCerrar() {
        return montoCerrar;
    }

    public void setMontoCerrar(float montoCerrar) {
        this.montoCerrar = montoCerrar;
    }

    @Override
    public String toString() {
        return "HistorialCaja{" + "idHistorialCaja=" + idHistorialCaja + ", cajaId=" + cajaId + ", idEmpleado=" + idEmpleado + ", fechaHoraApertura=" + fechaHoraApertura + ", fechaHoraCierre=" + fechaHoraCierre + ", montoInicial=" + montoInicial + ", montoActual=" + montoActual + ", montoCerrar=" + montoCerrar + ", consulta=" + consulta + '}';
    }

   
    
    
    
    
}
