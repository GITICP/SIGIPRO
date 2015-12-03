<%-- 
    Document   : Formulario_reservacion
    Created on : Nov 14, 2015, 1:57:03 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-Principal" class="form-horizontal" autocomplete="off" method="post" action="Inventario_PT">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_reservacion" value="${reservacion.getId_reservacion()}">
      <input hidden="true" name="accion" value="${accion}">
      <input hidden="true" id="rolesUsuario" name="rolesUsuario" value="">
      <label for="identificacion" class="control-label">Observaciones</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="150" placeholder="Observaciones" class="form-control" id="observaciones" name="observaciones" >${reservacion.getObservaciones()}</textarea>
          </div>
        </div>
      </div>
      <label for="hasta" class="control-label">*Fecha Límite de la Reservación</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="hasta" value="${reservacion.getHasta_S()}" class="form-control sigiproDatePickerEspecial" name="hasta" data-date-format="dd/mm/yyyy" required
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">    
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="widget widget-table">
    <div class="widget-header">
      <h3><i class="fa fa-check"></i> Selección de Lotes de Producto y Cantidades</h3>
      <div class="btn-group widget-header-toolbar">
        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarLote">Agregar</a>
      </div>
    </div>
    <div class="widget-content">
      <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
        <thead>
          <tr>
            <th>Lote</th>
            <th>Cantidad</th>
            <th>Modificar</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${reservaciones_inventarios}" var="inventario">
            <tr id="${inventario.getId_inventario_pt()}">
              <td>${inventario.getInventario().getLote()} (${inventario.getInventario().getProducto().getNombre()})</td>
              <td>${inventario.getCantidad()}</td>
              <td>
                <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarRolUsuario(${inventario.getId_inventario_pt()})"   >Editar</button>
                <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarRolUsuario(${inventario.getId_inventario_pt()})" >Eliminar</button>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
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
            <c:when test= "${accion.equals('Editar_reservacion')}">
              <button onclick="confirmacion()" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </c:when>
            <c:otherwise>
              <button onclick="confirmacion()" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Despacho</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</form>    

