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
            <input hidden="true" name="accion" value="${accion}">
            
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
            
            <label for="observaciones" class="control-label"> Observaciones</label>
            <!-- Observaciones -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea id="observaciones" name="observaciones" class="form-control">${seguimiento.getObservaciones()}</textarea>
                    </div>
                </div>
            </div>
                                <label for="tipo" class="control-label"> *Tipo</label>
            <!-- Tipo -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="tipo" class="select2" name="tipo" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${tipos}" var="tipo">
                            <c:choose>
                              <c:when test="${seguimiento.getTipo() == tipo}" >
                                <option value="${tipo}" selected> ${tipo}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${tipo}"> ${tipo}</option>
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
