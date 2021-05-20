/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Tarifa;
import Vista.FrmVerTarifas;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Percy
 */
public class ControladorVerTarifas {
    FrmVerTarifas vistaVerTarifas;
    DefaultTableModel modelo=new DefaultTableModel();
    public ControladorVerTarifas(FrmVerTarifas vistaVerTarifas,List<Tarifa> listaTarifas) {
        this.vistaVerTarifas=vistaVerTarifas;    
        
        String[] headers = {"Fecha", "Precio (S/)"};
	for (String i : headers) {
	    modelo.addColumn(i);
	}
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        for(Tarifa t:listaTarifas){
            String[] datos= new String[2];
            datos[0] = dateFormat.format(t.getFecha());
            datos[1] = t.getPrecio()+ "";
            
            modelo.addRow(datos);
        }
        vistaVerTarifas.tblTarifas.setModel(modelo);
        this.vistaVerTarifas.setVisible(true);
    }
}
