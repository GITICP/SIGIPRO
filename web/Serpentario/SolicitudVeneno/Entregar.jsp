<%-- 
    Document   : Entregar
    Created on : Apr 2, 2015, 1:49:04 AM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/SolicitudVeneno?">Solicitudes de Veneno</a>
            </li>
            <li class="active"> Entregar Solicitud </li>

          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-flask"></i> Entregar Solicitud ${solicitud.getId_solicitud()} - Cantidad Solicitada ${solicitud.getCantidad()} gramos</h3>
            </div>
            ${mensaje}
            <div class="widget-content">

             <form class="form-horizontal" id="entregarSolicitud" name="entregarSolicitud" >
                    <input hidden="true" name="accion" value="${accion}">
                    <input id="id_solicitud" hidden="true" name="id_solicitud" value="${solicitud.getId_solicitud()}">
                    <div class="col-md-12">
                    <label for="lotes" class="control-label">Cantidad Solicitada (gramos)</label>
                    <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <input name="cantidad_solicitada" class='form-control' value="${solicitud.getCantidad()}" disabled="true">
                                </div>
                            </div>
                        </div>
                    <label for="lotes" class="control-label">*Lotes</label>
                    <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group"id='inputGroupSeleccionLote'>
                                    <select id="seleccionLote" class="select2" name="lote_solicitud"
                                                style='background-color: #fff;' required
                                                oninvalid="setCustomValidity('Este campo es requerido')"
                                                onchange="setCustomValidity('')">
                                            <option value=''></option>
                                            <c:forEach items="${lotes}" var="lote">
                                                <option value=${lote.getId_lote()}>Lote ${lote.getNumero_lote()} (Cantidad - ${lote.getCantidad_actual()} gramos)</option>
                                            </c:forEach>
                                    </select>
                                    <div><br></div>
                                    <button id="btn-agregarLote" type="button" class="btn btn-primary" onclick="setLote()"><i class="fa fa-check-circle"></i> Agregar Lote</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    </form>
                    <form class="form-horizontal" id="agregarLote" name="agregarLote" autocomplete="off" method="post" action="SolicitudVeneno">
                        <input hidden="true" name="accion" value="${accion}">
                        <input id="id_solicitud" hidden="true" name="id_solicitud" value="${solicitud.getId_solicitud()}">
                        <div class="col-md-12">
                            <div class="widget widget-table">
                              <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Lotes a Entregar</h3>
                              </div>
                              <div class="widget-content">
                                <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                                  <thead>
                                    <tr>
                                      <th>Lote</th>
                                      <th>Cantidad a Entregar (gramos)</th>
                                      <th>Eliminar</th>
                                    </tr>
                                  </thead>
                                  <tbody>

                                  </tbody>
                                </table>
                              </div>
                            </div>
                            <p>
                              Los campos marcados con * son requeridos.
                            </p> 
                              <!-- /main -->
                            
                  <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                        <button type="button" class="btn btn-primary" onclick="confirmacionAgregarLotes(${solicitud.getId_solicitud()},'${solicitud.getUsuario().getNombreCompleto()}','${solicitud.getEspecie().getGenero_especie()}',${solicitud.getCantidad()})"><i class="fa fa-check-circle"></i> Realizar Entrega</button>
                    </div>
                  </div>

                </div>
                </form>
                </div>
                

            </div>
          </div>
            
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>

<t:modal idModal="modalEntregar" titulo="Entregar Solicitud">

        <jsp:attribute name="form">
            <form class="form-horizontal" id="form_modalautorizar" method="post" data-show-auth="${show_modal_auth}" action="SolicitudVeneno">
            ${mensaje_auth}
            <h4> Información sobre la solicitud </h4>
            <div class="form-group">
              <div class="col-sm-12">
                <label for="num-sol" class="control-label"> Número de Solicitud </label>  
                <input type="text" id="num-sol"  name="num-sol" disabled>
              </div>
              <div class="col-sm-12">
                <label for="usr-sol" class="control-label"> Usuario Solicitante </label>
                <input type="text" id="usr-sol"  name="usr-sol" disabled>
              </div>
              <div class="col-sm-12">
                <label for="prd" class="control-label"> Especie  </label>
                <input type="text" id="esp"  name="esp" disabled>
              </div>
              <div class="col-sm-12">
                <label for="cnt" class="control-label"> Cantidad Solicitada (gramos) </label>
                <input type="text" id="cnt"  name="cnt" disabled>
              </div>
                
              <div class="col-sm-12">
                <label for="cnt" class="control-label"> Cantidad a Entregar (gramos) </label>
                <input type="text" id="cntEnt"  name="cnt" disabled>
              </div>
            </div>
            <hr>
            <div><br></div>
          <h5>Para validar la entrega, el usuario recipiente debe iniciar sesión. </h5>
          
            <input hidden="true" name="id_solicitud_entregar" id="id_solicitud_entregar">
            <input hidden="true" name="accion" id="accion" value="Entregar">
            <input hidden="true" name="lotes" id="lotes">
            <input hidden="true" name="cantidad_entregada" id="cantidad_entregada">
            ${mensaje_auth}
        
            <label for="usr" class="control-label">Usuario</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group" style="display:table;">
                  <input type="text" id="usr"  name="usuario_recibo" required
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
              </div>
            </div>
            <label for="passw" class="control-label">Contraseña</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group" style="display:table;">
                  <input type="password" id="passw" name="passw" required
                         oninvalid="setCustomValidity('Este campo es requerido ')"
                         onchange="setCustomValidity('')">
                </div>
                <p id='mensajeValidación' style='color:red;'><p>
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

      <t:modal idModal="modalError" titulo="Advertencia">

        <jsp:attribute name="form">
          <form class="form-horizontal" id="form_modalrechazar" data-show-auth="${show_modal_auth}" method="post" action="Solicitudes">
            <h5> No puede entregar menos de lo solicitado. </h5>
            <table>
                <tr><td> <strong>Cantidad solicitada:</strong> <td id="cantidad_solicitada_error"></td></tr>
                <tr><td> <strong>Cantidad a entregar:</strong> <td id="cantidad_entregada_error"></td></tr>
            </table>
    
            <div class="form-group">
              <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              </div>
            </div>
          </form>


        </jsp:attribute>

      </t:modal>

  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/SolicitudSerpentario.js"></script>
  </jsp:attribute>
</t:plantilla_general>

