/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.icp.sigipro.utilidades;

import com.icp.sigipro.core.SIGIPROException;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Boga
 */
public class HelperExcel
{
    XSSFWorkbook archivo_excel;
    XSSFSheet hoja;
    
    public HelperExcel(){}
    
    public HelperExcel(String direccion_archivo) throws SIGIPROException {
        try {
            archivo_excel = new XSSFWorkbook(new FileInputStream(direccion_archivo));
            hoja = archivo_excel.getSheetAt(0);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new SIGIPROException("Error al obtener el archivo. Notifique al administrador del sistema.");
        }
    }
    
    public String obtenerCelda(String celda_por_buscar) {
        Cell celda = null;
        CellReference referencia  = new CellReference(celda_por_buscar);
        Row r = hoja.getRow(referencia.getRow());
        if (r!=null) {
            celda = r.getCell(referencia.getCol());
        }
        if (celda != null) {
            return celda.getStringCellValue();
        } else {
            return "Celda no encontrada. Verifique que el análisis esté configurado de acuerdo con su machote.";
        }
        
    }
}
