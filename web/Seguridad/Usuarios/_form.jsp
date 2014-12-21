<%-- 
    Document   : _form.jsp
    Created on : Dec 14, 2014, 11:42:14 AM
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<form class="form-horizontal" autocomplete="off" role="form" action="InsertarUsuario" method="post">
    <p class="title">Agregar Usuario</p>
    <label for="nombreUsuario" class="control-label">*Nombre de Usuario</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <input type="text" maxlength="45" placeholder="Nombre de Usuario" class="form-control" name="nombreUsuario" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')" > 
            </div>
        </div>
    </div>
    <label for="nombreCompleto" class="control-label">*Nombre Completo</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <input type="text" maxlength="200" placeholder="Nombre Completo" class="form-control" name="nombreCompleto" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')">
            </div>
        </div>
    </div>
    <label for="correoElectronico" class="control-label">*Correo Electrónico</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-at"></i></span>
                <input type="email" maxlength="45" placeholder="usuario@icp.ucr.ac.cr" class="form-control" name="correoElectronico" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')">
            </div>
        </div>
    </div>
    <label for="cedula" class="control-label">*Cédula</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-at"></i></span>
                <input type="text" placeholder="1-0001-4628" pattern="[0-9]{1}-[0-9]{4}-[0-9]{4}" class="form-control" name="cedula" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')">
            </div>
        </div>
    </div>
    <label for="departamento" class="control-label">*Departamento</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-at"></i></span>
                <input type="text" maxlength="200" placeholder="Producción" class="form-control" name="departamento" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')">
            </div>
        </div>
    </div>
    <label for="puesto" class="control-label">*Puesto</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-at"></i></span>
                <input type="text" maxlength="200" placeholder="Jefe" class="form-control" name="puesto" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       oninput="setCustomValidity('')">
            </div>
        </div>
    </div>
    <label for="fechaActivacion" class="control-label">*Fecha de Activación</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fechaActivacion" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
            </div>
        </div>
    </div>
    <label for="fechaDesactivacion" class="control-label">*Fecha de Desactivación</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">
                <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker2" class="form-control sigiproDatePicker" name="fechaDesactivacion" data-date-format="dd/mm/yyyy" required
                       oninvalid="setCustomValidity('Este campo es requerido ')"
                       onchange="setCustomValidity('')">
            </div>
        </div>
    </div>

    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Usuario</button>
        </div>
    </div>
</form>