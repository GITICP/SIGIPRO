ALTER TABLE BITACORA.BITACORA
ALTER COLUMN fecha_accion TYPE timestamp
USING CAST(fecha_accion as timestamp);

