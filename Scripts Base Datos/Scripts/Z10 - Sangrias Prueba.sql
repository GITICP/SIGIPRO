
/*

    Sangrías de Prueba

*/

-- Tablas --

    CREATE TABLE caballeriza.sangrias_pruebas (
        id_sangria_prueba serial NOT NULL,
        id_informe integer,
        id_usuario integer NOT NULL,
        fecha date NOT NULL
    );

    CREATE TABLE caballeriza.sangrias_pruebas_caballos (
        id_sangria_prueba integer NOT NULL,
        id_caballo integer NOT NULL,
        id_resultado integer
    );

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
    ALTER TABLE control_calidad.resultados_informes
        ADD CONSTRAINT fk_informes_resultados_resultados_sp FOREIGN KEY (id_resultado_sp) REFERENCES control_calidad.resultados_analisis_sangrias_prueba (id_resultado_analisis_sp) ON UPDATE NO ACTION ON DELETE NO ACTION;

    -- Análisis

    INSERT INTO control_calidad.analisis(id_analisis,estructura,machote,nombre,estado) VALUES (2147483647,null,'','Por Definir','Aprobado');

    CREATE TABLE control_calidad.resultados_analisis_sangrias_prueba(
        id_resultado_analisis_sp serial NOT NULL,
        id_ags integer NOT NULL,
        WBC character varying(200),
        RBC character varying(200),
        hematocrito decimal NOT NULL,
        hemoglobina decimal NOT NULL,
        repeticion integer NOT NULL,
        fecha date,
        id_usuario integer NOT NULL
    );

-- PKs

    ALTER TABLE ONLY control_calidad.resultados_analisis_sangrias_prueba ADD CONSTRAINT pk_resultados_analisis_sp PRIMARY KEY (id_resultado_analisis_sp);

    ALTER TABLE ONLY caballeriza.sangrias_pruebas  ADD CONSTRAINT pk_sangrias_pruebas PRIMARY KEY (id_sangria_prueba);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos  ADD CONSTRAINT pk_sangrias_pruebas_caballos PRIMARY KEY (id_sangria_prueba,id_caballo); 

-- FKs

    ALTER TABLE ONLY control_calidad.resultados_analisis_sangrias_prueba ADD CONSTRAINT fk_resultados_sp_ags FOREIGN KEY (id_ags) REFERENCES control_calidad.analisis_grupo_solicitud(id_analisis_grupo_solicitud);
    ALTER TABLE ONLY control_calidad.resultados_analisis_sangrias_prueba ADD CONSTRAINT fk_resultados_sp_usuarios FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios (id_usuario);

    ALTER TABLE ONLY caballeriza.sangrias_pruebas ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_sangria_prueba FOREIGN KEY (id_sangria_prueba) REFERENCES caballeriza.sangrias_pruebas(id_sangria_prueba);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_resultado_hematocrito FOREIGN KEY (id_resultado_hematrocito) REFERENCES control_calidad.resultados_analisis_sangrias_prueba (id_resultado_analisis_sp-);

-- Permisos

    INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (59, '[Caballeriza]AgregarSangriaPrueba', 'Permite agregar una Sangría de Prueba');
    INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (60, '[Caballeriza]EditarSangriaPrueba', 'Permite editar una Sangría de Prueba');

-- Menú
    
    -- Ítem menú
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (406, 400, 'Pruebas Sangría', '/Caballeriza/SangriaPrueba');
    
    -- Asociaciones permisos
    INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (59, 406);
    INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (60, 406);


CREATE FUNCTION control_calidad.consecutivo_repeticiones_resultados_sp() RETURNS TRIGGER as $numero_repeticion$

    BEGIN
        NEW.repeticion = (SELECT count(id_resultado_analisis_sp) FROM control_calidad.resultados_analisis_sangrias_prueba where id_ags = NEW.id_ags);
        RETURN NEW;
    END;
$numero_repeticion$ LANGUAGE plpgsql;

CREATE TRIGGER actualizacion_repeticion BEFORE INSERT ON control_calidad.resultados_analisis_sangrias_prueba FOR EACH ROW EXECUTE PROCEDURE control_calidad.consecutivo_repeticiones_resultados_sp();

