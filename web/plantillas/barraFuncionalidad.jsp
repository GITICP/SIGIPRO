<%-- 
    Document   : barraFuncionalidad
    Created on : Nov 27, 2014, 2:05:03 PM
    Author     : Boga
--%>


<%@page import="java.util.List"%>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.clases.BarraFuncionalidad"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<div class="col-sm-2">
    <!-- Left column -->
    <strong><i class="glyphicon glyphicon-wrench"></i> Módulos</strong>
    
    <hr> <!-- Línea Divisora -->

    <ul class="list-unstyled">
        
        <% 
            
            SingletonBD baseDatos = SingletonBD.getSingletonBD();

            List<BarraFuncionalidad> resultado = baseDatos.obtenerModulos((String)request.getAttribute("usuario"));
            
            if(resultado!=null)
            {
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

                        </ul> <%-- Final de sublista de funcionalidades --%>
                    </li> <%-- Final de lista de módulo --%>

                    <%
                }
            }
        %>

    </ul>
</div>
