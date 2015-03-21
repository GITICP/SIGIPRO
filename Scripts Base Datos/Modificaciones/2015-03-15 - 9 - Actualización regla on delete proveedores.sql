-- Modificación de ON DELETE en referencias
    -- Proveedores
ALTER TABLE ONLY bodega.catalogo_externo DROP CONSTRAINT fk_id_proveedor, 
ADD CONSTRAINT fk_id_proveedor FOREIGN KEY (id_proveedor) REFERENCES compras.proveedores(id_proveedor);