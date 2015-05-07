<%-- 
    Document   : Agregar
    Created on : Feb 19, 2015, 7:59:26 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Conejera" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio-Conejera</li>
            <li> 
                            <a href="/SIGIPRO/Conejera/Gruposhembras?">Grupo ${coneja.getCaja().getGrupo().getIdentificador()}</a>
                        </li>
                        <li> 
                            <a href="/SIGIPRO/Conejera/Cajas?id_grupo=${coneja.getCaja().getGrupo().getId_grupo()}">Espacios</a>
                        </li>
                        <li>
                           <a href="/SIGIPRO/Conejera/Cajas?accion=ver&id_caja=${coneja.getCaja().getId_caja()}">1</a>
                        </li>
            <li class="active"> Cruce de Coneja en Caja #${coneja.getCaja().getNumero()}</li>
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
              <h3><i class="fa fa-barcode"></i> Agregar Cruce de Coneja en Caja #${coneja.getCaja().getNumero()} </h3>
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


  </jsp:attribute>

</t:plantilla_general>

