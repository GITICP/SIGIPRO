<%-- 
    Document   : Formulario
    Created on : 21-ene-2015, 20:28:28
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<form class="form-horizontal" autocomplete="off" method="post" action="GrupoDeCaballos">
    <div class="row">
        <div class="col-md-6">
            <input hidden="true" name="id_grupo_de_caballo" value="${grupodecaballos.getId_grupo_caballo()}">
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
    <div class="widget widget-table">
        <div class="widget-header">
            <h3><i class="fa fa-check"></i> Caballos del Grupo Asociados</h3>
            <div class="btn-group widget-header-toolbar">
                <a class="btn btn-primary btn-sm boton-accion" data-toggle="modal" data-target="#modalAgregarCaballo">Agregar</a>
            </div>
        </div>
        <div class="widget-content">
            <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                <thead>
                    <tr>
                        <th>Nombre y Numero de Microchip</th>
                        <th>Eliminar</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${caballos}" var="caballo">
                        <tr id="${caballo.getId_caballo()}">
                            <td>${caballo.getNombre()} (${caballo.getNumero_microchip()})</td>
                            <td>
                                <button type="button" class="btn btn-danger btn-sm boton-accion" onclick="eliminarCaballoDeGrupos(${caballo.getId_caballo()})">Eliminar</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!-- Esta parte es la de los permisos de un rol -->
    <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>

    <div class="form-group">
        <div class="modal-footer">
            <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Grupo de Caballos</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>


</form>