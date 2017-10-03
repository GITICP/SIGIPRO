-- Nombre unico en cotización
alter table ventas.cotizacion
add constraint unique_nombre_cotizacion UNIQUE(identificador);