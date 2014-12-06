<%-- 
    Document   : header
    Created on : Nov 26, 2014, 10:17:45 PM
    Author     : Boga
--%>


<%-- 
    
    ¡¡QUITAR BOTÓN DE INICIAR SESIÓN!!

    ¡¡PONER SIGIPRO BLANCO Y EL LOGO SI SE PUEDE!!

--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!-- TOP BAR -->
    <div class="top-bar">
        <div class="container">
            <div class="row">
                <!-- logo -->
                <div class="col-md-2 logo">
                    <p style="color:#fff">SIGIPRO</p>
                </div>
                <!-- end logo -->
                <div class="col-md-10">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="top-bar-right">
                                <!-- responsive menu bar icon -->
                                <a href="#" class="hidden-md hidden-lg main-nav-toggle"><i class="fa fa-bars"></i></a>
                                <!-- end responsive menu bar icon -->


                                <div class="notifications">
                                    <ul>
                                        <!-- notification: general -->
                                        <li class="notification-item general">
                                            <div class="btn-group">
                                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                                    <i class="fa fa-bell"></i>
                                                    <span class="count">8</span>
                                                    <span class="circle"></span>
                                                </a>
                                                <ul class="dropdown-menu" role="menu">
                                                    <li class="notification-header">
                                                        <em>You have 8 notifications</em>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-comment green-font"></i>
                                                            <span class="text">New comment on the blog post</span>
                                                            <span class="timestamp">1 minute ago</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-user green-font"></i>
                                                            <span class="text">New registered user</span>
                                                            <span class="timestamp">12 minutes ago</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-comment green-font"></i>
                                                            <span class="text">New comment on the blog post</span>
                                                            <span class="timestamp">18 minutes ago</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-shopping-cart red-font"></i>
                                                            <span class="text">4 new sales order</span>
                                                            <span class="timestamp">4 hours ago</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-edit yellow-font"></i>
                                                            <span class="text">3 product reviews awaiting moderation</span>
                                                            <span class="timestamp">1 day ago</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-comment green-font"></i>
                                                            <span class="text">New comment on the blog post</span>
                                                            <span class="timestamp">3 days ago</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-comment green-font"></i>
                                                            <span class="text">New comment on the blog post</span>
                                                            <span class="timestamp">Oct 15</span>
                                                        </a>
                                                    </li>
                                                    <li>
                                                        <a href="#">
                                                            <i class="fa fa-warning red-font"></i>
                                                            <span class="text red-font">Low disk space!</span>
                                                            <span class="timestamp">Oct 11</span>
                                                        </a>
                                                    </li>
                                                    <li class="notification-footer">
                                                        <a href="#">View All Notifications</a>
                                                    </li>
                                                </ul>
                                            </div>
                                        </li>
                                        <!-- end notification: general -->
                                    </ul>
                                </div>

                                                            <!-- logged user and the menu -->
                                <div class="logged-user">
                                    <div class="btn-group">
                                        <a href="#" class="btn btn-link dropdown-toggle" data-toggle="dropdown">
                                            <span class="name">Bienvenido, Daniel</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" role="menu">
                                            <li>
                                                <a href="#">
                                                    <i class="fa fa-user"></i>
                                                    <span class="text">Cambiar Contraseña</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#">
                                                    <i class="fa fa-cog"></i>
                                                    <span class="text">Actualizar Correo</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="#">
                                                    <i class="fa fa-power-off"></i>
                                                    <span class="text">Cerrar Sesión</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                                <!-- end logged user and the menu -->
                            </div>
                            <!-- /top-bar-right -->
                        </div>
                    </div>
                                    <!-- /row -->
                </div>
            </div>
            <!-- /row -->
        </div>
        <!-- /container -->
    </div>
    <!-- /top -->















<%-- 
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
                        <li><a href="#">Modificar Contraseña</a></li>
                        <li><a href="#">Actualizar Correo Electrónico</a></li>
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

--%>