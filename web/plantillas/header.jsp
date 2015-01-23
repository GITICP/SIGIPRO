<%-- 
    Document   : header
    Created on : Nov 26, 2014, 10:17:45 PM
    Author     : Boga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.seguridad.modelos.BarraFuncionalidad"%>
<%@page import="java.util.List"%>
<%-- 
    
    ¡¡QUITAR BOTÓN DE INICIAR SESIÓN!!

    ¡¡PONER SIGIPRO BLANCO Y EL LOGO SI SE PUEDE!!

--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    List<BarraFuncionalidad> modulos;
    List<Integer> permisos = (List<Integer>)session.getAttribute("listaPermisos");
    request.setAttribute("permisos", permisos);
    int idusuario = (int)session.getAttribute("idusuario");
    
    modulos = (List<BarraFuncionalidad>)session.getAttribute("barraFuncionalidad");

    if(modulos!=null)
    {
        request.setAttribute("modulos", modulos);
    }  
%>
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
                                                    <span class="text" onclick="cambiarContrasena('${sessionScope.usuario}')">Cambiar Contraseña</span>
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
        <a href="#menu-sigipro" title="Show navigation">Mostrar Menú</a>
        <a href="#" title="Hide navigation">Ocultar Menú</a>
        <ul>
            <li><a href='<%= request.getContextPath() %>'> Inicio </a></li>
            <c:forEach items="${modulos}" var="modulo">
                <li>
                    <a href="#">
                        ${modulo.getModulo()}
                    </a>
                    <ul>
                        <c:forEach items="${modulo.getFuncionalidades()}" var="funcionalidad">
                            <li>
                                <a href="<%= request.getContextPath() %>${funcionalidad[1]}">
                                    ${funcionalidad[0]}
                                </a>
                            </li>
                        </c:forEach> 
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>
                <%--
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
            </li> 
            <li><a href="#">Bioterio</a></li> 
            <li><a href="#">Control de Calidad</a></li> 
            --%>
            
            
            

<%--
<c:forEach items="${modulos}" var="modulo">
                <li>
                    <a href="#" class="js-sub-menu-toggle">
                        <i class="fa fa-dashboard fa-fw"></i>
                        <span class="text">${modulo.getModulo()}</span>
                        <i class="toggle-icon fa fa-angle-left"></i>
                    </a>
                    <ul class="sub-menu " style="display: none; overflow: hidden;">
                    
                        <c:forEach items="${modulo.getFuncionalidades()}" var="funcionalidad">
                            <li>
                                <a href="<%= request.getContextPath() %>${funcionalidad[1]}">
                                    <span class="text">${funcionalidad[0]}</span>
                                </a>
                            </li>
                        </c:forEach> 
                    </ul>
                </li>
            </c:forEach>
--%>