<%-- 
    Document   : Formulario_despacho
    Created on : Nov 14, 2015, 1:57:03 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-Principal" class="form-horizontal" autocomplete="off" method="post" action="Inventario_PT">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_despacho" value="${despacho.getId_despacho()}">
      <input hidden="true" name="accion" value="${accion}">

      <label for="identificacion" class="control-label">Destino</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" id="destino" value="${despacho.getDestino()}" name="destino" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido. Por favor ingrese un número mayor a 1 ')"
                    onchange="setCustomValidity('')">
          </div>
        </div>
      </div>
      <label for="observaciones" class="control-label">Observaciones</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" name="observaciones">${despacho.getObservaciones()}</textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="widget widget-table">
    <div class="widget-header">
      <h3><i class="fa fa-check"></i> Seleccion de Lotes de Producto y Cantidades</h3>
      <div class="btn-group widget-header-toolbar">
        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarLotes">Agregar</a>
      </div>
    </div>
    <div class="widget-content">
      <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
        <thead>
          <tr>
            <th>Lote</th>
            <th>Cantidad</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${inventarios}" var="inventario">
            <tr id="${inventario.getId_inventario_pt()}">
              <td>${inventario.getLote()} (${inventario.getProducto().getNombre()})</td>
              <td>${inventario.getFechaActivacion()}</td>
              <td>${inventario.getFechaDesactivacion()}</td>
              <td>
                <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarRolUsuario(${inventario.getIDUsuario()})"   >Editar</button>
                <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarRolUsuario(${inventario.getIDUsuario()})" >Eliminar</button>
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
            <c:when test= "${accion.equals('Editar_despacho')}">
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </c:when>
            <c:otherwise>
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Despacho</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</form>    

