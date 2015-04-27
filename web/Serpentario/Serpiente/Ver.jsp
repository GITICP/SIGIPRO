<%-- 
    Document   : Ver
    Created on : Jan 11, 2015, 11:57:19 AM
    Author     : Walter
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
    <form method="get" action="Serpiente" id="form-pasoCV">
                                    <input hidden="true" name="accion" value="coleccionviva">
                                    <input hidden="true" name="id_serpiente" value="${serpiente.getId_serpiente()}">
    </form>
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Serpiente?">Serpientes</a>
            </li>
            <li class="active"> ${serpiente.getNumero_serpiente()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-bug"></i>Serpiente ${serpiente.getNumero_serpiente()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEvento" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 312}">
                    <c:set var="contienePermisoEvento" value="true" />
                  </c:if>
                </c:forEach>
                <c:set var="contienePermisoDeceso" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 313}">
                    <c:set var="contienePermisoDeceso" value="true" />
                  </c:if>
                </c:forEach>
                    <c:choose>
                        <c:when test="${deceso==null}">
                            <c:if test="${contienePermisoDeceso}">
                                <a class="btn btn-danger btn-sm boton-accion deceso-Modal" data-id='${serpiente.getId_serpiente()}' data-toggle="modal" data-target="#modalDeceso">Deceso</a>
                            </c:if>
                            <c:choose>
                            <c:when test="${coleccionViva == null}">
                                <c:if test="${contienePermisoEvento}">
                                    <a class="btn btn-primary btn-sm boton-accion confirmable-form" data-form-id="form-pasoCV" data-texto-confirmacion="pasar la serpiente a Colección Viva">Paso a CV</a>
                                </c:if>
                            </c:when>
                            </c:choose>
                               <c:if test="${contienePermisoEvento}">
                                    <a class="btn btn-primary btn-sm boton-accion evento-Modal" data-id='${serpiente.getId_serpiente()}' data-toggle="modal" data-target="#modalAgregarEvento">Evento</a>
                               </c:if>
                            <c:set var="contienePermisoEditar" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                              <c:if test="${permiso == 1 || permiso == 311 || permiso == 315}">
                                <c:set var="contienePermisoEditar" value="true" />
                              </c:if>
                            </c:forEach>
                            <c:if test="${contienePermisoEditar}">
                              <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Serpentario/Serpiente?accion=editar&id_serpiente=${serpiente.getId_serpiente()}">Editar</a>
                            </c:if>
                        </c:when>
                        <c:otherwise>
                            <c:set var="contienePermisoCHCT" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                              <c:if test="${permiso == 1 || permiso == 314}">
                                <c:set var="contienePermisoCHCT" value="true" />
                              </c:if>
                            </c:forEach>
                            <c:if test="${contienePermisoCHCT}">
                                <c:choose>
                                    <c:when test="${!descarte}">
                                        <c:if test="${coleccionhumeda == null}">
                                            <a class="btn btn-primary btn-sm boton-accion ch-Modal" data-id='${serpiente.getId_serpiente()}' data-toggle="modal" data-target="#modalAgregarColeccionHumeda">Colección Húmeda</a>
                                        </c:if>
                                        <c:if test="${catalogotejido == null}">
                                            <a class="btn btn-primary btn-sm boton-accion ct-Modal" data-id='${serpiente.getId_serpiente()}' data-toggle="modal" data-target="#modalAgregarCatalogoTejido">Catálogo Tejido</a>
                                        </c:if>
                                        <c:if test="${coleccionhumeda==null && catalogotejido==null}">
                                            <a class="btn btn-danger btn-sm boton-accion confirmable"  data-texto-confirmacion="descartar la Serpiente" data-href="/SIGIPRO/Serpentario/Serpiente?accion=descartar&id_serpiente=${serpiente.getId_serpiente()}">Descartar</a>
                                        </c:if>
                                    </c:when>
                                    <c:otherwise>
                                    </c:otherwise>
                                </c:choose>
                                
                            </c:if>
                            
                            
                        </c:otherwise>
                    </c:choose>
                <!--
                <c:set var="contienePermisoReversar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 316}">
                    <c:set var="contienePermisoReversar" value="true" />
                  </c:if>
                </c:forEach>
                -->
                
              </div>
            </div>
            ${mensaje}
            <div class="row">
            <div class="col-md-6">
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre de la Especie:</strong> <td>${serpiente.getEspecie().getGenero_especie()} </td></tr>
                <tr><td> <strong>Número de Ingreso:</strong> <td>${serpiente.getNumero_serpiente()} </td></tr>
                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${serpiente.getFecha_ingresoAsString()} </td></tr>
                <tr><td> <strong>Localidad de Origen:</strong> <td>${serpiente.getLocalidad_origen()} </td></tr>
                <tr><td> <strong>Colectada por:</strong> <td>${serpiente.getColectada()} </td></tr>
                <tr><td> <strong>Recibida por:</strong> <td>${serpiente.getRecibida().getNombre_completo()} </td></tr>
                <tr><td> <strong>Sexo:</strong> <td>${serpiente.getSexo()} </td></tr>
                <tr><td> <strong>Talla (Longitud Cabeza-Cloaca):</strong> <td>${serpiente.getTalla_cabeza()} Centímetros </td></tr>
                <tr><td> <strong>Talla (Longitud Cola):</strong> <td>${serpiente.getTalla_cola()} Centímetros</td></tr>
                <tr><td> <strong>Talla (Longitud Total):</strong> <td>${serpiente.getTalla_total()} Centímetros</td></tr>
                <tr><td> <strong>Peso:</strong> <td>${serpiente.getPeso()} Gramos</td></tr>
                <c:choose>
                    <c:when test="${coleccionViva != null}">
                        <tr><td><strong><div><br></div> </strong></td></tr>
                        <tr><td> <strong>Fecha de Paso a Colección Viva:</strong> <td>${coleccionViva.getFecha_eventoAsString()} </td></tr>
                        <tr><td> <strong>Responsable de Paso a Colección Viva:</strong> <td>${coleccionViva.getUsuario().getNombreCompleto()} </td></tr>
                        <tr><td><strong><div><br></div></strong></td></tr>
                    </c:when>
                </c:choose>
                <c:if test="${coleccionhumeda != null}">
                    <tr><td> <strong>Colección Húmeda:</strong> <td><a href="/SIGIPRO/Serpentario/ColeccionHumeda?accion=ver&id_serpiente=${serpiente.getId_serpiente()}">Ver Colección Húmeda</a> </td></tr>
                </c:if>
                <c:if test="${catalogotejido != null}">
                     <tr><td> <strong>Catálogo de Tejidos:</strong> <td><a href="/SIGIPRO/Serpentario/CatalogoTejido?accion=ver&id_serpiente=${serpiente.getId_serpiente()}">Ver Catálogo de Tejido</a> </td></tr>
                </c:if>
                                <c:choose>
                                    <c:when test="${deceso!=null}">
                                        <tr><td> <strong>Días en Cautiverio:</strong> <td>${serpiente.getDias_cautiverio(deceso.getFecha_evento())} </td></tr>
                                    </c:when>
                                    <c:otherwise>
                                        <tr><td> <strong>Días en Cautiverio:</strong> <td>${serpiente.getDias_cautiverio()} </td></tr>
                                    </c:otherwise>
                                </c:choose>
              </table>
            </div>
            </div>
                <div class="col-md-6" align="right">
                    <div class="widget-content">
                        <c:if test="${!imagenSerpiente.equals('')}">
                                    <img src="${imagenSerpiente}" height="250" width="250">
                        </c:if>
                    </div>
                    
                    
                </div>
            </div>
                <div class="col-md-12">
           <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-users"></i> Eventos</h3>
            </div>
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Fecha del Evento</th>
                    <th>Evento</th>
                    <th>Usuario responsable</th>
                    <th>Valor Cambiado</th>
                    <th>Extraccion</th>
                    <th>Accion</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaEventos}" var="eventos">
                    <tr id ="${eventos.getId_evento()}">
                      <td>${eventos.getFecha_eventoAsString()}</td>
                      <td>
                        <a data-toggle="modal" data-id="${eventos.getObservaciones()}" class='open-Modal' data-target="#modalVerEvento">
                        <div style="height:100%;width:100%">
                            ${eventos.getEvento()}
                          </div>
                        </a>
                      </td>
                      <td>${eventos.getUsuario().getNombreCompleto()}</td>
                      <c:choose>
                          <c:when test="${!eventos.getValor_cambiado().equals('')}">
                              <td>${eventos.getValor_cambiado()}</td>
                          </c:when>
                          <c:otherwise>
                              <td></td>
                          </c:otherwise>
                      </c:choose>
                      <c:choose>
                          <c:when test="${eventos.getExtraccion()!= null && eventos.getExtraccion().getId_extraccion()!=0}">
                              <td>
                              <a href="/SIGIPRO/Serpentario/Extraccion?accion=ver&id_extraccion=${eventos.getExtraccion().getId_extraccion()}">
                                    <div style="height:100%;width:100%">
                                      ${eventos.getExtraccion().getNumero_extraccion()}
                                    </div>
                                  </a>
                               </td>
                          </c:when>
                          <c:otherwise>
                              <td></td>
                          </c:otherwise>
                      </c:choose>
                              <td>
                          <c:if test="${eventos.getId_categoria()==5}">
                              <c:if test="${contienePermisoReversar}">
                                    <a class="btn btn-danger btn-sm boton-accion rPV-Modal" data-id='${serpiente.getId_serpiente()}' data-toggle="modal" data-target="#modalReversarCV">Reversar</a>
                              </c:if>
                          </c:if>
                              </td>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
            </div>
           </div>
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

