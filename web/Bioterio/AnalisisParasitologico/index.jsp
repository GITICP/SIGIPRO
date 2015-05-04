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
            <li>Bioterio - Análisis</li>
            <li> 
              <a href="/SIGIPRO/Bioterio/AnalisisParasitologico?especie=${especie}">Análisis Parasitológico</a>
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
              <h3><i class="fa fa-barcode"></i>Analisis Parasitológico</h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Bioterio/AnalisisParasitologico?accion=agregar&especie=${especie}">Agregar Análisis</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                      <th>Número de Informe</th>
                      <th>Fecha</th>
                      <th>Responsable</th>
                      <th>Recetado por</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${lista_analisis}" var="analisis">
                      
                    <tr>
                      <td>
                        <a href="/SIGIPRO/Bioterio/AnalisisParasitologico?accion=ver&id_analisis=${analisis.getId_analisis()}">
                          <div style="height:100%;width:100%">
                            ${analisis.getNumero_informe()}
                          </div>
                        </a>
                      </td>
                      <td>${analisis.getFecha_S()}</td>
                      <td>${analisis.getResponsable().getNombre_completo()}</td>
                      <td>${analisis.getRecetado_por()}</td>
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
