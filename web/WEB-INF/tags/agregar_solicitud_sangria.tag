<%-- 
    Document   : editar_solicitud_sangria
    Created on : 19.08.2015, 23:29:20
    Author     : Boga
--%>

<%@ tag description="plantilla para la creación del código de agregar una solicitud para una sangría" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="fila-select-sangria" class="row" hidden="true">
    <div class="col-md-6">
        <label for="sangria" class="control-label"> Sangría por asociar</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccion-sangria" name="sangria"
                            style='background-color: #fff;'>
                        <option value=''></option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="fila-select-dia" class="row" hidden="true">
    <div class="col-md-6">
        <label for="sangria" class="control-label"> Día por asignar</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccion-dia" name="dia"
                            style='background-color: #fff;'>
                        <option value=''></option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>