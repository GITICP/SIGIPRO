--########ESQUEMA DE SEGURIDAD########
DROP SCHEMA IF EXISTS seguridad CASCADE;
CREATE SCHEMA seguridad;
--Tablas de esquema de seguridad
CREATE TABLE seguridad.entradas_menu_principal (
    id_menu_principal serial NOT NULL,
    id_padre integer,
    tag character varying(45),
    redirect character varying(200)
);

CREATE TABLE seguridad.permisos_menu_principal(
    id_permiso integer NOT NULL,
    id_menu_principal integer NOT NULL
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

CREATE TABLE seguridad.secciones (
    id_seccion serial NOT NULL,
    nombre_seccion character varying(45),
    descripcion character varying(200)
);

CREATE TABLE seguridad.puestos (
    id_puesto serial NOT NULL,
    nombre_puesto character varying(45),
    descripcion character varying(200)
);

CREATE TABLE seguridad.usuarios (
    id_usuario serial NOT NULL,
    nombre_usuario character varying(45),
    contrasena character varying(45),
    correo character varying(45),
    nombre_completo character varying(200),
    cedula character varying(45),
    id_seccion integer,
    id_puesto integer,
    fecha_activacion date,
    fecha_desactivacion date,
    estado boolean,
    contrasena_caducada boolean
);

--Llaves primarias esquema seguridad
ALTER TABLE ONLY seguridad.entradas_menu_principal ADD CONSTRAINT pk_entradas_menu_principal PRIMARY KEY (id_menu_principal);
ALTER TABLE ONLY seguridad.permisos_menu_principal ADD CONSTRAINT pk_permisos_menu_principal PRIMARY KEY (id_permiso, id_menu_principal);
ALTER TABLE ONLY seguridad.permisos ADD CONSTRAINT pk_permisos PRIMARY KEY (id_permiso);
ALTER TABLE ONLY seguridad.permisos_roles ADD CONSTRAINT pk_permisos_roles PRIMARY KEY (id_rol, id_permiso);
ALTER TABLE ONLY seguridad.roles ADD CONSTRAINT pk_roles PRIMARY KEY (id_rol);
ALTER TABLE ONLY seguridad.roles_usuarios ADD CONSTRAINT pk_roles_usuarios PRIMARY KEY (id_usuario, id_rol);
ALTER TABLE ONLY seguridad.secciones ADD CONSTRAINT pk_secciones PRIMARY KEY (id_seccion);
ALTER TABLE ONLY seguridad.usuarios ADD CONSTRAINT pk_usuarios PRIMARY KEY (id_usuario);
ALTER TABLE ONLY seguridad.puestos ADD CONSTRAINT pk_puestos PRIMARY KEY (id_puesto);


--Indices unicos esquema seguridad
CREATE UNIQUE INDEX i_cedula ON seguridad.usuarios USING btree (cedula);
CREATE UNIQUE INDEX i_nombre_usuario ON seguridad.usuarios USING btree (nombre_usuario);
CREATE UNIQUE INDEX i_correo ON seguridad.usuarios USING btree (correo);
CREATE UNIQUE INDEX i_nombre_permiso ON seguridad.permisos USING btree (nombre);
CREATE UNIQUE INDEX i_nombre_rol ON seguridad.roles USING btree (nombre);
CREATE UNIQUE INDEX i_tag_emp ON seguridad.entradas_menu_principal USING btree (tag);
CREATE UNIQUE INDEX i_nombre_seccion ON seguridad.secciones USING btree (nombre_seccion);

--Llaves foraneas esquema seguridad
ALTER TABLE ONLY seguridad.permisos_roles ADD CONSTRAINT fk_id_permiso FOREIGN KEY (id_permiso) REFERENCES seguridad.permisos(id_permiso);
ALTER TABLE ONLY seguridad.roles_usuarios ADD CONSTRAINT fk_id_rol FOREIGN KEY (id_rol) REFERENCES seguridad.roles(id_rol) ON DELETE CASCADE;
ALTER TABLE ONLY seguridad.permisos_roles ADD CONSTRAINT fk_id_rol FOREIGN KEY (id_rol) REFERENCES seguridad.roles(id_rol) ON DELETE CASCADE;
ALTER TABLE ONLY seguridad.roles_usuarios ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY seguridad.permisos_menu_principal ADD CONSTRAINT fk_permiso FOREIGN KEY (id_permiso) REFERENCES seguridad.permisos(id_permiso);
ALTER TABLE ONLY seguridad.usuarios ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones(id_seccion) on delete set null;; 
ALTER TABLE ONLY seguridad.usuarios ADD CONSTRAINT fk_id_puesto FOREIGN KEY (id_puesto) REFERENCES seguridad.puestos(id_puesto)on delete set null;; 

--######ESQUEMA bitacora######
DROP SCHEMA IF EXISTS bitacora CASCADE;
CREATE SCHEMA bitacora;

--Tabla esquema bitacora

CREATE TABLE bitacora.bitacora ( 
	id_bitacora serial NOT NULL,
	fecha_accion character varying(45) NOT NULL,
        nombre_usuario character varying(45) NOT NULL,
        ip character varying(18) NOT NULL,
	accion character varying(45) NOT NULL,
        tabla character varying(45),
        estado json
 );

ALTER TABLE ONLY bitacora.bitacora ADD CONSTRAINT pk_bitacora PRIMARY KEY (id_bitacora);

ALTER TABLE ONLY bitacora.bitacora ADD CONSTRAINT fk_nombre_usuario FOREIGN KEY (nombre_usuario) REFERENCES seguridad.usuarios(nombre_usuario);

--######ESQUEMA bodega######
DROP SCHEMA IF EXISTS bodega CASCADE;
CREATE SCHEMA bodega;

--Tablas esquema bodega
CREATE TABLE bodega.activos_fijos ( 
	id_activo_fijo serial NOT NULL,
	placa character varying(45),
	equipo character varying(45) NOT NULL,
        numero_serie character varying(45),
	marca character varying(45),
        fecha_movimiento date,
        id_seccion integer,
	id_ubicacion integer,
        fecha_registro date NOT NULL,
        modelo character varying(45),
        responsable character varying(45),
        estado character varying(45)
 );

CREATE TABLE bodega.catalogo_interno(
	id_producto serial NOT NULL,
        nombre character varying(200) NOT NULL,
	codigo_icp character varying(45) NOT NULL,
	stock_minimo integer NOT NULL,
	stock_maximo integer NOT NULL,
	presentacion character varying(45) NOT NULL,
        descripcion character varying(500),
        cuarentena boolean,
        perecedero boolean
 );

CREATE TABLE bodega.catalogos_internos_externos(
        id_producto_ext integer,
        id_producto integer
);

CREATE TABLE bodega.catalogo_externo(
	id_producto_ext serial NOT NULL,
	producto character varying(45) NOT NULL,
	codigo_externo character varying(45),
	marca character varying(45),
	id_proveedor integer
 );


CREATE TABLE bodega.ingresos ( 
	id_ingreso serial NOT NULL,
	id_producto integer NOT NULL,
        id_seccion integer NOT NULL,
     	fecha_ingreso date NOT NULL,
     	fecha_registro date NOT NULL,
	cantidad integer NOT NULL,
	fecha_vencimiento date,
	estado character varying(45),
	precio integer
 );

CREATE TABLE bodega.inventarios ( 
        id_inventario serial NOT NULL,
	id_producto integer,
	id_seccion integer NOT NUll,
     	stock_actual integer NOT NULL
 ); 

CREATE TABLE bodega.reactivos ( 
	id_reactivo serial NOT NULL,
	id_producto integer,
     	numero_cas character varying(45),
	formula_quimica character varying(45),
	familia character varying(45),
	cantidad_botella_bodega integer,
	cantidad_botella_lab integer,
	volumen_bodega character varying(45),
	volumen_lab character varying(45)
 ); 

CREATE TABLE bodega.solicitudes ( 
	id_solicitud serial NOT NULL,
	id_usuario integer NOT NULL ,
	id_inventario integer NOT NULL,
	cantidad integer NOT NULL,
     	fecha_solicitud date NOT NULL,
        estado character varying(45) NOT NULL,
        fecha_entrega date,
        id_usuario_recibo integer,
        observaciones character varying(200)
 ); 
CREATE TABLE bodega.solicitudes_prestamos ( 
	id_solicitud integer NOT NULL,
	id_seccion_presta integer NOT NULL,
	id_usuario_aprobo integer
); 



CREATE TABLE bodega.usuarios_sub_bodegas_ingresos ( 
	id_sub_bodega serial NOT NULL,
        id_usuario integer NOT NULL 
 ); 

CREATE TABLE bodega.usuarios_sub_bodegas_egresos ( 
	id_sub_bodega serial NOT NULL,
        id_usuario integer NOT NULL 
 ); 

CREATE TABLE bodega.sub_bodegas ( 
	id_sub_bodega serial NOT NULL,
	id_seccion integer NOT NULL,
        id_usuario integer NOT NULL,
        nombre character varying(45) NOT NULL
 ); 

CREATE TABLE bodega.inventarios_bodegas ( 
	id_sub_bodega integer,
	id_producto integer,
     	cantidad integer NOT NULL
 );

 CREATE TABLE bodega.ubicaciones ( 
	id_ubicacion serial NOT NULL,
	nombre character varying(45) NOT NULL,
        descripcion character varying(500)
 );

CREATE TABLE bodega.ubicaciones_catalogo_interno ( 
	id_ubicacion integer NOT NULL,
        id_producto integer NOT NULL
	
 );

CREATE TABLE bodega.ubicaciones_bodega ( 
	id_ubicacion serial NOT NULL,
	nombre character varying(45) NOT NULL,
        descripcion character varying(500)
 );

 --Llaves primarias esquema bodega
ALTER TABLE ONLY bodega.activos_fijos ADD CONSTRAINT pk_activos_fijos PRIMARY KEY (id_activo_fijo);
ALTER TABLE ONLY bodega.catalogo_interno ADD CONSTRAINT pk_catalogo_interno PRIMARY KEY (id_producto);
ALTER TABLE ONLY bodega.catalogo_externo ADD CONSTRAINT pk_catalogo_externo PRIMARY KEY (id_producto_ext);
ALTER TABLE ONLY bodega.catalogos_internos_externos ADD CONSTRAINT pk_catalogos_internos_externos PRIMARY KEY ( id_producto_ext,id_producto);
ALTER TABLE ONLY bodega.ubicaciones_catalogo_interno ADD CONSTRAINT pk_ubicaciones_catalogo_interno PRIMARY KEY ( id_ubicacion,id_producto);
ALTER TABLE ONLY bodega.ingresos ADD CONSTRAINT pk_ingresos PRIMARY KEY (id_ingreso);
ALTER TABLE ONLY bodega.inventarios ADD CONSTRAINT pk_inventarios PRIMARY KEY (id_inventario);
ALTER TABLE ONLY bodega.reactivos ADD CONSTRAINT pk_reactivos PRIMARY KEY (id_reactivo);
ALTER TABLE ONLY bodega.solicitudes ADD CONSTRAINT pk_solicitudes PRIMARY KEY (id_solicitud);
ALTER TABLE ONLY bodega.solicitudes_prestamos ADD CONSTRAINT pk_prestamos PRIMARY KEY (id_solicitud);
ALTER TABLE ONLY bodega.sub_bodegas ADD CONSTRAINT pk_sub_bodegas PRIMARY KEY (id_sub_bodega);
ALTER TABLE ONLY bodega.inventarios_bodegas ADD CONSTRAINT pk_inventarios_bodegas PRIMARY KEY (id_sub_bodega, id_producto);
ALTER TABLE ONLY bodega.ubicaciones ADD CONSTRAINT pk_ubicaciones PRIMARY KEY (id_ubicacion);
ALTER TABLE ONLY bodega.ubicaciones_bodega ADD CONSTRAINT pk_ubicaciones_bodega PRIMARY KEY (id_ubicacion);
	
--Indices unicos esquema bodega
CREATE UNIQUE INDEX i_codigo_icp ON bodega.catalogo_interno USING btree (codigo_icp);

--Llaves foraneas esquema bodega
ALTER TABLE ONLY bodega.catalogos_internos_externos ADD CONSTRAINT fk_id_producto_ext FOREIGN KEY (id_producto_ext) REFERENCES bodega.catalogo_externo(id_producto_ext);
ALTER TABLE ONLY bodega.catalogos_internos_externos ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno(id_producto);
ALTER TABLE ONLY bodega.ubicaciones_catalogo_interno ADD CONSTRAINT fk_id_ubicacion FOREIGN KEY (id_ubicacion) REFERENCES bodega.ubicaciones_bodega(id_ubicacion);
ALTER TABLE ONLY bodega.ubicaciones_catalogo_interno ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno(id_producto);
ALTER TABLE ONLY bodega.ingresos ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno(id_producto);
ALTER TABLE ONLY bodega.ingresos ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones(id_seccion);
ALTER TABLE ONLY bodega.inventarios ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno(id_producto);
ALTER TABLE ONLY bodega.reactivos ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno(id_producto);
ALTER TABLE ONLY bodega.solicitudes ADD CONSTRAINT fk_id_inventario FOREIGN KEY (id_inventario) REFERENCES bodega.inventarios(id_inventario);
ALTER TABLE ONLY bodega.solicitudes ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY bodega.solicitudes ADD CONSTRAINT fk_id_usuario_recibo FOREIGN KEY (id_usuario_recibo) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY bodega.solicitudes_prestamos ADD CONSTRAINT fk_id_solicitud FOREIGN KEY (id_solicitud) REFERENCES bodega.solicitudes(id_solicitud);
ALTER TABLE ONLY bodega.solicitudes_prestamos ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion_presta) REFERENCES seguridad.secciones(id_seccion);
ALTER TABLE ONLY bodega.solicitudes_prestamos ADD CONSTRAINT fk_id_usuario_aprobo FOREIGN KEY (id_usuario_aprobo) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY bodega.inventarios_bodegas ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno(id_producto);
ALTER TABLE ONLY bodega.inventarios_bodegas ADD CONSTRAINT fk_id_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);
ALTER TABLE ONLY bodega.activos_fijos ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones(id_seccion)on delete set null;
ALTER TABLE ONLY bodega.activos_fijos ADD CONSTRAINT fk_id_ubicacion FOREIGN KEY (id_ubicacion) REFERENCES bodega.ubicaciones(id_ubicacion)on delete set null;
ALTER TABLE ONLY bodega.sub_bodegas ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones(id_seccion);
ALTER TABLE ONLY bodega.sub_bodegas ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_ingresos ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_egresos ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

