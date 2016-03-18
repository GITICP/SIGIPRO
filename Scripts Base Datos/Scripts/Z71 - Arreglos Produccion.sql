INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (664, '[produccion]RevisarPaso', 'Permite darle el check de revision a un paso de protocolo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (665, '[produccion]VerificarPaso', 'Permite darle el check de verificado a un paso de protocolo.');

ALTER TABLE produccion.respuesta_pxp
ADD COLUMN id_usuario_revisar int;

ALTER TABLE produccion.respuesta_pxp
ADD COLUMN id_usuario_verificar int;


ALTER TABLE ONLY produccion.respuesta_pxp ADD CONSTRAINT fk_historial_usuario_revisar FOREIGN KEY (id_usuario_revisar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.respuesta_pxp ADD CONSTRAINT fk_historial_usuario_VERIFICAR FOREIGN KEY (id_usuario_verificar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
