<%-- 
    Document   : Agregar
    Created on : Feb 11, 2016, 5:55:36 PM
    Author     : jespinozac95
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ventas" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Ventas</li>
            <li> 
              <a href="/SIGIPRO/Ventas/Tratamiento?">Tratamientos</a>
            </li>
            <li class="active"> Agregar Un Nuevo Tratamiento </li>

          </ul>
        </div>
        <div class="col-md-8 ">
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
              <h3><i class="fa fa-file-text-o"></i> Agregar Un Nuevo Tratamiento </h3>
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

    <t:modal idModal="modalAgregarAccion" titulo="Agregar Acción">

      <jsp:attribute name="form">

        <form class="form-horizontal" id="formAgregarAccion">
          <input type="text" name="accion"  hidden="true">
          <label for="id_accion" class="control-label">*Acción</label>
          <div class="form-group">
            <div class="col-sm-12">
              <div class="input-group" id='inputGroupSeleccionAccion'>
                <select id="seleccionAccion" class="select2" style='background-color: #fff;' name="seleccionAccion" required
                        oninvalid="setCustomValidity('Este campo es requerido')"
                        onchange="setCustomValidity('')">
                    <option value=''></option>
                  <c:forEach items="${acciones}" var="accion">
                    <option value="${accion.getId_accion()}"> ${accion.getAccion()}</option>
                  </c:forEach>
                </select>
              </div>
            </div>
          </div>
        </form>
        <div class="form-group">
          <div class="modal-footer">
            <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
            <button id="btn-agregarAccion" type="button" class="btn btn-primary" data-target="#modalAgregarAccion" onclick="agregarAccion()"><i class="fa fa-check-circle"></i> Agregar Accion</button>
          </div>
        </div>


      </jsp:attribute>

    </t:modal>
              

        <script src="${direccion_contexto}/SIGIPRO/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/SIGIPRO/recursos/js/sigipro/Tratamiento.js"></script>
  </jsp:attribute>

</t:plantilla_general>

