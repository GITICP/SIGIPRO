--NO SE QUE PASA CON LOS DATOS QUE YA SE METIERON POR MEDIO DEL SISTEMA
--SOLO ESTAN LAS TABLAS DE ACTIVO FIJO, REACTIVOS, UBICACIONES, CATALOGO INTERNO Y EXTERNO


CREATE OR REPLACE FUNCTION bitacora.insertBitacoraAF() RETURNS BOOLEAN AS $$
DECLARE
    fecha_accion TIMESTAMP := now();
    nombre_usuario CHARACTER VARYING(45) := 'Carga de Datos Inicial';
    ip CHARACTER VARYING(18) := '127.0.0.1';
    accion CHARACTER VARYING(45):= 'AGREGAR';
    tabla CHARACTER VARYING(45):= 'BODEGA.ACTIVOS_FIJOS';
    vid_objeto int;
    vplaca varchar;
    vequipo varchar;
    vnumero_serie varchar;
    vmarca varchar;
    vfecha_movimiento date;
    vid_seccion int;
    vid_ubicacion int;
    vfecha_registro date;
    vmodelo varchar;
    vresponsable varchar;
    vestado varchar;
    v_cursor cursor for
    SELECT id_activo_fijo AS id_objeto,
			    placa,
			    equipo,
			    numero_serie,
			    marca,
			    fecha_movimiento,
			    id_seccion,
			    id_ubicacion,
			    fecha_registro,
			    modelo,
			    responsable,
			    estado
			    FROM bodega.activos_fijos;
BEGIN
	open v_cursor;
	LOOP
		FETCH v_cursor INTO vid_objeto,
			    vplaca,
			    vequipo,
			    vnumero_serie,
			    vmarca,
			    vfecha_movimiento,
			    vid_seccion,
			    vid_ubicacion,
			    vfecha_registro,
			    vmodelo,
			    vresponsable,
			    vestado;
		exit when not found;
	
		INSERT INTO BITACORA.BITACORA (fecha_accion,nombre_usuario,ip,accion,tabla,estado)
		VALUES (fecha_accion,nombre_usuario,ip,accion,tabla,
		json_build_object('id_objeto',vid_objeto,'placa',vplaca,'equipo',vequipo,'numero_serie',vnumero_serie,'marca',vmarca,'fecha_movimiento',vfecha_movimiento,'id_seccion',vid_seccion,'id_ubicacion',
		vid_ubicacion,'fecha_registro',vfecha_registro,'modelo',vmodelo,'responsable',vresponsable,'estado',vestado)); 
	end loop;
	close v_cursor;
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION bitacora.insertBitacoraCI() RETURNS BOOLEAN AS $$
DECLARE
    fecha_accion TIMESTAMP := now();
    nombre_usuario CHARACTER VARYING(45) := 'Carga de Datos Inicial';
    ip CHARACTER VARYING(18) := '127.0.0.1';
    accion CHARACTER VARYING(45):= 'AGREGAR';
    tabla CHARACTER VARYING(45):= 'BODEGA.CATALOGO_INTERNO';
    vid_objeto int;
    vnombre varchar;
    vcodigo_icp varchar;
    vstock_minimo int;
    vstock_maximo int;
    vpresentacion varchar;
    vdescripcion varchar;
    vcuarentena boolean;
    v_cursor cursor for
    SELECT id_producto AS id_objeto,
			    nombre,
			    codigo_icp,
			    stock_minimo, 
			    stock_maximo, 
			    presentacion, 
			    descripcion, 
			    cuarentena
			    FROM bodega.catalogo_interno;
BEGIN
	open v_cursor;
	LOOP
		FETCH v_cursor INTO vid_objeto,
			    vnombre,
			    vcodigo_icp,
			    vstock_minimo, 
			    vstock_maximo, 
			    vpresentacion, 
			    vdescripcion, 
			    vcuarentena;
		exit when not found;
	
		INSERT INTO BITACORA.BITACORA (fecha_accion,nombre_usuario,ip,accion,tabla,estado)
		VALUES (fecha_accion,nombre_usuario,ip,accion,tabla,
		json_build_object('id_objeto',vid_objeto,'nombre',vnombre,'codigo_icp',vcodigo_icp,'stock_minimo',vstock_minimo,'stock_maximo',vstock_maximo
		,'presentacion',vpresentacion,'descripcion',vdescripcion,'cuarentena',vcuarentena)); 
	end loop;
	close v_cursor;
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION bitacora.insertBitacoraCE() RETURNS BOOLEAN AS $$
DECLARE
    fecha_accion TIMESTAMP := now();
    nombre_usuario CHARACTER VARYING(45) := 'Carga de Datos Inicial';
    ip CHARACTER VARYING(18) := '127.0.0.1';
    accion CHARACTER VARYING(45):= 'AGREGAR';
    tabla CHARACTER VARYING(45):= 'BODEGA.CATALOGO_EXTERNO';
    vid_objeto int;
    vproducto varchar;
    vcodigo_externo varchar;
    vmarca varchar;
    vid_proveedor int;
    v_cursor cursor for
    SELECT id_producto_ext AS id_objeto,
			    producto,
			    codigo_externo,
			    marca,
			    id_proveedor
			    FROM bodega.catalogo_externo;
