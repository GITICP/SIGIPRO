<%-- 
    Document   : Formulario
    Created on : 08-mar-2015, 20:28:28
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-consumir-inventario-sub-bodega" class="form-horizontal" autocomplete="off" method="post" action="SubBodegas">

    <div class="row">
        <div class="col-md-6">
            <input id="accion" hidden="true" name="accion" value="${accion}">
            <input id="id-sub-bodega" hidden="true" name="id-sub-bodega" value="${sub_bodega.getId_sub_bodega()}">
            <label for="producto" class="control-label">* Producto</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccion-consumir-producto" class="select2" style='background-color: #fff;' name="id-inventario-sub-bodega" required data-editar="${accionEditar}"
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:forEach items="${inventarios}" var="inventario">
                                <option value="${inventario.getId_inventario_sub_bodega()}" data-cantidad-disponible=${inventario.getCantidad()}>
                                    ${inventario.getProducto().getNombre()} 
                                    (${inventario.getProducto().getCodigo_icp()}) - 
                                    ${inventario.getNumero_lote() != null ? "Lote: ".concat(inventario.getNumero_lote()) : "Sin lote"} - 
                                    ${inventario.getFecha_vencimiento() != null ? " Vencimiento: ".concat(inventario.getFecha_vencimientoAsString()) : "Producto no perecedero"}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <label id="label-fecha" for="fecha" class="control-label">* Fecha de Consumo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${helper_fechas.getFecha_hoyAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha" class="form-control sigiproDatePicker" name="fecha" data-date-format="dd/mm/yyyy" required
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
                        <input id="cantidad" maxlength="45" placeholder="0" class="form-control campo-numero" name="cantidad" value="${ingreso.getCantidad()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" ><p id="error-cantidad" class="error-form"></p>
                    </div>
                </div>
            </div>
            <label for="observaciones" class="control-label">Observaciones</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" name="observaciones" >${ingreso.getObservaciones()}</textarea>
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
