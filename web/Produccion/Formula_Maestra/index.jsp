<%-- 
    Document   : index
    Created on : Jun 29, 2015, 4:39:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Producción" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Formula_Maestra?">Fórmulas Maestras</a>
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
              <h3><i class="fa fa-gears"></i> Fórmulas Maestras </h3>
              <c:if test="${helper_permisos.validarPermiso(sessionScope.listaPermisos, 635)}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Produccion/Formula_Maestra?accion=agregar">Agregar Fórmula Maestra</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Nombre</th>
                    <th>Descripción</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaFormulas}" var="formula">

                    <tr id ="${formula.getId_formula_maestra()}">
                      <td>
                        <a href="/SIGIPRO/Produccion/Formula_Maestra?accion=ver&id_formula_maestra=${formula.getId_formula_maestra()}">
                          <div style="height:100%;width:100%">
                            ${formula.getNombre()}
                          </div>
                        </a>
                      </td>
                      <td>
                          ${formula.getDescripcion()}
                      </td>
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