<%-- 
    Document   : Ver
    Created on : Mar 26, 2015, 1:33:54 PM
    Author     : ld.conejo
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
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Extraccion?">Extracción</a>
            </li>
            <li class="active"> ${extraccion.getNumero_extraccion()} </li>
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
              <h3><i class="fa fa-tint"></i> ${extraccion.getNumero_extraccion()} </h3>
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
                      <h3><i class="fa fa-bug"></i> Serpientes Asociadas</h3>
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
                      <h3><i class="fa fa-users"></i> Usuarios Asociados</h3>
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
                              <td>${usuario.getNombreCompleto()}</td>
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
                      <tr><td> <strong>Usuario de Registro:</strong> <td>${extraccion.getUsuario_registro().getNombreCompleto()} </td></tr>
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
                      <tr><td> <strong>Usuario de Centrifugado:</strong> <td>${centrifugado.getUsuario().getNombreCompleto()} </td></tr>
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
                      <tr><td> <strong>Usuario de Inicio:</strong></td> <td>${liofilizacion.getUsuario_inicio().getNombreCompleto()} </td></tr>
                      <tr><td> <strong>Fecha de Inicio:</strong> <td>${liofilizacion.getFecha_inicioAsString()} </td></tr>
              <c:if test="${liofilizacion.getFecha_fin() != null}" >
                      <tr><td> <strong>Peso recuperado (g):</strong> <td>${liofilizacion.getPeso_recuperado()} </td></tr>
                      <tr><td> <strong>Usuario de Fin:</strong></td> <td>${liofilizacion.getUsuario_fin().getNombreCompleto()} </td></tr>
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
                      <input name="volumen_recuperado" id="volumen_recuperado" type="number" max="${extraccion.getVolumen_extraido()}" step="any" placeholder="Número de mL recuperados" class="form-control" value="" required
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