<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : ld.conejo
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Serpentario</li>
                        <li> 
                            <a href="/SIGIPRO/Serpentario/Lote?">Lotes de Veneno</a>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-tint"></i> Lotes de Veneno </h3>

                            <c:set var="contienePermiso" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                <c:if test="${permiso == 1 || permiso == 330}">
                                    <c:set var="contienePermiso" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${contienePermiso}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarLote">Agregar Lote</a>
                                </div>
                            </c:if>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Lote</th>
                                        <th>Especie</th>
                                        <th>Cantidad Actual (G)</th>
                                        <th>Cantidad Original (G)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaLotes}" var="lote">

                                        <tr id ="${lote.getId_lote()}">
                                            <td>
                                                <a href="/SIGIPRO/Serpentario/Lote?accion=ver&id_lote=${lote.getId_lote()}">
                                                    <div style="height:100%;width:100%">
                                                        ${lote.getNumero_lote()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${lote.getEspecie().getGenero_especie()}</td>
                                            <td>${lote.getCantidad_actual()}</td>
                                            <td>${lote.getCantidad_original()}</td>
                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->

        </jsp:attribute>

    </t:plantilla_general>

    <t:modal idModal="modalAgregarLote" titulo="Agregar Lote de Veneno">
        <jsp:attribute name="form">
            <div class="widget-content">
                <form class="form-horizontal" id="agregarLote" autocomplete="off" method="get" action="Lote">
                    <input hidden="true" name="accion" value="Agregar">
                    <label for="observaciones" class="control-label">*Primero, elija el Tipo de Veneno del Lote a agregar</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <select id="seleccionEspecie" class="select2" name="id_veneno"
                                        style='background-color: #fff;' required
                                        oninvalid="setCustomValidity('Este campo es requerido')"
                                        onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${venenos}" var="veneno">
                                        <option value=${veneno.getId_veneno()}>${veneno.getEspecie().getGenero_especie()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Ir a Agregar Lote</button>            </div>
                    </div>
                </form>
            </div>

        </jsp:attribute>

    </t:modal>