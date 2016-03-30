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
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Producción</li>
                        <li> 
                            <a href="/SIGIPRO/Produccion/Lote?">Lotes de Producción</a>
                        </li>
                        <li> Realizar Paso de Protocolo de Producción </li>
                        <li class="active"> ${respuesta.getPaso().getPosicion()} - ${respuesta.getPaso().getNombre()} </li>

                    </ul>
                </div>
            </div>

            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-gears"></i> Realizar Paso ${lote.getPosicion_actual()} - ${lote.getPaso_actual().getNombre()} de Protocolo de Producción ${respuesta.getLote().getProtocolo().getNombre()} </h3>
                        </div>
                        ${mensaje}

                        <div class="widget-content">

                            <form method="post" class="form-horizontal" action="Lote" autocomplete="off" enctype='multipart/form-data'>
                                <input type="hidden" value="realizar" name="accion" />
                                <input type="hidden" value="${respuesta.getId_respuesta()}" name="id_respuesta" />

                                <div class="row">
                                    <div class="col-md-12">
                                        ${cuerpo_formulario}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                                        <c:choose>
                                            <c:when test= "${accion_especifica.equals('Editar')}">
                                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                                            </c:when>
                                            <c:otherwise>
                                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Realizar Paso de Protocolo</button>
                                            </c:otherwise>
                                        </c:choose>    
                                    </div>
                                </div>
                            </form>
                        </div>
                        <!-- END WIDGET TICKET TABLE -->
                    </div>
                    <!-- /main-content -->
                </div>
                <!-- /main -->
            </div>
        </div>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/LoteProduccion.js"></script>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <link rel="stylesheet" href="/resources/demos/style.css">
    </jsp:attribute>

</t:plantilla_general>
