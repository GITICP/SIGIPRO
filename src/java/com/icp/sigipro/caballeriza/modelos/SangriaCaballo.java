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

    Sangria sangria;
    Caballo caballo;
    float hematocrito; //Este campo no está en la BD. Se creó por conveniencia para evitar anidación extra para las sangrías.
    float sangre_dia1;
    float plasma_dia1;
    float lal_dia1;
    float sangre_dia2;
    float plasma_dia2;
    float lal_dia2;
    float sangre_dia3;
    float plasma_dia3;
    float lal_dia3;

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
            resultado = lal_dia2 + plasma_dia2 + sangre_dia3;
        } else if (dia == 3) {
            resultado = lal_dia3 + plasma_dia3 + sangre_dia3;
        }
        return resultado;
    }
}
