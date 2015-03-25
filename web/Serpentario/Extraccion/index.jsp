<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
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
              <a href="/SIGIPRO/Serpentario/Extraccion?">Extracciones</a>
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
              <h3><i class="fa fa-barcode"></i> Extracciones </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 43}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Serpentario/Extraccion?accion=agregar">Agregar Extraccion</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Numero de Extraccion</th>
                    <th>Especie</th>
                    <th>Es de Colección Viva</th>
                    <th>Fecha de Extraccion</th>
                    <th>Volumen Extraído (mL)</th>
                    <th>Usuario de Registro</th>
                    <th>Fecha de Registro</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaExtracciones}" var="extraccion">

                    <tr id ="${extraccion.getId_extraccion()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/Extraccion?accion=ver&id_extraccion=${extraccion.getId_extraccion()}">
                          <div style="height:100%;width:100%">
                            ${extraccion.getNumero_extraccion()}
                          </div>
                        </a>
                      </td>
                      <td>${extraccion.getEspecie().getGenero_especie()}</td>
                      <td>${extraccion.isIngreso_cv()}</td>
                      <td>${extraccion.getFecha_extraccionAsString()}</td>
                      <td>${extraccion.getVolumen_extraido()}</td>
                      <td>${extraccion.getUsuario().getNombre_usuario()}</td>
                      <td>${extraccion.getFecha_registroAsString()}</td>
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

