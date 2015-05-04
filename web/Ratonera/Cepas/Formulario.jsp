<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 10:58:00 AM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formCepa" class="form-horizontal" autocomplete="off" method="post" action="Cepas">
  <div class="row">
    <input hidden="true" name="id_cepa" value="${cepa.getId_cepa()}">
    <input hidden="true" name="accion" value="${accion}">
    <div class="col-md-6">
      <label for="nombre" class="control-label">*Nombre de la Cepa</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" class="form-control" id="nombre" value="${cepa.getNombre()}" name="nombre" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="descripcion" class="control-label">Descripción</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Descripcion" class="form-control" id="descripcion" name="descripcion" >${cepa.getDescripcion()}</textarea>   
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="col-md-12">
    <p>
      Los campos marcados con * son requeridos.
    </p>  
    <div class="row">
      <div class="form-group">
        <div class="modal-footer">
          <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
          <c:choose>
            <c:when test= "${accion.equals('Editar')}">
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </c:when>
            <c:otherwise>
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Cepa</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

