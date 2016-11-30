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
              <a href="/SIGIPRO/Ventas/SeguimientoVenta?">Seguimientos de Venta</a>
            </li>
            <li class="active"> Editar Seguimiento de Venta </li>

          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-gears"></i> Editar Seguimiento de Venta </h3>
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
              <t:modal idModal="modalAgregarProducto" titulo="Agregar Acción">

      <jsp:attribute name="form">
        <form class="form-horizontal" id="formAgregarProducto">
          <input type="text" id="idProductoEditar"     name="idProductoEditar" hidden>
          <input type="text" name="producto"  hidden>
          <label for="cantidad" class="control-label">*Tipo</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                  <select id="agregarCantidad" class="select2" name="editarCantidad" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${tipos}" var="tipo">
                                <option value="${tipo}"> ${tipo}</option>
                          </c:forEach>
                        </select>
              </div>
            </div>
          </div>
            <label for="posibleFechaEntrega" class="control-label"> *Fecha</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group" style="display:table;">
                  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="agregarPosibleFechaDespacho" required class="form-control sigiproDatePickerEspecial" name="editarPosibleFechaDespacho" data-date-format="dd/mm/yyyy"
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
              </div>
            </div>
            <label for="cantidad" class="control-label"> *Observaciones</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                  <input id="agregarObservaciones" type="text" maxlength="499" required class="form-control" name="editarObservaciones" value=""
                    oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                    oninput="setCustomValidity('')"> 
                
              </div>
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
            <button id="btn-editarProducto" type="button" class="btn btn-primary" onclick="confirmarAgregar()"><i class="fa fa-check-circle"></i> Agregar Producto</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>
              <t:modal idModal="modalEditarProducto" titulo="Editar Acción">

      <jsp:attribute name="form">
        <form class="form-horizontal" id="formEditarProducto">
          <input type="text" id="idProductoEditar"     name="idProductoEditar" hidden>
          <input type="text" name="producto"  hidden>
          <label for="cantidad" class="control-label">*Tipo</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                  <select id="editarCantidad" class="select2" name="editarCantidad" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                          <c:forEach items="${tipos}" var="tipo">
                                <option value="${tipo}"> ${tipo}</option>
                          </c:forEach>
                        </select>
              </div>
            </div>
          </div>
            <label for="posibleFechaEntrega" class="control-label"> *Fecha</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group" style="display:table;">
                  <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                  <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editarPosibleFechaDespacho" required class="form-control sigiproDatePickerEspecial" name="editarPosibleFechaDespacho" data-date-format="dd/mm/yyyy"
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
              </div>
            </div>
            <label for="cantidad" class="control-label"> *Observaciones</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                  <input id="editarObservaciones" type="text" maxlength="499" required class="form-control" name="editarObservaciones" value=""
                    oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                    oninput="setCustomValidity('')"> 
                
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
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Seguimiento_venta.js"></script>
  </jsp:attribute>

</t:plantilla_general>

