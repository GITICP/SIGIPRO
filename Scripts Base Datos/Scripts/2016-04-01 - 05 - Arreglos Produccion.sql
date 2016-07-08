ALTER TABLE produccion.historial_respuesta_pxp
ADD COLUMN id_usuario_revisar int;

ALTER TABLE produccion.historial_respuesta_pxp
ADD COLUMN id_usuario_verificar int;

ALTER TABLE produccion.respuesta_pxp
ADD COLUMN esultimo boolean;

ALTER TABLE ONLY produccion.historial_respuesta_pxp ADD CONSTRAINT fk_historial_usuario_revisar FOREIGN KEY (id_usuario_revisar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;
ALTER TABLE ONLY produccion.historial_respuesta_pxp ADD CONSTRAINT fk_historial_usuario_VERIFICAR FOREIGN KEY (id_usuario_verificar) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;

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

ALTER TABLE PRODUCCION.LOTE
ADD COLUMN id_usuario_distribucion int;

ALTER TABLE ONLY produccion.lote ADD CONSTRAINT fk_lote_usuario_distribucion FOREIGN KEY (id_usuario_distribucion) REFERENCES seguridad.usuarios(id_usuario) ON DELETE SET NULL;


ALTER TABLE PRODUCCION.LOTE
ADD COLUMN fecha_vencimiento date;

--Aspectos del Menu

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (650, 600, 'Estado Actual', null, 7);

INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect, orden) VALUES (651, 650, 'Ver Estado Actual', '/Produccion/Lote?accion=verestado', 1);


INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (664, '[produccion]RevisarPaso', 'Permite darle el check de revision a un paso de protocolo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (665, '[produccion]VerificarPaso', 'Permite darle el check de verificado a un paso de protocolo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (666, '[produccion]VerEstadoLote', 'Permite ver el estado del lote de producción actual.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (667, '[produccion]DistribucionLote', 'Permite aprobar la distribución de un lote.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (668, '[produccion]FechaVencimientoLote', 'Permite agregar la fecha de vencimiento de un lote.');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (678, '[produccion]RevisarActividadApoyo', 'Permite revisar una actividad de apoyo, por parte del regente.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (679, '[produccion]AprobarActividadApoyo', 'Permite aprobar una actividad de apoyo, por parte del encargado.');



INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (666, 651);

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (664, 613);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (665, 613);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (666, 613);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (667, 613);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (668, 613);

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (678, 632);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (679, 632);
