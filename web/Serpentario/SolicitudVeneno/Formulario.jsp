<%-- 
    Document   : Formulario
    Created on : Apr 1, 2015, 5:57:13 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="formSolicitud" class="form-horizontal" autocomplete="off" method="post" action="SolicitudVeneno">
  <div class="col-md-12">
    <input hidden="true" name="accion" id="accion" value=${accion}>
    <input hidden="true" name="id_solicitud" value="${solicitud.getId_solicitud()}">
    <input hidden="true" name="especie" value="${veneno.getEspecie().getId_especie()}"> 
    <input hidden="true" name="estado" value="${solicitud.getEstado()}">
    <input hidden="true" name="consumido" id="consumido" value="${restriccion.getCantidad_consumida()}">
    <input hidden="true" name="restriccion" id="restriccion" value="${restriccion.getCantidad_anual()}">
    
    <label for="seleccioninventario" class="control-label"> *Especie</label>
    <div class="form-group">
      <div class="col-sm-6">
        <div class="input-group">
            <input type="text" class="form-control" disabled="true" value="${veneno.getEspecie().getGenero_especie()} (${veneno.getCantidadAsMiligramos()} Miligramos)">
        </div>
      </div>
    </div>
  </div>
  <div class="col-md-6">
    <label for="cantidad" class="control-label">*Cantidad (mg)</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
            <input id="cantidad" placeholder="Número mayor a 1" type="number" step="any" min="1" class="form-control" name="cantidad" value="${solicitud.getCantidad()}" required 
                  oninput="validarSolicitud()"> 
        </div>
      </div>
    </div>
    <label for="cantidad" class="control-label">Proyecto</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
            <textarea rows="5" cols="50" maxlength="200" placeholder="Proyecto de la Solicitud" class="form-control" name="proyecto" value="${solicitud.getProyecto()}">${solicitud.getProyecto()}</textarea>
        </div>
      </div>
    </div> 
  </div>
  <div class="col-md-12">
    <p>
      Los campos marcados con * son requeridos.
    </p>  

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



</form>
      
      