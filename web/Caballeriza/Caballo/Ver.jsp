<%-- 
    Document   : Ver
    Created on : 25-mar-2015, 18:22:58
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
            <li class="active"> ${serpiente.getId_serpiente()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-barcode"></i> ${serpiente.getId_serpiente()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEvento" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 45}">
                    <c:set var="contienePermisoEvento" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEvento}">
                    <c:choose>
                        <c:when test="${deceso==null}">
                            <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="registrar el deceso de la Serpiente" data-href="/SIGIPRO/Serpentario/Serpiente?accion=deceso&id_serpiente=${serpiente.getId_serpiente()}">Deceso</a>
                            <c:choose>
                            <c:when test="${coleccionViva == null}">
                                <a class="btn btn-info btn-sm boton-accion confirmable-form" data-form-id="form-pasoCV" data-texto-confirmacion="pasar la serpiente a Colección Viva">Paso a CV</a>
                            </c:when>
                            </c:choose>
                            <a class="btn btn-info btn-sm boton-accion evento-Modal" data-id='${serpiente.getId_serpiente()}' data-toggle="modal" data-target="#modalAgregarEvento">Evento</a>
                        </c:when>
                    </c:choose>
                
                </c:if>
                  <!--
                <c:set var="contienePermisoEliminar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 41}">
                    <c:set var="contienePermisoEliminar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEliminar}">
                  <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar la especie" data-href="/SIGIPRO/Serpentario/Especie?accion=eliminar&id_especie=${especie.getId_especie()}">Eliminar</a>
                </c:if>
                -->
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 44}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Serpentario/Serpiente?accion=editar&id_serpiente=${serpiente.getId_serpiente()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre de la Especie:</strong> <td>${serpiente.getEspecie().getGenero_especie()} </td></tr>
                <tr><td> <strong>Numero de Ingreso:</strong> <td>${serpiente.getId_serpiente()} </td></tr>
                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${serpiente.getFecha_ingresoAsString()} </td></tr>
                <tr><td> <strong>Localidad de Origen:</strong> <td>${serpiente.getLocalidad_origen()} </td></tr>
                <tr><td> <strong>Colectada por:</strong> <td>${serpiente.getColectada()} </td></tr>
                <tr><td> <strong>Recibida por:</strong> <td>${serpiente.getRecibida().getNombre_usuario()} </td></tr>
                <tr><td> <strong>Sexo:</strong> <td>${serpiente.getSexo()} </td></tr>
                <tr><td> <strong>Talla (Longitud Cabeza-Cloaca):</strong> <td>${serpiente.getTalla_cabeza()} Metros </td></tr>
                <tr><td> <strong>Talla (Longitud Cola):</strong> <td>${serpiente.getTalla_cola()} Metros</td></tr>
                <tr><td> <strong>Talla (Longitud Total):</strong> <td>${serpiente.getTalla_total()} Metros</td></tr>
                <tr><td> <strong>Peso:</strong> <td>${serpiente.getPeso()} Gramos</td></tr>
                <c:choose>
                    <c:when test="${coleccionViva != null}">
                        <tr><td><strong><div><br></div> </strong></td></tr>
                        <tr><td> <strong>Fecha de Paso a Colección Viva:</strong> <td>${coleccionViva.getFecha_eventoAsString()} </td></tr>
                        <tr><td> <strong>Responsable de Paso a Colección Viva:</strong> <td>${coleccionViva.getUsuario().getNombre_usuario()} </td></tr>
                        <tr><td><strong><div><br></div></strong></td></tr>
                    </c:when>
                </c:choose>
                <tr><td> <strong>Días en Cautiverio:</strong> <td>${serpiente.getDias_cautiverio()} </td></tr>
                <tr><td> <strong>Imagen (Sin Implementar):</strong> <td>${serpiente.getImagen()} </td></tr>
                <tr><td> <strong>Histórico de Eventos</strong> 
              </table>
            </div>
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Fecha del Evento</th>
                    <th>Evento</th>
                    <th>Usuario responsable</th>
                    <th>Extraccion</th>
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
                      <td>${eventos.getUsuario().getNombre_usuario()}</td>
                      <c:choose>
                          <c:when test="${eventos.getExtraccion()!= null}">
                              <td>${eventos.getExtraccion().getId_extraccion()}</td>
                          </c:when>
                          <c:otherwise>
                              <td></td>
                          </c:otherwise>
                      </c:choose>
                    </tr>
                  </c:forEach>
                </tbody>
              </table>
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