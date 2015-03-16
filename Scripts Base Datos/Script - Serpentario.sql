DROP SCHEMA IF EXISTS serpentario CASCADE;
CREATE SCHEMA serpentario;

CREATE TABLE serpentario.especies (
    id_especie serial NOT NULL,
    genero character varying(45) NOT NULL,
    especie character varying(45) NOT NULL
);

ALTER TABLE ONLY serpentario.especies ADD CONSTRAINT pk_especies PRIMARY KEY (id_especie);


--Permisos

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (40, '[Serpentario]AgregarEspecie', 'Permite agregar una especie al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (41, '[Serpentario]EliminarEspecie', 'Permite eliminar una especie al catálogo');

--Menu

UPDATE seguridad.entradas_menu_principal SET redirect = '/Serpentario/Especie' WHERE id_menu_principal = 300;


INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (301, 300, 'Especie', '/Serpentario/Especie');

------Permisos Menu Principal

--Especie
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (40, 301);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (41, 301);