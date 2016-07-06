<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Veneno_Produccion">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_veneno" value="${veneno.getId_veneno()}">
      <input hidden="true" name="accion" value="${accion}">
            <label for="veneno" class="control-label"> *Veneno</label>
            <!-- Identificador -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="veneno" type="text" class="form-control" name="veneno" value="${veneno.getVeneno()}" required
                            oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                            oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
        <label for="fecha_ingreso" class="control-label"> *Fecha de Ingreso</label>
        <!-- Fecha de Ingreso -->
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                      <c:when test="${accion == 'Agregar'}" >
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_ingreso" class="form-control sigiproDatePickerEspecial" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
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
                            document.getElementById("fecha_ingreso").value = today;
                        </script>
                      </c:when>
                      <c:otherwise>
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_ingreso" value="${veneno.getFecha_ingreso_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')"> 
                      </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="id_veneno" class="control-label"> *Veneno del Serpentario Asociado (Lote)</label>
            <!-- Id Veneno Serpentario-->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="id_veneno_serpentario" class="select2" name="id_veneno_serpentario" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${listaVenenos}" var="veneno_serpentario">
                            <c:choose>
                              <c:when test="${veneno.getVeneno_serpentario().getId_lote() == veneno_serpentario.getId_lote()}" >
                                <option value=${veneno_serpentario.getId_lote()} selected> Lote: ${veneno_serpentario.getNumero_lote()}, Especie: ${veneno_serpentario.getEspecie().getGenero_especie()}</option>
                              </c:when>
                              <c:otherwise>
                                <option value=${veneno_serpentario.getId_lote()}> Lote: ${veneno_serpentario.getNumero_lote()}, Especie: ${veneno_serpentario.getEspecie().getGenero_especie()}</option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
    </div>    
    <div class="col-md-6">
<!-- Cantidad -->
        <label for="cantidad" class="control-label"> *Cantidad (mg)</label>
            <div class="form-group">
              <div class="col-sm-12">
                <!-- Peso -->
                <div class="input-group">
                    <input id="cantidad" type="number" min="1" class="form-control" name="cantidad" value="${veneno.getCantidad()}" required
                        oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                        oninput="setCustomValidity('')"> 
                </div>
              </div>
            </div>
            <label for="observaciones" class="control-label"> *Observaciones</label>
            <!-- Observaciones -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="observaciones" type="text" class="form-control" name="observaciones" value="${veneno.getObservaciones()}" required
                            oninvalid="setCustomValidity('Debe ingresar alguna observación. ')"
                            oninput="setCustomValidity('')">
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Veneno de Producción</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>