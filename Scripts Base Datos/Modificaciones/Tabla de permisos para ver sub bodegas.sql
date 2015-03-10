DROP TABLE bodega.usuarios_sub_bodegas_ingresos;
DROP TABLE bodega.usuarios_sub_bodegas_egresos;
DROP TABLE bodega.inventarios_sub_bodegas;

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
	id_sub_bodega integer,
	id_producto integer,
     	cantidad integer NOT NULL,
        fecha_vencimiento date NULL
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

ALTER TABLE ONLY bodega.inventarios_sub_bodegas ADD CONSTRAINT pk_inventarios_sub_bodegas_permite_fecha_null UNIQUE (id_sub_bodega, id_producto, fecha_vencimiento);
ALTER TABLE ONLY bodega.inventarios_sub_bodegas ADD CONSTRAINT fk_id_sub_bodega FOREIGN KEY (id_sub_bodega) REFERENCES bodega.sub_bodegas(id_sub_bodega);