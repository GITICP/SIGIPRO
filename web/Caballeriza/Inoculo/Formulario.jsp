<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<form class="form-horizontal" autocomplete="off" method="post" action="Inoculo">
    <div class="col-md-6">
        <input hidden="true" name="id_inoculo" value="${inoculo.getId_inoculo()}">
        <input hidden="true" name="accion" value="${accion}">
        <div>
        <label for="formulainoculo" class="control-label">Fórmula de Inyección:</label>
        </div>
        <label for="mnn" class="control-label">M.n.n</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getMnn()==null}">
                            <input type="text" placeholder="2mg" class="form-control" name="mnn"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="mnn" value="${inoculo.getMnn()}"> 
                            <input hidden="true" name="mnn" value="${inoculo.getMnn()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="baa" class="control-label">B.a -A</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getBaa()==null}">
                            <input type="text" placeholder="2mg" class="form-control" name="baa"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="baa" value="${inoculo.getBaa()}"> 
                            <input hidden="true" name="baa" value="${inoculo.getBaa()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div> 
        <label for="bap" class="control-label">B.a -P</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getBap()==null}">
                            <input type="text" placeholder="2mg" class="form-control" name="bap"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="bap" value="${inoculo.getBap()}"> 
                            <input hidden="true" name="bap" value="${inoculo.getBap()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div> 
        <label for="cdd" class="control-label">C.d.d</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getCdd()==null}">
                            <input type="text" placeholder="2mg" class="form-control" name="cdd"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="cdd" value="${inoculo.getCdd()}"> 
                            <input hidden="true" name="cdd" value="${inoculo.getCdd()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div> 
        <label for="lms" class="control-label">L.m.s</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getLms()==null}">
                            <input type="text" placeholder="2mg" class="form-control" name="lms"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="lms" value="${inoculo.getLms()}"> 
                            <input hidden="true" name="lms" value="${inoculo.getLms()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div> 
        <label for="tetox" class="control-label">Tetox</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getTetox()==null}">
                            <input type="text" placeholder="2mg" class="form-control" name="tetox"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="tetox" value="${inoculo.getTetox()}"> 
                            <input hidden="true" name="tetox" value="${inoculo.getTetox()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div> 
        <label for="otro" class="control-label">Otro</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getOtro()==null}">
                            <input type="text" placeholder="2mg" class="form-control" name="otro"
                                   oninput="setCustomValidity('')"> 
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="otro" value="${inoculo.getOtro()}"> 
                            <input hidden="true" name="otro" value="${inoculo.getOtro()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>         
  </div>
    <div class="col-md-6">

        <br>
        <label for="encargado_preparacion" class="control-label">*Encargado de Preparación</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getEncargado_preparacion()==null}">
                            <input type="text" placeholder="Persona encargada de la preparación" class="form-control" name="encargado_preparacion" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="encargado_preparacion" value="${inoculo.getEncargado_preparacion()}" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')"> 
                            <input hidden="true" name="encargado_preparacion" value="${inoculo.getEncargado_preparacion()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <label for="encargado_inyeccion" class="control-label">*Encargado de Inyección</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getEncargado_inyeccion()==null}">
                            <input type="text" placeholder="Persona encargada de la preparación" class="form-control" name="encargado_inyeccion" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" name="encargado_inyeccion" value="${inoculo.getEncargado_inyeccion()}"  required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')"> 
                            <input hidden="true" name="encargado_inyeccion" value="${inoculo.getEncargado_inyeccion()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div> 
        <label for="fecha" class="control-label">*Fecha</label>
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                        <c:when test="${inoculo.getFecha()==null}">
                            <input type="text" value="${helper.getFecha_hoy()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                        </c:when>
                        <c:otherwise>
                            <input type="text"  value="${inoculo.getFechaAsString()}" pattern="\d{1,2}/\d{1,2}/\d{4}" id="datepicker" class="form-control sigiproDatePicker" name="fecha" data-date-format="dd/mm/yyyy" required
                                   oninvalid="setCustomValidity('Este campo es requerido ')"
                                   onchange="setCustomValidity('')">
                            <input hidden="true" name="fecha" value="${inoculo.getFechaAsString()}">
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>        
    </div>

    <div class="col-md-12">
        <!-- Esta parte es la de los permisos de un rol -->
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

</div>
</form>