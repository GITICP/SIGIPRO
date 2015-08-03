<%-- 
    Document   : Agregar
    Created on : Jul 5, 2015, 8:19:17 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Control de Calidad" direccion_contexto="/SIGIPRO">

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
                        <li>Control de Calidad</li>
                        <li> 
                            <a href="/SIGIPRO/ControlCalidad/Analisis?">Análisis</a>
                        </li>
                        <li class="active"> Agregar Nuevo Análisis </li>

                    </ul>
                </div>
            </div>

            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Agregar Nuevo Análisis </h3>
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
        <script src="/SIGIPRO/recursos/js/sigipro/Analisis.js"></script>   
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">

    </jsp:attribute>
</t:plantilla_general>

<t:modal idModal="modalErrorResultado" titulo="Error en el Formulario">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-liofilizacion-inicio">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="label" class="control-label">Es requerido elegir un campo de Resultado al formulario. Favor asignar un Resultado al análisis. </label>

        </div>

    </jsp:attribute>

</t:modal>
        
        <t:modal idModal="modalErrorExcel" titulo="Error en el Formulario">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-liofilizacion-inicio">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="label" class="control-label">Es requerido elegir un archivo de Excel si tiene celdas asociadas al formulario. Favor subir un archivo de Excel o deseleccionar los campos tipo Excel.</label>

        </div>

    </jsp:attribute>

</t:modal>