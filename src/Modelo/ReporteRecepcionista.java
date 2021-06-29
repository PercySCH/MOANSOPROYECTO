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
public class ReporteRecepcionista {
    private int idReporteRecepcionista;
    private int idRecepcionista;
    private String descripcion;
    private Date fecha;
    
    CRUD consulta=CRUD.getInstance();
    
    public ReporteRecepcionista() {
    }

    public ReporteRecepcionista(int idReporteRecepcionista, int idRecepcionista, String descripcion, Date fecha) {
        this.idReporteRecepcionista = idReporteRecepcionista;
        this.idRecepcionista = idRecepcionista;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }
    public void leerReporteRecepcionista(int idReporteRecepcionista){
         try{
            ResultSet rs=consulta.select("select * from reporterecepcionistas where idReporteRecepcionistas="+idReporteRecepcionista);
            rs.next();
            this.idReporteRecepcionista = rs.getInt(1);
            this.idRecepcionista = rs.getInt(2);
            this.descripcion = rs.getString(3);
            this.fecha = new Date(rs.getDate(4).getTime());
        }catch(Exception ex){
            
        }
    }
    public void createReporteRecepcionista(){
        consulta.insert("insert into reporterecepcionistas values("
                +idReporteRecepcionista+","+
                +idRecepcionista+",\" "
                +descripcion +"\",\""
                +Convertidor.DateToStrFullMySql(fecha)
                + "\")"
        );
    }
    public int getIdReporteRecepcionista() {
        return idReporteRecepcionista;
    }

    public void setIdReporteRecepcionista(int idReporteRecepcionista) {
        this.idReporteRecepcionista = idReporteRecepcionista;
    }

    public int getIdRecepcionista() {
        return idRecepcionista;
    }

    public void setIdRecepcionista(int idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
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
    
    
}
