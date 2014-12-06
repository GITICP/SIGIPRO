<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Inicio" direccion_contexto="/SIGIPRO">
    
    <jsp:attribute name="contenido">

        <jsp:include page="plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-10 content-wrapper">
                <div class="row">
                        <div class="col-md-4 ">
                                <ul class="breadcrumb">
                                        <li class="active">Inicio</li>
                                </ul>
                        </div>
                        <div class="col-md-8 ">
                                <div class="top-content">

                                </div>
                        </div>
                </div>

                <!-- main -->
                <div class="content">
                    <h2>Inicio</h2>

                        <div class="main-content">


                        </div>
                        <!-- /main-content -->
                </div>
                <!-- /main -->
        </div>
        <!-- /content-wrapper -->

    </jsp:attribute>

</t:plantilla_general>