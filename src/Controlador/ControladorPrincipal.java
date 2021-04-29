/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Vista.FrmLogin;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Percy
 */
public class ControladorPrincipal {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Windows".equals(info.getName())) {       javax.swing.UIManager.setLookAndFeel(info.getClassName());
}
}

        FrmLogin vistaLogin = new FrmLogin();
        ControladorLogin clogin = new ControladorLogin(vistaLogin);
        vistaLogin.setVisible(true);
    }
/* try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String root= "root";
            String pass= "admin";
            String url="jdbc:mysql://localhost:3306/youtubet4";
            
            this.conexion=DriverManager.getConnection(url,root,pass);
            
            System.out.println("conecto a la base de datos");
        }catch(Exception ex){
            System.out.println("NO se ha podido conectar a la bd");
        }*/
}
