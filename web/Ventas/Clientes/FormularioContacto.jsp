<%-- 
    Document   : FormularioContacto
    Created on : Jan 20, 2016, 2:57:15 PM
    Author     : Josue
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Contactos">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_contacto" value="${contacto.getId_contacto()}">
      <input hidden="true" name="id_cliente" value="${id_cliente}">
      <input hidden="true" name="accion" value="${accion}">
                <label for="nombreContacto" class="control-label"> *Nombre del Contacto</label>
                    <!-- nombreContacto -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="nombreContacto" type="text" class="form-control" name="nombreContacto" value="${contacto.getNombre()}" required
                                    oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                <label for="telefono" class="control-label"> *Teléfono del Contacto</label>
                    <!-- telefono -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="telefono" type="text" class="form-control" name="telefono" value="${contacto.getTelefono()}" required
                                    oninvalid="setCustomValidity('Debe ingresar un telefono. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                <label for="telefono2" class="control-label"> Teléfono 2 del Contacto</label>
                    <!-- telefono2 -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="telefono2" type="text" class="form-control" name="telefono2" value="${contacto.getTelefono2()}" 
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
                                <input id="correo_electronico" type="text" class="form-control" name="correo_electronico" value="${contacto.getCorreo_electronico()}" required
                                    oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
                <label for="correo_electronico2" class="control-label"> Correo Electrónico 2 del Contacto</label>
                    <!-- correo_electronico2 -->
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input id="correo_electronico2" type="text" class="form-control" name="correo_electronico2" value="${contacto.getCorreo_electronico2()}" 
                                    oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                                    oninput="setCustomValidity('')">
                            </div>
                        </div>
                    </div>
              <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Contacto</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>