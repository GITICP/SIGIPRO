<%-- 
    Document   : index
    Created on : Abr 3, 2015, 11:27:57 AM
    Author     : Boga
--%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bioterio" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Salida</li>
            <li> 
              <a href="/SIGIPRO/Bioterio/Salidas?especie=${especie}">Salida Extraordinaria</a>
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
              <h3><i class="fa fa-barcode"></i>Salida Extraordinaria</h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Bioterio/Salidas?accion=agregar&especie=${especie}">Agregar Salida</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                      <th>Fecha</th>
                      <th>Cantidad</th>
                      <th>Razón</th>
                      <th>Observaciones</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${lista_salida}" var="salida">
                      
                    <tr>
                      <td>
                        <a href="/SIGIPRO/Bioterio/Salidas?accion=ver&id_salida=${salida.getId_salida()}">
                          <div style="height:100%;width:100%">
                            ${salida.getFecha_S()}
                          </div>
                        </a>
                      </td>
                      <td>${salida.getCantidad()}</td>
                      <td>${salida.getRazon()}</td>
                      <td>${salida.getObservaciones()}</td>
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
