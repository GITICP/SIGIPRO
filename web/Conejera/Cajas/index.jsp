<%-- 
    Document   : index
    Created on : Mar 26, 2015, 4:02:57 PM
    Author     : Amed
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
              <a href="/SIGIPRO/Conejera/Gruposhembras?">Grupo ${grupo.getIdentificador()}</a>
            </li>
            <li> 
              Espacios
            </li>
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
              <h3><i class="fa fa-archive"></i> Espacios del grupo ${grupo.getIdentificador()}</h3>
            </div>
            ${mensaje}
            <div class="widget-content">
              <div class="col-md-12 ">
                <c:forEach items="${listaCajas}" var="caja" begin="0" end="4">
                  <c:set var="flagllena" value="false" />  
                  <c:forEach items="${conejas}" var="coneja">
                    <c:if test="${coneja.getCaja().getId_caja() == caja.getId_caja()}">
                      <c:set var="flagllena" value="true" />
                    </c:if>
                  </c:forEach>  
                  <c:choose>
                    <c:when test="${flagllena}">
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:when>
                    <c:otherwise>
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:otherwise>
                  </c:choose>          
                </c:forEach>
              </div>
              <div class="col-md-12 ">
                <c:forEach items="${listaCajas}" var="caja" begin="5" end="9">
                  <c:set var="flagllena" value="false" />  
                  <c:forEach items="${conejas}" var="coneja">
                    <c:if test="${coneja.getCaja().getId_caja() == caja.getId_caja()}">
                      <c:set var="flagllena" value="true" />
                    </c:if>
                  </c:forEach>  
                  <c:choose>
                    <c:when test="${flagllena}">
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:when>
                    <c:otherwise>
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:otherwise>
                  </c:choose>          
                </c:forEach> 
              </div>
              <div class="col-md-12 ">
                <c:forEach items="${listaCajas}" var="caja" begin="10" end="14">
                  <c:set var="flagllena" value="false" />  
                  <c:forEach items="${conejas}" var="coneja">
                    <c:if test="${coneja.getCaja().getId_caja() == caja.getId_caja()}">
                      <c:set var="flagllena" value="true" />
                    </c:if>
                  </c:forEach>  
                  <c:choose>
                    <c:when test="${flagllena}">
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:when>
                    <c:otherwise>
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:otherwise>
                  </c:choose>          
                </c:forEach>
              </div>
              <div class="col-md-12 ">
                <c:forEach items="${listaCajas}" var="caja" begin="15" end="19">
                  <c:set var="flagllena" value="false" />  
                  <c:forEach items="${conejas}" var="coneja">
                    <c:if test="${coneja.getCaja().getId_caja() == caja.getId_caja()}">
                      <c:set var="flagllena" value="true" />
                    </c:if>
                  </c:forEach>  
                  <c:choose>
                    <c:when test="${flagllena}">
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-default btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Ocupado)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:when>
                    <c:otherwise>
                      <c:choose>
                        <c:when test="${caja.getNumero() < 10}">
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">

                            <div style="height:100%;width:100%">
                              Espacio #0${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:when>
                        <c:otherwise>
                          <a class="btn btn-warning btn-lg cajas" href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${caja.getId_caja()}">
                            <div style="height:100%;width:100%">
                              Espacio #${caja.getNumero()}
                              (Disponible)
                            </div>
                          </a>
                        </c:otherwise>
                      </c:choose>
                    </c:otherwise>
                  </c:choose>          
                </c:forEach> 
              </div>
            </div> 

          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->

    </jsp:attribute>

  </t:plantilla_general>

