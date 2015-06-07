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
                <div class="col-md-8 ">
                    <ul class="breadcrumb">
                        <li>Caballeriza</li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Sangria?">Sangría</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Sangria?accion=ver&id_sangria=${sangria.getId_sangria()}">Sangría ${sangria.getId_sangria()}</a>
                        </li>
                        <li class="active"> Agregar Extracción</li>

                    </ul>
                </div>
                <div class="col-md-4 ">
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
                            <h3><i class="fa fa-tint"></i> Agregar Extracción del día ${dia} para la sangría ${sangria.getId_sangria()}</h3>
                        </div>
                        ${mensaje}
                        <div class="widget-content">

                            <form class="form-horizontal" autocomplete="off" method="post" action="Sangria">
                                <div class="col-md-12">
                                    <input hidden="true" name="id_sangria" value="${sangria.getId_sangria()}">
                                    <input hidden="true" name="dia" value="${dia}">
                                    <input hidden="true" name="accion" value="Extraccion">
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
                                        <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                                            <thead>
                                                <tr>
                                                    <th rowspan="2">Nombre y Número de Caballo</th>
                                                    <th rowspan="2" style="text-align:center">Participó</th>
                                                    <th colspan="3">Día ${dia}</th>
                                                </tr>
                                                <tr>
                                                    <th>Sangre</th>
                                                    <th>Plasma</th>
                                                    <th>LAL</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${sangria.getSangrias_caballos()}" var="sangria_caballo">
                                                    <tr id="${caballo.getId_caballo()}">
                                                        <td>${sangria_caballo.getCaballo().getNombre()} (${sangria_caballo.getCaballo().getNumero()})</td>
                                                        <td>
                                                            <label class="fancy-checkbox" style="text-align:center">
                                                                <input type="checkbox" value="${sangria_caballo.getCaballo().getId_caballo()}" name="caballos" ${(editar && sangria_caballo.sumatoria(dia) == 0) ? "" : "checked"}>
                                                                <span></span>
                                                            </label>
                                                        </td>
                                                        <c:set var="sangre" value=""></c:set>
                                                        <c:set var="plasma" value=""></c:set>
                                                        <c:set var="lal" value=""></c:set>
                                                        <c:if test="${editar}">
                                                            <c:set var="sangre" value="${(sangria_caballo.getSangre(dia) == 0) ? '' : sangria_caballo.getSangre(dia)}"></c:set>
                                                            <c:set var="plasma" value="${(sangria_caballo.getPlasma(dia) == 0) ? '' : sangria_caballo.getPlasma(dia)}"></c:set>
                                                            <c:set var="lal" value="${(sangria_caballo.getLal(dia) == 0) ? '' : sangria_caballo.getLal(dia)}"></c:set>
                                                        </c:if>
                                                        <td>
                                                            <input type="number" step="any" placeholder="" class="form-control" name="sangre_${sangria_caballo.getCaballo().getId_caballo()}"
                                                                   value="${(sangre == 0) ? "" : sangre}" oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">
                                                        </td>
                                                        <td>
                                                            <input type="number" step="any" placeholder="" class="form-control" name="plasma_${sangria_caballo.getCaballo().getId_caballo()}"
                                                                   value="${(plasma == 0) ? "" : plasma}" oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">
                                                        </td>
                                                        <td>
                                                            <input type="number" step="any" placeholder="" class="form-control" name="lal_${sangria_caballo.getCaballo().getId_caballo()}"
                                                                   value="${(lal == 0) ? "" : lal}" oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">
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
                                                <c:choose>
                                                    <c:when test="${editar}">
                                                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Extracción</button>
                                                    </c:otherwise>
                                                </c:choose>
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
    </jsp:attribute>
</t:plantilla_general>
