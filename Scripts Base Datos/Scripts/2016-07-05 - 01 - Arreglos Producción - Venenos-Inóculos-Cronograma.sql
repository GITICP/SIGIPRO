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

-- Agregar Historial de Consumo Directo de Venenos de Producción
create table produccion.historial_consumos
(
id_historial_consumo serial NOT NULL,
id_veneno integer NOT NULL,
fecha date NOT NULL,
cantidad integer NOT NULL,
id_usuario integer NOT NULL,
CONSTRAINT pk_historial_consumos PRIMARY KEY (id_historial_consumo),
CONSTRAINT fk_id_veneno FOREIGN KEY (id_veneno)
      REFERENCES produccion.veneno_produccion (id_veneno) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE SET NULL
);

