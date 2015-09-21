<%-- 
    Document   : EditarEvento
    Created on : Sep 12, 2015, 12:28:28 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Serpentario</li>
                        <li> 
                            <a href="/SIGIPRO/Serpentario/Serpiente?">Serpientes</a>
                        </li>
                        <li class="active">Evento de Serpiente ${serpiente.getNumero_serpiente()}</li>

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
                            <h3><i class="sigipro-snake-1"></i> Editar Evento </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">

                            <form class="form-horizontal" autocomplete="off" method="post" action="Serpiente">
                                <div class="row">
                                    <div class="col-md-6">
                                        <input hidden="true" name="id_evento" value="${evento.getId_evento()}">
                                        <input hidden="true" name="id_serpiente" value="${serpiente.getId_serpiente()}">
                                        <input hidden="true" name="accion" value="${accion}">

                                        <label for="evento" class="control-label">*Evento</label>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="input-group">
                                                    <input type="text" disabled='true' class="form-control" value="${evento.getEvento()}">
                                                </div>
                                            </div>
                                        </div>
                                        <label for="fecha_ingreso" class="control-label">*Fecha de Evento</label>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="input-group">
                                                    <input type="text" value="${evento.getFecha_eventoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepickerSerpiente" class="form-control sigiproDatePicker" name="fecha_evento" data-date-format="dd/mm/yyyy" required
                                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                                           onchange="setCustomValidity('')">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <label for="observaciones" class="control-label">Observaciones</label>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="input-group">
                                                    <textarea rows="5" cols="50" maxlength="2000" placeholder="Observaciones del Evento" class="form-control" name="observaciones" >${evento.getObservaciones()}</textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!-- Esta parte es la de los permisos de un rol -->
                                    <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
                                </div>


                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>

                                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>

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
