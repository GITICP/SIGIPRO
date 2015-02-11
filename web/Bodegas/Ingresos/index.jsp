<%-- 
    Document   : index
    Created on : Feb 10, 2014, 9:49:57 PM
    Author     : Boga
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
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-sign-in"></i> Ingresos </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 11}">
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
              <table id="datatable-column-filter-bodegas-catalogo-interno" class="table table-sorting table-striped table-hover datatable tablaSigipro">
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
                      <td>${ingreso.getSeccion().getNombreSeccion()}</td>
                      <td>${ingreso.getFecha_ingreso()}</td>
                      <td>${ingreso.getFecha_registro()}</td>
                      <td>${ingreso.getFecha_vencimiento()}</td>
                      <td>${ingreso.getCantidad()}</td>
                      <td>${ingreso.getPrecio()}</td>
                      <td>${ingreso.getEstado()}</td>
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

  </t:plantilla_general>
