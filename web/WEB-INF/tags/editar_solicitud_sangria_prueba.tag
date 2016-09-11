<%-- 
    Document   : editar_solicitud_sangria_prueba
    Created on : 03-Apr-2016, 12:06:46
    Author     : Boga
--%>

<%@tag description="plantilla para la creación del código de editar una solicitud asociada a una sangría de prueba" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="fila-select-sangria-prueba" class="row">
    <div class="col-md-6">
        <label for="sangria_prueba" class="control-label"> Sangría de Prueba por asociar</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccion-sangria-prueba" name="sangria_prueba"
                            style='background-color: #fff;'>
                        <option value=''></option>
                        <c:forEach items="${sangria_prueba}" var="sangria">
                            <option value="${sangria.getId_sangria_prueba()}" data-caballos=${sangria.getCaballos_json()}
                                    ${(sangria.getId_sangria_prueba() == id_sangria) ? "selected" : ""}>
                                ${sangria.getId_sangria_especial()} 
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>