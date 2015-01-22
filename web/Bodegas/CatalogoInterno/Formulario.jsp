<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<form class="form-horizontal" autocomplete="off" method="post" action="CatalogoInterno">
  <div class="col-md-6">
    <input hidden="true" name="id_producto" value="${producto.getId_producto()}">
    <label for="nombre" class="control-label">* Nombre del Producto</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
              <input type="text" maxlength="45" placeholder="Nombre de Producto" class="form-control" name="nombre" value="${producto.getNombre()}"
                       required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>
    <label for="codigoICP" class="control-label">* Código ICP</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
              <input type="text" maxlength="45" placeholder="73b" class="form-control" name="codigoICP" value="${producto.getCodigo_icp()}"
                       required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>
    <label for="stockMinimo" class="control-label">* Stock Mínimo</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
              <input type="text" maxlength="45" placeholder="50" class="form-control" name="stockMinimo" value="${producto.getStock_minimo()}"
                       required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>  
    <label for="stockMaximo" class="control-label">* Stock Mínimo</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
              <input type="text" maxlength="45" placeholder="50" class="form-control" name="stockMaximo" value="${producto.getStock_maximo()}"
                       required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>
  </div>
  <div class="col-md-6">
    <label for="ubicacion" class="control-label">* Ubicación</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
              <input type="text" maxlength="45" placeholder="F-21" class="form-control" name="ubicacion" value="${producto.getUbicacion()}"
                       required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>
    <label for="presentacion" class="control-label">* Presentación</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
              <input type="text" maxlength="45" placeholder="Paquete 25 unidades" class="form-control" name="presentacion" value="${producto.getPresentacion()}"
                       required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>
    <label for="descripcion" class="control-label">Descripción</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
              <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${producto.getDescripcion()}</textarea>
            </div>
        </div>
    </div>
  </div>               
                       
  <button type="submit" class="btn btn-primary bt">${accion} Producto</button>
</form>