
/*

    Sangrías de Prueba

*/

-- Tablas --

    DROP TABLE IF EXISTS caballeriza.sangrias_pruebas_caballos;
    DROP TABLE IF EXISTS caballeriza.sangrias_pruebas;
    
    CREATE TABLE caballeriza.sangrias_pruebas (
        id_sangria_prueba serial NOT NULL,
        id_informe integer,
        id_usuario integer NOT NULL,
        id_grupo integer NOT NULL,
        fecha date NOT NULL
    );

    CREATE TABLE caballeriza.sangrias_pruebas_caballos (
        id_sangria_prueba integer NOT NULL,
        id_caballo integer NOT NULL,
        id_resultado integer
    );

    -- Análisis

    INSERT INTO control_calidad.analisis(id_analisis,estructura,machote,nombre,estado) VALUES (2147483647,null,'','Análisis Hematológico','Aprobado');

    INSERT INTO control_calidad.tipos_reactivos_analisis(id_analisis,id_tipo_reactivo) VALUES (2147483647, 11);
    INSERT INTO control_calidad.tipos_reactivos_analisis(id_analisis,id_tipo_reactivo) VALUES (2147483647, 10);
    INSERT INTO control_calidad.tipos_reactivos_analisis(id_analisis,id_tipo_reactivo) VALUES (2147483647, 78);

    INSERT INTO control_calidad.tipos_muestras_analisis(id_analisis,id_tipo_muestra) VALUES (2147483647, 90);
    INSERT INTO control_calidad.tipos_muestras_analisis(id_analisis,id_tipo_muestra) VALUES (2147483647, 31);
    INSERT INTO control_calidad.tipos_muestras_analisis(id_analisis,id_tipo_muestra) VALUES (2147483647, 32);
    INSERT INTO control_calidad.tipos_muestras_analisis(id_analisis,id_tipo_muestra) VALUES (2147483647, 91);
    INSERT INTO control_calidad.tipos_muestras_analisis(id_analisis,id_tipo_muestra) VALUES (2147483647, 34);
    INSERT INTO control_calidad.tipos_muestras_analisis(id_analisis,id_tipo_muestra) VALUES (2147483647, 33);

    INSERT INTO control_calidad.tipos_equipos_analisis(id_analisis,id_tipo_equipo) VALUES (2147483647, 28);
    INSERT INTO control_calidad.tipos_equipos_analisis(id_analisis,id_tipo_equipo) VALUES (2147483647, 30);
    INSERT INTO control_calidad.tipos_equipos_analisis(id_analisis,id_tipo_equipo) VALUES (2147483647, 25);

    CREATE TABLE control_calidad.resultados_analisis_sangrias_prueba(
        id_resultado_analisis_sp serial NOT NULL,
        id_ags integer NOT NULL,
        WBC decimal,
        RBC decimal,
        hematocrito decimal NOT NULL,
        hemoglobina decimal NOT NULL,
        mcv decimal,
        mch decimal,
        mchc decimal,
        plt decimal,
        lym decimal,
        otros decimal,
        linfocitos decimal,
        num_otros decimal,
        repeticion integer NOT NULL,
        fecha date,
        id_usuario integer NOT NULL,
        observaciones character varying(500)
    );

    CREATE TABLE control_calidad.equipos_resultado_sp (
      id_resultado_sp integer NOT NULL,
      id_equipo integer NOT NULL,
      CONSTRAINT equipos_resultado_sp_pk PRIMARY KEY (id_resultado_sp, id_equipo),
      CONSTRAINT equipos_resultado_resultados_sp FOREIGN KEY (id_resultado_sp)
          REFERENCES control_calidad.resultados_analisis_sangrias_prueba (id_resultado_analisis_sp) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION
    );

    CREATE TABLE control_calidad.patrones_resultados_sp (
      id_patron integer NOT NULL,
      id_resultado_sp integer NOT NULL,
      CONSTRAINT pk_patrones_resultados_sp PRIMARY KEY (id_patron, id_resultado_sp),
      CONSTRAINT fk_patrones_resultados_sp_patrones FOREIGN KEY (id_patron)
          REFERENCES control_calidad.patrones (id_patron) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION,
      CONSTRAINT fk_patrones_resultados_resultados_sp FOREIGN KEY (id_resultado_sp)
          REFERENCES control_calidad.resultados_analisis_sangrias_prueba (id_resultado_analisis_sp) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION
    );

    CREATE TABLE control_calidad.reactivos_resultado_sp (
      id_resultado_sp integer NOT NULL,
      id_reactivo integer NOT NULL,
      CONSTRAINT reactivos_resultado_sp_pk PRIMARY KEY (id_resultado_sp, id_reactivo),
      CONSTRAINT reacivos_resultado_resultados_sp FOREIGN KEY (id_resultado_sp)
          REFERENCES control_calidad.resultados_analisis_sangrias_prueba (id_resultado_analisis_sp) MATCH SIMPLE
          ON UPDATE NO ACTION ON DELETE NO ACTION
    );

