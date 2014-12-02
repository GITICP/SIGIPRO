<%-- 
    Document   : login
    Created on : Nov 28, 2014, 10:49:05 AM
    Author     : Boga
--%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%    
    if(session.getAttribute("usuario") != null)
    {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }
%>

<t:plantilla_general title="Inicio Sesión" direccion_contexto="/SIGIPRO">
    
    <jsp:attribute name="contenido">
        
        <!-- Main -->
        <form class="form form-vertical inicioSesion" action="IniciarSesion" method="post">
            
            ${errorMessage}
            <div class="control-group">
                <label>Usuario</label>
                <div class="controls">
                    <input type="text" name="usuario" class="form-control" placeholder="Ingrese nombre de usuario">
                </div>
            </div>
            <div class="control-group">
                <label>Contraseña</label>
                <div class="controls">
                    <input type="password" name="contrasenna" class="form-control" placeholder="Ingrese su contraseña">
                </div>
            </div>
            <br>
            <div class="control-group">
                <div class="controls">
                    <button type="submit" class="btn btn-primary">Iniciar Sesión</button>
                </div>
            </div>
            
        </form>
        <!-- /Main -->
        
    </jsp:attribute>
        
</t:plantilla_general>