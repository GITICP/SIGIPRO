/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.seguridad.modelos;

import java.util.Comparator;

/**
 *
 * @author Boga
 */
public class ComparadorItems implements Comparator<ItemMenu>
{
    @Override
    public int compare(ItemMenu i1, ItemMenu i2)
    {
        return i1.getOrden() - i2.getOrden();
    }
}
