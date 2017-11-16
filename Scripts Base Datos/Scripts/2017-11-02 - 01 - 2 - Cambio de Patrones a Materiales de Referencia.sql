
UPDATE seguridad.entradas_menu_principal
SET tag='Materiales de Referencia'
WHERE id_menu_principal=571 ;
UPDATE seguridad.entradas_menu_principal
SET tag='Tipos de Materiales de Referencia'
WHERE id_menu_principal=572 ;
UPDATE seguridad.entradas_menu_principal
SET tag='Materiales de Referencia'
WHERE id_menu_principal=570 ;


ALTER TABLE control_calidad.tipos_patronescontroles DROP COLUMN tipo ;
