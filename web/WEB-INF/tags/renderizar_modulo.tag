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
        <c:forEach items="${modulo.getFuncionalidades()}" var="funcionalidad">
            <li><a href="<%=request.getContextPath()%>${funcionalidad.getRedirect()}">${funcionalidad.getTag()}</a></li>
        </c:forEach>
        <c:if test="${modulo.getSub_modulos().size() > 0}">
            <c:forEach items="${modulo.getSub_modulos()}" var="sub_modulo">
                <t:renderizar_modulo modulo="${sub_modulo}" />
            </c:forEach>
        </c:if>
    </ul>
</li>