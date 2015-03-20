--########ESQUEMA DE CABALLERIZA########
DROP SCHEMA IF EXISTS caballeriza CASCADE;
CREATE SCHEMA caballeriza;
--Tablas de esquema de caballeriza

CREATE TABLE caballeriza.grupos_de_caballos (
    id_grupo_de_caballo serial NOT NULL,
    nombre character varying(45) NOT NULL,
    descripcion character varying(500)
);

CREATE TABLE caballeriza.grupos_caballos (
    id_grupo_de_caballo integer NOT NULL,
    id_caballo integer NOT NULL
);

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
    estado boolean NOT NULL
);

CREATE TABLE caballeriza.eventos_clinicos(
    id_evento serial NOT NULL,
    fecha Date NOT NULL,
    descripcion character varying(500) NOT NULL,
    responsable integer
);

CREATE TABLE caballeriza.eventos_grupos_caballos (
    id_evento integer NOT NULL,
    id_grupo_de_caballo integer NOT NULL
);
CREATE TABLE caballeriza.eventos_caballos (
    id_evento integer NOT NULL,
    id_caballo integer NOT NULL
);
--Llaves primarias esquema caballeriza
ALTER TABLE ONLY caballeriza.grupos_de_caballos  ADD CONSTRAINT pk_grupos_de_caballos PRIMARY KEY (id_grupo_de_caballo);
ALTER TABLE ONLY caballeriza.caballos  ADD CONSTRAINT pk_caballos PRIMARY KEY (id_caballo);
ALTER TABLE ONLY caballeriza.grupos_caballos  ADD CONSTRAINT pk_grupos_caballos PRIMARY KEY (id_grupo_de_caballo,id_caballo);
ALTER TABLE ONLY caballeriza.eventos_clinicos  ADD CONSTRAINT pk_eventos_clinicos PRIMARY KEY (id_evento);
ALTER TABLE ONLY caballeriza.eventos_grupos_caballos  ADD CONSTRAINT pk_eventos_grupos_caballos PRIMARY KEY (id_evento,id_grupo_de_caballo);
ALTER TABLE ONLY caballeriza.eventos_caballos  ADD CONSTRAINT pk_eventos_caballos PRIMARY KEY (id_evento,id_caballo);


--Indices unicos esquema caballeriza
CREATE UNIQUE INDEX i_nombre ON caballeriza.grupos_de_caballos USING btree (nombre);
CREATE UNIQUE INDEX i_numero_microchip ON caballeriza.caballos USING btree (numero_microchip);


--Llaves foraneas esquema caballeriza
ALTER TABLE ONLY caballeriza.grupos_caballos ADD CONSTRAINT fk_id_grupo_caballo FOREIGN KEY (id_grupo_de_caballo) REFERENCES caballeriza.grupos_de_caballos(id_grupo_de_caballo);
ALTER TABLE ONLY caballeriza.grupos_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
ALTER TABLE ONLY caballeriza.eventos_clinicos ADD CONSTRAINT fk_responsable FOREIGN KEY (responsable) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY caballeriza.eventos_grupos_caballos ADD CONSTRAINT fk_id_evento FOREIGN KEY (id_evento) REFERENCES caballeriza.eventos_clinicos(id_evento);
ALTER TABLE ONLY caballeriza.eventos_caballos ADD CONSTRAINT fk_id_evento FOREIGN KEY (id_evento) REFERENCES caballeriza.eventos_clinicos(id_evento);
ALTER TABLE ONLY caballeriza.eventos_grupos_caballos ADD CONSTRAINT fk_id_grupo_de_caballo FOREIGN KEY (id_grupo_de_caballo) REFERENCES caballeriza.grupos_de_caballos(id_grupo_de_caballo);
ALTER TABLE ONLY caballeriza.eventos_caballos ADD CONSTRAINT fk_id_caballo FOREIGN KEY (id_caballo) REFERENCES caballeriza.caballos(id_caballo);
