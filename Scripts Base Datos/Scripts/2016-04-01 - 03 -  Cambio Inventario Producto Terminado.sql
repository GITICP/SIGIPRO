
/*

    CAMBIOS Inventario de Producto Terminado
 
*/

-- Modificación a la tabla de inventario para que incluya un campo donde se guarda lo reservado

ALTER TABLE produccion.inventario_pt
  ADD COLUMN reservado integer;
