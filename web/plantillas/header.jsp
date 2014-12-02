<%-- 
    Document   : header
    Created on : Nov 26, 2014, 10:17:45 PM
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div id="top-nav" class="navbar navbar-inverse navbar-static-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">SIGIPRO</a>
        </div>
        
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <% 
            
                    if (request.getAttribute("usuario")!=null)
                    {
                %>
                <li class="dropdown">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
                        <i class="glyphicon glyphicon-user"></i> Bienvenido, ${usuario} 
                        <span class="caret"></span>
                    </a>
                    <ul id="g-account-menu" class="dropdown-menu" role="menu">
                        <li><a href="#">Perfil</a></li>
                    </ul>
                </li>

                <li>
                    <form action="<%= request.getContextPath() + "/Cuenta/CerrarSesion" %>" method="post">
                        <button class="botonCerrarSesion" type="submit" >
                            <i class="glyphicon glyphicon-lock"></i> Cerrar Sesión 
                        </button>
                    </form>
                </li>
                
                <%
                    }
                    else
                    {
                %>
                
                <li>
                    <a href="#">
                        <i class="glyphicon glyphicon-user"></i> Iniciar sesión
                    </a>
                </li>
                
                <%
                    }
                %>
            </ul>
        </div>
                        
                        
    </div><!-- /container -->
</div>