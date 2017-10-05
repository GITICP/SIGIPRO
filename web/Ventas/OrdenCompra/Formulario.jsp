<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="formularioProducto" enctype="multipart/form-data" autocomplete="off" method="post" action="OrdenCompra">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_orden" value="${orden.getId_orden()}">
      <input hidden="true" name="accion" id="accion" value="${accion}">
      <input id="listaProductos" hidden="true" name="listaProductos" value="${listadoProductos}">
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <label for="consecutivo" class="control-label"> Consecutivo: ${orden.getId_orden()}</label>
                </c:when>
                <c:otherwise>
                    <label for="consecutivo" class="control-label"> Consecutivo: ${consecutivo}</label>
                </c:otherwise>
            </c:choose>
                    <p></p>
            <label for="eleccion" class="control-label"> *Cotización o Intención de Venta</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="eleccion" class="select2" name="eleccion" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                <c:choose>
                                    <c:when test= "${accion.equals('Editar')}">
                                        <c:choose>
                                            <c:when test= "${(orden.getCotizacion() == null)||(orden.getCotizacion().getId_cotizacion() == 0)}">
                                                <option value="1"> Cotización</option>
                                                <option value="2" selected> Solicitud o Intención de Venta</option>
                                            </c:when>    
                                            <c:otherwise>
                                                <option value="1" selected> Cotización</option>
                                                <option value="2"> Solicitud o Intención de Venta</option>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="1"> Cotización</option>
                                        <option value="2"> Solicitud o Intención de Venta</option>
                                    </c:otherwise>
                                </c:choose>
                        </select>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <c:choose>
                        <c:when test= "${(orden.getCotizacion() == null)||(orden.getCotizacion().getId_cotizacion() == 0)}">
                            <label for="id_intencion" id="id_intencion_label" class="control-label"> *Solicitud o Intención de Venta</label>
                            <!-- Intención -->
                            <div class="form-group" id="id_intencionfg">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select id="id_intencion" class="select2" name="id_intencion" required
                                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                         <option value=""></option>
                                          <c:forEach items="${intenciones}" var="intencion">
                                            <c:choose>
                                              <c:when test="${orden.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                                <option value="${intencion.getId_intencion()}" selected> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}${intencion.getNombre_cliente()}</option>
                                              </c:when>
                                              <c:otherwise>
                                                <option value="${intencion.getId_intencion()}"> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}${intencion.getNombre_cliente()}</option>
                                              </c:otherwise>
                                            </c:choose>
                                          </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <label for="id_cotizacion" id="id_cotizacion_label" class="control-label" hidden> *Cotización</label>
                            <!-- Cotización -->
                            <div class="form-group" id="id_cotizacionfg" hidden>
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select id="id_cotizacion" class="select2" name="id_cotizacion"
                                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                          <option value=""></option>
                                          <c:forEach items="${cotizaciones}" var="cotizacion">
                                            <c:choose>
                                              <c:when test="${orden.getCotizacion().getId_cotizacion() == cotizacion.getId_cotizacion()}" >
                                                <option value="${cotizacion.getId_cotizacion()}" data-intencion="${cotizacion.getIntencion().getId_intencion()}" selected> ID: ${cotizacion.getIdentificador()}, Cliente: ${cotizacion.getIntencion().getCliente().getNombre()}${cotizacion.getIntencion().getNombre_cliente()}</option>
                                              </c:when>
                                              <c:otherwise>
                                                <option value="${cotizacion.getId_cotizacion()}" data-intencion="${cotizacion.getIntencion().getId_intencion()}"> ID: ${cotizacion.getIdentificador()}, Cliente: ${cotizacion.getIntencion().getCliente().getNombre()}${cotizacion.getIntencion().getNombre_cliente()}</option>
                                              </c:otherwise>
                                            </c:choose>
                                          </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <label for="id_cotizacion" id="id_cotizacion_label" class="control-label"> *Cotización</label>
                            <!-- Cotización -->
                            <div class="form-group" id="id_cotizacionfg">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select id="id_cotizacion" class="select2" name="id_cotizacion" required
                                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                          <option value=""></option>
                                          <c:forEach items="${cotizaciones}" var="cotizacion">
                                            <c:choose>
                                              <c:when test="${orden.getCotizacion().getId_cotizacion() == cotizacion.getId_cotizacion()}" >
                                                <option value="${cotizacion.getId_cotizacion()}" data-intencion="${cotizacion.getIntencion().getId_intencion()}" selected> ID: ${cotizacion.getIdentificador()}, Cliente: ${cotizacion.getIntencion().getCliente().getNombre()}${cotizacion.getIntencion().getNombre_cliente()}</option>
                                              </c:when>
                                              <c:otherwise>
                                                <option value="${cotizacion.getId_cotizacion()}" data-intencion="${cotizacion.getIntencion().getId_intencion()}"> ID: ${cotizacion.getIdentificador()}, Cliente: ${cotizacion.getIntencion().getCliente().getNombre()}${cotizacion.getIntencion().getNombre_cliente()}</option>
                                              </c:otherwise>
                                            </c:choose>
                                          </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                            <label for="id_intencion" id="id_intencion_label" class="control-label" hidden> *Solicitud o Intención de Venta</label>
                            <!-- Intención -->
                            <div class="form-group" id="id_intencionfg" hidden>
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <select id="id_intencion" class="select2" name="id_intencion"
                                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                         <option value=""></option>
                                          <c:forEach items="${intenciones}" var="intencion">
                                            <c:choose>
                                              <c:when test="${orden.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                                <option value="${intencion.getId_intencion()}" selected> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}${intencion.getNombre_cliente()}</option>
                                              </c:when>
                                              <c:otherwise>
                                                <option value="${intencion.getId_intencion()}"> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}${intencion.getNombre_cliente()}</option>
                                              </c:otherwise>
                                            </c:choose>
                                          </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:when>
                <c:otherwise>
                    <label for="id_cotizacion" id="id_cotizacion_label" class="control-label"> *Cotización</label>
                    <!-- Cotización -->
                    <div class="form-group" id="id_cotizacionfg">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <select id="id_cotizacion" class="select2" name="id_cotizacion" required
                                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                  <option value=""></option>
                                  <c:forEach items="${cotizaciones}" var="cotizacion">
                                    <c:choose>
                                      <c:when test="${orden.getCotizacion().getId_cotizacion() == cotizacion.getId_cotizacion()}" >
                                        <option value="${cotizacion.getId_cotizacion()}" data-intencion="${cotizacion.getIntencion().getId_intencion()}" selected> ID: ${cotizacion.getIdentificador()}, Cliente: ${cotizacion.getIntencion().getCliente().getNombre()}${cotizacion.getIntencion().getNombre_cliente()}</option>
                                      </c:when>
                                      <c:otherwise>
                                        <option value="${cotizacion.getId_cotizacion()}" data-intencion="${cotizacion.getIntencion().getId_intencion()}"> ID: ${cotizacion.getIdentificador()}, Cliente: ${cotizacion.getIntencion().getCliente().getNombre()}${cotizacion.getIntencion().getNombre_cliente()}</option>
                                      </c:otherwise>
                                    </c:choose>
                                  </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <label for="id_intencion" id="id_intencion_label" class="control-label" hidden> *Solicitud o Intención de Venta</label>
                    <!-- Intención -->
                    <div class="form-group" id="id_intencionfg" hidden>
                        <div class="col-sm-12">
                            <div class="input-group">
                                <select id="id_intencion" class="select2" name="id_intencion"
                                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                 <option value=""></option>
                                  <c:forEach items="${intenciones}" var="intencion">
                                    <c:choose>
                                      <c:when test="${orden.getIntencion().getId_intencion() == intencion.getId_intencion()}" >
                                        <option value="${intencion.getId_intencion()}" selected> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}${intencion.getNombre_cliente()}</option>
                                      </c:when>
                                      <c:otherwise>
                                        <option value="${intencion.getId_intencion()}"> ID: ${intencion.getId_intencion()} Cliente: ${intencion.getCliente().getNombre()}${intencion.getNombre_cliente()}</option>
                                      </c:otherwise>
                                    </c:choose>
                                  </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
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
                              <c:when test="${orden.getEstado() == Estado}" >
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
                        <textarea id="rotulacion" maxlength="1499" class="form-control" name="rotulacion" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">${orden.getRotulacion()}</textarea>
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${orden.getId_orden()!=0}">
                    <label for="documento" class="control-label"> Documento (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="documento" name="documento"  accept="text/html,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/pdf,image/jpeg,image/jpg,image/png" 
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="documento" class="control-label"> Documento</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="documento" name="documento"  accept="text/html,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/pdf,image/jpeg,image/jpg,image/png"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
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
                          <th>Fecha de Entrega</th>
                          <th></th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_orden}" var="producto">
                          <tr data-orden="${producto.getContador()}" id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>${producto.getFecha_S()}</td>
                            <td>
                              <button type="button" class="btn btn-warning btn-sm" style="margin-left:5px;margin-right:7px;" onclick="editarProducto(${producto.getContador()})">Modificar</button>
                              <button type="button" class="btn btn-primary btn-sm" style="margin-left:7px;margin-right:5px;" onclick="duplicarProducto(${producto.getProducto().getId_producto()},${producto.getContador()})">Duplicar</button>
                              <button type="button" class="btn btn-danger btn-sm" style="margin-left:7px;margin-right:5px;" onclick="eliminarProducto(${producto.getProducto().getId_producto()})" >Eliminar</button>
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