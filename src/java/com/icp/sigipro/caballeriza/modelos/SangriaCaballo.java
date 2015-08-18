/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.caballeriza.modelos;

/**
 *
 * @author Boga
 */
public class SangriaCaballo
{

    private Sangria sangria;
    private Caballo caballo;
    private float hematocrito; //Este campo no está en la BD. Se creó por conveniencia para evitar anidación extra para las sangrías.
    private Boolean participo_dia1;
    private float sangre_dia1;
    private float plasma_dia1;
    private float lal_dia1;
    private Boolean participo_dia2;
    private float sangre_dia2;
    private float plasma_dia2;
    private float lal_dia2;
    private Boolean participo_dia3;
    private float sangre_dia3;
    private float plasma_dia3;
    private float lal_dia3;
    
    private String observaciones_dia1; //Esta línea fue un cambio
    private String observaciones_dia2; //Esta línea fue un cambio
    private String observaciones_dia3; //Esta línea fue un cambio

    public SangriaCaballo()
    {

    }

    public Sangria getSangria()
    {
        return sangria;
    }

    public void setSangria(Sangria sangria)
    {
        this.sangria = sangria;
    }

    public Caballo getCaballo()
    {
        return caballo;
    }

    public void setCaballo(Caballo caballo)
    {
        this.caballo = caballo;
    }

    public Boolean isParticipo_dia1()
    {
        return participo_dia1;
    }

    public void setParticipo_dia1(Boolean participo_dia1)
    {
        this.participo_dia1 = participo_dia1;
    }

    public float getSangre_dia1()
    {
        return sangre_dia1;
    }

    public void setSangre_dia1(float sangre_dia1)
    {
        this.sangre_dia1 = sangre_dia1;
    }

    public float getPlasma_dia1()
    {
        return plasma_dia1;
    }

    public void setPlasma_dia1(float plasma_dia1)
    {
        this.plasma_dia1 = plasma_dia1;
    }

    public float getLal_dia1()
    {
        return lal_dia1;
    }

    public void setLal_dia1(float lal_dia1)
    {
        this.lal_dia1 = lal_dia1;
    }

    public Boolean isParticipo_dia2()
    {
        return participo_dia2;
    }

    public void setParticipo_dia2(Boolean participo_dia2)
    {
        this.participo_dia2 = participo_dia2;
    }

    public float getSangre_dia2()
    {
        return sangre_dia2;
    }

    public void setSangre_dia2(float sangre_dia2)
    {
        this.sangre_dia2 = sangre_dia2;
    }

    public float getPlasma_dia2()
    {
        return plasma_dia2;
    }

    public void setPlasma_dia2(float plasma_dia2)
    {
        this.plasma_dia2 = plasma_dia2;
    }

    public float getLal_dia2()
    {
        return lal_dia2;
    }

    public void setLal_dia2(float lal_dia2)
    {
        this.lal_dia2 = lal_dia2;
    }

    public Boolean isParticipo_dia3()
    {
        return participo_dia3;
    }

    public void setParticipo_dia3(Boolean participo_dia3)
    {
        this.participo_dia3 = participo_dia3;
    }

    public float getSangre_dia3()
    {
        return sangre_dia3;
    }

    public void setSangre_dia3(float sangre_dia3)
    {
        this.sangre_dia3 = sangre_dia3;
    }

    public float getPlasma_dia3()
    {
        return plasma_dia3;
    }

    public void setPlasma_dia3(float plasma_dia3)
    {
        this.plasma_dia3 = plasma_dia3;
    }

    public float getLal_dia3()
    {
        return lal_dia3;
    }

    public void setLal_dia3(float lal_dia3)
    {
        this.lal_dia3 = lal_dia3;
    }

    public float getHematocrito()
    {
        return hematocrito;
    }

    public void setHematocrito(float hematocrito)
    {
        this.hematocrito = hematocrito;
    }
    
    public Boolean getParticipo(int dia) {
        Boolean resultado = false;
        
        if (dia == 0) {
            resultado = true;
        } else if (dia == 1) {
            resultado = participo_dia1;
        } else if (dia == 2) {
            resultado = participo_dia2;
        } else if (dia == 3) {
            resultado = participo_dia3;
        }
        return resultado;
    }
    
