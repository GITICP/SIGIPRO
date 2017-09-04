-- Nombre unico en contactos de clientes
alter table ventas.contactos_cliente
add constraint unique_nombre_contacto UNIQUE(nombre);
-- Cambiar el nombre de la entrada de Facturas del menu principal
UPDATE seguridad.entradas_menu_principal
SET redirect = '/Ventas/Factura'
WHERE id_menu_principal = 716