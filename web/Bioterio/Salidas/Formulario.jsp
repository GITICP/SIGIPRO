<%-- 
    Document   : Formulario
    Created on : Abr 3, 2015, 10:58:00 AM
    Author     : Bpga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-salida" class="form-horizontal" autocomplete="off" method="post" action="Salidas">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_salida" value="${salida.getId_salida()}">
      <input hidden="true" name="accion" value="${accion}">
      <label for="identificacion" class="control-label">*Cantidad</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="number" min="1" id="numcantidadinforme" value="${salida.getCantidad()}" name="cantidad" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido. Por favor ingrese un número mayor a 1 ')"
                    onchange="setCustomValidity('')">
          </div>
        </div>
      </div>
      <label for="especie" class="control-label">*Especie</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <c:choose>
            <c:when test="${accion.equals('Agregar')}">
            <select id="select-especie" class="select2" style='background-color: #fff;' value=" " name="especie" required
                    oninvalid="setCustomValidity('Este campo es requerido')"
                    onchange="setCustomValidity('')">
              <c:choose>
                <c:when test="${accion == 'Editar'}">
                  <option value="True"  ${salida.isEspecie() ? 'selected' : ''}>Ratones</option>
                  <option value="False" ${salida.isEspecie() ? '' : 'selected'}>Conejos</option>
                </c:when>
                <c:when test="${accion == 'Agregar'}">
                  <option value="True"  ${especie ? 'selected' : ''}>Ratones</option>
                  <option value="False" ${especie ? '' : 'selected'}>Conejos</option>
                </c:when>
              </c:choose>
            </select>
            </c:when>
            <c:otherwise>
              <select id="select-especie" class="select2" style='background-color: #fff;' value=" " name="especie2" required disabled="true"
                    oninvalid="setCustomValidity('Este campo es requerido')"
                    onchange="setCustomValidity('')">
              <c:choose>
                <c:when test="${accion == 'Editar'}">
                  <option value="True"  ${salida.isEspecie() ? 'selected' : ''}>Ratones</option>
                  <option value="False" ${salida.isEspecie() ? '' : 'selected'}>Conejos</option>
                </c:when>
                <c:when test="${accion == 'Agregar'}">
                  <option value="True"  ${especie ? 'selected' : ''}>Ratones</option>
                  <option value="False" ${especie ? '' : 'selected'}>Conejos</option>
                </c:when>
              </c:choose>
            </select>
               <input hidden="true" name="especie" value="${salida.isEspecie()}">
            </c:otherwise>
            </c:choose>
            
          </div>
        </div>
      </div>
      <label for="razon" class="control-label">*Razón</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <select id="razon" class="select2" name="razon" style='background-color: #fff;' required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')" > 
              <option value='' ></option>
              <c:forEach items="${razones}" var="usuario">              
                    <c:choose>
                      <c:when test="${usuario == salida.getRazon()}">
                        <option value=${usuario} selected>${usuario}</option>
                      </c:when>
                      <c:otherwise>
                        <option value=${usuario}>${usuario}</option>
                      </c:otherwise>       
                    </c:choose>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      <label for="observaciones" class="control-label">Observaciones</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" name="observaciones">${salida.getObservaciones()}</textarea>
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
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Salida Extraordinaria</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

