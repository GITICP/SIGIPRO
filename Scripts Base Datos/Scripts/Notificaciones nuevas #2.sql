---------------------Tipos de notificaciones nuevas------------------
--Ratonera
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (103, 'Retiro de pie de cría próximo', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (104, 'Apareamiento de cara próximo', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (105, 'Eliminación de macho próxima', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (106, 'Eliminación de hembra próxima', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (107, 'Selección para cara próxima', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);
INSERT INTO calendario.tipo_notificaciones(
            id_tipo_notificacion, descripcion, icono, id_permisos, id_usuarios_bloqueados)
    VALUES (108, 'Reposición de cara próxima', 'sigipro-mouse-1', ARRAY[203]::INT[], ARRAY[]::INT[]);