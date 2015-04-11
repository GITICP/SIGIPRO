<%-- 
    Document   : Editar
    Created on : Apr 11, 2015, 12:36:48 AM
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
              <a href="/SIGIPRO/Serpentario/ColeccionHumeda?"> Colección Húmeda</a>
            </li>
            <li class="active">Serpiente ${serpiente.getNumero_serpiente()}</li>

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
              <h3><i class="fa fa-bug"></i> Editar Colección Húmeda de Serpiente ${serpiente.getNumero_serpiente()} </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
                            <form class="form-horizontal" autocomplete="off" method="post" action="ColeccionHumeda">
                            <div class="row">
                            <div class="col-md-6">
                                <input hidden="true" name="accion" value="${accion}">
                                <input hidden="true" name="id_serpiente_coleccion_humeda" id="id_serpiente_coleccion_humeda" value="${coleccionhumeda.getSerpiente().getId_serpiente()}">
                                <input hidden="true" name="id_ch" id="id_ch" value="${coleccionhumeda.getId_coleccion_humeda()}">
                                <label for="especie" class="control-label"> Colección Húmeda</label>
                                <div class="form-group">
                                    <div class="col-sm-12">
                                        <div class="input-group">
                                            <input id="id_coleccion_humeda" name="numero_coleccion_humeda" class="form-control" value="${coleccionhumeda.getNumero_coleccion_humeda()}" disabled="true">
                                        </div>
                                    </div>
                                </div>
                                <label for="proposito" class="control-label"> *Propósito</label>
                                <div class="form-group">
                                  <div class="col-sm-12">
                                    <div class="input-group">
                                      <input rows="5" cols="50" maxlength="200" placeholder="Propósito del paso a Colección Húmeda" class="form-control" name="proposito" value='${coleccionhumeda.getProposito()}' required 
                                             oninvalid="setCustomValidity('Este campo es requerido')"
                                             oninput="setCustomValidity('')">
                                    </div>
                                  </div>
                                </div>

                                <label for="observaciones" class="control-label"> Observaciones</label>
                                <div class="form-group">
                                  <div class="col-sm-12">
                                    <div class="input-group">
                                      <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones del paso a Colección Húmeda" class="form-control" name="observacionesCH" >${coleccionhumeda.getObservaciones()}</textarea>
                                    </div>
                                  </div>
                                </div>
                                <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
                            </div>
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

