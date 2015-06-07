<%-- 
    Document   : Formulario
    Created on : Abr 3, 2015, 10:58:00 AM
    Author     : Bpga
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-pie" class="form-horizontal" autocomplete="off" method="post" action="Pies">
  <div class="row">
    <input hidden="true" name="id_pie" value="${pie.getId_pie()}">
    <input hidden="true" name="accion" value="${accion}">
    <div class="col-md-6">
      <label for="codigo" class="control-label">*Código</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" maxlenght="20" id="codigo" value="${pie.getCodigo()}"  name="codigo" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_ingreso" value="${pie.getFecha_ingreso_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="fecha_retiro" class="control-label">*Fecha de Retiro</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_retiro" value="${pie.getFecha_retiro_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_retiro" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="fuente" class="control-label">*Fuente</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input  type="text" id="fuente" maxlength="100" value="${pie.getFuente()}"  name="fuente" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-12">                           
      <p id='mensajeFechas1' style='color:red;'><p>
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
              <button type="button" class="btn btn-primary" onclick="confirmar()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </c:when>
            <c:otherwise>
              <button type="button" class="btn btn-primary" onclick="confirmar()"><i class="fa fa-check-circle"></i> ${accion} Pie</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

