/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.produccion.modelos;
import java.sql.SQLXML;
/**
 *
 * @author Amed
 * FALTA EL XML */ 
public class Paso {
  private int id_paso;
  private String nombre;
  private SQLXML estructura;
  private boolean requiere_ap;
  private String version;
}
