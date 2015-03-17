-- Trigger para el código icp. Correr desde PGAdmin porque desde NetBeans no sé por qué no sirve.

    -- Función que añade como código el id asignado automáticamente.

CREATE FUNCTION bodega.cod_icp_consecutivo() RETURNS trigger AS $nuevo_producto$
    BEGIN
        NEW.codigo_icp := NEW.id_producto;
        RETURN NEW;
    END; 
$nuevo_producto$ LANGUAGE plpgsql;

CREATE TRIGGER bodega.tggr_cod_icp_insert BEFORE INSERT ON bodega.catalogo_interno FOR EACH ROW EXECUTE PROCEDURE bodega.cod_icp_consecutivo();
CREATE TRIGGER bodega.tggr_cod_icp_update BEFORE UPDATE ON bodega.catalogo_interno FOR EACH ROW EXECUTE PROCEDURE bodega.cod_icp_consecutivo();