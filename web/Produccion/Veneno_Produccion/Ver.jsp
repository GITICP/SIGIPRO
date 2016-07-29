<%-- 
    Document   : Ver
    Created on : Mar 28, 2015, 11:26:24 PM
    Author     : ld.conejo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Produccion" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <form id="form-eliminar-veneno_produccion" method="post" action="Veneno_Produccion">
        <input name="accion" value="Eliminar" hidden> 
        <input name="id_veneno" value="${veneno.getId_veneno()}" hidden>
    </form>
    
        <t:modal idModal="modalConsumirVeneno" titulo="Consumo Directo de Veneno de Producción">
            <jsp:attribute name="form">
                <div class="widget-content">
                    <form class="form-horizontal" id="consumirVeneno" autocomplete="off" method="post" action="Veneno_Produccion">
                        <input name="accion" value="Consumir" hidden> 
                        <input name="id_veneno" value="${veneno.getId_veneno()}" hidden>
                        <input hidden type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_consumo" class="form-control sigiproDatePickerEspecial" name="fecha_consumo" data-date-format="dd/mm/yyyy"
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')"> 
                        <script>
                            var today = new Date();
                            var dd = today.getDate();
                            var mm = today.getMonth()+1; //January is 0!

                            var yyyy = today.getFullYear();
                            if(dd<10){
                                dd='0'+dd
                            } 
                            if(mm<10){
                                mm='0'+mm
                            } 
                            var today = dd+'/'+mm+'/'+yyyy;
                            document.getElementById("fecha_consumo").value = today;
                            document.getElementById("fecha_consumo").style.display = "none";
                        </script>
                        <label for="observaciones" class="control-label">*Ingrese la cantidad (mg) a consumir del veneno</label>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <br>
                                    <input id="cantidad_consumir" name="cantidad_consumir" type="number" placeholder="Máximo ${veneno.getCantidad()}" min="0" class="form-control" max="${veneno.getCantidad()}" required
                                           oninvalid="setCustomValidity('Debe ingresar un valor válido. ')"
                                           oninput="setCustomValidity('')"> 
                                </div>
                            </div>
                        </div>
            
                        <div class="form-group">
                            <div class="modal-footer">
                                <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Consumir</button>            </div>
                        </div>
                    </form>
                </div>
                
            </jsp:attribute>
            
        </t:modal>
    
    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Producción</li>
            <li> 
              <a href="/SIGIPRO/Produccion/Veneno_Produccion?">Venenos de Producción</a>
            </li>
            <li class="active">Veneno de Producción de ${veneno.getVeneno()}</li>
          </ul>
        </div>
      </div>
      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-flask"></i> Veneno de Producción de ${veneno.getVeneno()}  </h3>
              <div class="btn-group widget-header-toolbar">
                <c:set var="contienePermisoEditarYBorrar" value="false" />
                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                  <c:if test="${permiso == 1 || permiso == 605}">
                    <c:set var="contienePermisoEditarYBorrar" value="true" />
                  </c:if>
                </c:forEach>
                <c:if test="${contienePermisoEditarYBorrar}">
                  <c:if test="${veneno.getCantidad() >= 1}">
                      <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalConsumirVeneno">Consumo Directo</a>
                  </c:if>
                  <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Produccion/Veneno_Produccion?accion=editar&id_veneno=${veneno.getId_veneno()}">Editar</a>
                  <a class="btn btn-danger btn-sm boton-accion confirmable-form" data-texto-confirmacion="eliminar este veneno de producción" data-form-id="form-eliminar-veneno_produccion">Eliminar</a>
                </c:if>
              </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table>
                <tr><td> <strong>Veneno:</strong></td> <td>${veneno.getVeneno()} </td></tr>
                <tr><td> <strong>Fecha de Ingreso:</strong> <td>${veneno.getFecha_ingreso_S()} </td></tr>
                <tr><td> <strong>Cantidad (mg):</strong> <td>${veneno.getCantidad()} </td></tr>
                <tr><td> <strong>Observaciones:</strong> <td>${veneno.getObservaciones()} </td></tr>
              </table>
              <br>
              
              <div class="widget widget-table">
                <div class="widget-header">
                  <h3><i class="fa fa-check"></i> Veneno del Serpentario Asociado (Lote)</h3>
                </div>
                <div class="widget-content">
                  <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                    <thead>
                      <tr>
                        <th>Lote</th>
                        <th>Especie del Veneno</th>
                      </tr>
                    </thead>
                    <tbody>
                      
                        <tr id="${veneno.getVeneno_serpentario().getId_lote()}">
                          <td>${veneno.getVeneno_serpentario().getNumero_lote()}</td>
                          <td>${veneno.getVeneno_serpentario().getEspecie().getGenero_especie()}</td>
                        </tr>
                      
                    </tbody>
                  </table>
                </div>
              </div>
              
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
