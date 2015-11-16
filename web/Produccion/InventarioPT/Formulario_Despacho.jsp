<%-- 
    Document   : Formulario_Despacho
    Created on : Nov 14, 2015, 1:57:03 PM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="form-despacho" class="form-horizontal" autocomplete="off" method="post" action="InventarioPT">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_despacho" value="${despacho.getId_despacho()}">
      <input hidden="true" name="accion" value="${accion}">

      <label for="identificacion" class="control-label">Destino</label>
      <div class="form-group">
        <div class="col-md-12">
          <div class="input-group">
            <input  type="text" id="destino" value="${despacho.getDestino()}" name="destino" required class="form-control"
                    oninvalid="setCustomValidity('Este campo es requerido. Por favor ingrese un número mayor a 1 ')"
                    onchange="setCustomValidity('')">
          </div>
        </div>
      </div>
      <label for="observaciones" class="control-label">Observaciones</label>
      <div class="form-group">
        <div class="col-sm-12">
          <div class="input-group">
            <textarea rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" name="observaciones">${despacho.getObservaciones()}</textarea>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="widget widget-table">
    <div class="widget-header">
      <h3><i class="fa fa-check"></i> Seleccion de Lotes y Cantidades</h3>
      <div class="btn-group widget-header-toolbar">
        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarPermisoRol">Agregar</a>
      </div>
    </div>
    <div class="widget-content">
      <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
        <thead>
          <tr>
            <th>Fecha Limite</th>
            <th>Observaciones</th>
            <th>Cantidad Total Reservada</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${rolesUsuario}" var="rolUsuario">
            <tr id="${rolUsuario.getIDUsuario()}">
              <td>${rolUsuario.getNombreUsuario()}</td>
              <td>${rolUsuario.getFechaActivacion()}</td>
              <td>${rolUsuario.getFechaDesactivacion()}</td>
              <td>
                <button type="button" class="btn btn-warning btn-sm boton-accion" onclick="editarRolUsuario(${rolUsuario.getIDUsuario()})"   >Editar</button>
                <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarRolUsuario(${rolUsuario.getIDUsuario()})" >Eliminar</button>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  <div class="col-md-12">
    <p>
      Los campos marcados con * son requeridos.
    </p>  
    <div class="row">
      <div class="form-group">
        <div class="modal-footer">
          <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
          <c:choose>
            <c:when test= "${accion.equals('Editar')}">
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </c:when>
            <c:otherwise>
              <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Despacho</button>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
</form>    
  <!-- Los modales de Editar Usuarios empiezan acá -->      
    <t:modal idModal="modalAgregarRolUsuario" titulo="Agregar Usuario">

        <form class="form-horizontal" id="formAgregarRolUsuario">
          <input type="text" name="rol"  hidden="true">
          <label for="idrol" class="control-label">*Usuario</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionRol'>
                <select id="seleccionRol" class="select2" style='background-color: #fff;' name="idrol" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
                <option value=''></option>
                  <c:forEach items="${usuariosRestantes}" var="rol">
                    <option value=${rol.getID()}>${rol.getNombreCompleto()} (${rol.getNombreUsuario()}) </option>
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
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="agregarFechaActivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaActivacion" data-date-format="dd/mm/yyyy" required
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
                  <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="agregarFechaDesactivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaDesactivacion" data-date-format="dd/mm/yyyy" required
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
                <p id='mensajeFechasModalAgregar' style='color:red;'><p>
              </div>
            </div>
          </div>

          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarRol()"><i class="fa fa-check-circle"></i> Agregar Usuario</button>
            </div>
          </div>
        </form>

    </t:modal>

    <t:modal idModal="modalEditarRolUsuario" titulo="Editar Usuario">
        <form class="form-horizontal" id="formEditarRolUsuario">
          <input type="text" id="idRolUsuarioEditar"     name="idRolEditar"      hidden="true">
          <input type="text" name="rol"  hidden="true">
          <label for="fechaActivacion" class="control-label">*Fecha de Activación</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group"  style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarFechaActivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaActivacion" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
              </div>
            </div>
          </div>
          <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group"  style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarFechaDesactivacion" class="form-control sigiproDatePickerEspecial" name="editarFechaDesactivacion" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
              </div>
              <p id='mensajeFechasModalEditar' style='color:red;'><p>
            </div>
          </div>
          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-editarRol" type="button" class="btn btn-primary" onclick="confirmarEdicion()"><i class="fa fa-check-circle"></i> Editar Rol</button>
            </div>
          </div>
        </form>

    </t:modal>

    <!-- Los modales de Editar Usuarios terminan acá -->
