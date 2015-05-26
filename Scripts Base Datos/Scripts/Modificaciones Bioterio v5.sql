CREATE UNIQUE INDEX i_nombrecepa ON bioterio.cepas USING btree (nombre);
ALTER TABLE bioterio.conejas DROP COLUMN fecha_cambio;
ALTER TABLE bioterio.conejas ADD  fecha_cambio date;
ALTER TABLE bioterio.conejas ADD  observaciones character varying(500);
