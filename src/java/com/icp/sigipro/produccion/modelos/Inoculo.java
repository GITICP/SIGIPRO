/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;
import com.icp.sigipro.seguridad.modelos.Usuario;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Inoculo {
  private int id_inoculo;
  private String identificador;
  private Date fecha_preparacion;
  private Usuario encargado_preparacion;
  private Veneno_Producción veneno;
  private int peso;
  
 
}
