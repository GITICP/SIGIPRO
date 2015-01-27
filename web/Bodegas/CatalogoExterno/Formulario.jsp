<%-- 
    Document   : Formulario
    Created on : Jan 27, 2015, 2:08:39 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<form class="form-horizontal" autocomplete="off" method="post" action="CatalogoExterno">
  <div class="col-md-6">
    <input hidden="true" name="id_producto" value="${producto.getId_producto()}">
    <label for="producto" class="control-label">* Nombre del Producto</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="text" maxlength="45" placeholder="Nombre de Producto" class="form-control" name="producto" value="${producto.getProducto()}"
                 required
                 oninvalid="setCustomValidity('Este campo es requerido ')"
                 oninput="setCustomValidity('')" > 
        </div>
      </div>
    </div>
    <label for="codigoExterno" class="control-label"> Código Externo</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="text" maxlength="45" placeholder="Ejemplo: 73b" class="form-control" name="codigoExterno" value="${producto.getCodigo_externo()}"
                 > 
        </div>
      </div>
    </div>
    <label for="marca" class="control-label">Marca</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="number" maxlength="45" placeholder="0" class="form-control" name="marca" value="${producto.getMarca()}"
                 > 
        </div>
      </div>
    </div>  
    <label for="proveedor" class="control-label">Proveedor</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <select id="proveedor" class="form-control" name="proveedor" style='background-color: #fff;' >  
                <option value=${producto.getId_Proveedor} selected>${producto.getNombreProveedor}</option>
                <c:forEach items="${proveedores}" var="proveedor">
                  <option value=${proveedor.getId_proveedor()}>${proveedor.getNombre_proveedor()}</option>
                </c:forEach>
          </select>
        </div>
      </div>
    </div>
  </div>
  <p>
    Los campos marcados con * son requeridos.
  </p>  

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger" onclick="history.back()"><i class="fa fa-times-circle"></i> Cancelar</button>
      <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Producto</button>
    </div>
  </div>


</form>