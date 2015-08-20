-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2015-06-23 21:00:25.101

DROP SCHEMA IF EXISTS control_calidad CASCADE;
CREATE SCHEMA control_calidad;

-- tables
-- Table: analisis
CREATE TABLE control_calidad.analisis (
    id_analisis serial  NOT NULL,
    estructura xml  NULL,
    machote varchar(500) NULL,
    CONSTRAINT analisis_pk PRIMARY KEY (id_analisis)
);



-- Table: analisis_grupo_solicitud
CREATE TABLE control_calidad.analisis_grupo_solicitud (
    id_analisis_grupo_solicitud serial  NOT NULL,
    id_grupo int  NOT NULL,
    id_analisis int  NOT NULL,
    CONSTRAINT analisis_grupo_solicitud_pk PRIMARY KEY (id_analisis_grupo_solicitud)
);



-- Table: certificados_equipos
CREATE TABLE control_calidad.certificados_equipos (
    id_certificado_equipo serial  NOT NULL,
    id_equipo int  NOT NULL,
    fecha_certificado date  NULL,
    path varchar(500)  NULL,
    CONSTRAINT certificados_equipos_pk PRIMARY KEY (id_certificado_equipo)
);



-- Table: certificados_reactivos
CREATE TABLE control_calidad.certificados_reactivos (
    id_certificado_reactivo serial  NOT NULL,
    fecha_certificado date  NULL,
    path varchar(500)  NULL,
    id_reactivo int  NOT NULL,
    CONSTRAINT certificados_reactivos_pk PRIMARY KEY (id_certificado_reactivo)
);



-- Table: equipos
CREATE TABLE control_calidad.equipos (
    id_equipo serial  NOT NULL,
    nombre varchar(50) NOT NULL,
    descripcion varchar(500)  NULL,
    id_tipo_equipo int  NOT NULL,
    CONSTRAINT equipos_pk PRIMARY KEY (id_equipo)
);



-- Table: grupos
CREATE TABLE control_calidad.grupos (
    id_grupo serial  NOT NULL,
    id_solicitud int  NOT NULL,
    CONSTRAINT grupos_pk PRIMARY KEY (id_grupo)
);



-- Table: grupos_muestras
CREATE TABLE control_calidad.grupos_muestras (
    id_muestra int  NOT NULL,
    id_grupo int  NOT NULL,
    CONSTRAINT grupos_muestras_pk PRIMARY KEY (id_muestra,id_grupo)
);



-- Table: muestras
CREATE TABLE control_calidad.muestras (
    id_muestra serial  NOT NULL,
    identificador varchar(20)  NOT NULL,
    id_tipo_muestra int  NOT NULL,
    fecha_descarte_estimada date,
    fecha_descarte_real date,
    CONSTRAINT muestras_pk PRIMARY KEY (id_muestra)
);



-- Table: tipos_muestras
CREATE TABLE control_calidad.tipos_muestras (
    id_tipo_muestra serial NOT NULL,
    nombre varchar(50) NOT NULL,
    descripcion varchar(500) NOT NULL,
    dias_descarte integer NULL,
    CONSTRAINT tipos_muestras_pk PRIMARY KEY (id_tipo_muestra)
);



-- Table: tipos_muestras_analisis
CREATE TABLE control_calidad.tipos_muestras_analisis (
    id_tipo_muestra int,
    id_analisis int,
    CONSTRAINT tipos_muestras_analisis_pk PRIMARY KEY (id_tipo_muestra, id_analisis)
);



-- Table: reactivos
CREATE TABLE control_calidad.reactivos (
    id_reactivo serial  NOT NULL,
    nombre varchar(50) NOT NULL,
    id_tipo_reactivo int  NOT NULL,
    preparacion varchar(500)  NULL,
    CONSTRAINT reactivos_pk PRIMARY KEY (id_reactivo)
);



-- Table: resultados
CREATE TABLE control_calidad.resultados (
    id_resultado serial  NOT NULL,
    id_analisis_grupo_solicitud int  NOT NULL,
    path varchar(500),
    datos xml  NOT NULL,
    id_usuario int NOT NULL,
    fecha date NOT NULL,
    repeticion integer NOT NULL,
    resultado varchar(500) NOT NULL,
    CONSTRAINT resultados_pk PRIMARY KEY (id_resultado)
);



