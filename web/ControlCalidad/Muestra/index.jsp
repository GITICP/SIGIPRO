<%-- 
    Document   : index
    Created on : Aug 2, 2015, 9:10:06 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Control de Calidad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Muestra?">Muestras</a>
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
                            <h3><i class="fa fa-gears"></i> Muestras </h3>
                            <c:set var="contienePermiso" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                <c:if test="${permiso == 1 || permiso == 560}">
                                    <c:set var="contienePermiso" value="true" />
                                </c:if>
                            </c:forEach>
                            <c:if test="${contienePermiso}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion descartar-Modal" data-toggle="modal" data-target="#modalDescartarMuestras">Descartar</a>
                                </div>
                            </c:if>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-no-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Selección</th>
                                        <th>Identificador</th>
                                        <th>Tipo de Muestra</th>
                                        <th>Fecha de Descarte Estimada</th>
                                        <th>Estado</th>
                                        <th>Fecha de Descarte Real</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaMuestras}" var="muestra">

                                        <tr id ="${muestra.getId_muestra()}">
                                            <td>
                                                <input type="checkbox" name="descartar" value="${muestra.getId_muestra()}">
                                            </td>
                                            <td>${muestra.getIdentificador()}</td>
                                            <td>${muestra.getTipo_muestra().getNombre()}</td>
                                            <td>${muestra.getFecha_descarte_estimadaAsString()}</td>
                                            <c:choose>
                                                <c:when test="${muestra.getFecha_descarte_real()==null}">
                                                    <td>No descartada</td>
                                                    <td></td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td>Descartada</td>
                                                    <td>${muestra.getFecha_descarte_realAsString()}</td>
                                                </c:otherwise>
                                            </c:choose>
                                            
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
        </div>

    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Muestras.js"></script>
    </jsp:attribute>
</t:plantilla_general>

<t:modal idModal="modalDescartarMuestras" titulo="Descartar Muestras">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="descartarMuestras" autocomplete="off" method="post" action="Muestra">
                <input hidden="true" name="accion" value="Descartar">
                <input hidden="true" id='id_muestras' name='id_muestras_descartar' value="">
                <label for="observaciones" class="control-label">Fecha de Descarte Real</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepickerDescarte" class="form-control sigiproDatePicker" name="fecha_descarte_real" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido y no pueden ser fechas futuras. ')"
                                   onchange="setCustomValidity('')">
                        </div>
                    </div>
                </div>


                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Descartar Muestras</button>            </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>