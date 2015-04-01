<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form id="caballosform" class="form-horizontal" autocomplete="off" method="post" action="Caballo">
    <div class="col-md-6">
        <input hidden="true" name="id_caballo" value="${caballo.getId_caballo()}">
        <input hidden="true" name="accion" value="${accion}">
        <input hidden='true' id='imagenSubida2' value=''>
        <label for="nombre" class="control-label">*Nombre</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getNombre()==null}">
                            <input type="text" placeholder="Nombre del caballo" class="form-control" name="nombre" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' class="form-control" name="nombre" value="${caballo.getNombre()}"> 
                            <input hidden="true" name="nombre" value="${caballo.getNombre()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="numero_microchip" class="control-label">*Número de Microchip</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getNumero_microchip()==null||caballo.getNumero_microchip()==0}">
                            <input type="number" placeholder="341341" class="form-control" name="numero_microchip" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="number" disabled='true' class="form-control" name="numero_microchip" value="${caballo.getNumero_microchip()}"> 
                            <input hidden="true" name="numero_microchip" value="${caballo.getNumero_microchip()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>              
        <label for="grupodecaballo" class="control-label">Grupo del caballo</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionGrupoDeCaballo" class="select2" name="grupodecaballo"
                            style='background-color: #fff;'
                            onchange="setCustomValidity('')">
                        <option value=''></option>
                        <c:forEach items="${listagrupos}" var="grupo">
                            <c:choose>
                                <c:when test="${grupo.getId_grupo_caballo() == caballo.getGrupo_de_caballos().getId_grupo_caballo()}" >
                                    <option value=${grupo.getId_grupo_caballo()} selected> ${grupo.getNombre()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value=${grupo.getId_grupo_caballo()}>${grupo.getNombre()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getFecha_ingreso()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' value="${caballo.getFecha_ingresoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_ingreso" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                            <input hidden="true" name="fecha_ingreso" value="${caballo.getFecha_ingresoAsString()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <label for="fecha_nacimiento" class="control-label">Fecha de Nacimiento</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getFecha_nacimiento()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_nacimiento" data-date-format="dd/mm/yyyy"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' value="${caballo.getFecha_nacimientoAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_nacimiento" data-date-format="dd/mm/yyyy"
                                   onchange="setCustomValidity('')">
                            <input hidden="true" name="fecha_nacimiento" value="${caballo.getFecha_ingresoAsString()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <label for="sexo" class="control-label">*Sexo</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getColor()==null}">
                            <select id="seleccionSexo" class="select2" name="sexo" 
                                    style='background-color: #fff;' required
                                    oninvalid="setCustomValidity('Por favor seleccione un sexo')"
                                    onchange="setCustomValidity('')">
                                <option value=''></option>
                                <c:forEach items="${sexos}" var="sexo">
                                    <c:choose>
                                        <c:when test="${sexo.equals(caballo.getSexo())}" >
                                            <option  value="${sexo}" selected> ${sexo}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${sexo}">${sexo}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' class="form-control" name="sexo" value="${caballo.getSexo()}"> 
                            <input hidden="true" name="sexo" value="${caballo.getSexo()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="color" class="control-label">Color</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${caballo.getColor()==null}">
                            <input type="text" placeholder="Negro" class="form-control" name="color"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" disabled='true' class="form-control" name="color" value="${caballo.getColor()}"> 
                            <input hidden="true" name="color" value="${caballo.getColor()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>         
        <label for="otras_sennas" class="control-label">Otras señas</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input id="otras_sennas" placeholder="Otras señas del caballo" type="text" step="any" class="form-control" name="otras_sennas" value="${caballo.getOtras_sennas()}" 
                           oninput="setCustomValidity('')"> 
                </div>
            </div>
        </div>
        <label for="estado" class="control-label">*Estado</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionEstado" class="select2" name="estado" 
                            style='background-color: #fff;' required
                            oninvalid="setCustomValidity('Por favor seleccione un estado')"
                            onchange="setCustomValidity('')">
                        <option value=''></option>
                        <c:forEach items="${estados}" var="estado">
                            <c:choose>
                                <c:when test="${estado.equals(caballo.getEstado())}" >
                                    <option value="${estado}" selected> ${estado}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${estado}" selected>${estado}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>  
    </div>
    <div class="row">
        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-map-marker"></i> Fotografía (Sin Implementar)</h3>
                </div>
                <div class="widget-content">
                    <label for="fotografia" class="control-label">Fotografía</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">                
                                <input type="file" value='' name="fotografia" accept="image/*" onchange="previewFile()" />
                                <input type='hidden' name='fotografia' id='fotografia' value=''>
                                <div><img name='imagenSubida' id="img_newjourney" src='' height="300" alt=""></div>
                            </div>
                        </div>
                    </div>
                    <img src="${caballo.getFotografia()}"
                </div>
            </div>
        </div>
    </div>
</div>                           
<div class="col-md-12">
    <!-- Esta parte es la de los caballos del grupo -->
    <div class="widget widget-table">
        <div class="widget-header">
            <h3><i class="fa fa-check"></i> Eventos Clínicos Realizados Al Caballo</h3>
            <div class="btn-group widget-header-toolbar">
                <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarEvento">Agregar</a>
            </div>
        </div>
        <div class="widget-content">
            <table id="caballos-evento" class="table table-sorting table-striped table-hover datatable">
                <thead>
                    <tr>
                        <th>Fecha del Evento</th>
                        <th>Tipo del Evento</th>
                        <th>Usuario Responsable</th>
                        <th>Eliminar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listaEventos}" var="evento">
                        <tr id="evento-${evento.getId_evento()}">
                            <td>${evento.getFecha()}</td>
                            <td>${evento.getTipo_evento().getNombre()}</td>
                            <td>${evento.getResponsable().getNombre_usuario()}</td>
                            <td>
                                <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarEventoDeCaballo(${evento.getId_evento()})">Eliminar</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<p class="campos-requeridos">
    Los campos marcados con * son requeridos.
</p>  
<div class="form-group">
    <div class="modal-footer">
        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
        <c:choose>
            <c:when test= "${accion.equals('Editar')}">
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
            </c:when>
            <c:otherwise>
                <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Caballo</button>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</form>

<t:modal idModal="modalAgregarEvento" titulo="Agregar Eventos">
    <jsp:attribute name="form">
            <form class="form-horizontal" id="agregarEventos" autocomplete="off" method="post" action="Caballeriza">
                <input hidden="true" name="accion" value="Evento">
                <input id="ids-eventos" hidden="true" name="ids_eventos" value="">
                <input hidden="true" id='id_caballo' name='id_caballo' value="">
                <label for="tipo-evento" class="control-label">*Seleccione El Evento:</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <select id="eventoModal" class="select2" name="eventoModal"
                                    style='background-color: #fff;' required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    onchange="setCustomValidity('')">
                                <option value=''></option>
                                <c:forEach items="${listaEventosRestantes}" var="evento">
                                    <option value="${evento.getId_evento()}|${evento.getDescripcion().toString()}|${evento.getFecha()}|${evento.getTipo_evento().getNombre()}|${evento.getResponsable().getNombre_usuario()}"> ID: ${evento.getId_evento()} -- Fecha: ${evento.getFechaAsString()} -- Tipo: ${evento.getTipo_evento().getNombre()}</option>
                                </c:forEach>

                            </select>
                        </div>
                    </div>
                </div>
                <label for="observaciones" class="control-label">Descripción del Evento:</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <BR>
                            <textarea disabled='true' rows="8" cols="50" maxlength="500" placeholder="Descripción Del Evento" class="form-control" name="observaciones" id="observaciones"></textarea>
                        </div>
                    </div>
                </div>
            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <button id="btn-agregarEventoCaballo" type="button" class="btn btn-primary" onclick="agregarEventoCaballo()"><i class="fa fa-check-circle"></i> Agregar Evento</button>
                </div>
            </div>
            </form>

    </jsp:attribute>

</t:modal>                   