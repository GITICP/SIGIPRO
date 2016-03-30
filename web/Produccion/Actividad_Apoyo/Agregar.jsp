<%-- 
    Document   : Agregar
    Created on : Jun 29, 2015, 4:46:37 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Producción" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <style>
            #sortable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
            .ui-state-highlight { height: 1.5em; line-height: 1.2em; }
        </style>

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Producción</li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Actividad_Apoyo?">Actividades de Apoyo</a>
                        </li>
                        <li class="active"> Agregar Actividad de Apoyo </li>

                    </ul>
                </div>
            </div>

            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Agregar Nueva Actividad de Apoyo </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">

                            <jsp:include page="Formulario.jsp"></jsp:include>

                            </div>
                        </div>
                        <!-- END WIDGET TICKET TABLE -->
                    </div>
                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/ActividadApoyo.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
    </jsp:attribute>

</t:plantilla_general>
