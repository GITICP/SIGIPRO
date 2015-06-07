<%-- 
    Document   : renderizar_modulo
    Created on : May 12, 2015, 10:00:00 PM
    Author     : Boga
--%>

<%@ tag description="Función para generar el código HTML de la barra de funcionalidad" pageEncoding="UTF-8" body-content="empty" %> 
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="barra" rtexprvalue="true"  required="true" type="com.icp.sigipro.seguridad.modelos.BarraFuncionalidad"  description="Barra de Funcionalidad que se desea renderizar" %>

<c:forEach items="${barra.getModulos()}" var="modulo">
    <t:renderizar_modulo modulo="${modulo}" />
</c:forEach>