--Actualización tabla reactivos
ALTER TABLE bodega.reactivos ALTER COLUMN cantidad_botella_bodega TYPE character varying(45);
ALTER TABLE bodega.reactivos ALTER COLUMN cantidad_botella_lab TYPE character varying(45);
