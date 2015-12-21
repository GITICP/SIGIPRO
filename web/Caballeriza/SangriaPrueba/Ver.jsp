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
                        <li class="active"> ${sangriap.getId_sangria_prueba()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> ${sangriap.getId_sangria_prueba()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Fecha:</strong></td><td>${sangriap.getFechaAsString()}</td></tr>
                                <tr><td> <strong>Responsable:</strong></td> <td>${sangriap.getUsuario().getId_usuario()} </td></tr>
                            </table>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Caballos de la Sangría de Prueba </h3>
                                </div>
                                <div class="widget-content">
                                    <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                                        <thead>
                                            <tr>
                                                <th>Nombre (Número) de Caballo</th>
                                                <th>Hematrocito</th>
                                                <th>Hemoglobina</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${sangriap.getLista_sangrias_prueba_caballo()}" var="sangria_prueba_caballo">
                                                <tr id="${sangria_prueba_caballo.getCaballo().getId_caballo()}">
                                                    <td>${sangria_prueba_caballo.getCaballo().getNombre()} (${sangria_prueba_caballo.getCaballo().getNumero()})</td> 
                                                    <td>${sangria_prueba_caballo.getHematocrito()}</td>
                                                    <td>${sangria_prueba_caballo.getHemoglobina()}</td>
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
