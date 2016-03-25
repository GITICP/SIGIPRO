INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (664, '[produccion]RevisarPaso', 'Permite darle el check de revision a un paso de protocolo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (665, '[produccion]VerificarPaso', 'Permite darle el check de verificado a un paso de protocolo.');

ALTER TABLE produccion.respuesta_pxp
ADD COLUMN id_usuario_revisar int;

ALTER TABLE produccion.respuesta_pxp
ADD COLUMN id_usuario_verificar int;

ALTER TABLE ONLY produccion.respuesta_pxp ADD CONSTRAINT fk_historial_usuario_revisar FOREIGN KEY (id_usuario_revisar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.respuesta_pxp ADD CONSTRAINT fk_historial_usuario_VERIFICAR FOREIGN KEY (id_usuario_verificar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;

ALTER TABLE produccion.actividad_apoyo
ADD COLUMN requiere_ap boolean;

ALTER TABLE produccion.respuesta_aa
ADD COLUMN estado int;

ALTER TABLE produccion.historial_respuesta_aa
ADD COLUMN id_usuario_revisar int;

ALTER TABLE produccion.historial_respuesta_aa
ADD COLUMN id_usuario_aprobar int;

ALTER TABLE ONLY produccion.historial_respuesta_aa ADD CONSTRAINT fk_historial_usuario_revisar FOREIGN KEY (id_usuario_revisar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.historial_respuesta_aa ADD CONSTRAINT fk_historial_usuario_aprobar FOREIGN KEY (id_usuario_aprobar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;

ALTER TABLE PRODUCCION.HISTORIAL_PASO
ALTER COLUMN NOMBRE TYPE CHARACTER VARYING (200)

