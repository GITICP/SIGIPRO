<%-- 
    Document   : renderizar_funcionalidad
    Created on : May 12, 2015, 10:00:00 PM
    Author     : Boga
--%>

<%@ tag description="Función para generar el código HTML de una funcionalidad" pageEncoding="UTF-8" body-content="empty" %> 
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%@ attribute name="funcionalidad" rtexprvalue="true"  required="true" type="com.icp.sigipro.seguridad.modelos.Funcionalidad"  description="Funcionalidad que se desea renderizar" %>

<li><a href="<%=request.getContextPath()%>${funcionalidad.getRedirect()}">${funcionalidad.getNombre()}</a></li>