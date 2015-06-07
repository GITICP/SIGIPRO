<%-- 
    Document   : index
    Created on : May 21, 2015, 7:36:10 PM
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
              <a href="/SIGIPRO/Serpentario/Restriccion?">Restricciones de Solicitudes</a>
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
              <h3><i class="fa fa-ban"></i> Restricciones de Solicitud de Veneno </h3>

              <c:set var="contienePermiso" value="false" />
              <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                <c:if test="${permiso == 1 || permiso == 360}">
                  <c:set var="contienePermiso" value="true" />
                </c:if>
              </c:forEach>
              <c:if test="${contienePermiso}">
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarRestriccion">Agregar Restricciones</a>
                </div>
              </c:if>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Usuario / Especie</th>
                    <th>Cantidad Restringida Año Fiscal ${ano} (mg)</th>
                    <th>Cantidad Consumida Año Fiscal ${ano} (mg)</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaRestricciones}" var="restriccion">

                    <tr id ="${restriccion.getId_restriccion()}">
                      <td>
                        <a href="/SIGIPRO/Serpentario/Restriccion?accion=ver&id_restriccion=${restriccion.getId_restriccion()}">
                          <div style="height:100%;width:100%">
                            ${restriccion.getUsuario().getNombre_completo()} / ${restriccion.getVeneno().getEspecie().getGenero_especie()}
                          </div>
                        </a>
                      </td>
                      <td>${restriccion.getCantidad_anual()}</td>
                      <td>${restriccion.getCantidad_consumida()}</td>
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

      
 <t:modal idModal="modalAgregarRestriccion" titulo="Agregar Restricción de Solicitudes">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarRestriccion" autocomplete="off" method="get" action="Restriccion">
                <input hidden="true" name="accion" value="Agregar">
                <strong><div id="numero_extraccion" class="control-label"></div></strong>
                <label for="observaciones" class="control-label">*Primero, elija el Tipo de Veneno que se restringirá a los Usuarios</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                        <br>
                      <select id="seleccionEspecie" class="select2" name="id_veneno"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                                    <option value=''></option>
                                    <c:forEach items="${venenos}" var="veneno">
                                        <option value=${veneno.getId_veneno()}>${veneno.getEspecie().getGenero_especie()}</option>
                                    </c:forEach>
                            </select>
                    </div>
                  </div>
                </div>
            
        <div class="form-group">
            <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Ir a Agregar Restriccion</button>            </div>
        </div>
        </form>
        </div>

    </jsp:attribute>

</t:modal>