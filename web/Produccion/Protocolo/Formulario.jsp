<%-- 
    Document   : Formulario
    Created on : Jun 29, 2015, 4:42:37 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Protocolo">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_protocolo" value="${protocolo.getId_protocolo()}">
            <input hidden="true" name="accion" value="${accion}">
            <input hidden="true" name="orden" id='orden' value="">
            <input hidden="true" id='listaPasos' name='listaPasos' value='${pasos}'>
            <label for="nombre" class="control-label">* Nombre</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre del Protocolo de Producción" class="form-control" name="nombre" value="${protocolo.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>

            <label for="formula" class="control-label">*Fórmula Maestra</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionFormula" class="select2" name="id_formula_maestra"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${formulas_maestras}" var="formula">
                                <c:choose>
                                    <c:when test="${formula.getId_formula_maestra() == protocolo.getFormula_maestra().getId_formula_maestra()}" >
                                        <option value=${formula.getId_formula_maestra()} selected> ${formula.getNombre()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value=${formula.getId_formula_maestra()}>${formula.getNombre()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>

                    </div>
                </div>
            </div>
            <label for="formula" class="control-label">Producto Terminado</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionFormula" class="select2" name="id_catalogo_pt"
                                style='background-color: #fff;' 
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${catalogo_pt}" var="pt">
                                <c:choose>
                                    <c:when test="${pt.getId_catalogo_pt() == protocolo.getProducto().getId_catalogo_pt()}" >
                                        <option value=${pt.getId_catalogo_pt()} selected> ${pt.getNombre()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value=${pt.getId_catalogo_pt()}>${pt.getNombre()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>

                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="descripcion" class="control-label">Descripción</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${protocolo.getDescripcion()}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <!-- Esta parte es la de los permisos de un rol -->
        <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </div>
    <div class='row'>
        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-flask"></i> Pasos de Protocolo</h3>
                </div>
                <div class="widget-content">
                    <h5>El orden mostrado será la secuencia de pasos dentro del protocolo. Los pasos se pueden arrastrar para colocarlos en el orden requerido.</h5>
                    <div class="campos sortable" id="sortable">


                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <button type="button" onclick="agregarPaso()" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Paso de Protocolo</button>
                            </div>
                        </div>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Protocolo</button>
                </c:otherwise>
            </c:choose>    </div>
    </div>


</form>
