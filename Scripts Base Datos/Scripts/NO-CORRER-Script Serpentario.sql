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
    talla_cabeza decimal,
    talla_cola decimal,
    peso decimal,
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
    volumen_extraido decimal,
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
    volumen_recuperado decimal NOT NULL,
    id_usuario integer NOT NULL,
    fecha_volumen_recuperado date NOT NULL
);

CREATE TABLE serpentario.liofilizacion(
    id_extraccion integer NOT NULL,
    id_usuario_inicio integer NOT NULL,
    fecha_inicio date NOT NULL,
    peso_recuperado decimal,
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
    id_especie integer NOT NULL,
    id_veneno integer
);

ALTER TABLE ONLY serpentario.lote ADD CONSTRAINT pk_id_lote PRIMARY KEY(id_lote);

ALTER TABLE ONLY serpentario.lote ADD CONSTRAINT fk_id_especie FOREIGN KEY (id_especie) REFERENCES serpentario.especies(id_especie);

ALTER TABLE ONLY serpentario.extraccion ADD CONSTRAINT fk_id_lote FOREIGN KEY (id_lote) REFERENCES serpentario.lote(id_lote);


--Catalogo de Venenos

CREATE TABLE serpentario.venenos(
    id_veneno serial NOT NULL,
    id_especie integer NOT NULL,
    restriccion boolean NOT NULL,
    cantidad_maxima decimal
);

ALTER TABLE ONLY serpentario.venenos ADD CONSTRAINT pk_id_veneno PRIMARY KEY (id_veneno);

ALTER TABLE ONLY serpentario.lote ADD CONSTRAINT fk_id_veneno FOREIGN KEY (id_veneno) REFERENCES serpentario.venenos(id_veneno);


ALTER TABLE ONLY serpentario.venenos ADD CONSTRAINT fk_id_especie FOREIGN KEY (id_especie) REFERENCES serpentario.especies(id_especie);



--Solicitudes de Venenos

CREATE TABLE serpentario.solicitudes(
    id_solicitud serial NOT NULL,
    fecha_solicitud date NOT NULL,
    id_especie integer NOT NULL,
    cantidad decimal NOT NULL,
    id_usuario integer NOT NULL,
    proyecto character varying(200),
    estado character varying(15),
    observaciones character varying(200)
);

ALTER TABLE ONLY serpentario.solicitudes ADD CONSTRAINT pk_id_solicitud PRIMARY KEY (id_solicitud);

ALTER TABLE ONLY serpentario.solicitudes ADD CONSTRAINT fk_id_especie FOREIGN KEY(id_especie)REFERENCES serpentario.especies(id_especie);

ALTER TABLE ONLY serpentario.solicitudes ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);


CREATE TABLE serpentario.entregas_solicitud(
    id_entrega serial NOT NULL,
    id_solicitud integer NOT NULL,
    id_usuario_entrega integer NOT NULL,
    fecha_entrega date NOT NULL,
    cantidad_entregada decimal NOT NULL,
    id_usuario_recibo integer
);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT pk_id_entrega PRIMARY KEY (id_entrega);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT fk_id_solicitud FOREIGN KEY (id_solicitud) REFERENCES serpentario.solicitudes(id_solicitud);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT fk_id_usuario_entrega FOREIGN KEY (id_usuario_entrega) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY serpentario.entregas_solicitud ADD CONSTRAINT fk_id_usuario_recibo FOREIGN KEY (id_usuario_recibo) REFERENCES seguridad.usuarios(id_usuario);

CREATE TABLE serpentario.lotes_entregas_solicitud(
    id_entrega integer NOT NULL,
    id_lote integer NOT NULL,
    cantidad decimal NOT NULL
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

ALTER TABLE SERPENTARIO.SERPIENTES
ADD COLUMN NUMERO_SERPIENTE integer NOT NULL UNIQUE;

CREATE UNIQUE INDEX i_numero_serpiente ON serpentario.serpientes USING btree (numero_serpiente);

ALTER TABLE SERPENTARIO.LOTE
ADD COLUMN NUMERO_LOTE CHARACTER VARYING(10) NOT NULL UNIQUE;

ALTER TABLE SERPENTARIO.COLECCION_HUMEDA
ADD COLUMN NUMERO_COLECCION_HUMEDA integer NOT NULL UNIQUE;

ALTER TABLE SERPENTARIO.CATALOGO_TEJIDO
ADD COLUMN NUMERO_CATALOGO_TEJIDO integer NOT NULL UNIQUE;

CREATE TABLE serpentario.categorias(
    id_categoria serial NOT NULL,
    nombre_categoria character varying(20)
);

ALTER TABLE ONLY serpentario.categorias ADD CONSTRAINT pk_id_categoria PRIMARY KEY(id_categoria);

ALTER TABLE serpentario.eventos
DROP COLUMN evento;

DELETE FROM SERPENTARIO.EVENTOS;

ALTER TABLE serpentario.eventos
ADD COLUMN id_categoria int NOT NULL;

ALTER TABLE ONLY serpentario.eventos ADD CONSTRAINT fk_id_categoria FOREIGN KEY (id_categoria) REFERENCES serpentario.categorias(id_categoria);

INSERT INTO SERPENTARIO.CATEGORIAS VALUES (1,'Defecación');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (2,'Cambio Piel');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (3,'Desparasitación');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (4,'Alimentación');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (5,'Colección Viva');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (6,'Deceso');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (7,'Colección Húmeda');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (8,'Catálogo Tejido');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (9,'Sexo');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (10,'Talla Cabeza');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (11,'Talla Cola');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (12,'Peso');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (13,'Extracción');
INSERT INTO SERPENTARIO.CATEGORIAS VALUES (14,'Descarte');

ALTER TABLE SERPENTARIO.SERPIENTES
ADD COLUMN coleccionviva boolean;

ALTER TABLE SERPENTARIO.SERPIENTES
ADD COLUMN estado boolean;

ALTER TABLE SERPENTARIO.CATALOGO_TEJIDO
ALTER COLUMN ESTADO TYPE CHARACTER VARYING(100);

ALTER TABLE SERPENTARIO.EXTRACCION
ADD COLUMN ESTADO_SERPIENTES BOOLEAN;

INSERT INTO SERPENTARIO.CATEGORIAS VALUES (15,'Otros');

