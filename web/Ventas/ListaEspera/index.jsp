<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : jespinozac95
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
              <a href="/SIGIPRO/Ventas/ListaEspera?">Listas de Espera</a>
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
                <h3><i class="fa fa-file-text-o"></i> Listas de Espera </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/ListaEspera?accion=agregar">Agregar a Lista</a>
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Fecha de Solicitud</th>
                    <th>Cliente</th>
                    <th>Fecha de Atención / Despacho</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaListas}" var="lista">

                    <tr id ="${lista.getId_lista()}">
                      <td><a href="/SIGIPRO/Ventas/ListaEspera?accion=ver&id_lista=${lista.getId_lista()}">
                        <div style="height:100%;width:100%">
                            ${lista.getFecha_solicitud_S()}
                        </div>
                        </a>
                      </td>
                      <td>
                        <a href="/SIGIPRO/Ventas/ListaEspera?accion=ver&id_lista=${lista.getId_lista()}">
                        <div style="height:100%;width:100%">
                            ${lista.getCliente().getNombre()}
                        </div>
                        </a>
                      </td>
                      <td>${lista.getFecha_atencion_S()}</td>
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
