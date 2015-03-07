-- Activo fijo como menú propio y no submenú de Bodegas

UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 1100,
    id_padre = 0,
    redirect = 'Activos Fijos'
WHERE id_menu_principal = 103;

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (1101, 1100, 'Activos', '/ActivosFijos/Activos');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (1102, 1100, 'Ubicaciones', '/ActivosFijos/Ubicaciones');

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 1101
WHERE id_permiso in (31,32,33);

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 1102
WHERE id_permiso in (34,35,36);