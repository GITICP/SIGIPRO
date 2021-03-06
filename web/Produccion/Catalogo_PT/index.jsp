<%-- 
    Document   : index
    Created on : Abr 3, 2015, 11:27:57 AM
    Author     : Boga
--%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Catalogo de Producto Terminado" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Produccion</li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Catalogo_PT?">Catalogo de Producto Terminado</a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-8 ">
                    <div class="top-content">

                    </div>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-list-alt"></i> Catalogo de Producto Terminado</h3>

                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 606)}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Catalogo_PT?accion=agregar">Agregar Producto</a>
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
                                        <th>Descripcion</th>
                                        <th>Vida �til (meses)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${productos}" var="producto">

                                        <tr>
                                            <td>
                                                <a href="/SIGIPRO/Produccion/Catalogo_PT?accion=ver&id_catalogo_pt=${producto.getId_catalogo_pt()}">
                                                    <div style="height:100%;width:100%">
                                                        ${producto.getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${producto.getDescripcion()}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${producto.getVida_util()==0}" >
                                                        Sin especificar
                                                    </c:when>
                                                    <c:otherwise>
                                                        ${producto.getVida_util()}
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
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
