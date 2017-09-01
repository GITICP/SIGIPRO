-- Quitar la columna de estado de tratamientos
alter table ventas.tratamiento
drop column estado;
-- Nombre unico en productos de venta
alter table ventas.producto_venta
add constraint unique_nombre UNIQUE(nombre);
-- Nombre unico en contratos 
alter table ventas.contrato_comercializacion
add constraint unique_nombre_contrato UNIQUE(nombre);