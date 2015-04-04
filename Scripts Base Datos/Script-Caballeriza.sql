--########ESQUEMA DE CABALLERIZA########
DROP SCHEMA IF EXISTS caballeriza CASCADE;
CREATE SCHEMA caballeriza;
--Tablas de esquema de caballeriza

CREATE TABLE caballeriza.grupos_de_caballos (
    id_grupo_de_caballo serial NOT NULL,
    nombre character varying(45) NOT NULL,
    descripcion character varying(500)
);

--CREATE TABLE caballeriza.grupos_caballos (
--    id_grupo_de_caballo integer NOT NULL,
--    id_caballo integer NOT NULL
--);

CREATE TABLE caballeriza.caballos (
    id_caballo serial NOT NULL,
    nombre character varying(100) NOT NULL,
    numero_microchip integer NOT NULL,
    fecha_nacimiento Date NOT NULL,
    fecha_ingreso Date NOT NULL,
    sexo character varying(45) NOT NULL,
    color character varying(45) NOT NULL,
    otras_sennas character varying(500),
    fotografia bytea,
    estado character varying(45) NOT NULL,
    id_grupo_de_caballo int
);

CREATE TABLE caballeriza.eventos_clinicos(
    id_evento serial NOT NULL,
    fecha Date NOT NULL,
    descripcion character varying(500) NOT NULL,
    responsable integer,
    id_tipo_evento int NOT NULL
);
CREATE TABLE caballeriza.tipos_eventos (
    id_tipo_evento serial NOT NULL,
    nombre character varying(45),
    descripcion character varying(200)
);
-- CREATE TABLE caballeriza.eventos_grupos_caballos (
--    id_evento integer NOT NULL,
--    id_grupo_de_caballo integer NOT NULL
--);
CREATE TABLE caballeriza.eventos_clinicos_caballos (
    id_evento integer NOT NULL,
    id_caballo integer NOT NULL
);
CREATE TABLE caballeriza.inoculos (
    id_inoculo serial NOT NULL,
    mnn character varying(45) NOT NULL,
    baa character varying(45) NOT NULL,
    bap character varying(45) NOT NULL,
    cdd character varying(45) NOT NULL,
    lms character varying(45) NOT NULL,
    tetox character varying(45) NOT NULL,
    otro character varying(45) NOT NULL,
    encargado_preparacion character varying(100) NOT NULL,
    encargado_inyeccion character varying(100) NOT NULL,
    fecha Date NOT NULL,
    grupo_de_caballos INT NOT NULL
);

CREATE TABLE caballeriza.inoculos_caballos (
    id_inoculo integer NOT NULL,
    id_caballo integer NOT NULL
);

CREATE TABLE caballeriza.sangrias_pruebas (
    id_sangria_prueba serial NOT NULL,
    muestra character varying(45) NOT NULL,
    num_solicitud integer,
    num_informe integer,
    fecha_recepcion_muestra Date NOT NULL,
    fecha_informe Date,
    responsable character varying(45) NOT NULL,
    id_inoculo integer not null
);


CREATE TABLE caballeriza.sangrias_pruebas_caballos (
    id_sangria_prueba integer NOT NULL,
    id_caballo integer NOT NULL,
    hematrocito decimal,
    hemoglobina decimal 
    
);

CREATE TABLE caballeriza.sangrias (
    id_sangria integer not null,
    id_sangria_prueba integer not null,
    fecha_dia1 date,
    fecha_dia2 date,
    fecha_dia3 date,
    hematrocito_promedio decimal,
    num_inf_cc integer,
    responsable character varying(45) NOT NULL,
    cantidad_de_caballos integer,
    sangre_total decimal,
    peso_plasma_total decimal,
    volumen_plasma_total decimal,
    plasma_por_caballo decimal,
    potencia decimal
    
);
CREATE TABLE caballeriza.sangrias_caballos (
    id_sangria integer NOT NULL,
    id_caballo integer NOT NULL,
    sangre_dia1 decimal,
    plasma_dia1 decimal,
    LAL_dia1 decimal,
    sangre_dia2 decimal,
    plasma_dia2 decimal,
    LAL_dia2 decimal,
    sangre_dia3 decimal,
    plasma_dia3 decimal,
    LAL_dia3 decimal 
);

