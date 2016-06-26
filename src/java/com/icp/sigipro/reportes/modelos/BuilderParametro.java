/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.reportes.modelos;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Boga
 */
public class BuilderParametro {
    
    public Parametro crearParametro(HttpServletRequest request, int num_parametro) {
        String tipo_param = request.getParameter("tipo_param_" + num_parametro);
        Parametro p = crearParametroEspecifico(tipo_param);
        if (tipo_param.equals("objeto_multiple")) {
            ObjetoMultiple p_ob = (ObjetoMultiple) p;
            p_ob.setTipo_objeto(request.getParameter("tipo_param_objeto_" + num_parametro));
            String[] valores = request.getParameterValues("val_param_" + num_parametro + "[]");
            p_ob.setValores(valores);
        } else {
            String valor = request.getParameter("val_param_" + num_parametro);
            p.setValor(valor);
        }
        return p;
    }
    
    private Parametro crearParametroEspecifico(String tipo) {
        Parametro p = null;
        switch(tipo) {
            case "numero":
                p = new Numero();
                break;
            case "fecha":
                p = new Fecha();
                break;
            case "objeto":
                p = new Objeto();
                break;
            case "objeto_multiple":
                p = new ObjetoMultiple();
                break;
        }
        
        return p;
    }
    
}
