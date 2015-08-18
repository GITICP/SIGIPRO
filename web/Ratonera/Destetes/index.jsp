<%-- 
    Document   : index
    Created on : Mar 26, 2015, 4:02:57 PM
    Author     : Amed
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ratonera" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Ratonera</li>
            <li> 
              <a href="/SIGIPRO/Ratonera/Destetes?">Destetes</a>
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
              <h3><i class="sigipro-mouse-1"></i> Destetes </h3>
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Ratonera/Destetes?accion=agregar">Agregar Destete</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="tablaindexdestetes" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Fecha del Destete</th>
                    <th>Número de Hembras</th>
                    <th>Número de Machos</th>
                    <th>Cepa</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaDestetes}" var="destete">

                    <tr id ="${destete.getId_destete()}">
                      <td>
                        <a href="/SIGIPRO/Ratonera/Destetes?accion=ver&id_destete=${destete.getId_destete()}">
                          <div style="height:100%;width:100%">
                            ${destete.getFecha_destete()}
                          </div>
                        </a>
                      </td>
                      <td>${destete.getNumero_hembras()}</td>
                      <td>${destete.getNumero_machos()}</td>
                      <td>${destete.getCepa().getNombre()}</td>
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
      <script src="/SIGIPRO/recursos/js/sigipro/destetes.js"></script>
    </jsp:attribute>

  </t:plantilla_general>

