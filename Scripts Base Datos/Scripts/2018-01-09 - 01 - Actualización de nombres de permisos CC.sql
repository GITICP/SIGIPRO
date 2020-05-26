UPDATE seguridad.permisos
SET nombre='AgregarMaterialReferencia', descripcion='Permite agregar un material de referencia.', id_seccion=2
WHERE id_permiso=571;
UPDATE seguridad.permisos
SET nombre='EditarMaterialReferencia', descripcion='Permite editar un material de referencia.', id_seccion=2
WHERE id_permiso=572;
UPDATE seguridad.permisos
SET nombre='EliminarMaterialReferencia', descripcion='Permite eliminar material de referencia.', id_seccion=2
WHERE id_permiso=573;
UPDATE seguridad.permisos
SET nombre='VerMaterialReferencia', descripcion='Permite ver un material de referencia.', id_seccion=2
WHERE id_permiso=574;
UPDATE seguridad.permisos
SET nombre='AgregarTipoMaterialReferencia', descripcion='Permite agregar un tipo de material de referencia.', id_seccion=2
WHERE id_permiso=580;
UPDATE seguridad.permisos
SET nombre='EditarTipoMaterialReferencia', descripcion='Permite editar un tipo de material de referencia.', id_seccion=2
WHERE id_permiso=581;
UPDATE seguridad.permisos
SET nombre='EliminarTipoMaterialReferencia', descripcion='Permite eliminar un tipo de material de referencia.', id_seccion=2
WHERE id_permiso=582;
UPDATE seguridad.permisos
SET nombre='VerTipoMaterialReferencia', descripcion='Permite ver un tipo de material de referencia.', id_seccion=2
WHERE id_permiso=583;

DELETE FROM seguridad.permisos_menu_principal 
WHERE id_menu_principal >= 570 AND id_menu_principal <= 572;

INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(571, 571);
INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(580, 572);
INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(581, 572);
INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(582, 572);
INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(583, 572);
INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(572, 571);
INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(573, 571);
INSERT INTO seguridad.permisos_menu_principal
(id_permiso, id_menu_principal)
VALUES(574, 571);
