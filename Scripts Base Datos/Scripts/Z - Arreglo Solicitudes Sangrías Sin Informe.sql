update 
    control_calidad.resultados_informes
set 
    id_resultado_sp = id_resultado
    , id_resultado = null
where 
    id_informe in
    (
        select
            -- distinct g.id_solicitud,
            id_informe
        from 
            control_calidad.resultados_analisis_sangrias_prueba r
            inner join control_calidad.analisis_grupo_solicitud ags 
                on ags.id_analisis_grupo_solicitud = r.id_ags
            inner join control_calidad.grupos g 
                on ags.id_grupo = g.id_grupo
            inner join control_calidad.informes ii 
                on g.id_solicitud = ii.id_solicitud 
        where 
                g.id_solicitud in (
                    select 
                        s1.id_solicitud 
                    from 
                        control_calidad.solicitudes s1
                        inner join control_calidad.informes i on i.id_solicitud = s1.id_solicitud
                        inner join control_calidad.resultados_informes ri on ri.id_informe = i.id_informe
                    where 
                        ri.id_resultado_sp is null)
    )
;
