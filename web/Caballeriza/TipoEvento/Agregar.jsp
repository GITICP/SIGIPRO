<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Caballeriza" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-8 ">
          <ul class="breadcrumb">
            <li>Caballeriza</li>
            <li> 
              <a href="/SIGIPRO/Caballeriza/TipoEvento?">Tipos de Eventos</a>
            </li>
            <li class="active"> Agregar Tipo De Evento</li>

          </ul>
        </div>
        <div class="col-md-4 ">
          <div class="top-content">

          </div>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-barcode"></i> Agregar Nuevo Tipo De Evento</h3>
            </div>
            ${mensaje}
            <div class="widget-content">

              <jsp:include page="Formulario.jsp"></jsp:include>

            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
      <t:modal idModal="modalAgregarEvento" titulo="Asociar Evento">

      <jsp:attribute name="form">

        <form name="form-Evento-Tipo" id="form-Evento-Tipo" class="form-horizontal">
          <input type="text" name="tipoevento"  hidden="true">
          <label for="idevento" class="control-label">Seleccione un evento:</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionEvento'>
                <select id="seleccionevento" class="select2" style='background-color: #fff;' name="idevento" required
                        oninvalid="setCustomValidity('Este campo es requerido ')"
                        onchange="setCustomValidity('')">
                  <option value=''></option>
                  <c:forEach items="${eventos_restantes}" var="pr">
                    <option value=${pr.getId_evento()}>${pr.getNombre()} </option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
          <div class="form-group">
            <div class="modal-footer">
              <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
              <button id="btn-agregarRol" type="button" class="btn btn-primary" onclick="agregarEvento()"><i class="fa fa-check-circle"></i> Asociar Evento</button>
            </div>
          </div>
        </form>

      </jsp:attribute>

    </t:modal>              

  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
  </jsp:attribute>
</t:plantilla_general>
