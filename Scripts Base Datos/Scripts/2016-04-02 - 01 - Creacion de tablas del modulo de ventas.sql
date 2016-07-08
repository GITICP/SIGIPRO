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
	id_cliente int NOT NULL,
	fechaInicial date NOT NULL,
	fechaRenovacion date NOT NULL,
	observaciones character varying(150)
);
CREATE TABLE ventas.producto_venta(
	id_producto serial NOT NULL,
	nombre character varying(100) NOT NULL,
	descripcion character varying(120),
	lote character varying(120)
);
CREATE TABLE ventas.intencion_venta(
	id_intencion serial NOT NULL,
	id_cliente integer NOT NULL,	
	observaciones character varying(100),
	estado character varying(20) NOT NULL
);
CREATE TABLE ventas.producto_intencion(
	id_producto integer NOT NULL,
	id_intencion integer NOT NULL,
	cantidad integer NOT NULL,
	posible_fecha_despacho date NOT NULL
);
CREATE TABLE ventas.cotizacion(
	id_cotizacion serial NOT NULL,
	id_intencion integer NOT NULL,
	id_cliente integer NOT NULL,
	total integer NOT NULL,
	flete integer NOT NULL
);
CREATE TABLE ventas.producto_cotizacion(
	id_producto integer NOT NULL,
	id_cotizacion integer NOT NULL,
	cantidad integer NOT NULL,
	precio integer NOT NULL
);
CREATE TABLE ventas.orden_compra(
	id_orden serial NOT NULL,
	id_cliente integer NOT NULL,
	id_cotizacion integer,
	id_intencion integer,
	rotulacion character varying(100),
	estado character varying(20)
);
CREATE TABLE ventas.producto_orden(
	id_producto integer NOT NULL,
	id_orden integer NOT NULL,
	cantidad integer NOT NULL
);
CREATE TABLE ventas.factura(
	id_factura serial NOT NULL,
	id_cliente integer NOT NULL,
	id_orden integer,
	fecha date NOT NULL,
	monto integer NOT NULL,
	fecha_vencimiento date NOT NULL,
	documento1 character varying(500),
	documento2 character varying(500),
	documento3 character varying(500),
	documento4 character varying(500),
	tipo character varying(20)
);
CREATE TABLE ventas.pago(
	id_pago serial NOT NULL,
	id_factura integer NOT NULL,
	pago integer NOT NULL,
	monto_pendiente integer NOT NULL
);
CREATE TABLE ventas.reunion_produccion(
	id_reunion serial NOT NULL,
	fecha date NOT NULL,
	observaciones character varying(100),
	minuta character varying(500) NOT NULL,
	minuta2 character varying(500) 
);
CREATE TABLE ventas.participantes_reunion(
	id_reunion int NOT NULL,
	id_usuario int NOT NULL
);
CREATE TABLE ventas.encuesta_satisfaccion(	
	id_encuesta serial NOT NULL,
	id_cliente integer NOT NULL,
	fecha date NOT NULL,
	observaciones character varying(100),
	documento character varying(500)
);
CREATE TABLE ventas.seguimiento_venta(	
	id_seguimiento serial NOT NULL,
	id_cliente integer NOT NULL,
	id_factura integer NOT NULL,
	observaciones character varying(100),
	tipo character varying(50) NOT NULL,
	documento character varying(500)
);
CREATE TABLE ventas.tratamiento(
	id_tratamiento serial NOT NULL,
	id_cliente integer NOT NULL,
	fecha date NOT NULL,
	observaciones character varying(200),
	estado character varying(40)
);
CREATE TABLE ventas.lista(
	id_enlistado serial NOT NULL,
	id_cliente integer NOT NULL,
	fecha_solicitud date NOT NULL,
	fecha_atencion date 
);

--Llaves primarias esquema de ventas 
ALTER TABLE ONLY ventas.cliente ADD CONSTRAINT pk_cliente PRIMARY KEY (id_cliente);
ALTER TABLE ONLY ventas.contactos_cliente ADD CONSTRAINT pk_contactos_cliente PRIMARY KEY (id_contacto);
ALTER TABLE ONLY ventas.contrato_comercializacion ADD CONSTRAINT pk_contrato_comercializacion PRIMARY KEY (id_contrato);
ALTER TABLE ONLY ventas.producto_venta ADD CONSTRAINT pk_producto_venta PRIMARY KEY (id_producto);
ALTER TABLE ONLY ventas.intencion_venta ADD CONSTRAINT pk_intencion_venta PRIMARY KEY (id_intencion);
ALTER TABLE ONLY ventas.producto_intencion ADD CONSTRAINT pk_producto_intencion PRIMARY KEY (id_producto, id_intencion);
ALTER TABLE ONLY ventas.cotizacion ADD CONSTRAINT pk_cotizacion_venta PRIMARY KEY (id_cotizacion);
ALTER TABLE ONLY ventas.producto_cotizacion ADD CONSTRAINT pk_producto_cotizacion PRIMARY KEY (id_producto, id_cotizacion);
ALTER TABLE ONLY ventas.orden_compra ADD CONSTRAINT pk_orden_compra PRIMARY KEY (id_orden);
ALTER TABLE ONLY ventas.producto_orden ADD CONSTRAINT pk_producto_orden PRIMARY KEY (id_producto, id_orden);
ALTER TABLE ONLY ventas.factura ADD CONSTRAINT pk_factura PRIMARY KEY (id_factura);
ALTER TABLE ONLY ventas.pago ADD CONSTRAINT pk_pago PRIMARY KEY (id_pago);
ALTER TABLE ONLY ventas.reunion_produccion ADD CONSTRAINT pk_reunion_produccion PRIMARY KEY (id_reunion);
ALTER TABLE ONLY ventas.encuesta_satisfaccion ADD CONSTRAINT pk_encuesta_satisfaccion PRIMARY KEY (id_encuesta);
ALTER TABLE ONLY ventas.seguimiento_venta ADD CONSTRAINT pk_seguimiento_venta PRIMARY KEY (id_seguimiento);
ALTER TABLE ONLY ventas.tratamiento ADD CONSTRAINT pk_tratamiento PRIMARY KEY (id_tratamiento);
ALTER TABLE ONLY ventas.lista ADD CONSTRAINT pk_lista PRIMARY KEY (id_enlistado);

