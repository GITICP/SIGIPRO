-- Cambiar la relación de veneno de producción -> veneno serpentario a producción -> lote serpentario
﻿ALTER TABLE produccion.veneno_produccion DROP CONSTRAINT fk_id_veneno_serpentario;
ALTER TABLE ONLY produccion.veneno_produccion ADD CONSTRAINT fk_id_veneno_lote_serpentario FOREIGN KEY (id_veneno_serpentario) REFERENCES serpentario.lote (id_lote) ON DELETE SET NULL;

-- Ajustar las columnas de las semanas de cronograma para que se puedan instertar hasta 500 caracteresALTER TABLE produccion.semanas_cronograma ALTER COLUMN sangria TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN plasma_proyectado TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN plasma_real TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN antivenenos_lote TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN antivenenos_proyectada TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN antivenenos_bruta TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN antivenenos_neta TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN entregas_cantidad TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN entregas_destino TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN entregas_lote TYPE character varying(500);
ALTER TABLE produccion.semanas_cronograma ALTER COLUMN observaciones TYPE character varying(500);