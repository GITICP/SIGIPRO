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
CREATE TABLE ventas.contrato_comercializacion(
	id_contrato serial NOT NULL,
	nombre character varying(100) NOT NULL,
	fecha date NOT NULL,
	observaciones character varying(150)
);
CREATE TABLE ventas.producto_venta(
	id_producto serial NOT NULL,
	nombre character varying(100) NOT NULL,
	descripcion character varying(120),
	cantidad_stock integer NOT NULL,
	precio integer NOT NULL
);

--
CREATE TABLE ventas.encuesta_satisfaccion(
	id_encuesta serial NOT NULL,
	id_cliente integer NOT NULL,
	fecha date NOT NULL,
	observaciones character varying(100)
	-- documento
);
CREATE TABLE ventas.reunion_produccion(
	id_reunion serial NOT NULL,
	fecha date NOT NULL,
	id_usuarios_participantes integer[] NOT NULL,
	observaciones character varying(100)
	-- documento (minuta)
);

--Llaves primarias esquema de ventas 
ALTER TABLE ONLY ventas.cliente ADD CONSTRAINT pk_cliente PRIMARY KEY (id_cliente);
ALTER TABLE ONLY ventas.contactos_cliente ADD CONSTRAINT pk_contactos_cliente PRIMARY KEY (id_contacto);
ALTER TABLE ONLY ventas.contrato_comercializacion ADD CONSTRAINT pk_contrato_comercializacion PRIMARY KEY (id_contrato);

--Llaves foraneas esquema ventas

ALTER TABLE ONLY ventas.contactos_cliente ADD CONSTRAINT fk_contactos_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE CASCADE;

--Permisos asociados a ventas
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (701, '[Ventas]AdministrarModuloVentas', 'Permite gestionar el modulo de ventas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (702, '[Ventas]AdministrarClientes', 'Permite agregar/editar/eliminar clientes');
--Entradas del Menu de ventas
DELETE FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 700;
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (700, 0, 'Ventas', null);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (701, 700, 'Clientes', '/Ventas/Clientes', 1);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (702, 700, 'Contratos de Comercialización', '/Ventas/ContratoComercializacion', 2);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, orden) VALUES (703, 700, 'Proceso de Venta', 3);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (704, 703, 'Intenciones de Venta', '/Ventas/IntencionVenta', 1);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (705, 703, 'Cotizaciones', '/Ventas/Cotizacion', 2);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (706, 703, 'Órdenes de Compra', '/Ventas/OrdenCompra', 3);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (707, 703, 'Facturas', '/Ventas/Factura', 4);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (708, 703, 'Pagos', '/Ventas/Pago', 5);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (709, 700, 'Productos de Venta', '/Ventas/Producto_ventas', 4);

--Permisos del menu principal de ventas
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 701);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (702, 701);
