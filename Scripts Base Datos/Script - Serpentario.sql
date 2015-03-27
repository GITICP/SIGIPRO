DROP SCHEMA IF EXISTS serpentario CASCADE;
CREATE SCHEMA serpentario;

--Especies de Serpientes

CREATE TABLE serpentario.especies (
    id_especie serial NOT NULL,
    genero character varying(45) NOT NULL,
    especie character varying(45) NOT NULL
);

ALTER TABLE ONLY serpentario.especies ADD CONSTRAINT pk_especies PRIMARY KEY (id_especie);

--Serpientes

CREATE TABLE serpentario.serpientes(
    id_serpiente serial NOT NULL,
    id_especie integer NOT NULL,
    fecha_ingreso date NOT NULL,
    localidad_origen character varying (45) NOT NULL,
    colectada character varying (45) NOT NULL,
    recibida character varying (45) NOT NULL,
    sexo character varying(10) NOT NULL,
    talla_cabeza float,
    talla_cola float,
    peso float,
    imagen bytea
);
    
ALTER TABLE ONLY serpentario.serpientes ADD CONSTRAINT pk_serpientes PRIMARY KEY (id_serpiente);

ALTER TABLE ONLY serpentario.serpientes ADD CONSTRAINT fk_id_especie FOREIGN KEY (id_especie) REFERENCES serpentario.especies(id_especie);

--Eventos

CREATE TABLE serpentario.eventos(
    id_evento serial NOT NULL,
    id_serpiente integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha_evento date NOT NULL,
    evento character varying(45) NOT NULL,
    observaciones character varying (200),
    valor_cambiado character varying(20),
    id_extraccion integer
);

ALTER TABLE ONLY serpentario.eventos ADD CONSTRAINT pk_eventos PRIMARY KEY (id_evento);

ALTER TABLE ONLY serpentario.eventos ADD CONSTRAINT fk_id_serpiente FOREIGN KEY (id_serpiente) REFERENCES serpentario.serpientes(id_serpiente);

ALTER TABLE ONLY serpentario.eventos ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

--Extracciones

CREATE TABLE serpentario.extraccion(
    id_extraccion serial NOT NULL,
    numero_extraccion character varying(45) NOT NULL UNIQUE,
    id_especie integer NOT NULL,
    ingreso_CV boolean NOT NULL,
    fecha_extraccion date NOT NULL,
    volumen_extraido float,
    id_usuario_registro integer,
    fecha_registro date,
    id_lote integer
);

CREATE TABLE serpentario.serpientes_extraccion(
    id_extraccion integer NOT NULL,
    id_serpiente integer NOT NULL
);

CREATE TABLE serpentario.usuarios_extraccion(
    id_extraccion integer NOT NULL,
    id_usuario integer NOT NULL
);

ALTER TABLE ONLY serpentario.extraccion ADD CONSTRAINT pk_extraccion PRIMARY KEY (id_extraccion);

ALTER TABLE ONLY serpentario.extraccion ADD CONSTRAINT fk_id_especie FOREIGN KEY (id_especie) REFERENCES serpentario.especies(id_especie);

ALTER TABLE ONLY serpentario.extraccion ADD CONSTRAINT fk_id_usuario_registro FOREIGN KEY (id_usuario_registro) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY serpentario.serpientes_extraccion ADD CONSTRAINT fk_id_extraccion FOREIGN KEY (id_extraccion) REFERENCES serpentario.extraccion(id_extraccion);

ALTER TABLE ONLY serpentario.serpientes_extraccion ADD CONSTRAINT fk_id_serpiente FOREIGN KEY (id_serpiente) REFERENCES serpentario.serpientes(id_serpiente);

ALTER TABLE ONLY serpentario.usuarios_extraccion ADD CONSTRAINT fk_id_extraccion FOREIGN KEY (id_extraccion) REFERENCES serpentario.extraccion(id_extraccion);

ALTER TABLE ONLY serpentario.usuarios_extraccion ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY serpentario.eventos ADD CONSTRAINT fk_id_extraccion FOREIGN KEY(id_extraccion) REFERENCES serpentario.extraccion(id_extraccion);

