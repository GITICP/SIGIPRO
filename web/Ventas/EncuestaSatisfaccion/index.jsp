<%-- 
    Document   : index
    Created on : Jun 29, 2015, 5:02:21 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/EncuestaSatisfaccion?">Encuestas de Satisfacci�n</a>
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
              <h3><i class="fa fa-gears"></i> Encuestas de Satisfacci�n </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/EncuestaSatisfaccion?accion=agregar">Agregar Encuesta de Satisfacci�n</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>ID</th>
                    <th>Cliente</th>
                    <th>Fecha</th>
                    <th>Observaciones</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaEncuestas}" var="encuesta">

                    <tr id ="${encuesta.getId_encuesta()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/EncuestaSatisfaccion?accion=ver&id_encuesta=${encuesta.getId_encuesta()}">
                          <div style="height:100%;width:100%">
                            ${encuesta.getId_encuesta()}
                          </div>
                        </a>
                      </td>
                      <td>${encuesta.getCliente().getNombre()}</td>
                      <td>${encuesta.getFecha_S()}</td>
                      <td>${encuesta.getObservaciones()}</td>
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
