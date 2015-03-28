<%-- 
    Document   : Formulario
    Created on : Jan 27, 2015, 2:08:39 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formCatalogoExterno" class="form-horizontal" autocomplete="off" method="post" action="CatalogoExterno">
  <div class="col-md-6">
    <input hidden="true" name="id_producto" value="${producto.getId_producto_ext()}">
    <input id="productosinternos" hidden="true" name="listaProductosInternos" value="">
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
          <input type="text" maxlength="45" placeholder="Ejemplo: 73b" class="form-control" name="codigoExterno" value="${producto.getCodigo_Externo()}"
                 > 
        </div>
      </div>
    </div>
  </div>
  <div class="col-md-6">
    <label for="marca" class="control-label">Marca</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <input type="text" maxlength="45" placeholder="" class="form-control" name="marca" value="${producto.getMarca()}"
                 > 
        </div>
      </div>
    </div>  
    <label for="proveedor" class="control-label">*Proveedor</label>
    <div class="form-group">
      <div class="col-sm-12">
        <div class="input-group">
          <select id="proveedor" class="select2" name="proveedor" style='background-color: #fff;' required
                 oninvalid="setCustomValidity('Este campo es requerido ')"
                 onchange="setCustomValidity('')" > 
            <option value='' ></option>
            <c:set var='varAccion' value="Agregar"/>
            <c:choose>
              <c:when test="${accion eq varAccion || producto.getId_Proveedor()==0}">
                <c:forEach items="${proveedores}" var="pr">
                  <option value=${pr.getId_proveedor()}>${pr.getNombre_proveedor()}</option>
                </c:forEach>
              </c:when>
              <c:otherwise>               
                <c:forEach items="${proveedores}" var="pr">              
                  <c:choose>
                    <c:when test="${pr.getId_proveedor() == producto.getId_Proveedor()}">
                      <option value=${pr.getId_proveedor()} selected>${pr.getNombre_proveedor()}</option>
                    </c:when>
                    <c:otherwise>
                      <option value=${pr.getId_proveedor()}>${pr.getNombre_proveedor()}</option>
                    </c:otherwise>       
                  </c:choose>
                </c:forEach>
              </c:otherwise>
            </c:choose>
          </select>
        </div>
      </div>
    </div>
  </div>
  <div class="col-md-12">
    <!-- Esta parte es la de los interno del catalogo externo -->
    <div class="widget widget-table">
      <div class="widget-header">
        <h3><i class="fa fa-check"></i> Productos del Catálogo Interno Asociados</h3>
        <div class="btn-group widget-header-toolbar">
          <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarCatalogoInterno">Agregar</a>
        </div>
      </div>
      <div class="widget-content">
        <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
          <thead>
            <tr>
              <th>Nombre y Código del Producto</th>
              <th>Eliminar</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${productos_internos}" var="interno">
              <tr id="${interno.getId_producto()}">
                <td>${interno.getNombre()}</td>
                <td>
                  <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarProductoInterno(${interno.getId_producto()})">Eliminar</button>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
    <p>
      Los campos marcados con * son requeridos.
    </p>  

    <div class="form-group">
      <div class="modal-footer">
        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
         <c:choose>
          <c:when test= "${accion.equals('Editar')}">
            <button type="button" class="btn btn-primary" onclick="confirmacionAgregarProductoExterno()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
          </c:when>
          <c:otherwise>
            <button type="button" class="btn btn-primary" onclick="confirmacionAgregarProductoExterno()"><i class="fa fa-check-circle"></i> ${accion} Producto</button>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>



</form>
