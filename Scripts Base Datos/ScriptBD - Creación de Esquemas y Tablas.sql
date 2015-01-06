--########ESQUEMA DE SEGURIDAD########
DROP SCHEMA IF EXISTS seguridad CASCADE;
CREATE SCHEMA seguridad;
--Tablas de esquema de seguridad
CREATE TABLE seguridad.entradas_menu_principal (
    id_menu_principal serial NOT NULL,
    id_padre integer,
    tag character varying(45),
    permiso integer,
    redirect character varying(200)
);

CREATE TABLE seguridad.permisos (
    id_permiso serial NOT NULL,
    nombre character varying(45),
    descripcion character varying(500)
);

CREATE TABLE seguridad.permisos_roles (
    id_rol integer NOT NULL,
    id_permiso integer NOT NULL
);

CREATE TABLE seguridad.roles (
    id_rol serial NOT NULL,
    nombre character varying(45),
    descripcion character varying(500)
);

CREATE TABLE seguridad.roles_usuarios (
    id_usuario integer NOT NULL,
    id_rol integer NOT NULL,
    fecha_activacion date,
    fecha_desactivacion date
);

CREATE TABLE seguridad.usuarios (
    id_usuario serial NOT NULL,
    nombre_usuario character varying(45),
    contrasena character varying(45),
    correo character varying(45),
    nombre_completo character varying(200),
    cedula character varying(45),
    departamento character varying(200),
    puesto character varying(200),
    fecha_activacion date,
    fecha_desactivacion date,
    estado boolean
);

--Llaves primarias esquema seguridad
ALTER TABLE ONLY seguridad.entradas_menu_principal ADD CONSTRAINT pk_entradas_menu_principal PRIMARY KEY (id_menu_principal);
ALTER TABLE ONLY seguridad.permisos ADD CONSTRAINT pk_permisos PRIMARY KEY (id_permiso);
ALTER TABLE ONLY seguridad.permisos_roles ADD CONSTRAINT pk_permisos_roles PRIMARY KEY (id_rol, id_permiso);
ALTER TABLE ONLY seguridad.roles ADD CONSTRAINT pk_roles PRIMARY KEY (id_rol);
ALTER TABLE ONLY seguridad.roles_usuarios ADD CONSTRAINT pk_roles_usuaios PRIMARY KEY (id_usuario, id_rol);
ALTER TABLE ONLY seguridad.usuarios ADD CONSTRAINT pk_usuarios PRIMARY KEY (id_usuario);

--Indices unicos esquema seguridad
CREATE UNIQUE INDEX i_cedula ON seguridad.usuarios USING btree (cedula);
CREATE UNIQUE INDEX i_nombre_usuario ON seguridad.usuarios USING btree (nombre_usuario);
CREATE UNIQUE INDEX i_correo ON seguridad.usuarios USING btree (correo);
CREATE UNIQUE INDEX i_nombre_permiso ON seguridad.permisos USING btree (nombre);
CREATE UNIQUE INDEX i_nombre_rol ON seguridad.roles USING btree (nombre);
CREATE UNIQUE INDEX i_tag_emp ON seguridad.entradas_menu_principal USING btree (tag);

--Llaves foraneas esquema seguridad
ALTER TABLE ONLY seguridad.permisos_roles ADD CONSTRAINT fk_id_permiso FOREIGN KEY (id_permiso) REFERENCES seguridad.permisos(id_permiso);
ALTER TABLE ONLY seguridad.roles_usuarios ADD CONSTRAINT fk_id_rol FOREIGN KEY (id_rol) REFERENCES seguridad.roles(id_rol);
ALTER TABLE ONLY seguridad.permisos_roles ADD CONSTRAINT fk_id_rol FOREIGN KEY (id_rol) REFERENCES seguridad.roles(id_rol);
ALTER TABLE ONLY seguridad.roles_usuarios ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY seguridad.entradas_menu_principal ADD CONSTRAINT fk_permiso FOREIGN KEY (permiso) REFERENCES seguridad.permisos(id_permiso);

--######ESQUEMA bodega######
DROP SCHEMA IF EXISTS bodega CASCADE;
CREATE SCHEMA bodega;

--Tablas esquema bodega
CREATE TABLE bodega.activos_fijos ( 
	id_activo_fijo serial NOT NULL,
	placa integer NOT NULL,
	equipo character varying(45) NOT NULL,
	marca character varying(45),
	ubicacion character varying(45) NOT NULL
 );

CREATE TABLE bodega.catalogos_internos(
	id_producto_int serial NOT NULL,
	codigo_icp character varying(45) NOT NULL,
	id_tipo integer NOT NULL,
	stock_minimo integer NOT NULL,
	stock_maximo integer NOT NULL,
	seccion character varying(45) NOT NULL,
	ubicacion character varying(45),
	presentacion character varying(45) NOT NULL
 );

