-- Agregar campos en factura para webservice Fundevi

ALTER TABLE ventas.factura
ADD COLUMN plazo int,
ADD COLUMN numero_factura int,
ADD COLUMN proyecto int,
ADD COLUMN nombre_factura character varying(100),
ADD COLUMN correo_a_enviar character varying(50),
ADD COLUMN detalle character varying(500)
ADD COLUMN estado character varying(500);

