<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Walter
--%>
<%@page import="java.util.List"%>
<%@page import="com.icp.sigipro.activosfijos.modelos.ActivoFijo"%>
<%@page import="com.icp.sigipro.activosfijos.dao.ActivoFijoDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%
    
    List<Integer> permisos = (List<Integer>) session.getAttribute("listaPermisos");
    System.out.println(permisos);
    if (!(permisos.contains(1) || permisos.contains(31) || permisos.contains(32) || permisos.contains(33))) {
        request.getRequestDispatcher("/").forward(request, response);
    }
    
    ActivoFijoDAO p = new ActivoFijoDAO();

    List<ActivoFijo> activosfijos = p.obtenerActivosFijos();

    if(activosfijos!=null)
    {
        request.setAttribute("listaActivosFijos", activosfijos);
    }
%>

<t:plantilla_general title="Activos Fijos" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Activos Fijos</li>
            <li> 
              <a href="/SIGIPRO/ActivosFijos/Activos?">Activos Fijos</a>
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
              <h3><i class="fa fa-barcode"></i> Activos Fijos </h3>
              <div class="btn-group widget-header-toolbar">
              <c:set var="contienePermisoEliminar" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 33}">
                  <c:set var="contienePermisoEliminar" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermisoEliminar}">
                  <form id="form-eliminar" action="Activos" method="post">
                      <input name="accion" hidden="true" value="eliminar-masivo">
                      <input id="ids-por-eliminar" name="ids-por-eliminar" hidden="true">
                  </form>
                  <a id="btn-eliminar-activos-fijos" 
                     class="btn btn-danger btn-sm boton-accion confirmable-form"
                     data-form-id="form-eliminar"
                     data-texto-confirmacion="eliminar los activos fijos seleccionados">Eliminar</a>
              </c:if>
              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 31}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/ActivosFijos/Activos?accion=agregar">Agregar Activo Fijo</a>
              </c:if>
                  </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Selección</th>
                    <th>Placa</th>
                    <th>Equipo</th>
                    <th>Marca</th>
                    <th>Sección</th>
                    <th>Ubicación</th>
                    <th>Estado</th>
                    <th>Responsable</th>
                    <th>Número de Serie</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaActivosFijos}" var="activos">
                    <tr id ="${activos.getId_activo_fijo()}">
                        <td><input type="checkbox" name="eliminar" value="${activos.getId_activo_fijo()}"></td>
                      <td>
                        <a href="/SIGIPRO/ActivosFijos/Activos?accion=ver&id_activo_fijo=${activos.getId_activo_fijo()}">
                          <div style="height:100%;width:100%">
                            ${activos.getPlaca()}
                          </div>
                        </a>
                      </td>
                      <td>${activos.getEquipo()}</td>
                      <td>${activos.getMarca()}</td>
                      <td>${activos.getNombre_seccion()}</td>
                      <td>${activos.getNombre_ubicacion()}</td>
                      <td>${activos.getEstado()}</td>
                      <td>${activos.getResponsable()}</td>
                      <td>${activos.getSerie()}</td>
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
      
      <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/activos-fijos.js"></script>
      </jsp:attribute>
      
  </t:plantilla_general>