--Llaves primarias esquema caballeriza
ALTER TABLE ONLY caballeriza.grupos_de_caballos  ADD CONSTRAINT pk_grupos_de_caballos PRIMARY KEY (id_grupo_de_caballo);
ALTER TABLE ONLY caballeriza.caballos  ADD CONSTRAINT pk_caballos PRIMARY KEY (id_caballo);
ALTER TABLE ONLY caballeriza.inoculos  ADD CONSTRAINT pk_inoculos PRIMARY KEY (id_inoculo);
ALTER TABLE ONLY caballeriza.eventos_clinicos  ADD CONSTRAINT pk_eventos_clinicos PRIMARY KEY (id_evento);
ALTER TABLE ONLY caballeriza.inoculos_caballos  ADD CONSTRAINT pk_inoculos_caballos PRIMARY KEY (id_caballo,id_inoculo);
ALTER TABLE ONLY caballeriza.eventos_clinicos_caballos  ADD CONSTRAINT pk_eventos_caballos PRIMARY KEY (id_evento,id_caballo);
ALTER TABLE ONLY caballeriza.tipos_eventos  ADD CONSTRAINT pk_tipos_eventos PRIMARY KEY (id_tipo_evento);
ALTER TABLE ONLY caballeriza.sangrias_pruebas  ADD CONSTRAINT pk_sangrias_pruebas PRIMARY KEY (id_sangria_prueba);
ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos  ADD CONSTRAINT pk_sangrias_pruebas_caballos PRIMARY KEY (id_sangria_prueba,id_caballo);
ALTER TABLE ONLY caballeriza.sangrias  ADD CONSTRAINT pk_sangrias PRIMARY KEY (id_sangria);
ALTER TABLE ONLY caballeriza.sangrias_caballos  ADD CONSTRAINT pk_sangrias_caballos PRIMARY KEY (id_sangria,id_caballo);


--Indices unicos esquema caballeriza
CREATE UNIQUE INDEX i_nombre ON caballeriza.grupos_de_caballos USING btree (nombre);
CREATE UNIQUE INDEX i_numero_microchip ON caballeriza.caballos USING btree (numero_microchip);
CREATE UNIQUE INDEX i_muestra ON caballeriza.sangrias_pruebas USING btree (muestra);


