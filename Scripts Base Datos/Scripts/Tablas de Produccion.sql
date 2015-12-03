--Esquema produccion
--HACEN FALTA LOS PERMISOS, ENTRADAS DEL MENU, PERMISOS DEL MENU DE LAS TABLAS DINÁMICAS
DROP SCHEMA IF EXISTS produccion CASCADE;
CREATE SCHEMA produccion;
--Tablas esquema produccion
CREATE TABLE produccion.catalogo_pt(
	id_catalogo_pt serial NOT NULL,
	nombre character varying(100) NOT NULL,
	descripcion character varying(200) 
);

CREATE TABLE produccion.categoria_aa( 
	id_categoria_aa serial NOT NULL,
	nombre character varying(100) NOT NULL,
	descripcion character varying(200)
 );

 CREATE TABLE produccion.formula_maestra( 
	id_formula_m serial NOT NULL,
	nombre character varying(100) NOT NULL,
	descripcion character varying(200)
 );

 CREATE TABLE produccion.despacho( 
	id_despacho serial NOT NULL,
	fecha date NOT NULL,
	destino character varying(100) NOT NULL,
	id_coordinador integer, 
	fecha_coordinador date,
	estado_coordinador boolean NOT NULL,
	id_regente integer, 
	fecha_regente date,
	estado_regente boolean NOT NULL,
	total int
	);

 CREATE TABLE produccion.inoculo( 
	id_inoculo serial NOT NULL,
	fecha_preparacion date NOT NULL,
	identificador character varying(20) NOT NULL,
	encargado_preparacion integer NOT NULL,
	id_veneno integer NOT NULL,
	peso integer NOT NULL
 );

  CREATE TABLE produccion.inventario_pt( 
	id_inventario_pt serial NOT NULL,
	fecha_vencimiento date NOT NULL,
	lote character varying(26) NOT NULL,
	id_protocolo integer NOT NULL,
	cantidad integer NOT NULL,
	id_catalogo_pt integer NOT NULL,
	cantidad_disponible integer
 );

 CREATE TABLE produccion.protocolo( 
	id_protocolo serial NOT NULL,
	nombre character varying(100) NOT NULL,
	descripcion character varying(200) NOT NULL,
	id_formula_m integer NOT NULL,
	id_catalogo_pt integer NOT NULL,
	aprobacion_calidad character varying(20) NOT NULL,
	aprobacion_direccion character varying(20) NOT NULL,
	version_p character varying(10) NOT NULL
 );

  CREATE TABLE produccion.paso_protocolo(
	id_pxp serial NOT NULL,
	id_protocolo integer NOT NULL,
	id_paso integer NOT NULL
);

 CREATE TABLE produccion.reservacion( 
	id_reservacion serial NOT NULL,
	hasta date NOT NULL,
	observaciones character varying(200),
	total int
 );
 
 CREATE TABLE produccion.salida_ext( 
	id_salida serial NOT NULL,
	fecha date NOT NULL,
	tipo character varying(30) NOT NULL,
	observaciones character varying(200),
	total int
 );

  CREATE TABLE produccion.veneno_produccion( 
	id_veneno serial NOT NULL,
	veneno character varying(40) NOT NULL, 
	fecha_ingreso date NOT NULL,
	cantidad integer NOT NULL,
	observaciones character varying(200) NOT NULL
 );
----TABLAS MUCHOS A MUCHOS INVENTARIO-----
CREATE TABLE produccion.despachos_inventario(
	id_dxi serial NOT NULL,
	id_despacho int NOT NULL,
	id_inventario_pt int NOT NULL,
	cantidad int NOT NULL
);
CREATE TABLE produccion.salidas_inventario(
	id_sxi serial NOT NULL,
	id_salida int NOT NULL,
	id_inventario_pt int NOT NULL,
	cantidad int NOT NULL
);
CREATE TABLE produccion.reservaciones_inventario(
	id_rxi serial NOT NULL,
	id_reservacion int NOT NULL,
	id_inventario_pt int NOT NULL,
	cantidad int NOT NULL
);