-- Table: solicitudes
CREATE TABLE control_calidad.solicitudes (
    id_solicitud serial  NOT NULL,
    numero_solicitud varchar(20)  NOT NULL,
    id_usuario_solicitante int  NOT NULL,
    fecha_solicitud date NOT NULL,
    id_usuario_recibido int,
    fecha_recibido date,
    observaciones varchar(500),
    estado varchar(50),
    tipo_referencia character varying(100),
    tabla_referencia character varying(100),
    id_referenciado integer,
    informacion_referencia_adicional character varying(100),
    CONSTRAINT solicitudes_pk PRIMARY KEY (id_solicitud)
);



-- Table: tipos_equipos
CREATE TABLE control_calidad.tipos_equipos (
    id_tipo_equipo serial  NOT NULL,
    nombre varchar(50) NOT NULL,
    descripcion varchar(500) NULL,
    certificable boolean NOT NULL,
    CONSTRAINT tipos_equipos_pk PRIMARY KEY (id_tipo_equipo)
);



-- Table: tipos_equipos_analisis
CREATE TABLE control_calidad.tipos_equipos_analisis (
    id_analisis int  NOT NULL,
    id_tipo_equipo int  NOT NULL,
    CONSTRAINT tipos_equipos_analisis_pk PRIMARY KEY (id_analisis,id_tipo_equipo)
);



-- Table: tipos_reactivos
CREATE TABLE control_calidad.tipos_reactivos (
    id_tipo_reactivo serial  NOT NULL,
    nombre varchar(50)  NOT NULL,
    machote varchar(500) NULL,
    CONSTRAINT tipos_reactivos_pk PRIMARY KEY (id_tipo_reactivo)
);



-- Table: tipos_reactivos_analisis
CREATE TABLE control_calidad.tipos_reactivos_analisis (
    id_analisis int  NOT NULL,
    id_tipo_reactivo int  NOT NULL,
    certificable boolean NOT NULL,
    CONSTRAINT tipos_reactivos_analisis_pk PRIMARY KEY (id_analisis,id_tipo_reactivo)
);


-- Table: equipos_resultado
CREATE TABLE control_calidad.equipos_resultado (
    id_resultado int NOT NULL,
    id_equipo int NOT NULL,
    CONSTRAINT equipos_resultado_pk PRIMARY KEY (id_resultado, id_equipo)
);

-- Table: reactivos_resultado
CREATE TABLE control_calidad.reactivos_resultado (
    id_resultado int NOT NULL,
    id_reactivo int NOT NULL,
    CONSTRAINT reactivos_resultado_pk PRIMARY KEY (id_resultado, id_reactivo)
);

-- Table: informes
CREATE TABLE control_calidad.informes
(
    id_informe serial NOT NULL, 
    id_solicitud integer, 
    fecha date NOT NULL,
    realizado_por integer NOT NULL,
    CONSTRAINT pk_informes PRIMARY KEY (id_informe)
);

-- Table: resultados_informes
CREATE TABLE control_calidad.resultados_informes
(
    id_informe integer NOT NULL,
    id_resultado integer NOT NULL,
    CONSTRAINT pk_resultaods PRIMARY KEY (id_informe, id_resultado)
);


-- foreign keys
-- Reference: Informes_Solicitudes (table: solicitudes)
ALTER TABLE control_calidad.informes ADD CONSTRAINT fk_informes_solicitudes
    FOREIGN KEY (id_solicitud) 
    REFERENCES control_calidad.solicitudes (id_solicitud)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference: Informes_Solicitudes (table: usuarios)
ALTER TABLE control_calidad.informes ADD CONSTRAINT fk_informes_usuarios
    FOREIGN KEY (realizado_por) 
    REFERENCES seguridad.usuarios (id_usuario)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference: Resultados_Informes (table: resultados)
ALTER TABLE control_calidad.resultados_informes ADD CONSTRAINT fk_informes_resultados_resultado
    FOREIGN KEY (id_resultado)
    REFERENCES control_calidad.resultados (id_resultado)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference: Resultados_Informes (table: informes)
ALTER TABLE control_calidad.resultados_informes ADD CONSTRAINT fk_informes_resultados_informe
    FOREIGN KEY (id_informe)
    REFERENCES control_calidad.informes (id_informe)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference:  Equipos_Resultado_Resultado (table: resultados)
ALTER TABLE control_calidad.equipos_resultado ADD CONSTRAINT Equipos_Resultado_Resultados
    FOREIGN KEY (id_resultado)
    REFERENCES control_calidad.resultados (id_resultado)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference:  Reactivos_Resultado_Resultado (table: resultados)
