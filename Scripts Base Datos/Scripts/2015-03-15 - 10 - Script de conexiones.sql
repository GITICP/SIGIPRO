-- Scripts para el cierre y análisis de conexiones cerradas de manera forzada

    -- Tabla para mantener el registro de las conexiones que se cierran
CREATE TABLE seguridad.conexiones_cerradas(
    query text,
    killed boolean,
    query_start timestamp with time zone,
    state_changed timestamp with time zone
);
    
    -- Función que cierra las conexiones que llevan más de un minuto abiertas sin hacer nada
CREATE FUNCTION seguridad.cerrar_conexiones() RETURNS boolean as $conexiones_cerradas$
    BEGIN
   	INSERT INTO seguridad.conexiones_cerradas
            SELECT query, (SELECT pg_terminate_backend(pid)) as killed, query_start, state_change
            FROM pg_stat_activity
            WHERE state = 'idle'
              and datname='sigipro'
              and current_timestamp > state_change + interval '1 minute';
        RETURN true;
    END;
$conexiones_cerradas$ LANGUAGE plpgsql;