ALTER TABLE produccion.veneno_produccion DROP CONSTRAINT fk_id_veneno_serpentario;

ALTER TABLE ONLY produccion.veneno_produccion ADD CONSTRAINT fk_id_veneno_lote_serpentario FOREIGN KEY (id_veneno_serpentario) REFERENCES serpentario.lote (id_lote) ON DELETE SET NULL;
