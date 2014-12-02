<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%
    String usuarioConectado;
    
    if(session.getAttribute("usuario") != null)
    {
        usuarioConectado = (String) session.getAttribute("usuario");
        request.setAttribute("usuario",usuarioConectado);
    }
    else
    {
        response.sendRedirect(request.getContextPath() + "/Cuenta/IniciarSesion.jsp");
    }
%>

<t:plantilla_general title="Inicio" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">
        
        <!-- Main -->
        <jsp:include page="plantillas/contenido.jsp" />
        <!-- /Main -->
        
    </jsp:attribute>

</t:plantilla_general>