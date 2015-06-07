<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/SolicitudVeneno?">Solicitudes de Veneno</a>
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
              <h3><i class="fa fa-list-alt"></i> Solicitudes de Veneno </h3>
              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 350}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarSolicitud">Agregar Solicitud</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Número de Solicitud</th>
                    <th>Usuario Solicitante</th>
                    <th>Especie</th>
                    <th>Cantidad Solicitada (mg)</th>
                    <th>Fecha de Solicitud</th>
                    <th>Cantidad Entregada (mg)</th>
                    <th>Fecha de Entrega</th>
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
                        <a href="/SIGIPRO/Serpentario/SolicitudVeneno?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">
                          <div style="height:100%;width:100%">
                            ${solicitud.getId_solicitud()}
                          </div>
                        </a>
                      </td>
                      <td>${solicitud.getUsuario().getNombreCompleto()}</td>
                      <td>${solicitud.getEspecie().getGenero_especie()}</td>
                      <td>${solicitud.getCantidad()}</td>
                      <td>${solicitud.getFecha_solicitudAsString()}</td>
                      <c:choose>
                          <c:when test="${solicitud.getEntrega().getCantidad_entregada()!=0}">
                              <td>${solicitud.getEntrega().getCantidad_entregada()}</td>
                              <td>${solicitud.getEntrega().getFecha_entregaAsString()}</td>
                          </c:when>
                          <c:otherwise>
                              <td></td>
                              <td></td>
                          </c:otherwise>
                      </c:choose>
                      <td>${solicitud.getEstado()}</td>
                      <c:if test="${booladmin}">
                        <c:choose>
                          <c:when test="${solicitud.getEstado().equals('Solicitado')}">
                              <c:if test="${booladmin}">
                                <td>
                                  <a class="btn btn-primary btn-sm boton-accion confirmable" data-texto-confirmacion="aprobar esta solicitud" data-href="/SIGIPRO/Serpentario/SolicitudVeneno?accion=aprobar&id_solicitud=${solicitud.getId_solicitud()}">Aprobar</a>
                                  <a class="btn btn-danger btn-sm boton-accion rechazar-Modal" data-id='${solicitud.getId_solicitud()}' data-toggle="modal" data-target="#modalRechazarSolicitud">Rechazar</a>
                                </td>
                            </c:if>
                          </c:when>
                          <c:otherwise>
                            <c:choose>
                              <c:when test="${solicitud.getEstado().equals('Aprobado')}">
                                  <c:if test="${boolentrega}">
                                    <td>
                                        <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Serpentario/SolicitudVeneno?accion=entregar&id_solicitud=${solicitud.getId_solicitud()}">Entregar</a>  
                                        <a class="btn btn-danger btn-sm boton-accion anular-Modal" data-id="${solicitud.getId_solicitud()}" data-toggle="modal" data-target="#modalAnularSolicitud">Anular</a>  
                                    </td>
                                  </c:if>
                              </c:when>
                              <c:otherwise>
                                <td>
                                 <button class="btn btn-danger btn-sm boton-accion" disabled >Solicitud Completada</button>
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
        
<t:modal idModal="modalRechazarSolicitud" titulo="Rechazar Solicitud">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="rechazarSolicitud" autocomplete="off" method="post" action="SolicitudVeneno">
                <input hidden="true" name="accion" value="Rechazar">
                <input hidden="true" id='id_solicitud_rechazar' name='id_solicitud_rechazar' value="">
                <label for="observaciones" class="control-label">¿Razones por las cuales rechaza la solicitud?</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones" class="form-control" name="observaciones" ></textarea>
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Rechazar Solicitud</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      

    </jsp:attribute>
    <jsp:attribute name="scripts">
      <script src="/SIGIPRO/recursos/js/sigipro/SolicitudSerpentario.js"></script>
    </jsp:attribute>

  </t:plantilla_general>

<t:modal idModal="modalAnularSolicitud" titulo="Anular Solicitud">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="rechazarSolicitud" autocomplete="off" method="post" action="SolicitudVeneno">
                <input hidden="true" name="accion" value="Anular">
                <input hidden="true" id='id_solicitud_anular' name='id_solicitud_anular' value="">
                <label for="observaciones" class="control-label">¿Razones por las cuales anula la solicitud?</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones" class="form-control" name="observaciones" ></textarea>
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Anular Solicitud</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      
<t:modal idModal="modalAgregarSolicitud" titulo="Agregar Solicitud de Veneno">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarSolicitud" autocomplete="off" method="get" action="SolicitudVeneno">
                <input hidden="true" name="accion" value="Agregar">
                <label for="veneno" class="control-label">*Primero, elija el Tipo de Veneno que va a solicitar.</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <br>
                      <select id="seleccionEspecie" class="select2" name="id_veneno"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${venenos}" var="veneno">
                                        <option value=${veneno.getId_veneno()}>${veneno.getEspecie().getGenero_especie()}</option>
                                    </c:forEach>
                            </select>
                    </div>
                  </div>
                </div>
            
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Ir a Agregar Solicitud</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>