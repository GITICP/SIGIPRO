<%-- 
    Document   : Editar
    Created on : Mar 28, 2015, 11:37:30 PM
    Author     : jespinozac95
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/IntencionVenta?">Solicitudes o Intenciones de Venta</a>
            </li>
            <li class="active">Solicitud o Intención de Venta ${intencion.getId_intencion()}</li>

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
              <h3><i class="fa fa-list-alt"></i> Editar Solicitud o Intención de Venta </h3>
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
            <label for="posibleFechaEntrega" class="control-label">*Posible Fecha de Despacho</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group" style="display:table;">
                  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="posibleFechaEntrega" class="form-control sigiproDatePickerEspecial" name="editarPosibleFechaDespacho" data-date-format="dd/mm/yyyy" required
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
                <p id='mensajeFechasModalAgregar' style='color:red;'><p>
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
            <label for="posibleFechaEntrega" class="control-label">*Posible Fecha de Despacho</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group" style="display:table;">
                  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarPosibleFechaDespacho" class="form-control sigiproDatePickerEspecial" name="editarPosibleFechaDespacho" data-date-format="dd/mm/yyyy" required
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
                <p id='mensajeFechasModalAgregar' style='color:red;'><p>
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

    <t:modal idModal="modalErrorFechaCantidad" titulo="Error">

      <jsp:attribute name="form">

        <h5>La cantidad a vender debe ser menor a la presente en stock. </h5>

        <div class="modal-footer">
          <button id="exitErrorFechaCantidad" type="button" data-dismiss="modal" class="btn btn-primary" ><i class="fa fa-check-circle"></i> Confirmar</button>
        </div>

      </jsp:attribute>

    </t:modal>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Intencion_venta.js"></script>
  </jsp:attribute>
        
</t:plantilla_general>

