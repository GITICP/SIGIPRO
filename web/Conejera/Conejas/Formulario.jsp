<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 10:58:00 AM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formConeja" class="form-horizontal" autocomplete="off" method="post" action="Conejas">
  <div class="row">
    <c:choose>
      <c:when test= "${accion.equals('Editar')}">
        <input hidden="true" name="id_caja" value="${coneja.getCaja().getId_caja()}">
      </c:when>
      <c:otherwise>
        <input hidden="true" name="id_caja" value="${id_caja}">
      </c:otherwise>
    </c:choose>
    <input hidden="true" name="id_coneja" value="${coneja.getId_coneja()}">
    <input hidden="true" name="accion" value="${accion}">
    <div class="col-md-6">
      <label for="fecha_nacimiento" class="control-label">*Fecha de Nacimiento</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_nacimiento" value="${coneja.getFecha_nacimiento_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_nacimiento" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="fecha_retiro" class="control-label">*Fecha de Retiro</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_retiro" value="${coneja.getFecha_retiro_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_retiro" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_ingreso" value="${coneja.getFecha_ingreso_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="fecha_cambio" class="control-label">*Fecha de Cambio</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_cambio" value="${coneja.getFecha_cambio_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_cambio" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="fecha_seleccion" class="control-label">*Fecha de Selección</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_seleccion" value="${coneja.getFecha_seleccion_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_seleccion" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="id_padre" class="control-label">*Identificación del Padre</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" id="id_padre" value="${coneja.getId_padre()}"  name="id_padre" required
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
            <input  type="text" id="id_madre" value="${coneja.getId_madre()}"  name="id_madre" required
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
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Coneja</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

