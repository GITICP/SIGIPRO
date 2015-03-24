<%-- 
    Document   : Formulario
    Created on : 24-mar-2015, 11:28:56
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" method="post" action="GrupoDeCaballos">
    <div class="col-md-6">
        <input hidden="true" name="id_frupo_de_caballo" value="${grupodecaballos.getId_grupo_caballo()}">
        <input hidden="true" name="accion" value="${accion}">
        <input hidden='true' id='imagenSubida2' value=''>
        <c:choose>
            <c:when test="${grupodecaballos.getId_grupo_caballo()!=0}">
                <label for="nombre" class="control-label">*Nombre del Grupo de Caballos</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" class="form-control" disabled='true' name="nombre" value="${grupodecaballos.getNombre()}"> 
                        </div>
                    </div>
                </div>
            </c:when>
            <c:otherwise>

            </c:otherwise>
        </c:choose>
    <label for="descripcion" class="control-label">Descripción</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <c:choose>
                    <c:when test="${grupodecaballos.getDescripcion()==null}">
                        <input type="text" placeholder="Detalles del grupo" class="form-control" name="descripcion"> 
                    </c:when>
                    <c:otherwise>
                        <input type="text" disabled='true' class="form-control" name="otras_sennas" value="${caballo.getOtras_sennas()}"> 
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
   
<div class="form-group">
    <div class="modal-footer">
        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
        <c:choose>
          <c:when test= "${accion.equals('Editar')}">
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
          </c:when>
          <c:otherwise>
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Grupo de Caballos</button>
          </c:otherwise>
        </c:choose>
    </div>
</div>

</div>
</form>