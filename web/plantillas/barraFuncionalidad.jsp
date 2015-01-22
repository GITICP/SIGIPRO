<%-- 
    Document   : barraFuncionalidad
    Created on : Nov 27, 2014, 2:05:03 PM
    Author     : Boga
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.List"%>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.seguridad.modelos.BarraFuncionalidad"%>
<%@page import="com.icp.sigipro.seguridad.dao.BarraFuncionalidadDAO"%>

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
<%--
<div class="container"> 
    <nav class="nav-sigipro" id="menu-sigipro" role="navigation">
        <a href="#menu-sigipro" title="Show navigation">Show navigation</a>
        <a href="#" title="Hide navigation">Hide navigation</a>
        <ul>
            <li><a href="/">Home</a></li>
            <li>
                <a href="/" aria-haspopup="true">Blog</a>
                <ul>
                    <li><a href="/">Design</a></li>
                    <li><a href="/">HTML</a></li>
                    <li><a href="/">CSS</a></li>
                    <li><a href="/">JavaScript</a></li>
                </ul>
            </li>
            <li>
                <a href="/" aria-haspopup="true">Work</a>
                <ul>
                    <li><a href="/">Web Design</a></li>
                    <li><a href="/">Typography</a></li>
                    <li><a href="/">Front-End</a></li>
                </ul>
            </li>
        </ul>
    </nav>
</div>

<!-- left sidebar -->
<div class="col-md-2 left-sidebar">

    <!-- main-nav -->
    <nav class="main-nav">
        <ul class="main-menu">
            <li class="active">
                <a href="/SIGIPRO/index.jsp">
                    <i class="fa fa-dashboard fa-fw"></i>
                    <span class="text">Inicio</span>
                </a>
            </li>
            <c:forEach items="${permisos}" var="permiso">
              <p>${permiso}</p>
            </c:forEach>
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
        </ul>
    </nav>
    <div class="sidebar-minified js-toggle-minified">
        <i class="fa fa-angle-left"></i>
    </div>
<!-- end sidebar content -->
</div>
<!-- end left sidebar -->

<%--
<div class="col-md-2 left-sidebar">
    <!-- Left column -->
    <nav class="main-nav">
        <ul class="main-menu">
            <c:forEach items="${modulos}" var="modulo">
                <li>
                    <a href="#" class="js-sub-menu-toggle">
                        <span class="text">${modulo.getModulo()}</span>
                    </a>
                    <ul class="sub-menu" style="display:block;">
                        <c:forEach items="${modulo.getFuncionalidades()}" var="funcionalidad">
                            <li>
                                <a href="#">
                                    <span class="text">${funcionalidad}</span>
                                </a>
                            </li>
                        </c:forEach>       
                    </ul>
                </li>
            </c:forEach>
        </ul>
    </nav>
</div>
        

--%>
            
            
            
            
            
            
            
            
            
            
            
            
            
            <%--
            
            
            
                for(BarraFuncionalidad b : resultado)
                {
                    %>

                    <li class="nav-header"> 
                        <a href="#" data-toggle="collapse" data-target="#<%= b.getModulo() %>">
                            <h5> <%= b.getModulo() %> <i class="glyphicon glyphicon-chevron-right"></i></h5>
                        </a>
                        <ul class="list-unstyled collapse" id="<%= b.getModulo() %>">

                    <%
                    for (String funcionalidad : b.getFuncionalidades())
                    {
                    %>

                        <li> <a href="#"><i class="glyphicon glyphicon-home"></i> <%= funcionalidad %></a></li>

                    <%
                    }
                    %>

                        </ul> <%-- Final de sublista de funcionalidades 
                    </li> <%-- Final de lista de módulo --%>



