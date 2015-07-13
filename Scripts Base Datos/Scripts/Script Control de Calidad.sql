-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2015-06-23 21:00:25.101

DROP SCHEMA IF EXISTS control_calidad CASCADE;
CREATE SCHEMA control_calidad;

-- tables
-- Table: analisis
CREATE TABLE control_calidad.analisis (
    id_analisis serial  NOT NULL,
    estructura xml  NULL,
    machote varchar(500)  NOT NULL,
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
    estado varchar(50),
    CONSTRAINT solicitudes_pk PRIMARY KEY (id_solicitud)
);



-- Table: tipos_equipos
CREATE TABLE control_calidad.tipos_equipos (
    id_tipo_equipo serial  NOT NULL,
    nombre varchar(50) NOT NULL,
    descripcion varchar(500) NULL,
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


-- foreign keys
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
    REFERENCES control_calidad.grupos (id_grupo)
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
    REFERENCES control_calidad.grupos (id_grupo)
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE 
;

-- Reference:  Grupos_Muestras_Muestras (table: grupos_muestras)


ALTER TABLE control_calidad.grupos_muestras ADD CONSTRAINT Grupos_Muestras_Muestras 
    FOREIGN KEY (id_muestra)
    REFERENCES control_calidad.muestras (id_muestra)
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

    -- Equipos
    
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (504, 500, 'Equipos', null);

        -- Submenús
    
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (520, 504, 'Equipos', '/ControlCalidad/Equipo');
    INSERT INTO seguridad.entradas_menu_principal(id_menu_principal, id_padre, tag, redirect) VALUES (521, 504, 'Tipos de Equipo', '/ControlCalidad/TipoEquipo');


-- PERMISOS

INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (500, '[ControlCalidad]AdministrarTipoEquipo', 'Permite agregar, editar y eliminar un tipo de equipo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (510, '[ControlCalidad]AdministrarTipoReactivo', 'Permite agregar, editar y eliminar un tipo de reactivo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (520, '[ControlCalidad]AdministrarEquipo', 'Permite agregar, editar y eliminar un equipo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (521, '[ControlCalidad]AdministrarCertificadoEquipo', 'Permite agregar y eliminar un certificado de equipo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (530, '[ControlCalidad]AdministrarReactivo', 'Permite agregar, editar y eliminar un reactivo');
INSERT INTO seguridad.permisos(id_permiso, nombre, descripcion) VALUES (531, '[ControlCalidad]AdminCertificadoReactivo', 'Permite agregar y eliminar un certificado de reactivo');


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

