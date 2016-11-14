<%-- 
    Document   : Formulario
    Created on : Jul 3, 2015, 8:43:02 AM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" id="agregarActividad" autocomplete="off" enctype='multipart/form-data' method="post" action="Actividad_Apoyo">
    <div class="row">

        <input hidden="true" name="id_actividad" value="${actividad.getId_actividad()}">
        <input hidden="true" name="accion" value="${accion}">
        <input hidden="true" name="orden" id="orden" value="${lista.toString().replace("]","").replace("[","").replace(" ","")}">
        <input hidden="true" name="contador" id="contador" value="${contador}">
        <input hidden="true" name="cantidad" id="cantidad" value="${cantidad}">
        <input hidden="true" name="version" id="version" value="${actividad.getVersion()}">
        <input hidden="true" id='listaSecciones' name='listaSecciones' value='${listaSecciones}'>
        <input hidden="true" id='listaSubbodegas' name='listaSubbodegas' value='${listaSubbodegas}'>
        <div class="col-sm-12">
            <div class="col-md-5">
                <label for="nombre" class="control-label">*Nombre del Actividad de Apoyo</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <input type="text" maxlength="45" placeholder="Nombre del Actividad de Apoyo" class="form-control" name="nombre" value="${actividad.getNombre()}"
                                   required
                                   oninvalid="setCustomValidity('Este campo es requerido')"
                                   oninput="setCustomValidity('')" > 
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-5">
                <label for="categoria" class="control-label">*Categoría de Actividad de Apoyo</label>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <select id="seleccionFormula" class="select2" name="id_categoria_aa"
                                    style='background-color: #fff;' required
                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                    onchange="setCustomValidity('')">
                                <option value=''></option>
                                <c:forEach items="${categorias}" var="categoria">
                                    <c:choose>
                                        <c:when test="${categoria.getId_categoria_aa() == actividad.getCategoria().getId_categoria_aa()}" >
                                            <option value=${categoria.getId_categoria_aa()} selected> ${categoria.getNombre()}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value=${categoria.getId_categoria_aa()}>${categoria.getNombre()}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>

                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-2">
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <c:choose>
                                <c:when test="${actividad.isRequiere_coordinacion()}">
                                    <input checked id="requiere_coordinacion" type="checkbox" name="requiere_coordinacion" style="width:20px; height:20px; alignment-baseline: central"><span> *Requiere aprobación de Coordinación</span>
                                </c:when>
                                <c:otherwise>
                                    <input id="requiere_coordinacion" type="checkbox" name="requiere_coordinacion" style="width:20px; height:20px; alignment-baseline: central"><span> *Requiere aprobación de Coordinación</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-12">
                        <div class="input-group">
                            <c:choose>
                                <c:when test="${actividad.isRequiere_regencia()}">
                                    <input checked id="requiere_regencia" type="checkbox" name="requiere_regencia" style="width:20px; height:20px; alignment-baseline: central"><span> *Requiere aprobación de Regencia</span>
                                </c:when>
                                <c:otherwise>
                                    <input id="requiere_regencia" type="checkbox" name="requiere_regencia" style="width:20px; height:20px; alignment-baseline: central"><span> *Requiere aprobación de Regencia</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
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
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('text')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text" selected>Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('textarea')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea" selected>Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('fecha')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha" selected>Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('hora')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora" selected>Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('blanco')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco" selected>Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('imagen')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen" selected>Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('cc')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc" selected>Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('sangria')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria" selected>Referencia a Sangría</option>
                                                                    <option value="lote">Referencia a Lote de Producción</option>
                                                                </c:if>
                                                                <c:if test="${diccionario.get(i).get('tipo').equals('lote')}">
                                                                    <option value="number">Número</option>
                                                                    <option value="text">Campo de Texto</option>
                                                                    <option value="textarea">Area de Texto</option>
                                                                    <option value="fecha">Fecha</option>
                                                                    <option value="hora">Hora</option>
                                                                    <option value="blanco">Espacio en blanco</option>
                                                                    <option value="imagen">Imagen</option>
                                                                    <option value="cc">Referencia a Control de Calidad</option>
                                                                    <option value="sangria">Referencia a Sangría</option>
                                                                    <option value="lote" selected>Referencia a Lote de Producción</option>
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
                                    <c:choose>
                                        <c:when test="${diccionario.get(i).get('tipocampo').equals('checkbox')}">
                                            <div class="widget widget-table seleccion_${i}" id="${i}">
                                                <input hidden="true" id="elemento_${i}" value="seleccion">
                                                <div class="widget-header">
                                                    <h3><i class="fa fa-edit"></i> Selección Múltiple #${i}</h3>
                                                    <div class="btn-group widget-header-toolbar">
                                                        <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('seleccion_${i}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
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
                                                                    <input type="text" maxlength="45" placeholder="Nombre de la Opción" class="form-control" name="o_opcion1_${i}_1" value="${diccionario.get(i).get('opcion1')}"
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

                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${diccionario.get(i).get('tipocampo').equals('subbodega')}">
                                                    <div class="widget widget-table articulo_${i}" id="${i}">
                                                        <input hidden="true" id="elemento_${i}" value="articulo">
                                                        <input hidden="true" id="nombresub_${i}" name="a_nombresubbodega_${i}" value="${diccionario.get(i).get('nombresubbodega')}">
                                                        <div class="widget-header">
                                                            <h3><i class="fa fa-edit"></i> Artículo de SubBodega #${i}</h3>
                                                            <div class="btn-group widget-header-toolbar">
                                                                <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('articulo_${i}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                                            </div>
                                                        </div>
                                                        <div class="widget-content">
                                                            <div class="col-md-12">
                                                                <label for="tipo" class="control-label"> *Sub bodega</label>
                                                                <div class="form-group">
                                                                    <div class="col-sm-12">
                                                                        <div class="input-group">
                                                                            <select id="subbodega_${i}" class="select2" name="a_subbodega_${i}" onchange="actualizarNombre_subbodega('${i}')"  
                                                                                    style='background-color: #fff;' required
                                                                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                    onchange="setCustomValidity('')">
                                                                                <option value=''></option>
                                                                                <c:forEach items="${subbodegas}" var="subbodega">
                                                                                    <c:choose>
                                                                                        <c:when test="${subbodega.getId_sub_bodega() == diccionario.get(i).get('subbodega')}" >
                                                                                            <option value=${subbodega.getId_sub_bodega()} selected> ${subbodega.getNombre()}</option>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <option value=${subbodega.getId_sub_bodega()}> ${subbodega.getNombre()}</option>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <label for="nombre" class="control-label">*Nombre del Campo</label>
                                                                <div class="form-group">
                                                                    <div class="col-sm-12">
                                                                        <div class="input-group">
                                                                            <input type="text" maxlength="45" placeholder="Nombre del Campo" class="form-control" name="a_nombre_${i}" value="${diccionario.get(i).get('nombre')}" 
                                                                                   required
                                                                                   oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                   oninput="setCustomValidity('')" > 
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="form-group">
                                                                    <div class="col-sm-12">
                                                                        <div class="input-group"> <br>
                                                                            <c:choose>
                                                                                <c:when test="${diccionario.get(i).containsKey('cantidad')}">
                                                                                    <input id="cantidad_${i}" type="checkbox" checked name="a_cantidad_${i}" style="width:20px; height:20px;"><span> Con cantidades</span>
                                                                                </c:when>
                                                                                <c:otherwise>
                                                                                    <input id="cantidad_${i}" type="checkbox" name="a_cantidad_${i}" style="width:20px; height:20px;"><span> Con cantidades</span>
                                                                                </c:otherwise>
                                                                            </c:choose>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <div class="widget widget-table usuario_${i}" id="${i}">
                                                        <input hidden="true" id="elemento_${i}" value="usuario">
                                                        <input hidden="true" id="nombresec_${i}" name="u_nombreseccion_${i}" value="${diccionario.get(i).get('nombreseccion')}">
                                                        <div class="widget-header">
                                                            <h3><i class="fa fa-edit"></i> Grupo de Usuarios #${i}</h3>
                                                            <div class="btn-group widget-header-toolbar">
                                                                <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarCampo('usuario_${i}')" style="margin-left:7px;margin-right:5px;">Eliminar</button>
                                                            </div>
                                                        </div>
                                                        <div class="widget-content">
                                                            <div class="col-md-12">
                                                                <label for="tipo" class="control-label"> *Sección de Usuarios</label>
                                                                <div class="form-group">
                                                                    <div class="col-sm-12">
                                                                        <div class="input-group">
                                                                            <select id="seccion_${i}" class="select2" name="u_seccion_${i}" onchange="actualizarNombre_seccion('${i}')"  
                                                                                    style='background-color: #fff;' required
                                                                                    oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                    onchange="setCustomValidity('')">
                                                                                <option value=''></option>
                                                                                <c:forEach items="${secciones}" var="seccion">
                                                                                    <c:choose>
                                                                                        <c:when test="${seccion.getId_seccion() == diccionario.get(i).get('seccion')}" >
                                                                                            <option value=${seccion.getId_seccion()} selected> ${seccion.getNombre_seccion()}</option>
                                                                                        </c:when>
                                                                                        <c:otherwise>
                                                                                            <option value=${seccion.getId_seccion()}> ${seccion.getNombre_seccion()}</option>
                                                                                        </c:otherwise>
                                                                                    </c:choose>
                                                                                </c:forEach>
                                                                            </select>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <label for="nombre" class="control-label">*Nombre del Campo</label>
                                                                <div class="form-group">
                                                                    <div class="col-sm-12">
                                                                        <div class="input-group">
                                                                            <input type="text" maxlength="45" placeholder="Nombre del Campo" class="form-control" name="u_nombre_${i}" value="${diccionario.get(i).get("nombre")}"
                                                                                   required
                                                                                   oninvalid="setCustomValidity('Este campo es requerido')"
                                                                                   oninput="setCustomValidity('')" > 
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>

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
                                <button type="button" onclick="agregarUsuario()" class="btn btn-primary"><i class="fa fa-plus-square-o"></i> Agregar Grupo de Usuarios </button>
                                <button type="button" onclick="agregarSubbodega()" class="btn btn-primary"><i class="fa fa-plus-square-o"></i> Agregar Artículos de Subbodegas </button>
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
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Actividad de Apoyo</button>
                </c:otherwise>
            </c:choose>    
        </div>
    </div>


</form>
