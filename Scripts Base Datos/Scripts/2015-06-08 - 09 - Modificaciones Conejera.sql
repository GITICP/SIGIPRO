ALTER TABLE ONLY bioterio.conejos_produccion ADD CONSTRAINT pk_conejos_produccion PRIMARY KEY (id_produccion);

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (256, '[Bioterio]AdministrarConejosProduccion', 'Permite agregar/editar/eliminar grupos de conejos de produccion');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (256, 250, 'Conejos Produccion', '/Conejera/ConejosProduccion');
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (256, 256);

ALTER TABLE bioterio.cepas  ADD  descripcion character varying(200);
ALTER TABLE bioterio.machos  ADD  fecha_preseleccion date NOT NULL;
