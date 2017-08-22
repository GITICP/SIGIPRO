<%-- 
    Document   : Formulario_Inventario
    Created on : Nov 14, 2015, 2:19:56 PM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-Principal" class="form-horizontal" autocomplete="off" method="post" action="Inventario_PT">
    <div class="row">
        <input hidden="true" name="id_inventario_pt" value="${inventario_pt.getId_inventario_pt()}">
        <input hidden="true" name="accion" value="${accion}">
        <div class="col-md-6">
            <label for="id_catalogo_pt" class="control-label">*Producto</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <select id="id_catalogo_pt" class="select2" name="id_catalogo_pt" required
                                oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                            <c:forEach items="${productos}" var="producto">
                                <c:choose>
                                    <c:when test="${inventario_pt.getProducto().getId_catalogo_pt() == producto.getId_catalogo_pt()}" >
                                        <option value=${producto.getId_catalogo_pt()} selected> ${producto.getNombre()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value=${producto.getId_catalogo_pt()}> ${producto.getNombre()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="id_lote" class="control-label">*Lote</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input id="id_lote" name="lote" required data-lotes='${lotes}' data-selected="${inventario_pt.getLote()}" />
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="fecha_vencimiento" class="control-label">*Fecha de Vencimiento</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_vencimiento" value="${inventario_pt.getFecha_vencimiento_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_vencimiento" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">      
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="cantidad" class="control-label">*Cantidad</label>
            <div class="form-group">
                <div class="col-md-12">
                    <div class="input-group">
                        <input  type="number" min="0" id="cantidad" value="${inventario_pt.getCantidad()}"  name="cantidad"  class="form-control"
                                oninvalid="setCustomValidity('Este campo es requerido')"
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
                        <c:when test= "${accion.equals('Editar_inventario')}">
                            <button type="button" class="btn btn-primary" onclick="confirmar()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="btn btn-primary" onclick="confirmar()"><i class="fa fa-check-circle"></i> Agregar Entrada de Inventario</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

</form>
