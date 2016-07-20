
SELECT salidas_entradas.id_producto, salidas_entradas.nombre AS "Nombre de Producto"
, salidas_entradas.sum_entradas AS "Cantidad de Salidas"
, salidas_entradas.sum_salidas AS "Cantidad de Salidas"
, inventarios.stock_actual AS "Stock Actual"
FROM 
(
	SELECT ci.id_producto, ci.nombre, SUM(ing.cantidad) AS sum_entradas, SUM(s.cantidad) AS sum_salidas
	FROM bodega.inventarios i
		INNER JOIN bodega.catalogo_interno ci ON ci.id_producto = i.id_producto
		INNER JOIN bodega.solicitudes s ON s.id_inventario = i.id_inventario
		INNER JOIN bodega.ingresos ing ON ing.id_producto = ci.id_producto
	WHERE ci.id_producto IN (SELECT id_producto FROM bodega.reactivos)
	GROUP BY ci.id_producto
	ORDER BY ci.id_producto
) AS salidas_entradas
INNER JOIN
(
	SELECT i.id_producto, SUM(i.stock_actual) AS stock_actual
	FROM bodega.inventarios i
	WHERE id_producto IN (SELECT id_producto FROM bodega.reactivos)
	
) AS inventarios ON salidas_entradas.id_producto = inventarios.id_producto
