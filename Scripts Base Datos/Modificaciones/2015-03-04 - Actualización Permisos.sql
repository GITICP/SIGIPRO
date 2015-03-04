--Update de un permiso
UPDATE seguridad.permisos SET descripcion = 'Permite visualizar los artículos en inventarios' WHERE id_permiso = 30;

--Update de una entrada del menú
UPDATE seguridad.entradas_menu_principal SET id_padre = 1000 WHERE id_menu_principal = 1001 and id_padre = 900;

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
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 31, '[Bodegas]Agregar Activo Fijo', 'Permite ingresar un activo fijo' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 31);
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 32, '[Bodegas]Editar Activo Fijo', 'Permite modificar y cambiar estado de un activo fijo' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 32);
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 33, '[Bodegas]Eliminar Activo Fijo', 'Permite eliminar un activo fijo' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 33);
  --Ubicaciones activos fijos
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 34, '[Bodegas]Agregar Ubicación Activo Fijo', 'Permite agregar una ubicación de activo fijo' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 34);
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 35, '[Bodegas]Editar Ubicación Activo Fijo', 'Permite editar una ubicación de activo fijo' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 35);
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 36, '[Bodegas]Eliminar Ubicación Activo Fijo', 'Permite eliminar una ubicación de activo fijo' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 36);
  --Ubicacoines de bodega
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 37, '[Bodegas]Agregar Ubicación de Bodega', 'Permite agregar una ubicación de bodega' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 37);
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 38, '[Bodegas]Editar Ubicación de Bodega', 'Permite editar una ubicación de bodega' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 38);
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) SELECT 39, '[Bodegas]Eliminar Ubicación de Bodega', 'Permite eliminar una ubicación de bodega' WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos WHERE id_permiso = 39);

--Inserción  de asociaciones con el menú
  --Activos fijos
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 31, 103 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 31 AND id_menu_principal = 103);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 32, 103 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 32 AND id_menu_principal = 103);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 33, 103 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 33 AND id_menu_principal = 103);
  --Ubicaciones activos fijos
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 34, 104 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 34 AND id_menu_principal = 104);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 35, 104 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 35 AND id_menu_principal = 104);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 36, 104 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 36 AND id_menu_principal = 104);
  --Ubicaciones de bodega
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 37, 105 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 37 AND id_menu_principal = 105);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 38, 105 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 38 AND id_menu_principal = 105);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) SELECT 39, 105 WHERE NOT EXISTS (SELECT 1 FROM seguridad.permisos_menu_principal WHERE id_permiso = 39 AND id_menu_principal = 105);