--Esquema bioterio
CREATE SCHEMA bioterio;
--Tablas esquema bioterio
CREATE TABLE bioterio.cajas_ratonera(
	id_caja_ratonera serial NOT NULL,
	numero_caja integer NOT NULL,
	id_cepa integer
);

CREATE TABLE bioterio.catalogo_cepas( 
	id_cepa serial NOT NULL,
	nombre character varying(45) NOT NULL,
	macho_as character varying(45) NOT NULL,
	hembra_as character varying(45) NOT NULL,
        fecha_apareamiento_i date NOT NULL,
        fecha_apareamiento_f date NOT NULL,
	fecha_eliminacionmacho_i date NOT NULL,
        fecha_eliminacionmacho_f date NOT NULL,
        fecha_eliminacionhembra_i date NOT NULL,
        fecha_eliminacionhembra_f date NOT NULL,
        fecha_seleccionnuevos_i date NOT NULL,
        fecha_seleccionnuevos_f date NOT NULL,
        fecha_reposicionciclo_i date NOT NULL,
        fecha_reposicionciclo_f date NOT NULL,
        fecha_vigencia date NOT NULL
 );

 CREATE TABLE bioterio.destetes( 
	id_destete serial NOT NULL,
	fecha_destete date NOT NULL,
	numero_hembras integer NOT NULL,
	numero_machos integer NOT NULL
 );

 CREATE TABLE bioterio.solicitudes_ratonera( 
	id_solicitud serial NOT NULL,
	fecha_solicitud date NOT NULL,
	numero_animales integer NOT NULL,
	peso_requerido character varying(20) NOT NULL,
	numero_cajas integer NOT NULL,
	sexo character varying(10) NOT NULL,
	id_cepa integer NOT NULL, 
	usuario_solicitante integer NOT NULL,
	observaciones character varying(200),
	observaciones_rechazo character varying(200),
	estado varchar(15) NOT NULL
 );

 CREATE TABLE bioterio.entregas_solicitudes_ratonera( 
	id_entrega serial NOT NULL,
	id_solicitud integer NOT NULL,
	fecha_entrega date NOT NULL,
	numero_animales integer NOT NULL,
	peso integer NOT NULL,
	numero_cajas integer NOT NULL,
	sexo character varying(10) NOT NULL,
	id_cepa integer NOT NULL, 
	usuario_recipiente integer NOT NULL
 );

--Llaves primarias esquema de bioterio 
ALTER TABLE ONLY bioterio.cajas_ratonera ADD CONSTRAINT pk_cajas_ratoner PRIMARY KEY (id_caja_ratonera);
ALTER TABLE ONLY bioterio.catalogo_cepas ADD CONSTRAINT pk_roles_usuarios PRIMARY KEY (id_cepa);
ALTER TABLE ONLY bioterio.destetes ADD CONSTRAINT pk_secciones PRIMARY KEY (id_destete);
ALTER TABLE ONLY bioterio.solicitudes_ratonera ADD CONSTRAINT pk_solicitudes PRIMARY KEY (id_solicitud);
ALTER TABLE ONLY bioterio.entregas_solicitudes_ratonera ADD CONSTRAINT pk_entregas PRIMARY KEY (id_entrega);

--Llaves foraneas esquema bioterio
ALTER TABLE ONLY bioterio.solicitudes_ratonera ADD CONSTRAINT fk_id_cepa FOREIGN KEY (id_cepa) REFERENCES bioterio.catalogo_cepas(id_cepa) ON DELETE SET NULL;
ALTER TABLE ONLY bioterio.solicitudes_ratonera ADD CONSTRAINT fk_id_usr_solicitante FOREIGN KEY (usuario_solicitante) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY bioterio.cajas_ratonera ADD CONSTRAINT fk_id_cepa_cajas FOREIGN KEY (id_cepa) REFERENCES bioterio.catalogo_cepas(id_cepa) ON DELETE SET NULL;
ALTER TABLE ONLY bioterio.entregas_solicitudes_ratonera ADD CONSTRAINT fk_id_usr_recipiente FOREIGN KEY (usuario_recipiente) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY bioterio.entregas_solicitudes_ratonera ADD CONSTRAINT fk_id_solicitud FOREIGN KEY (id_solicitud) REFERENCES bioterio.solicitudes_ratonera (id_solicitud) ON DELETE SET NULL;
ALTER TABLE ONLY bioterio.entregas_solicitudes_ratonera ADD CONSTRAINT fk_id_cepa_e FOREIGN KEY (id_cepa) REFERENCES bioterio.catalogo_cepas (id_cepa) ON DELETE SET NULL;

--Indices unicos esquema bioterio
CREATE UNIQUE INDEX i_caja_ratonera ON bioterio.cajas_ratonera USING btree (numero_caja);

--Borrar la entrada del Menu de Bioterio
DELETE FROM seguridad.entradas_menu_principal WHERE id_menu_principal = 200;

--Permisos asociados a Bioterio
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (201, '[Bioterio]AdministrarCajasRatonera', 'Permite agregar/editar/eliminar cajas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (202, '[Bioterio]AdministrarCepas', 'Permite agregar/editar/eliminar Cepas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (203, '[Bioterio]AdministrarSolicitudes', 'Permite agregar/editar/eliminar cajas');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (204, '[Bioterio]AdministrarDestetes', 'Permite agregar/editar/eliminar Destetes');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (205, '[Bioterio]RealizarSolicitudes', 'Permite realizar solicitudes de ratones');


--Entradas del Menu de Bioterio
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (250, 0, 'Conejera', '/Conejera/Cajas');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (200, 0, 'Ratonera', '/Ratonera/Cajas');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (201, 200, 'Cajas Ratonera', '/Ratonera/Cajas');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (202, 200, 'Cepas', '/Ratonera/Cepas');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (203, 200, 'Solicitudes de Ratones', '/Ratonera/Solicitudes');
INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (204, 200, 'Destetes', '/Ratonera/Destetes');


--Permisos del menu principal de Bioterio
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (201, 201);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (202, 202);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (204, 204);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (203, 203);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (205, 203);
