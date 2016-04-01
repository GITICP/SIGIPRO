<%-- 
    Document   : Ver
    Created on : 25-mar-2015, 18:22:58
    Author     : Walter
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        <!-- content-wrapper -->
        <div class="col-md-12 content-wrapper">
            <div class="row">
                <div class="col-md-12 ">
                    <ul class="breadcrumb">
                        <li>Caballeriza</li>
                        <li> 
                            <a href="/SIGIPRO/Caballeriza/Caballo?">Caballos</a>
                        </li>
                        <li class="active"> Caballo número ${caballo.getNumero()} </li>
                    </ul>
                </div>
            </div>
            <!-- main -->
            <div class="content">
                <div class="main-content">
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="sigipro-horse-1"></i> Caballo número ${caballo.getNumero()} </h3>
                            <div class="btn-group widget-header-toolbar">
                                <%--<a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=inoculo&id_caballo=${caballo.getId_caballo()}">Inóculos</a>--%>
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=sangriap&id_caballo=${caballo.getId_caballo()}">Sangrías de Prueba</a>
                                <a class="btn btn-primary btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=sangria&id_caballo=${caballo.getId_caballo()}">Sangrías</a>
                                <c:set var="contienePermisoEditar" value="false" />
                                <c:forEach var="permiso" items="${sessionScope.listaPermisos}">
                                    <c:if test="${permiso == 1 || permiso == 50}">
                                        <c:set var="contienePermisoEditar" value="true" />
                                    </c:if>
                                </c:forEach>
                                <c:if test="${contienePermisoEditar}">
                                    <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Caballeriza/Caballo?accion=editar&id_caballo=${caballo.getId_caballo()}">Editar</a>
                                </c:if>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <div class="row">
                                <div class="col-md-6">
                                    <div class="widget-content">
                                        <table>
                                            <tr><td> <strong>Nombre:</strong> <td>${caballo.getNombre()} </td></tr>
                                            <tr><td> <strong>Número de Caballo</strong> <td>${caballo.getNumero()} </td></tr>
                                            <tr><td> <strong>Número de Microchip:</strong> <td>${caballo.getNumero_microchip()} </td></tr>
                                            <tr><td> <strong>Grupo del Caballo:</strong> <td>
                                                    <c:set var="val" value=""/>
                                                    <c:choose> 
                                                        <c:when test="${caballo.getGrupo_de_caballos().getNombre() == null}">
                                                            No tiene grupo
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${caballo.getGrupo_de_caballos().getNombre()}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td></tr>                
                                            <tr><td> <strong>Fecha de Nacimiento:</strong> <td>${caballo.getFecha_nacimientoAsString()} </td></tr>
                                            <tr><td> <strong>Fecha de Ingreso:</strong> <td>${caballo.getFecha_ingresoAsString()} </td></tr>
                                            <tr><td> <strong>Sexo:</strong> <td>${caballo.getSexo()} </td></tr>
                                            <tr><td> <strong>Color:</strong> <td>${caballo.getColor()} </td></tr>
                                            <tr><td> <strong>Otras Señas:</strong> <td>${caballo.getOtras_sennas()}</td></tr>
                                            <tr><td> <strong>Estado:</strong> <td>${caballo.getEstado()}</td></tr>
                                        </table>
                                    </div>
                                </div>
                                <div class="col-md-6" align="right">
                                    <table>
                                        <c:forEach items="${caballo.getImagenes()}" var='imagen'>
                                            <td>
                                                <div class="widget-content">
                                                    <span style="cursor:zoom-in">
                                                        <img title="Click para ampliar imagen." src="${imagen.getImagen_ver()}" height="100" width="100" onclick="mostrarGrande(this)"></span>
                                                </div>  
                                            </td>
                                        </c:forEach>
                                    </table>

                                </div>
                            </div>
                            <br>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-book"></i> Historial de Peso </h3>
                                    <div class="btn-group widget-header-toolbar">
                                        <a class="btn btn-primary btn-sm boton-accion" data-id='${caballo.getId_caballo()}' data-toggle="modal" data-target="#modalAgregarPeso">Registrar Peso</a>
                                    </div>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Fecha de Pesaje</th>
                                                <th>Peso</th>
                                                <th>Editar</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${caballo.getPesos()}" var="peso">
                                                <tr data-id-peso="${peso.getId_peso()}">
                                                    <td class="fecha">${peso.getFechaAsString()}</td>
                                                    <td class="peso">${peso.getPeso()}</td>
                                                    <td>
                                                        <button type="button" class="btn btn-warning btn-sm boton-accion peso-caballo">Editar</button>
                                                        <button type="button" class="btn btn-danger btn-sm boton-accion peso-caballo-eliminar">Eliminar</button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="widget widget-table">
                                <div class="widget-header">
                                    <h3><i class="fa fa-check"></i> Eventos Clínicos del Caballo </h3>
                                </div>
                                <div class="widget-content">
                                    <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-desc-filter">
                                        <!-- Columnas -->
                                        <thead> 
                                            <tr>
                                                <th>Identificador</th>
                                                <th>Fecha del Evento</th>
                                                <th>Tipo de Evento</th>
                                                <th>Usuario responsable</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${caballo.getEventos()}" var="eventos">
                                                <tr id ="${eventos.getId_evento()}">
                                                    <td>
                                                        <a href="/SIGIPRO/Caballeriza/EventoClinico?accion=ver&id_evento=${eventos.getId_evento()}">
                                                            <div style="height:100%;width:100%">
                                                                ${eventos.getId_evento()}
                                                            </div>
                                                        </a>
                                                    </td>
                                                    <td>${eventos.getFechaAsString()}</td>
                                                    <td>${eventos.getTipo_evento().getNombre()}</td>
                                                    <c:choose>
                                                        <c:when test="${eventos.getResponsable()!= null}">
                                                            <td>${eventos.getResponsable().getNombre_completo()}</td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td>No Tiene Usuario Responsable</td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </tr>
                                            </c:forEach>
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

        <div class='modal fade' id='ModalConfirmacionEliminar' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>
            <div class='modal-dialog'>
                <div class='modal-content'>
                    <div class='modal-header'>
                        <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>
                        <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>
                    </div>
                    <div class='modal-body'>
                        <h5 class='title'>&iquest;Est&aacute; seguro que desea eliminar este registro de peso?</h5>
                        <br>
                        <form method='post' action="Caballo">
                            <input type='hidden' id='eliminar-peso-id' name='id_peso'>
                            <input type='hidden' id='eliminar-caballo-id' name='id_caballo_peso' value='${caballo.getId_caballo()}'>
                            <input type='hidden' name='accion' value='eliminarpeso'>
                            <div class='form-group'>
                                <div class='modal-footer'>
                                    <button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-times-circle'></i> Cancelar</button>
                                    <button type='submit' class='btn btn-primary'><i class='fa fa-check-circle'></i> Confirmar</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
    </jsp:attribute>

