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
                            <a href="/SIGIPRO/Caballeriza/SangriaPrueba?">Sangría de Prueba</a>
                        </li>
                        <li class="active"> Sangría de Prueba ${sangriap.getId_sangria_prueba_especial()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> Sangría de Prueba ${sangriap.getId_sangria_prueba_especial()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Fecha:</strong></td><td>${sangriap.getFechaAsString()}</td></tr>
                                <tr><td> <strong>Responsable:</strong></td> <td>${sangriap.getUsuario().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Número de Informe de CC:</strong></td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${sangriap.getInforme() == null}">
                                                Sin informe.
                                            </c:when>
                                            <c:otherwise>
                                                <a href="/SIGIPRO/ControlCalidad/Informe?accion=ver&id_solicitud=${sangriap.getInforme().getSolicitud().getId_solicitud()}">${sangriap.getInforme().getSolicitud().getNumero_solicitud()}</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Caballos de la Sangría de Prueba </h3>
                                </div>
                                <div class="widget-content">
                                    <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter" data-filas-defecto="50">
                                        <thead>
                                            <tr>
                                                <th>Nombre (Número) de Caballo</th>
                                                <th>Hematocrito</th>
                                                <th>Hemoglobina</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${sangriap.getLista_sangrias_prueba_caballo()}" var="sangria_prueba_caballo">
                                                <tr id="${sangria_prueba_caballo.getCaballo().getId_caballo()}">
                                                    <td>${sangria_prueba_caballo.getCaballo().getNombre()} (${sangria_prueba_caballo.getCaballo().getNumero()})</td>
                                                    <td>${(sangria_prueba_caballo.getHematocrito() == 0.0) ? "Sin resultado registrado." : sangria_prueba_caballo.getHematocrito()}</td>
                                                    <td>${(sangria_prueba_caballo.getHemoglobina() == 0.0) ? "Sin resultado registrado." : sangria_prueba_caballo.getHemoglobina()}</td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
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
