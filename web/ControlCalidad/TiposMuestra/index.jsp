<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="ControlCalidad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>ControlCalidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/TiposMuestra?">Tipos de Muestra</a>
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
                            <h3><i class="fa fa-flask"></i> Tipos de Muestra </h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 561)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/ControlCalidad/TiposMuestra?accion=agregar">Agregar Tipo de Muestra</a>
                                </div>
                            </c:if>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Descripción</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${tipos_muestra}" var="tipo_muestra">

                                        <tr id ="${tipo_muestra.getId_tipo_muestra()}">
                                            <td>
                                                <a href="/SIGIPRO/ControlCalidad/TiposMuestra?accion=ver&id_tipo_muestra=${tipo_muestra.getId_tipo_muestra()}">
                                                    <div style="height:100%;width:100%">
                                                        ${tipo_muestra.getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${tipo_muestra.getDescripcion()}</td>
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

</t:plantilla_general>
