<%-- 
    Document   : Editar
    Created on : Apr 11, 2015, 12:33:54 AM
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
              <a href="/SIGIPRO/Serpentario/CatalogoTejido?"> Catálogo de Tejidos</a>
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
              <h3><i class="fa fa-bug"></i> Editar Catálogo Tejido de Serpiente ${serpiente.getNumero_serpiente()} </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
                                <form class="form-horizontal" autocomplete="off" method="post" action="CatalogoTejido">
                                <div class="row">
                                <div class="col-md-6">
                                        <input hidden="true" name="accion" value="${accion}">
                                        <input hidden="true" name="id_serpiente_catalogo_tejido" id="id_serpiente_catalogo_tejido" value="${catalogotejido.getSerpiente().getId_serpiente()}">
                                        <input hidden="true" name="id_ct" id="id_ct" value="${catalogotejido.getId_catalogo_tejido()}">
                                        <label for="catalogotejido" class="control-label">Catálogo de Tejidos</label>
                                        <div class="form-group">
                                            <div class="col-sm-12">
                                                <div class="input-group">
                                                    <input id="id_catalogo_tejido" name="numero_catalogo_tejido" class="form-control" value="${catalogotejido.getNumero_catalogo_tejido()}" disabled="true">
                                                </div>
                                            </div>
                                        </div>
                                        <label for="numero_caja" class="control-label">*Número de Caja</label>
                                        <div class="form-group">
                                          <div class="col-sm-12">
                                            <div class="input-group">
                                              <input rows="5" cols="50" maxlength="10" placeholder="Número de la Caja" class="form-control" value="${catalogotejido.getNumero_caja()}" name="numero_caja" required 
                                                     oninvalid="setCustomValidity('Este campo es requerido')"
                                                     oninput="setCustomValidity('')">
                                            </div>
                                          </div>
                                        </div>
                                        <label for="observaciones" class="control-label">*Posición</label>
                                        <div class="form-group">
                                          <div class="col-sm-12">
                                            <div class="input-group">
                                              <input rows="5" cols="50" maxlength="10" placeholder="Posición en el Catálogo" value="${catalogotejido.getPosicion()}" class="form-control" name="posicion" required 
                                                     oninvalid="setCustomValidity('Este campo es requerido')"
                                                     oninput="setCustomValidity('')">
                                            </div>
                                          </div>
                                        </div>
                                        <label for="estado" class="control-label">Estado</label>
                                        <div class="form-group">
                                          <div class="col-sm-12">
                                            <div class="input-group">
                                              <input rows="5" cols="50" maxlength="20" placeholder="Estado en el Catálogo de Tejidos" value="${catalogotejido.getEstado()}" class="form-control" name="estado">
                                            </div>
                                          </div>
                                        </div>

                                        <label for="observaciones" class="control-label">Observaciones</label>
                                        <div class="form-group">
                                          <div class="col-sm-12">
                                            <div class="input-group">
                                              <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones del paso a Catálogo de Tejidos" class="form-control" name="observacionesCT" >${catalogotejido.getObservaciones()}</textarea>
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

