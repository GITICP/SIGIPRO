INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (315, '[Serpentario]EditarSerpienteAdmin', 'Permite editar elementos de la serpiente como Localidad de Origen y Colectada por');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (316, '[Serpentario]ReversarPasoCV', 'Permite reversar las decisiones de paso a Colección Viva.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (315, 302);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (316, 302);

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