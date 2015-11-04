/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;
import org.json.JSONObject;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
/**
 *
 * @author Amed
 */
public class Salida_Ext {
  private int id_salida;
  private Inventario_PT iventario;
  private Date fecha;
  private int cantidad;
  private String tipo;
  private String observaciones;
}
