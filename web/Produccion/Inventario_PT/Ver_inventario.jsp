<%-- 
    Document   : Ver_inventario
    Created on : Dec 2, 2015, 4:47:39 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Inventario de Producto Terminado" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        <form id="form-eliminar" class="form-horizontal" autocomplete="off" method="post" action="Inventario_PT">
          <input hidden="true" id="id_eliminar" name="id_eliminar" value="">
          <input hidden="true" id="accion" name="accion" value="">
        </form>
        
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Inventario_PT?">Inventario de Producto Terminado</a>
            </li>
            <li class="active"> Inventario de Producto Terminado </li>
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
                            <h3><i class="fa fa-list-alt"></i> Inventario Lote: ${inventario.getLote()}</h3>
                            <div class="btn-group widget-header-toolbar">
                               <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Inventario_PT?accion=editar_inventario&id_inventario_pt=${inventario.getId_inventario_pt()}">Editar</a>
                               <a class="btn btn-danger btn-sm boton-accion" onclick="Eliminar(${inventario.getId_inventario_pt()}, 'eliminar esta entrada de inventario', 'inventario')">Eliminar</a>                      
                          </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Lote:</strong> <td>${inventario.getLote()} </td></tr>
                                <tr><td> <strong>Producto:</strong> <td>${inventario.getProducto().getNombre()} </td></tr>
                                <tr><td> <strong>Fecha de vencimiento:</strong> <td>${inventario.getFecha_vencimiento_S()} </td></tr>
                                <tr><td> <strong>Cantidad:</strong> <td>${inventario.getCantidad()} </td></tr>
                                <tr><td> <strong>Cantidad Disponible:</strong> <td>${inventario.getCantidad_disponible()} </td></tr>     
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <!-- /main-content -->
        </div>
        <!-- /main -->
    </div>
</div>

</jsp:attribute>
 <jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/Produccion/Inventario/Eliminar.js"></script>
  </jsp:attribute>
</t:plantilla_general>

