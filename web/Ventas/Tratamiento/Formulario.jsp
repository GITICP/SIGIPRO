<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Tratamiento">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_tratamiento" value="${tratamiento.getId_tratamiento()}">
      <input hidden="true" name="accion" value="${accion}">
      <input id="listaAcciones" hidden="true" name="listaAcciones" value="">
            <label for="id_cliente" class="control-label"> *Cliente</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_cliente" class="select2" name="id_cliente" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${clientes}" var="cliente">
                            <c:choose>
                              <c:when test="${tratamiento.getCliente().getId_cliente() == cliente.getId_cliente()}" >
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
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha" value="${tratamiento.getFecha_S()}" class="form-control sigiproDatePickerEspecial" name="fecha" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')"> 
                          </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            </div>
      </div>
      <p id="texto"></p>
            
                    <!-- Esta parte es la de los acciones -->
                <div class="widget widget-table">
                  <div class="widget-header">
                      <c:choose>
                          <c:when test="${accion.equals('Editar')}" >
                            <h3><i class="fa fa-th-list"></i> *Acciones a aplicar. Debe haber al menos una acción. De lo contrario, no habrá cambios en las acciones.</h3>
                          </c:when>
                          <c:otherwise>
                            <h3><i class="fa fa-th-list"></i> *Acciones a aplicar</h3>
                          </c:otherwise>
                        </c:choose>
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarAccion">Agregar</a>
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-acciones" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>ID</th>
                          <th>Acción</th>
                          <th>Eliminar</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${acciones_tratamiento}" var="accion">
                          <tr id="${accion.getId_accion()}">
                            <td>${accion.getId_accion()}</td>
                            <td>${accion.getAccion()}</td>
                            <td>
                              <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarAccion(${accion.getId_accion()})" >Eliminar</button>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- Esta parte es la de los acciones de la solicitud -->
                        
                            
  

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" id="botonConfirmar" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" id="botonConfirmar" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Tratamiento</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>