--######ESQUEMA configuración ######
DROP SCHEMA IF EXISTS configuracion CASCADE;
CREATE SCHEMA configuracion;
CREATE TABLE configuracion.correo (
    id_correo serial NOT NULL,
    host character varying(80),
    puerto character varying(80),
    starttls character varying(80),
    nombre_emisor character varying(80),
    correo character varying(80),
    contrasena character varying(80)
);

ALTER TABLE ONLY configuracion.correo ADD CONSTRAINT pk_correo PRIMARY KEY (id_correo);

--#########Esquema Compras ####################
DROP SCHEMA IF EXISTS compras CASCADE;
CREATE SCHEMA compras;
CREATE TABLE compras.proveedores (
    id_proveedor serial NOT NULL,
    nombre_proveedor character varying(80),
    telefono1  character varying(80),
    telefono2  character varying(80),
    telefono3  character varying(80),
    correo character varying(80)
);
 --Llaves primarias esquema Compras
ALTER TABLE ONLY compras.proveedores ADD CONSTRAINT pk_proveedores PRIMARY KEY (id_proveedor);
-- Indices esquema compras
CREATE UNIQUE INDEX i_correo ON compras.proveedores USING btree (correo);
ALTER TABLE ONLY bodega.catalogo_externo ADD CONSTRAINT fk_id_proveedor FOREIGN KEY (id_proveedor) REFERENCES compras.proveedores(id_proveedor) on delete set null;


