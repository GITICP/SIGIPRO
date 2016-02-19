<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="ListaEspera">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_lista" value="${lista.getId_lista()}">
      <input hidden="true" name="accion" value="${accion}">
      <input id="listaProductos" hidden="true" name="listaProductos" value="">
      <input id="listaHistoriales" hidden="true" name="listaHistoriales" value="">
      <input id="listaObservaciones" hidden="true" name="listaObservaciones" value="">
            <label for="id_cliente" class="control-label"> *Cliente</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_cliente" class="select2" name="id_cliente" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${clientes}" var="cliente">
                            <c:choose>
                              <c:when test="${lista.getCliente().getId_cliente() == cliente.getId_cliente()}" >
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
            
            <label for="prioridad" class="control-label"> *Prioridad</label>
            <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                  <input id="prioridad" type="number" min="0" max="10" class="form-control" name="prioridad" value="${lista.getPrioridad()}" required
                    oninvalid="setCustomValidity('Debe ingresar un valor válido entre 1 y 10. ')"
                    oninput="setCustomValidity('')"> 
              </div>
            </div>
          </div>
            
            <label for="fecha" class="control-label"> *Fecha de Ingreso</label>
            <!-- Fecha -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                          <c:when test="${historial == 'Agregar'}" >
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha" class="form-control sigiproDatePickerEspecial" name="fecha" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')"> 
                            <script>
                                var today = new Date();
                                var dd = today.getDate();
                                var mm = today.getMonth()+1; //January is 0!

                                var yyyy = today.getFullYear();
                                if(dd<10){
                                    dd='0'+dd
                                } 
                                if(mm<10){
                                    mm='0'+mm
                                } 
                                var today = dd+'/'+mm+'/'+yyyy;
                                document.getElementById("fecha").value = today;
                            </script>
                          </c:when>
                          <c:otherwise>
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha" value="${lista.getFecha_ingreso_S()}" class="form-control sigiproDatePickerEspecial" name="fecha" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')"> 
                          </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            </div>
      </div>
      
      <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> *Productos </h3>
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
                          <th>Editar/Eliminar</th>
                          <th hidden>Stock</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${productos_lista}" var="producto">
                          <tr id="${producto.getProducto().getId_producto()}">
                            <td>${producto.getProducto().getNombre()}</td>
                            <td>${producto.getCantidad()}</td>
                            <td>
                              <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarProducto(${producto.getProducto().getId_producto()})"   >Editar</button>
                              <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarProducto(${producto.getProducto().getId_producto()})" >Eliminar</button>
                            </td>
                            <td hidden>${producto.getProducto().getStock()}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
            
                    <!-- Esta parte es la de los historiales -->
                <div class="widget widget-table">
                    <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> *Historiales</h3>
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarHistorial">Agregar</a>
                    </div>
                    </div>
                  
                  <div class="widget-content">
                    <table id="datatable-column-filter-historiales" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Historial</th>
                          <th>Eliminar</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${historiales_lista}" var="historial">
                          <tr id="${historial.getId_historial()}">
                            <td>${historial.getHistorial()}</td>
                            <td>
                              <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarHistorial(${historial.getId_historial()})" >Eliminar</button>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                    </div>
                <!-- Esta parte es la de los historiales de la solicitud -->
                         <!-- Esta parte es la de los observaciones -->
                <div class="widget widget-table">
                    <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> *Observaciones</h3>
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarObservacion">Agregar</a>
                    </div>
                    </div>
                  
                  <div class="widget-content">
                    <table id="datatable-column-filter-observaciones" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Observacion</th>
                          <th>Eliminar</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${observaciones_lista}" var="observacion">
                          <tr id="${observacion.getId_observacion()}">
                            <td>${observacion.getObservacion()}</td>
                            <td>
                              <button type="button" class="btn btn-danger btn-sm boton-observacion" onclick="eliminarObservacion(${observacion.getId_observacion()})" >Eliminar</button>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                    </div>
                <!-- Esta parte es la de los observaciones de la solicitud -->   
                            
  

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" id="botonConfirmar" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" id="botonConfirmar" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Lista</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>