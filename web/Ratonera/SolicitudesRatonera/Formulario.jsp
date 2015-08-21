<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 10:58:00 AM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formSolicitud" class="form-horizontal" autocomplete="off" method="post" action="SolicitudesRatonera">
  <div class="row">
    <input hidden="true" name="id_solicitud" value="${solicitud.getId_solicitud()}">
    <input hidden="true" name="fecha_solicitud" value="${solicitud.getFecha_solicitud_S()}">
    <input hidden="true" name="usuario_solicitante" value="${solicitud.getUsuario_solicitante().getID()}">
    <input hidden="true" name="accion" value="${accion}">

    <div class="col-md-6">
      <label for="numero_animales" class="control-label">*Número de Animales</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input id="numero_animales" placeholder="Número de animales" type="number" min="1" class="form-control" name="numero_animales" value="${solicitud.getNumero_animales()}"required 
                   oninvalid="setCustomValidity('Este campo es Requerido. Por favor introduzca un número válido')"
                   oninput="setCustomValidity('')"> 
          </div>
        </div>
      </div>  
      <label for="peso_requerido" class="control-label">*Peso Requerido</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <select id="peso_requerido" class="select2" name="peso_requerido" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <c:forEach items="${pesos}" var="peso">
                <c:choose>
                  <c:when test="${solicitud.getPeso_requerido() == peso}" >
                    <option value=${peso} selected> ${peso}</option>
                  </c:when>
                  <c:otherwise>
                    <option value=${peso}> ${peso}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>  
      <label for="numero_cajas" class="control-label">*Número de Cajas</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input id="numero_cajas" placeholder=" Número de Cajas" type="number" min="0"  class="form-control" name="numero_cajas" value="${solicitud.getNumero_cajas()}"required 
                   oninvalid="setCustomValidity('Este campo es Requerido. Por favor introduzca un número válido.')"
                   oninput="setCustomValidity('')"> 
          </div>
        </div>
      </div>
      <label for="observaciones" class="control-label">Observaciones</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" id="observaciones" name="observaciones" >${solicitud.getObservaciones()}</textarea>
          </div>
        </div>
      </div>
      <label for="usuario_utiliza" class="control-label">Usuario que utiliza</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <select id="usuario_utiliza" class="select2" name="usuario_utiliza" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">

              <c:forEach items="${usuarios}" var="us">
                <c:choose>
                  <c:when test="${us.getID() == solicitud.getUsuario_utiliza().getID()}" >
                    <option value=${us.getID()} selected> ${us.getNombreCompleto()}</option>
                  </c:when>
                  <c:otherwise>
                    <option value=${us.getID()}>${us.getNombreCompleto()}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
    </div>
    <div class="col-md-6">
      <label for="id_cepa" class="control-label">*Cepa</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <select id="id_cepa" class="select2" name="id_cepa" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <c:forEach items="${cepas}" var="cepa">
                <c:choose>
                  <c:when test="${solicitud.getCepa().getId_cepa() == cepa.getId_cepa()}" >
                    <option value=${cepa.getId_cepa()} selected> ${cepa.getNombre()}</option>
                  </c:when>
                  <c:otherwise>
                    <option value=${cepa.getId_cepa()}> ${cepa.getNombre()}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      <label for="sexo" class="control-label">*Sexo</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <select id="sexo" class="select2" name="sexo" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <c:forEach items="${sexos}" var="sexo">
                <c:choose>
                  <c:when test="${solicitud.getSexo() == sexo}" >
                    <option value=${sexo} selected> ${sexo}</option>
                  </c:when>
                  <c:otherwise>
                    <option value=${sexo}> ${sexo}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <label for="fecha_necesita" class="control-label">*Fecha en que necesita la entrega:</label>
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_necesita" value="${solicitud.getFecha_necesita_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_necesita" data-date-format="dd/mm/yyyy" required
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
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Solicitud</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

