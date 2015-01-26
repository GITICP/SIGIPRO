<%-- 
    Document   : Editar Usuario
    Created on : Dec 14, 2014, 11:44:07 AM
    Author     : Boga
--%>

<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


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
            <li class="active"> Editar ${usuario.getNombreUsuario()} </li>

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
                <c:if test="${actividad}">
                <a class="btn btn-danger btn-sm"  style="margin-left:5px;margin-right:5px;color:#fff;" >Desactivar</a>   
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <form id="editarUsuario" class="form-horizontal" autocomplete="off" role="form" action="Editar" method="post">

                <p class="title">Editar Usuario</p>
                <div class="row">
                  <div class="col-md-6">
                    <input id="editarIDUsuario" hidden="true" name="editarIDUsuario" value="${usuario.getID()}">
                    <input id="rolesUsuario" hidden="true" name="listaRolesUsuario" value="">
                    <label for="nombreUsuario" class="control-label">*Nombre de Usuario</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <input id="nombreUsuario"   type="text" value="${usuario.getNombreUsuario()}" maxlength="45" placeholder="Nombre de Usuario" class="form-control" name="nombreUsuario" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')" disabled> 
                        </div>
                      </div>
                    </div>
                    <label for="nombreCompleto" class="control-label">*Nombre Completo</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <input type="text" value="${usuario.getNombreCompleto()}" maxlength="200" placeholder="Nombre Completo" class="form-control" name="nombreCompleto" id="nombreCompleto"required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                    <label for="correoElectronico" class="control-label">*Correo Electrónico</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <%--<span class="input-group-addon"><i class="fa fa-at"></i></span>           SE ELIMINA EL ICONO --%>
                          <input type="email" maxlength="45" value="${usuario.getCorreo()}" placeholder="usuario@icp.ucr.ac.cr" class="form-control"  name="correoElectronico" id="correoElectronico" required
                                 oninvalid="setCustomValidity('Este campo es requerido, por favor introduzca un correo electrónico válido')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                    <label for="cedula" class="control-label">*Cédula</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <%--<span class="input-group-addon"><i class="fa fa-at"></i></span>           SE ELIMINA EL ICONO --%>
                          <input type="text" value="${usuario.getCedula()}" placeholder="1-0001-4628" pattern="[0-9]{1}-[0-9]{4}-[0-9]{4}" class="form-control"  name="cedula" id="cedula" required
                                 oninvalid="setCustomValidity('Este campo es requerido, por favor introduzca una cédula válida')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label for="seccion" class="control-label">*Sección</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <%--<span class="input-group-addon"><i class="fa fa-at"></i></span>           SE ELIMINA EL ICONO --%>
                          <select id="seleccionSeccion" class="form-control" name="seccion" required
                                  oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' >
                            <c:forEach items="${secciones}" var="seccion">
                              <c:choose>
                                <c:when test="${seccion.getID() == usuario.getIdSeccion()}" >
                                  <option value=${seccion.getID()} selected> ${seccion.getNombreSeccion()}</option>
                                </c:when>
                                <c:otherwise>
                                  <option value=${seccion.getID()}>${seccion.getNombreSeccion()}</option>
                                </c:otherwise>
                              </c:choose>
                            </c:forEach>
                          </select>
                        </div>
                      </div>
                    </div>
                    <label for="puesto" class="control-label">*Puesto</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <%--<span class="input-group-addon"><i class="fa fa-at"></i></span>           SE ELIMINA EL ICONO --%>
                          <input type="text" value="${usuario.getPuesto()}" maxlength="200" placeholder="Jefe" class="form-control"  name="puesto" id="puesto" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                    <label for="fechaActivacion" class="control-label">*Fecha de Activación</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <%--<span class="input-group-addon"><i class="fa fa-calendar"></i></span>           SE ELIMINA EL ICONO --%>
                          <input type="text" value="${usuario.getFechaActivacion()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fechaActivacion" class="form-control sigiproDatePicker" name="fechaActivacion" data-date-format="dd/mm/yyyy" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 onchange="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                    <%--title="Fecha de Desactivación: Si desea un usuario permanente, introduzca la misma fecha de activación" --%>             
                    <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <%--<span class="input-group-addon"><i class="fa fa-calendar"></i></span>           SE ELIMINA EL ICONO --%>
                          <input type="text" value="${usuario.getFechaDesactivacion()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fechaDesactivacion" class="form-control sigiproDatePicker" name="fechaDesactivacion" data-date-format="dd/mm/yyyy" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 onchange="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                    
                  </div>
                </div>
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-legal"></i> Roles</h3>
                    <div class="btn-group widget-header-toolbar">
                      <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarRolUsuario">Agregar</a>
                    </div>
                  </div>
                  <div class="widget-content">
                    <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                      <thead>
                        <tr>
                          <th>Nombre Rol</th>
                          <th>Fecha Activación</th>
                          <th>Fecha Desactivación</th>
                          <th>Editar/Eliminar</th>
                        </tr>
                      </thead>
                      <tbody>
                        <c:forEach items="${rolesUsuario}" var="rolUsuario">
                          <tr id="${rolUsuario.getIDRol()}">
                            <td>${rolUsuario.getNombreRol()}</td>
                            <td>${rolUsuario.getFechaActivacion()}</td>
                            <td>${rolUsuario.getFechaDesactivacion()}</td>
                            <td>
                              <button type="button" class="btn btn-primary btn-sm boton-accion" onclick="editarRolUsuario(${rolUsuario.getIDRol()})"   >Editar</button>
                              <button type="button" class="btn btn-primary btn-sm boton-accion" onclick="eliminarRolUsuario(${rolUsuario.getIDRol()})" >Eliminar</button>
                            </td>
                          </tr>
                        </c:forEach>
                      </tbody>
                    </table>
                  </div>
                </div>
                <div class="form-group">
                  <div class="modal-footer">
                    <button type="button" class="btn btn-danger" onclick="history.back()" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                    <button type="button" class="btn btn-primary" onclick="confirmacion()"><i class="fa fa-check-circle"></i> Confirmar Cambios </button>
                  </div>
                </div>
              </form>
            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

    <t:modal idModal="modalAgregarRolUsuario" titulo="Agregar Rol">

      <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarRolUsuario">
          <input type="text" name="rol"  hidden="true">
          <label for="idrol" class="control-label">*Rol</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <select id="seleccionRol" class="form-control" name="idrol" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        oninput="setCustomValidity('')" style='background-color: #fff;'>
                  <c:forEach items="${rolesRestantes}" var="rol">
                    <option value=${rol.getID()}>${rol.getNombreRol()}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <label for="fechaActivacion" class="control-label">*Fecha de Activación</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="agregarFechaActivacion" class="form-control sigiproDatePicker" name="editarFechaActivacion" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
              </div>
            </div>
          </div>
          <div title="Fecha de Desactivación: Si desea un rol permanente, introduzca la misma fecha de activación">
            <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group" style="display:table;">
                  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="agregarFechaDesactivacion" class="form-control sigiproDatePicker" name="editarFechaDesactivacion" data-date-format="dd/mm/yyyy" required
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
              </div>
            </div>
          </div>

          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarRol()"><i class="fa fa-check-circle"></i> Agregar Rol</button>
            </div>
          </div>
        </form>

      </jsp:attribute>

    </t:modal>

    <t:modal idModal="modalEditarRolUsuario" titulo="Editar Rol">

      <jsp:attribute name="form">
        <form class="form-horizontal" id="formEditarRolUsuario">
          <input type="text" id="idRolUsuarioEditar"     name="idRolEditar"      hidden="true">
          <input type="text" name="rol"  hidden="true">
          <label for="nombreUsuario" class="control-label">*Rol</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <select id="seleccionRol" class="form-control" style='background-color: #fff;' name="idrol" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        oninput="setCustomValidity('')">
                  <c:forEach items="${rolesRestantes}" var="rol">
                    <option value=${rol.getID()}>${rol.getNombreRol()}</option>
                  </c:forEach>
                </select>

              </div>
            </div>
          </div>
          <label for="fechaActivacion" class="control-label">*Fecha de Activación</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarFechaActivacion" class="form-control sigiproDatePicker" name="editarFechaActivacion" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
              </div>
            </div>
          </div>
          <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarFechaDesactivacion" class="form-control sigiproDatePicker" name="editarFechaDesactivacion" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-editarRol" type="button" class="btn btn-primary" onclick="confirmarEdicion()"><i class="fa fa-check-circle"></i> Editar Rol</button>
            </div>
          </div>
        </form>

      </jsp:attribute>

    </t:modal>
          
    <t:modal idModal="modalErrorFechaDesactivacion" titulo="Error">

      <jsp:attribute name="form">

        <h5>Las fechas de activación y desactivación deben ser iguales o posteriores a la de hoy. Además, la fecha de desactivación debe ser posterior o igual a la fecha de activación. </h5>

        <div class="modal-footer">
          <button id="exitErrorFechaDesactivacion" type="button" data-dismiss="modal" class="btn btn-primary" ><i class="fa fa-check-circle"></i> Confirmar</button>
        </div>

      </jsp:attribute>

    </t:modal>
  </jsp:attribute>

</t:plantilla_general>
