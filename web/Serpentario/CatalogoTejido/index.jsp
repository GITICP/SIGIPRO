<%-- 
    Document   : index
    Created on : Apr 11, 2015, 12:20:26 AM
    Author     : ld.conejo
--%>

<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
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
              <a href="/SIGIPRO/Serpentario/CatalogoTejido?">Catálogo de Tejidos</a>
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
              <h3><i class="fa fa-bug"></i> Catálogo de Tejidos </h3>

            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Número de CT</th>
                    <th>Número de Serpiente</th>
                    <th>Número de Caja</th>
                    <th>Posición</th>
                    <th>Estado</th>
                    <th>Usuario Responsable</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaCT}" var="ct">

                    <tr id ="${ct.getId_catalogo_tejido()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/CatalogoTejido?accion=ver&id_serpiente=${ct.getSerpiente().getId_serpiente()}">
                          <div style="height:100%;width:100%">
                            ${ct.getNumero_catalogo_tejido()}
                          </div>
                        </a>
                      </td>
                      <td>
                        <a href="/SIGIPRO/Serpentario/Serpiente?accion=ver&id_serpiente=${ct.getSerpiente().getId_serpiente()}">
                          <div style="height:100%;width:100%">
                            ${ct.getSerpiente().getNumero_serpiente()}
                          </div>
                        </a>
                      </td>
                      <td>${ct.getNumero_caja()}</td>
                      <td>${ct.getPosicion()}</td>
                      <td>${ct.getEstado()}</td>
                      <td>${ct.getUsuario().getNombre_completo()}</td>
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

