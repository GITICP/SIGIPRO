CREATE TABLE calendario.notificaciones
(
  id_notificacion serial,
  id_evento integer NOT NULL,
  id_usuario integer NOT NULL,
  tiempo_previo integer NOT NULL,
  CONSTRAINT pk_id_notificacion PRIMARY KEY (id_notificacion),
  CONSTRAINT fk_id_evento_n FOREIGN KEY (id_evento)
      REFERENCES calendario.eventos (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_id_usuario_n FOREIGN KEY (id_usuario)
      REFERENCES seguridad.usuarios (id_usuario) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)