/* INSERTS */

--##PERMISOS
--Permiso global
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (1, '[PermisoGlobal]Administración', 'Permite realizar cualquier operación');
--Permisos asociados a usuarios
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (2, '[Seguridad]AgregarUsuario', 'Permite agregar a un usuario');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (3, '[Seguridad]EditarUsuario', 'Permite modificar a un usuario');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (4, '[Seguridad]DesactivarUsuario', 'Permite desactivar a un usuario');
--Permisos asociados a roles
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (5, '[Seguridad]AgregarRol', 'Permite activar un rol');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (6, '[Seguridad]EditarRol', 'Permite modificar un rol');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (7, '[Seguridad]EliminarRol', 'Permite eliminar un rol');
--Permisos asociados a secciones
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (8, '[Seguridad]AgregarSección', 'Permite activar una sección');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (9, '[Seguridad]EditarSección', 'Permite modificar una sección');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (10, '[Seguridad]EliminarSección', 'Permite eliminar una sección');
--Permisos asociados a producto
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (11, '[Bodegas]AgregarProducto', 'Permite agregar un proudcto');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (12, '[Bodegas]EditarProducto', 'Permite modificar un producto');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (13, '[Bodegas]EliminarProducto', 'Permite eliminar un producto');
--Permisos asociados a proveedores
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (14, '[Bodegas]AgregarProveedor', 'Permite agregar un proveedor');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (15, '[Bodegas]EditarProveedor', 'Permite modificar un proveedor');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (16, '[Bodegas]EliminarProveedor', 'Permite eliminar un proveedor');
--Permisos asociado a restablecer contraseña
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (17, '[Seguridad]RestablecerContraseña', 'Permite restablecer la contraseña de un usuario');
--Permisos asociados a puestos
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (18, '[Seguridad]AgregarPuesto', 'Permite activar un puesto');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (19, '[Seguridad]EditarPuesto', 'Permite modificar un puesto');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (20, '[Seguridad]EliminarPuesto', 'Permite eliminar un puesto');
--Permisos asociados a producto externo
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (21, '[Bodegas]AgregarProductoExterno', 'Permite agregar un proudcto Externo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (22, '[Bodegas]EditarProductoExterno', 'Permite modificar un producto Externo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (23, '[Bodegas]EliminarProductoExterno', 'Permite eliminar un producto Externo');
--Permisos asociados a solicitudes
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (24, '[Bodegas]Solicitudes', 'Permite solicitar productos');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (25, '[Bodegas]AdministrarSolicitudes', 'Permite administrar las Solicitudes además de solicitar productos(admin bodega)');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (26, '[Bodegas]Aceptar y Rechazar Préstamos', 'Permite aceptar o rechazar préstamos');
--Permisos asociados a ingresos
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (27, '[Bodegas]Agregar Ingresos', 'Permite registrar ingresos');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (28, '[Bodegas]Editar Ingresos', 'Permite modificar toda la información de un ingreso');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (29, '[Bodegas]Aprobar/Rechazar Ingresos', 'Permite aprobar o rechazar un ingreso que se encuentre en cuarentena');
--Permisos asociados a inventarios
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (30, '[Bodegas]Ver Inventarios', 'Permite aprobar o rechazar un ingreso que se encuentre en cuarentena');


--##Entradas de menu principal
/*Observación importante:
Los tags de los módulos como tales deben estar de 
primero (para que obtengan los primeros id's y además deben llevar como id_padre el 0 y tener un redirect.*/

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (100, 0, 'Bodegas', '/Bodegas/CatalogoInterno');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (101, 100, 'Catálogo Interno', '/Bodegas/CatalogoInterno');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (102, 100, 'Catálogo Externo', '/Bodegas/CatalogoExterno');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (103, 100, 'Activos Fijos', '/Bodegas/ActivosFijos');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (104, 100, 'Ubicaciones Act.', '/Bodegas/Ubicaciones');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (105, 100, 'Ubicaciones Bod.', '/Bodegas/UbicacionesBodega');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (106, 100,'Solicitudes', '/Bodegas/Solicitudes');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (107, 100,'Ingresos', '/Bodegas/Ingresos');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (108, 100,'Inventarios', '/Bodegas/Inventarios');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (200, 0, 'Bioterio', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (300, 0, 'Serpentario', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (400, 0, 'Caballeriza', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (500, 0,'Control de Calidad', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (600, 0,'Producción', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (700, 0,'Ventas', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (800, 0,'Seguridad', '/Seguridad/Usuarios');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (801, 800,'Usuarios', '/Seguridad/Usuarios');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (802, 800,'Roles', '/Seguridad/Roles');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (803, 800,'Puestos', '/Seguridad/Puestos');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (900, 0,'Configuración', '/Configuracion/Secciones');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (901, 900,'Secciones', '/Configuracion/Secciones');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (902, 900,'Correo', '/Configuracion/Correo');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (1000, 0,'Compras', '/Compras/Proveedores');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (1001, 900,'Proveedores', '/Compras/Proveedores');


--## Permisos con menu principal
--Usuarios
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (2, 801);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (3, 801);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (4, 801);
--Roles
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (5, 802);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (6, 802);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (7, 802);
--Sección
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (8, 901);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (9, 901);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (10, 901);
--Producto
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (11, 101);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (12, 101);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (13, 101);
--Proveedores
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (14, 1001);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (15, 1001);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (16, 1001);
--Restablecer contraseña
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (17, 801);
--Puestos
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (18, 803);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (19, 803);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (20, 803);
--Catálogo Externo
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (21, 102);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (22, 102);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (23, 102);
--Solicitudes
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (24, 106);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (25, 106);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (26, 106);
--Ingresos
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (27, 107);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (28, 107);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (29, 107);
--Inventarios
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (30, 108);


--##Roles
INSERT INTO seguridad.roles(nombre, descripcion) VALUES ('Administrador','Administrador, Mantenimiento y acceso a todo el sistema');
INSERT INTO seguridad.roles(nombre, descripcion) VALUES ('Encargado de seguridad', 'Administración del módulo de seguridad');
INSERT INTO seguridad.roles(nombre, descripcion) VALUES ('Administrador de Usuarios', 'Administración de la sección de usuarios del módulo de seguridad');
INSERT INTO seguridad.roles(nombre, descripcion) VALUES ('Administrador de Roles', 'Administración de la sección de roles del módulo de seguridad');
INSERT INTO seguridad.roles(nombre, descripcion) VALUES ('Administrador de Secciones', 'Administración de las secciones del módulo de configuración');

--##Permisos en roles
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (1,1);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,2);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,3);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,4);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,5);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,6);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,7);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,8);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,9);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (2,10);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (3,2);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (3,3);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (3,4);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (4,5);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (4,6);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (4,7);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (5,8);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (5,9);
INSERT INTO seguridad.permisos_roles(id_rol, id_permiso) VALUES (5,10);

