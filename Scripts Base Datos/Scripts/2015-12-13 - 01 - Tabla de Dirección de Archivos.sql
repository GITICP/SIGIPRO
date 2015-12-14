CREATE TABLE configuracion.direccion_archivos (
    id_direccion serial NOT NULL,
    direccion character varying(1000) NOT NULL,
    CONSTRAINT pk_direccion_archivos PRIMARY KEY (id_direccion)
);

INSERT INTO configuracion.direccion_archivos (id_direccion, direccion) VALUES (1, '');