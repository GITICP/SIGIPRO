<%-- 
    Document   : Agregar.jsp
    Created on : 08-ene-2015, 20:47:43
    Author     : Walter
--%>

<%@page import="com.icp.sigipro.seguridad.modelos.Puesto"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Seguridad</li>
                        <li>
                            <a href="/SIGIPRO/Seguridad/Puestos/">Puestos</a>
                        </li>
                        <li class="active"> Agregar Puesto </li>

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
                            <h3><i class="fa fa-group"></i> Agregar Nuevo Puesto </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <form class="form-horizontal" autocomplete="off" role="form" action="Agregar" method="post">
                                <p class="title">Agregar Puesto</p>
                                <div class='col-md-6'>
                                <label for="nombreUsuario" class="control-label">*Nombre del Puesto</label>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <div class="input-group">
                                            <input type="text" maxlength="45" placeholder="Nombre del Puesto" class="form-control" name="nombre_puesto" required
                                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                                   oninput="setCustomValidity('')" > 
                                        </div>
                                    </div>
                                </div>
                                </div>
                                <div class='col-md-6'>
                                <label for="nombreCompleto" class="control-label">*Descripción</label>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <div class="input-group">
                                            <input type="text" maxlength="200" placeholder="Descripción" class="form-control" name="descripcion" required
                                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                                   oninput="setCustomValidity('')">
                                        </div>
                                    </div>
                                </div>
                                </div>
                                <p>
                                  Los campos marcados con * son requeridos.
                                </p>
                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger btn-volver" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Puesto</button>
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