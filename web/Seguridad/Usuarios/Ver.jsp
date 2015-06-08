<%-- 
    Document   : Ver Usuario
    Created on : Dec 14, 2014, 11:44:52 AM
    Author     : Boga
--%>

<%@page import="com.icp.sigipro.seguridad.dao.UsuarioDAO"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%
    List<Integer> permisos = (List<Integer>) session.getAttribute("listaPermisos");
    if (!(permisos.contains(1) || permisos.contains(2) || permisos.contains(3) || permisos.contains(4)))
    {
      request.getRequestDispatcher("/").forward(request, response);
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
            <li> 
                <a href="/SIGIPRO/Seguridad/Usuarios/">Usuarios</a>
            </li>
            <li class="active"> ${usuario.getNombreUsuario()} </li>
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
              <h3><i class="fa fa-group"></i> ${usuario.getNombreUsuario()} </h3>
              <div class="btn-group widget-header-toolbar">
                
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 4}">
                      <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <c:choose>
                    <c:when test="${actividad}">
                      <a class="btn btn-danger btn-sm boton-accion" data-toggle="modal" data-target="#modalDesactivarUsuario">Desactivar</a>   
                    </c:when>
                    <c:otherwise>
                      <c:if test="${boolfechadesactivacion}">
                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalActivarUsuario">Activar</a>
                      </c:if> 
                    </c:otherwise>
                  </c:choose> 
                </c:if>

                <c:set var="contienePermisoRestablecer" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 17}">
                    <c:set var="contienePermisoRestablecer" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoRestablecer}">
                  <a class="btn btn-warning btn-sm boton-accion " onclick="ConfirmacionRestablecerContrasena('/SIGIPRO/Cuenta/RecuperarContrasena?idUsuario=${usuario.getID()}&correoElectronico=${usuario.getCorreo()}')">Restablecer Contraseña</a>
                </c:if>
                  
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 3}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Seguridad/Usuarios/Editar?id=${usuario.getID()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre de Usuario:</strong></td> <td>${usuario.getNombreUsuario()} </td></tr>
                <tr><td> <strong>Nombre Completo:</strong> <td>${usuario.getNombreCompleto()} </td></tr>
                <tr><td> <strong>Correo Electrónico:</strong> <td>${usuario.getCorreo()} </td></tr>
                <tr><td> <strong>Cédula:</strong> <td>${usuario.getCedula()}</td></tr>
                <tr><td> <strong>Sección:</strong> <td>${usuario.getNombreSeccion()}</td></tr>
                <tr><td> <strong>Puesto:</strong> <td>${usuario.getNombrePuesto()}</td></tr>
                <tr><td> <strong>Fecha de Activación:</strong> <td>${usuario.getFechaActivacion()}</td></tr>
                <c:choose>
                  <c:when test="${usuario.getFechaActivacion() == usuario.getFechaDesactivacion()}">
                    <tr><td> <strong>Fecha de Desactivación:</strong>  <td> Usuario Permanente</td></tr>
                  </c:when>
                  <c:otherwise>
                    <tr><td> <strong>Fecha de Desactivación:</strong>  <td>${usuario.getFechaDesactivacion()}</td></tr>
                  </c:otherwise>
                </c:choose>

                <tr><td> <strong>Estado:</strong> <td>${usuario.getActivo()}</td></tr>
              </table>
              <br>
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-legal"></i> Roles ${usuario.getNombreUsuario()} </h3>
                </div>
                <div class="widget-content">
                  <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                    <thead>
                      <tr>
                        <th>Nombre Rol</th>
                        <th>Fecha Activación</th>
                        <th>Fecha Desactivación</th>
                      </tr>
                    </thead>
                    <tbody>
                      <c:forEach items="${rolesUsuario}" var="rolUsuario">
                        <tr id="${rolUsuario.getIDRol()}">
                          <td>${rolUsuario.getNombreRol()}</td>
                          <td>${rolUsuario.getFechaActivacion()}</td>
                          <td>${rolUsuario.getFechaDesactivacion()}</td>
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
    <t:modal idModal="modalDesactivarUsuario" titulo="Confirmación">

      <jsp:attribute name="form">
        <form class="form-horizontal" role="form" action="Desactivar" method="post">
          <h5>¿Está seguro que desea desactivar este usuario? </h5>
          <input type="text" value="${usuario.getID()}"  name="usuario"  hidden="true">
          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar </button>
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar </button>
            </div>
          </div>
        </form>
      </jsp:attribute>

    </t:modal>
    <t:modal idModal="modalActivarUsuario" titulo="Confirmación">

      <jsp:attribute name="form">
        <form class="form-horizontal" role="form" action="Activar" method="post">
          <h5>¿Está seguro que desea activar este usuario? </h5>
          <input type="text" value="${usuario.getID()}"  name="usuario"  hidden="true">
          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar </button>
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar </button>
            </div>
          </div>
        </form>
      </jsp:attribute>

    </t:modal>

  </jsp:attribute>

</t:plantilla_general>
