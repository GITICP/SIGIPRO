/**
 * Author:  Boga
 * Created: 23.09.2017
 * 
 * Archivo de cambios en la BD de acuerdo con los cambios finales de Control de Calidad previo al cierre del año fiscal 16-17.
 * 
 */

-- Agregado el campo de fecha de reporte de los análisis en un informe parcial/final
ALTER TABLE control_calidad.resultados_analisis_sangrias_prueba ADD fecha_reportado date NULL ;
ALTER TABLE control_calidad.resultados ADD fecha_reportado date NULL ;

-- Agregada la opción para identificar un análisis como "No realizarR
ALTER TABLE control_calidad.analisis_grupo_solicitud ADD observaciones_no_realizar varchar(500) NOT NULL DEFAULT 'Sin observaciones.' ;
ALTER TABLE control_calidad.analisis_grupo_solicitud ADD realizar bool NOT NULL DEFAULT true ;

-- Tabla para la implementación de tipos de patrones y controles
CREATE TABLE control_calidad.tipos_patronescontroles (
	id_tipo_patroncontrol serial NOT NULL,
	nombre varchar(50) NOT NULL,
	descripcion varchar(500) NOT NULL DEFAULT 'Sin descripción.',
	tipo varchar(10) NOT NULL,
	CONSTRAINT tipos_patronescontroles_pk PRIMARY KEY (id_patroncontrol)
)
WITH (
	OIDS=FALSE
) ;

-- Inserción de tipos de controles y patrones por defecto para los patrones y controles ya existentes
INSERT INTO control_calidad.tipos_patronescontroles
(id_tipo_patroncontrol, nombre, descripcion, tipo)
VALUES(nextval('control_calidad.tipos_patronescontroles_id_tipo_patroncontrol_seq'::regclass), 'Primario', 'Sin descripción.'::character varying, 'Patrón');
INSERT INTO control_calidad.tipos_patronescontroles
(id_tipo_patroncontrol, nombre, descripcion, tipo)
VALUES(nextval('control_calidad.tipos_patronescontroles_id_tipo_patroncontrol_seq'::regclass), 'Secundario', 'Sin descripción.'::character varying, 'Patrón');
INSERT INTO control_calidad.tipos_patronescontroles
(id_tipo_patroncontrol, nombre, descripcion, tipo)
VALUES(nextval('control_calidad.tipos_patronescontroles_id_tipo_patroncontrol_seq'::regclass), 'Control Interno', 'Sin descripción.'::character varying, 'Control');

-- Agregada la referencia a tipos de patrones y controles
ALTER TABLE control_calidad.patrones ADD id_tipo_patroncontrol int4 NULL ;
ALTER TABLE control_calidad.patrones ADD CONSTRAINT patrones_tipos_patronescontroles_fk FOREIGN KEY (id_tipo_patroncontrol) REFERENCES control_calidad.tipos_patronescontroles(id_tipo_patroncontrol) ;

-- Update para actualizar las referencias a los patrones y controles ya existentes.
update control_calidad.patrones
set id_tipo_patroncontrol = case 
                                when tipo = 'Primario' then 1 
                                when tipo = 'Secundario' then 2 
                                when tipo = 'Control Interno' then 3 
                                else null
                            end;

ALTER TABLE control_calidad.patrones ALTER COLUMN id_tipo_patroncontrol SET NOT NULL ;

-- Inserts menu y seguridad
INSERT INTO seguridad.permisos
(id_permiso, nombre, descripcion, id_seccion)
VALUES(580, 'AgregarTipoPatrónControl', 'Permite agregar un tipo de patrón o control.', 2);
INSERT INTO seguridad.permisos
(id_permiso, nombre, descripcion, id_seccion)
VALUES(581, 'EditarTipoPatrónControl', 'Permite editar un tipo de patrón o control.', 2);
INSERT INTO seguridad.permisos
(id_permiso, nombre, descripcion, id_seccion)
VALUES(582, 'EliminarTipoPatrónControl', 'Permite eliminar un tipo de patrón o control.', 2);
INSERT INTO seguridad.permisos
(id_permiso, nombre, descripcion, id_seccion)
VALUES(583, 'VerTipoPatrónControl', 'Permite ver un tipo de patrón o control.', 2);

INSERT INTO seguridad.entradas_menu_principal
(id_menu_principal, id_padre, tag, redirect, orden)
VALUES(571, 570, 'Patrones y Controles', '/ControlCalidad/Patron', 1);
INSERT INTO seguridad.entradas_menu_principal
(id_menu_principal, id_padre, tag, redirect, orden)
VALUES(572, 570, 'Tipos de Patrones y Controles', '/ControlCalidad/TipoPatronControl', 2);

UPDATE seguridad.entradas_menu_principal
SET id_padre=500, tag='Patrones y Controles', redirect=NULL, orden=4
WHERE id_menu_principal=570;

