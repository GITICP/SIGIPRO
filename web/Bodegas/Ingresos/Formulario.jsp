<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="ingresoForm" class="form-horizontal" autocomplete="off" method="post" action="Ingresos">

  <div class="row">

    <div class="col-md-6">
      <input hidden="true" name="id_producto" value="${ingreso.getProducto().producto.getId_producto()}">
      <label for="producto" class="control-label">* Producto</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <select id="seleccionProducto" class="select2" style='background-color: #fff;' name="producto" required
                    oninvalid="setCustomValidity('Este campo es requerido')"
                    oninput="setCustomValidity('')">
              <!--
              <c:forEach items="${rolesRestantes}" var="rol">
                <option value=${rol.getID()}>${rol.getNombreRol()}</option>
              </c:forEach>
              -->
              <option value=${rol.getID()}>Producto 1</option>
              <option value=${rol.getID()}>Producto 2</option>
              <option value=${rol.getID()}>Producto 3</option>
              <option value=${rol.getID()}>Producto 4</option>
            </select>
          </div>
        </div>
      </div>
      <label for="seccion" class="control-label">Sección</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <%-- Esto va en el valor de abajo ${ingreso.getSeccion().getNombreSeccion()} --%>
            <input type="text" value="Bodegas" id="seccion" class="form-control" name="seccion" required
                   oninvalid="setCustomValidity('Este campo es requerido ')"
                   onchange="setCustomValidity('')" disabled>
          </div>
        </div>
      </div>
      <label for="fechaIngreso" class="control-label">* Fecha de Ingreso</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input type="text" value="${ingreso.getFecha_ingreso()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fechaIngreso" class="form-control sigiproDatePicker" name="fechaIngreso" data-date-format="dd/mm/yyyy" required
                   oninvalid="setCustomValidity('Este campo es requerido ')"
                   onchange="setCustomValidity('')">
          </div>
        </div>
      </div>
      <label for="fechaVencimiento" class="control-label">* Fecha de Vencimiento</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input type="text" value="${ingreso.getFecha_vencimiento()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fechaVencimiento" class="form-control sigiproDatePicker" name="fechaVencimiento" data-date-format="dd/mm/yyyy" required
                   oninvalid="setCustomValidity('Este campo es requerido ')"
                   onchange="setCustomValidity('')">
          </div>
        </div>
      </div>  
    </div>

    <div class="col-md-6">
      <label for="cantidad" class="control-label">* Cantidad</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input id="cantidad" maxlength="45" placeholder="0" class="form-control campo-numero" name="cantidad" value="${ingreso.getCantidad()}"
                   required
                   oninvalid="setCustomValidity('Este campo es requerido ')"
                   oninput="setCustomValidity('')" ><p id="errorCantidad" class="error-form"></p>
          </div>
        </div>
      </div>
      <label for="precio" class="control-label">* Precio</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input id="precio" maxlength="45" placeholder="0" class="form-control campo-numero" name="precio" value="${ingreso.getPrecio()}"
                   required
                   oninvalid="setCustomValidity('Este campo es requerido ')"
                   oninput="setCustomValidity('')" ><p id="errorPrecio" class="error-form"></p>
          </div>
        </div>
      </div>
      <label for="estado" class="control-label">Estado</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <label class="fancy-radio">
              <input type="radio" name="estado" value="estado1"><span><i></i>Estado 1</span> 
            </label>
            <label class="fancy-radio">
              <input type="radio" name="estado" value="estado2"><span><i></i>Estado 2</span> 
            </label>
            <label class="fancy-radio">
              <input type="radio" name="estado" value="estado3"><span><i></i>Estado 3</span> 
            </label>
            <label class="fancy-radio">
              <input type="radio" name="estado" value="estado4"><span><i></i>Estado 4</span> 
            </label>
          </div>
        </div>
      </div>
    </div>

  </div>
  <!-- Esta parte es la de los permisos de un rol -->
  <span>
    Los campos marcados con * son requeridos.
  </span>
  <br>
  <br>

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
      <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Ingreso</button>
    </div>
  </div>
</form>