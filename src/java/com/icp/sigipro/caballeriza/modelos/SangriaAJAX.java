/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

import java.sql.Date;

/**
 *
 * @author Boga
 */
public class SangriaAJAX
{
    
    private int id_sangria;
    private String identificador;
    private Date fecha_dia1;
    private Date fecha_dia2;
    private Date fecha_dia3;
    
    public SangriaAJAX(Sangria s) {
        id_sangria = s.getId_sangria();
        identificador = s.getId_sangria_especial();
        fecha_dia1 = s.getFecha_dia1();
        fecha_dia2 = s.getFecha_dia2();
        fecha_dia3 = s.getFecha_dia3();
    }

    public int getId_sangria() {
        return id_sangria;
    }

    public void setId_sangria(int id_sangria) {
        this.id_sangria = id_sangria;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Date getFecha_dia1() {
        return fecha_dia1;
    }

    public void setFecha_dia1(Date fecha_dia1) {
        this.fecha_dia1 = fecha_dia1;
    }

    public Date getFecha_dia2() {
        return fecha_dia2;
    }

    public void setFecha_dia2(Date fecha_dia2) {
        this.fecha_dia2 = fecha_dia2;
    }

    public Date getFecha_dia3() {
        return fecha_dia3;
    }

    public void setFecha_dia3(Date fecha_dia3) {
        this.fecha_dia3 = fecha_dia3;
    }
    
    
}
