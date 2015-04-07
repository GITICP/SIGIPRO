<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Walter
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bodegas</li>
                        <li> 
                            <a href="/SIGIPRO/Bodegas/Inventarios?">Inventarios</a>
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
                            <h3><i class="fa fa-barcode"></i> Inventarios </h3>

                            <c:set var="contienePermiso" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                <c:if test="${permiso == 1 || permiso == 11}">
                                    <c:set var="contienePermiso" value="true" />
                                </c:if>
                            </c:forEach>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Código del producto</th>
                                        <th>Producto</th>
                                        <th>Sección</th>
                                        <th>Stock Actual</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaInventarios}" var="inventarios">
                                        <tr id ="${inventarios.getId_inventario()}">
                                            <td>${inventarios.getId_inventario()}</td>
                                            <td>
                                                <a href="/SIGIPRO/Bodegas/Inventarios?accion=ver&id_inventario=${inventarios.getId_inventario()}">
                                                    <div style="height:100%;width:100%">
                                                        ${inventarios.getProducto().getNombre()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${inventarios.getSeccion().getNombre_seccion()}</td>
                                            <td>${inventarios.getStock_actual()}</td>
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
