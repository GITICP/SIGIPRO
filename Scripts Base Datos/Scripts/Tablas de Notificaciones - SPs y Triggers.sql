---------------------CREACION DE TABLAS

CREATE TABLE calendario.tipo_notificaciones
(
	id_tipo_notificacion integer NOT NULL,
	descripcion character(100) NOT NULL,
	icono character(40),
	id_permisos integer[] NOT NULL,
	id_usuarios_bloqueados integer[],
	CONSTRAINT pk_tipo_notificaciones PRIMARY KEY (id_tipo_notificacion)
);

CREATE TABLE calendario.notificaciones(
	id_notificacion serial NOT NULL,
	id_tipo_notificacion integer,
	id_usuario integer,
	time_stamp timestamp NOT NULL,
	leida boolean NOT NULL DEFAULT false,
	redirect character varying(200),
	CONSTRAINT pk_notificaciones PRIMARY KEY (id_notificacion),
	CONSTRAINT fk_id_tipo_notificacion FOREIGN KEY (id_tipo_notificacion)
		REFERENCES calendario.tipo_notificaciones (id_tipo_notificacion ) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario)
		REFERENCES seguridad.usuarios (id_usuario) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);


---------------------CREACION DE FUNCIONES (STORED PROCEDURES)

--SOLICITUDES BODEGAS
--DROP FUNCTION bodega.crear_notificacion_solicitud();
CREATE OR REPLACE FUNCTION bodega.crear_notificacion_solicitud()
  RETURNS trigger AS
$BODY$
	DECLARE lista_usuarios integer[];
	DECLARE lista_permisos integer[];
	BEGIN
		lista_permisos := array(Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 1);
		FOR e IN array_lower(lista_permisos, 1) .. array_upper(lista_permisos, 1)
		LOOP
		lista_usuarios := lista_usuarios || array
			(Select distinct seguridad.usuarios.id_usuario from 
				seguridad.usuarios
				inner join seguridad.roles_usuarios as ru on (seguridad.usuarios.id_usuario = ru.id_usuario)
				inner join seguridad.roles as r on (ru.id_rol = r.id_rol)
				inner join seguridad.permisos_roles as pr on (r.id_rol = pr.id_rol)
				inner join (Select * from seguridad.permisos where id_permiso = lista_permisos[e]) as p on (pr.id_permiso = p.id_permiso));
		END LOOP;
		FOR i IN array_lower(lista_usuarios, 1) .. array_upper(lista_usuarios, 1)
		LOOP
			INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
			VALUES (1,lista_usuarios[i],(Select current_timestamp), False, '/Bodegas/Solicitudes');
		END LOOP;
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bodega.crear_notificacion_solicitud()
  OWNER TO postgres;


--BIOTERIO SOLICITUDES RATONERA
  --DROP FUNCTION bioterio.crear_notificacion_solicitud_ratonera();
CREATE OR REPLACE FUNCTION bioterio.crear_notificacion_solicitud_ratonera()
  RETURNS trigger AS
$BODY$
	DECLARE lista_usuarios integer[];
	DECLARE lista_permisos integer[];
	BEGIN
		lista_permisos := array(Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 100);
		FOR e IN array_lower(lista_permisos, 1) .. array_upper(lista_permisos, 1)
		LOOP
		lista_usuarios := lista_usuarios || array
			(Select distinct seguridad.usuarios.id_usuario from 
				seguridad.usuarios
				inner join seguridad.roles_usuarios as ru on (seguridad.usuarios.id_usuario = ru.id_usuario)
				inner join seguridad.roles as r on (ru.id_rol = r.id_rol)
				inner join seguridad.permisos_roles as pr on (r.id_rol = pr.id_rol)
				inner join (Select * from seguridad.permisos where id_permiso = lista_permisos[e]) as p on (pr.id_permiso = p.id_permiso));
		END LOOP;
		FOR i IN array_lower(lista_usuarios, 1) .. array_upper(lista_usuarios, 1)
		LOOP
			INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
			VALUES (100,lista_usuarios[i],(Select current_timestamp), False, '/Ratonera/SolicitudesRatonera');
		END LOOP;
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.crear_notificacion_solicitud_ratonera()
  OWNER TO postgres;


--BIOTERIO SOLICITUDES CONEJERA
  --DROP FUNCTION bioterio.crear_notificacion_solicitud_conejera();
CREATE OR REPLACE FUNCTION bioterio.crear_notificacion_solicitud_conejera()
  RETURNS trigger AS
