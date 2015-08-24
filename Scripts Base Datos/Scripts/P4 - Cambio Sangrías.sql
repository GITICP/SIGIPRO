ALTER TABLE caballeriza.sangrias_caballos 
    DROP COLUMN lal_dia1, DROP COLUMN lal_dia2, DROP COLUMN lal_dia3;

ALTER TABLE caballeriza.sangrias_caballos
    ADD id_resultado_lal_dia1 integer, ADD id_resultado_lal_dia2 integer, ADD id_resultado_lal_dia3 integer;

ALTER TABLE caballeriza.sangrias_caballos
    ADD CONSTRAINT fk_id_resultado_lal_dia_1
    FOREIGN KEY (id_resultado_lal_dia1)
    REFERENCES control_calidad.resultados (id_resultado);

ALTER TABLE caballeriza.sangrias_caballos
    ADD CONSTRAINT fk_id_resultado_lal_dia_2
    FOREIGN KEY (id_resultado_lal_dia2)
    REFERENCES control_calidad.resultados (id_resultado);

ALTER TABLE caballeriza.sangrias_caballos
    ADD CONSTRAINT fk_id_resultado_lal_dia_3
    FOREIGN KEY (id_resultado_lal_dia3)
    REFERENCES control_calidad.resultados (id_resultado);

ALTER TABLE caballeriza.sangrias
    DROP COLUMN num_inf_cc;

ALTER TABLE caballeriza.sangrias
    ADD id_informe_dia1 integer, ADD id_informe_dia2 integer, ADD id_informe_dia3 integer;

ALTER TABLE caballeriza.sangrias
    ADD CONSTRAINT fk_id_informe_dia1
    FOREIGN KEY (id_informe_dia1)
    REFERENCES  control_calidad.informes (id_informe);

ALTER TABLE caballeriza.sangrias
    ADD CONSTRAINT fk_id_informe_dia2
    FOREIGN KEY (id_informe_dia2)
    REFERENCES  control_calidad.informes (id_informe);

ALTER TABLE caballeriza.sangrias
    ADD CONSTRAINT fk_id_informe_dia3
    FOREIGN KEY (id_informe_dia3)
    REFERENCES  control_calidad.informes (id_informe);