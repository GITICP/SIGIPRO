<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 5:02:29 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="SeguimientoVenta">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_seguimiento" value="${seguimiento.getId_seguimiento()}">
            <input hidden="true" name="accion" id="accion" value="${accion}">
            <input id="listaProductos" hidden="true" name="listaProductos" value="${listaTipos}">
            <input id="idfactura" hidden="true" name="idfactura" value="${seguimiento.getFactura().getId_factura()}">
            
            <label for="id_cliente" class="control-label"> *Cliente</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_cliente" class="select2" name="id_cliente" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${clientes}" var="cliente">
                            <c:choose>
                              <c:when test="${seguimiento.getCliente().getId_cliente() == cliente.getId_cliente()}" >
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
            
            <label for="id_factura" class="control-label"> *Factura</label>
            <!-- Id Factura -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_factura_completo" name="id_factura" hidden>
                          <c:forEach items="${facturas}" var="factura">
                            <c:choose>
                              <c:when test="${seguimiento.getFactura().getId_factura() == factura.getId_factura()}" >
                                <option value="${factura.getId_factura()}" data-cliente="${factura.getCliente().getId_cliente()}" selected> ID: ${factura.getId_factura()} Cliente: ${factura.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${factura.getId_factura()}" data-cliente="${factura.getCliente().getId_cliente()}"> ID: ${factura.getId_factura()} Cliente: ${factura.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                        <select id="id_factura" class="select2" name="id_factura" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${facturas}" var="factura">
                            <c:choose>
                              <c:when test="${seguimiento.getFactura().getId_factura() == factura.getId_factura()}" >
                                <option value="${factura.getId_factura()}" data-cliente="${factura.getCliente().getId_cliente()}" selected> ID: ${factura.getId_factura()} Cliente: ${factura.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${factura.getId_factura()}" data-cliente="${factura.getCliente().getId_cliente()}"> ID: ${factura.getId_factura()} Cliente: ${factura.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            </div>
            <div class="col-md-6">
            <c:choose>
                <c:when test="${seguimiento.getId_seguimiento()!=0}">
                    <label for="documento_1" class="control-label"> Documento (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="documento_1" name="documento_1"  accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png" 
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="documento_1" class="control-label"> Documento</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="documento_1" name="documento_1"  accept="application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
                                
        <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </div>
                    <div class="col-md-12">
        
        <!-- Esta arte es la de los productos de la solicitud -->
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> *Acciones</h3>
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarProducto">Agregar</a>
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-productos" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Tipo</th>
                          <th>Fecha</th>
                          <th>Observaciones</th>
                          <th>Eliminar</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${tipos_seguimiento}" var="tipo_seguimiento">
                          <tr data-orden="${tipo_seguimiento.getContador()}" id="${tipo_seguimiento.getId_tipo()}">
                            <td>${tipo_seguimiento.getTipo()}</td>
                            <td>${tipo_seguimiento.getFecha_S()}</td>
                            <td>${tipo_seguimiento.getObservaciones()}</td>
                            <td>
                                <button type="button" class="btn btn-warning btn-sm" style="margin-left:5px;margin-right:7px;" onclick="editarProducto(${tipo_seguimiento.getContador()})">Modificar</button>
                                <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarProducto(${tipo_seguimiento.getContador()})">Eliminar</button>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- Esta parte es la de los productos de la solicitud -->
            </div>


    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Seguimiento de Venta</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>
