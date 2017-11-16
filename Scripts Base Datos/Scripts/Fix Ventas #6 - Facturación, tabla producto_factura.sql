-- Table: ventas.producto_orden

-- DROP TABLE ventas.producto_orden;

CREATE TABLE ventas.producto_factura
(
  id_producto integer NOT NULL,
  id_factura integer NOT NULL,
  cantidad integer NOT NULL,
  fecha_entrega date,
  lotes character varying (200),
  CONSTRAINT fk_id_factura FOREIGN KEY (id_factura)
      REFERENCES ventas.factura (id_factura) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE,
  CONSTRAINT fk_id_producto FOREIGN KEY (id_producto)
      REFERENCES ventas.producto_venta (id_producto) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)
WITH (
  OIDS=FALSE
);
ALTER TABLE ventas.producto_factura
  OWNER TO postgres;