INSERT INTO seguridad.secciones(id_seccion, nombre_seccion, descripcion) VALUES (0,'Unificada','Sección compartida');
INSERT INTO seguridad.secciones(nombre_seccion, descripcion) VALUES ('Producción','Dedicados a la producción');
INSERT INTO seguridad.secciones(nombre_seccion, descripcion) VALUES ('Control de Calidad','Dedicados al control de calidad');
INSERT INTO seguridad.secciones(nombre_seccion, descripcion) VALUES ('Ventas','Dedicados a la venta de productos');
INSERT INTO seguridad.secciones(nombre_seccion, descripcion) VALUES ('Bioterio','Dedicados al bioterio');
INSERT INTO seguridad.secciones(nombre_seccion, descripcion) VALUES ('Serpentario','Dedicados a los serpentarios');
INSERT INTO seguridad.secciones(nombre_seccion, descripcion) VALUES ('Caballeriza','Dedicados a la caballeriza');

--##Puestos
INSERT INTO seguridad.puestos(nombre_puesto, descripcion) VALUES ('Jefe','Puesto de jefatura');
INSERT INTO seguridad.puestos(nombre_puesto, descripcion) VALUES ('Secretario','Secretarios de jefatura');

--##Usuarios
INSERT INTO seguridad.usuarios(nombre_usuario, contrasena, correo, nombre_completo, cedula, id_seccion, id_puesto, fecha_activacion, fecha_desactivacion, estado, contrasena_caducada) VALUES ('waltercoru', md5('sigipro'), 'waltercori21@gmail.com', 'Walter Cordero Urena', '1-2345-6710', 1, 1, '2014-12-01', '2014-12-01', true, false);
INSERT INTO seguridad.usuarios(nombre_usuario, contrasena, correo, nombre_completo, cedula, id_seccion, id_puesto, fecha_activacion, fecha_desactivacion, estado, contrasena_caducada) VALUES ('dnjj14', md5('sigipro'), 'dnjj14@gmail.com', 'Daniel Thiel Jimenez', '2-2345-6789', 1, 1, '2014-12-01', '2014-12-01', false, true);
INSERT INTO seguridad.usuarios(nombre_usuario, contrasena, correo, nombre_completo, cedula, id_seccion, id_puesto, fecha_activacion, fecha_desactivacion, estado, contrasena_caducada) VALUES ('ametico', md5('sigipro'), 'ametico@gmail.com', 'Amed Espinoza', '3-2345-6789', 1, 1, '2014-12-01', '2014-12-01', true, true);
INSERT INTO seguridad.usuarios(nombre_usuario, contrasena, correo, nombre_completo, cedula, id_seccion, id_puesto, fecha_activacion, fecha_desactivacion, estado, contrasena_caducada) VALUES ('isaaclpez', md5('sigipro'), 'isaaclpez@gmail.com', 'Isaac Lopez', '4-2345-6789', 1 , 1, '2014-12-01', '2014-12-01', false, true);
INSERT INTO seguridad.usuarios(nombre_usuario, contrasena, correo, nombre_completo, cedula, id_seccion, id_puesto, fecha_activacion, fecha_desactivacion, estado, contrasena_caducada) VALUES ('estebav8', md5('sigipro'), 'estebav8@gmail.com', 'Esteban Aguilar Valverde', '5-2345-6789', 1, 1, '2014-12-01', '2014-12-01', true, true);

--#Roles en Usuarios
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (1, 1, '2014-12-01', '2014-12-01');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (2, 2, '2014-12-01', '2014-12-01');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (3, 3, '2014-12-01', '2014-12-01');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (4, 4, '2014-12-01', '2014-12-01');
INSERT INTO seguridad.roles_usuarios(id_usuario, id_rol, fecha_activacion, fecha_desactivacion) VALUES (5, 5, '2014-12-01', '2014-12-01');

--##Correo
INSERT INTO configuracion.correo(id_correo, host, puerto, starttls, nombre_emisor, correo, contrasena) VALUES (1, 'smtp.gmail.com', '587', 'true', 'SIGIPRO', 'sigiproicp@gmail.com', 'Sigipro2015');
