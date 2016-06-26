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
                        <input type="text" maxlength="100" placeholder="Nombre del Reporte" class="form-control" name="nombre" value="${reporte.getNombre()}"
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
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${reporte.getDescripcion()}</textarea>
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

SELECT *
FROM control_calidad.usuarios u 
    INNER JOIN control_calidad.otra_tabla_inventada 
        ON i.id = 5 AND u.id = 'Algo por acá';
                            
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
                    <button id="probar-reporte" class="btn btn-primary"><i class="fa fa-check-circle"></i> Probar Reporte</button>
                    <button type="submit" class="btn btn-primary" disabled><i class="fa fa-check-circle"></i> ${accion} Reporte</button>
                </c:otherwise>
            </c:choose>   
        </div>
    </div>


</form>

