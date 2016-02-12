<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Producto_ventas">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_producto" value="${producto.getId_producto()}">
      <input hidden="true" name="accion" value="${accion}">
            <label for="nombre" class="control-label"> *Nombre</label>
            <!-- Nombre -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="nombre" type="text" class="form-control" name="nombre" value="${producto.getNombre()}" required
                            oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                            oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
            <label for="descripcion" class="control-label"> Descripción</label>
            <!-- Descripción -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="descripcion" type="text" class="form-control" name="descripcion" value="${producto.getDescripcion()}"
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
    </div>                    
    <div class="col-md-6">
        <label for="stock" class="control-label"> *Cantidad en Stock</label>
            <div class="form-group">
              <div class="col-sm-12">
                <!-- Stock -->
                <div class="input-group">
                    <input id="stock" type="number" min="0" class="form-control" name="stock" value="${producto.getStock()}" required
                        oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                        oninput="setCustomValidity('')"> 
                </div>
              </div>
            </div>
        <label for="precio" class="control-label"> *Precio Unitario</label>
            <div class="form-group">
              <div class="col-sm-12">
                <!-- Precio -->
                <div class="input-group">
                    <input id="precio" type="number" min="0" class="form-control" name="precio" value="${producto.getPrecio()}" required
                        oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                        oninput="setCustomValidity('')"> 
                </div>
              </div>
            </div>
    </div>
  </div>

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Producto</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>