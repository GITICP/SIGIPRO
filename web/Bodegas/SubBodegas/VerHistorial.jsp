<%-- 
    Document   : Ver
    Created on : Mar 08, 2015, 10:43:19 AM
    Author     : Boga
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                            <a href="/SIGIPRO/Bodegas/SubBodegas?">SubBodegas</a>
                        </li>
                        <li class="active"> ${sub_bodega.getNombre()} </li>
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
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-th-large"></i> ${sub_bodega.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${permisos_usuario.isEncargado() || permisos_usuario.isIngresar() || sessionScope.listaPermisos.contains(70) || sessionScope.listaPermisos.contains(1)}">
                                    <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=ingresar&id_sub_bodega=${sub_bodega.getId_sub_bodega()}">Ingresar</a>
                                </c:if>
                                <c:if test="${permisos_usuario.isEncargado() || permisos_usuario.isConsumir() || sessionScope.listaPermisos.contains(70) || sessionScope.listaPermisos.contains(1)}">
                                    <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=consumir&id_sub_bodega=${sub_bodega.getId_sub_bodega()}">Consumir</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Fecha y Hora</th>
                                        <th>Accion</th>
                                        <th>Producto</th>
                                        <th>Cantidad</th>
                                        <th>Usuario</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${sub_bodega.getHistorial()}" var="bitacora">
                                        <tr>
                                            <td>${bitacora.getFecha_accion()}</td>
                                            <%----%>
                                            <td>${bitacora.getAccion()}</td>
                                            <td>${bitacora.getProducto().getNombre()}</td>
                                            <td>${bitacora.getCantidad()}</td>
                                            <td>${bitacora.getUsuario().getNombre_completo()}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- END WIDGET TICKET TABLE -->
                    </div>
                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>
        </div>
    </jsp:attribute>

</t:plantilla_general>
