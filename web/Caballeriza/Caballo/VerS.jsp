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
                            <a href="/SIGIPRO/Caballeriza/Caballo?accion=ver&id_caballo=${caballo.getId_caballo()}">Sangrías Caballo número ${caballo.getNumero()}</a>
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
                            <h3><i class="sigipro-horse-1"></i>Sangrías Caballo número ${caballo.getNumero()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre del Caballo:</strong> <td> ${caballo.getNombre()} </td></tr>
                                <tr><td> <strong>Número de Caballo</strong> <td>${caballo.getNumero()} </td></tr>
                            </table>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Sangrías del Caballo </h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Identificador</th>
                                                <th>Fecha de Inicio</th>
                                                <th>Sangre Día 1 </th>
                                                <th>Plama Día 1 </th>
                                                <th>LaL Día 1</th>
                                                <th>Sangre Día 2 </th>
                                                <th>Plama Día 2 </th>
                                                <th>LaL Día 2</th>
                                                <th>Sangre Día 3 </th>
                                                <th>Plama Día 3 </th>
                                                <th>LaL Día 3</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${listaSangrias}" var="sangria">
                                                <tr id ="${sangria.getSangria().getId_sangria()}">
                                                    <td>
                                                        <a href="/SIGIPRO/Caballeriza/Sangria?accion=ver&id_sangria=${sangria.getSangria().getId_sangria()}">
                                                            <div style="height:100%;width:100%">
                                                                ${sangria.getSangria().getId_sangria_especial()}
                                                            </div>
                                                        </a>
                                                    </td>
                                                    <td>${sangria.getSangria().getFecha_dia1AsString()}</td>
                                                    <td>${sangria.getSangre_dia1()}</td>
                                                    <td>${sangria.getPlasma_dia1()}</td>
                                                    <td>${sangria.getLal_dia1()}</td>
                                                    <td>${sangria.getSangre_dia2()}</td>
                                                    <td>${sangria.getPlasma_dia2()}</td>
                                                    <td>${sangria.getLal_dia2()}</td>
                                                    <td>${sangria.getSangre_dia3()}</td>
                                                    <td>${sangria.getPlasma_dia3()}</td>
                                                    <td>${sangria.getLal_dia3()}</td>
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
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
    </jsp:attribute>

</t:plantilla_general>

