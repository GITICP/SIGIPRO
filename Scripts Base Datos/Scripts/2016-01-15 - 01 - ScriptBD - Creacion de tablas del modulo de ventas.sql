DROP SCHEMA IF EXISTS ventas CASCADE;
CREATE SCHEMA ventas;
--Tablas esquema ventas
CREATE TABLE ventas.cliente(
	id_cliente serial NOT NULL,
	nombre character varying(80) NOT NULL,
	tipo character varying(20) NOT NULL,
	pais character varying(30) NOT NULL 
);
CREATE TABLE ventas.contactos_cliente(
	id_contacto serial NOT NULL,
	id_cliente integer NOT NULL,
	nombre character varying(80) NOT NULL,
	telefono character varying(12) NOT NULL,
	telefono2 character varying(12),
	correo_electronico character varying(70) NOT NULL,
	correo_electronico2 character varying(70) 
);

--Llaves primarias esquema de ventas 
ALTER TABLE ONLY ventas.cliente ADD CONSTRAINT pk_cliente PRIMARY KEY (id_cliente);
ALTER TABLE ONLY ventas.contactos_cliente ADD CONSTRAINT pk_contactos_cliente PRIMARY KEY (id_contacto);

--Llaves foraneas esquema ventas

ALTER TABLE ONLY ventas.contactos_cliente ADD CONSTRAINT fk_contactos_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;

--Permisos asociados a ventas
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (701, '[Ventas]AdministrarModuloVentas', 'Permite gestionar el modulo de ventas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (702, '[Ventas]AdministrarClientes', 'Permite agregar/editar/eliminar clientes');
--Entradas del Menu de ventas
DELETE FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 700;
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (700, 0, 'Ventas', null);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (701, 700, 'Clientes', null, 1);

--Permisos del menu principal de ventas
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 701);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (702, 701);