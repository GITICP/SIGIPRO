ALTER TABLE bioterio.caras DROP COLUMN fecha_vigencia;
ALTER TABLE bioterio.destetes ADD  id_cepa integer NOT NULL;
ALTER TABLE ONLY bioterio.destetes ADD CONSTRAINT fk_id_cepa_destetes FOREIGN KEY (id_cepa) REFERENCES bioterio.cepas(id_cepa) ON DELETE SET NULL;