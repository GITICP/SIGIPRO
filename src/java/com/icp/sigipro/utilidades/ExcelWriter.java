/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import java.io.IOException;
import java.sql.Date;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Boga
 */
public class ExcelWriter {
    
    XSSFWorkbook archivo;
    private XSSFSheet hoja;
    
    private Row fila_actual;
    private short contador_fila = 0;
    private short contador_columna = 0;
    
    //private CellReference celda_inicial;
    //private String columna_inicial;
    //private int fila_inicial;
    
    
    public ExcelWriter(){
        archivo = new XSSFWorkbook();
        hoja = archivo.createSheet("Datos");
    }
    
    /*
    public ExcelWriter(String columna_inicial, int fila_inicial) {
        archivo = new XSSFWorkbook();
        hoja = archivo.createSheet("Datos");
        
        this.columna_inicial = columna_inicial;
        this.fila_inicial = fila_inicial;
        
        String celda_por_buscar = columna_inicial.toUpperCase() + String.valueOf(fila_inicial);
        celda_inicial = new CellReference(celda_por_buscar);
    }*/
    
    public XSSFWorkbook getArchivo() {
        return archivo;
    }
    
    public void cerrarArchivo() {
        try{
            archivo.close();
        } catch(IOException io) {
            io.printStackTrace();
        }
    }

    public void comenzarFila() {
        this.fila_actual = hoja.createRow(contador_fila);
    }
    
    public void terminarFila() {
        contador_fila++;
        contador_columna = 0;
        this.fila_actual = hoja.createRow(contador_fila);
    }
    
    public void agregarString(String valor) {
        Cell celda = crearCelda();
        celda.setCellValue(valor);
    }
    
    public void agregarEntero(int valor) {
        Cell celda = crearCelda();
        celda.setCellValue(valor);
    }
    
    public void agregarFecha(Date fecha) {
        Cell celda = crearCelda();
        celda.setCellValue(fecha);
    }
    
    public void agregarDouble(double valor) {
        Cell celda = crearCelda();
        celda.setCellValue(valor);
    }
    
    
    
    private Cell crearCelda() {
        Cell celda = this.fila_actual.createCell(contador_columna);
        contador_columna++;
        return celda;
    }
    
}
