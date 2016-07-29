<%-- 
    Document   : Formulario
    Created on : Abr 3, 2015, 10:58:00 AM
    Author     : Bpga
--%>



<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-Principal" class="form-horizontal" autocomplete="off" method="post" action="Catalogo_PT">
  <div class="row">
    <input hidden="true" name="id_catalogo_pt" value="${catalogo_pt.getId_catalogo_pt()}">
    <input hidden="true" name="accion" value="${accion}">
    <div class="col-md-6">
      <label for="nombre" class="control-label">*Nombre</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" maxlenght="20" id="nombre" value="${catalogo_pt.getNombre()}"  name="nombre" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="descripcion" class="control-label">*Descripción</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input  type="text" id="descripcion" maxlength="100" value="${catalogo_pt.getDescripcion()}"  name="descripcion" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido ')"
                    onchange="setCustomValidity('')">      
          </div>
        </div>
      </div>
      <label for="vida_util" class="control-label">Vida útil en meses</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <input  type="number" id="vida_util" min="0" value="${catalogo_pt.getVida_util()}"  name="vida_util" class="form-control" >      
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="col-md-12">
    <p>
      Los campos marcados con * son requeridos.
    </p>  
    <div class="row">
      <div class="form-group">
        <div class="modal-footer">
          <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
          <c:choose>
            <c:when test= "${accion.equals('Editar')}">
              <button type="button" class="btn btn-primary" onclick="confirmar()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </c:when>
            <c:otherwise>
              <button type="button" class="btn btn-primary" onclick="confirmar()"><i class="fa fa-check-circle"></i> ${accion} Producto</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>

</form>

