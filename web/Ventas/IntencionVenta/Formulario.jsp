<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="formularioProducto" autocomplete="off" method="post" action="IntencionVenta">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_intencion" value="${intencion.getId_intencion()}">
      <input hidden="true" name="accion" value="${accion}">
            <label for="id_cliente" class="control-label"> *Cliente</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="id_cliente" type="text" class="form-control" maxlength="80" name="id_cliente" list="lista_clientes" required>
                        <datalist id="lista_clientes">
                          <c:forEach items="${clientes}" var="cliente">
                            <c:choose>  
                              <c:when test="${intencion.getCliente().getId_cliente() == cliente.getId_cliente()}" >
                                <option value=${cliente.getId_cliente()} selected> ${cliente.getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value=${cliente.getId_cliente()}> ${cliente.getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </datalist>
                    </div>
                </div>
            </div>
            <label for="telefono" class="control-label"> Teléfono (formato 506 22225555)</label>
                    <!-- telefono -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="telefono" type="text" class="form-control" name="telefono" maxlength="12" value="" 
                                    oninvalid="setCustomValidity('Debe ingresar un telefono. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
            <label for="correo_electronico" class="control-label"> Correo Electrónico</label>
                <!-- correo_electronico -->
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input id="correo_electronico" type="email" class="form-control" name="correo_electronico" value="" 
                                oninvalid="setCustomValidity('Debe ingresar un correo electrónico válido. ')"
                                oninput="setCustomValidity('')">
                        </div>
                    </div>
                </div>
            <label for="estado" class="control-label"> *Estado</label>
            <!-- Estado -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="estado" class="select2" name="estado" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${estados}" var="Estado">
                            <c:choose>
                              <c:when test="${intencion.getEstado() == Estado}" >
                                <option value="${Estado}" selected> ${Estado}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${Estado}"> ${Estado}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
    </div>
    <div class="col-md-6">
            <label for="observaciones" class="control-label"> Observaciones</label>
            <!-- Observaciones -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea id="observaciones" name="observaciones" class="form-control">${intencion.getObservaciones()}</textarea>
                    </div>
                </div>
            </div>
    </div>                    
         
    <div class="col-md-12">
        
        <!-- Esta arte es la de los productos de la solicitud -->
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> *Productos a Vender</h3>
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarProducto">Agregar</a>
                      <input id="listaProductos" hidden="true" name="listaProductos" value="" required>
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-productos" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Producto</th>
                          <th>Cantidad</th>
                          <th>Posible Fecha de Despacho</th>
                          <th>Editar/Eliminar</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_intencion}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getFecha_S()}</td>
                            <td>
                              <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarProducto(${producto.getProducto().getId_producto()})"   >Editar</button>
                              <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarProducto(${producto.getProducto().getId_producto()})" >Eliminar</button>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- Esta parte es la de los productos de la solicitud -->
    </div>
  </div>
  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" onclick="validarProductosYSubmit()" id="boton_confirmar" data-toggle="confirmar" title="Asegúrese de agregar productos" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" onclick="validarProductosYSubmit()" id="boton_confirmar" data-toggle="confirmar" title="Asegúrese de agregar productos" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Solicitud o Intención de Venta</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Intencion_venta.js"></script>