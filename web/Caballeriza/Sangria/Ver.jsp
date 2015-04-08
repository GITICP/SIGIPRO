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
                            <h3><i class="fa fa-book"></i> Sangría ${sangria.getId_sangria()} </h3>
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
                                <tr><td> <strong>Identificador:</strong></td> <td>${sangria.getId_sangria()} </td></tr>
                                <tr><td> <strong>Responsable:</strong></td> <td>${sangria.getResponsable()} </td></tr>
                                <tr><td> <strong>Sangría de Prueba:</strong></td> <td>${sangria.getSangria_prueba().getId_sangria_prueba()}</td></tr>
                                <tr><td> <strong>Número de Informe de Control de Calidad:</strong></td> <td>${sangria.getNum_inf_cc()} </td></tr>
                                <tr><td> <strong>Número de Caballos:</strong></td> <td>${sangria.getCantidad_de_caballos()} </td></tr>
                                <tr><td> <strong>Sangre Total:</strong></td> <td>${sangria.getSangre_total()} </td></tr>
                                <tr><td> <strong>Hematocrito Promedio:</strong></td> <td>${sangria.getHematrocito_promedio()} </td></tr>
                                <tr><td> <strong>Peso de Plasma Total:</strong></td> <td>${sangria.getPeso_plasma_total()} </td></tr>
                                <tr><td> <strong>Volumen de Plasma Total:</strong></td> <td>${sangria.getVolumen_plasma_total()} </td></tr>
                                <tr><td> <strong>Plasma por Caballo:</strong></td> <td>${sangria.getPlasma_por_caballo()} </td></tr>
                                <tr><td> <strong>Potencia:</strong></td> <td>${sangria.getPotencia()} </td></tr>
                            </table>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Información de la Sangría </h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <c:choose>
                                            <c:when test="${sangria.getFecha_dia1() == null}">
                                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Sangria?accion=extraccion&id_sangria=${sangria.getId_sangria()}&dia=1">Registrar Extracción Día 1</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Sangria?accion=editarextraccion&id_sangria=${sangria.getId_sangria()}&dia=1">Editar Extracción Día 1</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${sangria.getFecha_dia2() == null}">
                                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Sangria?accion=extraccion&id_sangria=${sangria.getId_sangria()}&dia=2">Registrar Extracción Día 2</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Sangria?accion=editarextraccion&id_sangria=${sangria.getId_sangria()}&dia=2">Editar Extracción Día 2</a>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${sangria.getFecha_dia3() == null}">
                                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Sangria?accion=extraccion&id_sangria=${sangria.getId_sangria()}&dia=3">Registrar Extracción Día 3</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Sangria?accion=editarextraccion&id_sangria=${sangria.getId_sangria()}&dia=3">Editar Extracción Día 3</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="widget-content">
                                    <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                                        <thead>
                                            <tr>
                                                <th rowspan="2">Nombre y Número de Microchip</th>
                                                <!--<th rowspan="2">Hematocrito</th>-->
                                                <th colspan="3">Día 1 - ${sangria.getFecha_dia1AsString()}</th>
                                                <th colspan="3">Día 2 - ${sangria.getFecha_dia2AsString()}</th>
                                                <th colspan="3">Día 3 - ${sangria.getFecha_dia3AsString()}</th>
                                            </tr>
                                            <tr>
                                                <th class="campo-tabla-centrado">Sangre</th>
                                                <th class="campo-tabla-centrado">Plasma</th>
                                                <th class="campo-tabla-centrado">LAL</th>
                                                <th class="campo-tabla-centrado">Sangre</th>
                                                <th class="campo-tabla-centrado">Plasma</th>
                                                <th class="campo-tabla-centrado">LAL</th>
                                                <th class="campo-tabla-centrado">Sangre</th>
                                                <th class="campo-tabla-centrado">Plasma</th>
                                                <th class="campo-tabla-centrado">LAL</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:set var="sin_datos" value="-"></c:set>
                                            <c:forEach items="${sangria.getSangrias_caballos()}" var="sangria_caballo">
                                                <tr id="${caballo.getId_caballo()}">
                                                    <td>${sangria_caballo.getCaballo().getNombre()} (${sangria_caballo.getCaballo().getNumero_microchip()})</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getSangre_dia1() == 0) ? sin_datos : sangria_caballo.getSangre_dia1()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getPlasma_dia1() == 0) ? sin_datos : sangria_caballo.getPlasma_dia1()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getLal_dia1() == 0)    ? sin_datos : sangria_caballo.getLal_dia1()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getSangre_dia2() == 0) ? sin_datos : sangria_caballo.getSangre_dia2()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getPlasma_dia2() == 0) ? sin_datos : sangria_caballo.getPlasma_dia2()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getLal_dia2() == 0)    ? sin_datos : sangria_caballo.getLal_dia2()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getSangre_dia3() == 0) ? sin_datos : sangria_caballo.getSangre_dia3()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getPlasma_dia3() == 0) ? sin_datos : sangria_caballo.getPlasma_dia3()}</td>
                                                    <td class="campo-tabla-centrado">${(sangria_caballo.getLal_dia3() == 0)    ? sin_datos : sangria_caballo.getLal_dia3()}</td>
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
