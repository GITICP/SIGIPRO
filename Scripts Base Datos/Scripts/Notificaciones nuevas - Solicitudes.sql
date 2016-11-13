---------------------Tipos de notificaciones nuevas------------------
--Bodegas
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (2, 'Solicitud de bodega aprobada', 'fa fa-shopping-cart red-font', ARRAY[25]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (3, 'Solicitud de bodega rechazada', 'fa fa-shopping-cart red-font', ARRAY[25]::INT[], ARRAY[]::INT[]);

--Bioterio Ratonera
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (101, 'Solicitud de ratones aprobada', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (102, 'Solicitud de ratones rechazada', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);

--Bioterio Conejera
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (151, 'Solicitud de conejos aprobada', 'sigipro-rabbit-2', ARRAY[254]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (152, 'Solicitud de conejos rechazada', 'sigipro-rabbit-2', ARRAY[254]::INT[], ARRAY[]::INT[]);

--Serpentario
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (201, 'Solicitud de venenos aprobada', 'sigipro-snake-1', ARRAY[352]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (202, 'Solicitud de venenos rechazada', 'sigipro-snake-1', ARRAY[352]::INT[], ARRAY[]::INT[]);

---------------------Funciones---------------------------------------------
--APROBAR SOLICITUDES BODEGAS
--DROP FUNCTION bodega.notificacion_aprobar_solicitud();
CREATE OR REPLACE FUNCTION bodega.notificacion_aprobar_solicitud()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (2,NEW.id_usuario,(Select current_timestamp), False, '/Bodegas/Solicitudes');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bodega.notificacion_aprobar_solicitud()
  OWNER TO postgres;
  
--RECHAZAR SOLICITUDES BODEGAS
--DROP FUNCTION bodega.notificacion_rechazar_solicitud();
CREATE OR REPLACE FUNCTION bodega.notificacion_rechazar_solicitud()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (3,NEW.id_usuario,(Select current_timestamp), False, '/Bodegas/Solicitudes');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bodega.notificacion_rechazar_solicitud()
  OWNER TO postgres;
  
--APROBAR SOLICITUDES RATONES
--DROP FUNCTION bioterio.notificacion_aprobar_solicitud_ratones();
CREATE OR REPLACE FUNCTION bioterio.notificacion_aprobar_solicitud_ratones()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (101,NEW.usuario_solicitante,(Select current_timestamp), False, '/Ratonera/SolicitudesRatonera');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_aprobar_solicitud_ratones()
  OWNER TO postgres;
  
--RECHAZAR SOLICITUDES RATONES
--DROP FUNCTION bioterio.notificacion_rechazar_solicitud_ratones();
CREATE OR REPLACE FUNCTION bioterio.notificacion_rechazar_solicitud_ratones()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (102,NEW.usuario_solicitante,(Select current_timestamp), False, '/Ratonera/SolicitudesRatonera');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_rechazar_solicitud_ratones()
  OWNER TO postgres;
  
--APROBAR SOLICITUDES CONEJOS
--DROP FUNCTION bioterio.notificacion_aprobar_solicitud_conejos();
CREATE OR REPLACE FUNCTION bioterio.notificacion_aprobar_solicitud_conejos()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (151,NEW.usuario_solicitante,(Select current_timestamp), False, '/Conejera/SolicitudesConejera');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_aprobar_solicitud_conejos()
  OWNER TO postgres;
  
--RECHAZAR SOLICITUDES CONEJOS
--DROP FUNCTION bioterio.notificacion_rechazar_solicitud_conejos();
CREATE OR REPLACE FUNCTION bioterio.notificacion_rechazar_solicitud_conejos()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (152,NEW.usuario_solicitante,(Select current_timestamp), False, '/Conejera/SolicitudesConejera');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_rechazar_solicitud_conejos()
  OWNER TO postgres;
--APROBAR SOLICITUDES VENENOS
--DROP FUNCTION serpentario.notificacion_aprobar_solicitud();
CREATE OR REPLACE FUNCTION serpentario.notificacion_aprobar_solicitud()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (201,NEW.id_usuario,(Select current_timestamp), False, '/Serpentario/SolicitudVeneno');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION serpentario.notificacion_aprobar_solicitud()
  OWNER TO postgres;
--RECHAZAR SOLICITUDES VENENOS
--DROP FUNCTION serpentario.notificacion_rechazar_solicitud();
CREATE OR REPLACE FUNCTION serpentario.notificacion_rechazar_solicitud()
  RETURNS trigger AS
$BODY$
	BEGIN
		INSERT INTO calendario.notificaciones(id_tipo_notificacion, id_usuario, time_stamp, leida, redirect)
		VALUES (202,NEW.id_usuario,(Select current_timestamp), False, '/Serpentario/SolicitudVeneno');
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION serpentario.notificacion_rechazar_solicitud()
  OWNER TO postgres;
---------------------Triggers----------------------------------------


--DROP TRIGGER insertar_notificacion_aprobar ON bodega.solicitudes;
CREATE TRIGGER insertar_notificacion_aprobar
  AFTER UPDATE
  ON bodega.solicitudes
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Aprobada')
  EXECUTE PROCEDURE bodega.notificacion_aprobar_solicitud();

--DROP TRIGGER insertar_notificacion_rechazar ON bodega.solicitudes;
CREATE TRIGGER insertar_notificacion_rechazar
  AFTER UPDATE
  ON bodega.solicitudes
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Rechazada')
  EXECUTE PROCEDURE bodega.notificacion_rechazar_solicitud();

----------------Serpentario
--DROP TRIGGER insertar_notificacion_aprobar ON serpentario.solicitudes;
CREATE TRIGGER insertar_notificacion_aprobar
  AFTER UPDATE
  ON serpentario.solicitudes
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Aprobado')
  EXECUTE PROCEDURE serpentario.notificacion_aprobar_solicitud();

--DROP TRIGGER insertar_notificacion_rechazar ON serpentario.solicitudes;
CREATE TRIGGER insertar_notificacion_rechazar
  AFTER UPDATE
  ON serpentario.solicitudes
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Rechazado')
  EXECUTE PROCEDURE serpentario.notificacion_rechazar_solicitud();

---------------Ratonera

--DROP TRIGGER insertar_notificacion_aprobar ON bioterio.solicitudes_ratonera;
CREATE TRIGGER insertar_notificacion_aprobar
  AFTER UPDATE
  ON bioterio.solicitudes_ratonera
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Aprobada')
  EXECUTE PROCEDURE bioterio.notificacion_aprobar_solicitud_ratones();

--DROP TRIGGER insertar_notificacion_rechazar ON bioterio.solicitudes_ratonera;
CREATE TRIGGER insertar_notificacion_rechazar
  AFTER UPDATE
  ON bioterio.solicitudes_ratonera
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Rechazada')
  EXECUTE PROCEDURE bioterio.notificacion_rechazar_solicitud_ratones();

---------------Conejera
--DROP TRIGGER insertar_notificacion_aprobar ON bioterio.solicitudes_conejera;
CREATE TRIGGER insertar_notificacion_aprobar
  AFTER UPDATE
  ON bioterio.solicitudes_conejera
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Aprobada')
  EXECUTE PROCEDURE bioterio.notificacion_aprobar_solicitud_conejos();

--DROP TRIGGER insertar_notificacion_rechazar ON bioterio.solicitudes_conejera;
CREATE TRIGGER insertar_notificacion_rechazar
  AFTER UPDATE
  ON bioterio.solicitudes_conejera
  FOR EACH ROW
  WHEN (OLD.estado IS DISTINCT FROM NEW.estado AND NEW.estado = 'Rechazada')
  EXECUTE PROCEDURE bioterio.notificacion_rechazar_solicitud_conejos();

-------------------------------------------------FIX DE UNA FUNCION DE SERPENTARIO--------------------------------------

--SOLICITUDES SERPENTARIO
--DROP FUNCTION serpentario.crear_notificacion_solicitud();
CREATE OR REPLACE FUNCTION serpentario.crear_notificacion_solicitud()
  RETURNS trigger AS
$BODY$
	DECLARE lista_usuarios integer[];
	DECLARE lista_permisos integer[];
	BEGIN
		lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 200);
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
			VALUES (200,lista_usuarios[i],(Select current_timestamp), False, '/Serpentario/SolicitudVeneno');
		END LOOP;
		RETURN NEW;
	END 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION serpentario.crear_notificacion_solicitud()
  OWNER TO postgres;


---------------------Fix del trigger de la función anterior

DROP TRIGGER insertar_notificacion ON serpentario.solicitudes;
CREATE TRIGGER insertar_notificacion
  AFTER INSERT
  ON serpentario.solicitudes
  FOR EACH ROW
  EXECUTE PROCEDURE serpentario.crear_notificacion_solicitud();

