<%-- 
    Document   : Index
    Created on : Jun 24, 2015, 10:49:21 AM
    Author     : Amed
--%>

<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Calendario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
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
              <h3><i class="fa fa-list-alt"></i>Calendario</h3>
              <div class="btn-group widget-header-toolbar">
                <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#ModalNuevoEvento">Agregar Evento</a>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <div id="data" data-eventos='${eventos}'></div>
              <div id='calendar'></div>

            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

    <t:modal idModal="ModalEliminarEvento" titulo="Eliminar Evento">
      <jsp:attribute name="form">
      <form id="form-eliminar-evento" method="post" action="Calendario">
        <h5>¿Está seguro que desea eliminar este evento? </h5>
        <input name="accion" value="eliminar" hidden> 
        <input name="id_evento_eliminar" id="id_evento_eliminar" type="text" value="kjhkh" hidden>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar </button>
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar </button>
          </div>
        </div>
      </form>
      </jsp:attribute>
    </t:modal>

      <t:modal idModal="ModalNuevoEvento" titulo="Nuevo Evento">

      <jsp:attribute name="form">
        <form name="modal" id="nuevo-evento-form" action="Calendario" method="POST">
          <input hidden="true" type="text" value="agregar" name="accion" id="accion"/>
          <div class="row">
            <div class="col-md-12">
              <input type="checkbox" name="allday" id="allday" value="true"/> 
              <label for="allday" class="control-label">Evento de todo el día</label> 
            </div>
            <div class="col-md-12">
              <label for="start_date" class="control-label">*Fecha y hora de inicio:</label>
            </div>
            <div class="col-md-12">
              <div class="col-md-6">
                <input  type="text" placeholder="Escoja la fecha" pattern="\d{4}-\d{1,2}-\d{1,2}" id="start_date" value="${cara.getFecha_eliminacionhembra_f_S()}" class="form-control sigiproDatePickerEspecial" name="start_date" data-date-format="yyyy-mm-dd" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')" style="width:220px;">
              </div>
              <div class="col-md-6">
                <input type="text" pattern="\d{1,2}:\d{1,2}" id="start_time" placeholder="Ej. 14:00" name="start_time" class="form-control" required oninvalid="setCustomValidity('Este campo es requerido, introduzca una hora en el formato 23:59 ')"
                       onchange="setCustomValidity('')"
                       style="width:100px;" />
              </div>
            </div>
            <div class="col-md-12">
              <label for="end_date" class="control-label">Fecha y hora final:</label>
            </div>
            <div class="col-md-12">
              <div class="col-md-6">
                <input  type="text" placeholder="Escoja la fecha" pattern="\d{4}-\d{1,2}-\d{1,2}" id="end_date" value="${cara.getFecha_eliminacionhembra_f_S()}" class="form-control sigiproDatePickerEspecial" name="end_date" data-date-format="yyyy-mm-dd" 
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')" style="width:220px;">
              </div>
              <div class="col-md-6">
                <input type="text" pattern="\d{1,2}:\d{1,2}" id="end_time" placeholder="Ej. 14:00" name="end_time" class="form-control"
                       style="width:100px;" />
              </div>
            </div>
            <div class="col-md-12">
              <label for="title" class="control-label">*Nombre del evento:</label>
            </div>
            <div class="col-md-12">
              <input type="text" id="title" name="title" class="form-control" required oninvalid="setCustomValidity('Este campo es requerido ')"
                     onchange="setCustomValidity('')"
                     maxlength="255" style="width:520px;"/>
            </div>
            <div class="col-md-12">
              <label for="description" class="control-label">Descripción</label>
              <textarea name="description" id="description"
                        rows="9" style="width:520px;"></textarea>
            </div>
            <div class="col-md-12">
              <input type="checkbox" name="shared" id="shared" value="true" />
              <label for="shared" class="control-label">Compartido</label>
            </div>
            <div class="col-md-12" hidden="true" id="divquien">
              <label for="whotoshare" class="control-label" >¿Compartido con quién?</label>
              <select id="whotoshare" class="select2" name="whotoshare"  hidden="true"
                      oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                <c:forEach items="${opcionesCompartir}" var="o">
                  <option value=${o} selected> ${o}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-12" hidden="true" id="divsecciones">
              <label for="shared" class="control-label" >Escoja las secciones con las que quiere compartir el evento</label>
              <select multiple id="secciones" class="select2 select2-multiple" name="secciones"  hidden="true"
                      oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                <c:forEach items="${secciones}" var="o">
                  <option value=${o.getId_seccion()} > ${o.getNombre_seccion()}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-12" hidden="true" id="divusuarios">            
              <label for="shared" class="control-label" >Escoja los usuarios con los que quiere compartir el evento</label>
              <select multiple tabindex="-1" id="usuarios" class="select2 select2-multiple" name="usuarios"  hidden="true"
                      oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                <c:forEach items="${usuarios}" var="o">
                  <option value=${o.getID()} > ${o.getNombreCompleto()}</option>
                </c:forEach>
              </select>
            </div>
            <div class="col-md-12" hidden="true" id="divroles">
              <label for="shared" class="control-label" >Escoja los roles con los que quiere compartir el evento</label>
              <select multiple id="roles" class="select2 select2-multiple" name="roles"  hidden="true"
                      oninvalid="setCustomValidity('Este campo es requerido')" style='background-color: #fff;' onchange="setCustomValidity('')">
                <c:forEach items="${roles}" var="o">
                  <option value=${o.getID()} > ${o.getNombreRol()}</option>
                </c:forEach>
              </select>
            </div>
          </div>

          <div class="row">
            <div class="form-group">
              <div class="modal-footer">
                <button type="button"  class="btn btn-primary" id="crear" onclick="validar()">Crear Evento</button>
                <button type="button" class="btn btn-danger" data-dismiss="modal" id="cancelar">Cancelar</button>
              </div>
            </div>
          </div>
        </form>
      </jsp:attribute>

    </t:modal>

  </jsp:attribute>
  <jsp:attribute name="scripts">
    <link rel='stylesheet' href="/SIGIPRO/recursos/css/fullcalendar.css" />
    <script src="/SIGIPRO/recursos/js/fullcalendar/moment.min.js"></script>
    <script src="/SIGIPRO/recursos/js/fullcalendar/fullcalendar.js"></script>
    <script src="/SIGIPRO/recursos/js/fullcalendar/es.js"></script>
    <script src="/SIGIPRO/recursos/js/sigipro/calendario.js"></script>
    <link rel='stylesheet' href="/SIGIPRO/recursos/css/jquery.timepicker.css" />
    <script src="/SIGIPRO/recursos/js/timepicker/jquery.timepicker.js"></script>
  </jsp:attribute>
</t:plantilla_general>
