<%-- 
    Document   : Usuario
    Created on : Dec 14, 2014, 11:44:52 AM
    Author     : Boga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

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
                <a class="btn btn-danger btn-sm"  style="margin-left:5px;margin-right:5px;color:#fff;" data-toggle="modal" data-target="#modalDesactivarUsuario">Desactivar</a>                                    
                <a class="btn btn-warning btn-sm" style="margin-left:5px;margin-right:5px;color:#fff;" href="Editar?id=${usuario.getID()}">Editar</a>
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
                <tr><td> <strong>Puesto:</strong> <td>${usuario.getPuesto()}</td></tr>
                <tr><td> <strong>Fecha de Activación:</strong> <td>${usuario.getFechaActivacion()}</td></tr>
                <tr><td> <strong>Fecha de Desactivacion:</strong>  <td>${usuario.getFechaDesactivacion()}</td></tr>
                <tr><td> <strong>Estado:</strong> <td>${usuario.getActivo()}</td></tr>
              </table>
              <br>
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-group"></i> Roles ${usuario.getNombreUsuario()} </h3>
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
    <t:modal idModal="modalDesactivarUsuario" titulo="Confirmacion">

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

  </jsp:attribute>

</t:plantilla_general>
