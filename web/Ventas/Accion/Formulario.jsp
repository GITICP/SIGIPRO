<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Accion">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_accion" value="${accion_venta.getId_accion()}">
      <input hidden="true" name="accion" value="${accion}">
            
            <label for="accion_accion" class="control-label"> *Acción</label>
            <!-- Acción -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="accion_accion" type="text" class="form-control" name="accion_accion" value="${accion_venta.getAccion()}" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Acción</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>