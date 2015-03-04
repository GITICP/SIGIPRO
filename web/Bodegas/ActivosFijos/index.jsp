<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Walter
--%>
<%@page import="java.util.List"%>
<%@page import="com.icp.sigipro.bodegas.modelos.ActivoFijo"%>
<%@page import="com.icp.sigipro.bodegas.dao.ActivoFijoDAO"%>
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

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bodegas</li>
            <li> 
              <a href="/SIGIPRO/Bodegas/ActivosFijos?">Activos Fijos</a>
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

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 31}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Bodegas/ActivosFijos?accion=agregar">Agregar Activo Fijo</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="datatable-column-filter-activos" class="table table-sorting table-striped table-hover datatable tablaSigipro">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Placa</th>
                    <th>Equipo</th>
                    <th>Marca</th>
                    <th>Fecha de Movimiento</th>
                    <th>Sección</th>
                    <th>Ubicación</th>
                    <th>Fecha de Registro</th>
                    <th>Estado</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaActivosFijos}" var="activos">

                    <tr id ="${activos.getId_activo_fijo()}">
                      <td>
                        <a href="/SIGIPRO/Bodegas/ActivosFijos?accion=ver&id_activo_fijo=${activos.getId_activo_fijo()}">
                          <div style="height:100%;width:100%">
                            ${activos.getPlaca()}
                          </div>
                        </a>
                      </td>
                      <td>${activos.getEquipo()}</td>
                      <td>${activos.getMarca()}</td>
                      <td>${activos.getFecha_movimiento()}</td>
                      <td>${activos.getNombre_seccion()}</td>
                      <td>${activos.getNombre_ubicacion()}</td>
                      <td>${activos.getFecha_registro()}</td>
                      <td>${activos.getEstado()}</td>
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
