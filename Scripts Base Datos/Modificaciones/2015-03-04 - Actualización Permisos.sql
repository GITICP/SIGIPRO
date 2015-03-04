--Update de un permiso
UPDATE seguridad.permisos SET descripcion = 'Permite visualizar los artículos en inventarios' WHERE id_permiso = 30;

--Update de una entrada del menú
UPDATE seguridad.permisos_menu_principal SET id_padre = 1000 WHERE id_menu_principal = 1001 and id_padre = 900;

--Actualización tabla producto externo
ALTER TABLE bodega.catalogo_externo ALTER COLUMN producto TYPE character varying(200);

--Eliminación de permisos erróneos
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 21 AND id_menu_principal = 103;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 22 AND id_menu_principal = 103;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 23 AND id_menu_principal = 103;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 21 AND id_menu_principal = 104;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 22 AND id_menu_principal = 104;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 23 AND id_menu_principal = 104;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 21 AND id_menu_principal = 105;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 22 AND id_menu_principal = 105;
DELETE FROM seguridad.permisos_menu_principal WHERE id_permiso = 23 AND id_menu_principal = 105;

--Inserción de permisos de activos fijos y ubicaciones
  --Activos fijos
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (31, '[Bodegas]Agregar Activo Fijo', 'Permite ingresar un activo fijo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (32, '[Bodegas]Editar Activo Fijo', 'Permite modificar y cambiar estado de un activo fijo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (33, '[Bodegas]Eliminar Activo Fijo', 'Permite eliminar un activo fijo');
  --Ubicaciones activos fijos
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (34, '[Bodegas]Agregar Ubicación Activo Fijo', 'Permite agregar una ubicación de activo fijo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (35, '[Bodegas]Editar Ubicación Activo Fijo', 'Permite editar una ubicación de activo fijo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (36, '[Bodegas]Eliminar Ubicación Activo Fijo', 'Permite eliminar una ubicación de activo fijo');
  --Ubicacoines de bodega
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (37, '[Bodegas]Agregar Ubicación de Bodega', 'Permite agregar una ubicación de bodega');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (38, '[Bodegas]Editar Ubicación de Bodega', 'Permite editar una ubicación de bodega');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (39, '[Bodegas]Eliminar Ubicación de Bodega', 'Permite eliminar una ubicación de bodega');

--Inserción  de asociaciones con el menú
  --Activos fijos
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (31, 103);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (32, 103);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (33, 103);
  --Ubicaciones activos fijos
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (34, 104);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (35, 104);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (36, 104);
  --Ubicaciones de bodega
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (37, 105);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (38, 105);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (39, 105);