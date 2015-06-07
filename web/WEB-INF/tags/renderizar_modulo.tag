<%-- 
    Document   : renderizar_modulo
    Created on : Apr 20, 2015, 3:22:31 PM
    Author     : Boga
--%>

<%@ tag description="Función para generar el código HTML de un módulo o sub-módulo" pageEncoding="UTF-8" body-content="empty" %> 
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="modulo" rtexprvalue="true"  required="true" type="com.icp.sigipro.seguridad.modelos.Modulo"  description="Description of varName" %> 

<li>
    <a>${modulo.getNombre()}</a>
    <ul class="dropdown-menu">
        <c:forEach items="${modulo.getItems()}" var="item">
            <c:choose>
                <c:when test="${item.getClass().getName() == 'com.icp.sigipro.seguridad.modelos.Modulo'}">
                    <t:renderizar_modulo modulo="${item}" />
                </c:when>
                <c:otherwise>
                    <t:renderizar_funcionalidad funcionalidad="${item}" />
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>
</li>