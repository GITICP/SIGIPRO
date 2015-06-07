ALTER TABLE bioterio.analisis_parasitologicos
DROP COLUMN responsable;

ALTER TABLE bioterio.analisis_parasitologicos
ADD COLUMN responsable integer;

ALTER TABLE bioterio.analisis_parasitologicos ADD CONSTRAINT fk_responsable FOREIGN KEY (responsable) REFERENCES seguridad.usuarios (id_usuario)
      ON DELETE SET NULL;