<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Pago">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_pago" value="${pago.getId_pago()}">
      <input hidden="true" name="accion" id="acccion" value="${accion}">
            <label for="id_factura" class="control-label"> *Factura</label>
            <!-- id_factura -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_factura" class="select2" name="id_factura" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                            <option value=""></option>
                          <c:forEach items="${facturas}" var="factura">
                            <c:choose>
                              <c:when test="${pago.getFactura().getId_factura() == factura.getId_factura()}" >
                                <option value="${factura.getId_factura()}" data-monto="${factura.getMonto()}" selected> FAC: ${factura.getId_factura()} Cliente: ${factura.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${factura.getId_factura()}" data-monto="${factura.getMonto()}"> FAC: ${factura.getId_factura()} Cliente: ${factura.getCliente().getNombre()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label for="monto_pendiente" class="control-label"> Monto Pendiente </label>
            <div class="form-group">
              <div class="col-sm-12">
                <!-- monto_pendiente -->
                <div class="input-group">
                    <input id="monto_pendiente" type="number" min="0" class="form-control" name="monto_pendiente" value="${pago.getMonto_pendiente()}" readonly
                        oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                        oninput="setCustomValidity('')"> 
                </div>
              </div>
            </div>
            <label for="pago" class="control-label"> *Pago</label>
            <div class="form-group">
              <div class="col-sm-12">
                <!-- pago -->
                <div class="input-group">
                    <input id="pago" type="number" min="0" max="${pago.getMonto_pendiente()}" class="form-control" name="pago" value="${pago.getPago()}" required
                        oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                        oninput="setCustomValidity('')"> 
                </div>
              </div>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Pago</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>