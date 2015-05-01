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
                            <a href="/SIGIPRO/Caballeriza/Caballo?accion=ver&id_caballo=${caballo.getId_caballo()}">Caballo número ${caballo.getNumero()}</a>
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
                            <h3><i class="fa fa-book"></i> Caballo número ${caballo.getNumero()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table>
                                <tr><td> <strong>Nombre del Caballo:</strong> <td> ${caballo.getNombre()} </td></tr>
                                <tr><td> <strong>Número de Caballo</strong> <td>${caballo.getNumero()} </td></tr>
                            </table>
                        </div>
            <div class="widget widget-table">
                            <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Inóculos del Caballo </h3>
                            </div>
                            <div class="widget-content">
                                <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Identificador</th>
                                        <th>Fecha del Inóculo</th>
                                        <th>M.n.n</th>
                                        <th>B.a -A</th>
                                        <th>B.a -P</th>
                                        <th>C.d.d</th>
                                        <th>L.m.s</th>
                                        <th>Tetox</th>
                                        <th>Otro</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaInoculos}" var="inoculo">
                                        <tr id ="${inoculo.getId_inoculo()}">
                                            <td>
                                                <a href="/SIGIPRO/Caballeriza/Inoculo?accion=ver&id_inoculo=${inoculo.getId_inoculo()}">
                                                    <div style="height:100%;width:100%">
                                                        ${inoculo.getId_inoculo()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${inoculo.getFechaAsString()}</td>
                                            <td>${inoculo.getMnn()}</td>
                                            <td>${inoculo.getBaa()}</td>
                                            <td>${inoculo.getBap()}</td>
                                            <td>${inoculo.getCdd()}</td>
                                            <td>${inoculo.getLms()}</td>
                                            <td>${inoculo.getTetox()}</td>
                                            <td>${inoculo.getOtro()}</td>
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

