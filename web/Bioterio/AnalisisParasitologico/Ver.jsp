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
        <form id="form-eliminar-analisis" method="post" action="AnalisisParasitologico">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_analisis" value="${analisis.getId_analisis()}" hidden>
        </form>
        
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Análisis</li>
                        <li> 
                            <a href="/SIGIPRO/Bioterio/AnalisisParasitologico?">Análisis Parasitológico</a>
                        </li>
                        <li class="active"> ${analisis.getNumero_informe()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-list-alt"></i> Análisis Parasitológico ${analisis.getNumero_informe()}</h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Bioterio/AnalisisParasitologico?accion=editar&id_analisis=${analisis.getId_analisis()}">Editar Análisis</a>
                                <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este análisis" data-form-id="form-eliminar-analisis">Eliminar Análisis</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Número de informe: </strong> <td>${analisis.getNumero_informe()} </td></tr>
                                <tr><td> <strong>Fecha:</strong> <td>${analisis.getFecha_S()} </td></tr>
                                <tr><td> <strong>Responsable</strong> <td>${analisis.getResponsable().getNombre_completo()} </td></tr>
                                <tr><td> <strong>Especie:</strong> <td>${analisis.getEspecie()} </td></tr>
                                <tr><td> <strong>Resultados:</strong> <td>${analisis.getResultados()} </td></tr>
                                <tr><td> <strong>Tratamiento y Dosis:</strong> <td>${analisis.getTratamiento_dosis()} </td></tr>
                                <tr><td> <strong>Fecha de Tratamiento:</strong> <td>${analisis.getFecha_tratamiento_S()} </td></tr>
                                <tr><td> <strong>Recetado por:</strong> <td>${analisis.getRecetado_por()} </td></tr>
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

