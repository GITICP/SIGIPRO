ALTER TABLE bioterio.conejos_produccion ADD mortalidad integer;

CREATE TABLE bioterio.salidas
(
  id_salida serial NOT NULL,
  especie boolean NOT NULL,
  fecha date NOT NULL,
  cantidad integer NOT NULL,
  razon character varying(20) NOT NULL,
  observaciones character varying(500),
  CONSTRAINT pk_salidas PRIMARY KEY (id_salida)
);
CREATE TABLE bioterio.pie_de_cria
(
  id_pie serial NOT NULL,
  codigo character varying(20) NOT NULL,
  fuente character varying(200) NOT NULL,
  fecha_ingreso date NOT NULL,
  fecha_retiro date,
  CONSTRAINT pk_pie_de_cria PRIMARY KEY (id_pie)
);

CREATE TABLE bioterio.piexcepa
(
  id_pie integer NOT NULL,
  id_cepa integer NOT NULL,
  fecha_inicio date NOT NULL,
  fecha_estimada_retiro date NOT NULL,
  CONSTRAINT pk_piexcepa PRIMARY KEY (id_pie,id_cepa)
);
-- Llaves Foráneas
ALTER TABLE ONLY bioterio.piexcepa ADD CONSTRAINT fk_id_piecepa FOREIGN KEY (id_pie) REFERENCES bioterio.pie_de_cria(id_pie) ON DELETE CASCADE;
ALTER TABLE ONLY bioterio.piexcepa ADD CONSTRAINT fk_id_cepapie FOREIGN KEY (id_cepa) REFERENCES bioterio.cepas (id_cepa) ON DELETE CASCADE;
--Permisos 
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (258, '[Bioterio]AdministrarSalidasConejos', 'Permite agregar/editar/eliminar salidas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (208, '[Bioterio]AdministrarSalidasRatones', 'Permite agregar/editar/eliminar salidas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (209, '[Bioterio]AdministrarPiedeCria', 'Permite agregar/editar/eliminar Pies de Cría');
--Entradas del Menu 
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (208, 200, 'Salidas', '/Bioterio/Salidas?especie=True'); --salidas de los ratones
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (258, 250, 'Salidas', '/Bioterio/Salidas?especie=False');  --salidas de los conejos
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (209, 200, 'Pies de Cría', '/Ratonera/Pies'); 

--Permisos del menu principal 
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (208, 208);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (258, 258);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (209, 209);

