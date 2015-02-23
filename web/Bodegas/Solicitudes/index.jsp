
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
              <h3><i class="fa fa-barcode"></i> Solicitudes de Bodega </h3>
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Solicitudes?accion=agregar">Agregar Nueva Solicitud</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Número de Solicitud</th>
                    <th>Usuario Solicitante</th>
                    <th>Producto</th>
                    <th>Cantidad</th>
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
                        <a href="/SIGIPRO/Bodegas/Solicitudes?accion=ver&id_solicitud=${solicitud.getId_solicitud()}">
                          <div style="height:100%;width:100%">
                            ${solicitud.getId_solicitud()}
                          </div>
                        </a>
                      </td>
                      <td>${solicitud.getUsuario().getNombreCompleto()}</td>
                      <td>${solicitud.getInventario().getProducto().getNombre()} (${solicitud.getInventario().getProducto().getCodigo_icp()})</td>
                      <td>${solicitud.getCantidad()}</td>
                      <td>${solicitud.getFecha_solicitud()}</td>
                      <td>${solicitud.getEstado()}</td>
                      <c:if test="${booladmin}">
                        <c:choose>
                          <c:when test="${solicitud.getEstado().equals('Pendiente')}">
                            <td>
                              <a class="btn btn-primary btn-sm boton-accion confirmableAprobar" data-texto-confirmacion="aprobar esta solicitud" data-href="/SIGIPRO/Bodegas/Solicitudes?accion=aprobar&id_solicitud=" onclick="AprobarSolicitud(${solicitud.getId_solicitud()})">Aprobar</a>
                              <a class="btn btn-danger btn-sm boton-accion confirmableRechazar" data-texto-confirmacion="rechazar esta solicitud " data-href="/SIGIPRO/Bodegas/Solicitudes?accion=rechazar&id_solicitud=" onclick="RechazarSolicitud(${solicitud.getId_solicitud()})">Rechazar</a>
                            </td>
                          </c:when>
                          <c:otherwise>
                            <c:choose>
                              <c:when test="${solicitud.getEstado().equals('Aprobada')}">
                                <td>
                                 <a class="btn btn-primary btn-sm boton-accion confirmableEntregar" data-href="/SIGIPRO/Bodegas/Solicitudes?accion=entregar&id_solicitud=${solicitud.getId_solicitud()}" onclick="confirmarAuth(${solicitud.getId_solicitud()})">Entregar</a>
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
      
      <t:modal idModal="ModalAutorizar" titulo="Autenticación">

      <jsp:attribute name="form">
        <h5> Para validar la entrega, el usuario recipiente debe iniciar sesión. </h5>
        <form class="form-horizontal" id="form_modalautorizar" data-show-auth="${show_modal_auth}" method="post" action="Solicitudes">
          <input hidden="true" name="id_solicitud_auth" id="id_solicitud_auth" value="${id_solicitud_authent}">
          <input hidden="true" name="id_solicitud_auth2" id="id_solicitud_auth2" >
          <input hidden="true" name="accionindex" id="accionindex" value="accionindex">
          ${mensaje_auth}
          <label for="usr" class="control-label">Usuario</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <input type="text" id="usr"  name="usr" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
              </div>
            </div>
          </div>
          <label for="passw" class="control-label">Contraseña</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <input type="password" id="passw" name="passw" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
              </div>
              <p id='mensajeValidación' style='color:red;'><p>
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
