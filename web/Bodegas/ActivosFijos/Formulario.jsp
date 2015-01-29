<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<form class="form-horizontal" autocomplete="off" method="post" action="ActivosFijos">
    <div class="col-md-6">
        <input hidden="true" name="id_activo_fijo" value="${activofijo.getId_activo_fijo()}">
        <label for="placa" class="control-label">* Placa del Activo Fijo</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="number" maxlength="45" placeholder="Placa del activo fijo" class="form-control" name="placa" value="${activofijo.getPlaca()}"
                           required
                           oninvalid="setCustomValidity('Este campo es requerido ')"
                           oninput="setCustomValidity('')" > 
                </div>
            </div>
        </div>
        <label for="equipo" class="control-label">* Equipo</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="number" maxlength="45" placeholder="Equipo" class="form-control" name="equipo" value="${activofijo.getEquipo()}"
                           required
                           oninvalid="setCustomValidity('Este campo es requerido ')"
                           oninput="setCustomValidity('')" > 
                </div>
            </div>
        </div>
        <label for="marca" class="control-label"> Marca</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="text" maxlength="45" placeholder="Marca" class="form-control" name="marca" value="${activofijo.getMarca()}"
                </div>
            </div>
        </div>
    </div>
    <label for="fecha_movimiento" class="control-label">*Fecha de Movimiento</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_movimiento" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
            </div>
        </div>
    </div>
</div>
<div class="col-md-6">
    <label for="seccion" class="control-label">Sección</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <select id="seleccionSeccion" class="form-control" name="seccion"
                        style='background-color: #fff;' >
                    <c:forEach items="${secciones}" var="seccion">
                        <option value="${seccion.getID()}">${seccion.getNombreSeccion()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <label for="ubicacion" class="control-label">Ubicación</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <select id="seleccionUbicacion" class="form-control" name="ubicacion" 
                        style='background-color: #fff;' >
                    <c:forEach items="${ubicaciones}" var="ubicacion">
                        <option value=${ubicacion.getId_ubicacion()}>${ubicacion.getNombre()}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
    </div>
    <label for="fecha_registro" class="control-label">*Fecha de Registro</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_registro" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
            </div>
        </div>
    </div>
    <label for="estado" class="control-label"> Estado</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <input type="text" maxlength="45" placeholder="Estado" class="form-control" name="estado" value="${activofijo.getEstado()}"
                       required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>
    <select name="department">
        <option value="${ubicaciones.get(1).getId_ubicacion()}">${ubicaciones.get(1).getNombre()}</option>
        <c:forEach var="item" items="${ubicaciones}">
            <option value="${item}">${item}</option>
        </c:forEach>
    </select>
    <input for="xxx" class="control-label" value="${ubicaciones.get(1).getNombre()}"/>
</div>
</div>
<!-- Esta parte es la de los permisos de un rol -->
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