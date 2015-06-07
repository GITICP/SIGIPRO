/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Boga
 */
public class Modulo extends ItemMenu implements java.io.Serializable
{

    private List<ItemMenu> items = new ArrayList<ItemMenu>();

    public Modulo()
    {
    }

    public List<ItemMenu> getItems()
    {
        return items;
    }

    public void setItems(List<ItemMenu> items)
    {
        this.items = items;
    }

    public boolean agregarItemMenu(ItemMenu item)
    {
        boolean resultado = false;
        if (id == item.getId_padre()) {
            items.add(item);
            resultado = true;
        }
        else {
            if (items != null) {
                for (ItemMenu item_iterable : items) {
                    if (item_iterable instanceof Modulo) {
                        Modulo mod = (Modulo) item_iterable;
                        resultado = mod.agregarItemMenu(item);
                    }
                }
            }
        }
        return resultado;
    }

    public boolean tieneContenido()
    {
        boolean tiene_sub_modulos = iterarSubModulos();
        boolean tiene_items = items.size() > 0;
        return tiene_items || tiene_sub_modulos;
    }

    public boolean iterarSubModulos()
    {
        List<ItemMenu> arreglo = new ArrayList<ItemMenu>();
        for (ItemMenu item : items) {
            if (item instanceof Modulo) {
                Modulo mod = (Modulo) item;
                if (mod.tieneContenido()) {
                    arreglo.add(mod);
                }
            }
            else {
                arreglo.add(item);
            }
        }
        items = arreglo;
        return items.size() > 0;
    }

    public void ordenar()
    {
        for (ItemMenu item : items) {
            if (item instanceof Modulo) {
                Modulo mod = (Modulo) item;
                mod.ordenar();
            }
        }
        Collections.sort(items, new ComparadorItems());
    }
}
