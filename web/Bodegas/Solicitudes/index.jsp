
<%-- 
    Document   : index
    Created on : Jan 27, 2015, 2:08:13 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bodegas</li>
                        <li> 
                            <a href="/SIGIPRO/Bodegas/Solicitudes?">Solicitudes</a>
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
                            <h3><i class="fa fa-list-alt"></i> Solicitudes de Bodega </h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Prestamos">Préstamos</a>
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Solicitudes?accion=agregar">Agregar Nueva Solicitud</a>
                                <c:if test="${booladmin}">
                                  <a id="btn-entregar-solicitudes" 
                                     class="btn btn-warning btn-sm boton-accion"
                                     >Entregar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter" id="tablita">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Selección Entrega</th>
                                        <th>Número de Solicitud</th>
                                        <th>Usuario Solicitante</th>
                                        <th>Producto</th>
                                        <th>Cantidad</th>
                                            <c:if test="${booladmin}">
                                            <th> Cantidad Disponible</th>
                                            </c:if>
                                        <th>Fecha de Solicitud</th>
                                        <th>Estado</th>
                                            <c:if test="${booladmin}">
                                            <th> Cambio Estado</th>
                                            </c:if>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listaSolicitudes}" var="solicitud">

                                        <tr id ="${solicitud.getId_solicitud()}">
                                            <td>
                                              <c:choose>
                                                    <c:when test="${solicitud.getEstado().equals('Aprobada')}">
                                                         <input type="checkbox" name="entregar" value="${solicitud.getId_solicitud()}">
                                                    </c:when>
                                                    <c:otherwise>
                                                      <input type="checkbox" name="entregar" value="${solicitud.getId_solicitud()}" disabled="true">
                                                    </c:otherwise>
                                            </c:choose>
                                            
                                            </td>
                                            <td>
                                                <a href="/SIGIPRO/Bodegas/Solicitudes?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">
                                                    <div style="height:100%;width:100%">
                                                        ${solicitud.getId_solicitud()}
                                                    </div>
                                                </a>
                                            </td>
                                            <td>${solicitud.getUsuario().getNombreCompleto()} (${solicitud.getUsuario().getNombreSeccion()})</td>
                                            <td>${solicitud.getInventario().getProducto().getNombre()} (${solicitud.getInventario().getProducto().getCodigo_icp()})</td>
                                            <td>${solicitud.getCantidad()} (${solicitud.getInventario().getSeccion().getNombre_seccion()})</td>
                                            <c:if test="${booladmin}">
                                                <td>${solicitud.getInventario().getStock_actual()}</td>
                                            </c:if>
                                            <td>${solicitud.getFecha_solicitud()}</td>
                                            <td>${solicitud.getEstado()}</td>
                                            <c:if test="${booladmin}">
                                                <c:choose>
                                                    <c:when test="${solicitud.getEstado().equals('Pendiente')}">
                                                        <td>
                                                            <a class="btn btn-primary btn-sm boton-accion confirmableAprobar" data-texto-confirmacion="aprobar esta solicitud" data-href="/SIGIPRO/Bodegas/Solicitudes?accion=aprobar&id_solicitud=" onclick="AprobarSolicitud(${solicitud.getId_solicitud()})">Aprobar</a>
                                                            <a class="btn btn-danger btn-sm boton-accion" onclick="RechazarSolicitud(${solicitud.getId_solicitud()})">Rechazar</a>
                                                        </td>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${solicitud.getEstado().equals('Aprobada')}">
                                                                <td>
                                                                    <a class="btn btn-danger btn-sm boton-accion confirmableCerrar" data-texto-confirmacion="cerrar esta solicitud" data-href="/SIGIPRO/Bodegas/Solicitudes?accion=cerrar&id_solicitud=" onclick="CerrarSolicitud(${solicitud.getId_solicitud()})">Cerrar</a>
                                                                </td>
                                                            </c:when>
                                                            <c:otherwise>
                                                                <td>
                                                                    <button class="btn btn-danger btn-sm boton-accion" disabled >Solicitud Completada</a>
                                                                </td>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:if>
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
        </div>

        <t:modal idModal="ModalAutorizar" titulo="Firma de Retiro">

            <jsp:attribute name="form">
                <form class="form-horizontal" id="form_modalautorizar" data-show-auth="${show_modal_auth}" method="post" action="Solicitudes">
                    <input hidden="true" name="ids-por-entregar" id="ids-por-entregar" >
                    <input hidden="true" name="accionindex" id="accionindex" value="accionindex">
                    ${mensaje_auth}
                    <table class="tabla-modal">
                        <tr>
                            <td><label for="usr" class="control-label">Usuario</label></td>
                            <td><input class="form-control" type="text" id="usr"  name="usr" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')"></td>
                        </tr>
                        <tr>
                            <td><label for="passw" class="control-label">Contraseña</label></td>
                            <td>
                                <input type="password" class="form-control" id="passw" name="passw" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                                <p id='mensajeValidación' style='color:red;'><p>
                            </td>
                        </tr>
                        
                    </table>
                    <hr>
                    <h4> Detalle de Despacho</h4>
                    <hr>
                    <table class="tabla-modal" id="tabla_informacion">
                      <thead> 
                          <th>Id Solicitud</th>
                          <th>Usuario Solicitante</th>
                          <th>Producto</th>
                          <th>Cantidad</th>
                      </thead>
                      <tbody>
                      </tbody>
                    </table>
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aceptar</button>
                        </div>
                    </div>
                </form>


            </jsp:attribute>
           </t:modal>
        <t:modal idModal="ModalRechazar" titulo="Observaciones">

            <jsp:attribute name="form">
                <h5> ¿Está seguro que desea rechazar esta solicitud? De ser así por favor indique las observaciones: </h5>
                <form class="form-horizontal" id="form_modalrechazar" data-show-auth="${show_modal_auth}" method="post" action="Solicitudes">
                    <input hidden="true" name="id_solicitud_rech" id="id_solicitud_rech" >
                    <input hidden="true" name="accionindex" id="accionindex" value="accionindex_rechazar">
                    ${mensaje_auth}
                    <label for="observaciones" class="control-label">Observaciones</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group" style="display:table;">
                                <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" id="observaciones" name="observaciones" >${ubicacion.getDescripcion()}</textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aceptar</button>
                        </div>
                    </div>
                </form>


            </jsp:attribute>

        </t:modal>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/solicitudes.js"></script>
    </jsp:attribute>

</t:plantilla_general>
