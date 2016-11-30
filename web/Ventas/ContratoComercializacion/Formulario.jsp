<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="formContrato" autocomplete="off" method="post" action="ContratoComercializacion">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_contrato" value="${contrato.getId_contrato()}">
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
                              <c:when test="${contrato.getCliente().getId_cliente() == cliente.getId_cliente()}" >
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
            <label for="nombre" class="control-label"> *Nombre del Contrato</label>
            <!-- Nombre -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="nombre" type="text" class="form-control" name="nombre" value="${contrato.getNombre()}" required
                            oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                            oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            
            <label for="observaciones" class="control-label"> Observaciones</label>
            <!-- Observaciones -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea id="observaciones" name="observaciones" class="form-control">${contrato.getObservaciones()}</textarea>
                    </div>
                </div>
            </div>
    </div>  
    <div class="col-md-6">
        <label for="fecha" class="control-label"> *Fecha Inicial</label>
            <!-- Fecha Inicial -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                          <c:when test="${accion == 'Agregar'}" >
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_inicial" class="form-control sigiproDatePickerEspecial" name="fecha_inicial" data-date-format="dd/mm/yyyy" required
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
                                document.getElementById("fecha_inicial").value = today;
                            </script>
                          </c:when>
                          <c:otherwise>
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_inicial" value="${contrato.getFechaInicial_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_inicial" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')"> 
                          </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="fecha" class="control-label"> *Fecha de Renovación</label>
            <!-- Fecha de Renovación -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                          <c:when test="${accion == 'Agregar'}" >
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_renovacion" class="form-control sigiproDatePickerEspecial" name="fecha_renovacion" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido y debe ser mayor a la fecha inicial ')"
                            onchange="setCustomValidity('')"> 
                            <script>
                                var today = new Date();
                                var dd = today.getDate();
                                var mm = today.getMonth()+1; //January is 0!

                                var yyyy = today.getFullYear();
                                dd += 1;
                                if(dd<10){
                                    dd='0'+dd
                                } 
                                if(mm<10){
                                    mm='0'+mm
                                } 
                                var today = dd+'/'+mm+'/'+yyyy;
                                document.getElementById("fecha_renovacion").value = today;
                            </script>
                          </c:when>
                          <c:otherwise>
                            <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_renovacion" value="${contrato.getFechaRenovacion_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_renovacion" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido y debe ser mayor a la fecha inicial ')"
                            onchange="setCustomValidity('')"> 
                          </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="firmado" class="control-label"> Firmado</label>
            <!-- Firmado o No -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test= "${(accion.equals('Editar')) && (contrato.isFirmado())}">
                                <input id="firmado" type="checkbox" class="form-control" name="firmado" value="Firmado" checked>
                            </c:when>
                            <c:otherwise>
                                <input id="firmado" type="checkbox" class="form-control" name="firmado" value="Firmado">
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
                    <button type="submit" onclick="comprobarFechas()" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" onclick="comprobarFechas()" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Contrato</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Contrato_comercializacion.js"></script>