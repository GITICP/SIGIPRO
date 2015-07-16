<%-- 
    Document   : Formulario
    Created on : Jul 3, 2015, 8:43:02 AM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="agregarAnalisis" autocomplete="off" enctype='multipart/form-data' method="post" action="Analisis">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_analisis" value="${analisis.getId_analisis()}">
            <input hidden="true" name="accion" value="${accion}">
            <input hidden="true" name="orden" id="orden" value="${lista.toString().replace("]","").replace("[","").replace(" ","")}">
            <input hidden="true" id="listaTiposReactivo" value="${analisis.getListaTiposReactivo()}">
            <input hidden="true" id="listaTiposEquipo" value="${analisis.getListaTiposEquipo()}">
            <input hidden="true" id="listaColumnasExcel">

            <label for="nombre" class="control-label">*Nombre/Código/Identificador</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="Nombre/Código/Identificador del Analisis" class="form-control" name="nombre" value="${analisis.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
            <c:choose>
                <c:when test="${analisis.getId_analisis()!=0}">
                    <label for="nombre" class="control-label"> Machote (si no selecciona un archivo, quedará registrado el subido anteriormente)</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="machote" name="machote"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel"
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <label for="nombre" class="control-label">*Machote</label>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <div class="input-group">
                                <input type="file" id="machote" name="machote"  accept=".csv, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" required
                                       oninvalid="setCustomValidity('No es un archivo permitido. ')"
                                       onchange="setCustomValidity('')"/>
                            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-6">
            <label for="especie" class="control-label"> *Tipos de Reactivos</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionTipoReactivo" class="select2" name="tiporeactivos" multiple="multiple"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${tiporeactivos}" var="tipo">
                                <option value=${tipo.getId_tipo_reactivo()}>${tipo.getNombre()}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>

            <label for="especie" class="control-label"> *Tipos de Equipo de Medición</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <select id="seleccionTipoEquipo" class="select2" name="tipoequipos" multiple="multiple"
                                style='background-color: #fff;' required
                                oninvalid="setCustomValidity('Este campo es requerido')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${tipoequipos}" var="tipo">
                                <option value=${tipo.getId_tipo_equipo()}>${tipo.getNombre()}</option>
                            </c:forEach>
                        </select>
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
                                            <div class="col-md-6">
                                                <label for="tipo" class="control-label"> *Tipo de Campo</label>
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="input-group">
                                                            <select id="tipocampo_${i}" class="select2" name="c_tipocampo_${i}"
                                                                    style='background-color: #fff;' required
                                                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                                                    onchange="setCustomValidity('')">
                                                                <option value=''></option>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('Excel') or diccionario.get(i).get('tipo').equals('number')}">
                                                                    <option value="number" selected>Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('text')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text" selected>Campo de Texto</option>
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
                                            <div class="col-md-6">
                                                <div class="col-sm-6">
                                                    <br>
                                                    <div class="form-group">
                                                        <div class="col-sm-12">
                                                            <div class="input-group">
                                                                <c:choose>
                                                                    <c:when test="${diccionario.get(i).get('manual').equals('True')}">
                                                                        <input id="manual_${i}" onchange='checkManual(this,${i})' type="checkbox" name="c_manual_${i}" style="width:20px; height:20px;" checked><span>  Automático</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input id="manual_${i}" onchange='checkManual(this,${i})' type="checkbox" name="c_manual_${i}" style="width:20px; height:20px;"><span>  Automático</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="form-group">
                                                        <div class="col-sm-12">
                                                            <div class="input-group">
                                                                <c:choose>
                                                                    <c:when test="${diccionario.get(i).get('resultado').equals('True')}">
                                                                        <input id="visible_${i}" type="radio" name="c_camporesultado_0" style="width:20px; height:20px;" checked><span>  Resultado</span>
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input id="visible_${i}" type="radio" name="c_camporesultado_0" style="width:20px; height:20px;"><span>  Resultado</span>
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="col-sm-6">
                                                    <br>
                                                    <div class="form-group">
                                                        <div class="col-sm-12">
                                                            <div class="input-group">
                                                                <c:choose>
                                                                    <c:when test="${diccionario.get(i).get('manual').equals('True')}">
                                                                        <input type="text" maxlength="45" placeholder="Celda eg. A34" class="form-control" id="celda_${i}" name="c_celda_${i}" value="${diccionario.get(i).get('celda')}" 
                                                                               oninvalid="setCustomValidity('Este campo es requerido o no coincide con el formato requerido.')"
                                                                               oninput="setCustomValidity('')" >                                                                 
                                                                    </c:when>
                                                                    <c:otherwise>
                                                                        <input type="text" maxlength="45" placeholder="Celda eg. A34" class="form-control" id="celda_${i}" name="c_celda_${i}" disabled 
                                                                               oninvalid="setCustomValidity('Este campo es requerido o no coincide con el formato requerido.')"
                                                                               oninput="setCustomValidity('')" >                                                                 
                                                                    </c:otherwise>
                                                                </c:choose>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="widget widget-table tabla_${i}" id="${i}">
                                        <input hidden="true" id="elemento_${i}" value="tabla">
                                        <div class="widget-header">
                                            <h3><i class="fa fa-table"></i> Tabla #${i}</h3>
                                            <div class="btn-group widget-header-toolbar">
                                                <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('tabla_${i}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                                <%--<c:choose>
                                                    <c:when test="${diccionario.get(i).get('visible').equals('True')}">
                                                        <input id="visible_${i}" type="checkbox" name="t_tablavisible_${i}" checked style="width:20px; height:20px; alignment-baseline: central"><span>  Visible para Usuarios</span>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <input id="visible_${i}" type="checkbox" name="t_tablavisible_${i}" style="width:20px; height:20px; alignment-baseline: central"><span>  Visible para Usuarios</span>
                                                    </c:otherwise>
                                                </c:choose>--%>
                                            </div>
                                        </div>
                                        <div class="widget-content">
                                            <label for="nombre" class="control-label">*Nombre de Tabla</label>
                                            <div class="form-group">
                                                <div class="col-sm-12">
                                                    <div class="input-group">
                                                        <input type="text" maxlength="45" placeholder="Nombre de la Tabla" class="form-control" name="t_nombretabla_${i}" value="${diccionario.get(i).get('nombre')}"
                                                               required
                                                               oninvalid="setCustomValidity('Este campo es requerido')"
                                                               oninput="setCustomValidity('')" > 
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="widget widget-table" id="${i}">
                                                <div class="widget-header">
                                                    <h3><i class="fa fa-columns"></i> Columnas</h3>
                                                    <div class="btn-group widget-header-toolbar">
                                                    </div>
                                                </div>
                                                <div class='widget-content'>
                                                    <div class="columnas_${i}">
                                                        <c:if test="${diccionario.get(i).get('columnas').size()>0}">
                                                            <c:forEach begin="0" end="${diccionario.get(i).get('columnas').size()-1}" var="c">
                                                                <div class='columna col-md-12 columna_${i}_${c}_e' id="columna_${i}_${c}_e" >
                                                                    <div class='col-md-5'>
                                                                        <label for="nombre" class="control-label">*Nombre de Columna</label>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-12">
                                                                                <div class="input-group">
                                                                                    <input type="text" maxlength="45" placeholder="Nombre" class="form-control" name="t_nombrecolumna_${i}_${c}" value="${diccionario.get(i).get('columnas').get(c)}"
                                                                                           required
                                                                                           oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                           oninput="setCustomValidity('')" > 
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-5">
                                                                        <label for="especie" class="control-label"> *Tipo del Campo</label>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-12">
                                                                                <div class="input-group">
                                                                                    <select id="tipocampocolumna_${i}_${c}_e" class="select2" name="t_tipocampocolumna_${i}_${c}"
                                                                                            style='background-color: #fff;' required
                                                                                            oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                            onchange="setCustomValidity('')">
                                                                                        <option value=''></option>
                                                                                        <c:if test="${diccionario.get(i).get('tipocolumnas').get(c).equals('number')}">
                                                                                            <option value="number_tabla" selected>Número</option>
                                                                                            <option value="text_tabla">Campo de Texto</option>
                                                                                        </c:if>
                                                                                        <c:if test="${diccionario.get(i).get('tipocolumnas').get(c).equals('text')}">
                                                                                            <option value="number_tabla">Número</option>
                                                                                            <option value="text_tabla" selected>Campo de Texto</option>
                                                                                        </c:if>
                                                                                    </select>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-2">
                                                                        <br>
                                                                        <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('columna_${i}_${c}_e')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </c:if>
                                                    </div>
                                                    <div class='col-md-12 form-group'>
                                                        <button type="button" onclick="agregarColumna('${i}')" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Columna</button>
                                                        <br>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="widget widget-table" id="${i}">
                                                <div class="widget-header">
                                                    <h3><i class="fa fa-bars"></i> Filas</h3>
                                                    <div class="btn-group widget-header-toolbar">
                                                    </div>
                                                </div>
                                                <div class='widget-content'>
                                                    <div class='filas col-md-12'>
                                                        <div class='col-md-6'>
                                                            <label for="nombre" class="control-label">*Cantidad</label>
                                                            <div class="form-group">
                                                                <div class="col-sm-12">
                                                                    <div class="input-group">
                                                                        <input type="number" min='0' id="cantidadfilas_${i}" maxlength="45" placeholder="Cantidad de Filas" class="form-control" name="t_cantidadfilas_${i}" value="${diccionario.get(i).get('cantidadfilas')}"
                                                                               required
                                                                               oninvalid="setCustomValidity('Este campo es requerido')"
                                                                               oninput="setCustomValidity('')" > 
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <label for="nombre" class="control-label">*Nombre de la Columna</label>
                                                            <div class="form-group">
                                                                <div class="col-sm-12">
                                                                    <div class="input-group">
                                                                        <input type="text" maxlength="45" placeholder="Nombre de la columna de las filas" class="form-control" name="t_nombrefilacolumna_${i}" value="${diccionario.get(i).get('nombrefilacolumna')}"
                                                                               required
                                                                               oninvalid="setCustomValidity('Este campo es requerido')"
                                                                               oninput="setCustomValidity('')" > 
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <br>
                                                            <div class="form-group">
                                                                <div class="col-sm-12">
                                                                    <div class="input-group">
                                                                        <c:choose>
                                                                            <c:when test="${diccionario.get(i).get('nombrefilas') == null}">
                                                                                <input id="connombre_${i}" onchange="checkNombres(this,${i})" type="checkbox" name="t_connombre_${i}" style="width:20px; height:20px; "><span>  Con Nombre</span>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <input id="connombre_${i}" onchange="checkNombres(this,${i})" checked type="checkbox" name="t_connombre_${i}" style="width:20px; height:20px; "><span>  Con Nombre</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <br>
                                                            <div class="form-group">
                                                                <div class="col-sm-12">
                                                                    <div class="input-group">
                                                                        <c:choose>
                                                                            <c:when test="${diccionario.get(i).get('nombrefilas') == null}">
                                                                                <input class="select2-tags" type="text" placeholder="Nombre, filas, separadas, por, comas" name="t_nombresfilas_${i}" id="nombresfilas_${i}" disabled
                                                                                       oninvalid="setCustomValidity('Este campo es requerido o no concuerda con la cantidad de filas a ingresar.')"
                                                                                       oninput="setCustomValidity('')" >                                                                             
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <input class="select2-tags" type="text" placeholder="Nombre, filas, separadas, por, comas" name="t_nombresfilas_${i}" id="nombresfilas_${i}" value="${diccionario.get(i).get('nombrefilas').toString().replace("[","").replace("]","")}"
                                                                                       oninvalid="setCustomValidity('Este campo es requerido o no concuerda con la cantidad de filas a ingresar.')"
                                                                                       oninput="setCustomValidity('')" >                                                                             
                                                                            </c:otherwise>
                                                                        </c:choose>

                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="filasespeciales_${i}">
                                                        <c:if test="${diccionario.get(i).get('filasespeciales').size()>0}">
                                                            <c:forEach begin="0" end="${diccionario.get(i).get('filasespeciales').size()-1}" var="f">
                                                                <div class='filaespecial col-md-12 filaespecial_${i}_${f} ' id="filaespecial_${i}_${f}">
                                                                    <div class='col-md-5'>
                                                                        <label for="nombre" class="control-label">*Nombre de Fila Especial</label>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-12">
                                                                                <div class="input-group">
                                                                                    <input type="text" maxlength="45" placeholder="Nombre" class="form-control" name="t_nombrefilaespecial_${i}_${f}" value="${diccionario.get(i).get('filasespeciales').get(f)}"
                                                                                           required
                                                                                           oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                           oninput="setCustomValidity('')" > 
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-5">
                                                                        <label for="especie" class="control-label"> *Tipo del Campo</label>
                                                                        <div class="form-group">
                                                                            <div class="col-sm-12">
                                                                                <div class="input-group">
                                                                                    <select id="tipocampofilaespecial_${i}_${f}" class="select2" name="t_tipocampofilaespecial_${i}_${f}"
                                                                                            style='background-color: #fff;' required
                                                                                            oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                            onchange="setCustomValidity('')">
                                                                                        <option value=''></option>
                                                                                        <c:if test="${diccionario.get(i).get('tipofilasespeciales').get(f).equals('promedio')}">
                                                                                            <option value="promedio" selected="">Promedio</option>
                                                                                            <option value="sumatoria">Sumatoria</option>
                                                                                        </c:if>
                                                                                        <c:if test="${diccionario.get(i).get('tipofilasespeciales').get(f).equals('sumatoria')}">
                                                                                            <option value="promedio">Promedio</option>
                                                                                            <option value="sumatoria" selected>Sumatoria</option>
                                                                                        </c:if>

                                                                                    </select>
                                                                                </div>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                    <div class="col-md-2">
                                                                        <br>
                                                                        <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('filaespecial_${i}_${f}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                                                    </div>
                                                                </div>
                                                            </c:forEach>
                                                        </c:if>
                                                    </div>
                                                    <div class='col-md-12 form-group'>
                                                        <button type="button" onclick="agregarFilaEspecial('${i}')" class="btn btn-primary"><i class="fa fa-plus-circle"></i> Agregar Fila Especial</button>
                                                    </div>
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
                                <button type="button" onclick="agregarTabla()" class="btn btn-primary"><i class="fa fa-plus-square"></i> Agregar Tabla</button>
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
                    <button type="button" class="btn btn-primary" onclick="agregarAnalisis()"><i class="fa fa-check-circle"></i> ${accion} Análisis</button>
                </c:otherwise>
            </c:choose>    
        </div>
    </div>


</form>
