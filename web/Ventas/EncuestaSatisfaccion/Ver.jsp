<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-cliente" method="post" action="Clientes">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_cliente" value="${cliente.getId_cliente()}" hidden>
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
            <li class="active">Cliente ${cliente.getNombre()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-group"></i> Cliente ${cliente.getNombre()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 702 || permiso == 701}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ventas/Clientes?accion=editar&id_cliente=${cliente.getId_cliente()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este cliente" data-form-id="form-eliminar-cliente">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre: </strong></td> <center> <td> ${cliente.getNombre()} </td> </center> </tr>
                <tr><td> <strong>Tipo: </strong>  </td> <center> <td> ${cliente.getTipo()}   </td> </center> </tr>
                <tr><td> <strong>País: </strong>  </td> <center> <td> ${cliente.getPais()}   </td> </center> </tr>
              </table>
              <br>
              
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-user"></i> Contactos Asociados </h3>
                  <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Ventas/Clientes?accion=agregar_contacto&id_cliente=${cliente.getId_cliente()}">Agregar un Contacto</a>
                </div>  
                </div>
                <div class="widget-content">
                  <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                    <thead>
                      <tr>
                        <th>Nombre</th>
                        <th>Teléfono</th>
                        <th>Teléfono 2</th>
                        <th>Correo Electrónico</th>
                        <th>Correo Electrónico 2</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listaContactos}" var="contacto">
                          <tr id ="${contacto.getId_contacto()}">
                            <td>
                              <a href="/SIGIPRO/Ventas/Clientes?accion=ver_contacto&id_contacto=${contacto.getId_contacto()}">
                              <div style="height:100%;width:100%">
                                  ${contacto.getNombre()}
                              </div>
                              </a>
                            </td>
                            <td>${contacto.getTelefono()}</td>
                            <td>${contacto.getTelefono2()}</td>
                            <td>${contacto.getCorreo_electronico()}</td>
                            <td>${contacto.getCorreo_electronico2()}</td>
                          </tr>
                        </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
              
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
