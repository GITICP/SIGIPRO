CREATE TABLE bioterio.gruposhembras(
	id_grupo serial NOT NULL,
	identificador character varying(20) NOT NULL,
	cantidad_espacios integer NOT NULL
);

ALTER TABLE bioterio.conejas ADD  fecha_ingreso date NOT NULL;
ALTER TABLE bioterio.conejas ADD  fecha_cambio date NOT NULL;
ALTER TABLE bioterio.conejas ADD  fecha_seleccion date NOT NULL;
ALTER TABLE bioterio.cajas  ADD  id_grupo integer NOT NULL;
ALTER TABLE bioterio.machos ADD  id_padre character varying(50) NOT NULL;
ALTER TABLE bioterio.machos ADD  id_madre character varying(50) NOT NULL;

CREATE TABLE bioterio.conejos_produccion(
	id_produccion serial NOT NULL,
	identificador character varying(50) NOT NULL,
	cantidad integer NOT NULL,
	detalle_procedencia character varying(250) NOT NULL
);

--PK y FK
ALTER TABLE ONLY bioterio.gruposhembras ADD CONSTRAINT pk_gruposhembras PRIMARY KEY (id_grupo);
ALTER TABLE ONLY bioterio.cajas ADD CONSTRAINT fk_id_grupo FOREIGN KEY (id_grupo) REFERENCES bioterio.gruposhembras(id_grupo) ON DELETE CASCADE;

DELETE FROM seguridad.permisos_menu_principal WHERE id_menu_principal = 251;
DELETE FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 250;
DELETE FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 251;
DELETE FROM seguridad.permisos WHERE id_permiso = 251;

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (251, '[Bioterio]AdministrarGruposHembras', 'Permite agregar/editar/eliminar grupos de hembras y cajas');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (250, 0, 'Conejera', '/Conejera/Gruposhembras');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (251, 250, 'Grupos Hembras', '/Conejera/Gruposhembras');
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (251, 251);
