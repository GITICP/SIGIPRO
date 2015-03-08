<%-- 
Secciones
    Document   : ver
    Created on : 08-ene-2015, 20:01:18
    Author     : Walter
--%>

<%@page import="com.icp.sigipro.configuracion.dao.SeccionDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.configuracion.modelos.Seccion"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%
  SeccionDAO s = new SeccionDAO();

  List<Seccion> secciones = s.obtenerSecciones();

  if (secciones != null) {
    request.setAttribute("listaSecciones", secciones);
  }
%>

<%
  List<Integer> permisos = (List<Integer>) session.getAttribute("listaPermisos");
  System.out.println(permisos);
  try{
  if (!(permisos.contains(1) || permisos.contains(8) || permisos.contains(9) || permisos.contains(10))) {
    response.sendRedirect("/index");
  }
  }
  catch(NullPointerException e){response.sendRedirect("/index");}
%>




<t:plantilla_general title="Configuracion" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Configuración</li>
            <li class="active">Secciones</li>
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
              <h3><i class="fa fa-puzzle-piece"></i> Secciones</h3>
              <div class="btn-group widget-header-toolbar">                    
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 10}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm boton-accion" onclick="eliminarSeccion()">Eliminar</a>                            
                </c:if>
                  
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 9}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" onclick="editarSeccion()" >Editar</a>
                </c:if>
                <c:set var="contienePermisoAgregar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 8}">
                    <c:set var="contienePermisoAgregar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoAgregar}">
                  <a class="btn btn-primary btn-sm boton-accion" href="Agregar">Agregar Sección</a>
                </c:if>
                </div>
              </div>
            ${mensaje}
            <div class="widget-content">
              <table id="datatable-column-filter-secciones" class="table table-sorting table-striped table-hover datatable">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Selección</th>
                    <th>Nombre Sección</th>
                    <th>Descripción</th>
                  </tr>
                </thead>
                <tbody>

                  <c:forEach items="${listaSecciones}" var="seccion">

                    <tr id ="${seccion.getID()}">
                      <td>
                        <input type="radio" name="controlSeccion" value="${seccion.getID()}">
                      </td>
                      <td>${seccion.getNombre_seccion()}</td>
                      <td>${seccion.getDescripcion()}</td>
                    </tr>

                  </c:forEach>

                </tbody>
              </table>
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- END WIDGET TICKET TABLE -->
      </div>
      <!-- /main-content -->
    </div>
    <!-- /main -->
    <div class="widget-content">
      <div class="modal fade" id="ModalEditarSeccion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
              <h4 class="modal-title" id="myModalLabel">Editar Sección</h4>
            </div>
            <div class="modal-body">

              <form class="form-horizontal" role="form" action="EditarSeccion" method="post">
                <input id="editarIdSeccion" hidden="true" name="editarIdSeccion">
                <label for="editarNombre" class="control-label">Nombre de la Sección</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input id="editarNombre" type="text" maxlength="45" placeholder="Nombre de la Sección" class="form-control" name="editarNombre" required
                             oninvalid="setCustomValidity('Este campo es requerido ')"
                             oninput="setCustomValidity('')" > 
                    </div>
                  </div>
                </div>
                <label for="editarDescripcion" class="control-label">Descripción</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input id="editarDescripcion" type="text" maxlength="500" placeholder="Drescripción de la Sección" class="form-control" name="editarDescripcion" required
                             oninvalid="setCustomValidity('Este campo es requerido ')"
                             oninput="setCustomValidity('')" > 
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar cambios</button>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>                            
    <div class="widget-content">
      <div class="modal fade" id="modalEliminarSeccion" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
              <h4 class="modal-title" id="myModalLabel">Confirmación</h4>
            </div>
            <div class="modal-body">
              <form class="form-horizontal" role="form" action="EliminarSeccion" method="post">
                <h5 class="title">¿Está seguro que desea eliminar la sección?</h5>
                <br><br>
                <input hidden="false" id="controlIDSeccion" name="controlIDSeccion">
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
    <div class="widget-content">
      <div class="modal fade" id="modalError" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-sm">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
              <h4 class="modal-title" id="myModalLabel">Error</h4>
            </div>
            <div class="modal-body">
              <h5>Debe seleccionar una sección.</h5>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cerrar</button>
            </div>
          </div>
        </div>
      </div>
    </div>        

  </jsp:attribute>

</t:plantilla_general>