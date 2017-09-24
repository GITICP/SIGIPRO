/**
 * Author:  Boga
 * Created: 23.09.2017
 * 
 * Archivo de cambios en la BD de acuerdo con los cambios finales de Control de Calidad previo al cierre del año fiscal 16-17.
 * 
 */

ALTER TABLE control_calidad.resultados_analisis_sangrias_prueba ADD fecha_reportado date NULL ;
ALTER TABLE control_calidad.resultados ADD fecha_reportado date NULL ;



