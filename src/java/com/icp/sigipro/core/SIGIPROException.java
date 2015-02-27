/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.core;

/**
 *
 * @author Boga
 */
public class SIGIPROException extends Exception
{

  public SIGIPROException() {  }
  public SIGIPROException(String mensaje)
  {
    super(mensaje);
  }

}
