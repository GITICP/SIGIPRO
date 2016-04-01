
/*

    CAMBIOS SUB BODEGAS
 
*/

-- Modificación a la tabla de sub bodegas para incluir el tipo de sub bodega

ALTER TABLE bodega.inventarios_sub_bodegas
  ADD COLUMN numero_lote character varying(100);

ALTER TABLE bodega.inventarios_sub_bodegas
  DROP CONSTRAINT unique_inventarios_sub_bodegas_permite_fecha_null;

ALTER TABLE bodega.inventarios_sub_bodegas
  ADD CONSTRAINT fk_id_producto FOREIGN KEY (id_producto) REFERENCES bodega.catalogo_interno (id_producto);

ALTER TABLE ONLY bodega.inventarios_sub_bodegas ADD CONSTRAINT 
    unique_inventarios_sub_bodegas_permite_num_lote_fecha_null UNIQUE (id_sub_bodega, id_producto, num_lote, fecha_vencimiento);