ALTER TABLE control_calidad.reactivos_resultado ADD CONSTRAINT Reacivos_Resultado_Resultados
    FOREIGN KEY (id_resultado)
    REFERENCES control_calidad.resultados (id_resultado)
    NOT DEFERRABLE
    INITIALLY IMMEDIATE
;

-- Reference:  Analisis_Equipos_Analisis (table: tipos_equipos_analisis)


ALTER TABLE control_calidad.tipos_equipos_analisis ADD CONSTRAINT Analisis_Equipos_Analisis 
    FOREIGN KEY (id_analisis)
    REFERENCES control_calidad.analisis (id_analisis)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Analisis_Equipos_TiposEquipo (table: tipos_equipos_analisis)


ALTER TABLE control_calidad.tipos_equipos_analisis ADD CONSTRAINT Analisis_Equipos_TiposEquipo 
    FOREIGN KEY (id_tipo_equipo)
    REFERENCES control_calidad.tipos_equipos (id_tipo_equipo)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Analisis_Grupo_Solicitud_Analisis (table: analisis_grupo_solicitud)


ALTER TABLE control_calidad.analisis_grupo_solicitud ADD CONSTRAINT Analisis_Grupo_Solicitud_Analisis 
    FOREIGN KEY (id_analisis)
    REFERENCES control_calidad.analisis (id_analisis)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Analisis_Grupo_Solicitud_Grupos (table: analisis_grupo_solicitud)


ALTER TABLE control_calidad.analisis_grupo_solicitud ADD CONSTRAINT Analisis_Grupo_Solicitud_Grupos 
    FOREIGN KEY (id_grupo) 
    REFERENCES control_calidad.grupos (id_grupo) ON DELETE CASCADE
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Analisis_Reactivos_Analisis (table: tipos_reactivos_analisis)


ALTER TABLE control_calidad.tipos_reactivos_analisis ADD CONSTRAINT Analisis_Reactivos_Analisis 
    FOREIGN KEY (id_analisis)
    REFERENCES control_calidad.analisis (id_analisis)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Analisis_Reactivos_TiposReactivo (table: tipos_reactivos_analisis)


ALTER TABLE control_calidad.tipos_reactivos_analisis ADD CONSTRAINT Analisis_Reactivos_TiposReactivo 
    FOREIGN KEY (id_tipo_reactivo)
    REFERENCES control_calidad.tipos_reactivos (id_tipo_reactivo)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  CertificadoEquipo_Equipo (table: certificados_equipos)


ALTER TABLE control_calidad.certificados_equipos ADD CONSTRAINT CertificadoEquipo_Equipo 
    FOREIGN KEY (id_equipo)
    REFERENCES control_calidad.equipos (id_equipo)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  CertificadoReactivo_Reactivo (table: certificados_reactivos)


ALTER TABLE control_calidad.certificados_reactivos ADD CONSTRAINT CertificadoReactivo_Reactivo 
    FOREIGN KEY (id_reactivo)
    REFERENCES control_calidad.reactivos (id_reactivo)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Equipo_TipoEquipo (table: equipos)


ALTER TABLE control_calidad.equipos ADD CONSTRAINT Equipo_TipoEquipo 
    FOREIGN KEY (id_tipo_equipo)
    REFERENCES control_calidad.tipos_equipos (id_tipo_equipo)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Grupos_Muestras_Grupos (table: grupos_muestras)


ALTER TABLE control_calidad.grupos_muestras ADD CONSTRAINT Grupos_Muestras_Grupos 
    FOREIGN KEY (id_grupo)
    REFERENCES control_calidad.grupos (id_grupo) ON DELETE CASCADE
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Grupos_Muestras_Muestras (table: grupos_muestras)


ALTER TABLE control_calidad.grupos_muestras ADD CONSTRAINT Grupos_Muestras_Muestras 
    FOREIGN KEY (id_muestra)
    REFERENCES control_calidad.muestras (id_muestra) ON DELETE CASCADE
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Grupos_Solicitud (table: grupos)


ALTER TABLE control_calidad.grupos ADD CONSTRAINT Grupos_Solicitud 
    FOREIGN KEY (id_solicitud)
    REFERENCES control_calidad.solicitudes (id_solicitud)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Reactivo_TipoReactivo (table: reactivos)


ALTER TABLE control_calidad.reactivos ADD CONSTRAINT Reactivo_TipoReactivo 
    FOREIGN KEY (id_tipo_reactivo)
    REFERENCES control_calidad.tipos_reactivos (id_tipo_reactivo)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Resultado_Analisis_Grupo_Solicitud (table: resultados)


