-- Trigger para el código icp. Correr desde PGAdmin porque desde NetBeans no sé por qué no sirve.

    -- Función que añade como código el id asignado automáticamente.

CREATE FUNCTION bodega.cod_icp_consecutivo() RETURNS trigger AS $nuevo_producto$
    BEGIN
        NEW.codigo_icp := NEW.id_producto;
        RETURN NEW;
    END; 
$nuevo_producto$ LANGUAGE plpgsql;

CREATE TRIGGER tggr_cod_icp BEFORE INSERT ON bodega.catalogo_interno FOR EACH ROW EXECUTE PROCEDURE cod_icp_consecutivo();