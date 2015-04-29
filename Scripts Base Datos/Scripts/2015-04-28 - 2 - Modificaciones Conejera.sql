CREATE TABLE bioterio.gruposhembras(
	id_grupo serial NOT NULL,
	identificador character varying(20) NOT NULL,
	cantidad_espacios integer NOT NULL
);

ALTER TABLE bioterio.conejas ADD  fecha_ingreso date NOT NULL;
ALTER TABLE bioterio.conejas ADD  fecha_cambio date NOT NULL;
ALTER TABLE bioterio.conejas ADD  fecha_seleccion date NOT NULL;
ALTER TABLE bioterio.machos ADD  id_padre character varying(50) NOT NULL;
ALTER TABLE bioterio.machos ADD  id_madre character varying(50) NOT NULL;

 CREATE TABLE bioterio.conejos_produccion(
	id_produccion serial NOT NULL,
	cantidad integer NOT NULL,
	detalle_procedencia character varying(250) NOT NULL
);

UPDATE seguridad.entradas_menu_principal SET redirect = '/Conejera/Gruposhembras', tag = 'Grupos Hembras' WHERE id_menu_principal = 251;