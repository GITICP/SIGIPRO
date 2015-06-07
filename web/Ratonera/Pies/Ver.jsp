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
        <form id="form-eliminar-pie" method="post" action="Pies">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_pie" value="${pie.getId_pie()}" hidden>
        </form>
        
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Ratonera</li>
                        <li> 
                            <a href="/SIGIPRO/Ratonera/Pies?">Pies</a>
                        </li>
                        <li class="active"> ${pie.getCodigo()} </li>
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
                            <h3><i class="sigipro-mouse-1"></i> Pie ${pie.getCodigo()}</h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ratonera/Pies?accion=editar&id_pie=${pie.getId_pie()}">Editar Pie</a>
                                <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar el pie" data-form-id="form-eliminar-pie">Eliminar Pie</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Código:</strong> <td>${pie.getCodigo()} </td></tr>
                                <tr><td> <strong>Fecha de ingreso:</strong> <td>${pie.getFecha_ingreso_S()} </td></tr>
                                <tr><td> <strong>Fecha de retiro:</strong> <td>${pie.getFecha_retiro_S()} </td></tr>
                                <tr><td> <strong>Fuente:</strong> <td>${pie.getFuente()} </td></tr>
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
    <script src="/SIGIPRO/recursos/js/sigipro/cajas.js"></script>
</jsp:attribute>
</t:plantilla_general>

