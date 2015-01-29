<%-- 
    Document   : Formulario
    Created on : 19-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<form class="form-horizontal" autocomplete="off" method="post" action="Proveedores">
  <div class='col-md-6'>
    <input hidden="true" name="id_proveedor" value="${proveedor.getId_proveedor()}">
    <label for="nombreProveedor" class="control-label">*Nombre de Proveedor</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="text" maxlength="45" placeholder="Nombre de Proveedor" class="form-control" name="nombreProveedor" value="${proveedor.getNombre_proveedor()}"
                 required
                 oninvalid="setCustomValidity('Este campo es requerido ')"
                 oninput="setCustomValidity('')" > 
        </div>
      </div>
    </div>
    <label for="correo" class="control-label">*Correo electrónico</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="email" maxlength="45" placeholder="proveedor@ejemplo.com" class="form-control" name="correo" value="${proveedor.getCorreo()}"
                 required
                 oninvalid="setCustomValidity('Este campo es requerido ')"
                 oninput="setCustomValidity('')" > 
        </div>
      </div>
    </div>
  </div>
  <div class='col-md-6'>
    <label for="telefono1" class="control-label">*Teléfono 1</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="text" maxlength="45" placeholder="2267-0000" class="form-control" name="telefono1" value="${proveedor.getTelefono1()}"
                 required
                 oninvalid="setCustomValidity('Este campo es requerido ')"
                 oninput="setCustomValidity('')" > 
        </div>
      </div>
    </div>
    <label for="telefono2" class="control-label">*Teléfono 2</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="text" maxlength="45" placeholder="2267-0000" class="form-control" name="telefono2" value="${proveedor.getTelefono2()}"
                 oninvalid="setCustomValidity('Este campo es requerido ')"
                 oninput="setCustomValidity('')" > 
        </div>
      </div>
    </div>
    <label for="telefono3" class="control-label">Teléfono 3</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="text" maxlength="45" placeholder="2267-0000" class="form-control" name="telefono3" value="${proveedor.getTelefono3()}"
                 oninvalid="setCustomValidity('Este campo es requerido ')"
                 oninput="setCustomValidity('')" > 
        </div>
      </div>
    </div>
  </div>

  <p>
    Los campos marcados con * son requeridos.
  </p>  

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
      <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Producto</button>
    </div>
  </div>

</form>