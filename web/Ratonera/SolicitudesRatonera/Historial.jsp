<%-- 
    Document   : index
    Created on : Mar 26, 2015, 4:02:57 PM
    Author     : Amed
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ratonera" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Ratonera</li>
                        <li> 
                            <a href="/SIGIPRO/Ratonera/SolicitudesRatonera?">Solicitudes Ratonera</a>
                        </li>
                    </ul>
                </div>
                <div class="col-md-8 ">
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
                            <h3><i class="fa fa-list-alt"></i> Solicitudes Ratonera </h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Ratonera/SolicitudesRatonera?">Volver</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter" data-columna-filtro="7">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Número de Solicitud</th>
                                        <th>Fecha de Solicitud</th>
                                        <th>Usuario Solicitante</th>
                                        <th>Número de Animales</th>
                                        <th>Peso</th>
                                        <th>Número de Cajas</th>
                                        <th>Estado</th>
                                        <th>Fecha necesita </th>
                                        <th>Fecha de Última Entrega</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaSolicitudesRatonera}" var="solicitud">

                                        <tr id ="${solicitud.getId_solicitud()}">
                                            <td>
                                                <a href="/SIGIPRO/Ratonera/SolicitudesRatonera?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">
                                                    <div style="height:100%;width:100%">
                                                        Número ${solicitud.getId_solicitud()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${solicitud.getFecha_solicitud_S()}</td>
                                            <td>${solicitud.getUsuario_solicitante().getNombreCompleto()}</td>
                                            <td>${solicitud.getNumero_animales()}</td>
                                            <td>${solicitud.getPeso_requerido()}</td>
                                            <td>${solicitud.getNumero_cajas()}</td>
                                            <td>${solicitud.getEstado()}</td>
                                            <td>${solicitud.getFecha_necesita_S()}</td>
                                            <td>${(solicitud.getEntrega() != null) ? solicitud.getEntrega().getFecha_entregaAsString() : "No aplica."}</td>
                                        </tr>

                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->

        </jsp:attribute>

        <jsp:attribute name="scripts">
            <script src="/SIGIPRO/recursos/js/sigipro/solicitudesRatonera.js"></script>
        </jsp:attribute>

    </t:plantilla_general>

