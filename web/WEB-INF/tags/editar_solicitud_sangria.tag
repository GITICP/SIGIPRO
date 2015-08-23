<%-- 
    Document   : editar_solicitud_sangria
    Created on : 19.08.2015, 23:29:20
    Author     : Boga
--%>

<%@ tag description="plantilla para la creación del código de la edición de una solicitud cuando está asociada a una sangría" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="derecha" type="java.lang.Boolean" required="true" description="Define si los select van del lado izquierdo o derecho"%>

<c:if test="${derecha}">
    <div class="col-md-6"></div>
</c:if>
<div id="fila-select-sangria" class="row">
    <div class="col-md-6">
        <label for="sangria" class="control-label"> Sangría por asociar</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccion-sangria" name="sangria"
                            style='background-color: #fff;'>
                        <option value=''></option>
                        <c:forEach items="${sangrias}" var="sangria">
                            <c:if test="${sangria.getId_sangria() == id_sangria}">
                                <c:set var="sangria_seleccionada" value="${sangria}" /> 
                            </c:if>

                            <option value="${sangria.getId_sangria()}"
                                    data-fecha-1="${sangria.getFecha_dia1()}"
                                    data-fecha-2="${sangria.getFecha_dia2()}"
                                    data-fecha-3="${sangria.getFecha_dia3()}"
                                    ${(sangria.getId_sangria() == id_sangria) ? "selected" : ""}>
                                ${sangria.getId_sangria_especial()}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>
<c:if test="${derecha}">
    <div class="col-md-6"></div>
</c:if>
<div id="fila-select-dia" class="row">
    <div class="col-md-6">
        <label for="sangria" class="control-label"> Día por asignar</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccion-dia" name="dia"
                            style='background-color: #fff;'>
                        <c:if test="${sangria_seleccionada.getFecha_dia1() != null}">
                            <option value="1" ${(dia == 1 ? "selected" : "")}>Día 1</option>
                        </c:if>
                        <c:if test="${sangria_seleccionada.getFecha_dia2() != null}">
                            <option value="1" ${(dia == 2 ? "selected" : "")}>Día 2</option>
                        </c:if>
                        <c:if test="${sangria_seleccionada.getFecha_dia3() != null}">
                            <option value="1" ${(dia == 3 ? "selected" : "")}>Día 3</option>
                        </c:if>
                    </select>
                </div>
            </div>
        </div>
    </div>
</div>