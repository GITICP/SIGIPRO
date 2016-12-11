--Permisos asociados a ventas
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (703, '[Ventas]AdministrarProductosDeVenta', 'Permite agregar/editar/eliminar productos de venta');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (704, '[Ventas]AdministrarFacturasTemporalmente', 'Permite agregar/editar/eliminar facturas temporalmente');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (705, '[Ventas]AdministrarContratosComercializacion', 'Permite agregar/editar/eliminar contratos de comercialización');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (706, '[Ventas]Consultar', 'Permite consultar todas las secciones de venta');

--Permisos del menu principal de ventas

--Administrador del ventas
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 700);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 702);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 703);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 704);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 705);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 706);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 707);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 708);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 709);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 710);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 711);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (701, 712);
--Clientes
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (702, 700);
--Productos de Venta
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (703, 700);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (703, 702);
--Facturas Temporal
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (704, 700);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (704, 703);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (704, 707);
--Contratos Comercialización
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (705, 700);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (705, 709);
--Consultar
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 700);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 701);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 702);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 703);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 704);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 705);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 706);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 707);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 708);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 709);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 710);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 711);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (706, 712);