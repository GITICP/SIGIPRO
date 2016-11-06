<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Clientes">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_cliente" value="${cliente.getId_cliente()}">
      <input hidden="true" name="accion" value="${accion}">
            <label for="nombre" class="control-label"> *Nombre</label>
            <i id="nombreCorrecto" hidden class="fa fa-check-circle" style="color: green"></i>
            <i id="nombreInCorrecto" hidden class="fa fa-times-circle" style="color: red"></i>
            <span id="labelnombreInCorrecto" style="color: red"> ! Nombre ya existente, digite uno diferente</span>
            <!-- Nombre -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="nombre" type="text" class="form-control" maxlength="80" name="nombre" value="${cliente.getNombre()}" required
                            oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                            oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="cedula" class="control-label"> *Cédula</label>
            <i id="cedulaCorrecta" hidden class="fa fa-check-circle" style="color: green"></i>
            <i id="cedulaInCorrecta" hidden class="fa fa-times-circle" style="color: red"></i>
            <span for="cedulaIncorrecta" style="color: red" id="labelcedulaInCorrecta"> ! Cédula ya existente, digite una diferente</span>
            <!-- Cédula -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="cedula" type="text" class="form-control" maxlength="30" name="cedula" value="${cliente.getCedula()}" required
                            oninvalid="setCustomValidity('Debe ingresar una cédula. ')"
                            oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="tipo" class="control-label"> *Tipo</label>
            <!-- Tipo -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="tipo" class="select2" style='background-color: #fff;' name="tipo" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                             <c:choose>
                                <c:when test="${cliente.getTipo()} == Distribuidor" >
                                  <option value="Distribuidor" selected> Distribuidor</option>
                                  <option value="Particular"> Particular</option>
                                </c:when>
                                <c:otherwise>
                                  <option value="Distribuidor"> Distribuidor</option>
                                  <option value="Particular" selected> Particular</option>
                                </c:otherwise>
                             </c:choose>
                        </select>
                    </div>
                </div>
            </div>
            <label for="pais" class="control-label"> *Pais</label>
            <!-- Pais -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="pais" class="select2" style='background-color: #fff;' name="pais" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                             <c:choose>
                                <c:when test="${cliente.getPais() != null}" >
                                  <option value="${cliente.getPais()}" selected> ${cliente.getPais()}</option>
                                  <c:forEach items="${paises}" var="pais">
                                    <option value="${pais}"> ${pais}</option>    
                                  </c:forEach>
                                </c:when>
                                <c:otherwise>
                                  <c:forEach items="${paises}" var="pais">
                                      <c:choose>
                                        <c:when test="${pais.equals('Costa Rica')}" >
                                          <option value="Costa Rica" selected> Costa Rica</option>      
                                        </c:when>
                                        <c:otherwise>
                                          <option value="${pais}"> ${pais}</option>    
                                        </c:otherwise>
                                      </c:choose>
                                  </c:forEach>
                                </c:otherwise>
                             </c:choose>
                        </select>
                    </div>
                </div>
            </div>
            <!-- Tipo de Persona -->
            <label for="Tipo_persona" class="control-label"> *Tipo de Persona</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                         <c:choose>
                            <c:when test="${cliente.getPersona()} == Física" >
                                <select id="persona" class="select2" style='background-color: #fff;' name="persona" required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    oninput="setCustomValidity('')">
                                      <option value="Física" selected> Física</option>
                                      <option value="Jurídica"> Jurídica</option>
                                </select>
                            </c:when>
                            <c:when test="${cliente.getPersona()} == Jurídica" >
                                <select id="persona" class="select2" style='background-color: #fff;' name="persona" required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    oninput="setCustomValidity('')">
                                      <option value="Jurídica" selected> Jurídica</option>
                                      <option value="Física"> Física</option>
                                </select>
                            </c:when>
                            <c:otherwise>
                                  <select id="persona" class="select2" style='background-color: #fff;' name="persona" required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    oninput="setCustomValidity('')">
                                      <option value="" selected></option>
                                      <option value="Jurídica"> Jurídica</option>
                                      <option value="Física"> Física</option>
                                </select>
                            </c:otherwise>
                         </c:choose>
                    </div>
                </div>
            </div>
            <!-- Dirección  -->
            <label for="Dirección" class="control-label"> Dirección</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                          <c:when test="${accion == 'Agregar'}" >
                              <textarea style="width:100%" maxlength="350" name="direccion" id="direccion"></textarea>
                          </c:when>
                          <c:otherwise>
                            <textarea style="width:100%" maxlength="350" name="direccion" id="direccion">${cliente.getDireccion()}</textarea>
                          </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
    </div>   
    <c:choose>
        <c:when test= "${accion.equals('Editar')}">
            
        </c:when>
        <c:otherwise>
            <div class="col-md-6">
                <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-user"></i> Contacto Asociado </h3>
                </div>
                <div class="widget-content">
                <!-- Primer Contacto Asociado -->
                <label for="nombreContacto" class="control-label"> *Nombre del Contacto</label>
                    <!-- nombreContacto -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="nombreContacto" type="text" class="form-control" name="nombreContacto" value="" required
                                    oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                <label for="telefono" class="control-label"> *Teléfono del Contacto (formato 506 22225555)</label>
                    <!-- telefono -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="telefono" type="text" class="form-control" name="telefono" value="" maxlength="12" required
                                    oninvalid="setCustomValidity('Debe ingresar un telefono. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                <label for="telefono2" class="control-label"> Teléfono 2 del Contacto (formato 506 22225555)</label>
                    <!-- telefono2 -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="telefono2" type="text" class="form-control" name="telefono2" maxlength="12" value="" 
                                    oninvalid="setCustomValidity('Debe ingresar un telefono. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                <label for="correo_electronico" class="control-label"> *Correo Electrónico del Contacto</label>
                    <!-- correo_electronico -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="correo_electronico" type="email" class="form-control" name="correo_electronico" value="" required
                                    oninvalid="setCustomValidity('Debe ingresar un correo electrónico válido. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                <label for="correo_electronico2" class="control-label"> Correo Electrónico 2 del Contacto</label>
                    <!-- correo_electronico2 -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="correo_electronico2" type="email" class="form-control" name="correo_electronico2" value="" 
                                    oninvalid="setCustomValidity('Debe ingresar un correo electrónico válido. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                </div>
            </div>
          <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
            </div>
        </c:otherwise>
    </c:choose>                        
                            
  </div>

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Cliente</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>
                        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.js"></script>
                        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
                        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Cliente.js"></script>