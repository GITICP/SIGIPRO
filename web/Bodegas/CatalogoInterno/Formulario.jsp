<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form id="catInternoForm" class="form-horizontal" autocomplete="off" method="post" action="CatalogoInterno">
    <div class="row">
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
                        <input type="text" maxlength="45" placeholder="Ejemplo: 73b" class="form-control" name="codigoICP" value="${producto.getCodigo_icp()}"
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
                        <input id="stockMinimo" maxlength="45" placeholder="0" class="form-control campo-numero" name="stockMinimo" value="${producto.getStock_minimo()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" ><p id="errorStockMinimo" class="error-form"></p>
                    </div>
                </div>
            </div>  
            <label for="stockMaximo" class="control-label">* Stock Máximo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="stockMaximo" maxlength="45" placeholder="0" class="form-control campo-numero" name="stockMaximo" value="${producto.getStock_maximo()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" ><p id="errorStockMaximo" class="error-form"></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
            <label for="presentacion" class="control-label">* Presentación</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Ejemplo: Paquete 25 unidades" class="form-control" name="presentacion" value="${producto.getPresentacion()}"
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
            <div class="widget widget-table cuadro-opciones">
                <div class="widget-header">
                    <h3><i class="fa fa-flask"></i> Opciones Avanzadas </h3>
                </div>
                <div class="widget-content">
                    <!--<label for="cuarentena" class="control-label">Opciones</label>-->
                    <div class="form-group opciones">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <c:set var="checkedPerecedero" value="" />
                                <c:set var="checkedCuarentena" value="" />
                                <c:set var="checkedReactivo" value="false" />
                                <c:set var="formReactivo" value="hidden" />
                                <c:if test="${producto.isPerecedero()}">
                                    <c:set var="checkedPerecedero" value="checked" />
                                </c:if>
                                <c:if test="${producto.isCuarentena()}">
                                    <c:set var="checkedCuarentena" value="checked" />
                                </c:if>
                                <c:if test="${producto.getReactivo() != null}">
                                    <c:set var="checkedReactivo" value="checked" />
                                    <c:set var="formReactivo" value="" />
                                </c:if>
                                <input type="checkbox" name="perecedero" value="true" ${checkedCuarentena}><span>  Es perecedero.</span>
                                <br>
                                <input type="checkbox" name="cuarentena" value="true" ${checkedCuarentena}><span>  Ingresa por defecto en cuarentena.</span>
                                <br>
                                <input id="check-reactivo" type="checkbox" name="reactivo" value="true" ${checkedReactivo}><span>  Es un reactivo.</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>      
        </div>
    </div>

    <div id="form-reactivo" class="row" ${formReactivo} style="padding:1em;">
        <div class="widget widget-table">
            <div class="widget-header">
                <h3><i class="fa fa-flask"></i> Información Reactivo</h3>
            </div>
            <div class="widget-content">

                <div class="col-md-6">
                    <label for="numero_cas" class="control-label">* Número Cas</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" maxlength="45" placeholder="" class="form-control campo-reactivo" name="numero_cas" value="${producto.getReactivo().getNumero_cas()}"
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')" > 
                            </div>
                        </div>
                    </div>
                    <label for="formula_quimica" class="control-label">* Fórmula Química</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" maxlength="45" placeholder="Ejemplo: 12H2O" class="form-control campo-reactivo" name="formula_quimica" value="${producto.getReactivo().getFormula_quimica()}"
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')" > 
                            </div>
                        </div>
                    </div>
                    <label for="familia" class="control-label">* Familia</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" maxlength="45" placeholder="Ejemplo: sulfato de amonio y aluminio " class="form-control campo-reactivo" name="familia" value="${producto.getReactivo().getFamilia()}"
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')" > 
                            </div>
                        </div>
                    </div>
                    <label for="cantidad_botella_bodega" class="control-label">* Cantidad Botella Bodega</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" id=cantBodega maxlength="45" placeholder="Ejemplo: 25" class="form-control campo-reactivo campo-numero" name="cantidad_botella_bodega" value="${producto.getReactivo().getCantidad_botella_bodega()}"
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')" ><p id="errorCantBodega" class="error-form"></p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <label for="cantidad_botella_lab" class="control-label">* Cantidad Botella Laboratorio</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" id=cantLab maxlength="45" placeholder="Ejemplo: 40" class="form-control campo-reactivo campo-numero" name="cantidad_botella_lab" value="${producto.getReactivo().getCantidad_botella_lab()}"
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')" ><p id="errorCantLab" class="error-form"></p>
                            </div>
                        </div>
                    </div>
                    <label for="volumen_bodega" class="control-label">* Volumen Bodega</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" maxlength="45" placeholder="Ejemplo: 10cm3" class="form-control campo-reactivo" name="volumen_bodega" value="${producto.getReactivo().getVolumen_bodega()}"
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')" > 
                            </div>
                        </div>
                    </div>
                    <label for="volumen_lab" class="control-label">* Volumen Laboratorio</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="text" maxlength="45" placeholder="Ejemplo: 5cm3" class="form-control campo-reactivo" name="volumen_lab" value="${producto.getReactivo().getVolumen_lab()}"
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')" > 
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Esta parte es la de los permisos de un rol -->
    <span>
        Los campos marcados con * son requeridos.
    </span>
    <br>
    <br>

    <div class="row">
        <div class="col-md-6">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-map-marker"></i> Ubicaciones</h3>
                    <div class="btn-group widget-header-toolbar">
                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarUbicacion">Agregar</a>
                    </div>
                </div>
                <div class="widget-content">
                    <table id="datatable-column-filter-ubicaciones-formulario" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                            <tr>
                                <th>Ubicación</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${ubicacionesProducto}" var="ubicacion">
                                <tr id="${ubicacion.getId_ubicacion()}">
                                    <td>${ubicacion.getNombre()}</td>
                                    <td>
                                        <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarUbicacion(${ubicacion.getId_ubicacion()})" >Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>

        <div class="col-md-6">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-truck"></i> Asociaciones con Productos Externos</h3>
                    <div class="btn-group widget-header-toolbar">
                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarProductoExterno">Agregar</a>
                    </div>
                </div>
                <div class="widget-content">
                    <table id="datatable-column-filter-productos-externos" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                            <tr>
                                <th>Producto Externo</th>
                                <th>Eliminar</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${productosExternos}" var="producto">
                                <tr id="${producto.getId_producto_ext()}">
                                    <td>${producto.getProducto()} (${producto.getCodigo_Externo()})</td>
                                    <td>
                                        <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarProductoExterno(${producto.getId_producto_ext()})" >Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <input hidden="true" id="ubicaciones"  name="ubicaciones" value="">
    <input hidden="true" id="productosExternos"  name="productosExternos" value="">


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
            </c:choose>
        </div>
    </div>
