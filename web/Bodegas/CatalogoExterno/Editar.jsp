<%-- 
    Document   : Editar
    Created on : Jan 27, 2015, 2:08:56 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bodegas</li>
            <li> 
              <a href="/SIGIPRO/Bodegas/CatalogoExterno?">Catálogo Externo</a>
            </li>
            <li class="active">${producto.getProducto()} (${producto.getCodigo_Externo()})</li>

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
              <h3><i class="fa fa-barcode"></i> Editar Producto </h3>
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
    <t:modal idModal="modalAgregarCatalogoInterno" titulo="Asociar Producto Interno">

      <jsp:attribute name="form">

        <form class="form-horizontal">
          <input type="text" name="productoexterno"  hidden="true">
          <label for="idinterno" class="control-label">Seleccione un producto del catálogo interno:</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group">
                <select id="seleccioninterno" class="form-control" style='background-color: #fff;' name="idinterno" >
                  <c:forEach items="${productos_internos_restantes}" var="pr">
                    <option value=${pr.getId_producto()}>${pr.getNombre()} (${pr.getCodigo_icp()})</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarProductoInterno()"><i class="fa fa-check-circle"></i> Asociar Producto Interno</button>
            </div>
          </div>
        </form>

      </jsp:attribute>

    </t:modal>

  </jsp:attribute>

</t:plantilla_general>
