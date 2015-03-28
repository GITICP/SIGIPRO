<%-- 
    Document   : Agregar
    Created on : Dec 14, 2014, 1:43:27 PM
    Author     : Walter
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Grupos de Caballos" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-8 ">
          <ul class="breadcrumb">
            <li>Caballeriza</li>
            <li> 
              <a href="/SIGIPRO/Caballeriza/GrupoDeCaballos?">Grupos de Caballos</a>
            </li>
            <li class="active"> Agregar Grupo de Caballos</li>

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
              <h3><i class="fa fa-barcode"></i> Agregar Nuevo Grupo de Caballos</h3>
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
                  <c:forEach items="${caballos_restantes}" var="pr">
                    <option value=${pr.getId_caballo()}>${pr.getNombre()} (${pr.getNumero_microchip()})</option>
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

  </jsp:attribute>
  <jsp:attribute name="scripts">
    <script src="/SIGIPRO/recursos/js/sigipro/Caballeriza.js"></script>
  </jsp:attribute>
</t:plantilla_general>
