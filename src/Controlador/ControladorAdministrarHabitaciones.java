/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.FrmAdministrarHabitaciones;

/**
 *
 * @author Percy
 */
public class ControladorAdministrarHabitaciones {
    public FrmAdministrarHabitaciones vistaAdministrarHabitaciones;
        
    public ControladorAdministrarHabitaciones(FrmAdministrarHabitaciones vistaAdministrarHabitaciones) {     
        this.vistaAdministrarHabitaciones=vistaAdministrarHabitaciones;
        ControladorRegHabitac contrRH = new ControladorRegHabitac(vistaAdministrarHabitaciones.pnlRegHabitac1,this); 
        ControladorRegTipo contrRT=new ControladorRegTipo(vistaAdministrarHabitaciones.pnlRegTipo1,this);
        ControladorHabitaciones contrH= new ControladorHabitaciones(vistaAdministrarHabitaciones.pnlHabitaciones1, this);
        vistaAdministrarHabitaciones.setVisible(true);
        
    } 
    
    
    
}
