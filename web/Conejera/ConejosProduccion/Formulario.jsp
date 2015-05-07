<%-- 
    Document   : Formulario
    Created on : Abr 3, 2015, 10:58:00 AM
    Author     : Bpga
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-macho" class="form-horizontal" autocomplete="off" method="post" action="ConejosProduccion">
  <div class="row">
    <input hidden="true" name="id_produccion" value="${conejo.getId_produccion()}">
    <input hidden="true" name="accion" value="${accion}">
    <div class="col-md-6">
      <label for="identificador" class="control-label">*Identificador</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" id="identificador" value="${conejo.getIdentificador()}"  name="identificador" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="cantidad" class="control-label">*Cantidad</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="number"  id="cantidad" value="${conejo.getCantidad()}" name="cantidad" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="detalle_procedencia" class="control-label">*Detalle de Procedencia</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Detalle" class="form-control" id="detalle_procedencia" name="detalle_procedencia" >${conejo.getDetalle_procedencia()}</textarea>   
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
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Grupo</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