CREATE TABLE serpentario.centrifugado(
    id_extraccion integer NOT NULL,
    volumen_recuperado integer NOT NULL,
    id_usuario integer NOT NULL,
    fecha_volumen_recuperado date NOT NULL
);

CREATE TABLE serpentario.liofilizacion(
    id_extraccion integer NOT NULL,
    id_usuario_inicio integer NOT NULL,
    fecha_inicio date NOT NULL,
    peso_recuperado integer,
    id_usuario_fin integer,
    fecha_fin date
);

ALTER TABLE ONLY serpentario.centrifugado ADD CONSTRAINT pk_id_centrifugado PRIMARY KEY (id_extraccion);

ALTER TABLE ONLY serpentario.liofilizacion ADD CONSTRAINT pk_id_liofilizacion PRIMARY KEY (id_extraccion);

ALTER TABLE ONLY serpentario.centrifugado ADD CONSTRAINT fk_id_extraccion FOREIGN KEY(id_extraccion) REFERENCES serpentario.extraccion(id_extraccion);

ALTER TABLE ONLY serpentario.liofilizacion ADD CONSTRAINT fk_id_extraccion FOREIGN KEY(id_extraccion) REFERENCES serpentario.extraccion(id_extraccion);

ALTER TABLE ONLY serpentario.centrifugado ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY serpentario.liofilizacion ADD CONSTRAINT fk_id_usuario_inicio FOREIGN KEY (id_usuario_inicio) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY serpentario.liofilizacion ADD CONSTRAINT fk_id_usuario_fin FOREIGN KEY (id_usuario_fin) REFERENCES seguridad.usuarios(id_usuario);

--Lotes

CREATE TABLE serpentario.lote(
    id_lote serial NOT NULL,
    id_especie integer NOT NULL
);

ALTER TABLE ONLY serpentario.lote ADD CONSTRAINT pk_id_lote PRIMARY KEY(id_lote);

ALTER TABLE ONLY serpentario.lote ADD CONSTRAINT fk_id_especie FOREIGN KEY (id_especie) REFERENCES serpentario.especies(id_especie);

ALTER TABLE ONLY serpentario.extraccion ADD CONSTRAINT fk_id_lote FOREIGN KEY (id_lote) REFERENCES serpentario.lote(id_lote);


--Catalogo de Venenos

CREATE TABLE serpentario.venenos(
    id_veneno serial NOT NULL,
    id_especie integer NOT NULL,
    restriccion boolean NOT NULL,
    cantidad_maxima integer
);

ALTER TABLE ONLY serpentario.venenos ADD CONSTRAINT pk_id_veneno PRIMARY KEY (id_veneno);

ALTER TABLE ONLY serpentario.venenos ADD CONSTRAINT fk_id_especie FOREIGN KEY (id_especie) REFERENCES serpentario.especies(id_especie);



--Solicitudes de Venenos

CREATE TABLE serpentario.solicitudes(
    id_solicitud serial NOT NULL,
    fecha_solicitud date NOT NULL,
    id_especie integer NOT NULL,
    cantidad integer NOT NULL,
    id_usuario integer NOT NULL,
    proyecto character varying(200),
    estado character varying(15)
);

ALTER TABLE ONLY serpentario.solicitudes ADD CONSTRAINT pk_id_solicitud PRIMARY KEY (id_solicitud);

ALTER TABLE ONLY serpentario.solicitudes ADD CONSTRAINT fk_id_especie FOREIGN KEY(id_especie)REFERENCES serpentario.especies(id_especie);

ALTER TABLE ONLY serpentario.solicitudes ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);


CREATE TABLE serpentario.entregas_solicitud(
    id_entrega serial NOT NULL,
    id_solicitud integer NOT NULL,
    id_usuario_entrega integer NOT NULL,
    fecha_entrega date NOT NULL,
    cantidad_entregada integer NOT NULL,
    id_usuario_recibo integer
);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT pk_id_entrega PRIMARY KEY (id_entrega);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT fk_id_solicitud FOREIGN KEY (id_solicitud) REFERENCES serpentario.solicitudes(id_solicitud);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT fk_id_usuario_entrega FOREIGN KEY (id_usuario_entrega) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT fk_id_usuario_recibo FOREIGN KEY (id_usuario_recibo) REFERENCES seguridad.usuarios(id_usuario);

