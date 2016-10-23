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
              <a href="/SIGIPRO/Ventas/Tratamiento?">Tratamientos</a>
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
                <h3><i class="fa fa-file-text-o"></i> Tratamientos </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/Tratamiento?accion=agregar">Agregar un Tratamiento</a>
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
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaTratamientos}" var="tratamiento">

                    <tr id ="${tratamiento.getId_tratamiento()}">
                      <td>
                        <a href="/SIGIPRO/Ventas/Tratamiento?accion=ver&id_tratamiento=${tratamiento.getId_tratamiento()}">
                        <div style="height:100%;width:100%">
                            ${tratamiento.getId_tratamiento()}
                        </div>
                        </a>
                      </td>
                      <td>${tratamiento.getCliente().getNombre()}</td>
                      <td>${tratamiento.getFecha_S()}</td>
                      <td>${tratamiento.getObservaciones()}</td>
                      <c:choose>
                        <c:when test="${tratamiento.getEstado().equals('Idóneo')}">
                           <td><font color="green">${tratamiento.getEstado()}</font></td>
                        </c:when>
                        <c:when test="${tratamiento.getEstado().equals('Normal')}">
                            <td><font color="blue">${tratamiento.getEstado()}</font></td>
                        </c:when>
                        <c:otherwise>
                            <td><font color="red">${tratamiento.getEstado()}</font></td>
                        </c:otherwise>
                    </c:choose>
                      
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
