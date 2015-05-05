<%-- 
    Document   : index
    Created on : Abr 3, 2015, 11:27:57 AM
    Author     : Boga
--%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Conejos Produccion" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Conejera</li>
            <li> 
              <a href="/SIGIPRO/Conejera/Machos?">Machos</a>
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
              <h3><i class="fa fa-barcode"></i>Machos</h3>

                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Conejera/Machos?accion=agregar">Agregar Macho</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Identificación</th>
                    <th>Fecha Ingreso</th>
                    <th>Fecha Retiro</th>
                    <th>Descripción</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${conejos}" var="conejo">
                      
                    <tr>
                      <td>
                        <a href="/SIGIPRO/Conejera/Machos?accion=ver&id_macho=${conejo.getId_macho()}">
                          <div style="height:100%;width:100%">
                            ${conejo.getIdentificacion()}
                          </div>
                        </a>
                      </td>
                      <td>${conejo.getFecha_ingreso_S()}</td>
                      <td>${conejo.getFecha_retiro_S()}</td>
                      <td>${conejo.getDescripcion()}</td>
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
