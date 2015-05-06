ALTER TABLE bioterio.conejos_produccion
DROP COLUMN detalle_procedencia;

ALTER TABLE bioterio.conejos_produccion
ADD COLUMN detalle_procedencia character varying(250);

CREATE UNIQUE INDEX i_identificador ON bioterio.gruposhembras USING btree (identificador);

