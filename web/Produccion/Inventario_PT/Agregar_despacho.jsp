<%-- 
    Document   : Agregar
    Created on : Feb 19, 2015, 7:59:26 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Despacho" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Inventario_PT?">Despachos</a>
            </li>
            <li class="active"> Nuevo Despacho </li>

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
              <h3><i class="fa fa-list-alt"></i> Agregar  Despacho </h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <jsp:include page="Formulario_despacho.jsp"></jsp:include>

              </div>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>


      <!-- Los modales de Agregar Inventario empiezan acá -->      
    <t:modal idModal="modalAgregarLote" titulo="Agregar Lote">
      <jsp:attribute name="form">
        <form class="form-horizontal" id="formAgregarRolUsuario">
          <input type="text" name="rol"  hidden="true">
          <label for="idrol" class="control-label">*Lote</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionRol'>
                <select id="seleccionLote" class="select2" style='background-color: #fff;' name="seleccionLote" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
                  <option value=''></option>
                  <c:forEach items="${lotes}" var="lote">
                    <option value=${lote.getId_inventario_pt()}>${lote.getLote()} (Producto: ${lote.getProducto().getNombre()}) (Disponible: ${lote.getCantidad_disponible()}) </option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <label for="cantidad" class="control-label">*Cantidad</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="number"  id="agregarcantidad" class="form-control" name="agregarcantidad"  required
                       oninvalid="setCustomValidity('Este campo es requerido. La cantidad debe ser mayor a uno')"
                       onchange="setCustomValidity('')">
              </div>
            </div>
          </div>

          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarRol()"><i class="fa fa-check-circle"></i> Agregar Lote</button>
            </div>
          </div>
        </form>
      </jsp:attribute>
    </t:modal>

    <t:modal idModal="modalEditarLote" titulo="Editar Lote">
      <jsp:attribute name="form">
        <form class="form-horizontal" id="formEditarRolUsuario">
          <input type="text" id="idRolUsuarioEditar"     name="idRolEditar"      hidden="true">
          <input type="text" name="rol"  hidden="true">
          <label for="cantidad" class="control-label">*Cantidad</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="number"  id="editarcantidad" class="form-control" name="editarcantidad"  required
                       oninvalid="setCustomValidity('Este campo es requerido. La cantidad debe ser mayor a uno')"
                       onchange="setCustomValidity('')">
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-editarRol" type="button" class="btn btn-primary" onclick="confirmarEdicion()"><i class="fa fa-check-circle"></i> Editar Lote</button>
            </div>
          </div>
        </form>
      </jsp:attribute>
    </t:modal>

  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/Produccion/Inventario/Seleccionar_Lotes.js"></script>
  </jsp:attribute>
</t:plantilla_general>

