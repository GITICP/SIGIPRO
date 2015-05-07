ALTER TABLE bioterio.conejas
DROP CONSTRAINT fk_cajas

ALTER TABLE bioterio.conejas 
ADD CONSTRAINT fk_cajas FOREIGN KEY (id_caja)
      REFERENCES bioterio.cajas (id_caja) ON DELETE CASCADE;

ALTER TABLE bioterio.cruces
DROP CONSTRAINT fk_id_coneja;

ALTER TABLE bioterio.cruces
DROP CONSTRAINT fk_id_macho;

ALTER TABLE bioterio.cruces 
ADD CONSTRAINT fk_id_coneja FOREIGN KEY (id_coneja)
      REFERENCES bioterio.conejas (id_coneja) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;

ALTER TABLE bioterio.cruces       
ADD CONSTRAINT fk_id_macho FOREIGN KEY (id_macho)
      REFERENCES bioterio.machos (id_macho) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE;