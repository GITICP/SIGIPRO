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
                            <table>
                                <tr><td> <strong>Muestra:</strong></td> <td>${sangriap.getMuestra()} </td></tr>
                                <tr><td> <strong>Solicitud N°:</strong></td> <td>${sangriap.getNum_solicitud()} </td></tr>
                                <tr><td> <strong>Informe N°:</strong></td> <td>${sangriap.getNum_informe()} </td></tr>
                                <tr><td> <strong>Fecha de recepción de la muestra:</strong></td> <td>${sangriap.getFecha_recepcion_muestraAsString()} </td></tr>
                                <tr><td> <strong>Fecha del informe:</strong></td> <td>${sangriap.getFecha_informeAsString()} </td></tr>
                                <tr><td> <strong>Responsable:</strong></td> <td>${sangriap.getResponsable()} </td></tr>
                                <tr><td> <strong>Inóculo:</strong></td> <td>${sangriap.getInoculo().getId_inoculo()} </td></tr>                                
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