CREATE TABLE bodega.catalogos_externos(
	id_producto_ext serial NOT NULL,
	producto character varying(45) NOT NULL,
	codigo_externo character varying(45),
	marca character varying(45),
	inf_proveedor character varying(500)
 );


CREATE TABLE bodega.catalogos_externos_por_catalogos_internos ( 
	id_producto_ext integer,
	id_producto_int integer
 );


CREATE TABLE bodega.ingresos ( 
	id_ingreso serial NOT NULL,
	id_producto_int integer,
     	fecha_ingreso date NOT NULL,
	cantidad integer NOT NULL,
	fecha_vencimiento date NOT NULL,
	estado character varying(45),
	precio integer
 ); 

CREATE TABLE bodega.inventarios ( 
	id_inventario serial NOT NULL,
	id_producto_int integer,
     	stock_actual integer NOT NULL
 ); 

CREATE TABLE bodega.reactivos ( 
	id_reactivo serial NOT NULL,
	id_producto_int integer,
     	numero_cas character varying(45),
	formula_quimica character varying(45),
	familia character varying(45),
	cantidad_botella_bodega integer,
	cantidad_botella_lab integer,
	volumen_bodega integer,
	volumen_lab integer
 ); 

CREATE TABLE bodega.solicitudes ( 
	id_solicitud serial NOT NULL,
	id_usuario integer NOT NULL ,
     	fecha_solicitud date NOT NULL
 ); 

CREATE TABLE bodega.detalles_solicitudes ( 
	id_detalle_solicitud serial NOT NULL,
	id_solicitud integer,
	id_producto_int integer,
	estado character varying(45) NOT NULL,
     	cantidad integer NOT NULL,
	fecha_entrega date NOT NULL
 ); 

CREATE TABLE bodega.sub_bodegas ( 
	id_sub_bodega serial NOT NULL,
	id_seccion integer NOT NULL 
 ); 


CREATE TABLE bodega.inventarios_bodegas ( 
	id_inventario_bodega serial NOT NULL,
	id_sub_bodega integer,
	id_producto_int integer,
     	cantidad integer NOT NULL
 ); 

 --Llaves primarias esquema bodega
ALTER TABLE ONLY bodega.activos_fijos ADD CONSTRAINT pk_activos_fijos PRIMARY KEY (id_activo_fijo);
ALTER TABLE ONLY bodega.catalogos_internos ADD CONSTRAINT pk_catalogos_internos PRIMARY KEY (id_producto_int);
ALTER TABLE ONLY bodega.catalogos_externos ADD CONSTRAINT pk_catalogos_externos PRIMARY KEY (id_producto_ext);
ALTER TABLE ONLY bodega.catalogos_externos_por_catalogos_internos ADD CONSTRAINT pk_catalogos_externos_por_catalogos_internos PRIMARY KEY ( id_producto_ext,id_producto_int);
ALTER TABLE ONLY bodega.ingresos ADD CONSTRAINT pk_ingresos PRIMARY KEY (id_ingreso);
ALTER TABLE ONLY bodega.inventarios ADD CONSTRAINT pk_inventarios PRIMARY KEY (id_inventario);
ALTER TABLE ONLY bodega.reactivos ADD CONSTRAINT pk_reactivos PRIMARY KEY (id_reactivo);
ALTER TABLE ONLY bodega.solicitudes ADD CONSTRAINT pk_solicitudes PRIMARY KEY (id_solicitud);
ALTER TABLE ONLY bodega.detalles_solicitudes ADD CONSTRAINT pk_detalles_solicitudes PRIMARY KEY (id_detalle_solicitud);
ALTER TABLE ONLY bodega.sub_bodegas ADD CONSTRAINT pk_sub_bodegas PRIMARY KEY (id_sub_bodega);
ALTER TABLE ONLY bodega.inventarios_bodegas ADD CONSTRAINT pk_inventarios_bodegas PRIMARY KEY (id_inventario_bodega);
	
--Indices unicos esquema bodega
CREATE UNIQUE INDEX i_codigo_icp ON bodega.catalogos_internos USING btree (codigo_icp);

