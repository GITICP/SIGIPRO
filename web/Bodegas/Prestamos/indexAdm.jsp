
<%-- 
    Document   : indexAdm
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
              <a href="/SIGIPRO/Bodegas/Prestamos?">Préstamos</a>
            </li>
            <li class="active"> Administrar los Préstamos de Sección</li>

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
              <h3><i class="fa fa-barcode"></i> Administrar Préstamos</h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Prestamos">Volver a Préstamos</a>
               </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="tabladeSolicitudes" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Número de Solicitud</th>
                    <th>Usuario Solicitante</th>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Fecha de Solicitud</th>
                    <th>Sección que Presta </th>
                    <th>Estado</th>
                    <c:if test="${booladminprest}">
                      <th> Cambio Estado</th>
                    </c:if>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaPrestamos}" var="prestamo">

                    <tr id ="${prestamo.getId_solicitud()}">
                      <td>
                        <a href="/SIGIPRO/Bodegas/Prestamos?accion=ver&id_solicitud=${prestamo.getId_solicitud()}">
                          <div style="height:100%;width:100%">
                            ${prestamo.getId_solicitud()}
                          </div>
                        </a>
                      </td>
                      <td>${prestamo.getSolicitud().getUsuario().getNombreCompleto()} (${prestamo.getSolicitud().getUsuario().getNombreSeccion()})</td>
                      <td>${prestamo.getSolicitud().getInventario().getProducto().getNombre()} (${prestamo.getSolicitud().getInventario().getProducto().getCodigo_icp()})</td>
                      <td>${prestamo.getSolicitud().getCantidad()}</td>
                      <td>${prestamo.getSolicitud().getFecha_solicitud()}</td>
                      <td>${prestamo.getSeccion().getNombre_seccion()}</td>
                      <td>${prestamo.getSolicitud().getEstado()}</td>
                      <c:if test="${booladminprest}">
                        <c:choose>
                          <c:when test="${prestamo.getSolicitud().getEstado().equals('Pendiente Prestamo')}">
                                <td>
                                  <a class="btn btn-primary btn-sm boton-accion confirmableAceptar" data-texto-confirmacion="aceptar esta solicitud de préstamo" data-href="/SIGIPRO/Bodegas/Prestamos?accion=aceptar&id_solicitud=" onclick="AceptarSolicitud(${prestamo.getSolicitud().getId_solicitud()})">Aprobar</a>
                                  <a class="btn btn-danger btn-sm boton-accion" onclick="RechazarSolicitud(${prestamo.getSolicitud().getId_solicitud()})">Rechazar</a>
                                </td>
                          </c:when>
                          <c:otherwise>
                                <c:choose>
                                  <c:when test="${prestamo.getSolicitud().getEstado().equals('Rechazada') || prestamo.getSolicitud().getEstado().equals('Prestamo Repuesto')}" >
                                    <td>
                                     <button class="btn btn-danger btn-sm boton-accion" disabled >Solicitud Finalizada</button>
                                    </td>
                                  </c:when>
                                  <c:otherwise>
                                    <td>
                                     <button class="btn btn-warning btn-sm boton-accion" disabled >Por Reponer</button>
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
    <t:modal idModal="ModalRechazar" titulo="Observaciones">

      <jsp:attribute name="form">
        <h5> ¿Está seguro que desea rechazar esta solicitud de préstamo? De ser así por favor indique las observaciones: </h5>
        <form class="form-horizontal" id="form_modalrechazar" data-show-auth="${show_modal_auth}" method="post" action="Prestamos">
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
      <script src="/SIGIPRO/recursos/js/sigipro/prestamos.js"></script>
    </jsp:attribute>

  </t:plantilla_general>

