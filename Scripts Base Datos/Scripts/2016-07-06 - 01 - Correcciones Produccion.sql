ALTER TABLE PRODUCCION.PROTOCOLO
ADD COLUMN aprobacion_gestion boolean;

ALTER TABLE PRODUCCION.ACTIVIDAD_APOYO
ADD COLUMN aprobacion_gestion boolean;

ALTER TABLE PRODUCCION.ACTIVIDAD_APOYO
ADD COLUMN estado BOOLEAN;

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (646, '[produccion]AprobarGestionProtocolo', 'Permite a un gestor de calidad aprobar o rechazar un protocolo de producción.');
 
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (680, '[produccion]AprobarGestionActividad', 'Permite a un gestor de calidad aprobar o rechazar una actividad de apoyo.');

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (681, '[produccion]ActivarRetirarActividad', 'Permite al usuario de retirar o activar una actividad de apoyo.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (646, 612);

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (680, 632);

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (681, 632);

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (669, '[produccion]CerrarPaso', 'Permite darle el check de cerrado a un paso de protocolo y devolverse.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (669, 613);