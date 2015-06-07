<%-- 
    Document   : index
    Created on : May 30, 2015, 1:42:42 PM
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
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Descarte?">Descartes</a>
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
              <h3><i class="fa fa-bug"></i> Serpientes Descartadas </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Número de Evento</th>
                    <th>Número de Serpiente</th>
                    <th>Fecha de Descarte</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaDescartes}" var="descarte">

                    <tr id ="${descarte.getId_evento()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/Descarte?accion=ver&id_evento=${descarte.getId_evento()}">
                          <div style="height:100%;width:100%">
                            ${descarte.getId_evento()}
                          </div>
                        </a>
                      </td>
                      <td>
                          <a href="/SIGIPRO/Serpentario/Serpiente?accion=ver&id_serpiente=${descarte.getSerpiente().getId_serpiente()}">
                          <div style="height:100%;width:100%">
                            ${descarte.getSerpiente().getNumero_serpiente()}
                          </div>
                        </a>
                      </td>
                      <td>${descarte.getFecha_eventoAsString()}</td>
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