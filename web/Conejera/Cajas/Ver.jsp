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

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Bioterio - Conejera</li>
                         <li> 
                            <a href="/SIGIPRO/Conejera/Gruposhembras?">Grupo ${caja.getGrupo().getIdentificador()}</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Conejera/Cajas?id_grupo=${caja.getGrupo().getId_grupo()}">Espacios</a>
                        </li>
                        <li class="active"> ${caja.getNumero()} </li>
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
                            <h3><i class="fa fa-barcode"></i> Espacio  ${caja.getNumero()} del Grupo ${caja.getGrupo().getIdentificador()} </h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">


                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3>Hoja de vida de la coneja</h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <c:choose>
                                            <c:when test="${!(coneja.getId_padre()  == null)}" >
                                                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Conejera/Conejas?accion=editar&id_coneja=${coneja.getId_coneja()}">Editar Hoja de Vida</a>
                                                <a class="btn btn-danger btn-sm boton-accion" data-toggle="modal" data-target="#modalRetirar">Retirar Coneja</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Conejera/Conejas?accion=agregar&id_caja=${caja.getId_caja()}">Agregar Información</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                                <div class="widget-content">
                                    <table class="tabla-ver">
                                        <tr><td> <strong>Fecha de nacimiento:</strong> <td>${coneja.getFecha_nacimiento()} </td></tr>
                                        <tr><td> <strong>Identificación del padre:</strong> <td>${coneja.getId_padre()} </td></tr>
                                        <tr><td> <strong>Identificación de la madre:</strong> <td>${coneja.getId_madre()} </td></tr>
                                        <tr><td> <strong>Fecha Estimada de Retiro:</strong> <td>${coneja.getFecha_retiro_S()} </td></tr>
                                        <tr><td> <strong>Fecha de Ingreso:</strong> <td>${coneja.getFecha_ingreso_S()} </td></tr>
                                        <tr><td> <strong>Fecha Real de Retiro:</strong> <td>${coneja.getFecha_cambio_S()} </td></tr>
                                        <tr><td> <strong>Fecha de Preselección:</strong> <td>${coneja.getFecha_seleccion_S()} </td></tr>
                                    </table>
                                    <br>
                                </div>
                            </div>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3>Cruces de la coneja</h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Conejera/Cruces?accion=agregar&id_coneja=${coneja.getId_coneja()}">Agregar Cruce</a>
                                    </div>     
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                                        <thead> 
                                            <tr>
                                                <th>Fecha del Cruce</th>
                                                <th>Identificacion del Macho</th>
                                                <th>Fecha del Parto</th>
                                                <th>Cantidad de crías</th>
                                                <th>Editar</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${cruces}" var="cruce">

                                                <tr id ="${cruce.getId_cruce()}">
                                                    <td>
                                                        <a href="/SIGIPRO/Conejera/Cruces?accion=ver&id_cruce=${cruce.getId_cruce()}">
                                                            <div style="height:100%;width:100%">
                                                                ${cruce.getFecha_cruce()}
                                                            </div>
                                                        </a>
                                                    </td>
                                                    <td>${cruce.getMacho().getIdentificacion()}</td>
                                                    <td>${cruce.getFecha_parto()}</td>
                                                    <td>${cruce.getCantidad_paridos()}</td>
                                                    <td>
                                                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Conejera/Cruces?accion=editar&id_cruce=${cruce.getId_cruce()}" >Editar</a>
                                                    </td>
                                                </tr>

                                            </c:forEach>
                                        </tbody>
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
                                    
       <t:modal idModal="modalRetirar" titulo="Observaciones">

        <jsp:attribute name="form">
          <h5> ¿Está seguro que desea retirar esta coneja? De ser así por favor indique las observaciones: </h5>
          <form class="form-horizontal" id="form_retirar" data-show-auth="${show_modal_auth}" method="post" action="Conejas">
            <input hidden="true" name="id_coneja" id="id_coneja" value="${coneja.getId_coneja()}">
            <input hidden="true" name="accion" value="Eliminar" id="accion">
            ${mensaje_auth}
            <label for="observaciones" class="control-label">Observaciones</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group">
                  <textarea class="form-control" rows="5" cols="50" maxlength="500" placeholder="Observaciones" class="form-control" id="observaciones" name="observaciones" ></textarea>
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

