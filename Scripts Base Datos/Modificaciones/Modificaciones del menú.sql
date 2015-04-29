-- Modificaciones de menú para hacerlo de sub niveles

    -- Registro padre de Bioterio
INSERT INTO seguridad.entradas_menu_principal (id_menu_principal, id_padre, tag, redirect) 
    VALUES (1300, 0, 'Bioterio', null);

    -- Registro padre de Administración
INSERT INTO seguridad.entradas_menu_principal (id_menu_principal, id_padre, tag, redirect) 
    VALUES (1400, 0, 'Administración', null);

    -- Actualizaciones de Ratonera y Conejera para estar debajo de Bioterio
UPDATE seguridad.entradas_menu_principal SET id_padre = 1300, redirect = null where id_menu_principal = 200;
UPDATE seguridad.entradas_menu_principal SET id_padre = 1300, redirect = null where id_menu_principal = 250;

    -- Actualizaciones de Configuración, Seguridad y Bitácora para estar debajo de Administración
UPDATE seguridad.entradas_menu_principal SET id_padre = 1400, redirect = null where id_menu_principal = 800;
UPDATE seguridad.entradas_menu_principal SET id_padre = 1400, redirect = null where id_menu_principal = 900;
UPDATE seguridad.entradas_menu_principal SET id_padre = 1400, redirect = null where id_menu_principal = 1200;

    -- Actualización de todos los padres para que no tengan redirect
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 100;
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 300;
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 400;
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 800;
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 900;
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 1000;
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 1100;
UPDATE seguridad.entradas_menu_principal SET redirect = null where id_menu_principal = 1200;


