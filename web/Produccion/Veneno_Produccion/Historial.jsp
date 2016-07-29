<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Produccion" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Veneno_Produccion?">Historial de Consumo de Venenos de Producción</a>
            </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
                <h3><i class="fa fa-flask"></i> Historial de Consumo de Venenos de Producción </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Veneno_Produccion">Catálogo de Venenos de Producción</a>
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Veneno</th>
                    <th>Usuario</th>
                    <th>Fecha de Consumo</th>
                    <th>Cantidad</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${historiales}" var="historial">

                    <tr id ="${historial.getId_veneno()}">
                      <td>
                        <a href="/SIGIPRO/Produccion/Veneno_Produccion?accion=ver&id_veneno=${historial.getId_veneno()}">
                        <div style="height:100%;width:100%">
                            ${historial.getVeneno().getVeneno()}
                        </div>
                        </a>
                      </td>
                      <td>${historial.getUsuario().getNombreUsuario()}</td>
                      <td>${historial.getFecha_S()}</td>
                      <td>${historial.getCantidad()}</td>
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
    </jsp:attribute>

  </t:plantilla_general>
