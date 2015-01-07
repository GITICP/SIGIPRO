--- Funcion para cambiar los estados de usuario de acuerdo a las fechas -- 
CREATE FUNCTION seguridad.f_FechasUsuario() RETURNS VOID 
AS
$func$
UPDATE seguridad.usuarios 
	set estado = TRUE
	where fecha_activacion = current_date;
UPDATE seguridad.usuarios 
       set estado = FALSE
       where fecha_desactivacion = current_date;
$func$
LANGUAGE SQL;

--- Funcion para cambiar los roles de acuerdo a las fechas -- 
CREATE FUNCTION seguridad.f_FechasRoles() RETURNS VOID 
AS
$func$
 DELETE FROM seguridad.roles_usuarios
        WHERE fecha_desactivacion= current_date;
$func$
LANGUAGE SQL;
