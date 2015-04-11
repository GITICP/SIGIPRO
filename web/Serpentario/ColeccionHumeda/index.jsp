<%-- 
    Document   : index
    Created on : Apr 11, 2015, 12:27:40 AM
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
              <a href="/SIGIPRO/Serpentario/ColeccionHumeda?">Colección Húmeda</a>
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
              <h3><i class="fa fa-bug"></i> Colección Húmeda </h3>

            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Número de CH</th>
                    <th>Número de Serpiente</th>
                    <th>Propósito</th>
                    <th>Usuario Responsable</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaCH}" var="ch">

                    <tr id ="${ch.getId_coleccion_humeda()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/ColeccionHumeda?accion=ver&id_serpiente=${ch.getSerpiente().getId_serpiente()}">
                          <div style="height:100%;width:100%">
                            ${ch.getNumero_coleccion_humeda()}
                          </div>
                        </a>
                      </td>
                      <td>${ch.getSerpiente().getNumero_serpiente()}</td>
                      <td>${ch.getProposito()}</td>
                      <td>${ch.getUsuario().getNombre_completo()}</td>
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