$BODY$
	DECLARE lista_usuarios integer[];
	DECLARE lista_permisos integer[];
	BEGIN
		lista_permisos := array(Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 150);
		FOR e IN array_lower(lista_permisos, 1) .. array_upper(lista_permisos, 1)
		LOOP
		lista_usuarios := lista_usuarios || array
			(Select distinct seguridad.usuarios.id_usuario from 
				seguridad.usuarios
				inner join seguridad.roles_usuarios as ru on (seguridad.usuarios.id_usuario = ru.id_usuario)
				inner join seguridad.roles as r on (ru.id_rol = r.id_rol)
				inner join seguridad.permisos_roles as pr on (r.id_rol = pr.id_rol)
				inner join (Select * from seguridad.permisos where id_permiso = lista_permisos[e]) as p on (pr.id_permiso = p.id_permiso));
		END LOOP;
		FOR i IN array_lower(lista_usuarios, 1) .. array_upper(lista_usuarios, 1)
		LOOP
			INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
			VALUES (150,lista_usuarios[i],(Select current_timestamp), False, '/Conejera/SolicitudesConejera');
		END LOOP;
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.crear_notificacion_solicitud_conejera()
  OWNER TO postgres;  


--SOLICITUDES SERPENTARIO
--DROP FUNCTION serpentario.crear_notificacion_solicitud();
CREATE OR REPLACE FUNCTION serpentario.crear_notificacion_solicitud()
  RETURNS trigger AS
$BODY$
	DECLARE lista_usuarios integer[];
	DECLARE lista_permisos integer[];
	BEGIN
		lista_permisos := array(Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 200);
		FOR e IN array_lower(lista_permisos, 1) .. array_upper(lista_permisos, 1)
		LOOP
		lista_usuarios := lista_usuarios || array
			(Select distinct seguridad.usuarios.id_usuario from 
				seguridad.usuarios
				inner join seguridad.roles_usuarios as ru on (seguridad.usuarios.id_usuario = ru.id_usuario)
				inner join seguridad.roles as r on (ru.id_rol = r.id_rol)
				inner join seguridad.permisos_roles as pr on (r.id_rol = pr.id_rol)
				inner join (Select * from seguridad.permisos where id_permiso = lista_permisos[e]) as p on (pr.id_permiso = p.id_permiso));
		END LOOP;
		FOR i IN array_lower(lista_usuarios, 1) .. array_upper(lista_usuarios, 1)
		LOOP
			INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
			VALUES (200,lista_usuarios[i],(Select current_timestamp), False, '/Serpentario/Serpiente');
		END LOOP;
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION serpentario.crear_notificacion_solicitud()
  OWNER TO postgres;


---------------------CREACION DE TRIGGERS

-- DROP TRIGGER insertar_notificacion ON bodega.solicitudes;
CREATE TRIGGER insertar_notificacion
  AFTER INSERT
  ON bodega.solicitudes
  FOR EACH ROW
  EXECUTE PROCEDURE bodega.crear_notificacion_solicitud();

-- DROP TRIGGER insertar_notificacion ON bioterio.solicitudes_conejera;
CREATE TRIGGER insertar_notificacion
  AFTER INSERT
  ON bioterio.solicitudes_conejera
  FOR EACH ROW
  EXECUTE PROCEDURE bioterio.crear_notificacion_solicitud_conejera();

-- DROP TRIGGER insertar_notificacion ON bioterio.solicitudes_ratonera;
CREATE TRIGGER insertar_notificacion
  AFTER INSERT
  ON bioterio.solicitudes_ratonera
  FOR EACH ROW
  EXECUTE PROCEDURE bioterio.crear_notificacion_solicitud_ratonera();

-- DROP TRIGGER insertar_notificacion ON serpentario.solicitudes;
CREATE TRIGGER insertar_notificacion
  AFTER INSERT
  ON serpentario.solicitudes
  FOR EACH ROW
  EXECUTE PROCEDURE serpentario.crear_notificacion_solicitud();


---------------------INSERCION DE NOTIFICACIONES POR DEFECTO

--Bodegas
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (1, 'Nueva solicitud de bodegas', 'fa fa-shopping-cart red-font', ARRAY[25]::INT[], ARRAY[]::INT[]);

--Bioterio Ratonera
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (100, 'Nueva solicitud de ratones', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);

--Bioterio Conejera
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (150, 'Nueva solicitud de conejos', 'sigipro-rabbit-2', ARRAY[254]::INT[], ARRAY[]::INT[]);

--Serpentario
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (200, 'Nueva solicitud de venenos', 'sigipro-snake-1', ARRAY[352]::INT[], ARRAY[]::INT[]);