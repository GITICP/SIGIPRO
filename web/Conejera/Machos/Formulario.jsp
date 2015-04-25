<%-- 
    Document   : Formulario
    Created on : Abr 3, 2015, 10:58:00 AM
    Author     : Bpga
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-macho" class="form-horizontal" autocomplete="off" method="post" action="Machos">
  <div class="row">
    <input hidden="true" name="id_macho" value="${conejo.getId_macho()}">
    <input hidden="true" name="accion" value="${accion}">
    <div class="col-md-6">
      <label for="identificacion" class="control-label">*Identificación</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" id="identificacion" value="${conejo.getIdentificacion()}"  name="identificacion" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_ingreso" value="${conejo.getFecha_ingreso_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="fecha_retiro" class="control-label">*Fecha de Retiro</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_retiro" value="${conejo.getFecha_retiro_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_retiro" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="descripcion" class="control-label">Descripción</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${conejo.getDescripcion()}</textarea>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="id_padre" class="control-label">*Identificación del Padre</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" id="id_padre" value="${macho.getId_padre()}"  name="id_padre" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="id_madre" class="control-label">*Identificación de la Madre</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" id="id_madre" value="${macho.getId_madre()}"  name="id_madre" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">  
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
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Macho</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

