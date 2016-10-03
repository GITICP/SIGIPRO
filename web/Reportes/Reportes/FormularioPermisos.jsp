<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Reportes" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-8 ">
                    <ul class="breadcrumb">
                        <li>Reportes</li>
                        <li> 
                            <a href="/SIGIPRO/Reportes/Reportes?">Reportes</a>
                        </li>
                        <li class="active"> Modificar Permisos</li>

                    </ul>
                </div>
                <div class="col-md-4 ">
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
                            <h3><i class="fa fa-table"></i> Modificar Permisos de Reporte ${reporte.getNombre()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <form class="form-horizontal" autocomplete="off" method="post" action="Reportes">
                                <input type="hidden" name="accion" value="${accion}">
                                <input type="hidden" name="id_reporte" value="${reporte.getId_reporte()}">
                                <div class="row">
                                    <c:forEach items="${secciones}" var="seccion" begin="0" end="2">
                                        <div class="col-md-4">
                                            <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                <div class="widget-header">
                                                    <h3> ${seccion.getNombre_seccion()}</h3>
                                                    <div class="widget-header-toolbar">
                                                        <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                    </div>
                                                </div>
                                                <div class="widget-content">
                                                    <c:forEach items="${seccion.getUsuarios_seccion()}" var="usuario">
                                                        <div class="col-md-6">
                                                            <label class="fancy-checkbox">
                                                                <input type="checkbox" value="${usuario.getId_usuario()}" name="usuarios" ${(usuarios.contains(usuario.getId_usuario())) ? "checked" : ""}>
                                                                <span>${usuario.getNombre_completo()}</span>
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="row">
                                    <c:forEach items="${secciones}" var="seccion" begin="3" end="5">
                                        <div class="col-md-4">
                                            <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                <div class="widget-header">
                                                    <h3> ${seccion.getNombre_seccion()}</h3>
                                                    <div class="widget-header-toolbar">
                                                        <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                    </div>
                                                </div>
                                                <div class="widget-content">
                                                    <c:forEach items="${seccion.getUsuarios_seccion()}" var="usuario">
                                                        <div class="col-md-6">
                                                            <label class="fancy-checkbox">
                                                                <input type="checkbox" value="${usuario.getId_usuario()}" name="usuarios" ${(usuarios.contains(usuario.getId_usuario())) ? "checked" : ""}>
                                                                <span>${usuario.getNombre_completo()}</span>
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="row">
                                    <c:forEach items="${secciones}" var="seccion" begin="6" end="8">
                                        <div class="col-md-4">
                                            <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                <div class="widget-header">
                                                    <h3> ${seccion.getNombre_seccion()}</h3>
                                                    <div class="widget-header-toolbar">
                                                        <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                    </div>
                                                </div>
                                                <div class="widget-content">
                                                    <c:forEach items="${seccion.getUsuarios_seccion()}" var="usuario">
                                                        <div class="col-md-6">
                                                            <label class="fancy-checkbox">
                                                                <input type="checkbox" value="${usuario.getId_usuario()}" name="usuarios" ${(usuarios.contains(usuario.getId_usuario())) ? "checked" : ""}>
                                                                <span>${usuario.getNombre_completo()}</span>
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>
                                <div class="row">
                                    <c:forEach items="${secciones}" var="seccion" begin="9">
                                        <div class="col-md-4">
                                            <div class="widget widget-table cuadro-opciones usuarios-seccion">
                                                <div class="widget-header">
                                                    <h3> ${seccion.getNombre_seccion()}</h3>
                                                    <div class="widget-header-toolbar">
                                                        <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                                                    </div>
                                                </div>
                                                <div class="widget-content">
                                                    <c:forEach items="${seccion.getUsuarios_seccion()}" var="usuario">
                                                        <div class="col-md-6">
                                                            <label class="fancy-checkbox">
                                                                <input type="checkbox" value="${usuario.getId_usuario()}" name="usuarios" ${(usuarios.contains(usuario.getId_usuario())) ? "checked" : ""}>
                                                                <span>${usuario.getNombre_completo()}</span>
                                                            </label>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </div>


                        </div>

                        <div class="form-group">
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                            </div>
                        </div>
                        </form>

                    </div>
                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->

        </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Reportes/reportes.js"></script>
    </jsp:attribute>

</t:plantilla_general>