CREATE TABLE serpentario.lotes_entregas_solicitud(
    id_entrega integer NOT NULL,
    id_lote integer NOT NULL,
    cantidad integer NOT NULL
);

ALTER TABLE ONLY serpentario.lotes_entregas_solicitud ADD CONSTRAINT pk_lotes_entregas_solicitud PRIMARY KEY (id_entrega,id_lote);

ALTER TABLE ONLY serpentario.lotes_entregas_solicitud ADD CONSTRAINT fk_id_lote FOREIGN KEY (id_lote) REFERENCES serpentario.lote(id_lote);

ALTER TABLE ONLY serpentario.lotes_entregas_solicitud ADD CONSTRAINT fk_id_entrega FOREIGN KEY (id_entrega) REFERENCES serpentario.entregas_solicitud(id_entrega);

--Coleccion Humeda y Tejidos

CREATE TABLE serpentario.coleccion_humeda(
    id_coleccion_humeda serial NOT NULL,
    id_serpiente integer NOT NULL,
    proposito character varying(200) NOT NULL,
    observaciones character varying(200),
    id_usuario integer NOT NULL
);

CREATE TABLE serpentario.catalogo_tejido(
    id_catalogo_tejido serial NOT NULL,
    id_serpiente integer NOT NULL,
    numero_caja character varying (10) NOT NULL,
    posicion character varying(10) NOT NULL,
    observaciones character varying(200),
    estado character varying(20),
    id_usuario integer NOT NULL
);

ALTER TABLE ONLY serpentario.coleccion_humeda ADD CONSTRAINT pk_id_coleccion_humeda PRIMARY KEY (id_coleccion_humeda);

ALTER TABLE ONLY serpentario.coleccion_humeda ADD CONSTRAINT fk_id_serpiente FOREIGN KEY (id_serpiente) REFERENCES serpentario.serpientes (id_serpiente);

ALTER TABLE ONLY serpentario.coleccion_humeda ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios (id_usuario);

ALTER TABLE ONLY serpentario.catalogo_tejido ADD CONSTRAINT pk_id_catalogo_tejido PRIMARY KEY (id_catalogo_tejido);

ALTER TABLE ONLY serpentario.catalogo_tejido ADD CONSTRAINT fk_id_serpiente FOREIGN KEY (id_serpiente) REFERENCES serpentario.serpientes (id_serpiente);

ALTER TABLE ONLY serpentario.catalogo_tejido ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios (id_usuario);

CREATE TABLE serpentario.lotes_veneno(
    id_veneno integer NOT NULL,
    id_lote integer NOT NULL
);

ALTER TABLE ONLY serpentario.lotes_veneno ADD CONSTRAINT fk_id_veneno FOREIGN KEY (id_veneno) REFERENCES serpentario.venenos(id_veneno);

ALTER TABLE ONLY serpentario.lotes_veneno ADD CONSTRAINT fk_id_lote FOREIGN KEY (id_lote) REFERENCES serpentario.lote(id_lote);



--Permisos

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (300, '[Serpentario]AgregarEspecie', 'Permite agregar una especie al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (301, '[Serpentario]EliminarEspecie', 'Permite eliminar una especie al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (302, '[Serpentario]EditarEspecie', 'Permite editar una especie al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (310, '[Serpentario]AgregarSerpiente', 'Permite agregar una serpiente al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (311, '[Serpentario]EditarSerpiente', 'Permite editar una serpiente al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (312, '[Serpentario]EventoSerpiente', 'Permite registrar eventos a una serpiente al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (320, '[Serpentario]AgregarExtraccion', 'Permite agregar una extraccion');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (321, '[Serpentario]EditarExtraccion', 'Permite editar una extraccion al catálogo');

--Menu

UPDATE seguridad.entradas_menu_principal SET redirect = '/Serpentario/Especie' WHERE id_menu_principal = 300;


INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (301, 300, 'Especie', '/Serpentario/Especie');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (302, 300, 'Serpiente', '/Serpentario/Serpiente');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (303, 300, 'Extraccion', '/Serpentario/Extraccion');

------Permisos Menu Principal

--Especie
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (300, 301);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (301, 301);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (310, 302);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (311, 302);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (320, 303);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (321, 303);
