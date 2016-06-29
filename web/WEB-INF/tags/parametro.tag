<%-- 
    Document   : parametro
    Created on : 28.06.2016, 17:29:20
    Author     : Boga
--%>

<%@ tag description="Plantilla para los parámetros de los reportes" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="parametro" type="com.icp.sigipro.reportes.modelos.Parametro" required="true" description="Parametro que se va a analizar para imprimir su código HTML" %>

<div class="col-md-6">
    <label for="valor_param_${paramtro.getNumero()}" class="control-label">${parametro.getNombre()}</label>
    <div class="form-group">
        <div class="col-sm-12">
            <div class="input-group">

                <c:choose>
                    <c:when test="${parametro.getTipo() == 'fecha'}">
                        <input type="text" value="${helper_fechas.getFecha_hoyAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" 
                               class="form-control sigiproDatePicker" 
                               name="valor_param_${parametro.getNumero()}" 
                               data-date-format="dd/mm/yyyy" required
                               oninvalid="setCustomValidity('Este campo es requerido.')"
                               onchange="setCustomValidity('')">
                    </c:when>
                    <c:when test="${parametro.getTipo() == 'numero'}">

                    </c:when>
                    <c:when test="${parametro.getTipo() == 'objeto'}">

                    </c:when>
                    <c:when test="${parametro.getTipo() == 'objeto_multiple'}">
                        <select class="select2" multiple="multiple" required
                                name="valor_param_${parametro.getNumero()}" 
                                style='background-color: #fff;' 
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <c:forEach items="${parametro.getListaItems()}" var="item">
                                <option value="${item.getVal()}">${item.getTexto()}</option>
                            </c:forEach>
                        </select>
                    </c:when>
                </c:choose>

            </div>
        </div>
    </div>
</div>