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
public class Inventario_PT {
  private int id_inventario_pt;
  private String lote;
  private int cantidad;
  private Date fecha_vencimiento;
  private Protocolo protocolo;
}
