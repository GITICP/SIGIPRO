<%-- 
    Document   : Formulario
    Created on : Dic 04, 2015, 9:10:38 AM
    Author     : jespinoza
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form class="form-horizontal" autocomplete="off" method="post" action="Cronograma">
  <div class="row">
    <div class="col-md-6">
      <input hidden="true" name="id_cronograma" value="${cronograma.getId_cronograma()}">
      <input hidden="true" name="accion" value="${accion}">
            <label for="cronograma" class="control-label"> *Nombre</label>
        <!-- Nombre -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <input id="nombre" type="text" class="form-control" name="nombre" value="${cronograma.getNombre()}" required
                            oninvalid="setCustomValidity('Debe ingresar un nombre. ')"
                            oninput="setCustomValidity('')">
                    </div>
                </div>
            </div>
        <label for="observaciones" class="control-label"> Observaciones</label>
            <!-- Observaciones -->
            <div class="form-group">
                <div class="col-sm-12">
                    <div class="input-group">
                        <textarea style="font-size: 14px; padding: 6px 12px; width: 100%;" name="observaciones" id="observaciones" rows="5">${cronograma.getObservaciones()}</textarea>
                    </div>
                </div>
            </div>
        </div>
        <div class="col-md-6">
        <label for="valido_desde" class="control-label"> *Válido desde</label>
        <!-- Válido desde -->
        <div class="form-group">
            <div class="col-sm-12">
                <div class="input-group">
                    <c:choose>
                      <c:when test="${accion == 'Agregar'}" >
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="valido_desde" class="form-control sigiproDatePickerEspecial" name="valido_desde" data-date-format="dd/mm/yyyy" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')"> 
                        <script>
                            var today = new Date();
                            var dd = today.getDate();
                            var mm = today.getMonth()+1; //January is 0!

                            var yyyy = today.getFullYear();
                            if(dd<10){
                                dd='0'+dd
                            } 
                            if(mm<10){
                                mm='0'+mm
                            } 
                            var today = dd+'/'+mm+'/'+yyyy;
                            document.getElementById("valido_desde").value = today;
                        </script>
                      </c:when>
                      <c:otherwise>
                        <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="valido_desde" value="${cronograma.getValido_desde_S()}" class="form-control sigiproDatePickerEspecial" name="valido_desde" data-date-format="dd/mm/yyyy" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')"> 
                      </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
      <span class="campos-requeridos">Los campos marcados con * son requeridos.</span>
    </div>
  </div>

  <div class="form-group">
    <div class="modal-footer">
      <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
            <c:choose>
                <c:when test= "${accion.equals('Editar')}">
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Guardar Cambios</button>
                </c:when>
                <c:otherwise>
                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Cronograma de Producción</button>
                </c:otherwise>
            </c:choose>    </div>
  </div>


</form>