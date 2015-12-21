
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
        id_resultado_hematocrito integer,
        id_resultado_hemoglobina integer 
    );

-- PKs

    ALTER TABLE ONLY caballeriza.sangrias_pruebas  ADD CONSTRAINT pk_sangrias_pruebas PRIMARY KEY (id_sangria_prueba);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos  ADD CONSTRAINT pk_sangrias_pruebas_caballos PRIMARY KEY (id_sangria_prueba,id_caballo); 

-- FKs

    ALTER TABLE ONLY caballeriza.sangrias_pruebas ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_sangria_prueba FOREIGN KEY (id_sangria_prueba) REFERENCES caballeriza.sangrias_pruebas(id_sangria_prueba);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_resultado_hematocrito FOREIGN KEY (id_resultado_hematrocito) REFERENCES control_calidad.resultados (id_resultado);
    ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_resultado_hemoglobina FOREIGN KEY (id_resultado_hemoglobina) REFERENCES control_calidad.resultados (id_resultado);

-- Permisos

    INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (59, '[Caballeriza]AgregarSangriaPrueba', 'Permite agregar una Sangría de Prueba');
    INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (60, '[Caballeriza]EditarSangriaPrueba', 'Permite editar una Sangría de Prueba');

-- Menú
    
    -- Ítem menú
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (406, 400, 'Pruebas Sangría', '/Caballeriza/SangriaPrueba');
    
    -- Asociaciones permisos
    INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (59, 406);
    INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (60, 406);