BEGIN
	open v_cursor;
	LOOP
		FETCH v_cursor INTO vid_objeto,
			    vproducto,
			    vcodigo_externo,
			    vmarca,
			    vid_proveedor;
		exit when not found;
	
		INSERT INTO BITACORA.BITACORA (fecha_accion,nombre_usuario,ip,accion,tabla,estado)
		VALUES (fecha_accion,nombre_usuario,ip,accion,tabla,
		json_build_object('id_objeto',vid_objeto,'producto',vproducto,'codigo_externo',vcodigo_externo,'marca',vmarca,'id_proveedor',vid_proveedor
		)); 
	end loop;
	close v_cursor;
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION bitacora.insertBitacoraU() RETURNS BOOLEAN AS $$
DECLARE
    fecha_accion TIMESTAMP := now();
    nombre_usuario CHARACTER VARYING(45) := 'Carga de Datos Inicial';
    ip CHARACTER VARYING(18) := '127.0.0.1';
    accion CHARACTER VARYING(45):= 'AGREGAR';
    tabla CHARACTER VARYING(45):= 'BODEGA.UBICACIONES';
    vid_objeto int;
    vnombre varchar;
    vdescripcion varchar;
    v_cursor cursor for
    SELECT id_ubicacion AS id_objeto,
			    nombre,
			    descripcion
			    FROM bodega.ubicaciones;
BEGIN
	open v_cursor;
	LOOP
		FETCH v_cursor INTO vid_objeto,
			    vnombre,
			    vdescripcion;
		exit when not found;
	
		INSERT INTO BITACORA.BITACORA (fecha_accion,nombre_usuario,ip,accion,tabla,estado)
		VALUES (fecha_accion,nombre_usuario,ip,accion,tabla,
		json_build_object('id_objeto',vid_objeto,'nombre',vnombre,'descripcion',vdescripcion)); 
	end loop;
	close v_cursor;
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION bitacora.insertBitacoraCIE() RETURNS BOOLEAN AS $$
DECLARE
    fecha_accion TIMESTAMP := now();
    nombre_usuario CHARACTER VARYING(45) := 'Carga de Datos Inicial';
    ip CHARACTER VARYING(18) := '127.0.0.1';
    accion CHARACTER VARYING(45):= 'AGREGAR';
    tabla CHARACTER VARYING(45):= 'BODEGA.CATALOGOS_INTERNOS_EXTERNOS';
    vid_objeto int;
    vid_producto_ext varchar;
    v_cursor cursor for
    SELECT id_producto AS id_objeto,
			    id_producto_ext
			    FROM bodega.catalogos_internos_externos;
BEGIN
	open v_cursor;
	LOOP
		FETCH v_cursor INTO vid_objeto,
			    vid_producto_ext;
		exit when not found;
	
		INSERT INTO BITACORA.BITACORA (fecha_accion,nombre_usuario,ip,accion,tabla,estado)
		VALUES (fecha_accion,nombre_usuario,ip,accion,tabla,
		json_build_object('id_objeto',vid_objeto,'id_producto_ext',vid_producto_ext)); 
	end loop;
	close v_cursor;
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION bitacora.insertBitacoraR() RETURNS BOOLEAN AS $$
DECLARE
    fecha_accion TIMESTAMP := now();
    nombre_usuario CHARACTER VARYING(45) := 'Carga de Datos Inicial';
    ip CHARACTER VARYING(18) := '127.0.0.1';
    accion CHARACTER VARYING(45):= 'AGREGAR';
    tabla CHARACTER VARYING(45):= 'BODEGA.REACTIVOS';
    vid_objeto int;
    vid_producto int;
    vnumero_cas varchar;
    vformula_quimica varchar;
    vfamilia varchar;
    vcantidad_botella_bodega varchar;
    vcantidad_botella_lab varchar;
    vvolumen_bodega varchar;
    vvolumen_lab varchar;
    v_cursor cursor for
    SELECT id_reactivo AS id_objeto,
			    id_producto, 
			    numero_cas, 
			    formula_quimica, 
			    familia
			    FROM bodega.reactivos;
BEGIN
	open v_cursor;
	LOOP
		FETCH v_cursor INTO vid_objeto,
			    vid_producto, 
			    vnumero_cas, 
			    vformula_quimica, 
			    vfamilia;
		exit when not found;
	
		INSERT INTO BITACORA.BITACORA (fecha_accion,nombre_usuario,ip,accion,tabla,estado)
		VALUES (fecha_accion,nombre_usuario,ip,accion,tabla,
		json_build_object('id_objeto',vid_objeto,'id_producto',vid_producto,'numero_cas',vnumero_cas,'formula_quimica',vformula_quimica,'familia',vfamilia)); 
	end loop;
	close v_cursor;
	RETURN TRUE;
END;
$$ LANGUAGE plpgsql;

select * from bitacora.insertbitacorau();

select * from bitacora.insertbitacoraaf();

select * from bitacora.insertbitacoraci();

select * from bitacora.insertbitacorace();

select * from bitacora.insertbitacoracie();

select * from bitacora.insertbitacorar();
