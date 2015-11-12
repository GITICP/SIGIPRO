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
 */
public class Actividad_Apoyo {
  private int id_actividad;
  private String nombre;
  private SQLXML estructura;
  private Categoria_AA categoria;
  private String estado_calidad;
  private String estado_direccion;
  private String version;
}
