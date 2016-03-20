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
            <label for="id_intencion" class="control-label"> *Solicitud o Intención de Venta</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_intencion_completo" name="id_intencion_completo" hidden>
                          <option value=''></option>
                          <c:forEach items="${intenciones}" var="intencion">
                            <c:choose>
                              <c:when test="${cotizacion.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                <option value="${intencion.getId_intencion()}" data-cliente="${intencion.getCliente().getId_cliente()}" selected> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${intencion.getId_intencion()}" data-cliente="${intencion.getCliente().getId_cliente()}"> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                        <select id="id_intencion" class="select2" name="id_intencion" 
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')" required>
                          <option value=''></option>
                          <c:forEach items="${intenciones}" var="intencion">
                            <c:choose>
                              <c:when test="${cotizacion.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                <option value="${intencion.getId_intencion()}" data-cliente="${intencion.getCliente().getId_cliente()}" selected> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${intencion.getId_intencion()}" data-cliente="${intencion.getCliente().getId_cliente()}"> ID: ${intencion.getId_intencion()}, Cliente: ${intencion.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
    </div>
    <div class="col-md-6">
            <label for="total" class="control-label"> Total (Sin Flete) </label>
            <!-- Total Parcial -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="total_parcial" type="number" min="0" class="form-control" name="total_parcial" value="${cotizacion.getTotal() - cotizacion.getFlete()}"
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="flete" class="control-label"> Flete</label>
            <!-- Flete -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="flete" type="number" min="0" class="form-control" name="flete" value="${cotizacion.getFlete()}" 
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="total" class="control-label"> Total Final </label>
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
                          <th>Precio Unitario</th>
                          <th>Agregar/Cambiar Precio</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_cotizacion}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getProducto().getLote()}</td>
                            <td>${producto.getPrecio()}</td>
                            <td>
                                <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarProducto(${producto.getProducto().getId_producto()})"   >Modificar Precio</button>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
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