<t:modal idModal="modalVerEvento" titulo="Ver Observaciones del Evento">
    <jsp:attribute name="form">
        <div class="widget-content">
              <table>
                  <tr><td> <strong>Observaciones:</strong><div id ="observacionesModal"></div> <td></td></tr>
              </table>
            </div>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            </div>
        </div>


    </jsp:attribute>

</t:modal>
    
<t:modal idModal="modalAgregarEvento" titulo="Agregar Eventos">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarEventos" autocomplete="off" method="post" action="Serpiente">
                <input hidden="true" name="accion" value="Evento">
                <input hidden="true" id='id_serpiente' name='id_serpiente' value="">
                <label for="especie" class="control-label">*Evento</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <select id="seleccionEvento" class="select2" name="eventoModal"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${listaTipoEventos}" var="evento">
                                <option value=${evento}> ${evento}</option>
                            </c:forEach>
                            
                            </select>
                        </div>
                    </div>
                </div>
                <label for="observaciones" class="control-label">Observaciones</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones del Evento" class="form-control" name="observacionesModal" ></textarea>
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Evento</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
    
<t:modal idModal="modalDeceso" titulo="Registrar Deceso">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarEventos" autocomplete="off" method="post" action="Serpiente">
                <input hidden="true" name="accion" value="Deceso">
                <input hidden="true" id='id_serpiente_deceso' name='id_serpiente_deceso'>
                <label for="especie" class="control-label">*Fecha de Deceso</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepickerSerpiente" class="form-control sigiproDatePickerSerpiente" name="fecha_deceso" data-date-format="dd/mm/yyyy" required
                                oninvalid="setCustomValidity('Este campo es requerido y no pueden ser fechas futuras. ')"
                                onchange="setCustomValidity('')">
                            </select>
                        </div>
                    </div>
                </div>
                <label for="observaciones" class="control-label">*Observaciones</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones del Evento" class="form-control" name="observacionesModal" required
                                oninvalid="setCustomValidity('Este campo es requerido. ')"
                                oninput="setCustomValidity('')"></textarea>
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Deceso</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalAgregarColeccionHumeda" titulo="Agregar Serpiente a Colección Húmeda">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarColeccionHumeda" autocomplete="off" method="post" action="ColeccionHumeda">
                <input hidden="true" name="accion" value="agregar">
                <input hidden="true" name="id_serpiente_coleccion_humeda" id="id_serpiente_coleccion_humeda">
                <label for="especie" class="control-label">Colección Húmeda</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="number" min="0" id="id_coleccion_humeda" name="numero_coleccion_humeda" value="${siguienteCH}" class="form-control" required
                                oninvalid="setCustomValidity('Este campo es requerido y debe ser número.')"
                                oninput="setCustomValidity('')">
                        </div>
                    </div>
                </div>
                <label for="observaciones" class="control-label">*Propósito</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <select id="seleccionEvento" class="select2" name="proposito"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <option value='Docencia'>Docencia</option>
                            <option value='Investigación'>Investigación</option>
                            
                            
                            </select>
                    </div>
                  </div>
                </div>
                
                <label for="observaciones" class="control-label">Observaciones</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones del paso a Colección Húmeda" class="form-control" name="observacionesCH" ></textarea>
                    </div>
                  </div>
                </div>
                <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>

            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar a Colección Húmeda</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
    
 <t:modal idModal="modalAgregarCatalogoTejido" titulo="Agregar Serpiente a Catálogo de Tejidos">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarCatalogoTejido" autocomplete="off" method="post" action="CatalogoTejido">
                <input hidden="true" name="accion" value="agregar">
                <input hidden="true" name="id_serpiente_catalogo_tejido" id="id_serpiente_catalogo_tejido">
                <label for="especie" class="control-label">Catálogo de Tejidos</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="number" min="0" id="id_catalogo_tejido" value="${siguienteCT}" name="numero_catalogo_tejido" class="form-control" required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                oninput="setCustomValidity('')">
                        </div>
                    </div>
                </div>
                <label for="numero_caja" class="control-label">*Número de Caja</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <input rows="5" cols="50" maxlength="10" placeholder="Número de la Caja" class="form-control" name="numero_caja" required 
                             oninvalid="setCustomValidity('Este campo es requerido')"
                             oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
                <label for="observaciones" class="control-label">*Posición</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <input rows="5" cols="50" maxlength="10" placeholder="Posición en el Catálogo" class="form-control" name="posicion" required 
                             oninvalid="setCustomValidity('Este campo es requerido')"
                             oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
                <label for="estado" class="control-label">Estado</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <input rows="5" cols="50" maxlength="100" placeholder="Estado en el Catálogo de Tejidos" class="form-control" name="estado">
                    </div>
                  </div>
                </div>
                
                <label for="observaciones" class="control-label">Observaciones</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <textarea rows="5" cols="50" maxlength="200" placeholder="Observaciones del paso a Catálogo de Tejidos" class="form-control" name="observacionesCT" ></textarea>
                    </div>
                  </div>
                </div>
                <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>

            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar a Catálogo de Tejidos</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
    
 <t:modal idModal="modalReversarCV" titulo="Reversar paso a Colección Viva">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarCatalogoTejido" autocomplete="off" method="post" action="Serpiente">
                <input hidden="true" name="accion" value="reversar">
                <input hidden="true" name="id_serpiente_reversar" id="id_serpiente_reversar">
                <label for="especie" class="control-label">¿Está seguro que desea reversar el paso a Colección Viva?</label>
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Reversar</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
