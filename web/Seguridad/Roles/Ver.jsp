<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Amed
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-10 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Seguridad</li>
            <li>Roles</li>
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
              <h3><i class="fa fa-group"></i> ${rol.getNombreRol()} </h3>
              <div class="btn-group widget-header-toolbar">
                <a class="btn btn-danger btn-sm"  style="margin-left:5px;margin-right:5px;color:#fff;" data-toggle="modal" data-target="#ModalEliminarRol">Eliminar</a>                                    
                <a class="btn btn-warning btn-sm" style="margin-left:5px;margin-right:5px;color:#fff;" href="Editar?id=${rol.getID()}">Editar</a>
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
                          <td><a href="/SIGIPRO/Seguridad/Usuarios/Ver?id=${usuarioRol.getIDUsuario()}">${usuarioRol.getNombreUsuario()}</a></td>
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
                  <h3><i class="fa fa-group"></i> Permisos del Rol ${rol.getNombreRol()} </h3>
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