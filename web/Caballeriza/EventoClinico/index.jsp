<%-- 
    Document   : index
    Created on : 24-mar-2015, 15:19:40
    Author     : Walter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Caballeriza</li>
            <li> 
              <a href="/SIGIPRO/Caballeriza/EventoClinico?">Eventos Clínicos</a>
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
              <h3><i class="fa fa-book"></i> Eventos Clínicos </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 55}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Caballeriza/EventoClinico?accion=agregar">Agregar Evento Clínico</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Identificador</th>
                    <th>Tipo de Evento</th>
                    <th>Fecha</th>
                    
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaEventosClinicos}" var="evento">

                    <tr id ="${evento.getId_evento()}">
                      <td>
                        <a href="/SIGIPRO/Caballeriza/EventoClinico?accion=ver&id_evento=${evento.getId_evento()}">
                          <div style="height:100%;width:100%">
                            ${evento.getId_evento()}
                          </div>
                        </a>
                      </td>
                      <td>${evento.getTipo_evento().getNombre()}</td>
                      <td>${evento.getFecha()}</td>
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