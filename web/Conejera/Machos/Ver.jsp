<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Conejera" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        <form id="form-eliminar-macho" method="post" action="Machos">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_macho" value="${conejo.getId_macho()}" hidden>
        </form>
        
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Conejera</li>
                        <li> 
                            <a href="/SIGIPRO/Conejera/Machos?">Machos</a>
                        </li>
                        <li class="active"> ${conejo.getIdentificacion()} </li>
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
                            <h3><i class="fa fa-barcode"></i> Macho ${conejo.getIdentificacion()}</h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Conejera/Machos?accion=editar&id_macho=${conejo.getId_macho()}">Editar Macho</a>
                                <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar el conejo" data-form-id="form-eliminar-macho">Eliminar Macho</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Identificación:</strong> <td>${conejo.getIdentificacion()} </td></tr>
                                <tr><td> <strong>Fecha de ingreso:</strong> <td>${conejo.getFecha_ingreso_S()} </td></tr>
                                <tr><td> <strong>Fecha de retiro:</strong> <td>${conejo.getFecha_retiro_S()} </td></tr>
                                <tr><td> <strong>Descripción:</strong> <td>${conejo.getDescripcion()} </td></tr>
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

