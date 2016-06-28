<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 4:42:37 PM
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Reportes">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_tipo_equipo" value="${reporte.getId_reporte()}">
            <input hidden="true" name="accion" value="${accion}">

            <label for="nombre" class="control-label">* Nombre</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="input-nombre" type="text" maxlength="100" placeholder="Nombre del Reporte" class="form-control" name="nombre" value="${reporte.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="descripcion" class="control-label">Descripción</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea id="text-descripcion" rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${reporte.getDescripcion()}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="widget widget-table">
        <div class="widget-header">
            <h3><i class="fa fa-th-list"></i> Código de Consulta</h3>
            <div class="btn-group widget-header-toolbar">

            </div>
        </div>
        <div class="widget-content">
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea id="text-codigo" rows="4" cols="50" name="codigo" >

SELECT s_s.nombre_seccion, b_ci.nombre, SUM(b_s.cantidad)
FROM bodega.solicitudes b_s 	
	INNER JOIN bodega.inventarios b_i ON b_i.id_inventario = b_s.id_inventario
	INNER JOIN bodega.catalogo_interno b_ci ON b_ci.id_producto = b_i.id_producto 	
	INNER JOIN seguridad.usuarios s_u ON s_u.id_usuario = b_s.id_usuario AND s_u.id_seccion IN()
	INNER JOIN seguridad.secciones s_s ON s_s.id_seccion = s_u.id_seccion
WHERE b_s.estado = 'Entregada' 
	AND b_s.fecha_solicitud >= 
	AND b_s.fecha_solicitud <= 
GROUP BY s_s.nombre_seccion, b_ci.id_producto
ORDER BY s_s.nombre_seccion;
                            
                            ${reporte.getConsulta()}
                        
                        </textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="widget widget-table">
        <div class="widget-header">
            <h3><i class="fa fa-th-list"></i> Parámetros</h3>
            <div class="btn-group widget-header-toolbar">

            </div>
        </div>
        <div class="widget-content" id="componente-parametros">
            
        </div>
    </div>

    <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>

    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button id="guardar-reporte" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Reporte</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

</form>

