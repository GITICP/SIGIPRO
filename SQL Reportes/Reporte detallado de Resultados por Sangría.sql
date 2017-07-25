SELECT 
	sp.fecha as "Fecha Sangría", 
	i.fecha  as "Fecha Informe",
	s.numero_solicitud as "Número de Solicitud", 
	c.numero,
	c.nombre,
	rasp.wbc as "Glóbulos blancos (WBC)( x 1000/µL)",
	rasp.rbc as "Glóbulos Rojos (RBC)( x 1000000/µL)",
	rasp.hematocrito as "Hematocrito (HCT)%",
	rasp.hemoglobina as "Hemoglobina (HGB)g/dL",
	rasp.mcv as "Volumen corpuscular media (MCV )fL",
	rasp.mch as "Hemoglobina corpuscular Media (MCH) pg",
	rasp.mchc as "Conc HGB Corpuscular Media (MCHC) (g/dL)",
	rasp.plt as "Plaquetas (PLT)( x 1000/µL)",
	rasp.lym as "Linfocitos (LYM)%",
	rasp.otros as "Otros %",
	rasp.linfocitos as "Número de Linfocitos ( x 1000/µL)",
	rasp.num_otros as "Número de otros ( x 1000/µL)"
FROM caballeriza.sangrias_pruebas sp 
	INNER JOIN caballeriza.sangrias_pruebas_caballos csp ON sp.id_sangria_prueba = csp.id_sangria_prueba 
	INNER JOIN caballeriza.caballos c ON csp.id_caballo = c.id_caballo 
	INNER JOIN caballeriza.grupos_de_caballos gc ON gc.id_grupo_de_caballo = sp.id_grupo 
	LEFT JOIN control_calidad.resultados_analisis_sangrias_prueba rasp ON csp.id_resultado = rasp.id_resultado_analisis_sp 
	LEFT JOIN control_calidad.informes i ON i.id_informe = sp.id_informe 
	LEFT JOIN control_calidad.solicitudes s ON s.id_solicitud = i.id_solicitud 
where sp.id_sangria_prueba = ?;