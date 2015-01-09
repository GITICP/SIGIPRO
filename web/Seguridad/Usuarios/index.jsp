<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>

<%@page import="com.icp.sigipro.seguridad.dao.UsuarioDAO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.icp.sigipro.basededatos.SingletonBD"%>
<%@page import="com.icp.sigipro.seguridad.modelos.Usuario"%>
<%@page import="java.util.List"%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<%        
    UsuarioDAO u = new UsuarioDAO();

    List<Usuario> usuarios = u.obtenerUsuarios();

    if(usuarios!=null)
    {
        request.setAttribute("listaUsuarios", usuarios);
    }
%>

<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">
    
    <jsp:attribute name="contenido">

        <jsp:include page="../../plantillas/barraFuncionalidad.jsp" />
        
        <!-- content-wrapper -->
        <div class="col-md-10 content-wrapper">
            <div class="row">
                <div class="col-md-4 ">
                    <ul class="breadcrumb">
                        <li>Seguridad</li>
                        <li class="active">Usuarios</li>
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
                            <h3><i class="fa fa-group"></i> Usuarios</h3>
                            <div class="btn-group widget-header-toolbar">                                 
                                <a class="btn btn-primary btn-sm"  style="margin-left:5px;margin-right:5px;color:#fff;" href="Agregar">Agregar Usuario</a>
                            </div>
                        </div>
                        ${mensaje}
                        <div class="widget-content">
                            <table id="datatable-column-filter" class="table table-sorting table-striped table-hover datatable tablaSigipro">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Nombre Usuario</th>
                                        <th>Nombre Completo</th>
                                        <th>Correo</th>
                                        <th>Cédula</th>
                                        <th>Departamento</th>
                                        <th>Puesto</th>
                                        <th>Fecha Activacion</th>
                                        <th>Fecha Desactivacion</th>
                                        <th>Activo</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    
                                    <c:forEach items="${listaUsuarios}" var="usuario">
                                    
                                        <tr id="${usuario.getID()}" >
                                            <td>
                                            <a href="/SIGIPRO/Seguridad/Usuarios/Ver?id=${usuario.getID()}">
                                                <div style="height:100%;width:100%">
                                                  ${usuario.getNombreUsuario()}
                                                </div>
                                              </a>
                                            </td>
                                            <td>${usuario.getNombreCompleto()}</td>
                                            <td>${usuario.getCorreo()}</td>
                                            <td>${usuario.getCedula()}</td>
                                            <td>${usuario.getDepartamento()}</td>
                                            <td>${usuario.getPuesto()}</td>
                                            <td>${usuario.getFechaActivacion()}</td>
                                            <td>${usuario.getFechaDesactivacion()}</td>
                                            <td>${usuario.getActivo()}</td>
                                        </tr>
                                        
                                    </c:forEach>
                                    
                                </tbody>
                            </table>
                        </div>
                    </div>
                    <!-- END COLUMN FILTER DATA TABLE -->
                </div>
                <!-- END WIDGET TICKET TABLE -->
            </div>
            <!-- /main-content -->
        </div>
        <!-- /main -->
                            
        <div class="widget-content">
            <div class="modal fade" id="modalDesactivarUsuario" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Confirmación</h4>
                        </div>
                        <div class="modal-body">
                       <form class="form-horizontal" role="form" action="EliminarUsuario" method="post">
                            <h5 class="title">¿Está seguro que desea desactivar el usuario?</h5>
                            <br><br>
                            <input hidden="false" id="controlID" name="controlID">
                            <div class="form-group">
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Confirmar</button>
                                </div>
                            </div>
                        </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        
    </jsp:attribute>

</t:plantilla_general>