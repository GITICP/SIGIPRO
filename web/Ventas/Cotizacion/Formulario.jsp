<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="formularioProducto" autocomplete="off" method="post" action="Cotizacion">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_cotizacion" value="${cotizacion.getId_cotizacion()}">
      <input hidden="true" name="accion" value="${accion}">
      <input id="listaProductos" hidden="true" name="listaProductos" value="">
            <label for="id_cliente" class="control-label"> *Cliente</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_cliente" class="select2" name="id_cliente" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${clientes}" var="cliente">
                            <c:choose>
                              <c:when test="${cotizacion.getCliente().getId_cliente() == cliente.getId_cliente()}" >
                                <option value=${cliente.getId_cliente()} selected> ${cliente.getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value=${cliente.getId_cliente()}> ${cliente.getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label for="id_intencion" class="control-label"> Solicitud o Intención de Venta</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_intencion" class="select2" name="id_intencion" 
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <option value=''></option>
                          <c:forEach items="${intenciones}" var="intencion">
                            <c:choose>
                              <c:when test="${cotizacion.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                <option value=${intencion.getId_intencion()} selected> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value=${intencion.getId_intencion()}> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
    </div>
    <div class="col-md-6">
            <label for="flete" class="control-label"> *Flete</label>
            <!-- Flete -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="flete" type="number" min="0" class="form-control" name="flete" value="${cotizacion.getFlete()}" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="total" class="control-label"> Total (Auto Calculado)</label>
            <!-- Total -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="total" type="number" min="0" class="form-control" name="total" value="${cotizacion.getTotal()}"
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
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
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-productos" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Producto</th>
                          <th>Cantidad</th>
                          <th>Posible Fecha de Despacho</th>
                          <th>Precio Unitario</th>
                          <th>Editar/Eliminar</th>
                          <th hidden="true">Stock</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_cotizacion}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getFecha_S()}</td>
                            <td>${producto.getProducto().getPrecio()}</td>
                            <td>
                              <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarProducto(${producto.getProducto().getId_producto()})"   >Editar</button>
                              <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarProducto(${producto.getProducto().getId_producto()})" >Eliminar</button>
                            </td>
                            <td hidden="true">${producto.getProducto().getStock()}</td>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Cotización</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Cotizacion.js"></script>