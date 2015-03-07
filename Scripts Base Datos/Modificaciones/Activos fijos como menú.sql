-- Activo fijo como menú propio y no submenú de Bodegas

-- Actualización para que sea el tag padre de los activos fijos
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 1100,
    id_padre = 0,
    redirect = 'Activos Fijos'
WHERE id_menu_principal = 103;

-- Ingreso de las ubicaciones y del menú de activo fijo propiamente

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (1101, 1100, 'Activos', '/ActivosFijos/Activos');

UPDATE seguridad.entradas_menu_principal 
SET id_menu_principal = 1102, 
    id_padre = 1100,
    tag = 'Ubicaciones',
    redirect = '/ActivosFijos/Ubicaciones'
WHERE id_menu_principal = 104;

-- Actualización de los permisos

    -- Activos Fijos
UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 1101
WHERE id_permiso in (31,32,33);

    -- Ubicaciones
UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 1102
WHERE id_permiso in (34,35,36);