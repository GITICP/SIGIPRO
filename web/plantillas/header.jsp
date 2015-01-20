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
                                <%--
                                <a href="#" class="hidden-md hidden-lg main-nav-toggle">
                                    <i class="fa fa-bars"></i>
                                </a>
                                --%>
                                <!-- end responsive menu bar icon -->

                                <%-- 
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
                                --%>

                                                            <!-- logged user and the menu -->
                                <div class="logged-user">
                                    <div class="btn-group">
                                        <a href="#" class="btn btn-link dropdown-toggle" data-toggle="dropdown">
                                            <span class="name">Bienvenido, ${sessionScope.usuario}</span>
                                            <span class="caret"></span>
                                        </a>
                                        <ul class="dropdown-menu" role="menu" style="z-index:1055">
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
                                        </ul>
                                        
                                        
                                    </div>
                                </div>
                                <div class="logged-user">
                                    <form action="<%= request.getContextPath() %>/Cuenta/CerrarSesion" method="post">
                                        <button class="botonCerrarSesion" type="submit">
                                            <i class="fa fa-power-off"></i>
                                            <span class="text">Cerrar Sesión</span>
                                        </button>
                                    </form>
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




<div id="top-nav" class="navbar navbar-inverse navbar-static-top" style="background-color: #3C730D; border-color: #fff;"> 
    <nav class="nav-sigipro" id="menu-sigipro" role="navigation">
        <a href="#menu-sigipro" title="Show navigation">Show navigation</a>
        <a href="#" title="Hide navigation">Hide navigation</a>
        <ul>
            <li><a href="#">Inicio</a></li> 
            <li>
                <a href="#" aria-haspopup="true">Seguridad</a>
                <ul>
                    <li><a href="#">Usuarios</a></li>
                    <li><a href="#">Roles</a></li>
                    <li><a href="#">Secciones</a></li>
                    <li><a href="#">Sub Bodega</a></li>
                </ul>
            </li>
            <li>
                <a href="#" aria-haspopup="true">Bodegas</a>
                <ul>
                    <li><a href="#">Activos Fijos</a></li>
                    <li><a href="#">Solicitudes</a></li>
                    <li><a href="#">Inventario</a></li>
                </ul>
            </li>
                <a href="#" aria-haspopup="true">Serpentario</a>
            </li> <%--
            <li><a href="#">Bioterio</a></li> 
            <li><a href="#">Control de Calidad</a></li> 
            --%>
            
            
            
        </ul>
    </nav>
</div>

