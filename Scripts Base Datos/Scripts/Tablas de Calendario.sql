-- Schema: calendario

-- DROP SCHEMA calendario;

CREATE SCHEMA calendario
  AUTHORIZATION postgres;

CREATE TABLE calendario.eventos
(
  id serial NOT NULL,
  title character varying(150) NOT NULL,
  start_date timestamp NOT NULL,
  end_date timestamp,
  description character varying(500),
  allDay boolean NOT NULL,
  CONSTRAINT pk_id_evento PRIMARY KEY (id)
)

CREATE TABLE calendario.eventos_usuarios
(
  id_evento integer NOT NULL,
  id_usuario integer NOT NULL,
  CONSTRAINT pk_id_evento_usuario PRIMARY KEY (id_evento, id_usuario),
  CONSTRAINT fk_id_evento_u FOREIGN KEY (id_evento)
      REFERENCES calendario.eventos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_id_usuario_e FOREIGN KEY (id_usuario)
      REFERENCES seguridad.usuarios (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)

CREATE TABLE calendario.eventos_secciones
(
  id_evento integer NOT NULL,
  id_seccion integer NOT NULL,
  CONSTRAINT pk_id_evento_seccion PRIMARY KEY (id_evento, id_seccion),
  CONSTRAINT fk_id_evento_s FOREIGN KEY (id_evento)
      REFERENCES calendario.eventos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_id_seccion_e FOREIGN KEY (id_seccion)
      REFERENCES seguridad.secciones(id_seccion) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)

CREATE TABLE calendario.eventos_roles
(
  id_evento integer NOT NULL,
  id_rol integer NOT NULL,
  CONSTRAINT pk_id_evento_rol PRIMARY KEY (id_evento, id_rol),
  CONSTRAINT fk_id_evento_r FOREIGN KEY (id_evento)
      REFERENCES calendario.eventos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_id_rol_e FOREIGN KEY (id_rol)
      REFERENCES seguridad.roles (id_rol) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)