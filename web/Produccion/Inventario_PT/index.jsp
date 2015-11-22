<%-- 
    Document   : index
    Created on : Abr 3, 2015, 11:27:57 AM
    Author     : Boga
--%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Inventario de Producto Terminado" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Produccion</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Inventario_PT?">Inventario de Producto Terminado</a>
            </li>
          </ul>
        </div>
        <div class="col-md-8 ">
          <div class="top-content">
          </div>
        </div>
      </div>
      <!-- Form de eliminar -->
      <form id="form-eliminar" class="form-horizontal" autocomplete="off" method="post" action="Inventario_PT">
        <input hidden="true" id="id_eliminar" name="id_eliminar" value="">
        <input hidden="true" id="accion" name="accion" value="">
      </form>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <ul class="nav nav-tabs nav-tabs-custom-colored" role="tablist">
            <c:if test="${admin}">
              <li id="inv" class="${inv_tab}">
                <a href="#inventario" role="tab" data-toggle="tab">Inventario</a>
              </li>
              <li id="sal" class="${sal_tab}"> <!--------------------------------------------------------------------AQUI HAY QUE MANDAR DESDE EL CONTROLADOR LA QUE ESTÁ ACTIVA--->
                <a href="#salidas" role="tab" data-toggle="tab">Salidas</a>
              </li>
              <li id="res" class="${res_tab}">
                <a href="#reservaciones" role="tab" data-toggle="tab">Reservaciones</a>
              </li>
            </c:if>
            <li id="desp" class="${desp_tab}">
              <a href="#despachos" role="tab" data-toggle="tab">Despachos</a>
            </li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade active in" id="inventario"> <!--------------------------------------------------------------------------------------------- Inventario -->
              <!-- COLUMN FILTER DATA TABLE -->
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-list-alt"></i> Inventario de Producto Terminado </h3>
                   <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=agregar_inventario">Agregar Entrada de Inventario</a>
                  </div>
                </div>
                ${mensaje}
                <div class="widget-content">
                  <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <!-- Columnas -->
                    <thead> 
                      <tr>
                        <th>Lote</th>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Cantidad Disponible</th>
                        <th>Fecha de Vencimiento</th>
                        <th>Cambio de Estado</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${inventario}" var="inventario">

                        <tr>
                          <td>
                            <a href="/SIGIPRO/Produccion/Inventario_PT?accion=ver_inventario&id_inventario_pt=${inventario.getId_inventario_pt()}">
                              <div style="height:100%;width:100%">
                                ${inventario.getLote()}
                              </div>
                            </a>
                          </td>
                          <td>${inventario.getProducto().getNombre()}</td>
                          <td>${inventario.getCantidad()}</td>
                          <td>${inventario.getCantidad_disponible()}</td>
                          <td>${inventario.getFecha_vencimiento_S()}</td>
                          <td>
                            <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=editar_inventario&id_inventario_pt=${inventario.getId_inventario_pt()}">Editar</a>
                            <a class="btn btn-danger btn-sm boton-accion" onclick="Eliminar(${inventario.getId_inventario_pt()},'eliminar esta entrada de inventario','inventario')">Eliminar</a>                          </td>
                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <!-- COLUMN FILTER DATA TABLE -->
            </div>
            <div class="tab-pane fade" id="salidas"> <!--------------------------------------------------------------------- SALIDAS -->
              <!-- COLUMN FILTER DATA TABLE -->
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-list-alt"></i> Salidas Extraordinarias de Producto Terminado </h3>
                  <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=agregar_salida">Agregar Salida</a>
                  </div>
                </div>
                ${mensaje}
                <div class="widget-content">
                  <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <!-- Columnas -->
                    <thead> 
                      <tr>
                        <th>Fecha</th>
                        <th>Tipo</th>
                        <th>Observaciones</th>
                        <th>Cantidad Total de Salida</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${inventarios}" var="inventario">

                        <tr>
                          <td>
                            <a href="/SIGIPRO/Conejera/Machos?accion=ver&id_macho=${inventario.getId_macho()}">
                              <div style="height:100%;width:100%">
                                ${inventario.getIdentificacion()}
                              </div>
                            </a>
                          </td>
                          <td>${inventario.getFecha_ingreso_S()}</td>
                          <td>${inventario.getFecha_retiro_S()}</td>
                          <td>${inventario.getDescripcion()}</td>
                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <!-- COLUMN FILTER DATA TABLE --> 
            </div>
            <div class="tab-pane fade" id="despachos"> <!--------------------------------------------------------------------- Despachos -->
              <!-- COLUMN FILTER DATA TABLE -->
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-list-alt"></i> Despachos de Producto Terminado </h3>
                  <c:if test="${admin}">
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=agregar_despacho">Agregar Despacho</a>
                    </div>
                  </c:if>
                </div>
                ${mensaje}
                <div class="widget-content">
                  <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <!-- Columnas -->
                    <thead> 
                      <tr>
                        <th>Fecha</th>
                        <th>Destino</th>
                        <th>Aprobacion de Coordinador</th>
                        <th>Aprobacion de Regente</th>
                        <th>Cantidad Total a Despachar</th>
                        <th>Cambio de Estado </th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${inventarios}" var="inventario">

                        <tr>
                          <td>
                            <a href="/SIGIPRO/Conejera/Machos?accion=ver&id_macho=${inventario.getId_macho()}">
                              <div style="height:100%;width:100%">
                                ${inventario.getIdentificacion()}
                              </div>
                            </a>
                          </td>
                          <td>${inventario.getFecha_ingreso_S()}</td>
                          <td>${inventario.getFecha_retiro_S()}</td>
                          <td>${inventario.getDescripcion()}</td>
                          <c:choose>
                            <c:when test="${autorizador}">
                              <c:choose>
                                <c:when test="${solicitud.getEstado().equals('Pendiente')}">
                                  <td>
                                    <a class="btn btn-primary btn-sm boton-accion confirmableAprobar" data-texto-confirmacion="aprobar esta solicitud" data-href="/SIGIPRO/Conejera/SolicitudesConejera?accion=aprobar&id_solicitud=" onclick="AprobarSolicitud(${solicitud.getId_solicitud()})">Aprobar</a>
                                    <a class="btn btn-danger btn-sm boton-accion" onclick="RechazarSolicitud(${solicitud.getId_solicitud()})">Rechazar</a>
                                  </td>
                                </c:when>
                                <c:otherwise>
                                  <td>
                                    <button class="btn btn-danger btn-sm boton-accion" disabled >Despacho Completado</a>
                                  </td>
                                </c:otherwise>
                              </c:choose>
                            </c:when>
                            <c:otherwise>
                              <c:choose>
                                <c:when test="${solicitud.getEstado().equals('Pendiente')}">
                                  <td>
                                    <a class="btn btn-primary btn-sm boton-accion "  onclick="" >Editar</a>
                                    <a class="btn btn-danger btn-sm boton-accion confirmableEliminar" data-texto-confirmacion="eliminar esta solicitud" data-href="/SIGIPRO/Conejera/SolicitudesConejera?accion=cerrar&tipo=Anulada&id_solicitud=" onclick="CerrarSolicitud(${solicitud.getId_solicitud()})">Eliminar</a>
                                  </td>
                                </c:when>
                                <c:otherwise>
                                  <td>
                                    <button class="btn btn-danger btn-sm boton-accion" disabled >Despacho Completado</a>
                                  </td>
                                </c:otherwise>
                              </c:choose>
                            </c:otherwise>
                          </c:choose>
                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <!-- COLUMN FILTER DATA TABLE -->    </div>
            <div class="tab-pane fade" id="reservaciones"> <!--------------------------------------------------------------------------------------------- Reservaciones -->
              <!-- COLUMN FILTER DATA TABLE -->
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-list-alt"></i> Reservaciones de Producto Terminado </h3>
                  <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=agregar_reservacion">Agregar Reservacion</a>
                  </div>
                </div>
                ${mensaje}
                <div class="widget-content">
                  <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <!-- Columnas -->
                    <thead> 
                      <tr>
                        <th>Identificación</th>
                        <th>Fecha Ingreso</th>
                        <th>Fecha Retiro</th>
                        <th>Descripción</th>
                        <th> Cambio Estado</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${inventarios}" var="inventario">

                        <tr>
                          <td>
                            <a href="/SIGIPRO/Conejera/Machos?accion=ver&id_macho=${inventario.getId_macho()}">
                              <div style="height:100%;width:100%">
                                ${inventario.getIdentificacion()}
                              </div>
                            </a>
                          </td>
                          <td>${inventario.getFecha_ingreso_S()}</td>
                          <td>${inventario.getFecha_retiro_S()}</td>
                          <td>${inventario.getDescripcion()}</td>
                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <!-- COLUMN FILTER DATA TABLE --> 
            </div>
          </div>

          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
  </jsp:attribute>
<jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/Produccion/Inventario/Eliminar.js"></script>
</jsp:attribute>
</t:plantilla_general>
