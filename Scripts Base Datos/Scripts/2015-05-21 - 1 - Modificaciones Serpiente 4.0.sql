ALTER TABLE SERPENTARIO.VENENOS
RENAME COLUMN cantidad_maxima to cantidad_minima;

CREATE TABLE SERPENTARIO.RESTRICCION(
    id_restriccion serial not null,
    id_usuario integer not null,
    id_veneno integer not null,
    cantidad_anual decimal not null
);

ALTER TABLE ONLY serpentario.RESTRICCION ADD CONSTRAINT pk_restriccion PRIMARY KEY (id_restriccion);

ALTER TABLE ONLY serpentario.restriccion ADD CONSTRAINT fk_id_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios(id_usuario);

ALTER TABLE ONLY serpentario.restriccion ADD CONSTRAINT fk_id_veneno FOREIGN KEY (id_veneno) REFERENCES serpentario.venenos(id_veneno);

ALTER TABLE ONLY serpentario.restriccion ADD CONSTRAINT unq_restriccion UNIQUE (id_usuario,id_veneno);

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (360, '[Serpentario]AgregarRestriccion', 'Permite agregar restricciones a los usuarios.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (361, '[Serpentario]EditarRestriccion', 'Permite editar las cantidades anuales restringidas de cada usuario.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (362, '[Serpentario]EliminarRestriccion', 'Permite eliminar una restriccion de un usuario.');
