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

-- Modificación del orden de submenú de bodegas
    
    -- Pivote de ids

        -- Catálogo Interno
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 25001
WHERE id_menu_principal = 101;
        -- Catálogo Externo
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 25002
WHERE id_menu_principal = 102;
        -- Ubicaciones
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 25005
WHERE id_menu_principal = 105;
        -- Solicitudes
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 25006
WHERE id_menu_principal = 106;
        -- Ingresos
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 25007
WHERE id_menu_principal = 107;
        -- Inventarios
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 25008
WHERE id_menu_principal = 108;

    -- Reordenación

        -- Catálogo Interno
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 102
WHERE id_menu_principal = 25001;

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 102
WHERE id_permiso in (11,12,13);


        -- Catálogo Externo
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 103
WHERE id_menu_principal = 25002;

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 103
WHERE id_permiso in (21,22,23);


        -- Ubicaciones
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 101
WHERE id_menu_principal = 25005;

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 101
WHERE id_permiso in (37,39,39);

        -- Solicitudes
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 106
WHERE id_menu_principal = 25006;

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 106
WHERE id_permiso in (24,25,26);


        -- Ingresos
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 104
WHERE id_menu_principal = 25007;

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 104
WHERE id_permiso in (27,28,29);


        -- Inventarios
UPDATE seguridad.entradas_menu_principal
SET id_menu_principal = 105
WHERE id_menu_principal = 25008;

UPDATE seguridad.permisos_menu_principal
SET id_menu_principal = 105
WHERE id_permiso = 30;