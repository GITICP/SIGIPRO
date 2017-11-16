

-- Solicitudes Normales
update CONTROL_CALIDAD.SOLICITUDES S SET FECHA_CIERRE = (
	select 
		min(cc_r.fecha) + interval '12 hours'
	from 
		control_calidad.solicitudes cc_s
		inner join control_calidad.informes cc_i ON cc_i.id_solicitud = cc_s.id_solicitud
		inner join control_calidad.resultados_informes cc_ri ON cc_ri.id_informe = cc_i.id_informe
		inner join control_calidad.resultados cc_r ON cc_r.id_resultado = cc_ri.id_resultado
	where 
		fecha_cierre is null 
		and estado = 'Completada'
		and cc_s.id_solicitud = s.id_solicitud
	group by cc_s.id_solicitud
	order by cc_s.id_solicitud
)
where 
	id_solicitud in (
		select 
			distinct cc_s.id_solicitud
		from 
			control_calidad.solicitudes cc_s
			inner join control_calidad.informes cc_i ON cc_i.id_solicitud = cc_s.id_solicitud
			inner join control_calidad.resultados_informes cc_ri ON cc_ri.id_informe = cc_i.id_informe
			inner join control_calidad.resultados cc_r ON cc_r.id_resultado = cc_ri.id_resultado
		where 
			fecha_cierre is null 
			and estado = 'Completada'
	);


-- Solicitudes Sangría Prueba
update CONTROL_CALIDAD.SOLICITUDES S SET FECHA_CIERRE = (
	select 
		min(cc_rasp.fecha) + interval '12 hours'
	from 
		control_calidad.solicitudes cc_s
		inner join control_calidad.informes cc_i ON cc_i.id_solicitud = cc_s.id_solicitud
		inner join control_calidad.resultados_informes cc_ri ON cc_ri.id_informe = cc_i.id_informe
		inner join control_calidad.resultados_analisis_sangrias_prueba cc_rasp ON cc_rasp.id_resultado_analisis_sp = cc_ri.id_resultado_sp
	where 
		fecha_cierre is null 
		and estado = 'Completada'
		and s.id_solicitud = cc_s.id_solicitud
	group by cc_s.id_solicitud )
where 
	id_solicitud in (
		select 
			distinct cc_s.id_solicitud
		from 
			control_calidad.solicitudes cc_s
			inner join control_calidad.informes cc_i ON cc_i.id_solicitud = cc_s.id_solicitud
			inner join control_calidad.resultados_informes cc_ri ON cc_ri.id_informe = cc_i.id_informe
			inner join control_calidad.resultados_analisis_sangrias_prueba cc_rasp ON cc_rasp.id_resultado_analisis_sp = cc_ri.id_resultado_sp
		where 
			fecha_cierre is null 
			and estado = 'Completada'
	);
