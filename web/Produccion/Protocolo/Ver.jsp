<%-- 
    Document   : Ver
    Created on : Jun 29, 2015, 4:48:27 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
                        <li> 
                            <a href="/SIGIPRO/Produccion/Formula_Maestra?">Fórmulas Maestras</a>
                        </li>
                        <li class="active"> ${formula_maestra.getNombre()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> ${formula_maestra.getNombre()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 635)}">
                                    <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la fórmula maestra" data-href="/SIGIPRO/Produccion/Formula_Maestra?accion=eliminar&id_formula_maestra=${formula_maestra.getId_formula_maestra()}">Eliminar</a>
                                </c:if>

                                <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 635)}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Formula_Maestra?accion=editar&id_formula_maestra=${formula_maestra.getId_formula_maestra()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong></td> <td>${formula_maestra.getNombre()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${formula_maestra.getDescripcion()} </td></tr>
                            </table>
                            <br>
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
