--Indices unicos esquema bodega
CREATE UNIQUE INDEX i_placa_activo_fijo ON bodega.activos_fijos USING btree (placa);