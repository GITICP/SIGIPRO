
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
              <a href="/SIGIPRO/Bodegas/Prestamos?">Préstamos</a>
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
              <h3><i class="fa fa-barcode"></i> Préstamos</h3>
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Solicitudes">Solicitudes</a>
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Prestamos?accion=agregar">Solicitar nuevo Préstamo</a>
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
                    <c:if test="${booladmin}">
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
                      <c:if test="${booladmin}">
                        <c:choose>
                          <c:when test="${prestamo.getSolicitud().getEstado().equals('Entregada')}">
                            <td>
                              <a class="btn btn-primary btn-sm boton-accion confirmableAprobar" data-texto-confirmacion="marcar este préstamo como 'Repuesto'" data-href="/SIGIPRO/Bodegas/Prestamos?accion=reponer&id_solicitud=" onclick="AprobarSolicitud(${prestamo.getSolicitud().getId_solicitud()})">Reponer</a>
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
    </jsp:attribute>
    <jsp:attribute name="scripts">
      <script src="/SIGIPRO/recursos/js/sigipro/prestamos.js"></script>
    </jsp:attribute>

  </t:plantilla_general>
