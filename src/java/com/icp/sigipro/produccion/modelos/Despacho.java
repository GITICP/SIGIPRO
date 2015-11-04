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
public class Despacho {
 private int id_despacho;
 private Inventario_PT inventario;
 private Date fecha;
 private int cantidad;
 private String destino;
 private Usuario coordinador;
 private Date fecha_coordinador;
 private boolean estado_coordinador;
 private Usuario regente;
 private Date fecha_regente;
 private boolean estado_regente;
}