ALTER TABLE control_calidad.resultados ADD CONSTRAINT Resultado_Analisis_Grupo_Solicitud 
    FOREIGN KEY (id_analisis_grupo_solicitud)
    REFERENCES control_calidad.analisis_grupo_solicitud (id_analisis_grupo_solicitud)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Resultado_Usuario (table: resultados)


ALTER TABLE control_calidad.resultados ADD CONSTRAINT Resultado_Usuario 
    FOREIGN KEY (id_usuario)
    REFERENCES seguridad.usuarios (id_usuario)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Solicitudes (table: usuarios)


ALTER TABLE control_calidad.solicitudes ADD CONSTRAINT solicitudes_usuario_solicitante
    FOREIGN KEY (id_usuario_solicitante)
    REFERENCES seguridad.usuarios (id_usuario)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Solicitudes (table: usuarios)


ALTER TABLE control_calidad.solicitudes ADD CONSTRAINT solicitudes_usuario_recibido
    FOREIGN KEY (id_usuario_recibido)
    REFERENCES seguridad.usuarios (id_usuario)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Muestras (table: tipos_muestras)


ALTER TABLE control_calidad.muestras ADD CONSTRAINT muestras_tipo_muestra
    FOREIGN KEY (id_tipo_muestra)
    REFERENCES control_calidad.tipos_muestras (id_tipo_muestra)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Tipos_Muestras_Analisis (table: muestras)


ALTER TABLE control_calidad.tipos_muestras_analisis ADD CONSTRAINT tipos_muestras_analisis_tipos_muestras
    FOREIGN KEY (id_tipo_muestra)
    REFERENCES control_calidad.tipos_muestras (id_tipo_muestra)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Tipos_Muestras_Analisis (table: analisis)


ALTER TABLE control_calidad.tipos_muestras_analisis ADD CONSTRAINT tipos_muestras_analisis_analisis
    FOREIGN KEY (id_analisis)
    REFERENCES control_calidad.analisis (id_analisis)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

ALTER TABLE control_calidad.tipos_reactivos
ADD COLUMN descripcion varchar(500);

ALTER TABLE control_calidad.analisis
ADD COLUMN nombre varchar(50);

ALTER TABLE control_calidad.analisis
ADD COLUMN aprobado boolean;


-- MENÚ PRINCIPAL

    -- Solicitudes

    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (501, 500, 'Solicitudes', '/ControlCalidad/Solicitud');

    -- Reactivos

    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (502, 500, 'Reactivos', null);

        -- Submenús

    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (510, 502, 'Tipos de Reactivos', '/ControlCalidad/TipoReactivo');
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (530, 502, 'Reactivos', '/ControlCalidad/Reactivo');

    -- Análisis

    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (503, 500, 'Análisis', null);

        -- Submenús

    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (540, 503, 'Análisis', '/ControlCalidad/Analisis');
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (550, 503, 'Tipos de Muestra', '/ControlCalidad/TiposMuestra');
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (560, 503, 'Muestras', '/ControlCalidad/Muestra');

    -- Equipos
    
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (504, 500, 'Equipos', null);

        -- Submenús
    
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (520, 504, 'Equipos', '/ControlCalidad/Equipo');
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (521, 504, 'Tipos de Equipo', '/ControlCalidad/TipoEquipo');


