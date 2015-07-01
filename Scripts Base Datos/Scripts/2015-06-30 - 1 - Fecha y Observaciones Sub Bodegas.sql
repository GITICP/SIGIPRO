
-- Cambio para ingresar fecha del

ALTER TABLE bodega.bitacora_sub_bodegas
    ADD COLUMN fecha date;
ALTER TABLE bodega.bitacora_sub_bodegas
    ADD COLUMN observaciones character varying(500);

UPDATE bodega.bitacora_sub_bodegas SET fecha = fecha_accion::date;

ALTER TABLE bodega.bitacora_sub_bodegas
    ALTER COLUMN fecha SET NOT NULL;

