<%-- 
    Document   : index
    Created on : Mar 16, 2015, 6:11:43 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
              <a href="/SIGIPRO/Serpentario/Extraccion?">Extracciones</a>
            </li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-tint"></i> Extracciones </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 320}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Serpentario/Extraccion?accion=agregar">Agregar Extracción</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Número de Extracción</th>
                    <th>Especie</th>
                    <th>Volumen Extraído (mL)</th>
                    <th>Volumen Recuperado (mL)</th>
                    <th>Peso Liofilizado (G)</th>
                    <th>Acción</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaExtracciones}" var="extraccion">

                    <tr id ="${extraccion.getId_extraccion()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/Extraccion?accion=ver&id_extraccion=${extraccion.getId_extraccion()}">
                          <div style="height:100%;width:100%">
                            ${extraccion.getNumero_extraccion()}
                          </div>
                        </a>
                      </td>
                      <td>${extraccion.getEspecie().getGenero_especie()}</td>

                      <c:choose>
                          <c:when test="${extraccion.isIsRegistro()}">
                             <td>${extraccion.getVolumen_extraido()}</td>
                          </c:when>
                          <c:otherwise>
                            <td></td>
                          </c:otherwise>
                      </c:choose>
                      <c:choose>
                          <c:when test="${extraccion.isIsCentrifugado()}">
                             <td>${extraccion.getCentrifugado().getVolumen_recuperado()}</td>
                          </c:when>
                          <c:otherwise>
                            <td></td>
                          </c:otherwise>
                      </c:choose>
                      <c:choose>
                          <c:when test="${extraccion.isIsLiofilizacionFin()}">
                             <td>${extraccion.getLiofilizacion().getPeso_recuperado()}</td>
                          </c:when>
                          <c:otherwise>
                            <td></td>
                          </c:otherwise>
                      </c:choose>
                        
                            <c:set var="contienePermisoAgregar" value="false" />
                            <c:set var="contienePermisoRegistrar" value="false" />
                            <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                              <c:if test="${permiso == 1 || permiso==320 || permiso == 321}">
                                <c:set var="contienePermisoAgregar" value="true" />
                              </c:if>
                              <c:if test="${permiso == 1 || permiso==322}">
                                <c:set var="contienePermisoRegistrar" value="true" />
                              </c:if>
                            </c:forEach>
                            
                      <c:if test="${contienePermisoAgregar || contienePermisoRegistrar}">
                      <td align="center">
                          <c:choose>
                              <c:when test="${!extraccion.isIsSerpiente()}">
                                    <a class="btn btn-warning btn-sm boton-accion " href="/SIGIPRO/Serpentario/Extraccion?accion=editarserpientes&id_extraccion=${extraccion.getId_extraccion()}">1- Agregar Serpientes</a></td>
                              </c:when>
                              <c:otherwise>
                                  <c:if test="${contienePermisoRegistrar}">
                                  <c:choose>
                                    <c:when test="${!extraccion.isIsRegistro()}">
                                        <a class="btn btn-warning btn-sm boton-accion registrar-Modal" data-id='${extraccion.getId_extraccion()}/-/${extraccion.getNumero_extraccion()}' data-toggle="modal" data-target="#modalRegistrarExtraccion">2- Registrar Vol. Extraído</a>
                                     </c:when>
                                     <c:otherwise>
                                         <c:choose>
                                             <c:when test="${!extraccion.isIsCentrifugado()}">
                                                    <a class="btn btn-warning btn-sm boton-accion centrifugado-Modal" data-id='${extraccion.getId_extraccion()}/-/${extraccion.getNumero_extraccion()}/-/${extraccion.getVolumen_extraido()}' data-toggle="modal" data-target="#modalRegistrarCentrifugado">3- Registrar Vol. Recuperado</a>
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
                                                            <c:otherwise>
                                                                <a class="btn btn-warning btn-sm boton-accion"disabled='true'>Extracción Finalizada</a>
                                                            </c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                         </c:choose>
                                     </c:otherwise>
                                  </c:choose>
                                  </c:if>
                              </c:otherwise>
                              
                          </c:choose>
                </c:if>    
                </tr>

                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->

    </jsp:attribute>

  </t:plantilla_general>

<jsp:include page="Modales.jsp" />