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
    localidad_origen character varying (200) NOT NULL,
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

--Permisos

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (300, '[Serpentario]AgregarEspecie', 'Permite agregar una especie al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (301, '[Serpentario]EliminarEspecie', 'Permite eliminar una especie al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (302, '[Serpentario]EditarEspecie', 'Permite editar una especie al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (310, '[Serpentario]AgregarSerpiente', 'Permite agregar una serpiente al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (311, '[Serpentario]EditarSerpiente', 'Permite editar una serpiente al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (312, '[Serpentario]EventoSerpiente', 'Permite registrar eventos a una serpiente al catálogo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (313, '[Serpentario]RegistrarDeceso', 'Permite registrar el deceso de una serpiente.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (314, '[Serpentario]RegistrarCH-CT', 'Permite agregar la serpiente a Colección Húmeda o Catálogo de Tejidos');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (320, '[Serpentario]AgregarExtraccion', 'Permite agregar una extraccion');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (321, '[Serpentario]EditarExtraccion', 'Permite editar una extraccion al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (330, '[Serpentario]AgregarLote', 'Permite agregar un lote');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (331, '[Serpentario]EditarLote', 'Permite editar un lote al catálogo');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (340, '[Serpentario]VerVenenoSerpentario', 'Permite ver el catalogo de venenos completo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (341, '[Serpentario]VerVenenoExterno', 'Permite ver parte del catalogo de venenos.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (342, '[Serpentario]EditarVeneno', 'Permite editar los venenos.');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (350, '[Serpentario]AgregarSolicitud', 'Permite agregar solicitudes.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (351, '[Serpentario]EditarSolicitud', 'Permite editar solicitudes que no esten en proceso.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (352, '[Serpentario]AdministrarSolicitud', 'Permite aprobar o rechazar solicitudes.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (353, '[Serpentario]EntregarSolicitud', 'Permite entregar una solicitud.');


--Menu

UPDATE seguridad.entradas_menu_principal SET redirect = '/Serpentario/Especie' WHERE id_menu_principal = 300;

--Sub menús
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (299, 300, 'Serpientes', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (298, 300, 'Venenos', null);
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (297, 300, 'Decesos', null);


INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (301, 299, 'Especie', '/Serpentario/Especie');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (302, 299, 'Serpiente', '/Serpentario/Serpiente');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (303, 300, 'Extracción', '/Serpentario/Extraccion');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (304, 298, 'Lote', '/Serpentario/Lote');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (305, 298, 'Veneno', '/Serpentario/Veneno');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (306, 300, 'Solicitud Veneno', '/Serpentario/SolicitudVeneno');

------Permisos Menu Principal

--Especie
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (300, 301);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (301, 301);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (310, 302);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (311, 302);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (312, 302);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (320, 303);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (321, 303);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (330, 304);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (331, 304);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (340, 305);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (341, 305);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (350, 306);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (351, 306);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (352, 306);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (353, 306);

