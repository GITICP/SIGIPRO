<%-- 
    Document   : index
    Created on : Mar 26, 2015, 4:02:57 PM
    Author     : Amed
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Conejera" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Conejera</li>
            <li> 
              <a href="/SIGIPRO/Conejera/Cajas?">Cajas</a>
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
              <h3><i class="fa fa-barcode"></i> Cajas </h3>
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Conejera/Cajas?accion=agregar">Agregar Caja</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <c:forEach items="${listaCajas}" var="caja">

                        <a class="btn btn-default btn-lg" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                          <div style="height:100%;width:100%">
                            Caja #${caja.getNumero()}
                          </div>
                        </a>


              </c:forEach> 
              <!--<table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                <thead> 
                  <tr>
                    <th>Numero de la Caja</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaCajas}" var="caja">

                    <tr id ="${caja.getId_caja()}">
                      <td>
                        <a href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                          <div style="height:100%;width:100%">
                            ${caja.getNumero()}
                          </div>
                        </a>
                      </td>
                    </tr>

                  </c:forEach>
                </tbody>
              </table> -->
            </div> 
           
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->

    </jsp:attribute>

  </t:plantilla_general>

