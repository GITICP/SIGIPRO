DROP TRIGGER IF EXISTS Rest_Cant_Despachos ON produccion.despachos_inventario;
DROP TRIGGER IF EXISTS Rest_Cant_Salidas ON produccion.salidas_inventario;
DROP TRIGGER IF EXISTS Rest_Cant_Reservaciones ON produccion.reservaciones_inventario;
DROP TRIGGER IF EXISTS Sum_Cant_Despachos ON produccion.despachos_inventario;
DROP TRIGGER IF EXISTS Sum_Cant_Salidas ON produccion.salidas_inventario;
DROP TRIGGER IF EXISTS Sum_Cant_Reservaciones ON produccion.reservaciones_inventario;
DROP TRIGGER IF EXISTS Sum_Cant_Disponible_Despachos ON produccion.despachos_inventario;
DROP TRIGGER IF EXISTS Sum_Cant_Disponible_Salidas ON produccion.salidas_inventario;
DROP TRIGGER IF EXISTS Sum_Cant_Disponible_Reservaciones ON produccion.reservaciones_inventario;

DROP FUNCTION IF EXISTS Rest_Cant_Disponible();
DROP FUNCTION IF EXISTS Sum_Cant_Disponible();
DROP FUNCTION IF EXISTS Sum_total_salida();
DROP FUNCTION IF EXISTS Sum_total_reservacion();
DROP FUNCTION IF EXISTS Sum_total_despacho();

----Se separan las funciones en varias porque al momento de eliminar se debe usar rest_cant_disp pero no sum_total_x

CREATE FUNCTION Rest_Cant_Disponible() RETURNS "trigger" AS
	$BODY$
	BEGIN
	   UPDATE produccion.inventario_pt 
	   SET cantidad_disponible = cantidad_disponible - NEW.cantidad
	   WHERE id_inventario_pt = NEW.id_inventario_pt;
	   RETURN NEW;
	END;
	$BODY$
	LANGUAGE 'plpgsql';
	
CREATE FUNCTION Sum_Cant_Disponible() RETURNS "trigger" AS
	$BODY$
	BEGIN
	   UPDATE produccion.inventario_pt 
	   SET cantidad_disponible = cantidad_disponible + OLD.cantidad
	   WHERE id_inventario_pt = OLD.id_inventario_pt;
	   RETURN OLD;
	END;
	$BODY$
	LANGUAGE 'plpgsql';
	
CREATE FUNCTION Sum_total_despacho() RETURNS "trigger" AS 
	$BODY$
	BEGIN
	   UPDATE produccion.despacho 
	   SET total = total + NEW.cantidad
	   WHERE id_despacho = NEW.id_despacho;
	   RETURN NEW;
	END;
	$BODY$
	LANGUAGE 'plpgsql';

CREATE FUNCTION Sum_total_reservacion() RETURNS "trigger" AS 
	$BODY$
	BEGIN
	   UPDATE produccion.reservacion 
	   SET total = total + NEW.cantidad
	   WHERE id_reservacion = NEW.id_reservacion;
	   RETURN NEW;
	END;
	$BODY$
	LANGUAGE 'plpgsql';

CREATE FUNCTION Sum_total_salida() RETURNS "trigger" AS 
	$BODY$
	BEGIN
	   UPDATE produccion.salida_ext
	   SET total = total + NEW.cantidad
	   WHERE id_salida= NEW.id_salida;
	   RETURN NEW;
	END;
	$BODY$
	LANGUAGE 'plpgsql';

--TRIGGERS-------------------------------------------------

CREATE TRIGGER Rest_Cant_Despachos
    AFTER INSERT ON produccion.despachos_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Rest_Cant_Disponible();

CREATE TRIGGER Rest_Cant_Reservaciones
    AFTER INSERT ON produccion.reservaciones_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Rest_Cant_Disponible();

CREATE TRIGGER Rest_Cant_Salidas
    AFTER INSERT ON produccion.salidas_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Rest_Cant_Disponible();

CREATE TRIGGER Sum_Cant_Despachos
    AFTER INSERT ON produccion.despachos_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Sum_total_despacho();

CREATE TRIGGER Sum_Cant_Salidas
    AFTER INSERT ON produccion.salidas_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Sum_total_salida();

CREATE TRIGGER Sum_Cant_Reservaciones
    AFTER INSERT ON produccion.reservaciones_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Sum_total_reservacion();

CREATE TRIGGER Sum_Cant_Disponible_Despachos
    AFTER DELETE ON produccion.despachos_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Sum_Cant_Disponible();

CREATE TRIGGER Sum_Cant_Disponible_Salidas
    AFTER DELETE ON produccion.salidas_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Sum_Cant_Disponible();
    
CREATE TRIGGER Sum_Cant_Disponible_Reservaciones
    AFTER DELETE ON produccion.reservaciones_inventario
    FOR EACH ROW
    EXECUTE PROCEDURE Sum_Cant_Disponible();