-- PERMISOS

    -- Tipos de Equipo

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (500, '[ControlCalidad]AgregarTipoEquipo', 'Permite agregar un tipo de equipo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (501, '[ControlCalidad]EditarTipoEquipo', 'Permite editar un tipo de equipo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (502, '[ControlCalidad]EliminarTipoEquipo', 'Permite eliminar un tipo de equipo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (503, '[ControlCalidad]VerTipoEquipo', 'Permite ver un tipo de equipo.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (500, 521);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (501, 521);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (502, 521);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (503, 521);

    -- Tipos de Reactivo

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (510, '[ControlCalidad]AgregarTipoReactivo', 'Permite agregar un tipo de reactivo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (511, '[ControlCalidad]EditarTipoReactivo', 'Permite editar un tipo de reactivo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (512, '[ControlCalidad]EliminarTipoReactivo', 'Permite eliminar un tipo de reactivo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (513, '[ControlCalidad]VerTipoReactivo', 'Permite ver un tipo de reactivo.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (510, 510);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (511, 510);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (512, 510);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (513, 510);

    -- Equipos

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (520, '[ControlCalidad]AgregarEquipo', 'Permite agregar un equipo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (521, '[ControlCalidad]AdministrarCertificadoEquipo', 'Permite agregar y eliminar un certificado de equipo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (522, '[ControlCalidad]EditarEquipo', 'Permite editar un equipo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (523, '[ControlCalidad]EliminarEquipo', 'Permite eliminar un equipo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (524, '[ControlCalidad]VerEquipo', 'Permite ver un equipo.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (520, 520);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (521, 520);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (522, 520);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (523, 520);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (524, 520);

    -- Reactivos

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (530, '[ControlCalidad]AgregarReactivo', 'Permite agregar un reactivo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (531, '[ControlCalidad]AdministrarCertificadoReactivo', 'Permite agregar y eliminar un certificado de reactivo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (532, '[ControlCalidad]EditarReactivo', 'Permite editar un reactivo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (533, '[ControlCalidad]EliminarReactivo', 'Permite eliminar un reactivo.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (534, '[ControlCalidad]VerReactivo', 'Permite ver un reactivo.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (530, 530);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (531, 530);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (532, 530);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (533, 530);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (534, 530);

    -- Análisis

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (540, '[ControlCalidad]AgregarAnálisis', 'Permite agregar un Análisis.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (541, '[ControlCalidad]RealizarAnálisis', 'Permite realizar un Análisis de Control de Calidad.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (542, '[ControlCalidad]EditarAnálisis', 'Permite editar un Análisis.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (543, '[ControlCalidad]EliminarAnálisis', 'Permite eliminar un Análisis.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (544, '[ControlCalidad]AprobarAnálisis', 'Permite aprobar un Análisis.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (545, '[ControlCalidad]VerAnálisis', 'Permite ver un Análisis.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (546, '[ControlCalidad]VerResultado', 'Permite ver un Resultado.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (547, '[ControlCalidad]EditarResultado', 'Permite editar un Resultado.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (540, 540);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (541, 540);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (542, 540);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (543, 540);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (544, 540);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (545, 540);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (546, 540);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (547, 540);

    -- Solicitudes

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (550, '[ControlCalidad]SolicitarAnalisis', 'Permite agregar una Solicitud de Control de Calidad.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (551, '[ControlCalidad]RecibirSolicitud', 'Permite recibir la Solicitud para su agrupación y realización.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (552, '[ControlCalidad]AnularSolicitud', 'Permite anular una Solicitud de Control de Calidad.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (553, '[ControlCalidad]EditarSolicitud', 'Permite editar una Solicitud de Control de Calidad.');
--INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (554, '[ControlCalidad]EliminarSolicitud', 'Permite eliminar una Solicitud de Control de Calidad.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (555, '[ControlCalidad]VerSolicitud', 'Permite ver una Solicitud de Control de Calidad.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (556, '[ControlCalidad]AgruparMuestras', 'Permite agrupar las muestras de Solicitud de Control de Calidad.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (557, '[ControlCalidad]GenerarInforme', 'Permite generar un informe para una Solicitud de Control de Calidad.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (550, 501);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (551, 501);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (552, 501);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (553, 501);
--INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (554, 501);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (555, 501);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (556, 501);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (557, 501);

    -- Muestras

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (560, '[ControlCalidad]DescartarMuestras', 'Permite seleccionar un conjunto de Muestras y descartarlas.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (560, 560);

    -- Tipos de Muestra

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (561, '[ControlCalidad]AgregarTipoDeMuestra', 'Permite agregar un tipo de muestra.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (562, '[ControlCalidad]EditarTipoDeMuestra', 'Permite editar un tipo de muestra.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (563, '[ControlCalidad]EliminarTipoDeMuestra', 'Permite eliminar un tipo de muestra.');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (564, '[ControlCalidad]VerTipoDeMuestra', 'Permite ver un tipo de muestra.');

INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (561, 550);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (562, 550);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (563, 550);
INSERT INTO seguridad.permisos_menu_principal(id_permiso, id_menu_principal) VALUES (564, 550);


-- End of file.

/*
 *  Trigger de las repeticiones de los análisis
 */

CREATE FUNCTION control_calidad.consecutivo_repeticiones_resultados() RETURNS TRIGGER as $numero_repeticion$

    BEGIN
        NEW.repeticion = (SELECT count(id_resultado) FROM control_calidad.resultados where id_analisis_grupo_solicitud = NEW.id_analisis_grupo_solicitud);
        RETURN NEW;
    END;
$numero_repeticion$ LANGUAGE plpgsql;

CREATE TRIGGER actualizacion_repeticion BEFORE INSERT ON control_calidad.resultados FOR EACH ROW EXECUTE PROCEDURE control_calidad.consecutivo_repeticiones_resultados();