----TABLAS DINÁMICAS-----
 CREATE TABLE produccion.paso (
    id_paso serial  NOT NULL,
    estructura xml  NULL,
    nombre character varying(40) NOT NULL,
    requiere_ap boolean Not NULL,
    version_paso character varying(10) NOT NULL,
    CONSTRAINT pk_paso PRIMARY KEY (id_paso)
);

 CREATE TABLE produccion.actividad_apoyo (
    id_actividad serial  NOT NULL,
    estructura xml  NULL,
    nombre character varying(40) NOT NULL,
    id_categoria_aa  integer NOT NULL,
    estado_calidad character varying(10) NOT NULL,
    estado_direccion character varying(10) NOT NULL,
    version_paso character varying(10) NOT NULL,
    CONSTRAINT pk_actividada PRIMARY KEY (id_actividad)
);

 CREATE TABLE produccion.respuesta_pxp (
    id_respuesta serial  NOT NULL,
    respuesta xml  NOT NULL,
    id_pxp integer NOT NULL,
    CONSTRAINT pk_respuesta_pxp PRIMARY KEY (id_respuesta)
);

CREATE TABLE produccion.respuesta_aa (
    id_respuesta serial  NOT NULL,
    respuesta xml  NOT NULL,
    id_pxp integer NOT NULL,
    id_actividad integer NOT NULL,
    CONSTRAINT pk_respuesta_aa PRIMARY KEY (id_respuesta)
);
 
--Llaves primarias esquema de produccion 
ALTER TABLE ONLY produccion.catalogo_pt ADD CONSTRAINT pk_catalogo_pt PRIMARY KEY (id_catalogo_pt);
ALTER TABLE ONLY produccion.categoria_aa ADD CONSTRAINT pk_categoria_aa PRIMARY KEY (id_categoria_aa);
ALTER TABLE ONLY produccion.despacho ADD CONSTRAINT pk_despacho PRIMARY KEY (id_despacho);
ALTER TABLE ONLY produccion.formula_maestra ADD CONSTRAINT pk_fm PRIMARY KEY (id_formula_m);
ALTER TABLE ONLY produccion.inoculo ADD CONSTRAINT pk_inoculo PRIMARY KEY (id_inoculo);
ALTER TABLE ONLY produccion.inventario_pt ADD CONSTRAINT pk_inventario_pt PRIMARY KEY (id_inventario_pt);
ALTER TABLE ONLY produccion.paso_protocolo ADD CONSTRAINT pk_pxp PRIMARY KEY (id_pxp);
ALTER TABLE ONLY produccion.protocolo ADD CONSTRAINT pk_protocolo PRIMARY KEY (id_protocolo);
ALTER TABLE ONLY produccion.reservacion ADD CONSTRAINT pk_reservacion PRIMARY KEY (id_reservacion);
ALTER TABLE ONLY produccion.salida_ext ADD CONSTRAINT pk_salida_ext PRIMARY KEY (id_salida);
ALTER TABLE ONLY produccion.veneno_produccion ADD CONSTRAINT pk_veneno_produccion PRIMARY KEY (id_veneno);
ALTER TABLE ONLY produccion.reservaciones_inventario ADD CONSTRAINT pk_rxi PRIMARY KEY (id_rxi);
ALTER TABLE ONLY produccion.salidas_inventario ADD CONSTRAINT pk_sxi PRIMARY KEY (id_sxi);
ALTER TABLE ONLY produccion.despachos_inventario ADD CONSTRAINT pk_dxi PRIMARY KEY (id_dxi);

--Llaves foraneas esquema produccion

