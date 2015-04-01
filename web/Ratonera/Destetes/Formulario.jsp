<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 10:58:00 AM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formDestete" class="form-horizontal" autocomplete="off" method="post" action="Destetes">
    <div class="row">
        <input hidden="true" name="id_destete" value="${destete.getId_destete()}">
        <input hidden="true" name="accion" value="${accion}">
        <div class="col-md-6">
            <label for="fechaDestete" class="control-label">*Fecha del Destete</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_destete" value="${destete.getFecha_destete_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_destete" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">      
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="numero_hembras" class="control-label">*Cantidad de Hembras</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input id="cantidadinput" placeholder="Número de hembras" type="number" min="0" class="form-control" name="numero_hembras" value="${destete.getNumero_hembras()}"required 
                               oninvalid="setCustomValidity('Por favor introduzca un número válido')"
                               oninput="setCustomValidity('')"> 
                    </div>
                </div>
            </div>  
            <label for="numero_machos" class="control-label">*Cantidad de Machos</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input id="cantidadinput" placeholder="Número de machos" type="number" min="0" class="form-control" name="numero_machos" value="${destete.getNumero_machos()}"required 
                               oninvalid="setCustomValidity('Por favor introduzca un número válido')"
                               oninput="setCustomValidity('')"> 
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
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Destete</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
</form>

