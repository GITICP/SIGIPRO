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
                    <!-- COLUMN FILTER DATA TABLE -->
                    <ul class="nav nav-tabs nav-tabs-right sigipro-nav-tabs">
                        <li class="active">
                            <a href="#inventarios" data-toggle="tab">Inventarios</a>
                        </li>
                        <li class="">
                            <a href="#informacion" data-toggle="tab">Información</a>
                        </li>
                    </ul>

                    <div class="tab-content sigipro-tab-content">
                        <div class="tab-pane fade active in" id="inventarios">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-th-large"></i> ${sub_bodega.getNombre()} </h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <c:choose>
                                            <c:when test="${agrupacion_por_producto == null}">
                                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=ver&id_sub_bodega=${sub_bodega.getId_sub_bodega()}&agrupar=true">Agrupar Por Producto</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=ver&id_sub_bodega=${sub_bodega.getId_sub_bodega()}">Agrupar Por Fecha de Vencimiento</a>
                                            </c:otherwise>
                                        </c:choose>

                                        <c:if test="${permisos_usuario.isEncargado() || sessionScope.listaPermisos.contains(70) || sessionScope.listaPermisos.contains(1)}">
                                            <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=historial&id_sub_bodega=${sub_bodega.getId_sub_bodega()}">Historial</a>
                                            <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=mover&id_sub_bodega=${sub_bodega.getId_sub_bodega()}">Mover</a>
                                        </c:if>
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
                                                <th>Producto</th>
                                                <th>Código</th>
                                                <th>Fecha de Vencimiento</th>
                                                <th>Cantidad</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${inventarios}" var="inventario">
                                                <tr id="${subBodega.getId_sub_bodega()}">
                                                    <td>${inventario.getProducto().getNombre()}</td>
                                                    <td>${inventario.getProducto().getCodigo_icp()}</td>
                                                    <td>${inventario.getFecha_vencimiento() != null ? " Vencimiento: ".concat(inventario.getFecha_vencimientoAsString()) : "Producto no perecedero"}</td>
                                                    <td>${inventario.getCantidad()}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <!-- END WIDGET TICKET TABLE -->
                            </div>
                        </div>
                        <div class="tab-pane fade" id="informacion">
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-th-large"></i> ${sub_bodega.getNombre()} </h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <c:if test="${permisos_usuario.isEncargado() || sessionScope.listaPermisos.contains(70) || sessionScope.listaPermisos.contains(1)}">
                                            <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bodegas/SubBodegas?accion=editar&id_sub_bodega=${sub_bodega.getId_sub_bodega()}">Editar</a>
                                        </c:if>
                                    </div>
                                </div>
                                ${mensaje}
                                <div class="widget-content">
                                    <table>
                                        <tr><td> <strong>Nombre de la Sub-bodega:</strong></td> <td>${sub_bodega.getNombre()} </td></tr>
                                        <tr><td> <strong>Sección a la que pertenece:</strong> <td>${sub_bodega.getSeccion().getNombre_seccion()} </td></tr>
                                        <tr><td> <strong>Usuario encargado:</strong> <td>${sub_bodega.getUsuario().getNombre_completo()} </td></tr>
                                    </table>
                                    <br>
                                    <br>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="widget widget-table">
                                                <div class="widget-header">
                                                    <h3><i class="fa fa-sign-in"></i> Usuarios con permiso de ingresar artículos a sub bodega</h3>
                                                </div>
                                                <div class="widget-content">
                                                    <table id="ingresos-sub-bodegas" class="table">
                                                        <thead>
                                                            <tr>
                                                                <th>Usuario</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${usuarios_ingresos}" var="usuario">
                                                                <tr id="ingreso-${usuario.getId_usuario()}">
                                                                    <td>${usuario.getNombre_completo()}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="widget widget-table">
                                                <div class="widget-header">
                                                    <h3><i class="fa fa-sign-out"></i> Usuarios con permiso de sacar artículos de sub bodega</h3>
                                                </div>
                                                <div class="widget-content">
                                                    <table id="egresos-sub-bodegas" class="table">
                                                        <thead>
                                                            <tr>
                                                                <th>Usuario</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${usuarios_egresos}" var="usuario">
                                                                <tr id="egreso-${usuario.getId_usuario()}">
                                                                    <td>${usuario.getNombre_completo()}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="widget widget-table">
                                                <div class="widget-header">
                                                    <h3><i class="fa fa-eye"></i> Usuarios con permiso de ver inventario de sub bodega</h3>
                                                </div>
                                                <div class="widget-content">
                                                    <table id="ver-sub-bodegas" class="table">
                                                        <thead>
                                                            <tr>
                                                                <th>Usuario</th>
                                                            </tr>
                                                        </thead>
                                                        <tbody>
                                                            <c:forEach items="${usuarios_ver}" var="usuario">
                                                                <tr id="ver-${usuario.getId_usuario()}">
                                                                    <td>${usuario.getNombre_completo()}</td>
                                                                </tr>
                                                            </c:forEach>
                                                        </tbody>
                                                    </table>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- END WIDGET TICKET TABLE -->
                            </div>
                        </div>
                    </div>

                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>
        </div>
    </jsp:attribute>

</t:plantilla_general>
