<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 4:42:37 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="TipoEquipo">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_tipo_equipo" value="${tipoequipo.getId_tipo_equipo()}">
      <input hidden="true" name="accion" value="${accion}">
      
      <label for="nombre" class="control-label">* Nombre</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input type="text" maxlength="45" placeholder="Nombre del Tipo de Equipo" class="form-control" name="nombre" value="${tipoequipo.getNombre()}"
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
            <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${tipoequipo.getDescripcion()}</textarea>
          </div>
        </div>
      </div>
    </div>
    <!-- Esta parte es la de los permisos de un rol -->
    <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
  </div>


  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Tipo de Equipo</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
