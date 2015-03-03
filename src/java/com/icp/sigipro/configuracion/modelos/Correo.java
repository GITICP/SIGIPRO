/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.configuracion.modelos;

import java.lang.reflect.Field;
import org.json.JSONObject;

/**
 *
 * @author Amed
 */
public class Correo {
  Integer id_correo;
  String host;
  String puerto;
  String starttls;
  String nombreEmisor;
  String correo;
  String contrasena;
  
  public Correo( Integer p_idcorreo, String p_host,
          String p_puerto,
          String p_starttls,
          String p_nombreEmisor,
          String p_correo,
          String p_contrasena) {
    id_correo = p_idcorreo;
    host = p_host;
    puerto = p_puerto;
    starttls = p_starttls;
    nombreEmisor = p_nombreEmisor;
    correo = p_correo;
    contrasena = p_contrasena;
  }
  
          //Parsea a JSON la clase de forma automatica y estandarizada para todas las clases
    public String parseJSON(){
        Class _class = this.getClass();
        JSONObject JSON = new JSONObject();
        try{
            Field properties[] = _class.getDeclaredFields();
            for (int i = 0; i < properties.length; i++) {
                Field field = properties[i];
                if (i != 0){
                    JSON.put(field.getName(), field.get(this));
                }else{
                    JSON.put("id_objeto", field.get(this));
                }
            }          
        }catch (Exception e){
            
        }
        return JSON.toString();
    }
    
  public int getID()                 {return id_correo;}
  public String getHost()   {return host;}
  public String getPuerto()          {return puerto;}
  public String getStarttls()                 {return starttls;}
  public String getEmisor()   {return nombreEmisor;}
  public String getCorreo()          {return correo;}
  public String getContrasena()          {return contrasena;}
}
