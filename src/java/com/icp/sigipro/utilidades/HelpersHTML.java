/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Boga
 */
public class HelpersHTML
{

  private static HelpersHTML theSingleton = null;

  private HelpersHTML()
  {
  }
  
  public static HelpersHTML getSingletonHelpersHTML()
  {
    if (theSingleton == null) {
      theSingleton = new HelpersHTML();
    }
    return theSingleton;
  }
  
    public String getFecha_hoy(){
      Date date = new Date();
      DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
      return df.format(date);
  }
    
    public Date getFecha_hoy_asDate(){
      Date date = new Date();
      return date;
  }

  public String mensajeDeError(String mensaje)
  {
    return String.format("<div class=\"alert alert-danger alert-dismissible\" role=\"alert\">"
                         + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                         + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                         + "%s"
                         + "</div>", mensaje);
  }

  public String mensajeDeExito(String mensaje)
  {
    return String.format("<div class=\"alert alert-success alert-dismissible\" role=\"alert\">"
                         + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                         + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                         + "%s"
                         + "</div>", mensaje);
  }
  
  public String mensajeDeAdvertencia(String mensaje)
  {
    return String.format("<div class=\"alert alert-warning alert-dismissible\" role=\"alert\">"
                         + "<span class=\"glyphicon glyphicon-exclamation-sign\" aria-hidden=\"true\"></span>\n"
                         + "<button type=\"button\" class=\"close\" data-dismiss=\"alert\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Close</span></button>"
                         + "%s"
                         + "</div>", mensaje);
  }
}
