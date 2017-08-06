-- Insert Facturación y submenu: Otros
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, orden) VALUES (715, 700, 'Otros', 9);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (716, 700, 'Facturación', '/Ventas/Facturas', 5);

-- Update orden de Menú
UPDATE seguridad.entradas_menu_principal
SET orden = 2
WHERE id_menu_principal = 701; -- Productos de Venta

UPDATE seguridad.entradas_menu_principal
SET orden = 1
WHERE id_menu_principal = 702; -- Clientes

UPDATE seguridad.entradas_menu_principal
SET orden = 4
WHERE id_menu_principal = 703; -- submenu Proceso de Venta

UPDATE seguridad.entradas_menu_principal
SET orden = 3
WHERE id_menu_principal = 709; -- Contrato de Comercialización

UPDATE seguridad.entradas_menu_principal
SET orden = 2
WHERE id_menu_principal = 710; -- Reunión Producción

UPDATE seguridad.entradas_menu_principal
SET id_padre = 715
WHERE id_menu_principal = 710; -- Reunión Producción

UPDATE seguridad.entradas_menu_principal
SET orden = 6
WHERE id_menu_principal = 711; -- Encuesta Satisfacción

UPDATE seguridad.entradas_menu_principal
SET orden = 7
WHERE id_menu_principal = 712; -- Seguimiento de Venta

UPDATE seguridad.entradas_menu_principal
SET orden = 8
WHERE id_menu_principal = 713; -- Tratamiento de cliente

UPDATE seguridad.entradas_menu_principal
SET id_padre = 715
WHERE id_menu_principal = 714; -- Lista de Espera

UPDATE seguridad.entradas_menu_principal
SET orden = 1
WHERE id_menu_principal = 714; -- Lista de Espera