/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Boga
 * Created: 05.11.2016
 */

ALTER TABLE seguridad.permisos
  ADD COLUMN id_seccion integer NOT NULL DEFAULT 0;
ALTER TABLE seguridad.permisos
  ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones (id_seccion) ON UPDATE NO ACTION ON DELETE NO ACTION;

UPDATE seguridad.permisos
    SET id_seccion = 7
        WHERE 
            nombre like '[Seguridad]%'
        OR  nombre like '[PermisoGlobal]%';

UPDATE seguridad.permisos
    SET id_seccion = 8
        WHERE 
            nombre like '[Bodegas]%'
        OR  nombre like '[ActivosFijos]%';

UPDATE seguridad.permisos
    SET id_seccion = 6
        WHERE nombre like '[Caballeriza]%';

UPDATE seguridad.permisos
    SET id_seccion = 4
        WHERE nombre like '[Bioterio]%';

UPDATE seguridad.permisos
    SET id_seccion = 5
        WHERE nombre like '[Serpentario]%';

UPDATE seguridad.permisos
    SET id_seccion = 2
        WHERE nombre like '[ControlCalidad]%';

UPDATE seguridad.permisos
    SET id_seccion = 1
        WHERE nombre like '[produccion]%';

UPDATE seguridad.permisos
    SET id_seccion = 3
        WHERE nombre like '[Ventas]%';