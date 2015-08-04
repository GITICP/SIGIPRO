ALTER TABLE bioterio.solicitudes_conejera ADD  fecha_necesita date;
ALTER TABLE bioterio.solicitudes_conejera ADD  usuario_utiliza int;
ALTER TABLE bioterio.solicitudes_ratonera ADD  usuario_utiliza int;
ALTER TABLE bioterio.solicitudes_ratonera ADD CONSTRAINT fk_usuario_u FOREIGN KEY (usuario_utiliza)
      REFERENCES seguridad.usuarios (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE SET NULL;
ALTER TABLE bioterio.solicitudes_conejera ADD CONSTRAINT fk_usuario_u FOREIGN KEY (usuario_utiliza)
      REFERENCES seguridad.usuarios (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE SET NULL;