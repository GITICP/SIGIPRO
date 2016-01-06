<%-- 
    Document   : index
    Created on : Mar 26, 2015, 4:02:57 PM
    Author     : Amed
--%>


<%@page import="com.icp.sigipro.notificaciones.modelos.Notificacion"%>
<%@page import="java.util.List"%>
<%@page import="com.icp.sigipro.notificaciones.dao.NotificacionesDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%  
    NotificacionesDAO nD = new NotificacionesDAO();
    String nombre_usr = (String) session.getAttribute("usuario");
    List<Notificacion> notificaciones = nD.obtenerNotificaciones(nombre_usr);
    request.setAttribute("notificaciones", notificaciones);
%>

<t:plantilla_general title="Inicio" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">


    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-xs-1"></div>
        <div class="col-xs-1"></div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-header">
          <h2>Notificaciones</h2>
          <em>${sessionScope.usuario}</em>
        </div>
        
        <!-- /main-content -->
        <div class="main-content">
            
            <!-- /menu-tabs -->
            <ul class="nav nav-tabs nav-tabs-custom-colored tabs-iconized">
                <li class="active">
                    <a href="#notificaciones-tab" data-toggle="tab"><i class="fa fa-user"></i> Notificaciones</a>
                </li>
            </ul>
            
            <div class="widget widget-table">
                <div class="widget-content">
                    <table class="table table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                        <thead> 
                            <tr>
                              <th>Tipo</th>
                              <th>Fecha</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${notificaciones}" var="notificacion">
                                <tr id ="${notificacion.getId()}" onclick="marcarNotificacionesleidas(${notificacion.getId()})">
                                    <td>
                                    <i class="${notificacion.getIcono()} activity-icon pull-left"></i>
                                    <p>
                                        <a href="/SIGIPRO${notificacion.getRedirect()}">
                                            <c:choose>
                                                <c:when test="${!(notificacion.isLeida())}">
                                                    <b>${notificacion.getDescripcion()}</b>
                                                </c:when>
                                                <c:otherwise>
                                                    ${notificacion.getDescripcion()}
                                                </c:otherwise>
                                            </c:choose>
                                        </a>
                                    </p>
                                    </td>
                                    <td>
                                        <span class="timestamp">${notificacion.getDateTime()}</span>
                                    </td>
                                </tr>                            
                            </c:forEach>    
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- END WIDGET TICKET TABLE -->     
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
    <!-- /content-wrapper -->
    </jsp:attribute>

  </t:plantilla_general>
  
  <script src="${direccionContexto}/recursos/js/sigipro/notificaciones-api.js"></script>