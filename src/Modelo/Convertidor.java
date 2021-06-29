/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Percy
 */
public class Convertidor {
    public static SimpleDateFormat MySqlFormat = new SimpleDateFormat("yyyy-MM-dd");  //Mysql
    public static SimpleDateFormat MySqlFullFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  //Mysql
    public static SimpleDateFormat StandardFormat = new SimpleDateFormat("dd-MM-yyyy");  
    public static SimpleDateFormat FullStandardFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");  
    public static Date StrToDate(String fecha){
        Date fechaRetornar;
        try {
            return  StandardFormat.parse(fecha);
        } catch (ParseException ex) {
            return null;
        }
    }
    public static String DateToString(Date fecha){
        String fechaRetornar= StandardFormat.format(fecha);;
        return fechaRetornar;
    }
    public static String DateToFullString(Date fecha){
        String fechaRetornar= FullStandardFormat.format(fecha);;
        return fechaRetornar;
    }
    public static String DateToStrMySql(Date fecha){ 
        String fechaRetornar= MySqlFormat.format(fecha);;
        return fechaRetornar;
    }
    public static String DateToStrFullMySql(Date fecha){ 
        String fechaRetornar= MySqlFullFormat.format(fecha);;
        return fechaRetornar;
    }
}
