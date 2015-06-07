<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="form-prueba-sangria" class="form-horizontal" autocomplete="off" method="post" action="SangriaPrueba">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_sangria_prueba" value="${sangria_prueba.getId_sangria_prueba()}">
            <input hidden="true" name="accion" value="${accion}">
            <label for="muestra" class="control-label">*Muestra</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangria_prueba.getMuestra()==null}">
                                <input type="text" placeholder="Sangría P0 2" class="form-control" name="muestra" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control" name="muestra" value="${sangria_prueba.getMuestra()}"> 
                                <input hidden="true" name="muestra" value="${sangria_prueba.getMuestra()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="num_solicitud" class="control-label">Solicitud N°</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangria_prueba.getNum_solicitud()==null||sangria_prueba.getNum_solicitud()==0}">
                                <input type="number" placeholder="6461213" class="form-control" name="num_solicitud" 
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="number" class="form-control" name="num_solicitud" value="${sangria_prueba.getNum_solicitud()}"> 
                                <input hidden="true" name="num_solicitud" value="${sangria_prueba.getNum_solicitud()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="num_informe" class="control-label">Informe N°</label>          
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangria_prueba.getNum_informe()==null||sangria_prueba.getNum_informe()==0}">
                                <input type="number" placeholder="6461213" class="form-control" name="num_informe"
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="number" class="form-control" name="num_informe" value="${sangria_prueba.getNum_informe()}"> 
                                <input hidden="true" name="num_informe" value="${sangria_prueba.getNum_informe()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="responsable" class="control-label">*Responsable</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangria_prueba.getResponsable()==null}">
                                <input type="text" placeholder="Persona Responsable" class="form-control" name="responsable" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       oninput="setCustomValidity('')"> 
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="form-control" name="responsable" value="${sangria_prueba.getResponsable()}"> 
                                <input hidden="true" name="responsable" value="${sangria_prueba.getResponsable()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>            
        </div>
        <div class="col-md-6">
            <label for="fecha_recepcion_muestra" class="control-label">*Fecha de recepción de la muestra</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangria_prueba.getFecha_recepcion_muestra()==null}">
                                <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_recepcion_muestra" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                            </c:when>
                            <c:otherwise>
                                <input type="text"  value="${sangria_prueba.getFecha_recepcion_muestraAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_recepcion_muestra" data-date-format="dd/mm/yyyy" required
                                       oninvalid="setCustomValidity('Este campo es requerido ')"
                                       onchange="setCustomValidity('')">
                                <input hidden="true" name="fecha_recepcion_muestra" value="${sangria_prueba.getFecha_recepcion_muestraAsString()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>
            <label for="fecha_informe" class="control-label">*Fecha del informe</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <c:choose>
                            <c:when test="${sangria_prueba.getFecha_informe()==null}">
                                <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_informe" data-date-format="dd/mm/yyyy" 
                                       onchange="setCustomValidity('')">
                            </c:when>
                            <c:otherwise>
                                <input type="text"  value="${sangria_prueba.getFecha_informeAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_informe" data-date-format="dd/mm/yyyy"
                                       onchange="setCustomValidity('')">
                                <input hidden="true" name="fecha_informe" value="${sangria_prueba.getFecha_informeAsString()}">
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>        
            <label for="inoculo" class="control-label">*Inóculo</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionInoculo" class="select2" name="inoculo" 
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Por favor seleccione el tipo del evento')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${lista_inoculos}" var="inoculo">
                                <c:choose>
                                    <c:when test="${inoculo.getId_inoculo() == sangria_prueba.getInoculo().getId_inoculo()}" >
                                        <option value ="${inoculo.getId_inoculo()}"  selected> ${inoculo.getId_inoculo()}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value ="${inoculo.getId_inoculo()}">${inoculo.getId_inoculo()}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>           
        </div>
    </div>
    <br>
    <input hidden="true" name="accion" value="${accion}">
    <input id="caballos" hidden="true" name="caballos" value="">
    <div class="col-md-12" >
        <div class="widget widget-table">
            <div class="widget-header">
                <h3><i class="fa fa-check"></i> Caballos Asociados</h3>
            </div>
            <div class="widget-content">
                <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                    <thead>
                        <tr>
                            <th>Caballo</th>
                            <th>Número de Caballo</th>
                            <th>Hematrocito</th>
                            <th>Hemoglobina</th>
                            <th>Eliminar</th>
                        </tr>
                    </thead>
                    <c:forEach items="${lista_inoculos}" var="inoculo">
                        <tbody class="tabla-caballos" id="inoculo-${inoculo.getId_inoculo()}" hidden>
                            <c:forEach items="${inoculo.getCaballos()}" var="caballo">
                                <tr id="${caballo.getId_caballo()}">
                                    <td>${caballo.getNombre()}</td>
                                    <td>${caballo.getNumero()}</td>
                                    <td width=150px>
                                        <input type="number" step="any" placeholder="" class="form-control" name="hematrocito_${caballo.getId_caballo()}" value="" oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">
                                    </td>
                                    <td width=150px>
                                        <input type="number" step="any" placeholder="" class="form-control" name="hemoglobina_${caballo.getId_caballo()}" value="" oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">
                                    </td>
                                    <td width=50px>
                                        <button type="button" class="btn btn-danger btn-sm" onclick="eliminarCaballoSP(${caballo.getId_caballo()})" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>
    <div class="col-md-12">    
        <div class="row">
            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <c:choose>
                        <c:when test= "${accion.equals('Editar')}">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle" onclick="confirmacionAgregarCaballos()"></i> ${accion} Sangría de Prueba</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

</div>
</form>                        