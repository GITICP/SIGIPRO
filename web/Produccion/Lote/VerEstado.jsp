<%-- 
    Document   : VerEstado
    Created on : Mar 24, 2016, 9:58:44 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <li class="active">Ver Estado Actual </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Estado del Lote Actual</h3>
                            <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 660)}">
                                <div class="btn-group widget-header-toolbar">

                                </div>
                            </c:if>
                        </div>
                        ${mensaje}
                        <div class="widget-content row">
                            <c:forEach items="${listaLotes}" var="lote">
                                <div class="col-sm-12">
                                    <div class="square-lote center-block">
                                        ${lote.getNombre()}
                                    </div>
                                    <c:forEach items="${lote.getRespuestas()}" var="respuesta">
                                        <div class="center-block">
                                            <c:choose>
                                                <c:when test="${respuesta.getEstado()==1}">
                                                    <div class="square-deshabilitado center-block">
                                                        ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} <i class="fa fa-lock"></i>
                                                    </div>
                                                </c:when>
                                                <c:when test="${respuesta.getEstado()==2}">
                                                    <div class="square-deshabilitado center-block">
                                                        ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} <i class="fa fa-lock"></i> <i class="fa fa-pause"></i>
                                                    </div>
                                                </c:when>
                                                <c:when test="${respuesta.getEstado()==3}">
                                                    <div class="square-habilitado center-block">
                                                        ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} <i class="fa fa-unlock"></i>
                                                    </div>
                                                </c:when> 
                                                <c:when test="${respuesta.getEstado()==4}">
                                                    <div class="square-habilitado center-block">
                                                        ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} <i class="fa fa-unlock"></i> <i class="fa fa-pause"></i>
                                                    </div>
                                                </c:when> 
                                                <c:when test="${respuesta.getEstado()==5}">
                                                    <div class="square-incompleto center-block">
                                                        ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} <i class="fa fa-warning"></i>
                                                    </div>
                                                </c:when> 
                                                <c:when test="${respuesta.getEstado()==6}">
                                                    <div class="square-aprobacion center-block">
                                                        ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} <i class="fa fa-pause"></i>
                                                    </div>
                                                </c:when> 
                                                <c:when test="${respuesta.getEstado()==7}">
                                                    <div class="square-completo center-block">
                                                        ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} <i class="fa fa-check"></i>
                                                    </div>
                                                </c:when>
                                            </c:choose>

                                        </div>
                                    </c:forEach>
                                    <hr>
                                </div>
                            </c:forEach>
                            <div class="col-sm-12">
                                <table class="tabla-ver">
                                    <tr><td> <strong><i class="fa fa-check"></i></strong></td> <td>Completado</td></tr>
                                    <tr><td> <strong><i class="fa fa-pause"></i></strong></td> <td>Requiere aprobaciones</td></tr>
                                    <tr><td> <strong><i class="fa fa-warning"></i></strong></td> <td>Incompleto</td></tr>
                                    <tr><td> <strong><i class="fa fa-unlock"></i></strong></td> <td>Habilitado</td></tr>
                                    <tr><td> <strong><i class="fa fa-lock"></i></strong></td> <td>Deshabilitado</td></tr>
                                </table>
                            </div>
                        </div>

                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>
</t:plantilla_general>
