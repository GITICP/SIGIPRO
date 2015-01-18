package com.icp.sigipro.utilidades;

import com.icp.sigipro.configuracion.dao.CorreoDAO;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.Authenticator;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class UtilidadEmail
{
  private static UtilidadEmail theSingleton = null;
  private UtilidadEmail(){}
  
  public static UtilidadEmail getSingletonUtilidadEmail(){
    if (theSingleton == null)
      theSingleton = new UtilidadEmail();
    return theSingleton;
  }
  
  private boolean enviarCorreo(String para, String sujeto, String cuerpo, InformacionCorreo info)
  {
    boolean resultado = false;
    try 
    {     
      Properties props = new Properties();
      props.put("mail.smtp.host", info.host); //SMTP Host
      props.put("mail.smtp.port", info.puerto); //TLS Port
      props.put("mail.smtp.auth", "true"); //enable authentication
      props.put("mail.smtp.starttls.enable", info.starttls); //enable STARTTLS
      
      final String correo = info.correo;
      final String contrasena = info.contrasenna;

              //create Authenticator object to pass in Session.getInstance argument
      Authenticator auth = new Authenticator() {
          //override the getPasswordAuthentication method
          protected PasswordAuthentication getPasswordAuthentication() {
              return new PasswordAuthentication(correo, contrasena);
          }
      };
      Session sesion = Session.getInstance(props, auth);
        
      MimeMessage msg = new MimeMessage(sesion);
      
      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
      msg.addHeader("format", "flowed");
      msg.addHeader("Content-Transfer-Encoding", "8bit");
      
      msg.setFrom(new InternetAddress(correo, info.nombreEmisor));
      
      msg.setSubject(sujeto, "UTF-8");
      msg.setText(cuerpo, "UTF-8");
      msg.setSentDate(new Date());
      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(para, false));
      
      Transport.send(msg);
      resultado = true;
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    return resultado;
  }
  
  public boolean enviarRecuperacionContrasena(String para, String contrasenaNueva)
  {
    return enviarCorreo(para, "Recuperación de contraseña", contrasenaNueva, new InformacionCorreo());
  }
  
  public boolean enviarUsuarioCreado(String para, String contrasenaNueva)
  {  
    return enviarCorreo(para, "Cuenta SIGIPRO creada", contrasenaNueva, new InformacionCorreo());
  }
  
  public boolean probarNuevaConfiguracion(List<String> nuevaConfiguracion)
  {  
    return enviarCorreo(nuevaConfiguracion.get(4), "Prueba Exitosa", "Este correo es una prueba de la nueva configuración.", new InformacionCorreo(nuevaConfiguracion));    
  }
  
  
  private class InformacionCorreo
  {
    String host;
    String puerto;
    String starttls;
    String nombreEmisor;
    String correo;
    String contrasenna;
    
    private InformacionCorreo()
    {
      CorreoDAO c = new CorreoDAO();
      List<String> lista = c.obtenerListaCorreo();
      rellenarAtributos(lista);
    }
    
    //Para cuando se crea para hacer prueba de una nueva configuración.
    private InformacionCorreo(List<String> lista)
    {
      rellenarAtributos(lista);
    }
    
    private void rellenarAtributos(List<String> lista)
    {
      host = lista.get(0);
      puerto = lista.get(1);
      starttls = lista.get(2);
      nombreEmisor = lista.get(3);
      correo = lista.get(4);
      contrasenna = lista.get(5);
    }
  }
}


