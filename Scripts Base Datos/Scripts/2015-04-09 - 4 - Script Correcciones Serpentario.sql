ALTER TABLE SERPENTARIO.SERPIENTES
ADD COLUMN NUMERO_SERPIENTE integer NOT NULL UNIQUE;

CREATE UNIQUE INDEX i_numero_serpiente ON serpentario.serpientes USING btree (numero_serpiente);

ALTER TABLE SERPENTARIO.LOTE
ADD COLUMN NUMERO_LOTE CHARACTER VARYING(10) NOT NULL UNIQUE;

ALTER TABLE SERPENTARIO.COLECCION_HUMEDA
ADD COLUMN NUMERO_COLECCION_HUMEDA integer NOT NULL UNIQUE;

ALTER TABLE SERPENTARIO.CATALOGO_TEJIDO
ADD COLUMN NUMERO_CATALOGO_TEJIDO integer NOT NULL UNIQUE;

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (307, 297, 'Colección Húmeda', '/Serpentario/ColeccionHumeda');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (308, 297, 'CatálogoTejido', '/Serpentario/CatalogoTejido');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (314, 307);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (314, 308);

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (322, '[Serpentario]RegistrarExtraccion', 'Permite registrar todos los pasos de una extraccion');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (322, 303);
