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
    ProveedorDAO p = new ProveedorDAO();

    List<Proveedor> proveedores = p.obtenerProveedores();

    if(proveedores!=null)
    {
        request.setAttribute("listaProveedores", proveedores);
    }
%>

<t:plantilla_general title="Compras" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-10 content-wrapper">
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
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-legal"></i> Proveedores</h3>
              <div class="btn-group widget-header-toolbar">
                <button class="btn btn-primary btn-sm" style="margin-left:5px;margin-right:5px;" onclick="window.location.href='../Proveedores?accion=agregar'">Agregar Proveedor</button>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table id="datatable-column-filter-roles" class="table table-sorting table-striped table-hover datatable">
                <!-- Columnas -->
                <thead> 
                    <tr>
                        <th>Nombre Proveedor</th>
                        <th>Telfono 1</th>
                        <th>Telfono 2</th>
                        <th>Telfono 3</th>
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

    </jsp:attribute>

  </t:plantilla_general>