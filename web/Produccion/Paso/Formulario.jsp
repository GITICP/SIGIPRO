<%-- 
    Document   : Formulario
    Created on : Jul 3, 2015, 8:43:02 AM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="agregarPaso" autocomplete="off" enctype='multipart/form-data' method="post" action="Paso">
    <div class="row">
        <div class="col-md-12">
            <input hidden="true" name="id_paso" value="${paso.getId_paso()}">
            <input hidden="true" name="accion" value="${accion}">
            <input hidden="true" name="orden" id="orden" value="${lista.toString().replace("]","").replace("[","").replace(" ","")}">
            <input hidden="true" name="contador" id="contador" value="${contador}">
            <input hidden="true" name="cantidad" id="cantidad" value="${cantidad}">
            <input hidden="true" name="version" id="version" value="${paso.getVersion()}">

            <label for="nombre" class="control-label">*Nombre del Paso</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre del Paso de Protocolo" class="form-control" name="nombre" value="${paso.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
        </div>

        <div class="col-md-12">
            <div class="widget widget-table">
                <div class="widget-header">
                    <h3><i class="fa fa-th-list"></i> Estructura</h3>
                    <div class="btn-group widget-header-toolbar">

                    </div>
                </div>
                <div class="widget-content">
                    <div class="campos sortable" id="sortable">
                        <c:forEach items="${lista}" var="i">
                            <c:choose>
                                <c:when test="${diccionario.get(i).get('tipocampo').equals('campo')}">
                                    <div class="widget widget-table campo_${i}" id="${i}">
                                        <input hidden="true" id="elemento_${i}" value="campo">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-edit"></i> Campo #${i}</h3>
                                            <div class="btn-group widget-header-toolbar">
                                                <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('campo_${i}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                            </div>
                                        </div>
                                        <div class="widget-content">
                                            <div class="col-md-12">
                                                <label for="tipo" class="control-label"> *Tipo de Campo</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group">
                                                            <select id="tipocampo_${i}" class="select2" name="c_tipocampo_${i}"
                                                                    style='background-color: #fff;' required
                                                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                                                    onchange="setCustomValidity('')">
                                                                <option value=''></option>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('number')}">
                                                                    <option value="number" selected>Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('text')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text" selected>Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('textarea')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea" selected>Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('fecha')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha" selected>Fecha</option>
                                                                </c:if>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                                <label for="nombre" class="control-label">*Nombre del Campo</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group">
                                                            <input type="text" maxlength="45" placeholder="Nombre" class="form-control" name="c_nombre_${i}" value="${diccionario.get(i).get('etiqueta')}"
                                                                   required
                                                                   oninvalid="setCustomValidity('Este campo es requerido')"
                                                                   oninput="setCustomValidity('')" > 
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="widget widget-table seleccion_${i}" id="${i}">
                                        <input hidden="true" id="elemento_${i}" value="seleccion">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-edit"></i> Selección Múltiple #${i}</h3>
                                            <div class="btn-group widget-header-toolbar">
                                                <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('seleccion_' + contador + '')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                            </div>
                                        </div>
                                        <div class="widget-content">
                                            <div class="col-md-12">
                                                <label for="tipo" class="control-label"> *Nombre de Selección Múltiple</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group">
                                                            <input type="text" maxlength="45" placeholder="Nombre" class="form-control" name="s_snombre_${i}" value="${diccionario.get(i).get('nombre')}"
                                                                   required
                                                                   oninvalid="setCustomValidity('Este campo es requerido')"
                                                                   oninput="setCustomValidity('')" > 
                                                        </div>
                                                    </div>
                                                </div>
                                                <label for="nombre" class="control-label">*Opciones</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group opciones_${i}">
                                                            <input type="text" maxlength="45" placeholder="Nombre de la Opción" class="form-control" name="o_opcion_${i}_1" value="${diccionario.get(i).get('opcion1')}"
                                                                   required
                                                                   oninvalid="setCustomValidity('Este campo es requerido')"
                                                                   oninput="setCustomValidity('')" >
                                                            <c:forEach var="id" begin="2" end="${diccionario.get(i).get('cantidad')}">
                                                                <c:set var="idstring">${id}</c:set>
                                                                <div class='col-md-8 o_opcion${id}_${i}_${id}'> 
                                                                    <br><input type="text" maxlength="45" placeholder="Nombre de la Opción" class="form-control" name="o_opcion${id}_${i}_${id}" value="${diccionario.get(i).get('opcion'.concat(idstring))}"
                                                                               required
                                                                               oninvalid="setCustomValidity('Este campo es requerido')"
                                                                               oninput="setCustomValidity('')" ></div> 
                                                                <div class='col-md-4 o_opcion${id}_${i}_${id}'> <br> <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarOpcion('o_opcion${id}_${i}_${id}')" style="margin-left:7px;margin-right:5px;">Eliminar</button> </div>
                                                                </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class='col-md-12 form-group'>
                                                    <button type="button" onclick="agregarOpcion(${i})" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Opción</button>
                                                    <br>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>    
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <br>
                                <button type="button" onclick="agregarCampo()" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Campo</button>
                                <button type="button" onclick="agregarSeleccion()" class="btn btn-primary"><i class="fa fa-plus-square"></i> Agregar Selección Múltiple </button>
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
        <!-- Esta parte es la de los permisos de un rol -->
        <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </div>


    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Paso</button>
                </c:otherwise>
            </c:choose>    
        </div>
    </div>


</form>
