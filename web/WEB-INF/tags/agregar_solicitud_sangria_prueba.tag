<%-- 
    Document   : editar_solicitud_sangria
    Created on : 19.08.2015, 23:29:20
    Author     : Boga
--%>

<%@ tag description="plantilla para la creación del código de agregar una solicitud asociada a una sangría de prueba" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="col-md-6"></div>
<div id="fila-select-sangria-prueba" class="row" hidden="true">
    <div class="col-md-6">
        <label for="sangria_prueba" class="control-label"> Sangría de Prueba por asociar</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccion-sangria-prueba" name="sangria_prueba"
                            style='background-color: #fff;'>
                        <option value=''></option>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>