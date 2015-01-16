<%-- 
    Document   : Agregar.jsp
    Created on : 08-ene-2015, 20:47:43
    Author     : Walter
--%>

<%@page import="com.icp.sigipro.configuracion.modelos.Seccion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Configuracion" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-10 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Configuración</li>
                        <li>Secciones</li>
                        <li class="active"> Agregar Seccion </li>

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
                            <h3><i class="fa fa-group"></i> Agregar Nueva Seccion </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <form class="form-horizontal" autocomplete="off" role="form" action="Agregar" method="post">
                                <p class="title">Agregar Seccion</p>
                                <label for="nombreUsuario" class="control-label">*Nombre de Seccion</label>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <div class="input-group">
                                            <input type="text" maxlength="45" placeholder="Nombre de Seccion" class="form-control" name="nombre_seccion" required
                                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                                   oninput="setCustomValidity('')" > 
                                        </div>
                                    </div>
                                </div>
                                <label for="nombreCompleto" class="control-label">*Desccripción</label>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <div class="input-group">
                                            <input type="text" maxlength="200" placeholder="Descripción" class="form-control" name="descripcion" required
                                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                                   oninput="setCustomValidity('')">
                                        </div>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Seccion</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <!-- END WIDGET TICKET TABLE -->
                </div>
                <!-- /main-content -->
            </div>
            <!-- /main -->
        </div>

    </jsp:attribute>

</t:plantilla_general>