    public float getSangre(int dia) {
        float resultado = 0;
        if (dia == 1) {
            resultado = sangre_dia1;
        } else if (dia == 2) {
            resultado = sangre_dia2;
        } else if (dia == 3) {
            resultado = sangre_dia3;
        }
        return resultado;
    }
    
    public float getPlasma(int dia) {
        float resultado = 0;
        if (dia == 1) {
            resultado = plasma_dia1;
        } else if (dia == 2) {
            resultado = plasma_dia2;
        } else if (dia == 3) {
            resultado = plasma_dia3;
        }
        return resultado;
    }
    
    public float getLal(int dia) {
        float resultado = 0;
        if (dia == 1) {
            resultado = lal_dia1;
        } else if (dia == 2) {
            resultado = lal_dia2;
        } else if (dia == 3) {
            resultado = lal_dia3;
        }
        return resultado;
    }
    
    public void setSangre(int dia, float valor) {
        if (dia == 1) {
            sangre_dia1 = valor;
        } else if (dia == 2) {
            sangre_dia2 = valor;
        } else if (dia == 3) {
            sangre_dia3 = valor;
        }
    }
    
    public void setPlasma(int dia, float valor) {
        if (dia == 1) {
            plasma_dia1 = valor;
        } else if (dia == 2) {
            plasma_dia2 = valor;
        } else if (dia == 3) {
            plasma_dia3 = valor;
        }
    }
    
    public void setLal(int dia, float valor) {
        if (dia == 1) {
            lal_dia1 = valor;
        } else if (dia == 2) {
            lal_dia2 = valor;
        } else if (dia == 3) {
            lal_dia3 = valor;
        }
    }
    
    public float sumatoria(int dia) {
        float resultado = 0;
        if (dia == 1) {
            resultado = lal_dia1 + plasma_dia1 + sangre_dia1;
        } else if (dia == 2) {
            resultado = lal_dia2 + plasma_dia2 + sangre_dia2;
        } else if (dia == 3) {
            resultado = lal_dia3 + plasma_dia3 + sangre_dia3;
        }
        return resultado;
    }
    
    /* Código Nuevo */
    public String getObservaciones(int dia) {
        String resultado = "Sin observaciones.";
        if (dia == 1) {
            resultado = observaciones_dia1;
        } else if (dia == 2) {
            resultado = observaciones_dia2;
        } else if (dia == 3) {
            resultado = observaciones_dia3;
        }
        return resultado;
    }
    
    public void setObservaciones(int dia, String valor) {
        if (valor == null) {
            valor = "Sin observaciones.";
        } else {
            if (valor.isEmpty()) {
                valor = "Sin observaciones.";
            }
        }
        if (dia == 1) {
            observaciones_dia1 = valor;
        } else if (dia == 2) {
            observaciones_dia2 = valor;
        } else if (dia == 3) {
            observaciones_dia3 = valor;
        }
    }

    public String getObservaciones_dia1() {
        return observaciones_dia1;
    }

    public void setObservaciones_dia1(String observaciones_dia1) {
        this.observaciones_dia1 = observaciones_dia1;
    }

    public String getObservaciones_dia2() {
        return observaciones_dia2;
    }

    public void setObservaciones_dia2(String observaciones_dia2) {
        this.observaciones_dia2 = observaciones_dia2;
    }

    public String getObservaciones_dia3() {
        return observaciones_dia3;
    }

    public void setObservaciones_dia3(String observaciones_dia3) {
        this.observaciones_dia3 = observaciones_dia3;
    }
    
    public boolean tieneObservaciones() {
        boolean dia_1 = false;
        boolean dia_2 = false;
        boolean dia_3 = false;
        if (observaciones_dia1 != null) {
            if (!observaciones_dia1.equals("Sin observaciones.")) {
                dia_1 = true;
            }
        }
        if (observaciones_dia2 != null) {
            if (!observaciones_dia2.equals("Sin observaciones.")) {
                dia_1 = true;
            }
        }
        if (observaciones_dia3 != null) {
            if (!observaciones_dia3.equals("Sin observaciones.")) {
                dia_1 = true;
            }
        }
        return dia_1 || dia_2 || dia_3;
    }
    
    /* Código Nuevo */
}
