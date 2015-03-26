<%-- 
    Document   : Ver
    Created on : 25-mar-2015, 18:22:58
    Author     : Walter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Caballeriza</li>
            <li> 
              <a href="/SIGIPRO/Caballeriza/Caballo?">Caballos</a>
            </li>
            <li class="active"> ${caballo.getNumero_microchip()} </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-barcode"></i> ${caballo.getNumero_microchip()} </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEvento" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 45}">
                    <c:set var="contienePermisoEvento" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEvento}">
                   <a class="btn btn-info btn-sm boton-accion evento-Modal" data-id='${caballo.getId_caballo()}' data-toggle="modal" data-target="#modalAgregarEvento">Evento clinico</a>  
                </c:if>
                  <!--
                -->
                <c:set var="contienePermisoEditar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 44}">
                    <c:set var="contienePermisoEditar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditar}">
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=editar&id_caballo=${caballo.getId_caballo()}">Editar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre:</strong> <td>${caballo.getNombre()} </td></tr>
                <tr><td> <strong>Numero de Microchip:</strong> <td>${caballo.getNumero_microchip()} </td></tr>
                <tr><td> <strong>Grupo del Caballo:</strong> <td>${caballo.getGrupo_de_caballos().getNombre()} </td></tr>                
                <tr><td> <strong>Fecha de Nacimiento:</strong> <td>${caballo.getFecha_nacimientoAsString()} </td></tr>
                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${caballo.getFecha_ingresoAsString()} </td></tr>
                <tr><td> <strong>Sexo:</strong> <td>${caballo.getSexo()} </td></tr>
                <tr><td> <strong>Color:</strong> <td>${caballo.getColor()} </td></tr>
                <tr><td> <strong>Otras Señas:</strong> <td>${caballo.getOtras_sennas()}</td></tr>
                <tr><td> <strong>Estado:</strong> <td>${caballo.getEstado()}</td></tr>
                <tr><td> <strong>Imagen (Sin Implementar):</strong> <td>${caballo.getFotografia()} </td></tr>
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