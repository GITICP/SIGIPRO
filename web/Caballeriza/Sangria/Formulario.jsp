<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form class="form-horizontal" autocomplete="off" method="post" action="Sangria">
    <div class="col-md-6">
        <input hidden="true" name="id_sangria" value="${sangria.getId_sangria()}">
        <input hidden="true" name="accion" value="${accion}">
        <label for="responsable" class="control-label">Responsable</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <select id="seleccionResponsable" class="select2" name="responsable" 
                            style='background-color: #fff;' required
                            oninvalid="setCustomValidity('Por favor seleccione el responsable del evento')"
                            onchange="setCustomValidity('')">
                        <option value=''></option>
                        <c:forEach items="${usuarios_cab}" var="usuario">
                            <c:choose>
                                <c:when test="${usuario.getId_usuario() == sangria.getResponsable().getId_usuario()}" >
                                    <option value="${usuario.getId_usuario()}" selected> ${usuario.getNombre_completo()}</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${usuario.getId_usuario()}"> ${usuario.getNombre_completo()}</option>
                                </c:otherwise>
                            </c:choose>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <label for="responsable" class="control-label">Potencia</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="number" step="any" placeholder="" class="form-control" name="potencia" 
                           value="${sangria.getPotencia()}" oninput="setCustomValidity(\'\')" 
                           oninvalid="setCustomValidity(\'Ingrese solo números\')">
                </div>
            </div>
        </div>
        <label for="responsable" class="control-label">Volumen Plasma Total</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="number" step="any" placeholder="" class="form-control" name="volumen_plasma" 
                           value="${sangria.getVolumen_plasma_total()}" oninput="setCustomValidity(\'\')" 
                           oninvalid="setCustomValidity(\'Ingrese solo números\')">
                </div>
            </div>
        </div>
        <label for="potencia" class="control-label">Número de Informe de Control de Calidad</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="number" step="any" placeholder="" class="form-control" name="num_inf_cc" 
                           value="${sangria.getNum_inf_cc()}" oninput="setCustomValidity(\'\')" 
                           oninvalid="setCustomValidity(\'Ingrese solo números\')">
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-6">
        <label for="grupo" class="control-label">*Grupo de Caballos</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose >
                        <c:when test="${accion == 'Agregar'}">
                            <select id="seleccionInoculoGrupo" class="select2" name="grupo"
                                    style="background-color: #fff" required
                                    oninvalid="setCustomValidity('Por favor seleccione el grupo de caballos.')"
                                    onchange="setCustomValidity('')">
                                <option value=""></option>
                                <c:forEach items="${lista_grupos}" var="grupo">
                                    <option value ="${grupo.getId_grupo_caballo()}">${grupo.getNombre()}</option>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <input value="${sangria.getGrupo().getNombre()}" readonly="true" class="form-control">
                            <input name="grupo" hidden="true" value="${sangria.getGrupo().getId_grupo_caballo()}">
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
        <c:choose >
            <c:when test="${accion == 'Agregar'}">
                <c:forEach items="${lista_grupos}" var="grupo_caballo">
                    <div class="widget widget-table cuadro-opciones caballos-grupo" id="grupo-${grupo_caballo.getId_grupo_caballo()}" hidden>
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> Caballos del Grupo ${grupo_caballo.getNombre()} para la sangría</h3>
                            <div class="widget-header-toolbar">
                                <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Marcar Todos</a>
                            </div>
                        </div>
                        <div class="widget-content">
                            <c:forEach items="${grupo_caballo.getCaballos()}" var="caballo">
                                <div class="col-md-4">
                                    <label class="fancy-checkbox">
                                        <input type="checkbox" value="${caballo.getId_caballo()}" name="caballos">
                                        <span>${caballo.getNombre()} (${caballo.getNumero()}) </span>
                                    </label>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <c:choose >
                    <c:when test="${sangria.getFecha_dia1() == null}">
                        <div class="widget widget-table cuadro-opciones caballos-grupo" id="grupo-${sangria.getGrupo().getId_grupo_caballo()}">
                            <div class="widget-header">
                                <h3><i class="fa fa-flask"></i> Caballos del grupo ${sangria.getGrupo().getNombre()} </h3>
                                <div class="widget-header-toolbar">
                                    <a class="btn btn-primary btn-sm boton-accion seleccionar-todo">Desmarcar Todos</a>
                                </div>
                            </div>
                            <div class="widget-content">
                                <c:forEach items="${sangria.getGrupo().getCaballos()}" var="caballo">
                                    <div class="col-md-4">
                                        <label class="fancy-checkbox">
                                            <input type="checkbox" value="${caballo.getId_caballo()}" name="caballos" ${(sangria.valididarCaballoEnSangria(caballo)) ? "checked" : ""}>
                                            <span>${caballo.getNombre()} (${caballo.getNumero()}) </span>
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="widget widget-table cuadro-opciones caballos-grupo" id="grupo-${sangria.getGrupo().getId_grupo()}">
                            <div class="widget-header">
                                <h3><i class="fa fa-flask"></i> Caballos del grupo (No modificable debido a que la sangría ya comenzó)</h3>
                            </div>
                            <div class="widget-content">
                                <c:forEach items="${sangria.getGrupo().getCaballos()}" var="caballo">
                                    <div class="col-md-4">
                                        <label class="fancy-checkbox">
                                            <input type="checkbox" value="${caballo.getId_caballo()}" name="caballos" disabled ${(sangria.valididarCaballoEnSangria(caballo)) ? "checked" : ""}>
                                            <span>${caballo.getNombre()} (${caballo.getNumero()}) </span>
                                        </label>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
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
                        <c:when test= "${accion.equals('Editar')}">
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Sangría</button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div> 
    </div>
</form>