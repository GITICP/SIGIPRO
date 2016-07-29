<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ratonera" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        <form id="form-eliminar" class="form-horizontal" autocomplete="off" method="post" action="Catalogo_PT">
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
                            <a href="/SIGIPRO/Produccion/Catalogo_PT?">Catálogo de Producto Terminado</a>
                        </li>
                        <li class="active"> ${catalogo_pt.getNombre()} </li>
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
                            <h3><i class="fa fa-list-alt"></i> Pie ${catalogo_pt.getNombre()}</h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Catalogo_PT?accion=editar&id_catalogo_pt=${catalogo_pt.getId_catalogo_pt()}">Editar Producto</a>
                                <a class="btn btn-danger btn-sm boton-accion" onclick="Eliminar(${catalogo_pt.getId_catalogo_pt()}, 'eliminar este producto', '')">Eliminar Producto</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Nombre:</strong> <td>${catalogo_pt.getNombre()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${catalogo_pt.getDescripcion()} </td></tr>
                                <tr><td> <strong>Vida útil (meses):</strong> <td>${catalogo_pt.getVida_util()} </td></tr>
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

