<%-- 
    Document   : editar_solicitud_sangria
    Created on : 19.08.2015, 23:29:20
    Author     : Boga
--%>

<%@ tag description="plantilla para la creación del código de la edición de una solicitud cuando está asociada a una sangría" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="derecha" type="java.lang.Boolean" required="true" description="Define si los select van del lado izquierdo o derecho"%>

<div id="fila-select-sangria" class="row">

    <div class="col-md-6">
        <label for="objeto-relacionado" class="control-label"> Objecto asociado</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <p><strong>Sangría de Prueba</strong> ${sangria_prueba.getId_sangria_prueba_especial()}</p>
                    <input type="hidden" name="objeto-relacionado" value="sangria_prueba">
                    <input type="hidden" name="sangria_prueba" value="${sangria_prueba.getId_sangria_prueba()}">
                </div>
            </div>
        </div>
    </div>
</div>
                
