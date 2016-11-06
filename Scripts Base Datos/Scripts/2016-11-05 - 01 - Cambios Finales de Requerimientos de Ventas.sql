-- Agregar dirección, cédula y tipo de persona (Jurídica o Física) en Cliente 
ALTER TABLE ventas.cliente
ADD COLUMN direccion character varying(350),
ADD COLUMN cedula character varying(30),
ADD COLUMN persona character varying(20);