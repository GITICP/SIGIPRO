<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Inoculo">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_inoculo" value="${inoculo.getId_inoculo()}">
      <input hidden="true" name="accion" value="${accion}">
      <input hidden="true" id="listaTipoMuestra" value='${tipomuestraparse}'>
      <input hidden="true" id="listaMuestras" name="listaMuestras" value="">
      <input hidden="true" id="listaPesos" name="listaPesos" value="">
      <input hidden="true" id="listaIds" value="${listaTM}">
            
            <label for="identificador" class="control-label"> *Identificador</label>
            <!-- Identificador -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="identificador" type="text" class="form-control" name="identificador" value="${inoculo.getIdentificador()}" required
                            oninvalid="setCustomValidity('Debe ingresar un identificador. ')"
                            oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
        <label for="fecha_preparacion" class="control-label"> *Fecha de Preparación</label>
        <!-- Fecha de Preparación -->
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                      <c:when test="${accion == 'Agregar'}" >
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_preparacion" class="form-control sigiproDatePickerEspecial" name="fecha_preparacion" data-date-format="dd/mm/yyyy" required
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
                            document.getElementById("fecha_preparacion").value = today;
                        </script>
                      </c:when>
                      <c:otherwise>
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_preparacion" value="${inoculo.getFecha_preparacion_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_preparacion" data-date-format="dd/mm/yyyy" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')"> 
                      </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>    
    <div class="col-md-6">
        <label for="encargado_preparación" class="control-label">*Encargado de Preparación</label>
        <!-- Encargado de Preparación -->
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="encargado_preparacion" class="select2" name="encargado_preparacion" required
                    oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                      <c:forEach items="${usuarios}" var="us">
                        <c:choose>
                          <c:when test="${us.getID() == inoculo.getEncargado_preparacion().getID()}" >
                            <option value=${us.getID()} selected> ${us.getNombreCompleto()}</option>
                          </c:when>
                          <c:otherwise>
                            <option value=${us.getID()}>${us.getNombreCompleto()}</option>
                          </c:otherwise>
                        </c:choose>
                      </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
      <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Venenos</h3>
                </div>
                <div class="widget-content">
                    <div class="muestras">
                        <c:forEach items="${listaSolicitud}" var="muestra">
                            <div id="${muestra.get(0)}" class="col-sm-12">
                                <div class="col-sm-3">
                                    <label for="veneno" class="control-label">*Veneno de Producción</label>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="input-group">
                                                <input hidden="true" id="editartipomuestra_${muestra.get(0)}" value="${muestra.get(1)}">
                                                <select id="seleccionVeneno_${muestra.get(0)}" class="tipomuestra tipomuestra_${muestra.get(0)}" name="veneno_${muestra.get(0)}"
                                                        style='background-color: #fff;' required
                                                        oninvalid="setCustomValidity('Este campo es requerido')">
                                                    <option value=''></option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2">
                                    <label for="peso" class="control-label">*Peso (mg)</label>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="input-group">
                                                <input type="number" min="0" id="peso_${muestra.get(0)}" class="identificadores_${muestra.get(0)}" name="peso_${muestra.get(0)}" value="${muestra.get(2)}" required
                                                       required 
                                                       oninvalid="setCustomValidity('Este campo es requerido')"
                                                       oninput="setCustomValidity('')" onchange="setCustomValidity('')" > 
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-sm-2"> <br>
                                    <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarMuestra('${muestra.get(0)}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <button id="boton-muestra" type="button" onclick="agregarMuestra()" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Veneno</button>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Inóculo</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>