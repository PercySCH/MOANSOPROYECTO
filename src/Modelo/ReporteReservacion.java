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
public class ReporteReservacion {
    private int idReporteReservacion;
    private int nroConfirmacion;
    private String descripcion;
    private int idCliente;
    private int idRecepcionista;
    private Date fecha;
    
    CRUD consulta=CRUD.getInstance();
    
    public ReporteReservacion() {
    }

    public ReporteReservacion(int idReporteReservacion, int nroConfirmacion, String descripcion, int idCliente, int idRecepcionista, Date fecha) {
        this.idReporteReservacion = idReporteReservacion;
        this.nroConfirmacion = nroConfirmacion;
        this.descripcion = descripcion;
        this.idCliente = idCliente;
        this.idRecepcionista = idRecepcionista;
        this.fecha = fecha;
    }
    
    public void leerReporteReservacion(int idReporteReservacion){
        try{
            ResultSet rs=consulta.select("select * from reportereservaciones where idReporteReservaciones="+idReporteReservacion);
            rs.next();
            this.idRecepcionista = rs.getInt(5);
            this.idReporteReservacion = rs.getInt(1);
            this.nroConfirmacion = rs.getInt(2);
            this.descripcion = rs.getString(3);
            this.idCliente = rs.getInt(4);
            
            this.fecha = new Date(rs.getDate(6).getTime());
        }catch(Exception ex){
            
        }
    }
    public void createReporteReservacion(){
        consulta.insert("insert into reportereservaciones values("
                +idReporteReservacion+","+
                +nroConfirmacion+",\""
                +descripcion +"\","
                +idCliente+","
                +idRecepcionista+",\""
                +Convertidor.DateToStrFullMySql(fecha)
                + "\")"
        );
    }
    public int getIdReporteReservacion() {
        return idReporteReservacion;
    }

    public void setIdReporteReservacion(int idReporteReservacion) {
        this.idReporteReservacion = idReporteReservacion;
    }

    public int getIdRecepcionista() {
        return nroConfirmacion;
    }

    public void setIdRecepcionista(int idRecepcionista) {
        this.nroConfirmacion = idRecepcionista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNroConfirmacion() {
        return nroConfirmacion;
    }

    public void setNroConfirmacion(int nroConfirmacion) {
        this.nroConfirmacion = nroConfirmacion;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    @Override
    public String toString() {
        return "ReporteReservacion{" + "idReporteReservacion=" + idReporteReservacion + ", nroConfirmacion=" + nroConfirmacion + ", descripcion=" + descripcion + ", idCliente=" + idCliente + ", idRecepcionista=" + idRecepcionista + ", fecha=" + fecha + ", consulta=" + consulta + '}';
    }
    
}
