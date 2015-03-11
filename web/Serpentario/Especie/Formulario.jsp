<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Especie">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_especie" value="${especie.getId_especie()}">
      <input hidden="true" name="accion" value="${accion}">
      
      <label for="genero" class="control-label">* Nombre del Género</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input type="text" maxlength="45" placeholder="Nombre de Género" class="form-control" name="genero" value="${especie.getGenero()}"
                   required
                   oninvalid="setCustomValidity('Este campo es requerido')"
                   oninput="setCustomValidity('')" > 
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="especie" class="control-label">* Nombre de la Especie</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input type="text" maxlength="45" placeholder="Nombre de Especie" class="form-control" name="especie" value="${especie.getEspecie()}"
                   required
                   oninvalid="setCustomValidity('Este campo es requerido')"
                   oninput="setCustomValidity('')" > 
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Especie</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>