<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="ingresoForm" class="form-horizontal" autocomplete="off" method="post" action="Ingresos">

    <div class="row">

        <div class="col-md-6">
            <input id="hiddenID" hidden="true" name="id_ingreso" value="${ingreso.getId_ingreso()}" data-estado="${ingreso.getEstado()}">
            <label for="producto" class="control-label">* Producto</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${accion == 'Editar'}">
                                <input name="producto" type="hidden" value=${ingreso.getProducto().getId_producto()}>
                                <input id="seleccionProducto" class="form-control" data-perecedero="${ingreso.getProducto().isPerecedero()}" value="${ingreso.getProducto().getNombre()} (${ingreso.getProducto().getCodigo_icp()})" disabled>
                            </c:when>
                            <c:when test="${accion == 'Registrar'}">
                                <select id="seleccionProducto" class="select2" style='background-color: #fff;' value= ' ' name="producto" required data-editar="${accionEditar}"
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${productos}" var="producto">
                                        <option value=${producto.getId_producto()} data-cuarentena=${producto.isCuarentena()} data-perecedero="${producto.isPerecedero()}">${producto.getNombre()} (${producto.getCodigo_icp()})</option>
                                    </c:forEach>
                                </select>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="seccion" class="control-label">Sección</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${accion == 'Editar'}">
                                <input name="seccion" type="hidden" value="${ingreso.getSeccion().getID()}">
                                <input class="form-control" value="${ingreso.getSeccion().getNombre_seccion()}" disabled>
                            </c:when>
                            <c:when test="${accion == 'Registrar'}">
                                <select id="seleccionSeccion" class="select2" style='background-color: #fff;' name="seccion" required data-editar="${accionEditar}"
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <%--
                                    <c:if test="${ingreso.getSeccion() != null}">
                                      <option selected value=${ingreso.getSeccion().getID()} selected>${ingreso.getSeccion().getNombre_seccion()}</option>
                                    </c:if>--%>
                                    <c:forEach items="${secciones}" var="seccion">
                                        <option value=${seccion.getID()}>${seccion.getNombre_seccion()}</option>
                                    </c:forEach>
                                </c:when>
                            </c:choose>
                        </select>
                    </div>
                </div>
            </div>
            <label for="fechaIngreso" class="control-label">* Fecha de Ingreso</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${(ingreso.getFecha_ingreso() == null) ? helper.getFecha_hoy() : ingreso.getFecha_ingresoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fechaIngreso" class="form-control sigiproDatePicker" name="fechaIngreso" data-date-format="dd/mm/yyyy" required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label id="label-fecha-vencimiento" for="fechaVencimiento" class="control-label">* Fecha de Vencimiento</label>
            <div id="campo-fecha-vencimiento" class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${ingreso.getFecha_vencimientoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fechaVencimiento" class="form-control sigiproDatePicker" name="fechaVencimiento" data-date-format="dd/mm/yyyy" required
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
                        <input id="cantidad" maxlength="45" placeholder="0" class="form-control campo-numero" name="cantidad" value="${ingreso.getCantidad()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" ><p id="errorCantidad" class="error-form"></p>
                    </div>
                </div>
            </div>
            <label for="precio" class="control-label">* Precio (colones)</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="precio" maxlength="45" placeholder="0" class="form-control campo-numero" name="precio" value="${ingreso.getPrecio()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" ><p id="errorPrecio" class="error-form"></p>
                    </div>
                </div>
            </div>
            <label for="estado" class="control-label">* Estado</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="control-estado" hidden="true" name="control-estado" value="${ingreso.getEstado()}">
                        <label class="fancy-radio">
                            <input id="radio-disponible" type="radio" name="estado" value="Disponible"><span><i></i>Disponible</span> 
                        </label>
                        <label class="fancy-radio">
                            <input id="radio-cuarentena" type="radio" name="estado" value="En Cuarentena"><span><i></i>En cuarentena</span> 
                        </label>
                        <label class="fancy-radio">
                            <input type="radio" name="estado" value="No Disponible"><span><i></i>No disponible</span> 
                        </label>
                        <c:if test="${ingreso.getEstado() == 'Rechazado'}">
                            <label class="fancy-radio">
                                <input type="radio" name="estado" value="Rechazado" selected><span><i></i>Rechazado</span> 
                            </label>
                        </c:if>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Ingreso</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form>