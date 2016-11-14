<%-- 
    Document   : Agregar
    Created on : Abr 3, 2015, 1:43:27 PM
    Author     : Boga
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Calendario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12">
          <ul class="breadcrumb">
            <li> 
              <a href="/SIGIPRO/Calendario/Calendario">Calendario</a>
            </li>
            <li class="active"> Editar Evento</li>

          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-sign-out"></i> Editar Evento</h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <form id="form-editar-evento" class="form-horizontal" autocomplete="off" method="post" action="Calendario">
                <div class="row">
                  <div class="col-md-6">
                    <input hidden="true" id="id_evento" name="id_evento" value="${evento.getId()}">
                    <input hidden="true" name="accion" value="${accion}">
                    <h3 align="center">Información del evento</h3>
                    <hr>
                    <div class="col-md-12">
                      <c:choose>
                        <c:when test="${evento.getAllDay()}">
                          <input type="checkbox" name="allday" id="allday" value="true" checked/> 
                        </c:when>    
                        <c:otherwise>
                          <input type="checkbox" name="allday" id="allday" value="true"/> 
                        </c:otherwise>
                      </c:choose>                      
                      <label for="allday" class="control-label">Evento de todo el día</label> 
                    </div>
                    <div class="col-md-12">
                      <label for="start_date" class="control-label">*Fecha y hora de inicio:</label>
                    </div>
                    <div>
                      <div class="col-md-4">
                        <input  type="text" placeholder="Escoja la fecha" pattern="\d{4}-\d{1,2}-\d{1,2}" id="start_date" value="${evento.getStart_S()}" class="form-control sigiproDatePickerEspecial" name="start_date" data-date-format="yyyy-mm-dd" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')" style="width:220px;">
                      </div>
                      <div class="col-md-4">
                        <c:choose>
                        <c:when test="${evento.getAllDay()}">
                          <input disabled="true" type="text" pattern="\d{1,2}:\d{1,2}" id="start_time" placeholder="Ej. 14:00" name="start_time" class="form-control" required oninvalid="setCustomValidity('Este campo es requerido, introduzca una hora en el formato 23:59 ')"
                               onchange="setCustomValidity('')"
                               style="width:100px;" />
                        </c:when>    
                        <c:otherwise>
                          <input type="text" pattern="\d{1,2}:\d{1,2}" id="start_time" placeholder="Ej. 14:00" name="start_time" value="${evento.getHora()}" class="form-control" required oninvalid="setCustomValidity('Este campo es requerido, introduzca una hora en el formato 23:59 ')"
                               onchange="setCustomValidity('')"
                               style="width:100px;" />
                        </c:otherwise>
                      </c:choose>

                      </div>
                    </div>
                    <div class="col-md-12">
                      <label for="end_date" class="control-label">Fecha y hora final:</label>
                    </div>
                    <div>
                      <div class="col-md-4">
                        <c:choose>
                        <c:when test="${evento.getAllDay()}">
                          <input  disabled="true" type="text" placeholder="Escoja la fecha" pattern="\d{4}-\d{1,2}-\d{1,2}" id="end_date" class="form-control sigiproDatePickerEspecial" name="end_date" data-date-format="yyyy-mm-dd" 
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')" style="width:220px;">
                        </c:when>    
                        <c:otherwise>
                          <input  type="text" placeholder="Escoja la fecha" pattern="\d{4}-\d{1,2}-\d{1,2}" id="end_date" value="${evento.getEnd_S()}" class="form-control sigiproDatePickerEspecial" name="end_date" data-date-format="yyyy-mm-dd" 
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')" style="width:220px;">
                        </c:otherwise>
                      </c:choose>
                        
                      </div>
                      <div class="col-md-4">
                        <c:choose>
                        <c:when test="${evento.getAllDay()}">
                          <input type="text" disabled="true" pattern="\d{1,2}:\d{1,2}" id="end_time" placeholder="Ej. 14:00" name="end_time" class="form-control"
                               style="width:100px;" />
                        </c:when>    
                        <c:otherwise>
                          <input type="text" pattern="\d{1,2}:\d{1,2}" id="end_time" placeholder="Ej. 14:00" value="${evento.getHoraFin()}" name="end_time" class="form-control"
                               style="width:100px;" />
                        </c:otherwise>
                      </c:choose>
                        
                      </div>
                    </div>
                    <div class="col-md-12">
                      <label for="title" class="control-label">*Nombre del evento:</label>
                    </div>
                    <div class="col-md-12">
                      <input type="text" id="title" name="title" class="form-control" value="${evento.getTitle()}"required oninvalid="setCustomValidity('Este campo es requerido ')"
                             onchange="setCustomValidity('')"
                             maxlength="255" style="width:520px;"/>
                    </div>
                    <div class="col-md-12">
                      <label for="description" class="control-label">Descripción</label>
                      <div></div>
                      <textarea name="description" id="description"
                                rows="9" style="width:520px;">${evento.getDescription()}</textarea>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <h3 align="center">Compartido con:</h3>
                    <hr>
                    <label class="control-label">Usuarios: </label>
                    <c:forEach items="${shares[0]}" var="usuario">  
                      <div class="label label-info">${usuario}</div>
                    </c:forEach>
                    <hr>
                    <label class="control-label">Secciones:</label>
                    <c:forEach items="${shares[1]}" var="usuario">  
                      <div class="label label-warning">${usuario}</div>
                    </c:forEach>
                    <hr>
                    <label class="control-label">Roles:</label>
                    <c:forEach items="${shares[2]}" var="usuario">  
                      <div class="label label-success">${usuario}</div>
                    </c:forEach>
                    <hr>
                  </div>
                </div>
                <div class="col-md-12">
                  <p>
                    Los campos marcados con * son requeridos.
                  </p>  
                  <div class="row">
                    <div class="form-group">
                      <div class="modal-footer">
                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                      </div>
                    </div>
                  </div>
                </div>

              </form>


            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

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
