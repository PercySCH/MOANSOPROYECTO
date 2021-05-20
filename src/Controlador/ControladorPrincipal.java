/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.FrmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Percy
 */
public class ControladorPrincipal {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        
    /*    for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {       javax.swing.UIManager.setLookAndFeel(info.getClassName());
    }
    }*/
    javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatLightLaf());
                   
    FrmLogin vistaLogin = new FrmLogin();
    ControladorLogin clogin = new ControladorLogin(vistaLogin);
    vistaLogin.setVisible(true);
    
    }

}
