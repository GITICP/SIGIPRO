<%-- 
    Document   : Editar
    Created on : Jun 29, 2015, 5:02:44 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Factura?">Facturas</a>
            </li>
            <li class="active"> Editar Factura </li>

          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-gears"></i> Editar Factura </h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <jsp:include page="Formulario.jsp"></jsp:include>

              </div>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>
      <!-- Los modales de Agregar Productos empiezan acá -->      
    <t:modal idModal="modalAgregarProducto" titulo="Agregar Producto">

      <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarProducto">
          <input type="text" name="producto"  hidden="true">
          <label for="id_producto" class="control-label">*Producto</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionProducto'>
                <select id="seleccionProducto" class="select2" style='background-color: #fff;' name="seleccionProducto" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
                  <option value=''></option>
                  <c:forEach items="${productos}" var="producto">
                    <option value="${producto.getId_producto()}"> ${producto.getNombre()}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <label for="cantidad" class="control-label">*Cantidad</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input id="cantidad" type="number" min="0" class="form-control" name="editarCantidad" value="" required
                       oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                       oninput="setCustomValidity('')"> 
              </div>
            </div>
          </div>
          <label for="posibleFechaEntrega" class="control-label"> Fecha de Despacho</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="posibleFechaEntrega" class="form-control sigiproDatePickerEspecial" name="editarPosibleFechaDespacho" data-date-format="dd/mm/yyyy"
                       >
              </div>
              <p id='mensajeFechasModalAgregar' style='color:red;'><p>
            </div>
          </div>
          <label for="lotes" class="control-label"> Selección Lotes</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionLotes'>
                <select id="seleccionLote" class="select2" style='background-color: #fff;' name="seleccionLote" >
                  <option value=''></option>
                  <c:forEach items="${lotes}" var="lote">
                    <option value="${lote.getNombre()}"> ${lote.getNombre()}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <label for="arealotes" class="control-label"> Lotes</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupLotes'>
                <textarea  style="width:100%" name="lotes" id="lotes"></textarea>
              </div>
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            <button id="btn-agregarProducto" type="button" class="btn btn-primary" data-target="#modalAgregarProducto" onclick="agregarProducto()"><i class="fa fa-check-circle"></i> Agregar Producto</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>

    <t:modal idModal="modalEditarProducto" titulo="Editar Producto">

      <jsp:attribute name="form">
        <form class="form-horizontal" id="formEditarProducto">
          <input type="text" id="idProductoEditar"     name="idProductoEditar"      hidden="true">
          <input type="text" name="producto"  hidden="true">
          <label for="cantidad" class="control-label">*Cantidad</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input id="editarCantidad" type="number" min="0" class="form-control" name="editarCantidad" value="" required
                       oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                       oninput="setCustomValidity('')"> 
              </div>
            </div>
          </div>
          <label for="posibleFechaEntrega" class="control-label"> Posible Fecha de Despacho</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" style="display:table;">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarPosibleFechaDespacho" class="form-control sigiproDatePickerEspecial" name="editarPosibleFechaDespacho" data-date-format="dd/mm/yyyy"
                       >
              </div>
              <p id='mensajeFechasModalAgregar' style='color:red;'><p>
            </div>
          </div>
          <label for="lotes" class="control-label"> Selección Lotes</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionLotes'>
                <select id="editarSeleccionLote" class="select2" style='background-color: #fff;' name="seleccionLote" >
                  <option value=''></option>
                  <c:forEach items="${lotes}" var="lote">
                    <option value="${lote.getNombre()}"> ${lote.getNombre()}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <label for="arealotes" class="control-label"> Lotes</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupLotes'>
                <textarea  style="width:100%" name="lotes" id="editarLotes"></textarea>
              </div>
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
            <button id="btn-editarProducto" type="button" class="btn btn-primary" onclick="confirmarEdicionProducto()"><i class="fa fa-check-circle"></i> Editar Producto</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>

    <!-- Los modales de Editar Roles terminan acá -->
  </jsp:attribute>

</t:plantilla_general>

