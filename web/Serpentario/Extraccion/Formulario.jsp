<%-- 
    Document   : Formulario
    Created on : Mar 25, 2015, 4:30:30 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Extraccion">
    <div class="col-md-6">
      <input hidden="true" name="id_extraccion" value="${extraccion.getId_extraccion()}">
      <input hidden="true" name="accion" value="${accion}">
      
      <label for="genero" class="control-label">*Número de Extracción</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input type="text" maxlength="45" placeholder="Número de la Extracción. Ex: 01-2015-Coral" class="form-control" name="numero_extraccion" value="${extraccion.getNumero_extraccion()}"
                   required
                   oninvalid="setCustomValidity('Este campo es requerido')"
                   oninput="setCustomValidity('')" > 
          </div>
        </div>
      </div>
      <label for="especie" class="control-label">*Especie</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionEspecie" class="select2" name="especie"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${especies}" var="especie">
                                <option value=${especie.getId_especie()}>${especie.getGenero_especie()}</option>
                            </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
    <label for="usuarios" class="control-label">*Usuarios de la Extracción</label>
    <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionUsuarios" class="select2" name="usuarios_extraccion" multiple="multiple"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${usuarios}" var="usuario">
                                <option value=${usuario.getId_usuario()}>${usuario.getNombreCompleto()}</option>
                            </c:forEach>
                    </select>
                </div>
            </div>
        </div>
      <label for="cv" class="control-label">*Colección Viva</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionEspecie" class="select2" name="ingreso_cv"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <option value=1>De Colección Viva</option>
                            <option value=2>De Ingreso</option>
                    </select>
                </div>
            </div>
        </div>
    
    </div>
    <div class="col-md-12">
    <!-- Esta parte es la de los permisos de un rol -->
    <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>


  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Extraccion</button>
                </c:otherwise>
            </c:choose>    
    </div>
  </div>

    
</div>
</form>