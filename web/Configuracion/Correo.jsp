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

<t:plantilla_general title="Configuración" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Configuración</li>
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
              <h3><i class="fa fa-envelope"></i> Configuración del Correo </h3>
            </div>
            ${mensaje}
            <div class="widget-content">
              <form id="formConfigurarCorreo" class="form-horizontal" autocomplete="off" role="form" action="Correo" method="post">
                <div class="row">
                  <div class="col-md-6">
                    <input id="idCorreo" hidden="true" name="idCorreo" value="${correo.getID()}">
                    <label for="puerto" class="control-label">Puerto</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <input type="text" maxlength="10" placeholder="Ejemplo: 587"  class="form-control" name="puerto" value="${correo.getPuerto()}" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')" > 
                        </div>
                      </div>
                    </div>
                    <label for="host" class="control-label">Dirección smtp del proveedor</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <input type="text" maxlength="80" placeholder="Ejemplo: smtp.gmail.com"  class="form-control" name="host" value="${correo.getHost()}" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                    <label for="emisor" class="control-label">Nombre del Emisor</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <input type="text" maxlength="80" placeholder="Ejemplo: SIGIPRO"  class="form-control"  name="emisor" value="${correo.getEmisor()}" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6">
                    <label for="correo" class="control-label">Correo</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <input type="text" maxlength="80" placeholder="sigiproicp@gmail.com" class="form-control" name="correo" value="${correo.getCorreo()}" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>

                    <label for="contraseña" class="control-label">Contraseña</label>
                    <div class="form-group">
                      <div class="col-sm-12">
                        <div class="input-group">
                          <input type="password" maxlength="30" class="form-control" name="contrasena" value="${correo.getContrasena()}" required
                                 oninvalid="setCustomValidity('Este campo es requerido ')"
                                 oninput="setCustomValidity('')">
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="form-group">
                  <div class="modal-footer">
                    <button type="button" class="btn btn-danger btn-volver" data-dismiss="modal"><i class="fa fa-times-circle"></i>  Cancelar</button>
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


