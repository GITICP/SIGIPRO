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
                    <c:when test="${coleccionViva == null}">
                        <a class="btn btn-info btn-sm boton-accion confirmable" data-texto-confirmacion="pasar la serpiente a Colección Viva" data-href="/SIGIPRO/Serpentario/Serpiente?accion=coleccionviva&id_serpiente=${serpiente.getId_serpiente()}">Paso a CV</a>
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
                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${serpiente.getFecha_ingreso()} </td></tr>
                <tr><td> <strong>Localidad de Origen:</strong> <td>${serpiente.getLocalidad_origen()} </td></tr>
                <tr><td> <strong>Colectada por:</strong> <td>${serpiente.getColectada()} </td></tr>
                <tr><td> <strong>Recibida por:</strong> <td>${serpiente.getRecibida()} </td></tr>
                <tr><td> <strong>Sexo:</strong> <td>${serpiente.getSexo()} </td></tr>
                <tr><td> <strong>Talla (Longitud Cabeza-Cloaca):</strong> <td>${serpiente.getTalla_cabeza()} Metros </td></tr>
                <tr><td> <strong>Talla (Longitud Cola):</strong> <td>${serpiente.getTalla_cola()} Metros</td></tr>
                <tr><td> <strong>Talla (Longitud Total):</strong> <td>${serpiente.getTalla_total()} Metros</td></tr>
                <tr><td> <strong>Peso:</strong> <td>${serpiente.getPeso()} Gramos</td></tr>
                <c:choose>
                    <c:when test="${coleccionViva != null}">
                        <tr><td> <strong>Fecha de Paso a Colección Viva:</strong> <td>${coleccionViva.getFecha_evento()} </td></tr>
                        <tr><td> <strong>Responsable de Paso a Colección Viva:</strong> <td>${coleccionViva.getUsuario().getNombre_usuario()} </td></tr>
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
                    <th>Evento</th>
                    <th>Fecha del Evento</th>
                    <th>Usuario responsable</th>
                    <th>Extraccion</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaEventos}" var="eventos">
                    <tr id ="${eventos.getId_evento()}">
                      <td>
                        <a data-toggle="modal" data-id="${eventos.getObservaciones()}" class='open-Modal' data-target="#modalVerEvento">
                        <div style="height:100%;width:100%">
                            ${eventos.getEvento()}
                          </div>
                        </a>
                      </td>
                      <td>${eventos.getFecha_evento()}</td>
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