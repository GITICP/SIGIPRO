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
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Serpentario/Extraccion?accion=agregar">Agregar Extraccion</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Numero de Extraccion</th>
                    <th>Especie</th>
                    <th>Es de Colección Viva</th>
                    <th>Fecha de Extraccion</th>
                    <th>Volumen Extraído (mL)</th>
                    <th>Usuario de Registro</th>
                    <th>Fecha de Registro</th>
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
                          <c:when test="${extraccion.isIngreso_cv()!=false}">
                             <td>Si</td> 
                          </c:when>
                          <c:otherwise>
                              <td>No</td>
                          </c:otherwise>
                      </c:choose>
                      
                      <td>${extraccion.getFecha_extraccionAsString()}</td>
                      <c:choose>
                          <c:when test="${extraccion.getVolumen_extraido() == 0}">
                             <td></td>
                            <td></td>
                            <td></td> 
                          </c:when>
                          <c:otherwise>
                              <td>${extraccion.getVolumen_extraido()}</td>
                            <td>${extraccion.getUsuario_registro().getNombre_usuario()}</td>
                            <td>${extraccion.getFecha_registroAsString()}</td>
                          </c:otherwise>
                      </c:choose>
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

<t:modal idModal="modalRegistrarExtraccion" titulo="Registrar Extraccion">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-registrar">
            <form class="form-horizontal" id="registrarExtraccion" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Registrar">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="observaciones" class="control-label">*Volumen Extraído (mL)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <input name="volumen_extraido" id="volumen_extraido" type="number" step="any" placeholder="Número de mL extraídos" class="form-control" value="" required
                             oninput="setCustomValidity(\'\')" 
                             oninvalid="setCustomValidity(\'Ingrese solo números\')">
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Extracción</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      
<t:modal idModal="modalRegistrarCentrifugado" titulo="Registrar Centrifugado">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-centrifugado">
            <form class="form-horizontal" id="registrarCentrifugado" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Centrifugado">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="observaciones" class="control-label">*Volumen Recuperado (mL)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <input name="volumen_recuperado" id="volumen_recuperado" type="number" step="any" placeholder="Número de mL recuperados" class="form-control" value="" required
                             oninput="setCustomValidity(\'\')" 
                             oninvalid="setCustomValidity(\'Ingrese solo números\')">
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Centrifugado</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      
<t:modal idModal="modalRegistrarLiofilizacionInicio" titulo="Registrar Inicio de Liofilización">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-liofilizacion-inicio">
            <form class="form-horizontal" id="registrarLiofilizacionInicio" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Liofilizacioninicio">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="label" class="control-label">¿Está seguro que desea registrar el inicio de la Liofilización?</label>
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Iniciar Liofilización</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>
      
      <t:modal idModal="modalRegistrarLiofilizacionFin" titulo="Registrar Fin de Liofilización">
    <jsp:attribute name="form">
        <div class="widget-content" id="class-liofilizacion-fin">
            <form class="form-horizontal" id="registrarLiofilizacionFin" autocomplete="off" method="post" action="Extraccion">
                <input hidden="true" name="accion" value="Liofilizacionfin">
                <input hidden="true" id='id_extraccion' name='id_extraccion' value="">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="peso_recuperado" class="control-label">*Peso recuperado (g)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <BR>
                      <input name="peso_recuperado" id="peso_recuperado" type="number" step="any" placeholder="Número de g recuperados" class="form-control" value="" required
                             oninput="setCustomValidity(\'\')" 
                             oninvalid="setCustomValidity(\'Ingrese solo números\')">
                    </div>
                  </div>
                </div>
            
        
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Finalizar Liofilización</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>