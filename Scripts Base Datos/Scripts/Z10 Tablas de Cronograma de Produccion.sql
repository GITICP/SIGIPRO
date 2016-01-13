insert into seguridad.entradas_menu_principal values (601,600,'Cronograma de Producción','/Produccion/Cronograma de Producción');

create table produccion.cronograma
(
id_cronograma serial NOT NULL,
nombre character varying(100) NOT NULL,
observaciones character varying(500),
valido_desde date NOT NULL,
CONSTRAINT pk_cronograma_produccion PRIMARY KEY (id_cronograma)
);

create table produccion.semanas_cronograma
(
id_semana serial NOT NULL,
id_cronograma int NOT NULL,
fecha character varying(30) NOT NULL,
sangria character varying(100),
plasma_proyectado character varying(100),
plasma_real character varying(100),
antivenenos_lote character varying(100),
antivenenos_proyectada character varying(100),
antivenenos_bruta character varying(100),
antivenenos_neta character varying(100),
entregas_cantidad character varying(100),
entregas_destino character varying(100),
entregas_lote character varying(100),
observaciones character varying(100),
CONSTRAINT pk_semana_produccion PRIMARY KEY (id_semana),
CONSTRAINT fk_semana_cronograma FOREIGN KEY (id_cronograma)
      REFERENCES produccion.cronograma (id_cronograma) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE SET NULL
);