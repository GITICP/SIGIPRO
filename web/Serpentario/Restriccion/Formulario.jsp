<%-- 
    Document   : Formulario
    Created on : May 21, 2015, 8:21:44 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Restriccion">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_restriccion" value="${restriccion.getId_restriccion()}">
      <c:choose>
          <c:when test="${restriccion.getVeneno()==null}">
              <input hidden="true" name="id_veneno" value ="${veneno.getId_veneno()}">
          </c:when>
          <c:otherwise>
              <input hidden="true" name="id_veneno" value ="${restriccion.getVeneno().getId_veneno()}">
          </c:otherwise>
      </c:choose>
      <input hidden="true" name="accion" value="${accion}">
      
      <c:choose>
          <c:when test="${restriccion.getUsuario()==null}">
              <label for="usuarios" class="control-label">* Usuarios</label>
                <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionUsuarios" class="select2" name="usuarios_restriccion" multiple="multiple"
                                    style='background-color: #fff;' required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    onchange="setCustomValidity('')">
                                <option value=''></option>
                                <c:forEach items="${usuarios}" var="usuario">
                                    <option value=${usuario.getId_usuario()}>${usuario.getNombreCompleto()}</option>
                                </c:forEach>
                        </select>
                    </div>
                </div>
                </div>
          </c:when>
          <c:otherwise>
                <label for="usuarios" class="control-label">Usuario</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" class="form-control" name="usuario" value="${restriccion.getUsuario().getNombre_completo()}" disabled="true"> 
                    </div>
                  </div>
                </div>
                </c:otherwise>
      </c:choose>
      
     <label for="veneno" class="control-label">*Veneno</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${restriccion.getVeneno()!=null}">
                            <input type="text" class="form-control" name="usuario" value="${restriccion.getVeneno().getEspecie().getGenero_especie()}" disabled="true">
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="usuario" value="${veneno.getEspecie().getGenero_especie()}" disabled="true">
                        </c:otherwise>
                    </c:choose>
                    
                </div>
            </div>
        </div>                   
    </div>
    <div class="col-md-6">
      <label for="cantidad" class="control-label">*Cantidad Restringida Anual (mg) - Esta cantidad corresponde a la restricción del Año Fiscal actual.</label>
        <div class="form-group">
          <div class="col-sm-12">
            <div class="input-group">
                <input id="cantidad_anual" type="number" step="any" min="1" class="form-control" name="cantidad_anual" value="${restriccion.getCantidad_anual()}" required
                            oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                            oninput="setCustomValidity('')">
            </div>
          </div>
        </div>
    </div>
    <!-- Esta parte es la de los permisos de un rol -->
    <div class="col-sm-12">
    <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </div>
  </div>


  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Especie</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
