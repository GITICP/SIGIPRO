-- Cliente: Agregar dirección, cédula y tipo de persona (Jurídica o Física) en Cliente 
ALTER TABLE ventas.cliente
ADD COLUMN direccion character varying(350),
ADD COLUMN cedula character varying(30),
ADD COLUMN persona character varying(20);

-- Productos de Venta: Agregar Tipo
ALTER TABLE ventas.producto_venta
ADD COLUMN tipo character varying(20);

-- Intención de Venta
ALTER TABLE ventas.intencion_venta
DROP CONSTRAINT fk_intenciones_cliente,
ALTER COLUMN id_cliente DROP NOT NULL,
ADD COLUMN nombre_cliente character varying(80),
ADD COLUMN telefono character varying(12),
ADD COLUMN correo_electronico character varying(70);

-- Productos Intención de Venta
ALTER TABLE ventas.producto_intencion
ALTER COLUMN posible_fecha_despacho DROP NOT NULL;

-- Cotización
ALTER TABLE ventas.cotizacion
DROP CONSTRAINT fk_cotizacion_cliente,
ALTER COLUMN id_cliente DROP NOT NULL,
DROP COLUMN id_cliente,
ADD COLUMN identificador character varying(12);

-- Orden de Compra
ALTER TABLE ventas.orden_compra
DROP CONSTRAINT fk_id_cliente,
ALTER COLUMN id_cliente DROP NOT NULL,
DROP COLUMN id_cliente,
DROP COLUMN rotulacion,
ADD COLUMN rotulacion character varying(1500),
ADD COLUMN documento character varying(500);

-- Productos de la Orden de Compra
ALTER TABLE ventas.producto_orden
ADD COLUMN fecha_entrega date,
DROP CONSTRAINT pk_producto_orden;

-- Factura
ALTER TABLE ventas.factura
ADD COLUMN moneda character varying(20);
