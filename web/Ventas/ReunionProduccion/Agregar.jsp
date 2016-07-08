<%-- 
    Document   : Agregar
    Created on : Jun 29, 2015, 5:02:37 PM
    Author     : ld.conejo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/ReunionProduccion?">Reuniones de Producción</a>
            </li>
            <li class="active"> Agregar Reunión de Producción </li>

          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-list-alt"></i> Agregar Nueva Reunión de Producción </h3>
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

              <t:modal idModal="modalAgregarProducto" titulo="Agregar Participante">

      <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarProducto">
          <input type="text" name="participante"  hidden="true">
          <label for="id_participante" class="control-label">*Participante</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionProducto'>
                <select id="seleccionParticipante" class="select2" style='background-color: #fff;' name="seleccionParticipante" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
                    <option value=''></option>
                  <c:forEach items="${usuarios}" var="usuario">
                    <option value="${usuario.getId_usuario()}"> ${usuario.getNombre_completo()}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            <button id="btn-agregarProducto" type="button" class="btn btn-primary" data-target="#modalAgregarProducto" onclick="agregarProducto()"><i class="fa fa-check-circle"></i> Agregar Participante</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>
              <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Participantes_reunion.js"></script>
              
  </jsp:attribute>

</t:plantilla_general>

