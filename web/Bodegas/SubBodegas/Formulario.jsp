<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="subbodegaForm" class="form-horizontal" autocomplete="off" method="post" action="SubBodegas">

    <div class="row">

        <div class="col-md-6">
            <input hidden="true" name="accion" value="${accion.toLowerCase()}">
            <input hidden="true" name="id_sub_bodega" value="${sub_bodega.getId_sub_bodega()}">
            <input id="ids-ingresos" hidden="true" name="ids-ingresos" value="">
            <input id="ids-egresos"  hidden="true" name="ids-egresos" value="">
            <input id="ids-ver"  hidden="true" name="ids-ver" value="">
            <label for="nombre" class="control-label">* Nombre</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" value="${sub_bodega.getNombre()}" class="form-control" name="nombre" required
                               oninvalid="setCustomValidity('Este campo es requerido ')" placeholder="Nombre de Sub Bodega"
                               onchange="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="seccion" class="control-label">* Sección</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionSeccion" class="select2" style='background-color: #fff;' name="seccion" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:forEach items="${secciones}" var="seccion">
                              <c:choose>
                                <c:when test="${seccion.getId_seccion() == sub_bodega.getSeccion().getId_seccion()}" >
                                  <option value=${seccion.getId_seccion()} selected> ${seccion.getNombre_seccion()}</option>
                                </c:when>
                                <c:otherwise>
                                  <option value=${seccion.getId_seccion()}>${seccion.getNombre_seccion()}</option>
                                </c:otherwise>
                              </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="usuario" class="control-label">* Usuario Encargado</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionUsuario" class="select2" style='background-color: #fff;' name="usuario" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:forEach items="${usuarios}" var="usuario">
                              <c:choose>
                                <c:when test="${usuario.getId_usuario() == sub_bodega.getUsuario().getId_usuario()}" >
                                  <option value=${usuario.getId_usuario()} selected>${usuario.getNombre_completo()} (${usuario.getNombre_usuario()})</option>
                                </c:when>
                                <c:otherwise>
                                  <option value=${usuario.getId_usuario()}>${usuario.getNombre_completo()} (${usuario.getNombre_usuario()})</option>
                                </c:otherwise>
                              </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <br>
    <br>
    <div class="row">
        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-sign-in"></i> Usuarios con permiso de ingresar artículos a sub bodega</h3>
                    <div class="btn-group widget-header-toolbar">
                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarUsuarioIngresos">Agregar</a>
                    </div>
                </div>
                <div class="widget-content">
                    <table id="ingresos-sub-bodegas" class="table">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuarios_ingresos}" var="usuario">
                                <tr id="ingreso-${usuario.getId_usuario()}">
                                    <td>${usuario.getNombre_completo()}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarUsuarioIngreso(${usuario.getId_usuario()})" >Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-sign-out"></i> Usuarios con permiso de sacar artículos de sub bodega</h3>
                    <div class="btn-group widget-header-toolbar">
                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarUsuarioEgresos">Agregar</a>
                    </div>
                </div>
                <div class="widget-content">
                    <table id="egresos-sub-bodegas" class="table">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuarios_egresos}" var="usuario">
                                <tr id="egreso-${usuario.getId_usuario()}">
                                    <td>${usuario.getNombre_completo()}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarUsuarioEgreso(${usuario.getId_usuario()})" >Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-eye"></i> Usuarios con permiso de ver inventario de sub bodega</h3>
                    <div class="btn-group widget-header-toolbar">
                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarUsuarioVer">Agregar</a>
                    </div>
                </div>
                <div class="widget-content">
                    <table id="ver-sub-bodegas" class="table">
                        <thead>
                            <tr>
                                <th>Usuario</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${usuarios_ver}" var="usuario">
                                <tr id="ver-${usuario.getId_usuario()}">
                                    <td>${usuario.getNombre_completo()}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarUsuarioVer(${usuario.getId_usuario()})" >Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <span class="campos-requeridos">
        Los campos marcados con * son requeridos.
    </span>
    <br>
    <br>

    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Sub Bodega</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form>

<t:modal idModal="modalAgregarUsuarioIngresos" titulo="Agregar Usuario a Ingresos de Sub Bodega">

    <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarUsuarioIngresos">
            <label for="id-usuario" class="control-label">*Usuario</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccion-usuario-ingreso" class="select2" style='background-color: #fff;' name="id-usuario" multiple="multiple" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:forEach items="${usuarios}" var="usuario">
                                <option value=${usuario.getId_usuario()}>${usuario.getNombre_completo()} (${usuario.getNombre_usuario()})</option>
                            </c:forEach> 
                        </select>
                    </div>
                </div>
            </div>
        </form>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button id="btn-agregar-ubicacion" type="button" class="btn btn-primary" onclick="agregarUsuarioIngresos()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </div>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAgregarUsuarioEgresos" titulo="Agregar Usuario a Egresos de Sub Bodega">

    <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarUsuarioEgresos">
            <label for="id-usuario" class="control-label">*Usuario</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccion-usuario-egreso" class="select2" style='background-color: #fff;' name="id-usuario" multiple="multiple" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:forEach items="${usuarios}" var="usuario">
                                <option value=${usuario.getId_usuario()}>${usuario.getNombre_completo()} (${usuario.getNombre_usuario()})</option>
                            </c:forEach> 
                        </select>
                    </div>
                </div>
            </div>
        </form>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button id="btn-agregar-ubicacion" type="button" class="btn btn-primary" onclick="agregarUsuarioEgresos()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </div>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAgregarUsuarioVer" titulo="Agregar Usuario a Ver inventario de Sub Bodega">

    <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarUsuarioVer">
            <label for="id-usuario" class="control-label">*Usuario</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccion-usuario-ver" class="select2" style='background-color: #fff;' name="id-usuario" multiple="multiple" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:forEach items="${usuarios}" var="usuario">
                                <option value=${usuario.getId_usuario()}>${usuario.getNombre_completo()} (${usuario.getNombre_usuario()})</option>
                            </c:forEach> 
                        </select>
                    </div>
                </div>
            </div>
        </form>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button id="btn-agregar-ubicacion" type="button" class="btn btn-primary" onclick="agregarUsuarioVer()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </div>
        </div>

    </jsp:attribute>

</t:modal>
