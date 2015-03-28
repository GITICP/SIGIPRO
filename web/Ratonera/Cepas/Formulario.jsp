<%-- 
    Document   : Formulario
    Created on : Mar 28, 2015, 10:58:00 AM
    Author     : Amed
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<form id="formCepa" class="form-horizontal" autocomplete="off" method="post" action="Cepas">
  <div class="col-md-12">
    <input hidden="true" name="id_cepa" value="${cepa.getId_cepa()}">
    <input hidden="true" name="accion" value="${accion}">
  </div>
  <div class="col-md-12">
    <label for="nombre" class="control-label">*Nombre</label>
    <div class="form-group">
      <div class="col-md-6">
        <div class="input-group">
          <input id="nombre" placeholder="Nombre" type="text"  class="form-control" name="nombre" value="${cepa.getNombre()}"required 
                 oninvalid="setCustomValidity('Este campo es Requerido')"
                 oninput="setCustomValidity('')"> 
        </div>
      </div>
    </div>  
  </div>
  <div class="col-md-12">
    <label for="macho_as" class="control-label">*Ascendencia Macho</label>
    <div class="form-group">
      <div class="col-md-6">
        <div class="input-group">
          <input id="macho_as" placeholder="Ascendencia Macho" type="text"  class="form-control" name="macho_as" value="${cepa.getMacho_as()}"required 
                 oninvalid="setCustomValidity('Este campo es Requerido')"
                 oninput="setCustomValidity('')"> 
        </div>
      </div>
    </div>  
  </div>
  <div class="col-md-12">
    <label for="hembra_as" class="control-label">*Ascendencia Hembra</label>
    <div class="form-group">
      <div class="col-md-6">
        <div class="input-group">
          <input id="hembra_as" placeholder=" Ascendencia Hembra" type="text"  class="form-control" name="hembra_as" value="${cepa.getHembra_as()}"required 
                 oninvalid="setCustomValidity('Este campo es Requerido')"
                 oninput="setCustomValidity('')"> 
        </div>
      </div>
    </div>  
  </div>


  <div class="widget widget-table">
    <div class="widget-header">
      <h3><i class="fa fa-calendar"></i> Fechas de la Cepa</h3>
    </div>
    <div class="widget-content">
      <div class="col-md-6">
        <label for="cantidad" class="control-label"><strong>*Fechas de Apareamiento</strong></label>
        <div class="form-group">
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha de Inicio:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_apai" value="${cepa.getFecha_apareamiento_i_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_apai" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha Final:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_apaf" value="${cepa.getFecha_apareamiento_f_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_apaf" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
        </div>  
      </div>
      <div class="col-md-6">
        <label for="cantidad" class="control-label"><strong>*Fechas de Eliminación del Macho</strong></label>
        <div class="form-group">
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha de Inicio:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_elimi" value="${cepa.getFecha_eliminacionmacho_i_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_elimi" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha Final:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_elimf" value="${cepa.getFecha_eliminacionmacho_f_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_elimf" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
        </div>  
      </div>
      <div class="col-md-6">
        <label for="cantidad" class="control-label"><strong>*Fechas de Eliminación de la Hembra</strong></label>
        <div class="form-group">
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha de Inicio:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_elihi" value="${cepa.getFecha_eliminacionhembra_i_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_elihi" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha Final:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_elihf" value="${cepa.getFecha_eliminacionhembra_f_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_elihf" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
        </div>  
      </div>
      <div class="col-md-6">
        <label for="cantidad" class="control-label"><strong>*Fechas de Selección de Machos y Hembras</strong></label>
        <div class="form-group">
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha de Inicio:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_selni" value="${cepa.getFecha_seleccionnuevos_i_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_selni" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha Final:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_selnf" value="${cepa.getFecha_seleccionnuevos_f_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_selnf" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
        </div>  
      </div>
      <div class="col-md-6">
        <label for="cantidad" class="control-label"><strong>*Fechas de Reposición del Ciclo</strong></label>
        <div class="form-group">
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha de Inicio:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_repoi" value="${cepa.getFecha_reposicionciclo_i_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_repoi" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Fecha Final:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_repof" value="${cepa.getFecha_reposicionciclo_f_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_repof" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
        </div>  
      </div>
      <div class="col-md-6">
        <label for="cantidad" class="control-label"><strong>*Fecha Límite de Vigencia</strong></label>
        <div class="form-group">
          <div class="col-sm-6">
            <div class="input-group">
              <label for="cantidad" class="control-label">Vigente Hasta:</label>
              <input  type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="fecha_vigencia" value="${cepa.getFecha_vigencia_S()}" class="form-control sigiproDatePickerEspecial" name="fecha_vigencia" data-date-format="dd/mm/yyyy" required
                      oninvalid="setCustomValidity('Este campo es requerido ')"
                      onchange="setCustomValidity('')">   
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <hr>
  <div class="col-md-12">              
    <p>
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
            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Cepa</button>
          </c:otherwise>
        </c:choose>
      </div>
    </div>
  </div>

</form>

