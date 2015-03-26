<%-- 
    Document   : Ver
    Created on : Mar 26, 2015, 1:33:54 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Bodegas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Extraccion?">Extracción</a>
            </li>
            <li class="active"> ${extraccion.getId_extraccion()} </li>
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
              <h3><i class="fa fa-barcode"></i> ${extraccion.getNumero_extraccion()} </h3>
              <div class="btn-group widget-header-toolbar">
              <c:set var="contienePermiso" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                              <c:if test="${permiso == 1 || permiso == 321}">
                                <c:set var="contienePermiso" value="true" />
                              </c:if>
                            </c:forEach>
                      <c:if test="${contienePermiso}">
                      <td align="center">
                          <c:choose>
                              <c:when test="${!extraccion.isIsSerpiente()}">
                                    <a class="btn btn-warning btn-sm boton-accion " href="/SIGIPRO/Serpentario/Extraccion?accion=editarserpientes&id_extraccion=${extraccion.getId_extraccion()}">1- Agregar Serpientes</a></td>
                              </c:when>
                              <c:otherwise>
                                  <c:choose>
                                    <c:when test="${!extraccion.isIsRegistro()}">
                                        <a class="btn btn-warning btn-sm boton-accion registrar-Modal" data-id='${extraccion.getId_extraccion()}/-/${extraccion.getNumero_extraccion()}' data-toggle="modal" data-target="#modalRegistrarExtraccion">2- Registrar Extracción</a>
                                     </c:when>
                                     <c:otherwise>
                                         <c:choose>
                                             <c:when test="${!extraccion.isIsCentrifugado()}">
                                                    <a class="btn btn-warning btn-sm boton-accion centrifugado-Modal" data-id='${extraccion.getId_extraccion()}/-/${extraccion.getNumero_extraccion()}' data-toggle="modal" data-target="#modalRegistrarCentrifugado">3- Registrar Centrifugado</a>
                                             </c:when>
                                             <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${!extraccion.isIsLiofilizacionInicio()}">
                                                           <a class="btn btn-warning btn-sm boton-accion liofilizacion-inicio-Modal" data-id='${extraccion.getId_extraccion()}/-/${extraccion.getNumero_extraccion()}' data-toggle="modal" data-target="#modalRegistrarLiofilizacionInicio">4- Iniciar Liofilizacion</a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${!extraccion.isIsLiofilizacionFin()}">
                                                                   <a class="btn btn-warning btn-sm boton-accion liofilizacion-fin-Modal" data-id='${extraccion.getId_extraccion()}/-/${extraccion.getNumero_extraccion()}' data-toggle="modal" data-target="#modalRegistrarLiofilizacionFin">5- Fin Liofilizacion</a>
                                                            </c:when>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                         </c:choose>
                                     </c:otherwise>
                                  </c:choose>
                              </c:otherwise>
                              
                          </c:choose>
                </c:if>    
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Número de Extracción:</strong></td> <td>${extraccion.getNumero_extraccion()} </td></tr>
                <tr><td> <strong>Especie:</strong> <td>${extraccion.getEspecie().getGenero_especie()} </td></tr>
                <tr><td> <strong>Ejemplares:</strong> <td>${ejemplares} </td></tr>
                <tr><td> <strong>Fecha de la Extracción:</strong> <td>${extraccion.getFecha_extraccionAsString()} </td></tr>
              </table>
              <br>
              <div class="row">
                <div class="col-md-6">
                  <div class="widget widget-table">
                    <div class="widget-header">
                      <h3><i class="fa fa-map-marker"></i> Serpientes Asociadas</h3>
                    </div>
                    <div class="widget-content">
                      <table id="datatable-column-filter-ubicaciones-formulario" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                          <tr>
                            <th>Serpiente</th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${listaSerpientes}" var="serpiente">
                            <tr>
                              <td><a href="/SIGIPRO/Serpentario/Serpiente?accion=ver&id_serpiente=${serpiente.getId_serpiente()}">${serpiente.getId_serpiente()}</a></td>
                            </tr>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="widget widget-table">
                    <div class="widget-header">
                      <h3><i class="fa fa-truck"></i> Usuarios Asociados</h3>
                    </div>
                    <div class="widget-content">
                      <table id="datatable-column-filter-productos-externos" class="table table-sorting table-striped table-hover datatable">
                        <thead>
                          <tr>
                            <th>Usuario</th>
                          </tr>
                        </thead>
                        <tbody>
                          <c:forEach items="${listaUsuarios}" var="usuario">
                            <tr>
                              <td>${usuario.getNombre_usuario()}</td>
                            </tr>
                          </c:forEach>
                        </tbody>
                      </table>
                    </div>
                  </div>
                </div>
              </div>
              <div class="row">
              <c:if test="${extraccion.getVolumen_extraido() != 0.0}" >
                  <div class="col-md-4">
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-flask"></i> Registro</h3>
                  </div>
                  <div class="widget-content">
                    <table>
                      <tr><td> <strong>Volumen Extraído (mL):</strong></td> <td>${extraccion.getVolumen_extraido()} </td></tr>
                      <tr><td> <strong>Usuario de Registro:</strong> <td>${extraccion.getUsuario_registro().getNombre_usuario()} </td></tr>
                      <tr><td> <strong>Fecha Volumen extraído:</strong> <td>${extraccion.getFecha_registroAsString()} </td></tr>
                    </table>
                  </div>
                </div>
                    </div>
              </c:if>
              
              <c:if test="${centrifugado.getVolumen_recuperado() != 0.0}" >
                  <div class="col-md-4">
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-flask"></i> Centrifugado</h3>
                  </div>
                  <div class="widget-content">
                    <table>
                      <tr><td> <strong>Volumen Recuperado (mL):</strong></td> <td>${centrifugado.getVolumen_recuperado()} </td></tr>
                      <tr><td> <strong>Usuario de Centrifugado:</strong> <td>${centrifugado.getUsuario().getNombre_usuario()} </td></tr>
                      <tr><td> <strong>Fecha Volumen Recuperado:</strong> <td>${centrifugado.getFecha_volumen_recuperadoAsString()} </td></tr>
                    </table>
                  </div>
                </div>
                    </div>
              </c:if>
              
              <c:if test="${liofilizacion.getFecha_inicio() != null}" >
                  <div class="col-md-4">
                <div class="widget widget-table">
                  <div class="widget-header">
                    <h3><i class="fa fa-flask"></i> Liofilización</h3>
                  </div>
                  <div class="widget-content">
                    <table>
                      <tr><td> <strong>Usuario de Inicio:</strong></td> <td>${liofilizacion.getUsuario_inicio().getNombre_usuario()} </td></tr>
                      <tr><td> <strong>Fecha de Inicio:</strong> <td>${liofilizacion.getFecha_inicioAsString()} </td></tr>
              <c:if test="${liofilizacion.getFecha_fin() != null}" >
                      <tr><td> <strong>Peso recuperado (g):</strong> <td>${liofilizacion.getPeso_recuperado()} </td></tr>
                      <tr><td> <strong>Usuario de Fin:</strong></td> <td>${liofilizacion.getUsuario_fin().getNombre_usuario()} </td></tr>
                      <tr><td> <strong>Fecha de Fin:</strong> <td>${liofilizacion.getFecha_finAsString()} </td></tr>
               </c:if>       
                    </table>
                  </div>
                </div>
                    </div>
              </c:if>
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
