<%-- 
    Document   : index
    Created on : Feb 10, 2014, 9:49:57 PM
    Author     : Boga
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="css">
    <link href="../recursos/css/sigipro/bodegas/ingresos.css" rel="stylesheet" type="text/css" media="screen">
  </jsp:attribute>

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bodegas</li>
            <li> 
              <a href="/SIGIPRO/Bodegas/Ingresos?">Ingresos</a>
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
          <ul class="nav nav-tabs nav-tabs-right sigipro-nav-tabs">
            <li class="active">
              <a href="#ingresos" data-toggle="tab">Ingresos</a>
            </li>
            <li class="">
              <a href="#ingresosPendientes" data-toggle="tab">Cuarentena
                <span class="badge">${listaIngresosCuarentena.size()}</span>
              </a>
            </li>
            <li class="">
              <a href="#ingresosNoDisponibles" data-toggle="tab">No Disponibles</a>
            </li>
            <li class="">
              <a href="#ingresosRechazados" data-toggle="tab">Rechazados</a>
            </li>
          </ul>

          <div class="tab-content sigipro-tab-content">
            <div class="tab-pane fade active in" id="ingresos">
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-sign-in"></i> Ingresos </h3>

                  <c:set var="contienePermiso" value="false" />
                  <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                    <c:if test="${permiso == 1 || permiso == 27}">
                      <c:set var="contienePermiso" value="true" />
                    </c:if>
                  </c:forEach>
                  <c:if test="${contienePermiso}">
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/Ingresos?accion=agregar">Registrar Ingreso</a>
                    </div>
                  </c:if>
                </div>
                ${mensaje}
                <div class="widget-content">
                  <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <!-- Columnas -->
                    <thead> 
                      <tr>
                        <th>Producto</th>
                        <th>Sección</th>
                        <th>Fecha Ingreso</th>
                        <th>Fecha Registro</th>
                        <th>Fecha Vencimiento</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                        <th>Estado</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${listaIngresos}" var="ingreso">

                        <tr id ="${ingreso.getId_ingreso()}">
                          <td>
                            <a href="/SIGIPRO/Bodegas/Ingresos?accion=ver&id_ingreso=${ingreso.getId_ingreso()}">
                              <div style="height:100%;width:100%">
                                ${ingreso.getProducto().getNombre()}
                              </div>
                            </a>
                          </td>
                          <td>${ingreso.getSeccion().getNombre_seccion()}</td>
                          <td>${ingreso.getFecha_ingresoAsString()}</td>
                          <td>${ingreso.getFecha_registroAsString()}</td>
                          <td>${ingreso.getFecha_vencimientoAsString()}</td>
                          <td>${ingreso.getCantidad()}</td>
                          <td>${ingreso.getPrecio()}</td>
                          <td>${ingreso.getEstado()}</td>
                        </tr>

                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>


            <div class="tab-pane fade" id="ingresosPendientes">
              <form id="formAprobaciones" method="post" action="Ingresos">
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-sign-in"></i> Ingresos En Cuarentena </h3>

                    <c:set var="contienePermisoAprobar" value="false" />
                    <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                      <c:if test="${permiso == 1 || permiso == 29}">
                        <c:set var="contienePermisoAprobar" value="true" />
                      </c:if>
                    </c:forEach>
                  </div>
                  <div class="widget-content">
                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                      <!-- Columnas -->
                      <thead> 
                        <tr>
                          <th>Producto</th>
                          <th>Sección</th>
                          <th>Fecha Ingreso</th>
                          <th>Fecha Registro</th>
                          <th>Fecha Vencimiento</th>
                          <th>Cantidad</th>
                          <th>Precio</th>
                            <c:if test="${contienePermisoAprobar}">
                            <th>Acción</th>
                            </c:if>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${listaIngresosCuarentena}" var="ingresoCuarentena">

                          <tr id ="${ingresoCuarentena.getId_ingreso()}">
                            <td>
                              <a href="/SIGIPRO/Bodegas/Ingresos?accion=ver&id_ingreso=${ingresoCuarentena.getId_ingreso()}">
                                <div style="height:100%;width:100%">
                                  ${ingresoCuarentena.getProducto().getNombre()}
                                </div>
                              </a>
                            </td>
                            <td>${ingresoCuarentena.getSeccion().getNombre_seccion()}</td>
                            <td>${ingresoCuarentena.getFecha_ingresoAsString()}</td>
                            <td>${ingresoCuarentena.getFecha_registroAsString()}</td>
                            <td>${ingresoCuarentena.getFecha_vencimientoAsString()}</td>
                            <td>${ingresoCuarentena.getCantidad()}</td>
                            <td>${ingresoCuarentena.getPrecio()}</td>
                            <c:if test="${contienePermisoAprobar}">
                              <td class="fila-decision">
                                <form></form> <!-- Necesario para que los otros forms sean válidos. No sé por qué -->
                                <form id="form-ingreso-${ingresoCuarentena.getId_ingreso()}-aprobar" action="Ingresos" method="post">
                                  <input type="hidden" name="id_ingreso" value="${ingresoCuarentena.getId_ingreso()}">
                                  <input type="hidden" name="accion" value="aprobar">
                                </form>
                                <form id="form-ingreso-${ingresoCuarentena.getId_ingreso()}-rechazar" action="Ingresos" method="post">
                                  <input type="hidden" name="id_ingreso" value="${ingresoCuarentena.getId_ingreso()}">
                                  <input type="hidden" name="accion" value="rechazar">
                                </form>
                                <a class="btn btn-primary btn-sm boton-accion confirmable-form" data-texto-confirmacion="aprobar este ingreso" data-form-id="form-ingreso-${ingresoCuarentena.getId_ingreso()}-aprobar">Aprobar</a>
                                <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="rechazar este ingreso" data-form-id="form-ingreso-${ingresoCuarentena.getId_ingreso()}-rechazar">Rechazar</a>
                              </td>
                            </c:if>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
              </form>
            </div>

            <div class="tab-pane fade" id="ingresosRechazados">
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-sign-in"></i> Ingresos Rechazados </h3>
                </div>
                <div class="widget-content">
                  <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <!-- Columnas -->
                    <thead> 
                      <tr>
                        <th>Producto</th>
                        <th>Sección</th>
                        <th>Fecha Ingreso</th>
                        <th>Fecha Registro</th>
                        <th>Fecha Vencimiento</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${listaIngresosRechazados}" var="ingresoRechazado">

                        <tr id ="${ingresoRechazado.getId_ingreso()}">
                          <td>
                            <a href="/SIGIPRO/Bodegas/Ingresos?accion=ver&id_ingreso=${ingresoRechazado.getId_ingreso()}">
                              <div style="height:100%;width:100%">
                                ${ingresoRechazado.getProducto().getNombre()}
                              </div>
                            </a>
                          </td>
                          <td>${ingresoRechazado.getSeccion().getNombre_seccion()}</td>
                          <td>${ingresoRechazado.getFecha_ingresoAsString()}</td>
                          <td>${ingresoRechazado.getFecha_registroAsString()}</td>
                          <td>${ingresoRechazado.getFecha_vencimientoAsString()}</td>
                          <td>${ingresoRechazado.getCantidad()}</td>
                          <td>${ingresoRechazado.getPrecio()}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
            <div class="tab-pane fade" id="ingresosNoDisponibles">
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-sign-in"></i> Ingresos No Disponibles </h3>
                </div>
                <div class="widget-content">
                  <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <!-- Columnas -->
                    <thead> 
                      <tr>
                        <th>Producto</th>
                        <th>Sección</th>
                        <th>Fecha Ingreso</th>
                        <th>Fecha Registro</th>
                        <th>Fecha Vencimiento</th>
                        <th>Cantidad</th>
                        <th>Precio</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${listaIngresosNoDisponibles}" var="ingresoNoDisponible">

                        <tr id ="${ingresoNoDisponible.getId_ingreso()}">
                          <td>
                            <a href="/SIGIPRO/Bodegas/Ingresos?accion=ver&id_ingreso=${ingresoNoDisponible.getId_ingreso()}">
                              <div style="height:100%;width:100%">
                                ${ingresoNoDisponible.getProducto().getNombre()}
                              </div>
                            </a>
                          </td>
                          <td>${ingresoNoDisponible.getSeccion().getNombre_seccion()}</td>
                          <td>${ingresoNoDisponible.getFecha_ingresoAsString()}</td>
                          <td>${ingresoNoDisponible.getFecha_registroAsString()}</td>
                          <td>${ingresoNoDisponible.getFecha_vencimientoAsString()}</td>
                          <td>${ingresoNoDisponible.getCantidad()}</td>
                          <td>${ingresoNoDisponible.getPrecio()}</td>
                        </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->

    </jsp:attribute>

    <jsp:attribute name="scripts">
      <script src="/SIGIPRO/recursos/js/sigipro/ingresos.js"></script>
    </jsp:attribute>

  </t:plantilla_general>
