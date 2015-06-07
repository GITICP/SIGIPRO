-- Modificación de ON DELETE en referencias

    -- Activos Fijos
ALTER TABLE bodega.activos_fijos DROP CONSTRAINT fk_id_seccion,
ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones(id_seccion);
    
ALTER TABLE ONLY bodega.activos_fijos DROP CONSTRAINT fk_id_ubicacion,
ADD CONSTRAINT fk_id_ubicacion FOREIGN KEY (id_ubicacion) REFERENCES bodega.ubicaciones(id_ubicacion);

    -- Secciones
ALTER TABLE ONLY seguridad.usuarios DROP CONSTRAINT fk_id_seccion,
ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones(id_seccion);