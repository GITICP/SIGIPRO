-- Modificar la columna de observaciones en contratos de comercializacion a 500 caracteres
alter table ventas.contrato_comercializacion
drop column observaciones;
alter table ventas.contrato_comercializacion
add column observaciones character varying(500);
-- Agregar columna de estado en cliente
alter table ventas.cliente
add column estado character varying(20);
-- Nombre y cédula unicos en clientes
alter table ventas.cliente
add constraint unique_nombre_cliente UNIQUE(nombre);
alter table ventas.cliente
add constraint unique_cedula_cliente UNIQUE(cedula);