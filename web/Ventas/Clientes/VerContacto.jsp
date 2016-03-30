<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : Josue
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-contacto" method="post" action="Contactos">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_contacto" value="${contacto.getId_contacto()}" hidden>
    </form>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Clientes?">Clientes</a>
            </li>
            <li>
                <a href="/SIGIPRO/Ventas/Clientes?accion=ver&id_cliente=${contacto.getCliente().getId_cliente()}">Cliente ${contacto.getCliente().getNombre()} </a>
            </li>
            <li class="active">Contacto ${contacto.getNombre()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-user"></i> Contacto ${contacto.getNombre()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/Clientes?accion=editar_contacto&id_contacto=${contacto.getId_contacto()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este contacto" data-form-id="form-eliminar-contacto">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre:</strong></td> <td>${contacto.getNombre()} </td></tr>
                <tr><td> <strong>Cliente Asociado:</strong> <td><a href="/SIGIPRO/Ventas/Clientes?accion=ver&id_cliente=${contacto.getCliente().getId_cliente()}">${contacto.getCliente().getNombre()} </a></td></tr>
                <tr><td> <strong>Teléfono:</strong> <td>${contacto.getTelefono()} </td></tr>
                <tr><td> <strong>Teléfono 2:</strong> <td>${contacto.getTelefono2()} </td></tr>
                <tr><td> <strong>Correo Electrónico:</strong> <td>${contacto.getCorreo_electronico()} </td></tr>
                <tr><td> <strong>Correo Electrónico 2:</strong> <td>${contacto.getCorreo_electronico2()} </td></tr>
              </table>
              <br>
              
            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>
