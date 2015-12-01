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
            <li id="desp" class="${des_tab}">
              <a href="#despachos" role="tab" data-toggle="tab">Despachos</a>
            </li>
          </ul>
          <div class="tab-content">
            <div class="tab-pane fade ${inv_tab} in" id="inventario"> <!--------------------------------------------------------------------------------------------- Inventario -->
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
                            <a class="btn btn-danger btn-sm boton-accion" onclick="Eliminar(${inventario.getId_inventario_pt()}, 'eliminar esta entrada de inventario', 'inventario')">Eliminar</a>                          </td>
                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <!-- COLUMN FILTER DATA TABLE -->
            </div>
            <div class="tab-pane fade ${sal_tab} in" id="salidas"> <!--------------------------------------------------------------------- SALIDAS -->
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
                        <th>Cambio de Estado</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${salidas}" var="salida">

                        <tr>
                          <td>
                            <a href="/SIGIPRO/Produccion/Inventario_PT?accion=ver_salida&id_macho=${salida.getId_salida()}">
                              <div style="height:100%;width:100%">
                                ${salida.getFecha_S()}
                              </div>
                            </a>
                          </td>
                          <td>${salida.getTipo()}</td>
                          <td>${salida.getObservaciones()}</td>
                          <td>${salida.getTotal()}</td>
                          <td> <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=editar_salida&id_salida=${salida.getId_salida()}">Editar</a>
                            <a class="btn btn-danger btn-sm boton-accion" onclick="Eliminar(${salida.getId_salida()}, 'eliminar esta salida', 'salida')">Eliminar</a>                          </td>

                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              <!-- COLUMN FILTER DATA TABLE --> 
            </div>
            <div class="tab-pane fade ${des_tab} in" id="despachos"> <!--------------------------------------------------------------------- Despachos -->
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
                        <th>Firma de Coordinador</th>
                        <th>Firma de Regente</th>
                        <th>Cantidad Total a Despachar</th>
                        <th>Cambio de Estado </th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${despachos}" var="despacho">

                        <tr>
                          <td>
                            <a href="/SIGIPRO/Produccion/Inventario_PT?accion=ver_despacho&id_despacho=${despacho.getId_despacho()}">
                              <div style="height:100%;width:100%">
                                ${despacho.getFecha_S()}
                              </div>
                            </a>
                          </td>
                          <td>${despacho.getDestino()}</td>
                          <td>
                            <c:choose>
                              <c:when test="${despacho.isEstado_coordinador()}">
                                Firmada

                              </c:when>
                              <c:otherwise>
                                Pendiente
                              </c:otherwise>
                            </c:choose>

                          <td>
                          <c:choose>
                              <c:when test="${despacho.isEstado_regente()}">
                                Firmada

                              </c:when>
                              <c:otherwise>
                                Pendiente
                              </c:otherwise>
                            </c:choose>
                          </td>
                          <td>${despacho.getTotal()}</td>
                          <c:choose>
                            <c:when test="${autorizador}">
                              <c:choose>
                                <c:when test="${!(despacho.isEstado_coordinador()) or !(despacho.isEstado_regente())}">
                                  <td>
                                    <a class="btn btn-primary btn-sm boton-accion confirmableAprobar" data-texto-confirmacion="firmar esta solicitud" data-href="/SIGIPRO/Produccion/Inventario_PT?accion=firmar_despacho&id_despacho=" onclick="AprobarSolicitud(${despacho.getId_despacho()})">Firmar</a>
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
                                <c:when test="${!(despacho.isEstado_coordinador()) or !(despacho.isEstado_regente())}">
                                  <td>
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=editar_despacho&id_despacho=${despacho.getId_despacho()}">Editar</a>
                                    <a class="btn btn-danger btn-sm boton-accion" onclick="Eliminar(${despacho.getId_despacho()}, 'eliminar este despacho', 'despacho')">Eliminar</a>  
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
            <div class="tab-pane fade ${res_tab} in" id="reservaciones"> <!--------------------------------------------------------------------------------------------- Reservaciones -->
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
                        <th>Reservado Hasta</th>
                        <th>Observaciones</th>
                        <th>Total</th>
                        <th>Cambio Estado</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${reservaciones}" var="reservacion">

                        <tr>
                          <td>
                            <a href="/SIGIPRO/Produccion/Inventario_PT?accion=ver_reservacion&id_reservacion=${reservacion.getId_reservacion()}">
                              <div style="height:100%;width:100%">
                                ${reservacion.getId_reservacion()}
                              </div>
                            </a>
                          </td>
                          <td>${reservacion.getHasta_S()}</td>
                          <td>${reservacion.getObservaciones()}</td>
                          <td>${reservacion.getTotal()}</td>
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
