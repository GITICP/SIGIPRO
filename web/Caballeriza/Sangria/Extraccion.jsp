<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Caballeriza</li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Sangria?">Sangrías</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Sangria?accion=ver&id_sangria=${sangria.getId_sangria()}">Sangría ${sangria.getId_sangria_especial()}</a>
                        </li>
                        <li class="active"> ${(editar == true) ? "Editar" : "Registrar"} Extracción</li>

                    </ul>
                </div>
            </div>

            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-tint"></i> ${(editar == true) ? "Editar" : "Registrar"} Extracción del día ${dia} para la sangría ${sangria.getId_sangria_especial()}</h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">

                            <form id="form-extraccion-sangria" class="form-horizontal" autocomplete="off" method="post" action="Sangria">
                                <div class="col-md-12">
                                    <input hidden="true" name="id_sangria" value="${sangria.getId_sangria()}">
                                    <input hidden="true" name="dia" value="${dia}">
                                    <input hidden="true" name="accion" value="Extraccion">
                                    <input id="input-volver" hidden="true" name="volver" value="false">
                                    <label for="fecha_extraccion" class="control-label">*Fecha de Extracción</label>
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <div class="input-group">
                                                <input type="text" value="${(fecha_sangria != null) ? fecha_sangria : helper_fechas.getFecha_hoyAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_extraccion" data-date-format="dd/mm/yyyy" required
                                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                                       onchange="setCustomValidity('')">
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                </div>
                                <div class="widget widget-table">
                                    <div class="widget-header">
                                        <h3><i class="fa fa-check"></i> Información de la Sangría </h3>                                        
                                    </div>
                                    <div class="widget-content">
                                        <table id="tabla-sangrias-caballos" class="table table-sorting table-striped table-hover datatable">
                                            <thead>
                                                <tr>
                                                    <th rowspan="2">Nombre y Número de Caballo</th>
                                                    <th rowspan="2" style="text-align:center">Participó</th>
                                                    <th colspan="3">Día ${dia}</th>
                                                </tr>
                                                <tr>
                                                    <th>Sangre</th>
                                                    <th>Plasma</th>
                                                    <th>Observaciones</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${sangria.getSangrias_caballos()}" var="sangria_caballo">
                                                    <c:set var="deshabilitado" value="false"></c:set>
                                                    <tr id="${caballo.getId_caballo()}">
                                                        <td>${sangria_caballo.getCaballo().getNombre()} (${sangria_caballo.getCaballo().getNumero()})</td>
                                                        <td>

                                                            <c:choose>
                                                                <c:when test="${editar}">
                                                                    <c:choose>
                                                                        <c:when test="${sangria_caballo.getParticipo(dia) || sangria_caballo.getParticipo(dia) == null}">
                                                                            <label class="fancy-checkbox" style="text-align:center">
                                                                                <input type="checkbox" value="${sangria_caballo.getCaballo().getId_caballo()}" name="caballos" checked>
                                                                                <span></span>
                                                                            </label>
                                                                            <input type="checkbox" name="caballos_false" value="${sangria_caballo.getCaballo().getId_caballo()}" style="display:none">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <label class="fancy-checkbox" style="text-align:center">
                                                                                <input type="checkbox" value="${sangria_caballo.getCaballo().getId_caballo()}" name="caballos">
                                                                                <span></span>
                                                                            </label>
                                                                            <input type="checkbox" name="caballos_false" value="${sangria_caballo.getCaballo().getId_caballo()}" checked style="display:none">
                                                                            <c:set var="deshabilitado" value="true"></c:set>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:choose>
                                                                        <c:when test="${sangria_caballo.getParticipo(dia - 1)}">
                                                                            <label class="fancy-checkbox" style="text-align:center">
                                                                                <input type="checkbox" value="${sangria_caballo.getCaballo().getId_caballo()}" name="caballos" checked>
                                                                                <span></span>
                                                                            </label>
                                                                            <input type="checkbox" name="caballos_false"  value="${sangria_caballo.getCaballo().getId_caballo()}" style="display:none">
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <label class="fancy-checkbox" style="text-align:center">
                                                                                <input type="checkbox" value="${sangria_caballo.getCaballo().getId_caballo()}" name="caballos" disabled>    
                                                                                <span></span>
                                                                            </label>
                                                                            <input type="checkbox" name="caballos_false" value="${sangria_caballo.getCaballo().getId_caballo()}" checked style="display:none">
                                                                            <c:set var="deshabilitado" value="true"></c:set>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:otherwise>
                                                            </c:choose>

                                                        </td>
                                                        <c:set var="sangre" value=""></c:set>
                                                        <c:set var="plasma" value=""></c:set>
                                                        <c:set var="observaciones" value=""></c:set>
                                                        <c:if test="${editar}">
                                                            <c:set var="sangre" value="${(sangria_caballo.getSangre(dia) == 0) ? '' : sangria_caballo.getSangre(dia)}"></c:set>
                                                            <c:set var="plasma" value="${(sangria_caballo.getPlasma(dia) == 0) ? '' : sangria_caballo.getPlasma(dia)}"></c:set>
                                                            <c:set var="observaciones" value="${(sangria_caballo.getObservaciones(dia) == 'Sin observaciones.') ? '' : sangria_caballo.getObservaciones(dia)}"></c:set>
                                                        </c:if>
                                                        <td>
                                                            <input type="number" step="any" placeholder="" class="form-control" name="sangre_${sangria_caballo.getCaballo().getId_caballo()}"
                                                                   value="${(sangre == 0) ? "" : sangre}" oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')" ${(deshabilitado == true) ? "disabled" : ""}>
                                                        </td>
                                                        <td>
                                                            <input type="number" step="any" placeholder="" class="form-control" name="plasma_${sangria_caballo.getCaballo().getId_caballo()}"
                                                                   value="${(plasma == 0) ? "" : plasma}" oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')" ${(deshabilitado == true) ? "disabled" : ""}>
                                                        </td>
                                                        <td>
                                                            <textarea class="form-control" name="observaciones_${sangria_caballo.getCaballo().getId_caballo()}"
                                                                      oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">${observaciones}</textarea>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>

                                <div class="col-md-12">
                                    <p class="campos-requeridos">
                                        Los campos marcados con * son requeridos.
                                    </p>

                                    <div class="row">
                                        <div class="form-group">
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                                                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar y Salir</button>
                                                <button id="boton-guardar-volver" type="button" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar </button>
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
        <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
        <script src="/SIGIPRO/recursos/js/sigipro/sangrias.js"></script>
    </jsp:attribute>
</t:plantilla_general>
