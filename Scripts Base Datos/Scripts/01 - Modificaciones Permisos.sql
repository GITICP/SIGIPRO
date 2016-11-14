/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Boga
 * Created: 05.11.2016
 */

UPDATE seguridad.permisos
    SET nombre = 'EditarSolicitudCC'
        WHERE id_permiso = 553;

UPDATE seguridad.permisos
    SET nombre = 'VerSolicitudCC'
        WHERE id_permiso = 555;

UPDATE seguridad.permisos
    SET nombre = 'AnularSolicitudCC'
        WHERE id_permiso = 552;

UPDATE seguridad.permisos
    SET nombre = 'RecibirSolicitudCC'
        WHERE id_permiso = 551;

UPDATE seguridad.permisos
    SET nombre = 'VerTodasSolicitudesCC'
        WHERE id_permiso = 554;

ALTER TABLE seguridad.permisos
  ADD COLUMN id_seccion integer NOT NULL DEFAULT 0;
ALTER TABLE seguridad.permisos
  ADD CONSTRAINT fk_id_seccion FOREIGN KEY (id_seccion) REFERENCES seguridad.secciones (id_seccion) ON UPDATE NO ACTION ON DELETE NO ACTION;

UPDATE seguridad.permisos
    SET id_seccion = 7, nombre = substring(nombre from 12)
        WHERE 
            nombre like '[Seguridad]%';

UPDATE seguridad.permisos
    SET id_seccion = 7, nombre = substring(nombre from 16)
        WHERE 
            nombre like '[PermisoGlobal]%';

UPDATE seguridad.permisos
    SET id_seccion = 7, nombre = substring(nombre from 11)
        WHERE 
            nombre like '[Reportes]%';

UPDATE seguridad.permisos
    SET id_seccion = 8, nombre = substring(nombre from 10)
        WHERE 
            nombre like '[Bodegas]%';

UPDATE seguridad.permisos
    SET id_seccion = 8, nombre = substring(nombre from 15)
        WHERE 
            nombre like '[ActivosFijos]%';

UPDATE seguridad.permisos
    SET id_seccion = 6, nombre = substring(nombre from 14)
        WHERE nombre like '[Caballeriza]%';

UPDATE seguridad.permisos
    SET id_seccion = 4, nombre = substring(nombre from 11)
        WHERE nombre like '[Bioterio]%';

UPDATE seguridad.permisos
    SET id_seccion = 5, nombre = substring(nombre from 14)
        WHERE nombre like '[Serpentario]%';

UPDATE seguridad.permisos
    SET id_seccion = 2, nombre = substring(nombre from 17)
        WHERE nombre like '[ControlCalidad]%';

UPDATE seguridad.permisos
    SET id_seccion = 1, nombre = substring(nombre from 13)
        WHERE nombre like '[produccion]%';

UPDATE seguridad.permisos
    SET id_seccion = 3, nombre = substring(nombre from 9)
        WHERE nombre like '[Ventas]%';