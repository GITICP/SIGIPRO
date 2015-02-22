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
    <input hidden="true" name="fecha_entrega" value="${solicitud.getFecha_entregaAsDate()}">
    <input hidden="true" name="id_usuario_recibo" value="${solicitud.getId_usuario_recibo()}">
    <label for="seleccionproducto" class="control-label"> *Producto</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <select id="seleccionproducto" class="select2" style='background-color: #fff;' name="seleccionproducto" required
                  oninvalid="setCustomValidity('Este campo es requerido')"
                  oninput="setCustomValidity('')">
            <c:forEach items="${inventarios}" var="inventario">
                <option value=${inventario.getId_producto()}> Producto: ${inventario.getProducto().getNombre()} (${inventario.getProducto().getCodigo_icp()}) - Sección: ${inventario.getSeccion().getNombre_seccion()} - Existencias: ${inventario.getStock_actual()} - Presentación: ${inventario.getProducto().getPresentacion()} </option>
              </c:forEach>
          </select> </div>
      </div>
    </div>
  </div>
  <div class="col-md-6">
    <label for="cantidad" class="control-label">*Cantidad</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="number" min="1" class="form-control" name="cantidad" value="${solicitud.getCantidad()}"required 
                  oninvalid="setCustomValidity('Este campo es requerido, por favor introduzca un número válido mayor a 1')"
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
        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Solicitud</button>
      </div>
    </div>
  </div>



</form>
      
      