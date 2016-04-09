-- Agregar el campo de tipo de moneda a la cotización
ALTER TABLE ventas.cotizacion
ADD COLUMN moneda character varying(20);

-- Agregar el campo de tipo de moneda a la factura
ALTER TABLE ventas.factura
ADD COLUMN moneda character varying(20);