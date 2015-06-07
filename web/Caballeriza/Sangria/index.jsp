<%-- 
    Document   : index
    Created on : 24-mar-2015, 15:19:40
    Author     : Walter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Caballeriza</li>
            <li> 
              <a href="/SIGIPRO/Caballeriza/Sangria?">Sangría</a>
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
              <h3><i class="fa fa-book"></i> Sangrías </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 61}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Caballeriza/Sangria?accion=agregar">Agregar Sangría</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                      <th>Identificador:</th>
                      <th>Responsable:</th>
                      <th>Número de caballos:</th>
                      <th>Potencia:</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${lista_sangrias}" var="sangria">

                    <tr id ="${sangria.getId_sangria()}">
                      <td>
                        <a href="/SIGIPRO/Caballeriza/Sangria?accion=ver&id_sangria=${sangria.getId_sangria()}">
                          <div style="height:100%;width:100%">
                            ${sangria.getId_sangria()}
                          </div>
                        </a>
                      </td>
                      <td>${sangria.getResponsable().getNombre_completo()}</td>
                      <td>${sangria.getCantidad_de_caballos()}</td>
                      <td>${sangria.getPotencia()}</td>
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