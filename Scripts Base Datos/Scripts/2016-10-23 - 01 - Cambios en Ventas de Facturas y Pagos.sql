-- Agregar Monto Pendiente en Factura
ALTER TABLE ventas.factura 
ADD COLUMN monto_pendiente numeric;

-- Actualizar Pagos con la información de que provee SIGEFAC
ALTER TABLE ventas.pago
DROP COLUMN monto_pendiente,
DROP COLUMN pago,
ADD COLUMN codigo int,
ADD COLUMN monto numeric,
ADD COLUMN nota character varying(250),
ADD COLUMN fecha character varying(50),
ADD COLUMN consecutive character varying(50),
ADD COLUMN moneda character varying(20),
ADD COLUMN codigo_remision int,
ADD COLUMN consecutive_remision character varying(50),
ADD COLUMN fecha_remision character varying(50);