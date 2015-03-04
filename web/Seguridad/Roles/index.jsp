<%-- 
Roles
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>
<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page import="com.icp.sigipro.seguridad.dao.UsuarioDAO"%>
<%@page import="com.icp.sigipro.seguridad.dao.RolDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.seguridad.modelos.Rol"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%

  RolDAO r = new RolDAO();
  List<Rol> roles = r.obtenerRoles();

  if (roles != null) {
    request.setAttribute("listaRoles", roles);
  }

%>

<%
    List<Integer> permisos = (List<Integer>) session.getAttribute("listaPermisos");
    System.out.println(permisos);
    if (!(permisos.contains(1) || permisos.contains(5) || permisos.contains(6) || permisos.contains(7)))
    {
      request.getRequestDispatcher("/").forward(request, response);
    }
    
    UsuarioDAO u = new UsuarioDAO();

  List<Usuario> usuarios = u.obtenerUsuarios();

  if (usuarios != null) {
    request.setAttribute("listaUsuarios", usuarios);
  }
%>

<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Seguridad</li>
            <li class="active">Roles</li>
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
              <h3><i class="fa fa-legal"></i> Roles</h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoAgregar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 5}">
                    <c:set var="contienePermisoAgregar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoAgregar}">
                  <a class="btn btn-primary btn-sm boton-accion" href='Agregar'>Agregar Rol</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Nombre del Rol</th>
                    <th>Descripción</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaRoles}" var="rol">

                    <tr id ="${rol.getID()}">
                      <td>
                        <a href="/SIGIPRO/Seguridad/Roles/Ver?id=${rol.getID()}">
                          <div style="height:100%;width:100%">
                            ${rol.getNombreRol()}
                          </div>
                        </a>
                      </td>
                      <td>${rol.getDescripcion()}</td>
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
