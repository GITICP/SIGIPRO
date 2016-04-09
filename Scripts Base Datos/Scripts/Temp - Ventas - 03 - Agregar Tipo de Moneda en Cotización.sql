-- Agregar el campo de tipo de moneda a la cotización
ALTER TABLE ventas.cotizacion
ADD COLUMN moneda character varying(20);