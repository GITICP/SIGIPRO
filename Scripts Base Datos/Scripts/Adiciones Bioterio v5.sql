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
)


--Permisos asociados a Bioterio
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (258, '[Bioterio]AdministrarSalidasConejos', 'Permite agregar/editar/eliminar salidas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (208, '[Bioterio]AdministrarSalidasRatones', 'Permite agregar/editar/eliminar salidas');
--Entradas del Menu de Bioterio
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (208, 200, 'Salidas', '/Bioterio/Salidas?especie=True'); --análisis de los ratones
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (258, 250, 'Salidas', '/Bioterio/Salidas?especie=False');  --análisis de los conejos


--Permisos del menu principal de Bioterio
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (208, 208);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (258, 258);