</form>

<t:modal idModal="modalAgregarUbicacion" titulo="Asociar Ubicación">

    <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarUbicacion">
            <input type="text" name="ubicacion"  hidden="true">
            <label for="idUbicacion" class="control-label">*Ubicación</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group" id='inputGroupSeleccionUbicacion'>
                        <select id="seleccionUbicacion" class="select2" style='background-color: #fff;' name="idUbicacion" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value='' ></option>
                            <c:forEach items="${ubicacionesRestantes}" var="ubicacion">
                                <option value=${ubicacion.getId_ubicacion()}>${ubicacion.getNombre()}</option>
                            </c:forEach> 
                        </select>
                    </div>
                </div>
            </div>
        </form>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button id="btn-agregar-ubicacion" type="button" class="btn btn-primary" onclick="agregarUbicacion()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </div>
        </div>


    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAgregarProductoExterno" titulo="Asociar Producto Externo">

    <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarProductoExterno">
            <input type="text" name="productoExterno"  hidden="true">
            <label for="idProductoExterno" class="control-label">*Producto Externo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group" id='inputGroupSeleccionProductoExterno'>
                        <select id="seleccionProductoExterno" class="select2" style='background-color: #fff;' name="idProductoExterno" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value='' ></option>
                            <c:forEach items="${productosExternosRestantes}" var="producto">
                                <option value=${producto.getId_producto_ext()}>${producto.getProducto()} (${producto.getCodigo_Externo()})</option>
                            </c:forEach> 
                        </select>
                    </div>
                </div>
            </div>
        </form>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button id="btn-agregar-ubicacion" type="button" class="btn btn-primary" onclick="agregarProductoExterno()"><i class="fa fa-check-circle"></i> Asociar Producto Externo</button>
            </div>
        </div>

    </jsp:attribute>

</t:modal>    
