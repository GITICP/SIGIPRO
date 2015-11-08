<%-- 
    Document   : header
    Created on : Nov 26, 2014, 10:17:45 PM
    Author     : Boga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="com.icp.sigipro.notificaciones.modelos.Notificacion"%>
<%@page import="com.icp.sigipro.notificaciones.dao.NotificacionesDAO"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<form id="cerrar-sesion" action="<%= request.getContextPath()%>/Cuenta/CerrarSesion" method="post"></form>

<div class="navbar navbar-default navbar-static-top" id="navbar_top" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="/SIGIPRO">SIGIPRO</a>
    </div>
    <div class="navbar-collapse collapse" id="navbar_content">

        <!-- Left nav -->
        <ul class="nav navbar-nav">
            <t:renderizar_barra barra="${sessionScope.barraFuncionalidad}" />
        </ul>

        <!-- Right nav -->
        <ul class="nav navbar-nav navbar-right" id="notificacion-item">
            <li>
                <a href="#">
                    <span class="name">Bienvenido, ${sessionScope.usuario}</span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="#">
                            <i class="fa fa-user"></i>
                            <span class="text" data-toggle="modal" data-target="#modalCambiarContrasena">Cambiar Contraseña</span>
                        </a>
                    </li>
                </ul>
            </li>
            <!-- Notificaciones -->
            
<%  
    NotificacionesDAO nD = new NotificacionesDAO();
    String nombre_usr = (String) session.getAttribute("usuario");
    List<Notificacion> notificaciones = nD.obtenerNotificaciones(nombre_usr);
    int numeroNotificacionesNoLeidas = nD.contarNotificacionesNoLeidas(nombre_usr);
    request.setAttribute("notificaciones", notificaciones);
    request.setAttribute("numeroNotificacionesNoLeidas", numeroNotificacionesNoLeidas);
    request.setAttribute("nombre_usr", nombre_usr);
    request.setAttribute("nD", nD);
%>
            
            <li class="notification-item">  
                <a href="#" id="campana_notificaciones" class="dropdown-toggle" data-toggle="dropdown">
                    <i class="fa fa-bell" ></i>
                    <span id="numero_notificaciones" class="count">
                        <c:if test="${numeroNotificacionesNoLeidas > 0}">
                            <c:out value="${numeroNotificacionesNoLeidas}"/>
                        </c:if>
                    </span>
                    <span class="circle"></span>
                </a>
                <ul class="dropdown-menu" id="notificaciones-dropdown" role="menu">
                    <!-- Header de las notificaciones -->
                    <li class="notification-header">
                        <center><em id="notificaciones-header-dropdown">Notificaciones recientes</em></center>
                    </li>
                    <!-- Iteración sobre las notificaciones -->
                    <c:set var="cantidadNotificacionesCargadas" value="${0}" />
                    <c:forEach items="${notificaciones}" var="notificacion">
                        <c:if test="${cantidadNotificacionesCargadas < 10}">
                            <li onclick="marcarNotificacionesleidas()">
                                <a href="/SIGIPRO${notificacion.getRedirect()}">
                                    <i class="${notificacion.getIcono()}" width="30" height="30"></i>
                                        <c:choose>
                                            <c:when test="${!(notificacion.isLeida())}">
                                                <b><span class="text">${notificacion.getDescripcion()}</span></b>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="text">${notificacion.getDescripcion()}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    <span class="timestamp"> - ${notificacion.getDateTime()}</span>
                                </a>
                            </li>
                            <c:set var="cantidadNotificacionesCargadas" value="${cantidadNotificacionesCargadas + 1}"/>
                        </c:if>
                    </c:forEach>
                    
                    <!-- Footer de las notificaciones -->
                    <li class="notification-footer">
                        <center><u><a href="/SIGIPRO/Inicio/Notificaciones">Ver todas las notificaciones</a></u></center>
                    </li>
                </ul>
            </li>
            
            <li>
                <a class="confirmable-form cerrar-sesion" data-form-id="cerrar-sesion" data-texto-confirmacion="cerrar sesión">
                    <i class="fa fa-power-off"></i>
                    <span class="text">Cerrar Sesión</span>
                </a>
            </li>
        </ul>
    </div><!--/.nav-collapse -->
</div>
                
                