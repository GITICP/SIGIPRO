<%-- 
    Document   : Ver Rol
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Amed
--%>


<%@page import="com.icp.sigipro.seguridad.dao.UsuarioDAO"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%
  List<Integer> permisos = (List<Integer>) session.getAttribute("listaPermisos");
  System.out.println(permisos);
  if (!(permisos.contains(1) || permisos.contains(5) || permisos.contains(6) || permisos.contains(7))) {
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
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Seguridad</li>
            <li>
              <a href="/SIGIPRO/Seguridad/Roles/">Roles</a>
            </li>
            <li class="active"> ${rol.getNombreRol()} </li>
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
              <h3><i class="fa fa-legal"></i> ${rol.getNombreRol()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 7}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm boton-accion" data-toggle="modal" data-target="#ModalEliminarRol">Eliminar</a>                                    
                </c:if>

                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 6}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="Editar?id=${rol.getID()}">Editar</a>
                </c:if>
                </div>
              </div>
              ${mensaje}
              <div class="widget-content">
                <table>
                  <tr><td> <strong>Nombre de Rol:   </strong></td> <td>${rol.getNombreRol()} </td></tr>
                  <tr><td> <strong>Descripcion:</strong> <td>${rol.getDescripcion()} </td></tr>
                </table>
                <br>
                <!-- Ver usuarios -->
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-group"></i> Usuarios con el rol ${rol.getNombreRol()} </h3>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Nombre Usuario</th>
                          <th>Fecha Activación</th>
                          <th>Fecha Desactivación</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${usuariosRol}" var="usuarioRol">
                          <tr id="${usuarioRol.getIDUsuario()}">
                            <td>${usuarioRol.getNombreUsuario()}</td>
                            <td>${usuarioRol.getFechaActivacion()}</td>
                            <td>${usuarioRol.getFechaDesactivacion()}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                <!-- Ver permisos -->
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-check"></i> Permisos del Rol ${rol.getNombreRol()} </h3>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Nombre Permiso</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${permisosRol}" var="permisoRol">
                          <tr id="${permisoRol.getIDPermiso()}">
                            <td>${permisoRol.getNombrePermiso()}</td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
              </div>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>
      <div class="widget-content">
        <div class="modal fade" id="ModalEliminarRol" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title" id="myModalLabel">Confirmación</h4>
              </div>
              <div class="modal-body">
                <form class="form-horizontal" role="form" action="Eliminar" method="post">
                  <h5 class="title">¿Está seguro que desea eliminar el Rol?</h5>
                  <br><br>
                  <input hidden="false" id="controlIDRol" name="controlIDRol" value="${rol.getID()}">
                  <div class="form-group">
                    <div class="modal-footer">
                      <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                      <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar</button>
                    </div>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>

  </jsp:attribute>

</t:plantilla_general>
