<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 5:02:29 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="formularioProducto" autocomplete="off" enctype='multipart/form-data' method="post" action="Factura">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_factura" value="${factura.getId_factura()}">
      <input hidden="true" name="accion" id="accion" value="${accion}">
      <input id="listaProductos" hidden="true" name="listaProductos" value="" >
      <label for="id_cliente" class="control-label"> *Cliente</label>
      <!-- Id Cliente -->
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <select id="id_cliente" class="select2" name="id_cliente" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <c:forEach items="${clientes}" var="cliente">
                <c:choose>
                  <c:when test="${factura.getCliente().getId_cliente() == cliente.getId_cliente()}" >
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

      <label for="id_orden" class="control-label"> Orden de Compra</label>
      <!-- Id Cliente -->
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <select id="id_orden_completo" name="id_orden_completo" hidden>
              <option value=""></option>
              <c:forEach items="${ordenes}" var="orden">
                <c:choose>
                  <c:when test="${factura.getOrden().getId_orden() == orden.getId_orden()}" >
                    <c:choose>
                      <c:when test="${(orden.getCotizacion() == null) || (orden.getCotizacion().getId_cotizacion() == 0)}" >
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getIntencion().getCliente().getId_cliente()}" selected> ID: ${orden.getId_orden()}</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getCotizacion().getIntencion().getCliente().getId_cliente()}" selected> ID: ${orden.getId_orden()}</option>
                      </c:otherwise>
                    </c:choose>
                  </c:when>
                  <c:otherwise>
                    <c:choose>
                      <c:when test="${(orden.getCotizacion() == null) || (orden.getCotizacion().getId_cotizacion() == 0)}" >
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getIntencion().getCliente().getId_cliente()}"> ID: ${orden.getId_orden()}</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getCotizacion().getIntencion().getCliente().getId_cliente()}"> ID: ${orden.getId_orden()}</option>
                      </c:otherwise>
                    </c:choose>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
            <select id="id_orden" class="select2" name="id_orden"
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <option value=""></option>
              <c:forEach items="${ordenes}" var="orden">
                <c:choose>
                  <c:when test="${factura.getOrden().getId_orden() == orden.getId_orden()}" >
                    <c:choose>
                      <c:when test="${(orden.getCotizacion() == null) || (orden.getCotizacion().getId_cotizacion() == 0)}" >
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getIntencion().getCliente().getId_cliente()}" selected> ID: ${orden.getId_orden()}</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getCotizacion().getIntencion().getCliente().getId_cliente()}" selected> ID: ${orden.getId_orden()}</option>
                      </c:otherwise>
                    </c:choose>
                  </c:when>
                  <c:otherwise>
                    <c:choose>
                      <c:when test="${(orden.getCotizacion() == null) || (orden.getCotizacion().getId_cotizacion() == 0)}" >
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getIntencion().getCliente().getId_cliente()}"> ID: ${orden.getId_orden()}</option>
                      </c:when>
                      <c:otherwise>
                        <option value="${orden.getId_orden()}" data-cliente="${orden.getCotizacion().getIntencion().getCliente().getId_cliente()}"> ID: ${orden.getId_orden()}</option>
                      </c:otherwise>
                    </c:choose>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>

      <c:choose>
        <c:when test="${factura.getId_factura()!=0}">
          <label for="documento_1" class="control-label"> Documento (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="file" id="documento_1" name="documento_1"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png" 
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
                <input type="file" id="documento_1" name="documento_1"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png"
                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                       onchange="setCustomValidity('')"/>
              </div>
            </div>
          </div>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${factura.getId_factura()!=0}">
          <label for="documento_2" class="control-label"> Documento (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="file" id="documento_2" name="documento_2"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png" 
                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                       onchange="setCustomValidity('')"/>
              </div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <label for="documento_2" class="control-label"> Documento</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="file" id="documento_2" name="documento_2"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png"
                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                       onchange="setCustomValidity('')"/>
              </div>
            </div>
          </div>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${factura.getId_factura()!=0}">
          <label for="documento_3" class="control-label"> Documento (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="file" id="documento_3" name="documento_3"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png" 
                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                       onchange="setCustomValidity('')"/>
              </div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <label for="documento_3" class="control-label"> Documento</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="file" id="documento_3" name="documento_3"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png"
                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                       onchange="setCustomValidity('')"/>
              </div>
            </div>
          </div>
        </c:otherwise>
      </c:choose>

      <c:choose>
        <c:when test="${factura.getId_factura()!=0}">
          <label for="documento_4" class="control-label"> Documento (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="file" id="documento_4" name="documento_4"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png" 
                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                       onchange="setCustomValidity('')"/>
              </div>
            </div>
          </div>
        </c:when>
        <c:otherwise>
          <label for="documento_4" class="control-label"> Documento</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input type="file" id="documento_4" name="documento_4"  accept="video/3gpp,audio/mpeg,audio/x-wav,audio/mp4,audio/x-ms-wma,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/pdf,image/jpeg,image/gif,image/png"
                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                       onchange="setCustomValidity('')"/>
              </div>
            </div>
          </div>
        </c:otherwise>
      </c:choose>

    </div>
    <div class="col-md-6">

      <label for="fecha" class="control-label"> *Fecha</label>
      <!-- Fecha -->
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <c:choose>
              <c:when test="${accion == 'Agregar'}" >
                <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha" class="form-control sigiproDatePickerEspecial" name="fecha" data-date-format="dd/mm/yyyy" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')"> 
                <script>
                  var today = new Date();
                  var dd = today.getDate();
                  var mm = today.getMonth() + 1; //January is 0!

                  var yyyy = today.getFullYear();
                  if (dd < 10) {
                    dd = '0' + dd
                  }
                  if (mm < 10) {
                    mm = '0' + mm
                  }
                  var today = dd + '/' + mm + '/' + yyyy;
                  document.getElementById("fecha").value = today;
                </script>
              </c:when>
              <c:otherwise>
                <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha" value="${factura.getFecha_S()}" class="form-control sigiproDatePickerEspecial" name="fecha" data-date-format="dd/mm/yyyy" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')"> 
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
      <label for="contado" class="control-label"> *Crédito o Contado</label>
      <!-- contado -->
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <select id="contado" class="select2" name="contado" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <c:choose>
                <c:when test="${((factura.getPlazo() == null)||(factura.getPlazo() == 0))}" >
                  <option value="1">Crédito</option>
                  <option value="2" selected>Contado</option>
                </c:when>
                <c:otherwise>
                  <option value="1" selected>Crédito</option>
                  <option value="2">Contado</option>
                </c:otherwise>
              </c:choose>
            </select>
          </div>
        </div>
      </div>
      <label for="plazo" class="control-label"> *Plazo (días)</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <c:choose>
              <c:when test="${accion == 'Agregar'}">
                <select id="plazo" class="select2" name="plazo" required
                        oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                  <option value="0"> 0 </option>
                  <option value="30"> 30 </option>
                  <option value="60"> 60 </option>
                  <option value="90"> 90 </option>
                  <option value="120"> 120 </option>
                </select>
              </c:when>
              <c:otherwise>  
                <c:choose>
                  <c:when test="${factura.getPlazo() == 30}" >
                    <select id="plazo" class="select2" name="plazo" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                      <option value="0"> 0 </option>
                      <option value="30" selected> 30 </option>
                      <option value="60"> 60 </option>
                      <option value="90"> 90 </option>
                      <option value="120"> 120 </option>
                    </select>
                  </c:when>
                  <c:when test="${factura.getPlazo() == 60}" >
                    <select id="plazo" class="select2" name="plazo" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                      <option value="0"> 0 </option>
                      <option value="30"> 30 </option>
                      <option value="60" selected> 60 </option>
                      <option value="90"> 90 </option>
                      <option value="120"> 120 </option>
                    </select>
                  </c:when>
                  <c:when test="${factura.getPlazo() == 90}" >
                    <select id="plazo" class="select2" name="plazo" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                      <option value="0"> 0 </option>
                      <option value="30"> 30 </option>
                      <option value="60"> 60 </option>
                      <option value="90" selected> 90 </option>
                      <option value="120"> 120 </option>
                    </select>
                  </c:when>
                  <c:otherwise>
                    <select id="plazo" class="select2" name="plazo" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                      <option value="0"> 0 </option>
                      <option value="30"> 30 </option>
                      <option value="60"> 60 </option>
                      <option value="90"> 90 </option>
                      <option value="120" selected> 120 </option>
                    </select>
                  </c:otherwise>
                </c:choose>
              </c:otherwise>
            </c:choose> 
          </div>
        </div>
      </div>
      <label for="fecha_vencimiento" class="control-label"> Fecha de Vencimiento</label>
      <!-- Fecha Vencimiento-->
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <c:choose>
              <c:when test="${accion == 'Agregar'}" >
                <input readonly type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_vencimiento" class="form-control" name="fecha_vencimiento"
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')"> 
              </c:when>
              <c:otherwise>
                <input readonly type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_vencimiento" class="form-control" value="${factura.getFecha_vencimiento_S()}" name="fecha_vencimiento"
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')"> 
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
      <label for="monto" class="control-label"> *Monto</label>
      <div class="form-group">
        <div class="col-sm-12">
          <!-- Stock -->
          <div class="input-group">
            <input id="monto" type="number" min="0" class="form-control" name="monto" value="${factura.getMonto()}" required
                   oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                   oninput="setCustomValidity('')"> 
          </div>
        </div>
      </div>
      <label for="moneda" class="control-label"> *Moneda</label>
      <!-- moneda -->
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <select id="moneda" class="select2" name="moneda" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
              <c:forEach items="${monedas}" var="moneda">
                <c:choose>
                  <c:when test="${factura.getMoneda() == moneda}" >
                    <option value="${moneda}" selected> ${moneda}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${moneda}"> ${moneda}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
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
                  <c:when test="${factura.getTipo() == tipo}" >
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
      <!-- Observaciones -->
      <label for="Observaciones" class="control-label"> *Observaciones</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <c:choose>
              <c:when test="${accion == 'Agregar'}" >
                <textarea required style="width:100%" name="observaciones" id="observaciones"></textarea>
              </c:when>
              <c:otherwise>
                <textarea required style="width:100%" name="observaciones" id="observaciones">${factura.getDetalle()}</textarea>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </div>

    <div class="col-md-12" id="Info_Fundevi">
      <div class="widget widget-table">
        <div class="widget-header">
          <h3><i class="fa fa-th-list"></i> Información para FUNDEVI</h3>
        </div>
        <div class="widget-content">
          <div class="col-md-6"> 

            <label for="proyecto" class="control-label"> *Proyecto</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group">
                  <select id="proyecto" class="select2" name="proyecto" required
                          oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                    <option value="404"> 0418-00</option>
                    <option value="1965"> 1770-00</option>
                    <option value="2815"> 2541-00</option>
                  </select>
                </div>
              </div>
            </div>
          </div>
          <div class="col-md-6"> 
            <label for="proyecto" class="control-label"> Correo a Enviar</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group">
                  <input id="correo" type="email" class="form-control" name="correo" value="" placeholder=""
                         oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                         oninput="setCustomValidity('')"> 
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>


    <div class="col-md-12">

      <!-- Esta parte es la de los productos-->
      <div class="widget widget-table">
        <div class="widget-header">
          <h3><i class="fa fa-th-list"></i> *Productos de la Factura </h3>
          <div class="btn-group widget-header-toolbar">
            <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarProducto">Agregar</a>
            
          </div>
        </div>
        <div class="widget-content">
          <table id="datatable-column-filter-productos" class="table table-striped table-hover datatable">
            <thead>
              <tr>
                <th>Nombre del Producto</th>
                <th>Cantidad</th>
                <th>Fecha de Entrega</th>
                <th>Lotes</th>
                <th>Editar/Eliminar</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${productos_factura}" var="producto">
                <tr  id="${producto.getProducto().getId_producto()}">
                  <td>${producto.getProducto().getNombre()}</td>
                  <td>${producto.getCantidad()}</td>
                  <td>${producto.getFecha_S()}</td>
                  <td>${producto.getLote()}</td>
                  <td>
                    <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarProducto(${producto.getProducto().getId_producto()})">Editar</button>
                    <button type="button" class="btn btn-danger btn-sm boton-accion"  onclick="eliminarProducto(${producto.getProducto().getId_producto()})" >Eliminar</button>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
      <!-- Aqui termina la parte de los productos -->
    </div>

    <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
  </div>
  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
      <c:choose>
        <c:when test= "${accion.equals('Editar')}">
          <button type="submit" onclick="validarProductosYSubmit()" id="boton_confirmar" data-toggle="confirmar" title="Asegúrese de agregar productos" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
        </c:when>
        <c:otherwise>
          <button type="submit" onclick="validarProductosYSubmit()" id="boton_confirmar" data-toggle="confirmar" title="Asegúrese de agregar productos" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Factura</button>
        </c:otherwise>
      </c:choose>    </div>
  </div>


</form>
<script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
<script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Factura.js"></script>