--Llaves foraneas esquema ventas

ALTER TABLE ONLY ventas.contactos_cliente ADD CONSTRAINT fk_contactos_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.contrato_comercializacion ADD CONSTRAINT fk_contrato_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.intencion_venta ADD CONSTRAINT fk_intenciones_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.producto_intencion ADD CONSTRAINT fk_id_intencion FOREIGN KEY (id_intencion) REFERENCES ventas.intencion_venta(id_intencion) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.producto_intencion ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES ventas.producto_venta(id_producto) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.cotizacion ADD CONSTRAINT fk_cotizacion_intencion FOREIGN KEY (id_intencion) REFERENCES ventas.intencion_venta(id_intencion) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.cotizacion ADD CONSTRAINT fk_cotizacion_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.producto_cotizacion ADD CONSTRAINT fk_id_cotizacion FOREIGN KEY (id_cotizacion) REFERENCES ventas.cotizacion(id_cotizacion) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.producto_cotizacion ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES ventas.producto_venta(id_producto) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.orden_compra ADD CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.orden_compra ADD CONSTRAINT fk_id_intencion FOREIGN KEY (id_intencion) REFERENCES ventas.intencion_venta(id_intencion) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.orden_compra ADD CONSTRAINT fk_id_cotizacion FOREIGN KEY (id_cotizacion) REFERENCES ventas.cotizacion(id_cotizacion) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.producto_orden ADD CONSTRAINT fk_id_orden FOREIGN KEY (id_orden) REFERENCES ventas.orden_compra(id_orden) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.producto_orden ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES ventas.producto_venta(id_producto) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.factura ADD CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.factura ADD CONSTRAINT fk_id_orden FOREIGN KEY (id_orden) REFERENCES ventas.orden_compra(id_orden) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.pago ADD CONSTRAINT fk_id_factura FOREIGN KEY (id_factura) REFERENCES ventas.factura(id_factura) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.participantes_reunion ADD CONSTRAINT fk_id_reunion FOREIGN KEY (id_reunion) REFERENCES ventas.reunion_produccion(id_reunion) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.participantes_reunion ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.encuesta_satisfaccion ADD CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.seguimiento_venta ADD CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.seguimiento_venta ADD CONSTRAINT fk_id_factura FOREIGN KEY (id_factura) REFERENCES ventas.factura(id_factura) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.tratamiento ADD CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;
ALTER TABLE ONLY ventas.lista ADD CONSTRAINT fk_id_cliente FOREIGN KEY (id_cliente) REFERENCES ventas.cliente(id_cliente) ON DELETE SET NULL;

--Permisos asociados a ventas
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (701, '[Ventas]AdministrarModuloVentas', 'Permite gestionar el modulo de ventas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (702, '[Ventas]AdministrarClientes', 'Permite agregar/editar/eliminar clientes');
--Entradas del Menu de ventas
DELETE FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 700;
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (700, 0, 'Ventas', null);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (701, 700, 'Clientes', '/Ventas/Clientes', 1);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (702, 700, 'Productos de Venta', '/Ventas/Producto_ventas', 2);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, orden) VALUES (703, 700, 'Proceso de Venta', 3);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (704, 703, 'Solicitudes o Intenciones de Venta', '/Ventas/IntencionVenta', 1);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (705, 703, 'Cotizaciones', '/Ventas/Cotizacion', 2);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (706, 703, 'Órdenes de Compra', '/Ventas/OrdenCompra', 3);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (707, 703, 'Facturas', '/Ventas/Factura', 4);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (708, 703, 'Pagos', '/Ventas/Pago', 5);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (709, 700, 'Contratos de Comercialización', '/Ventas/ContratoComercializacion', 4);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (710, 700, 'Reuniones de Producción', '/Ventas/ReunionProduccion', 5);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (711, 700, 'Encuestas de Satisfacción', '/Ventas/EncuestaSatisfaccion', 6);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (712, 700, 'Seguimientos de Venta', '/Ventas/SeguimientoVenta', 7);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (713, 700, 'Tratamientos de Clientes', '/Ventas/Tratamiento', 8);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (714, 700, 'Listas de Espera', '/Ventas/ListaEspera', 9);

--Permisos del menu principal de ventas
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 701);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (702, 701);
