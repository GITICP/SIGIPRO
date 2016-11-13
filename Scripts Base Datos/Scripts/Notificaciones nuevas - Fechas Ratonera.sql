---------------------Tipos de notificaciones nuevas------------------
--Ratonera
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (103, 'Retiro de pie de cría próximo', 'sigipro-mouse-1', ARRAY[209,1]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (104, 'Apareamiento de cara próximo', 'sigipro-mouse-1', ARRAY[201,1]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (105, 'Eliminación de macho próxima', 'sigipro-mouse-1', ARRAY[201,1]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (106, 'Eliminación de hembra próxima', 'sigipro-mouse-1', ARRAY[201,1]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (107, 'Selección para cara próxima', 'sigipro-mouse-1', ARRAY[201,1]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (108, 'Reposición de cara próxima', 'sigipro-mouse-1', ARRAY[201,1]::INT[], ARRAY[]::INT[]);

--Funciones
CREATE OR REPLACE FUNCTION bioterio.notificacion_retiro()
  RETURNS VOID AS
$BODY$
    DECLARE lista_usuarios integer[];
    DECLARE lista_permisos integer[];
    BEGIN
        IF EXISTS (Select id_pie from bioterio.pie_de_cria where (fecha_retiro = current_date)) THEN
            lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 103);
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
                VALUES (103,lista_usuarios[i],(Select current_timestamp), False, '/Ratonera/Pies');
            END LOOP;
        END IF;
    END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_retiro()
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION bioterio.notificacion_apareamiento()
  RETURNS VOID AS
$BODY$
    DECLARE lista_usuarios integer[];
    DECLARE lista_permisos integer[];
    BEGIN
        IF EXISTS (Select id_cara from bioterio.caras where (fecha_apareamiento_i = current_date)) THEN
            lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 104);
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
                VALUES (104,lista_usuarios[i],(Select current_timestamp), False, '/Ratonera/Caras');
            END LOOP;
        END IF;
    END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_apareamiento()
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION bioterio.notificacion_eliminacion_macho()
  RETURNS VOID AS
$BODY$
    DECLARE lista_usuarios integer[];
    DECLARE lista_permisos integer[];
    BEGIN
        IF EXISTS (Select id_cara from bioterio.caras where (fecha_eliminacionmacho_i = current_date)) THEN
            lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 105);
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
                VALUES (105,lista_usuarios[i],(Select current_timestamp), False, '/Ratonera/Caras');
            END LOOP;
        END IF;
    END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_eliminacion_macho()
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION bioterio.notificacion_eliminacion_hembra()
  RETURNS VOID AS
$BODY$
    DECLARE lista_usuarios integer[];
    DECLARE lista_permisos integer[];
    BEGIN
        IF EXISTS (Select id_cara from bioterio.caras where (fecha_eliminacionhembra_i = current_date)) THEN
            lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 106);
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
                VALUES (106,lista_usuarios[i],(Select current_timestamp), False, '/Ratonera/Caras');
            END LOOP;
        END IF;
    END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_eliminacion_hembra()
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION bioterio.notificacion_seleccion_cara()
  RETURNS VOID AS
$BODY$
    DECLARE lista_usuarios integer[];
    DECLARE lista_permisos integer[];
    BEGIN
        IF EXISTS (Select id_cara from bioterio.caras where (fecha_seleccionnuevos_i = current_date)) THEN
            lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 107);
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
                VALUES (107,lista_usuarios[i],(Select current_timestamp), False, '/Ratonera/Caras');
            END LOOP;
        END IF;
    END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_seleccion_cara()
  OWNER TO postgres;

CREATE OR REPLACE FUNCTION bioterio.notificacion_reposicion_cara()
  RETURNS VOID AS
$BODY$
    DECLARE lista_usuarios integer[];
    DECLARE lista_permisos integer[];
    BEGIN
        IF EXISTS (Select id_cara from bioterio.caras where (fecha_reposicionciclo_i = current_date)) THEN
            lista_permisos := (Select id_permisos from calendario.tipo_notificaciones where id_tipo_notificacion = 108);
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
                VALUES (108,lista_usuarios[i],(Select current_timestamp), False, '/Ratonera/Caras');
            END LOOP;
        END IF;
    END; 
$BODY$
  LANGUAGE plpgsql VOLATILE
  COST 100;
ALTER FUNCTION bioterio.notificacion_reposicion_cara()
  OWNER TO postgres;