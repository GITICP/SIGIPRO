<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Walter
--%>
<%@page import="com.icp.sigipro.compras.dao.ProveedorDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.compras.modelos.Proveedor"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%

    List<Integer> permisos = (List<Integer>) session.getAttribute("listaPermisos");
    if (!(permisos.contains(1) || permisos.contains(14) || permisos.contains(15) || permisos.contains(16))) {
        request.getRequestDispatcher("/").forward(request, response);
    }
    
    ProveedorDAO p = new ProveedorDAO();

    List<Proveedor> proveedores = p.obtenerProveedores();

    if (proveedores != null) {
        request.setAttribute("listaProveedores", proveedores);
    }
%>

<t:plantilla_general title="Compras" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Compras</li>
            <li class="active">Proveedores</li>
          </ul>
        </div>
        <div class="col-md-8 ">
          <div class="top-content">

          </div>
        </div>
      </div>
      <!-- main -->
      <div class="content">
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-truck"></i> Proveedores </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 14}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                  <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Compras/Proveedores?accion=agregar">Agregar Proveedor</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                    <tr>
                        <th>Nombre Proveedor</th>
                        <th>Teléfono 1</th>
                        <th>Teléfono 2</th>
                        <th>Teléfono 3</th>
                        <th>Correo</th>
                    </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaProveedores}" var="proveedor">

                      <tr id ="${proveedor.getId_proveedor()}">
                          <td>
                              <a href="/SIGIPRO/Compras/Proveedores?accion=ver&id_proveedor=${proveedor.getId_proveedor()}">
                                  <div style="height:100%;width:100%">
                                      ${proveedor.getNombre_proveedor()}
                                  </div>
                              </a>
                          </td>
                          <td>${proveedor.getTelefono1()}</td>
                          <td>${proveedor.getTelefono2()}</td>
                          <td>${proveedor.getTelefono3()}</td>
                          <td>${proveedor.getCorreo()}</td>
                      </tr>

                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

    </jsp:attribute>

  </t:plantilla_general>