--Llaves foraneas esquema caballeriza
ALTER TABLE ONLY caballeriza.caballos ADD CONSTRAINT fk_id_grupo_caballo FOREIGN KEY (id_grupo_de_caballo) REFERENCES caballeriza.grupos_de_caballos(id_grupo_de_caballo);
ALTER TABLE ONLY caballeriza.eventos_clinicos ADD CONSTRAINT fk_responsable FOREIGN KEY (responsable) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY caballeriza.eventos_clinicos_caballos ADD CONSTRAINT fk_id_evento FOREIGN KEY (id_evento) REFERENCES caballeriza.eventos_clinicos(id_evento);
ALTER TABLE ONLY caballeriza.eventos_clinicos_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
ALTER TABLE ONLY caballeriza.eventos_clinicos ADD CONSTRAINT fk_id_tipo_evento FOREIGN KEY (id_tipo_evento) REFERENCES caballeriza.tipos_eventos(id_tipo_evento);
ALTER TABLE ONLY caballeriza.inoculos_caballos ADD CONSTRAINT fk_id_inoculo FOREIGN KEY (id_inoculo) REFERENCES caballeriza.inoculos(id_inoculo);
ALTER TABLE ONLY caballeriza.inoculos_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
ALTER TABLE ONLY caballeriza.inoculos ADD CONSTRAINT fk_grupo_de_caballos FOREIGN KEY (grupo_de_caballos) REFERENCES caballeriza.grupos_de_caballos(id_grupo_de_caballo);
ALTER TABLE ONLY caballeriza.sangrias_pruebas ADD CONSTRAINT fk_inoculo FOREIGN KEY (id_inoculo) REFERENCES caballeriza.inoculos(id_inoculo);
ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_sangria_prueba FOREIGN KEY (id_sangria_prueba) REFERENCES caballeriza.sangrias_pruebas(id_sangria_prueba);
ALTER TABLE ONLY caballeriza.sangrias_pruebas_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
ALTER TABLE ONLY caballeriza.sangrias ADD CONSTRAINT fk_id_sangria_prueba FOREIGN KEY (id_sangria_prueba) REFERENCES caballeriza.sangrias_pruebas(id_sangria_prueba);
ALTER TABLE ONLY caballeriza.sangrias_caballos ADD CONSTRAINT fk_id_sangria FOREIGN KEY (id_sangria) REFERENCES caballeriza.sangrias(id_sangria);
ALTER TABLE ONLY caballeriza.sangrias_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);

--Permisos

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (46, '[Caballeriza]AgregarTipoEvento', 'Permite agregar una TipoEvento al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (47, '[Caballeriza]EliminarTipoEvento', 'Permite eliminar una TipoEvento al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (48, '[Caballeriza]EditarTipoEvento', 'Permite editar una TipoEvento al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (49, '[Caballeriza]AgregarCaballo', 'Permite agregar una Caballo al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (50, '[Caballeriza]EditarCaballo', 'Permite editar una Caballo al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (52, '[Caballeriza]AgregarGrupoCaballo', 'Permite agregar una Caballo al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (53, '[Caballeriza]EditarGrupoCaballo', 'Permite editar una Caballo al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (54, '[Caballeriza]EliminarGrupoCaballo', 'Permite eliminar un grupo de caballos');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (55, '[Caballeriza]AgregarEventoClinico', 'Permite agregar una Caballo al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (56, '[Caballeriza]EditarEventoClinico', 'Permite editar una Caballo al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (57, '[Caballeriza]AgregarInoculo', 'Permite agregar un Inóculo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (58, '[Caballeriza]EditarInoculo', 'Permite editar un Inóculo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (59, '[Caballeriza]AgregarSangriaPrueba', 'Permite agregar una Sangría de Prueba');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (60, '[Caballeriza]EditarSangriaPrueba', 'Permite editar una Sangría de Prueba');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (61, '[Caballeriza]AgregarSangria', 'Permite agregar una Sangría');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (62, '[Caballeriza]EditarSangria', 'Permite editar una Sangría');

--Menu

UPDATE seguridad.entradas_menu_principal SET redirect = '/Caballeriza/TipoEvento' WHERE id_menu_principal = 400;


INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (401, 400, 'Tipo de Evento', '/Caballeriza/TipoEvento');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (402, 400, 'Caballos', '/Caballeriza/Caballo');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (403, 400, 'Grupos', '/Caballeriza/GrupoDeCaballos');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (404, 400, 'Eventos Clínicos', '/Caballeriza/EventoClinico');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (405, 400, 'Inóculos', '/Caballeriza/Inoculo');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (406, 400, 'Pruebas Sangría', '/Caballeriza/SangriaPrueba');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (407, 400, 'Sangría', '/Caballeriza/Sangria');
------Permisos Menu Principal

--TipoEvento
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (46, 401);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (47, 401);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (48, 401);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (49, 402);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (50, 402);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (52, 403);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (53, 403);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (54, 403);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (55, 404);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (56, 404);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (57, 405);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (58, 405);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (59, 406);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (60, 406);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (61, 407);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (62, 407);