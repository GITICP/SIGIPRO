<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Produccion" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Inoculo?">Catálogo de Inóculos</a>
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
                <h3><i class="fa fa-flask"></i> Catálogo de Inóculos </h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Inoculo?accion=agregar">Agregar Inóculo</a>
                </div>  
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Identificador</th>
                    <th>Fecha de Preparación</th>
                    <th>Encargado de Preparación</th>
                    <th>Veneno asociado</th>
                    <th>Peso (miligramos)</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaInoculos}" var="inoculo">

                    <tr id ="${inoculo.getId_inoculo()}">
                      <td>
                        <a href="/SIGIPRO/Produccion/Inoculo?accion=ver&id_inoculo=${inoculo.getId_inoculo()}">
                        <div style="height:100%;width:100%">
                            ${inoculo.getIdentificador()}
                        </div>
                        </a>
                      </td>
                      <td>${inoculo.getFecha_preparacion_S()}</td>
                      <td>${inoculo.getEncargado_preparacion()}</td>
                      <td>
                            <a href="/SIGIPRO/Produccion/Veneno?accion=ver&id_veneno=${inoculo.getVeneno().getId_veneno()}">
                                ${inoculo.getVeneno().getId_veneno()}
                            </a>
                      </td>
                      <td>${inoculo.getPeso()}</td>
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
