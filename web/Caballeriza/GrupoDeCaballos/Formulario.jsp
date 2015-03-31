<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form id="grupodecaballosForm" class="form-horizontal" autocomplete="off" method="post" action="GrupoDeCaballos">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_grupo_de_caballo" value="${grupodecaballos.getId_grupo_caballo()}">
            <input id="caballos" hidden="true" name="listaCaballos" value="">
            <input id="ids-caballos" hidden="true" name="ids_caballos" value="">
            <input hidden="true" name="accion" value="${accion}">
            <label for="nombre" class="control-label">* Nombre del Grupo de Caballos</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input type="text" maxlength="45" placeholder="AF-1" class="form-control" name="nombre" value="${grupodecaballos.getNombre()}"
                               required
                               oninvalid="setCustomValidity('Este campo es requerido ')"
                               oninput="setCustomValidity('')" > 
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">                     
            <label for="descripcion" class="control-label">Descripción</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea rows="5" cols="50" maxlength="500" placeholder="Descripción" class="form-control" name="descripcion" >${grupodecaballos.getDescripcion()}</textarea>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-md-12">
        <!-- Esta parte es la de los caballos del grupo -->
        <div class="widget widget-table">
            <div class="widget-header">
                <h3><i class="fa fa-check"></i> Caballos del Grupo de Caballos Asociados</h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarCaballo">Agregar</a>
                </div>
            </div>
            <div class="widget-content">
                <table id="caballos-grupo" class="table table-sorting table-striped table-hover datatable">
                    <thead>
                        <tr>
                            <th>Nombre y Número de Microchip</th>
                            <th>Eliminar</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${caballos}" var="caballo">
                            <tr id="caballo-${caballo.getId_caballo()}">
                                <td>${caballo.getNombre()} (${caballo.getNumero_microchip()})</td>
                                <td>
                                    <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarCaballo(${caballo.getId_caballo()})">Eliminar</button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <p>                    
        <!-- Esta parte es la de los permisos de un rol -->
        <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </p>
    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary" onclick="confirmacionAgregarGrupo()"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Grupo de Caballos</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</form>

<t:modal idModal="modalAgregarCaballo" titulo="Asociar Caballo">

    <jsp:attribute name="form">

        <form name="form-Caballo-Grupo" id="form-Caballo-Grupo" class="form-horizontal">
            <input type="text" name="grupodecaballos"  hidden="true">
            <label for="idcaballo" class="control-label">Seleccione un caballo:</label>
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group" id='inputGroupSeleccionCaballo'>
                        <select id="seleccioncaballo" class="select2" style='background-color: #fff;' name="idcaballo" required
                                oninvalid="setCustomValidity('Este campo es requerido ')"
                                onchange="setCustomValidity('')">
                            <option value=''></option>
                            <c:forEach items="${caballos_restantes}" var="caballo">
                                <option value=${caballo.getId_caballo()}>${caballo.getNombre()} (${caballo.getNumero_microchip()})</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </div>
            <div class="form-group">
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                    <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarCaballo()"><i class="fa fa-check-circle"></i> Asociar Caballo</button>
                </div>
            </div>
        </form>

    </jsp:attribute>

</t:modal>              