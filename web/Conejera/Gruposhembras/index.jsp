<%-- 
    Document   : index
    Created on : Apr 25, 2015, 3:19:45 PM
    Author     : Amed
--%>

<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Grupos de Hembras" direccion_contexto="/SIGIPRO">

  <jsp:attribute name="contenido">

    <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />

    <!-- content-wrapper -->
    <div class="col-md-12 content-wrapper">
      <div class="row">
        <div class="col-md-4 ">
          <ul class="breadcrumb">
            <li>Bioterio - Conejera</li>
            <li> 
              <a href="/SIGIPRO/Conejera/Gruposhembras?">Grupos de Hembras</a>
            </li>
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
              <h3><i class="fa fa-barcode"></i>Grupos de Hembras</h3>
                <div class="btn-group widget-header-toolbar">
                    <a class="btn btn-primary btn-sm boton-accion " href="/SIGIPRO/Conejera/Gruposhembras?accion=agregar">Agregar Grupo</a>
                </div>
            </div>
            ${mensaje}
            <div class="widget-content">
              <table class="table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter">
                <!-- Columnas -->
                <thead> 
                  <tr>
                    <th>Identificación</th>
                    <th>Cantidad de Espacios</th>
                    <th>Eliminar/Editar</th>
                  </tr>
                </thead>
                <tbody>
                  <c:forEach items="${listaGruposhembras}" var="grupo">
                    <tr>
                      <td>
                        <a href="/SIGIPRO/Conejera/Cajas?accion=index&id_grupo=${grupo.getId_grupo()}">
                          <div style="height:100%;width:100%">
                            ${grupo.getIdentificador()}
                          </div>
                        </a>
                      </td>
                      <td>${grupo.getCantidad_espacios()}</td>
                      <td>
                        <a class="btn btn-warning btn-sm boton-accion" href="/SIGIPRO/Conejera/Gruposhembras?accion=editar&id_grupo=${grupo.getId_grupo()}" >Editar</a>
                        <a class="btn btn-danger btn-sm boton-accion confirmable" data-texto-confirmacion="eliminar este grupo" data-href="/SIGIPRO/Conejera/Gruposhembras?accion=eliminar&id_grupo=" onclick="Eliminar(${grupo.getId_grupo()})">Eliminar</a>
                      </td>
                    </tr>

                  </c:forEach>
                </tbody>
              </table>
            </div>
          </div>
          <!-- END COLUMN FILTER DATA TABLE -->
        </div>
        <!-- /main-content -->
      </div>
      <!-- /main -->

    </jsp:attribute>
    <jsp:attribute name="scripts">
      <script src="/SIGIPRO/recursos/js/sigipro/gruposhembras.js"></script>
    </jsp:attribute>
  </t:plantilla_general>

