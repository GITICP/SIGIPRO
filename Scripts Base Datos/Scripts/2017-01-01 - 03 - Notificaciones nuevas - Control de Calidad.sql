---------------------Tipos de notificaciones nuevas-----------------------------------------------------------------------------------------------
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (300, 'Nueva solicitud de Control de Calidad', 'fa fa-list-alt', ARRAY[551,552,554]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (301, 'Solicitud de Control de Calidad recibida', 'fa fa-list-alt', ARRAY[551,552,554]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (302, 'Solicitud de Control de Calidad anulada', 'fa fa-list-alt', ARRAY[551,552,554]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (303, 'Informe parcial generado', 'fa fa-list-alt', ARRAY[557]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (304, 'Informe final generado', 'fa fa-list-alt', ARRAY[557]::INT[], ARRAY[]::INT[]);

---------------------Funciones-----------------------------------------------------------------------------------------------------------------------
--Ingresar solicitud Control Calidad
--DROP FUNCTION control_calidad.crear_notificacion_solicitud();
CREATE OR REPLACE FUNCTION control_calidad.crear_notificacion_solicitud()
  RETURNS trigger AS
$BODY$
	DECLARE lista_usuarios integer[];
	DECLARE lista_permisos integer[];
	BEGIN
		lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 300);
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
			VALUES (300,lista_usuarios[i],(Select current_timestamp), False, '/ControlCalidad/Solicitud');
		END LOOP;
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION control_calidad.crear_notificacion_solicitud()
  OWNER TO postgres;
  
--RECIBIR SOLICITUD CC 
--DROP FUNCTION control_calidad.notificacion_recibir_solicitud();
CREATE OR REPLACE FUNCTION control_calidad.notificacion_recibir_solicitud()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (301,NEW.id_usuario_solicitante,(Select current_timestamp), False, '/ControlCalidad/Solicitud');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION control_calidad.notificacion_recibir_solicitud()
  OWNER TO postgres;

--ANULAR SOLICITUD CC 
--DROP FUNCTION control_calidad.notificacion_anular_solicitud();
CREATE OR REPLACE FUNCTION control_calidad.notificacion_anular_solicitud()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (302,NEW.id_usuario_solicitante,(Select current_timestamp), False, '/ControlCalidad/Solicitud');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION control_calidad.notificacion_anular_solicitud()
  OWNER TO postgres;

--Notificación Informe Parcial - TODA PRODUCCIÓN 
--DROP FUNCTION control_calidad.crear_notificacion_informe_parcial_todo();
CREATE OR REPLACE FUNCTION control_calidad.crear_notificacion_informe_parcial_todo()
  RETURNS void AS
$BODY$
	DECLARE lista_usuarios integer[];
	BEGIN
		lista_usuarios := lista_usuarios || array
			(Select distinct u.id_usuario from 
				seguridad.usuarios u
				inner join seguridad.secciones s on (u.id_seccion = s.id_seccion)
				WHERE s.nombre_seccion = 'Producción');
		FOR i IN array_lower(lista_usuarios, 1) .. array_upper(lista_usuarios, 1)
		LOOP
			INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
			VALUES (303,lista_usuarios[i],(Select current_timestamp), False, '/ControlCalidad/Solicitud');
		END LOOP;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION control_calidad.crear_notificacion_informe_parcial_todo()
  OWNER TO postgres;

--Notificación Informe Final - TODA PRODUCCIÓN 
--DROP FUNCTION control_calidad.crear_notificacion_informe_final_todo();
CREATE OR REPLACE FUNCTION control_calidad.crear_notificacion_informe_final_todo()
  RETURNS void AS
$BODY$
	DECLARE lista_usuarios integer[];
	BEGIN
		lista_usuarios := lista_usuarios || array
			(Select distinct u.id_usuario from 
				seguridad.usuarios u
				inner join seguridad.secciones s on (u.id_seccion = s.id_seccion)
				WHERE s.nombre_seccion = 'Producción');
		FOR i IN array_lower(lista_usuarios, 1) .. array_upper(lista_usuarios, 1)
		LOOP
			INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
			VALUES (304,lista_usuarios[i],(Select current_timestamp), False, '/ControlCalidad/Solicitud');
		END LOOP;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION control_calidad.crear_notificacion_informe_final_todo()
  OWNER TO postgres;

--Notificación Informe Parcial
--DROP FUNCTION control_calidad.crear_notificacion_informe_parcial();
CREATE OR REPLACE FUNCTION control_calidad.crear_notificacion_informe_parcial(id_usuario integer)
  RETURNS void AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (303,id_usuario,(Select current_timestamp), False, '/ControlCalidad/Solicitud');
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION control_calidad.crear_notificacion_informe_parcial(id_usuario integer)
  OWNER TO postgres;

--Notificación Informe Parcial
--DROP FUNCTION control_calidad.crear_notificacion_informe_final();
CREATE OR REPLACE FUNCTION control_calidad.crear_notificacion_informe_final(id_usuario integer)
  RETURNS void AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (304,id_usuario,(Select current_timestamp), False, '/ControlCalidad/Solicitud');
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION control_calidad.crear_notificacion_informe_final(id_usuario integer)
  OWNER TO postgres;

---------------------Triggers-----------------------------------------------------------------------------------------------------------------------
-- DROP TRIGGER insertar_notificacion ON control_calidad.solicitudes;
CREATE TRIGGER insertar_notificacion
  AFTER INSERT
  ON control_calidad.solicitudes
  FOR EACH ROW
  EXECUTE PROCEDURE control_calidad.crear_notificacion_solicitud();

--DROP TRIGGER insertar_notificacion_recibir ON control_calidad.solicitudes;
CREATE TRIGGER insertar_notificacion_recibir
  AFTER UPDATE
  ON control_calidad.solicitudes
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Recibido')
  EXECUTE PROCEDURE control_calidad.notificacion_recibir_solicitud();

--DROP TRIGGER insertar_notificacion_anular ON control_calidad.solicitudes;
CREATE TRIGGER insertar_notificacion_anular
  AFTER UPDATE
  ON control_calidad.solicitudes
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Anulada')
  EXECUTE PROCEDURE control_calidad.notificacion_anular_solicitud();

