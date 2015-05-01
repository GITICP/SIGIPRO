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
        <label for="responsable" class="control-label">*Responsable</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <input type="text" placeholder="Responsable Sangría" class="form-control" name="responsable" required
                           value="${sangria.getResponsable()}" oninvalid="setCustomValidity('Este campo es requerido ')"
                           onchange="setCustomValidity('')">
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
        <label for="sangria_prueba" class="control-label">*Sangría de Prueba</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose >
                        <c:when test="${accion == 'Agregar'}">
                            <select id="seleccion-sangria-prueba" class="select2" name="sangria_prueba"
                                    style="background-color: #fff" required
                                    oninvalid="setCustomValidity('Por favor seleccione la sangría de prueba.')"
                                    onchange="setCustomValidity('')">
                                <option value=""></option>
                                <c:forEach items="${sangrias_prueba}" var="sangria_prueba">
                                    <option value="${sangria_prueba.getId_sangria_prueba()}">${sangria_prueba.getId_sangria_prueba()}</option>
                                </c:forEach>
                            </select>
                        </c:when>
                        <c:otherwise>
                            <input class="form-control" name="sangria_prueba" value="${sangria.getSangria_prueba().getId_sangria_prueba()}" readonly>
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
        <c:choose >
            <c:when test="${accion == 'Agregar'}">
                <c:forEach items="${sangrias_prueba}" var="sangria_prueba">
                    <div class="widget widget-table cuadro-opciones caballos-prueba" id="prueba-${sangria_prueba.getId_sangria_prueba()}" hidden>
                        <div class="widget-header">
                            <h3><i class="fa fa-flask"></i> Caballos de la sangría de prueba </h3>
                        </div>
                        <div class="widget-content">
                            <c:forEach items="${sangria_prueba.getCaballos()}" var="caballo">
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
                        <div class="widget widget-table cuadro-opciones caballos-prueba" id="prueba-${sangria.getSangria_prueba().getId_sangria_prueba()}">
                            <div class="widget-header">
                                <h3><i class="fa fa-flask"></i> Caballos de la sangría de prueba </h3>
                            </div>
                            <div class="widget-content">
                                <c:forEach items="${sangria.getSangria_prueba().getCaballos()}" var="caballo">
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
                        <div class="widget widget-table cuadro-opciones caballos-prueba" id="prueba-${sangria.getSangria_prueba().getId_sangria_prueba()}">
                            <div class="widget-header">
                                <h3><i class="fa fa-flask"></i> Caballos de la sangría de prueba (No modificable debido a que la sangría ya comenzó)</h3>
                            </div>
                            <div class="widget-content">
                                <c:forEach items="${sangria.getSangria_prueba().getCaballos()}" var="caballo">
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