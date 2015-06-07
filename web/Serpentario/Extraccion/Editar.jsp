<%-- 
    Document   : Editar
    Created on : May 19, 2015, 10:53:16 AM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Serpentario" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Serpentario</li>
            <li> 
              <a href="/SIGIPRO/Serpentario/Extraccion?">Extracciones</a>
            </li>
            <li class="active"> Extracción ${extraccion.getNumero_extraccion()} </li>

          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-tint"></i> Editar Extracción </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
            <form class="form-horizontal" autocomplete="off" method="post" action="Extraccion">
                <div class="col-md-6">
            <input hidden="true" name="id_extraccion" value="${extraccion.getId_extraccion()}">
            <input hidden="true" name="accion" value="${accion}">
            <label for="genero" class="control-label">Número de Extracción</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group">
                    <input type="text" maxlength="45" disabled="true" placeholder="Número de la Extracción. Ex: 01-2015-Coral" class="form-control" name="numero_extraccion" value="${extraccion.getNumero_extraccion()}"> 
                </div>
              </div>
            </div>
            <label for="observaciones" class="control-label">*Volumen Extraído (mL)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input name="volumen_extraido" id="volumen_extraido" type="number" min="0" step="any" placeholder="Número de mL extraídos" class="form-control" value="${extraccion.getVolumen_extraido()}" required
                             oninvalid="setCustomValidity('El volumen extraído debe ser un número mayor a 0. ')"
                            oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
                </div>
                <div class="col-sm-6">
                <label for="observaciones" class="control-label">*Volumen Recuperado (mL)</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input name="volumen_recuperado" id="volumen_recuperado" type="number" step="any" min="0" placeholder="Número de mL recuperados" class="form-control" value="${extraccion.getCentrifugado().getVolumen_recuperado()}" required
                            oninvalid="setCustomValidity('El volumen recuperado debe ser un número mayor a 0. ')"
                            oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
            <label for="peso_recuperado" class="control-label">*Peso recuperado (G)</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group">
                  <input name="peso_recuperado" id="peso_recuperado" type="number" step="any" min="0" placeholder="Número de G recuperados" class="form-control" value="${extraccion.getLiofilizacion().getPeso_recuperado()}" required
                         oninvalid="setCustomValidity('El peso recuperado debe ser un número mayor a 0. ')"
                        oninput="setCustomValidity('')">
                </div>
              </div>
            </div>
                </div>
           <div class="col-md-12">
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
                            <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> ${accion} Extraccion</button>
                        </c:otherwise>
                    </c:choose>    
            </div>
          </div>


        </div>                  
                         
            </form>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>

  </jsp:attribute>

</t:plantilla_general>

