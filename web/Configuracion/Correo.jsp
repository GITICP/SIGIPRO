<%-- 
    Document   : Ingresar
    Created on : Jan 16, 2015, 6:41:49 PM
    Author     : Amed
--%>


<%@page import="com.icp.sigipro.configuracion.dao.CorreoDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.configuracion.modelos.Correo"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Configuraci�n" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Configuraci�n</li>
            <li class="active"> Correo</li>

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
              <h3><i class="fa fa-group"></i> Configuraci�n del Correo </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
              <form id="formConfigurarCorreo" class="form-horizontal" autocomplete="off" role="form" action="Correo" method="post">
                <input id="idCorreo" hidden="true" name="idCorreo" value="${correo.getID()}">
                <label for="puerto" class="control-label">Puerto</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input type="text" maxlength="10"  class="form-control" name="puerto" value="${correo.getPuerto()}" required
                             oninvalid="setCustomValidity('Este campo es requerido ')"
                             oninput="setCustomValidity('')" > 
                    </div>
                  </div>
                </div>
                <label for="host" class="control-label">Direcci�n smtp del proveedor</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input type="text" maxlength="80"  class="form-control" name="host" value="${correo.getHost()}" required
                             oninvalid="setCustomValidity('Este campo es requerido ')"
                             oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
                <label for="emisor" class="control-label">Nombre del Emisor</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input type="text" maxlength="80"  class="form-control" name="emisor" value="${correo.getEmisor()}" required
                             oninvalid="setCustomValidity('Este campo es requerido ')"
                             oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
                 <label for="correo" class="control-label">Correo</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input type="text" maxlength="80" class="form-control" name="correo" value="${correo.getCorreo()}" required
                             oninvalid="setCustomValidity('Este campo es requerido ')"
                             oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
                  <label for="contrase�a" class="control-label">Contrase�a</label>
                <div class="form-group">
                  <div class="col-sm-12">
                    <div class="input-group">
                      <input type="text" maxlength="30" class="form-control" name="contrasena" value="${correo.getContrasena()}" required
                             oninvalid="setCustomValidity('Este campo es requerido ')"
                             oninput="setCustomValidity('')">
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" ><i class="fa fa-check-circle"></i> Confirmar Cambios</button>
                  </div>
                </div>
              </form>
            </div>
          </div>
          <!-- END WIDGET TICKET TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->
    </div>
        
  </jsp:attribute>

</t:plantilla_general>


