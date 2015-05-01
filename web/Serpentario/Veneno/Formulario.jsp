<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 11:37:38 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Veneno">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_veneno" value="${veneno.getId_veneno()}">
      <input hidden="true" name="accion" value="${accion}">
            <label for="especie" class="control-label"> *Especie</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input hidden="true" name='especie' value="${veneno.getEspecie().getId_especie()}">
                        <select id="seleccionEspecie" class="select2" name="especie" disabled="true"
                                    style='background-color: #fff;'>
                                <option value=''></option>
                                <option value=${veneno.getEspecie().getId_especie()} selected> ${veneno.getEspecie().getGenero_especie()}</option>
                                </select>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${veneno.isRestriccion()}">
                                <c:set var="checkedRestriccion" value="true" />
                                <input id="restriccion" type="checkbox" name="restriccion" value="true" style="width:20px; height:20px;" checked="true" ${checkedRestriccion}><span>  ¿Es restringido?</span>
                            </c:when>
                            <c:otherwise>
                                <c:set var="checkedRestriccion" value="false" />
                                <input id="restriccion" type="checkbox" name="restriccion" style="width:20px; height:20px;" value="false" ${checkedRestriccion}><span>  ¿Es restringido?</span>
                            </c:otherwise>
                        </c:choose>
                        
                    </div>
                </div>
            </div>
    </div>    
    <div class="col-md-6">
        <label for="cantidad_maxima" class="control-label">*Cantidad Máxima (mg)</label>
        <div class="form-group">
          <div class="col-sm-12">
            <div class="input-group">
                <c:choose>
                    <c:when test="${veneno.isRestriccion()}">
                        <input id="cantidad_maxima" type="number" step="any" class="form-control" name="cantidad_maxima" value="${veneno.getCantidad_maxima()}" required
                            oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                            oninput="setCustomValidity('')"> 
                    </c:when>
                    <c:otherwise>
                        <input id="cantidad_maxima" type="number" step="any" class="form-control" name="cantidad_maxima" value="0" disabled="true" required
                            oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                            oninput="setCustomValidity('')"> 
                    </c:otherwise>
                </c:choose>
                
            </div>
          </div>
        </div>
    </div>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Venno</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>