</t:plantilla_general>

<t:modal idModal="modalAgregarPeso" titulo="Registrar Peso">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarPeso" method="post" action="Caballo">
                <input hidden="true" name="accion" value="agregarpeso">
                <input hidden="true" id="id_serpiente_imagen" name="id_caballo_peso" value="${caballo.getId_caballo()}">
                <div class="row">
                    <div class="col-md-12">
                        <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha_pesaje" data-date-format="dd/mm/yyyy" required
                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                           onchange="setCustomValidity('')">
                                </div>
                            </div>
                        </div>
                        <label for="fecha_ingreso" class="control-label">*Peso (kg)</label>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <input type="number" step="any" placeholder="" class="form-control" name="peso"
                                           oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>            

                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Registrar Peso</button>            
                    </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalEditarPeso" titulo="Editar Peso">
    <jsp:attribute name="form">
        <div class="widget-content">
            <form class="form-horizontal" id="agregarPeso" method="post" action="Caballo">
                <input hidden="true" name="accion" value="editarpeso">
                <input hidden="true" id="editar-id-caballo" name="id_caballo_peso" value="${caballo.getId_caballo()}">
                <input hidden="true" id="editar-id-peso" name="id_peso">
                <div class="row">
                    <div class="col-md-12">
                        <label for="fecha_ingreso" class="control-label">*Fecha de Ingreso</label>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="editar-fecha-peso" class="form-control sigiproDatePicker" name="fecha_pesaje" data-date-format="dd/mm/yyyy" required
                                           oninvalid="setCustomValidity('Este campo es requerido ')"
                                           onchange="setCustomValidity('')">
                                </div>
                            </div>
                        </div>
                        <label for="fecha_ingreso" class="control-label">*Peso (kg)</label>
                        <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group">
                                    <input id="editar-peso" type="number" step="any" placeholder="" class="form-control" name="peso"
                                           oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Ingrese solo números\')">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>            

                <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>Cancelar</button>
                        <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>            
                    </div>
                </div>
            </form>
        </div>

    </jsp:attribute>

</t:modal>

<t:modal idModal="modalVerImagen" titulo="Ver Imagen">
    <jsp:attribute name="form">
        <div class="widget-content">
            <img id="imagenGrande" src="" height="540" width="540">
        </div>

    </jsp:attribute>

</t:modal>

