-- Eliminación temporal de Facturas y Pagos del Menú principal hasta que se aclare el asunto del web service de FUNDEVI

delete from seguridad.entradas_menu_principal where id_menu_principal = 707; -- Facturas
delete from seguridad.entradas_menu_principal where id_menu_principal = 708; -- Pagos