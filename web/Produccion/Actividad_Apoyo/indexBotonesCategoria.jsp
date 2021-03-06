<%-- 
    Document   : index
    Created on : Jun 29, 2015, 4:39:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Producción" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Producción</li>
                        <li class="active"> 
                            Categorías de Actividades de Apoyo
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
                            <h3><i class="fa fa-gears"></i> Categorías de Actividades de Apoyo </h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Categoria_AA?">Index General</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <c:forEach items="${listaCategorias}" var="categoria">
                                <div class="col-md-2 widget-content">
                                    <a style="height:200px;
                                       width:200px;
                                       font-size: 20px;
                                       position:relative;
                                       text-align: center;
                                       display:table-cell;
                                       vertical-align:middle;
                                       color:#fff;
                                       background-color:#3071a9;
                                       border-color:#285e8e;" href="/SIGIPRO/Produccion/Actividad_Apoyo?accion=indexactividades&id_categoria_aa=${categoria.getId_categoria_aa()}">
                                        ${categoria.getNombre()}
                                    </a>

                                </div>
                            </c:forEach>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 630)}">

                                <div class="col-md-2 widget-content">
                                    <a style="height:200px;
                                       width:200px;
                                       font-size: 20px;
                                       position:relative;
                                       text-align: center;
                                       display:table-cell;
                                       vertical-align:middle;
                                       color:#fff;
                                       background-color:#6e9bc2;
                                       border-color:#285e8e;" href="/SIGIPRO/Produccion/Categoria_AA?accion=agregar">
                                        Agregar Nueva Categoría
                                    </a>

                                </div>
                            </c:if>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>
        </div>
    </jsp:attribute>
</t:plantilla_general>
