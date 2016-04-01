<%-- 
    Document   : agregar_informe_sanria
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
                    <c:forEach items="${sangrias}" var="sangria">
                        <c:if test="${sangria.getId_sangria() == id_sangria}">
                            <c:choose>
                                <c:when test="${sangria.getFecha_dia1() != null}">
                                    <c:set var="dia" value="1"></c:set>
                                </c:when>
                                <c:when test="${sangria.getFecha_dia2() != null}">
                                    <c:set var="dia" value="2"></c:set>
                                </c:when>
                                <c:when test="${sangria.getFecha_dia3() != null}">
                                    <c:set var="dia" value="3"></c:set>
                                </c:when>    
                            </c:choose>
                            <p><strong>Sangría</strong> ${sangria.getId_sangria_especial()}, día ${dia}</p>
                            <input type="hidden" name="objeto-relacionado" value="sangria">
                            <input type="hidden" name="dia" value="${dia}">
                            <input type="hidden" name="sangria" value="${sangria.getId_sangria()}">
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>