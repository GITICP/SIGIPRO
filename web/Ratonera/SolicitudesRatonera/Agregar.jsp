<%-- 
    Document   : Agregar
    Created on : Feb 19, 2015, 7:59:26 PM
    Author     : Amed
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Ratonera" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">



    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-12 ">
          <ul class="breadcrumb">
            <li>Bioterio-Ratonera</li>
            <li> 
              <a href="/SIGIPRO/Ratonera/SolicitudesRatonera?">SolicitudesRatonera</a>
            </li>
            <li class="active"> Nueva Cara </li>
          </ul>
        </div>
      </div>

      <!-- main -->
      <div class="content">
        <div class="main-content">
          <!-- COLUMN FILTER DATA TABLE -->
          <div class="widget widget-table">
            <div class="widget-header">
              <h3><i class="fa fa-alt-list"></i> Agregar Nueva Solicitud de Ratonera </h3>
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

