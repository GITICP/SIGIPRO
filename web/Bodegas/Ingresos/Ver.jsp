<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.icp.sigipro.bodegas.modelos.Ingreso" %>

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Bodegas</li>
                        <li> 
                            <a href="/SIGIPRO/Bodegas/Ingresos?">Ingresos</a>
                        </li>
                        <li class="active"> Ingreso del ${ingreso.getFecha_ingreso()} de ${ingreso.getProducto().getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-sign-in"></i> Ingreso del ${ingreso.getFecha_ingreso()} de ${ingreso.getProducto().getNombre()} </h3>
                            <c:if test="${!(ingreso.getEstado() == 'Rechazado')}">
                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 28}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                            </c:if>
                            <c:if test="${contienePermisoEditar && ingreso.getSub_bodega() == null}">
                                <div class="btn-group widget-header-toolbar">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bodegas/Ingresos?accion=editar&id_ingreso=${ingreso.getId_ingreso()}">Editar</a>
                                </div>
                            </c:if>

                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre del Producto: </strong></td> <td>${ingreso.getProducto().getNombre()} </td></tr>
                                <tr><td> <strong>Sección del ingreso: </strong></td> <td>${ingreso.getSeccion().getNombre_seccion()} </td></tr>
                                <tr><td> <strong>Fecha de ingreso: </strong></td> <td>${ingreso.getFecha_ingresoAsString()} </td></tr>
                                <tr><td> <strong>Fecha de registro: </strong></td> <td>${ingreso.getFecha_registroAsString()} </td></tr>
                                <c:if test="${ingreso.getFecha_vencimiento() != null}">
                                    <tr><td> <strong>Fecha de vencimiento del producto: </strong></td> <td>${ingreso.getFecha_vencimientoAsString()} </td></tr>
                                </c:if>
                                <tr><td> <strong>Cantidad ingresada: </strong></td> <td>${ingreso.getCantidad()} </td></tr>
                                <tr><td> <strong>Estado: </strong></td> <td>${ingreso.getEstado()} </td></tr>
                                <tr><td> <strong>Precio: </strong></td> <td>${ingreso.getPrecio()} </td></tr>
                                <tr><td> <strong>Destino </strong></td> <td>${(ingreso.getSub_bodega() == null) ? "Bodega" : ingreso.getSub_bodega().getNombre()} </td></tr>
                            </table>
                            <br>
                            <!-- END WIDGET TICKET TABLE -->
                        </div>
                        <!-- /main-content -->
                    </div>
                    <!-- /main -->
                </div>
            </div>

        </jsp:attribute>

    </t:plantilla_general>
