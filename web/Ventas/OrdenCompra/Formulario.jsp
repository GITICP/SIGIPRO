<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="formularioProducto" autocomplete="off" method="post" action="OrdenCompra">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_orden" value="${orden.getId_orden()}">
      <input hidden="true" name="accion" id="accion" value="${accion}">
      <input id="listaProductos" hidden="true" name="listaProductos" value="">
            <label for="id_cliente" class="control-label"> *Cliente (Este campo no podrá ser editado)</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_cliente" class="select2" name="id_cliente" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${clientes}" var="cliente">
                            <c:choose>
                              <c:when test="${orden.getCliente().getId_cliente() == cliente.getId_cliente()}" >
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
            <label for="id_cotizacion" class="control-label"> Cotización</label>
            <!-- Cotización -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_cotizacion_completo" name="id_cotizacion_completo" hidden>
                          <option value=""></option>
                          <c:forEach items="${cotizaciones}" var="cotizacion">
                            <c:choose>
                              <c:when test="${orden.getCotizacion().getId_cotizacion() == cotizacion.getId_cotizacion()}" >
                                <option value="${cotizacion.getId_cotizacion()}" data-cliente="${cotizacion.getCliente().getId_cliente()}" selected> ID: ${cotizacion.getId_cotizacion()}, Cliente: ${cotizacion.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${cotizacion.getId_cotizacion()}" data-cliente="${cotizacion.getCliente().getId_cliente()}"> ID: ${cotizacion.getId_cotizacion()}, Cliente: ${cotizacion.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                        <select id="id_cotizacion" class="select2" name="id_cotizacion"
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <option value=""></option>
                          <c:forEach items="${cotizaciones}" var="cotizacion">
                            <c:choose>
                              <c:when test="${orden.getCotizacion().getId_cotizacion() == cotizacion.getId_cotizacion()}" >
                                <option value="${cotizacion.getId_cotizacion()}" data-cliente="${cotizacion.getCliente().getId_cliente()}" selected> ID: ${cotizacion.getId_cotizacion()}, Cliente: ${cotizacion.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${cotizacion.getId_cotizacion()}"data-cliente="${cotizacion.getCliente().getId_cliente()}"> ID: ${cotizacion.getId_cotizacion()}, Cliente: ${cotizacion.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label for="id_intencion" class="control-label"> *Solicitud o Intención de Venta</label>
            <!-- Intención -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_intencion_completo" name="id_intencion_completo" hidden>
                          <option value=''></option>
                          <c:forEach items="${intenciones}" var="intencion">
                            <c:choose>
                              <c:when test="${orden.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                <option value="${intencion.getId_intencion()}" data-cliente="${intencion.getCliente().getId_cliente()}" selected> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${intencion.getId_intencion()}" data-cliente="${intencion.getCliente().getId_cliente()}"> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                        <select id="id_intencion" class="select2" name="id_intencion" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                         <option value=""></option>
                          <c:forEach items="${intenciones}" var="intencion">
                            <c:choose>
                              <c:when test="${orden.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                <option value="${intencion.getId_intencion()}" selected> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${intencion.getId_intencion()}"> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
    </div>
    <div class="col-md-6">
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
            <label for="rotulacion" class="control-label"> *Información de Rotulación</label>
            <!-- Información de Rotulación -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="rotulacion" type="text" class="form-control" name="rotulacion" value="${orden.getRotulacion()}" required
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
                    <h3><i class="fa fa-th-list"></i> Productos de la Solicitud / Intención de Venta</h3>
                    <div class="btn-group widget-header-toolbar">
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-productos" class="table table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Nombre del Producto</th>
                          <th>Cantidad</th>
                          <th>Lote</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_orden}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getProducto().getLote()}</td>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Orden de Compra</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Orden_compra.js"></script>