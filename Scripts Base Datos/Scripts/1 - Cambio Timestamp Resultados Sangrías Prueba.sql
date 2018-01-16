/*

    Este primera línea es indispensable.

*/

ALTER TABLE control_calidad.resultados_analisis_sangrias_prueba
   ALTER COLUMN fecha_reportado TYPE timestamp without time zone;



-- En caso de fecha de cierre

-- Resultados Normales --
update control_calidad.resultados ru set fecha_reportado = (
	
	select max(case when s.fecha_cierre is null then r.fecha else s.fecha_cierre end)
	from control_calidad.resultados r
		inner join control_calidad.resultados_informes ri on ri.id_resultado = r.id_resultado
		inner join control_calidad.informes i on i.id_informe = ri.id_informe 
		inner join control_calidad.solicitudes s on s.id_solicitud = i.id_solicitud 
	where 
		r.id_resultado = ru.id_resultado

)
where fecha_reportado is null and id_resultado in (

	select r.id_resultado 
	from control_calidad.resultados r
		inner join control_calidad.resultados_informes ri on ri.id_resultado = r.id_resultado
		inner join control_calidad.informes i on i.id_informe = ri.id_informe 
		inner join control_calidad.solicitudes s on s.id_solicitud = i.id_solicitud 
	where 
		s.estado = 'Completada'

);

-- Resultados de Sangrías de Prueba -- 
update control_calidad.resultados_analisis_sangrias_prueba ru set fecha_reportado = (
	
	select max(case when s.fecha_cierre is null then r.fecha else s.fecha_cierre end)
	from control_calidad.resultados_analisis_sangrias_prueba r
		inner join control_calidad.resultados_informes ri on r.id_resultado_analisis_sp = ri.id_resultado_sp
		inner join control_calidad.informes i on i.id_informe = ri.id_informe 
		inner join control_calidad.solicitudes s on s.id_solicitud = i.id_solicitud 
	where 
		r.id_resultado_analisis_sp = ru.id_resultado_analisis_sp

)
where ru.fecha_reportado is null and ru.id_resultado_analisis_sp in (

	select r2.id_resultado_analisis_sp 
	from control_calidad.resultados_analisis_sangrias_prueba r2
		inner join control_calidad.resultados_informes ri2 on r2.id_resultado_analisis_sp = ri2.id_resultado_sp
		inner join control_calidad.informes i2 on i2.id_informe = ri2.id_informe 
		inner join control_calidad.solicitudes s2 on s2.id_solicitud = i2.id_solicitud 
	where 
		s2.estado = 'Completada'

);

-- En caso de fecha de resultado

update control_calidad.resultados set fecha_reportado = fecha;
update control_calidad.resultados_analisis_sangrias_prueba set fecha_reportado = fecha;
