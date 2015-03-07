<%-- 
    Document   : Formulario
    Created on : Feb 19, 2015, 8:03:02 PM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="boolEditar" value="false" />
<c:if test="${accion.equals('Editar')}">
  <c:set var="boolEditar" value="true" />
</c:if>

<form id="formSolicitud" class="form-horizontal" autocomplete="off" method="post" action="Solicitudes">
  <div class="col-md-12">
    <input hidden="true" name="accionindex" id="accionindex" value="">
    <input hidden="true" name="id_solicitud" value="${solicitud.getId_solicitud()}">
    <input hidden="true" name="id_usuario" value="${solicitud.getId_usuario()}">
    <input hidden="true" name="fecha_solicitud" value="${solicitud.getFecha_solicitudAsDate()}">
    <input hidden="true" name="estado" value="${solicitud.getEstado()}">
    <label for="seleccioninventario" class="control-label"> *Producto</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <c:choose>
            <c:when test="${boolEditar}">
              <select id="seleccioninventario" class="select2" style='background-color: #fff;' name="seleccioninventario" required
                      oninvalid="setCustomValidity('Este campo es requerido')"
                      oninput="setCustomValidity('')" disabled>
                <c:forEach items="${inventarios}" var="inventario">
                  <c:choose>
                    <c:when test="${inventario.getId_inventario() == solicitud.getId_inventario()}">
                      <option data-stock="${inventario.getStock_actual()}" value=${inventario.getId_inventario()} selected> Producto: ${inventario.getProducto().getNombre()} (${inventario.getProducto().getCodigo_icp()}) - Sección: ${inventario.getSeccion().getNombre_seccion()} - Presentación: ${inventario.getProducto().getPresentacion()} </option>
                    </c:when>
                    <c:otherwise>
                      <option data-stock="${inventario.getStock_actual()}" value=${inventario.getId_inventario()}> Producto: ${inventario.getProducto().getNombre()} (${inventario.getProducto().getCodigo_icp()}) - Sección: ${inventario.getSeccion().getNombre_seccion()} -  Presentación: ${inventario.getProducto().getPresentacion()} </option>
                    </c:otherwise>
                  </c:choose>
                </c:forEach>
              </select> 
            </c:when>
            <c:otherwise>
              <select id="seleccioninventario" class="select2" style='background-color: #fff;' name="seleccioninventario" required
                      oninvalid="setCustomValidity('Este campo es requerido')"
                      oninput="setCustomValidity('')">
                <c:forEach items="${inventarios}" var="inventario">
                  <option data-stock="${inventario.getStock_actual()}" value=${inventario.getId_inventario()}> Producto: ${inventario.getProducto().getNombre()} (${inventario.getProducto().getCodigo_icp()}) - Sección: ${inventario.getSeccion().getNombre_seccion()}  - Presentación: ${inventario.getProducto().getPresentacion()} </option>
                </c:forEach>
              </select> 
            </c:otherwise>    

          </c:choose>
        </div>
      </div>
    </div>
  </div>
  <div class="col-md-6">
    <label for="cantidad" class="control-label">*Cantidad</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input id="cantidadinput" placeholder="Número mayor a 1" type="number" min="1" class="form-control" name="cantidad" value="${solicitud.getCantidad()}"required 
                  oninvalid="setCustomValidity('No hay suficientes existencias para realizar la solicitud. ')"
                  oninput="setCustomValidity('')"> 
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
        <button type="button" class="btn btn-danger" onclick="history.back()"><i class="fa fa-times-circle"></i> Cancelar</button>
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
      
      