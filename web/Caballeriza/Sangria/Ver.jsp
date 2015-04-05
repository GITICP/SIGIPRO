<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Caballeriza</li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Sangria?">Sangría</a>
                        </li>
                        <li class="active"> ${sangria.getId_sangria()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> ${sangria.getId_sangria()} </h3>
                            <div class="btn-group widget-header-toolbar">

                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 58}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Sangria?accion=editar&id_sangria=${sangria.getId_sangria()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Campo:</strong></td> <td>${sangria.funcion()} </td></tr>
                            </table>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Caballos del Inóculo </h3>
                                </div>
                                <div class="widget-content">
                                    <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                                        <thead>
                                            <tr>
                                                <th>Nombre y Número de Microchip</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${caballos}" var="caballo">
                                                <tr id="${caballo.getId_caballo()}">
                                                    <td>${caballo.getNombre()} (${caballo.getNumero_microchip()})</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <c:forEach items="${[1,2,3]}" var="dia">
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-check"></i> Día ${dia} </h3>
                                    </div>
                                    <div class="widget-content">
                                        <table>
                                            <thead>
                                            <th>Caballo (Número Microchip)</th>
                                            </thead>
                                            <c:forEach items="${lista_caballos}" var="caballo">
                                                <tbody>

                                                </tbody>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>


                    </div>
                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>

</t:plantilla_general>
