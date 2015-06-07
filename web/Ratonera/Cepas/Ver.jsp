<%-- 
    Document   : Ver
    Created on : Feb 19, 2015, 6:29:37 PM
    Author     : Amed
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ratonera" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
    <form id="form-eliminar-piexcepa" method="post" action="PiexCepa">
      <input name="accion" value="Eliminar" hidden> 
      <input name="id_cepa" value="${cepa.getId_cepa()}" hidden>
    </form>
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Ratonera</li>
            <li> 
              <a href="/SIGIPRO/Ratonera/Cepas?">Cepas</a>
            </li>
            <li class="active"> ${cepa.getNombre()} </li>
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
              <h3><i class="sigipro-mouse-1"></i> Cepa  ${cepa.getNombre()} </h3>
              <div class="btn-group widget-header-toolbar">
                <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Ratonera/Cepas?accion=editar&id_cepa=${cepa.getId_cepa()}">Editar</a>
                <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar el Cepa" data-href="/SIGIPRO/Ratonera/Cepas?accion=eliminar&id_cepa=${cepa.getId_cepa()}">Eliminar</a>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Nombre de la Cepa:</strong> <td>${cepa.getNombre()} </td></tr>
                <tr><td> <strong>Descripción:</strong> <td>${cepa.getDescripcion()} </td></tr>
              </table>
              <br>
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3>Pie de Cría de la Cepa</h3>
                  <div class="btn-group widget-header-toolbar">
                    <c:choose>
                      <c:when test="${empty pieasociado.getCepa()}">
                        <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAsociar">Asociar Pie</a>
                      </c:when>
                      <c:otherwise>
                        <a class="btn btn-warning btn-sm boton-warning confirmable-form boton-accion" data-texto-confirmacion="retirar el pie" data-form-id="form-eliminar-piexcepa">Retirar Pie</a>
                      </c:otherwise>
                    </c:choose>
                  </div>     
                </div>
                <div class="widget-content">
                  <table>
                    <tr><td> <strong>Pie de Cría:</strong> <td>${pieasociado.getPie().getCodigo()} </td></tr>
                    <tr><td> <strong>Fecha de Inicio:</strong> <td>${pieasociado.getFecha_inicio_S()} </td></tr>
                    <tr><td> <strong>Fecha Estimada de Retiro</strong> <td>${pieasociado.getFecha_estimada_retiro_S()} </td></tr>
                  </table>
                </div>
              </div>
            </div>
            <!-- END WIDGET TICKET TABLE -->
          </div>
          <!-- /main-content -->
        </div>
        <!-- /main -->
      </div>

      <t:modal idModal="modalAsociar" titulo="Asociar Pie de Cría">

        <jsp:attribute name="form">
          <h5> Por favor complete la información necesaria: </h5>
          <form class="form-horizontal" id="form-asociar" data-show-auth="${show_modal_auth}" method="post" action="PiexCepa">
            <input hidden="true" name="id_cepa" id="id_cepa" value="${cepa.getId_cepa()}">
            <input hidden="true" name="accion" value="Agregar" id="accion">

            <label for="id_pie" class="control-label">*Pie</label>
              <div class="form-group">
                <div class="col-md-12">
                  <div class="input-group">
                    <select id="id_pie" class="select2" name="id_pie" required
                            oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                      <c:forEach items="${pies}" var="pie">
                        <option value=${pie.getId_pie()} selected> ${pie.getCodigo()}</option> 
                      </c:forEach>
                    </select>
                  </div>
                </div>
              </div>
              <label for="fecha_inicio" class="control-label">*Fecha de Inicio</label>
              <div class="form-group">
                <div class="col-md-12">
                  <div class="input-group">
                    <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_inicio"  class="form-control sigiproDatePickerEspecial" name="fecha_inicio" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')">      
                  </div>
                </div>
              </div>


              <label for="fecha_retiro" class="control-label">*Fecha Estimada de Retiro</label>
              <div class="form-group">
                <div class="col-md-12">
                  <div class="input-group">
                    <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_retiro"  class="form-control sigiproDatePickerEspecial" name="fecha_retiro" data-date-format="dd/mm/yyyy" required
                            oninvalid="setCustomValidity('Este campo es requerido ')"
                            onchange="setCustomValidity('')">      
                  </div>
                </div>
              </div>
            
            <div class="col-md-12">                           
              <p id='mensajeFechas1' style='color:red;'><p>
            </div>
              <hr>
            <div class="form-group">
              <div class="modal-footer">
                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                <button type="button" class="btn btn-primary" onclick="confirmar()"><i class="fa fa-check-circle"></i> Aceptar</button>
              </div>
            </div>
          </form>


        </jsp:attribute>

      </t:modal>
    </jsp:attribute>
    <jsp:attribute name="scripts">
      <script src="/SIGIPRO/recursos/js/sigipro/cepas.js"></script>
    </jsp:attribute>
  </t:plantilla_general>