ALTER TABLE ONLY produccion.despacho ADD CONSTRAINT fk_cu FOREIGN KEY (id_coordinador) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.despacho ADD CONSTRAINT fk_ru FOREIGN KEY (id_regente) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.inoculo ADD CONSTRAINT fk_encargado_preparacion FOREIGN KEY (encargado_preparacion) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.inoculo ADD CONSTRAINT fk_id_veneno FOREIGN KEY (id_veneno) REFERENCES produccion.veneno_produccion (id_veneno) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.inventario_pt ADD CONSTRAINT fk_id_protocolo FOREIGN KEY (id_protocolo) REFERENCES produccion.protocolo (id_protocolo) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.inventario_pt ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_catalogo_pt) REFERENCES produccion.catalogo_pt (id_catalogo_pt) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.paso_protocolo ADD CONSTRAINT fk_id_paso FOREIGN KEY (id_paso) REFERENCES produccion.paso(id_paso) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.paso_protocolo ADD CONSTRAINT fk_id_protocolo FOREIGN KEY (id_protocolo) REFERENCES produccion.protocolo(id_protocolo) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.protocolo ADD CONSTRAINT fk_id_formula_m FOREIGN KEY (id_formula_m) REFERENCES produccion.formula_maestra(id_formula_m) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.protocolo ADD CONSTRAINT fk_id_catalago_pt FOREIGN KEY (id_catalogo_pt) REFERENCES produccion.catalogo_pt(id_catalogo_pt) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.actividad_apoyo ADD CONSTRAINT fk_aac FOREIGN KEY (id_categoria_aa) REFERENCES produccion.categoria_aa(id_categoria_aa) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.respuesta_pxp ADD CONSTRAINT fk_pxpr FOREIGN KEY (id_pxp) REFERENCES produccion.paso_protocolo(id_pxp) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.respuesta_aa ADD CONSTRAINT fk_pxpraa FOREIGN KEY (id_pxp) REFERENCES produccion.paso_protocolo(id_pxp) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.respuesta_aa ADD CONSTRAINT fk_acraa FOREIGN KEY (id_actividad) REFERENCES produccion.actividad_apoyo(id_actividad) ON DELETE SET NULL;

ALTER TABLE ONLY produccion.despachos_inventario ADD CONSTRAINT fk_pt FOREIGN KEY (id_inventario_pt) REFERENCES produccion.inventario_pt(id_inventario_pt) ON DELETE CASCADE;
ALTER TABLE ONLY produccion.reservaciones_inventario ADD CONSTRAINT fk_rpt FOREIGN KEY (id_inventario_pt) REFERENCES produccion.inventario_pt(id_inventario_pt) ON DELETE CASCADE;
ALTER TABLE ONLY produccion.salidas_inventario ADD CONSTRAINT fk_spt FOREIGN KEY (id_inventario_pt) REFERENCES produccion.inventario_pt(id_inventario_pt) ON DELETE CASCADE;
ALTER TABLE ONLY produccion.despachos_inventario ADD CONSTRAINT fk_ptd FOREIGN KEY (id_despacho) REFERENCES produccion.despacho(id_despacho) ON DELETE CASCADE;
ALTER TABLE ONLY produccion.reservaciones_inventario ADD CONSTRAINT fk_rptr FOREIGN KEY (id_reservacion) REFERENCES produccion.reservacion(id_reservacion) ON DELETE CASCADE;
ALTER TABLE ONLY produccion.salidas_inventario ADD CONSTRAINT fk_spts FOREIGN KEY (id_salida) REFERENCES produccion.salida_ext(id_salida) ON DELETE CASCADE;
--Indices unicos esquema produccion

--Permisos asociados a produccion
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (601, '[produccion]AdministrarModuloProduccion', 'Permite gestionar el modulo de produccion');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (602, '[produccion]AdministrarInventarioPT', 'Permite agregar/editar/eliminar inventario de producto terminado');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (603, '[produccion]AutorizarDespachosRegente', 'Permite autorizar despachos por parte del Regente Farmacéutico');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (604, '[produccion]AdministrarInoculos', 'Permite agregar/editar/eliminar inoculos');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (605, '[produccion]AdministrarVenenoProduccion', 'Permite agregar/editar/eliminar venenos de produccion');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (606, '[produccion]AdministrarCatalogoPT', 'Permite agregar/editar/eliminar CatalogoPT');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (607, '[produccion]AutorizarDespachosCoordinador', 'Permite autorizar despachos por parte del Coordinador');



INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (630, '[produccion]AdministrarCategoriaAA', 'Permite agregar/editar/eliminar categorías de actividades de apoyo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (635, '[produccion]AdministrarFormulaMaestra', 'Permite agregar/editar/eliminar fórmulas maestras.');


--Entradas del Menu de produccion
DELETE FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 600;
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (600, 0, 'Produccion', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (602, 600, 'Inventario de Producto T.', '/Produccion/Inventario_PT');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (604, 600, 'Inoculo', '/Produccion/Inoculo'); 
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (605, 600, 'Venenos de Produccion', '/Produccion/Veneno_Produccion');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (606, 600, 'Catalogo de Producto T.', '/Produccion/Catalogo_PT');

--Permisos del menu principal de produccion
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (602, 602);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (603, 602);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (607, 602);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (604, 604);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (605, 605);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (606, 606);

