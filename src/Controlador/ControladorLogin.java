/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.Login;
import Modelo.Recepcionista;
import Vista.FrmLogin;
import Vista.FrmMenuPrincipal;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import java.util.Timer;
import java.util.TimerTask;
/**
 *
 * @author Percy
 */
public final class ControladorLogin implements ActionListener{
    protected FrmLogin vista;
    protected int contador = 0;
    private Timer timer;
    private TimerTask tarea;
    private ImageIcon rutaImagenAmostrar;
    
    ControladorLogin(FrmLogin vista){
        this.vista=vista;
        DeslizarImagenes();
        ActionCerrar();
        InsertarLogo();
        this.vista.btnInciarSesion.addActionListener(this); 
    }
    public void ActionCerrar(){
        vista.jLabel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent me) {
                timer.cancel();
                vista.dispose();
            }

            @Override
            public void mousePressed(MouseEvent me) {
                
            }

            @Override
            public void mouseReleased(MouseEvent me) {
               
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                            }

            @Override
            public void mouseExited(MouseEvent me) {
               
            }
        });
    }
    public void DeslizarImagenes(){
        
        int tiempo= 1*1000; //1seg
        tarea= new TimerTask(){
            @Override
            public void run(){
                // Recogemos la imagen de la ruta
                
                rutaImagenAmostrar = new ImageIcon("src/IMAGENES/LATERAL"+(contador+1)+".jpg");
                 Image Imagen= rutaImagenAmostrar.getImage();
                //Remidencionamos
                Image ImagenModificada= Imagen.getScaledInstance(561, 530, java.awt.Image.SCALE_SMOOTH);
                //Mostramos
                rutaImagenAmostrar= new ImageIcon(ImagenModificada);
                vista.InsertarImagen.setIcon(rutaImagenAmostrar);
                
                if(contador<6) contador++;
                else contador=0;
            }    
        };
        timer = new Timer(); 
        timer.scheduleAtFixedRate(tarea, 0, tiempo);
        
       
    }
    public void InsertarLogo(){
        rutaImagenAmostrar = new ImageIcon("src/IMAGENES/logopng.png");
        Image Imagen= rutaImagenAmostrar.getImage();
       //Remidencionamos
       Image ImagenModificada= Imagen.getScaledInstance(250, 250, java.awt.Image.SCALE_SMOOTH);
       //Mostramos
       rutaImagenAmostrar= new ImageIcon(ImagenModificada);
       vista.lblLogo.setIcon(rutaImagenAmostrar);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {
          Login login= new Login(vista.txtusuario.getText(), vista.txtcontrasena.getText());
          Recepcionista activo= login.IniciarSesion();
        if(activo.getIdRecepcionista()!=0){
            //pasar al siguiente formulario
              FrmMenuPrincipal vistaMP=new FrmMenuPrincipal();
              ControladorMenuPrincipal contrMP= new ControladorMenuPrincipal(vistaMP,activo);
              vistaMP.setVisible(true);
              vista.dispose();
            
        }
    }
    
}
