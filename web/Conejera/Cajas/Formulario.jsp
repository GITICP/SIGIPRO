<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 10:58:00 AM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formCaja" class="form-horizontal" autocomplete="off" method="post" action="Cajas">
    <div class="row">
        <input hidden="true" name="id_caja" value="${caja.getId_caja()}">
        <input hidden="true" name="accion" value="${accion}">
        <div class="col-md-6">
            <label for="numero" class="control-label">*Número de la Caja</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="number" min="1" class="form-control" id="numero" value="${caja.getNumero()}" name="numero" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido. Por favor introduzca un número válido ')"
                                onchange="setCustomValidity('')">      
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
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Caja</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

</form>

