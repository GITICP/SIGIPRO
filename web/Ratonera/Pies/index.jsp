<%-- 
    Document   : index
    Created on : Abr 3, 2015, 11:27:57 AM
    Author     : Boga
--%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Pies de Cría" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Ratonera</li>
            <li> 
              <a href="/SIGIPRO/Ratonera/Pies?">Pies de Cría</a>
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
              <h3><i class="sigipro-mouse-1"></i>Pies de Cría</h3>

                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ratonera/Pies?accion=agregar">Agregar Pie</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Código</th>
                    <th>Fecha Ingreso</th>
                    <th>Fecha Retiro</th>
                    <th>Fuente</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${pies}" var="pie">
                      
                    <tr>
                      <td>
                        <a href="/SIGIPRO/Ratonera/Pies?accion=ver&id_pie=${pie.getId_pie()}">
                          <div style="height:100%;width:100%">
                            ${pie.getCodigo()}
                          </div>
                        </a>
                      </td>
                      <td>${pie.getFecha_ingreso_S()}</td>
                      <td>${pie.getFecha_retiro_S()}</td>
                      <td>${pie.getFuente()}</td>
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
