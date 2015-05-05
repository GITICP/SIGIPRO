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
    <form method="POST" action="Extraccion" id="form-terminarEdicion">
                                    <input hidden="true" name="accion" value="terminar">
                                    <input hidden="true" name="id_extraccion" value="${id_extraccion}">
    </form>


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
                                                <option value=${serpiente.getId_serpiente()}>${serpiente.getNumero_serpiente()}</option>
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
                                      <th>Talla Cabeza (cm)</th>
                                      <th>Talla Cola (cm)</th>
                                      <th>Peso (g)</th>
                                      <th>Sexo</th>
                                      <th>Eliminar</th>
                                      <th>Estado</th>
                                    </tr>
                                  </thead>
                                  <tbody>
                                      <c:forEach items="${serpientesextraccion}" var="serpiente">
                                          <tr id="${serpiente.getId_serpiente()}">
                                              <td width=50px>${serpiente.getNumero_serpiente()}</td> 
                                              <td width=150px><input type="number" step="any" placeholder="" min="0" class="form-control" name="talla_cabeza_${serpiente.getId_serpiente()}" value="" oninput="setCustomValidity('')" oninvalid="setCustomValidity('Ingrese solo números mayores a 0')"></td>
                                              <td width=150px><input type="number" step="any" placeholder="" min="0" class="form-control" name="talla_cola_${serpiente.getId_serpiente()}" value="" oninput="setCustomValidity('')" oninvalid="setCustomValidity('Ingrese solo números mayores a 0')"></td>
                                              <td width=150px><input type="number" step="any" placeholder="" min="0" class="form-control" name="peso_${serpiente.getId_serpiente()}" value="" oninput="setCustomValidity('')" oninvalid="setCustomValidity('Ingrese solo números mayores a 0')"></td>
                                              <td width=150px><select id="seleccionSexo" name="sexo_${serpiente.getId_serpiente()}" style="background-color: #fff;">
                                                    <option value=''></option>
                                                    <option value='Macho'>Macho</option>
                                                    <option value='Hembra'>Hembra</option>
                                                    <option value='Indefinido'>Indefinido</option> 
                                                  </select>
                                              </td>
                                              <td width=50px><button type="button" id="boton_eliminar" disabled="true" class="btn btn-danger btn-sm" onclick="eliminarSerpiente(' + id_serpiente + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button></td>
                                              <td width=50px><div class="estado">Guardado</div></td>
                                          </tr>
                                      </c:forEach>

                                  </tbody>
                                </table>
                              </div>
                                <button style='float:right;' type="button" class="btn btn-primary" onclick="confirmacionAgregarSerpientes()"><i class="fa fa-check-circle"></i> Guardar Serpientes</button>
                            </div>
                            <p>
                              Los campos marcados con * son requeridos.
                            </p> 
                              <!-- /main -->
                            
                  <div class="form-group">
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger btn-volver"><i class="fa fa-times-circle"></i> Cerrar Edición</button>
                        <a class="btn btn-primary confirmable-form" data-form-id="form-terminarEdicion" data-texto-confirmacion="terminar la edición de Serpientes a la Extracción"><i class="fa fa-check-circle"></i> Terminar Edición</a>
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
