CREATE SCHEMA reportes;

CREATE TABLE reportes.reportes
(
   id_reporte serial NOT NULL PRIMARY KEY, 
   nombre character varying(200) NOT NULL,
   consulta character varying(5000) NOT NULL,
   descripcion character varying(500),
   url_js character varying(200) NOT NULL,
   id_seccion integer NOT NULL
);

CREATE TABLE reportes.parametros
(
   id_reporte integer NOT NULL, 
   num_parametro integer NOT NULL, 
   nombre character varying(50) NOT NULL
   tipo_parametro character varying(30) NOT NULL, 
   info_adicional character varying(50), 
   CONSTRAINT pk_parametros PRIMARY KEY (num_parametro, id_reporte), 
   CONSTRAINT fk_parametros_reportes FOREIGN KEY (id_reporte) REFERENCES reportes.reportes (id_reporte) ON UPDATE NO ACTION ON DELETE CASCADE
);

CREATE TABLE reportes.permisos_reportes
(
   id_reporte integer NOT NULL, 
   id_usuario integer NOT NULL, 
   CONSTRAINT pk_permisos_reportes PRIMARY KEY (id_reporte, id_usuario), 
   CONSTRAINT fk_permisos_reportes_reporte FOREIGN KEY (id_reporte) REFERENCES reportes.reportes (id_reporte) ON UPDATE NO ACTION ON DELETE CASCADE, 
   CONSTRAINT fk_permisos_reportes_usuario FOREIGN KEY (id_usuario) REFERENCES seguridad.usuarios (id_usuario) ON UPDATE NO ACTION ON DELETE CASCADE
);

ALTER TABLE reportes.reportes
  ADD CONSTRAINT fk_reportes_secciones FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones (id_seccion) ON UPDATE NO ACTION ON DELETE NO ACTION;
