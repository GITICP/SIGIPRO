<%-- 
    Document   : EditarSerpientes
    Created on : Mar 26, 2015, 12:39:00 AM
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
            <li class="active"> Agregar Serpientes a Extracción </li>

          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-bug"></i> Agregar Serpientes a la Extracción ${numero_extraccion}</h3>
            </div>
            ${mensaje}
            <div class="widget-content">

             <form class="form-horizontal" id="agregarSerpiente" name="agregarSerpiente" >
                 <input hidden="true" name="accion" value="${accion}">
                    <input id="id_extraccion" hidden="true" name="id_extraccion" value="${id_extraccion}">
                    <div class="col-md-12">
                    <label for="usuarios" class="control-label">*Serpientes</label>
                    <div class="form-group">
                            <div class="col-sm-12">
                                <div class="input-group"id='inputGroupSeleccionSerpiente'>
                                    <select id="seleccionSerpiente" class="select2" name="serpientes_extraccion"
                                                style='background-color: #fff;' required
                                                oninvalid="setCustomValidity('Este campo es requerido')"
                                                onchange="setCustomValidity('')">
                                            <option value=''></option>
                                            <c:forEach items="${serpientes}" var="serpiente">
                                                <option value=${serpiente.getId_serpiente()}>${serpiente.getId_serpiente()}</option>
                                            </c:forEach>
                                    </select>
                                    <div><br></div>
                                    <button id="btn-agregarSerpiente" type="button" class="btn btn-primary" onclick="setSerpiente()"><i class="fa fa-check-circle"></i> Agregar Serpiente</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    </form>
                    <form class="form-horizontal" id="modificarSerpiente" name="modificarSerpiente" autocomplete="off" method="post" action="Extraccion">
                       <input hidden="true" name="accion" value="${accion}">
                        <input id="serpientes" hidden="true" name="serpientes" value="">
                        <input id="id_extraccion" hidden="true" name="id_extraccion" value="${id_extraccion}">
                        <div class="col-md-12">
                            <!-- Esta parte es la de los interno del catalogo externo -->
                            <div class="widget widget-table">
                              <div class="widget-header">
                                <h3><i class="fa fa-check"></i> Serpientes Asociadas</h3>
                              </div>
                              <div class="widget-content">
                                <table id="datatable-column-filter-permisos" class="table table-sorting table-striped table-hover datatable">
                                  <thead>
                                    <tr>
                                      <th>Serpiente</th>
                                      <th>Talla Cabeza (metros)</th>
                                      <th>Talla Cola (metros)</th>
                                      <th>Peso (gramos)</th>
                                      <th>Sexo</th>
                                      <th>Eliminar</th>
                                    </tr>
                                  </thead>
                                  <tbody>

                                  </tbody>
                                </table>
                              </div>
                            </div>
                            <p>
                              Los campos marcados con * son requeridos.
                            </p> 
                              <!-- /main -->
                            
                  <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cancelar</button>
                        <button type="submit" class="btn btn-primary" onclick="confirmacionAgregarSerpientes()"><i class="fa fa-check-circle"></i> Actualizar Serpientes</button>
                    </div>
                  </div>

                </div>
                </form>
                </div>
                

            </div>
          </div>
            
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
    


  </jsp:attribute>

</t:plantilla_general>
