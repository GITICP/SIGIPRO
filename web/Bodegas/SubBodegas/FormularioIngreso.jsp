<%-- 
    Document   : Formulario
    Created on : 08-mar-2015, 20:28:28
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="ingresoForm" class="form-horizontal" autocomplete="off" method="post" action="SubBodegas">

    <div class="row">
        <div class="col-md-6">
            <input id="id_sub_bodega" hidden="true" name="id_sub_bodega" value="${sub_bodega.getId_sub_bodega()}">
            <input id="accion" hidden="true" name="accion" value="${accion}">
            <label for="producto" class="control-label">* Producto</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionProducto" class="select2" style='background-color: #fff;' name="producto" required data-editar="${accionEditar}"
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:forEach items="${productos}" var="producto">
                                <option value=${producto.getId_producto()} data-cuarentena=${producto.isCuarentena()} data-perecedero="${producto.isPerecedero()}">${producto.getNombre()} (${producto.getCodigo_icp()})</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label id="label-fecha-vencimiento" for="fechaVencimiento" class="control-label">* Fecha de Vencimiento</label>
            <div id="campo-fecha-vencimiento" class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${ingreso.getFecha_vencimientoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fechaVencimiento" class="form-control sigiproDatePicker" name="fecha_vencimiento" data-date-format="dd/mm/yyyy" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>  
        </div>
        <div class="col-md-6">
            <label for="cantidad" class="control-label">* Cantidad</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="control-cantidad" hidden="true" name="control-cantidad" value="${ingreso.getCantidad()}">
                        <input id="cantidad" type="number" maxlength="45" placeholder="0" class="form-control campo-numero" name="cantidad" value="${ingreso.getCantidad()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" ><p id="errorCantidad" class="error-form"></p>
                    </div>
                </div>
            </div>
        </div>

    </div>
    <!-- Esta parte es la de los permisos de un rol -->
    <span>
        Los campos marcados con * son requeridos.
    </span>
    <br>
    <br>

    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Artículos</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form>