--Llaves foraneas esquema seguridad
ALTER TABLE ONLY bodega.catalogos_externos_por_catalogos_internos ADD CONSTRAINT fk_id_producto_ext FOREIGN KEY (id_producto_ext) REFERENCES bodega.catalogos_externos(id_producto_ext);
ALTER TABLE ONLY bodega.catalogos_externos_por_catalogos_internos ADD CONSTRAINT fk_id_producto_int FOREIGN KEY (id_producto_int) REFERENCES bodega.catalogos_internos(id_producto_int);
ALTER TABLE ONLY bodega.ingresos ADD CONSTRAINT fk_id_producto_int FOREIGN KEY (id_producto_int) REFERENCES bodega.catalogos_internos(id_producto_int);
ALTER TABLE ONLY bodega.inventarios ADD CONSTRAINT fk_id_producto_int FOREIGN KEY (id_producto_int) REFERENCES bodega.catalogos_internos(id_producto_int);
ALTER TABLE ONLY bodega.reactivos ADD CONSTRAINT fk_id_producto_int FOREIGN KEY (id_producto_int) REFERENCES bodega.catalogos_internos(id_producto_int);
ALTER TABLE ONLY bodega.detalles_solicitudes ADD CONSTRAINT fk_id_producto_int FOREIGN KEY (id_producto_int) REFERENCES bodega.catalogos_internos(id_producto_int);
ALTER TABLE ONLY bodega.detalles_solicitudes ADD CONSTRAINT fk_id_solicitud FOREIGN KEY (id_solicitud) REFERENCES bodega.solicitudes(id_solicitud);
ALTER TABLE ONLY bodega.inventarios_bodegas ADD CONSTRAINT fk_id_producto_int FOREIGN KEY (id_producto_int) REFERENCES bodega.catalogos_internos(id_producto_int);
ALTER TABLE ONLY bodega.inventarios_bodegas ADD CONSTRAINT fk_id_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);



