<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 10:58:00 AM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formCruce" class="form-horizontal" autocomplete="off" method="post" action="Cruces">
  <div class="row">
    <c:choose>
      <c:when test= "${accion.equals('Editar')}">
        <input hidden="true" name="id_coneja" value="${cruce.getConeja().getId_coneja()}">
      </c:when>
      <c:otherwise>
        <input hidden="true" name="id_coneja" value="${coneja.getId_coneja()}">
      </c:otherwise>
    </c:choose>
    <input hidden="true" name="id_cruce" value="${cruce.getId_cruce()}">
    <input hidden="true" name="accion" value="${accion}">
    <div class="col-md-6">
      <label for="id_macho" class="control-label">*Macho</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <select id="id_macho" class="select2" name="id_macho" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <c:forEach items="${machos}" var="macho">
                <c:choose>
                  <c:when test="${cruce.getMacho().getId_macho() == macho.getId_macho()}" >
                    <option value=${macho.getId_macho()} selected> ${macho.getIdentificacion()}</option>
                  </c:when>
                  <c:otherwise>
                    <option value=${macho.getId_macho()}> ${macho.getIdentificacion()}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="fecha_cruce" class="control-label">*Fecha del Cruce</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_cruce" value="${cruce.getFecha_cruce_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_cruce" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-12">
      <label for="observaciones" class="control-label">Observaciones</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" id="observaciones" name="observaciones" >${cruce.getObservaciones()}</textarea>
          </div>
        </div>
      </div>        
    </div>
    <div class="col-md-6">
      <label for="fecha_parto" class="control-label">Fecha del Parto</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_parto" value="${cruce.getFecha_parto_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_parto" data-date-format="dd/mm/yyyy" 
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="cantidad_paridos" class="control-label">Cantidad de Paridos</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="number" id="cantidad_paridos" value="${cruce.getCantidad_paridos()}"  name="cantidad_paridos" 
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
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Cruce</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

