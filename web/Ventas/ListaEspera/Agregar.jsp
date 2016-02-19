<%-- 
    Document   : Agregar
    Created on : Feb 11, 2016, 5:55:36 PM
    Author     : jespinozac95
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/ListaEspera?">Listas de Espera</a>
            </li>
            <li class="active"> Agregar Una Nueva Lista de Espera </li>

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
              <h3><i class="fa fa-file-text-o"></i> Agregar Una Nueva Lista de Espera </h3>
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
                    <option value="${producto.getId_producto()}" data-stock="${producto.getStock()}"> ${producto.getNombre()}</option>
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
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
            <button id="btn-editarProducto" type="button" class="btn btn-primary" onclick="confirmarEdicionProducto()"><i class="fa fa-check-circle"></i> Editar Producto</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>
              
    <t:modal idModal="modalAgregarHistorial" titulo="Agregar Historial">

      <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarHistorial">
          <input type="text" name="accion"  hidden="true">
          <label for="seleccionHistorial" class="control-label">*Historial</label>
          <div class="form-group">
              <div class="col-sm-12" id="InputSeleccionHistorial">
                  <input id="seleccionHistorial" type="text" style='background-color: #fff;' name="seleccionHistorial" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            <button id="btn-agregarHistorial" type="button" class="btn btn-primary" data-target="#modalAgregarHistorial" onclick="agregarHistorial()"><i class="fa fa-check-circle"></i> Agregar Historial</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>
              
    <t:modal idModal="modalEditarHistorial" titulo="Editar Historial">

      <jsp:attribute name="form">
        <form class="form-horizontal" id="formEditarHistorial">
          <input type="text" id="idHistorialEditar"     name="idHistorialEditar"      hidden="true">
          <input type="text" name="observacion"  hidden="true">
          <label for="cantidad" class="control-label">*Historial</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input id="editarHistorial" type="text" class="form-control" name="editarHistorial" value="" required
                    oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                    oninput="setCustomValidity('')"> 
              </div>
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
            <button id="btn-editarHistorial" type="button" class="btn btn-primary" onclick="confirmarEdicionHistorial()"><i class="fa fa-check-circle"></i> Editar Historial</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>
              
    <t:modal idModal="modalAgregarObservacion" titulo="Agregar Observacion">

      <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarObservacion">
          <input type="text" name="accion"  hidden="true">
          <label for="seleccionObservacion" class="control-label">Observacion</label>
          <div class="form-group">
            <div class="col-sm-12" id="InputSeleccionObservacion">
                  <input id="seleccionObservacion" type="text" style='background-color: #fff;' name="seleccionObservacion" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            <button id="btn-agregarObservacion" type="button" class="btn btn-primary" data-target="#modalAgregarObservacion" onclick="agregarObservacion()"><i class="fa fa-check-circle"></i> Agregar Observacion</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>

    <t:modal idModal="modalEditarObservacion" titulo="Editar Observación">

      <jsp:attribute name="form">
        <form class="form-horizontal" id="formEditarObservacion">
          <input type="text" id="idObservacionEditar"     name="idObservacionEditar"      hidden="true">
          <input type="text" name="observacion"  hidden="true">
          <label for="cantidad" class="control-label">*Observacion</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <input id="editarObservacion" type="text" class="form-control" name="editarObservacion" value="" required
                    oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                    oninput="setCustomValidity('')"> 
              </div>
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
            <button id="btn-editarObservacion" type="button" class="btn btn-primary" onclick="confirmarEdicionObservacion()"><i class="fa fa-check-circle"></i> Editar Observacion</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>
              
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/ListaEspera.js"></script>
  </jsp:attribute>

</t:plantilla_general>