/* INSERTS */
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (1, 'Administrar Usuarios', 'Puede acceder a las funcionalidades sobre usuarios dentro del Módulo de Seguridad. Además le permite realizar todas las operaciones sobre los mismos');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (2, 'Solicitar Veneno', 'Permite al usuario realizar solicitudes de Veneno');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (3, 'Visualizar Bodega', 'Permite al usuario acceder al módulo Bodega');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (4, 'Visualizar Bioterio', 'Permite al usuario acceder al módulo Bioterio');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (5, 'Visualizar Serpentario', 'Permite al usuario acceder al módulo Serpentario');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (6, 'Visualizar Caballeriza', 'Permite al usuario acceder al módulo Caballeriza');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (7, 'Visualizar Control de Calidad', 'Permite al usuario acceder al módulo Control de Calidad');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (8, 'Visualizar Producción', 'Permite al usuario acceder al módulo Producción');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (9, 'Visualizar Ventas', 'Permite al usuario acceder al módulo Ventas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (10, 'Visualizar Usuarios', 'Permite al usuario acceder al módulo Seguridad');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (11, 'Visualizar Roles', 'Permite al usuario acceder a ingresar roles.');

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (1, 0, 'Bodega', 3, null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (2, 0, 'Bioterio', 4, null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (3, 0, 'Serpentario', 5, null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (4, 0, 'Caballeriza', 6, null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (5, 0, 'Control de Calidad', 7, null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (6, 0, 'Producción', 8, null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (7, 0, 'Ventas', 9, null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (9, 8, 'Usuarios', 10, '/Seguridad/Usuarios.jsp');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (10, 8, 'Roles', 11, '/Seguridad/Roles.jsp');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, permiso, redirect) VALUES (8, 8, 'Seguridad', 10, null);

INSERT INTO seguridad.roles(id_rol, nombre, descripcion) VALUES (1, 'Administrador', 'mantemiento y acceso a todo el sistema');
INSERT INTO seguridad.roles(id_rol, nombre, descripcion) VALUES (2, 'Encargado de seguridad', 'el modulo de seguridad');
INSERT INTO seguridad.roles(id_rol, nombre, descripcion) VALUES (3, 'Encargado de bodegas', 'Manejo de Inventarios y gestion de solicitudes');
INSERT INTO seguridad.roles(id_rol, nombre, descripcion) VALUES (4, 'Encargado de bioterio', 'Gestion de formularios de alimentacion, reproduccion y gestion de solicitudes');

INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2, 1);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2, 2);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (1, 1);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (1, 10);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (1, 11);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2, 10);

INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (4, 'isaaclpez', md5('sigipro'), 'isaaclpez@gmail.com', 'Isaac Lopez', '4-2345-6789', 'Produccion', 'Jefe', '2014-12-01', '2014-12-01', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (6, 'jchaconbogarin', md5('sigipro'), 'jdcb92@gmail.com', 'Jose Daniel Chacón Bogarín', '1-1519-0808', 'Control de Calidad', 'Jefe', '2014-12-01', '2014-12-01', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (9, 'hmartinez', md5('sigipro'), 'hmartinez@icp.ucr.ac.cr', 'Hugo Martínez Vásquez', '1-0025-9908', 'Ventas', 'Vendedor', '2014-12-02', '2014-12-31', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (10, 'jrodriguez', md5('sigipro'), 'jrodriguez@icp.ucr.ac.cr', 'Juan Rodríguez Jara', '5-0098-6678', 'Bioterio', 'Administrador', '2014-12-04', '2014-01-10', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (19, 'cscabbia', md5('sigipro'), 'cscabbia@icp.ucr.ac.cr', 'Cristina Scabbia', '9-0089-2679', 'Bioterio', 'Jefe', '2014-12-10', '2014-12-19', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (2, 'dnjj14', md5('sigipro'), 'dnjj14@gmail.com', 'Daniel Thiel Jimenez', '2-2345-6789', 'Produccion', 'Jefe', '2014-12-01', '2014-12-01', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (3, 'ametico', md5('sigipro'), 'ametico@gmail.com', 'Amed Espinoza', '3-2345-6789', 'Produccion', 'Jefe', '2014-12-01', '2014-12-01', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (8, 'mjimenez', md5('sigipro'), 'mjimenez@icp.ucr.ac.cr', 'Maria Jiménez', '3-0987-8899', 'Ventas', 'Vendedora', '2014-12-03', '2014-12-04', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (11, 'ggalvez', md5('sigipro'), 'ggalvez@icp.ucr.ac.cr', 'Gabriela Gálvez', '7-8890-6672', 'Serpentario', 'Administrador', '2014-12-16', '2015-01-09', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (14, 'afraijanes', md5('sigipro'), 'afraijanes@icp.ucr.ac.cr', 'Alberto Fraijanes', '5-9980-6678', 'Ventas', 'Vendedor', '2014-12-02', '2014-12-05', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (15, 'mfernandez', md5('sigipro'), 'mfernandez@icp.ucr.ac.cr', 'Mariela Fernández', '1-0248-1111', 'Ventas', 'Vendedora', '2014-12-01', '2014-12-03', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (16, 'Prueba', md5('sigipro'), 'prueba@prueba.com', 'Prueba', '1-0808-1241', 'ProducciÃ³n', 'Productor', '2014-12-30', '2015-01-22', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (18, 'segprueba', md5('sigipro'), 'segprueba@icp.ucr.ac.cr', 'Segunda Prueba', '1-9980-1234', 'ProducciÃ³n', 'Administrador', '2014-12-24', '2015-01-10', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (12, 'ubarahona', md5('sigipro'), 'ubarahona@icp.ucr.ac.cr', 'Ulises Barahona', '6-0998-8876', 'Ventas', 'Jefe', '2014-12-01', '2014-12-19', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (13, 'Raquel UmaÃ±a', md5('sigipro'), 'rugalde@icp.ucr.ac.cr', 'rugalde', '7-0025-7890', 'Caballeriza', 'Cuidadora', '2014-12-08', '2014-12-27', false);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (7, 'bgreene', md5('sigipro'), 'mgreene@icp.ucr.ac.cr', 'Maggie Greene', '9-0456-6789', 'Serpentario', 'Administrador', '2014-12-01', '2015-01-10', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (20, 'hjimenez', md5('sigipro'), 'hjimenez@icp.ucr.ac.cr', 'Hugo Jiménez', '6-0900-1241', 'Producción', 'Jefe', '2015-01-17', '2015-01-17', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (5, 'estebav8', md5('sigipro'), 'estebav8@gmail.com', 'Esteban Aguilar Valverde', '5-2345-6789', 'Produccion', 'Jefe', '2014-12-01', '2014-12-01', true);
INSERT INTO seguridad.usuarios(id_usuario, nombre_usuario, contrasena, correo, nombre_completo, cedula, departamento, puesto, fecha_activacion, fecha_desactivacion, estado) VALUES (1, 'waltercoru', md5('sigipro'), 'waltercori21@gmail.com', 'Walter Cordero Urena', '1-2345-6710', 'Produccion', 'Jefe', '2014-12-01', '2014-12-01', true);

INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (2, 1, '2014-12-01', '2014-12-01');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (3, 3, '2014-12-01', '2014-12-01');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (3, 2, '2014-12-01', '2014-12-01');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (7, 4, '2014-12-09', '2015-01-10');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (5, 2, '2014-12-11', '2015-01-10');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (5, 1, '2014-12-03', '2015-01-02');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (11, 1, '2014-12-17', '2014-12-26');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (5, 3, '2014-12-08', '2015-02-05');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (1, 1, '2014-12-01', '2015-01-13');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (19, 1, '2014-12-16', '2014-12-26');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (20, 2, '2015-01-08', '2015-01-22');



