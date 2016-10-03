SELECT
  s.nombre_seccion AS "Sección"
, ci.codigo_icp AS "Código ICP"
, ci.nombre AS "Nombre Producto"
, ci.presentacion AS "Presentación"
, i.stock_actual AS "Stock Actual"
FROM bodega.inventarios i 
	INNER JOIN bodega.catalogo_interno ci ON ci.id_producto = i.id_producto
	INNER JOIN seguridad.secciones s ON s.id_seccion = i.id_seccion
WHERE i.id_seccion IN (?)
ORDER BY s.nombre_seccion;