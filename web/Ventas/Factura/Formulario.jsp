<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 5:02:29 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" enctype='multipart/form-data' method="post" action="Factura">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_factura" value="${factura.getId_factura()}">
            <input hidden="true" name="accion" id="accion" value="${accion}">
            
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
                                <option value="${orden.getId_orden()}" data-cliente="${orden.getCliente().getId_cliente()}" selected> ID: ${orden.getId_orden()} Cliente: ${orden.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${orden.getId_orden()}" data-cliente="${orden.getCliente().getId_cliente()}"> ID: ${orden.getId_orden()} Cliente: ${orden.getCliente().getNombre()}</option>
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
                                <option value="${orden.getId_orden()}" data-cliente="${orden.getCliente().getId_cliente()}" selected> ID: ${orden.getId_orden()} Cliente: ${orden.getCliente().getNombre()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value="${orden.getId_orden()}" data-cliente="${orden.getCliente().getId_cliente()}"> ID: ${orden.getId_orden()} Cliente: ${orden.getCliente().getNombre()}</option>
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
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha" value="${factura.getFecha_S()}" class="form-control sigiproDatePickerEspecial" name="fecha" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')"> 
                          </c:otherwise>
                        </c:choose>
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
                                                        <option value="0" selected> 0 (Pendiente)</option>
                                                        <option value="30"> 30 (Crédito)</option>
                                                        <option value="60"> 60 (Crédito)</option>
                                                        <option value="90"> 90 (Crédito)</option>
                                                        <option value="120"> 120 (Crédito)</option>
                                                    </select>
                                                    </c:when>
                                                    <c:otherwise>  
                                                        <c:choose>
                                                            <c:when test="${factura.getPlazo() == 0}" >
                                                                <select id="plazo" class="select2" name="plazo" required
                                                                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                                                    <option value="0" selected> 0 (Pendiente)</option>
                                                                    <option value="30"> 30 (Crédito)</option>
                                                                    <option value="60"> 60 (Crédito)</option>
                                                                    <option value="90"> 90 (Crédito)</option>
                                                                    <option value="120"> 120 (Crédito)</option>
                                                                </select>
                                                            </c:when>
                                                            <c:when test="${factura.getPlazo() == 30}" >
                                                                <select id="plazo" class="select2" name="plazo" required
                                                                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                                                    <option value="0"> 0 (Pendiente)</option>
                                                                    <option value="30" selected> 30 (Crédito)</option>
                                                                    <option value="60"> 60 (Crédito)</option>
                                                                    <option value="90"> 90 (Crédito)</option>
                                                                    <option value="120"> 120 (Crédito)</option>
                                                                </select>
                                                            </c:when>
                                                            <c:when test="${factura.getPlazo() == 60}" >
                                                                <select id="plazo" class="select2" name="plazo" required
                                                                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                                                    <option value="0"> 0 (Pendiente)</option>
                                                                    <option value="30"> 30 (Crédito)</option>
                                                                    <option value="60" selected> 60 (Crédito)</option>
                                                                    <option value="90"> 90 (Crédito)</option>
                                                                    <option value="120"> 120 (Crédito)</option>
                                                                </select>
                                                            </c:when>
                                                            <c:when test="${factura.getPlazo() == 90}" >
                                                                <select id="plazo" class="select2" name="plazo" required
                                                                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                                                    <option value="0"> 0 (Pendiente)</option>
                                                                    <option value="30"> 30 (Crédito)</option>
                                                                    <option value="60"> 60 (Crédito)</option>
                                                                    <option value="90" selected> 90 (Crédito)</option>
                                                                    <option value="120"> 120 (Crédito)</option>
                                                                </select>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <select id="plazo" class="select2" name="plazo" required
                                                                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                                                                    <option value="0"> 0 (Pendiente)</option>
                                                                    <option value="30"> 30 (Crédito)</option>
                                                                    <option value="60"> 60 (Crédito)</option>
                                                                    <option value="90"> 90 (Crédito)</option>
                                                                    <option value="120" selected> 120 (Crédito)</option>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Factura</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>
    <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
    <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Factura.js"></script>