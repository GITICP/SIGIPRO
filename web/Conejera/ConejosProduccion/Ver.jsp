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
        <form id="form-eliminar-conejoproduccion" method="post" action="ConejosProduccion">
            <input name="accion" value="Eliminar" hidden> 
            <input name="id_produccion" value="${conejo.getId_produccion()}" hidden>
        </form>
        
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Conejera</li>
                        <li> 
                            <a href="/SIGIPRO/Conejera/ConejosProduccion?">Grupos de Conejos de Produccion</a>
                        </li>
                        <li class="active"> ${conejo.getIdentificador()} </li>
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
                            <h3><i class="fa fa-barcode"></i> Grupo ${conejo.getIdentificador()}</h3>
                            <div class="btn-group widget-header-toolbar">
                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Conejera/ConejosProduccion?accion=editar&id_produccion=${conejo.getId_produccion()}">Editar Grupo</a>
                                <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar el grupo de producción" data-form-id="form-eliminar-conejoproduccion">Eliminar Grupo</a>
                                <c:if test="${conejo.getMortalidad() ==-1}">
                                   <a class="btn btn-warning btn-sm boton-accion" data-toggle="modal" data-target="#modalTraslado">Trasladar Grupo</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table class="tabla-ver">
                                <tr><td> <strong>Fecha del Grupo:</strong> <td>${conejo.getIdentificador()} </td></tr>
                                <tr><td> <strong>Cantidad:</strong> <td>${conejo.getCantidad()} </td></tr>
                                <tr><td> <strong>Observaciones:</strong> <td>${conejo.getDetalle_procedencia()} </td></tr>
                                <c:if test="${conejo.getMortalidad() >-1}">
                                <tr><td> <strong>Mortalidad:</strong> <td>${conejo.getMortalidad()} </td></tr>
                                </c:if>
                                    
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
<t:modal idModal="modalTraslado" titulo="Observaciones">

            <jsp:attribute name="form">
                <h5> ¿Está seguro que desea trasladar el grupo? De ser así, ingrese la cantidad de conejos vivos: </h5>
                <form class="form-horizontal" id="form_modaltraslado" data-show-auth="${show_modal_auth}" method="post" action="ConejosProduccion">
                    <input hidden="true" name="id_produccion" id="id_produccion" value="${conejo.getId_produccion()}" >
                    <input hidden="true" name="accion" id="accion" value="trasladar">
                    <label for="cant_vivos" class="control-label">Cantidad de Conejos Vivos</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group" style="display:table;">
                               <input id="cant_vivos" placeholder="" type="number" min="0" class="form-control" name="cant_vivos" required 
                  oninvalid="setCustomValidity('Este campo es requerido, por favor ingrese un número válido. ')"
                  oninput="setCustomValidity('')"> 
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="modal-footer">
                            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Aceptar</button>
                        </div>
                    </div>
                </form>


            </jsp:attribute>

        </t:modal>
</jsp:attribute>
<jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/cajas.js"></script>
</jsp:attribute>
</t:plantilla_general>

