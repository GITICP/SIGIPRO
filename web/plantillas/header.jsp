<%-- 
    Document   : header
    Created on : Nov 26, 2014, 10:17:45 PM
    Author     : Boga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="com.icp.sigipro.seguridad.modelos.BarraFuncionalidad"%>
<%@page import="java.util.List"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<form id="cerrar-sesion" action="<%= request.getContextPath()%>/Cuenta/CerrarSesion" method="post"></form>

<div class="navbar navbar-default navbar-static-top" role="navigation">
    <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
        </button>
        <a class="navbar-brand" href="#">SIGIPRO</a>
    </div>
    <div class="navbar-collapse collapse">

        <!-- Left nav -->
        <ul class="nav navbar-nav">
            <c:forEach items="${sessionScope.barraFuncionalidad.getModulos()}" var="modulo">
                <t:renderizar_modulo modulo="${modulo}" />
            </c:forEach>
        </ul>

        <!-- Right nav -->
        <ul class="nav navbar-nav navbar-right">
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
            <li>
                <a class="confirmable-form cerrar-sesion" data-form-id="cerrar-sesion" data-texto-confirmacion="cerrar sesión">
                    <i class="fa fa-power-off"></i>
                    <span class="text">Cerrar Sesión</span>
                </a>
            </li>
        </ul>
    </div><!--/.nav-collapse -->
</div>