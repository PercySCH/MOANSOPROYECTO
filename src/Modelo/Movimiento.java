/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.dbconecction.CRUD;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Percy
 */
public class Movimiento {
    private int idMovimiento;
    private float monto;
    private Date fecha; 
    private String descripcion;
    private int tipoPago;
    private int idBalance;
    private int idHistorialCaja;
    
    CRUD consulta=CRUD.getInstance();

    public Movimiento() {
    }

    public Movimiento(int idMovimiento, float monto, Date fecha, String descripcion,int tipoPago, int idBalance, int HistorialCaja) {
        this.idMovimiento = idMovimiento;
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.tipoPago=tipoPago;
        this.idBalance = idBalance;
        this.idHistorialCaja = HistorialCaja;
    }
    public void LeerMovimiento(int idMovimiento){
        try {
            ResultSet rs = consulta.select("select * from Movimiento where idMovimiento="+idMovimiento);
            rs.next();
            this.idMovimiento = rs.getInt(1);
            this.monto = rs.getFloat(2);
            this.fecha = new Date(rs.getDate(3).getTime());
            this.descripcion = rs.getString(4);
            this.tipoPago=rs.getInt(5);
            this.idBalance = rs.getInt(6);
            this.idHistorialCaja = rs.getInt(7);
        } catch (SQLException ex) {
            Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CreateMovimiento(){
        consulta.insert("insert into Movimiento values("
        + this.idMovimiento+","
        + this.monto+",\""
        + Convertidor.DateToStrFullMySql(this.fecha)+"\",\""
        + this.descripcion+"\","
        + this.tipoPago+","
        + this.idBalance+","
        + this.idHistorialCaja+")"
        );
    }
    public void UpdateMovimiento(){
        
    }
    public int getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(int idBalance) {
        this.idBalance = idBalance;
    }

    public int getHistorialCaja() {
        return idHistorialCaja;
    }

    public void setHistorialCaja(int HistorialCaja) {
        this.idHistorialCaja = HistorialCaja;
    }

    public CRUD getConsulta() {
        return consulta;
    }

    public void setConsulta(CRUD consulta) {
        this.consulta = consulta;
    }

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
    }

    public int getIdHistorialCaja() {
        return idHistorialCaja;
    }

    public void setIdHistorialCaja(int idHistorialCaja) {
        this.idHistorialCaja = idHistorialCaja;
    }
    
    
}
