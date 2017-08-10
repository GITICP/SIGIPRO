SELECT
	s.nombre_seccion AS "Nombre Sección",
	SUM(sc.numero_animales)
FROM 
	bioterio.solicitudes_conejera sc
	INNER JOIN seguridad.usuarios u ON sc.usuario_utiliza = u.id_usuario
	INNER JOIN seguridad.secciones s ON s.id_seccion = u.id_seccion
WHERE
	sc.estado = 'Entregada'
	AND fecha_necesita >= ? /*(fecha inicial)*/ AND fecha_necesita <= ? /*(Fecha final)*/
GROUP BY
	s.nombre_seccion;


SELECT
	u.nombre_completo AS "Nombre Usuario",
	SUM(sc.numero_animales)
FROM 
	bioterio.solicitudes_conejera sc
	INNER JOIN seguridad.usuarios u ON sc.usuario_utiliza = u.id_usuario
WHERE
	sc.estado = 'Entregada'
	AND fecha_necesita >= ? /*(fecha inicial)*/ AND fecha_necesita <= ? /*(Fecha final)*/
GROUP BY
	u.nombre_completo;

SELECT
	s.nombre_seccion AS "Nombre Sección",
	SUM(sc.numero_animales)
FROM 
	bioterio.solicitudes_ratonera sc
	INNER JOIN seguridad.usuarios u ON sc.usuario_utiliza = u.id_usuario
	INNER JOIN seguridad.secciones s ON s.id_seccion = u.id_seccion
WHERE
	sc.estado = 'Entregada'
	AND fecha_necesita >= ? /*(fecha inicial)*/ AND fecha_necesita <= ? /*(Fecha final)*/
GROUP BY
	s.nombre_seccion;


SELECT
	u.nombre_completo AS "Nombre Usuario",
	SUM(sc.numero_animales)
FROM 
	bioterio.solicitudes_ratonera sc
	INNER JOIN seguridad.usuarios u ON sc.usuario_utiliza = u.id_usuario
WHERE
	sc.estado = 'Entregada'
	AND fecha_necesita >= ? /*(fecha inicial)*/ AND fecha_necesita <= ? /*(Fecha final)*/
GROUP BY
	u.nombre_completo;


