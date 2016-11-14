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
            <label for="id_cliente" class="control-label"> *Cliente</label>
            <!-- Id Cliente -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div>
                        <select id="id_cliente" class="select2" name="id_cliente" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                <option value=0> Otro Cliente (Ingreso Manual)</option>
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
            <label for="nombre_cliente_label" id="nombre_cliente_label" class="control-label"> *Nombre del Cliente</label>
                    <!-- Nombre_cliente -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="nombre_cliente" type="text" class="form-control" name="nombre_cliente" maxlength="80" value="${lista.getNombre_cliente()}" 
                                    oninvalid="setCustomValidity('Debe ingresar el nombre del cliente. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
            <label for="telefono" id="telefono_label" class="control-label"> *Teléfono (formato 506 22225555)</label>
                    <!-- telefono -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="telefono" type="text" class="form-control" name="telefono" maxlength="12" value="${lista.getTelefono()}" 
                                    oninvalid="setCustomValidity('Debe ingresar un telefono. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
            <label for="correo_electronico" id="correo_electronico_label" class="control-label"> *Correo Electrónico</label>
                <!-- correo_electronico -->
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input id="correo_electronico" type="email" class="form-control" maxlength="70" name="correo_electronico" value="${lista.getCorreo()}" 
                                oninvalid="setCustomValidity('Debe ingresar un correo electrónico válido. ')"
                                oninput="setCustomValidity('')">
                        </div>
                    </div>
                </div>
            
            
            </div>
            <div class="col-md-6">
                <label for="fecha" class="control-label"> *Fecha de Solicitud</label>
            <!-- Fecha -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                          <c:when test="${historial == 'Agregar'}" >
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_solicitud" class="form-control sigiproDatePickerEspecial" name="fecha_solicitud" data-date-format="dd/mm/yyyy" required
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
                                document.getElementById("fecha_solicitud").value = today;
                            </script>
                          </c:when>
                          <c:otherwise>
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_solicitud" value="${lista.getFecha_solicitud_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_solicitud" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')"> 
                          </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="fecha" class="control-label"> Fecha de Atención / Despacho</label>
            <!-- Fecha -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                          <c:when test="${historial == 'Agregar'}" >
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_atencion" class="form-control sigiproDatePickerEspecial" name="fecha_atencion" data-date-format="dd/mm/yyyy" 
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
                                document.getElementById("fecha_atencion").value = today;
                            </script>
                          </c:when>
                          <c:otherwise>
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_atencion" value="${lista.getFecha_atencion_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_atencion" data-date-format="dd/mm/yyyy" 
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')"> 
                          </c:otherwise>
                        </c:choose>
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
                    <button type="submit" id="botonConfirmar" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" id="botonConfirmar" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Lista</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/ListaEspera.js"></script>