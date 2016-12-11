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
                        <textarea id="descripcion" name="descripcion" class="form-control">${producto.getDescripcion()}</textarea>
                    </div>
                </div>
            </div>
    </div>                    
    <div class="col-md-6">
        <label for="tipo" class="control-label"> *Tipo</label>
            <!-- Tipo -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="tipo" class="select2" style='background-color: #fff;' name="tipo" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                            <c:choose>
                                <c:when test="${producto.getTipo()} == Sueros" >
                                  <option value="Servicios"> Servicios</option>
                                  <option value="Sueros" selected> Sueros</option>
                                  <option value="Otros Productos"> Otros Productos</option>
                                </c:when>
                                <c:when test="${producto.getTipo()} == Servicios" >
                                  <option value="Servicios" selected> Servicios</option>
                                  <option value="Sueros"> Sueros</option>
                                  <option value="Otros Productos"> Otros Productos</option>
                                </c:when>
                                <c:when test="${producto.getTipo()} == Otros Servicios" >
                                  <option value="Servicios"> Servicios</option>
                                  <option value="Sueros"> Sueros</option>
                                  <option value="Otros Productos" selected> Otros Productos</option>
                                </c:when>
                                <c:otherwise>
                                  <option value=""></option>
                                  <option value="Sueros"> Sueros</option>
                                  <option value="Servicios"> Servicios</option>
                                  <option value="Otros Productos"> Otros Productos</option>
                                </c:otherwise>
                            </c:choose>    
                        </select>
                    </div>
                </div>
            </div>
            <label id="lotelabel" for="lote" class="control-label"> *Número de Lote (Inventario de Producción)</label>
            <div id="loteformgroup" class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="lote" class="select2" name="lote" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                            <option value=""></option>
                          <c:forEach items="${inventarios}" var="inventario">
                            <c:choose>
                              <c:when test="${inventario.getLote() == producto.getLote()}" >
                                <option value="${inventario.getLote()}" selected> Lote: ${inventario.getLote()} Cantidad: ${inventario.getCantidad_disponible()} </option>
                              </c:when>
                              <c:otherwise>
                                <option value="${inventario.getLote()}"> Lote: ${inventario.getLote()} Cantidad: ${inventario.getCantidad_disponible()} </option>
                              </c:otherwise>
                            </c:choose>
                          </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
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
<script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.js"></script>
<script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
<script>
    $(function(){ /* DOM ready */ //
        $("#tipo").change(function () {
            var tipo = document.getElementById("tipo").value;
            if (tipo === "Sueros"){
                document.getElementById("loteformgroup").style.display = 'block';
                document.getElementById("lotelabel").style.display = 'block';
                document.getElementById("lote").required = true;
            }
            else{
                document.getElementById("loteformgroup").style.display = 'none';
                document.getElementById("lotelabel").style.display = 'none';
                document.getElementById("lote").required = false;
            }
        }).change();
    });
</script>