-- Cambios a otras tablas

    ALTER TABLE control_calidad.resultados_informes
        ADD COLUMN id_resultado_informe serial NOT NULL;
    ALTER TABLE control_calidad.resultados_informes
        DROP CONSTRAINT pk_resultaods;
    ALTER TABLE control_calidad.resultados_informes
        ADD CONSTRAINT pk_resultado_informe PRIMARY KEY (id_resultado_informe);
    ALTER TABLE control_calidad.resultados_informes
        ADD COLUMN id_resultado_sp integer;
    ALTER TABLE control_calidad.resultados_informes
        ALTER COLUMN id_resultado DROP NOT NULL;

-- PKs

    ALTER TABLE ONLY control_calidad.resultados_analisis_sangrias_prueba ADD CONSTRAINT pk_resultados_analisis_sp PRIMARY KEY (id_resultado_analisis_sp);

    ALTER TABLE ONLY caballeriza.sangrias_pruebas  ADD CONSTRAINT pk_sangrias_pruebas PRIMARY KEY (id_sangria_prueba);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos  ADD CONSTRAINT pk_sangrias_pruebas_caballos PRIMARY KEY (id_sangria_prueba,id_caballo); 

-- FKs

    ALTER TABLE control_calidad.resultados_informes
        ADD CONSTRAINT fk_informes_resultados_resultados_sp FOREIGN KEY (id_resultado_sp) REFERENCES control_calidad.resultados_analisis_sangrias_prueba (id_resultado_analisis_sp) ON UPDATE NO ACTION ON DELETE NO ACTION;

    ALTER TABLE ONLY control_calidad.resultados_analisis_sangrias_prueba ADD CONSTRAINT fk_resultados_sp_ags FOREIGN KEY (id_ags) REFERENCES control_calidad.analisis_grupo_solicitud(id_analisis_grupo_solicitud);
    ALTER TABLE ONLY control_calidad.resultados_analisis_sangrias_prueba ADD CONSTRAINT fk_resultados_sp_usuarios FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios (id_usuario);

    ALTER TABLE ONLY caballeriza.sangrias_pruebas ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas ADD CONSTRAINT fk_id_grupo FOREIGN KEY (id_grupo) REFERENCES caballeriza.grupos_de_caballos (id_grupo_de_caballo);

    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_sangria_prueba FOREIGN KEY (id_sangria_prueba) REFERENCES caballeriza.sangrias_pruebas(id_sangria_prueba);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_resultado FOREIGN KEY (id_resultado) REFERENCES control_calidad.resultados_analisis_sangrias_prueba (id_resultado_analisis_sp);

-- Permisos

    INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) 
	SELECT 59, '[Caballeriza]AgregarSangriaPrueba', 'Permite agregar una Sangría de Prueba'
	WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 59);
	
    INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) 
	SELECT 60, '[Caballeriza]EditarSangriaPrueba', 'Permite editar una Sangría de Prueba'
	WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 60);

-- Menú
    
    -- Ítem menú
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) 
	SELECT 406, 400, 'Pruebas Sangría', '/Caballeriza/SangriaPrueba'
	WHERE NOT EXISTS (SELECT 1 FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 406);
    
    -- Asociaciones permisos
    INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal)
	SELECT 59, 406
	WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 59 AND id_menu_principal = 406);
	
    INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) 
	SELECT 60, 406
	WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 60 AND id_menu_principal = 406);

CREATE FUNCTION control_calidad.consecutivo_repeticiones_resultados_sp() RETURNS TRIGGER as $numero_repeticion$

    BEGIN
        NEW.repeticion = (SELECT count(id_resultado_analisis_sp) FROM control_calidad.resultados_analisis_sangrias_prueba where id_ags = NEW.id_ags);
        RETURN NEW;
    END;
$numero_repeticion$ LANGUAGE plpgsql;

CREATE TRIGGER actualizacion_repeticion BEFORE INSERT ON control_calidad.resultados_analisis_sangrias_prueba FOR EACH ROW EXECUTE PROCEDURE control_calidad.consecutivo_repeticiones_resultados_sp();

