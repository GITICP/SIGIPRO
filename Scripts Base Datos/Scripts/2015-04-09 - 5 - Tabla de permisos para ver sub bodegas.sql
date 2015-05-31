DROP TABLE bodega.usuarios_sub_bodegas_ingresos;
DROP TABLE bodega.usuarios_sub_bodegas_egresos;

CREATE TABLE bodega.usuarios_sub_bodegas_ingresos ( 
	id_sub_bodega integer NOT NULL,
        id_usuario integer NOT NULL 
 ); 

CREATE TABLE bodega.usuarios_sub_bodegas_egresos ( 
	id_sub_bodega integer NOT NULL,
        id_usuario integer NOT NULL 
 ); 

CREATE TABLE bodega.usuarios_sub_bodegas_ver (
        id_sub_bodega integer NOT NULL,
        id_usuario integer NOT NULL
);

CREATE TABLE bodega.inventarios_sub_bodegas ( 
        id_inventario_sub_bodega serial NOT NULL,
	id_sub_bodega integer,
	id_producto integer,
     	cantidad integer NOT NULL,
        fecha_vencimiento date NULL
);

CREATE TABLE bodega.bitacora_sub_bodegas (
        id_bitacora_sub_bodegas serial NOT NULL,
        fecha_accion timestamp DEFAULT NOW(),
        id_sub_bodega integer NOT NULL,
        id_sub_bodega_destino integer,
        accion character varying(20) NOT NULL,
        id_producto integer NOT NULL,
        cantidad integer NOT NULL,
        id_usuario integer NOT NULL
);

ALTER TABLE ONLY bodega.usuarios_sub_bodegas_ingresos ADD CONSTRAINT pk_usuarios_sub_bodegas_ingresos PRIMARY KEY (id_sub_bodega, id_usuario);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_egresos ADD CONSTRAINT pk_usuarios_sub_bodegas_egresos PRIMARY KEY (id_sub_bodega, id_usuario);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_ver ADD CONSTRAINT pk_usuarios_sub_bodegas_ver PRIMARY KEY (id_sub_bodega, id_usuario);

ALTER TABLE ONLY bodega.usuarios_sub_bodegas_ingresos ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_egresos ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_ver ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY bodega.usuarios_sub_bodegas_ingresos ADD CONSTRAINT fk_id_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_egresos ADD CONSTRAINT fk_id_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);
ALTER TABLE ONLY bodega.usuarios_sub_bodegas_ver ADD CONSTRAINT fk_id_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);

ALTER TABLE ONLY bodega.inventarios_sub_bodegas ADD CONSTRAINT unique_inventarios_sub_bodegas_permite_fecha_null UNIQUE (id_sub_bodega, id_producto, fecha_vencimiento);
ALTER TABLE ONLY bodega.inventarios_sub_bodegas ADD CONSTRAINT p_inventarios_sub_bodegas_permite_fecha_null PRIMARY KEY (id_inventario_sub_bodega);
ALTER TABLE ONLY bodega.inventarios_sub_bodegas ADD CONSTRAINT fk_id_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);

ALTER TABLE ONLY bodega.bitacora_sub_bodegas ADD CONSTRAINT pk_bitacora_sub_bodegas PRIMARY KEY (id_bitacora_sub_bodegas);
ALTER TABLE ONLY bodega.bitacora_sub_bodegas ADD CONSTRAINT fk_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);
ALTER TABLE ONLY bodega.bitacora_sub_bodegas ADD CONSTRAINT fk_sub_bodega_destino FOREIGN KEY (id_sub_bodega_destino) REFERENCES bodega.sub_bodegas(id_sub_bodega);
ALTER TABLE ONLY bodega.bitacora_sub_bodegas ADD CONSTRAINT fk_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno(id_producto);
ALTER TABLE ONLY bodega.bitacora_sub_bodegas ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (70, '[Bodegas]AdministrarSubBodegas', 'Permite Agregar/Editar una sub bodega');
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (70, 109);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (109, 100,'Sub Bodegas', '/Bodegas/SubBodegas');

ALTER TABLE ONLY bodega.ingresos ADD id_sub_bodega integer;
ALTER TABLE ONLY bodega.ingresos ADD CONSTRAINT fk_destino FOREIGN KEY (destino) REFERENCES bodega.sub_bodegas (id_sub_bodega);