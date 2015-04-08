<%-- 
    Document   : VerInoculo
    Created on : 25-mar-2015, 18:22:58
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
                            <a href="/SIGIPRO/Caballeriza/Caballo?">Caballos</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Caballo?accion=ver&id_caballo=${caballo.getId_caballo()}">${caballo.getNumero_microchip()}</a>
                        </li>

                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-book"></i> ${caballo.getNumero_microchip()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre del Caballo:</strong> <td> ${caballo.getNombre()} </td></tr>

                            </table>
                        </div>
                        <div class="widget widget-table">
                            <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Sangrías de Prueba del Caballo </h3>
                            </div>
                            <div class="widget-content">
                                <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                    <!-- Columnas -->
                                    <thead> 
                                        <tr>
                                            <th>Identificador</th>
                                            <th>Muestra </th>
                                            <th>Fecha de Recepción </th>
                                            <th>Hematrocito</th>
                                            <th>Hemoglobina</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${listaSangriasPruebas}" var="sangriap">
                                            <tr id ="${sangriap.getSangria_prueba().getId_sangria_prueba()}">
                                                <td>
                                                    <a href="/SIGIPRO/Caballeriza/SangriaPrueba?accion=ver&id_sangria_prueba=${sangriap.getSangria_prueba().getId_sangria_prueba()}">
                                                        <div style="height:100%;width:100%">
                                                            ${sangriap.getSangria_prueba().getId_sangria_prueba()}
                                                        </div>
                                                    </a>
                                                </td>
                                                <td>${sangriap.getSangria_prueba().getMuestra()}</td>
                                                <td>${sangriap.getSangria_prueba().getFecha_recepcion_muestraAsString()}</td>
                                                <td>${sangriap.getHematrocito()}</td>
                                                <td>${sangriap.getHemoglobina()}</td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
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
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
    </jsp:attribute>

</t:plantilla_general>

