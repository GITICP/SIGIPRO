<%-- 
    Document   : index
    Created on : Nov 26, 2014, 10:16:57 PM
    Author     : Boga
--%>

<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<t:plantilla_general title="Seguridad" direccion_contexto="/SIGIPRO">
    
    <jsp:attribute name="contenido">

        <jsp:include page="../plantillas/barraFuncionalidad.jsp" />
        
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
                    
                    <%-- Tabla vieja --%>
                    <%--
                    <!-- WIDGET TICKET TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-group"></i> Usuarios</h3>
                            <div class="btn-group widget-header-toolbar">
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Eliminar</button>
                                <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Editar</button>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Agregar</button>
                            </div>
                        </div>
                        <div class="widget-content">
                            <table id="ticket-table" class="table table-sorting dataTable no-footer" role="grid">
                                <thead>
                                        <tr role="row"><th class="sorting_asc" tabindex="0" aria-controls="ticket-table" rowspan="1" colspan="1" aria-sort="ascending" aria-label="Number: activate to sort column ascending" style="width: 89px;">Nombre de Usuario</th>
                                            <th class="sorting" tabindex="0" aria-controls="ticket-table" rowspan="1" colspan="1" aria-label="Date: activate to sort column ascending" style="width: 168px;">Nombre Completo</th>
                                            <th class="sorting" tabindex="0" aria-controls="ticket-table" rowspan="1" colspan="1" aria-label="Category: activate to sort column ascending" style="width: 156px;">Correo</th>
                                            <th class="sorting" tabindex="0" aria-controls="ticket-table" rowspan="1" colspan="1" aria-label="Name: activate to sort column ascending" style="width: 82px;">Estado</th>
                                            <th class="sorting" tabindex="0" aria-controls="ticket-table" rowspan="1" colspan="1" aria-label="Name: activate to sort column ascending" style="width: 82px;">Opciones</th>
                                        </tr>
                                </thead>
                                <tbody>
                                    <tr role="row" class="odd">
                                        <td class="sorting_1">
                                            <a href="#">jmorales</a>
                                        </td>
                                        <td>Juan Morales</td>
                                        <td>jmorales@icp.ucr.ac.cr</td>
                                        <td>Inactivo</td>
                                        <td>
                                            <div class="btn btn-primary" style="margin:5px;"> Editar </div>
                                            <div class="btn btn-success" style="margin:5px;min-width:96px"> Activar </div>
                                        </td>
                                    </tr>
                                    <tr role="row" class="odd">
                                        <td class="sorting_1">
                                            <a href="#">idelgado</a>
                                        </td>
                                        <td>Íngrid Delgado</td>
                                        <td>idelgado@icp.ucr.ac.cr</td>
                                        <td>Activo</td>
                                        <td>
                                            <div class="btn btn-primary" style="margin:5px;"> Editar </div>
                                            <div class="btn btn-danger" style="margin:5px;min-width:96px"> Desactivar </div>
                                        </td>
                                    </tr>
                                    <tr role="row" class="odd">
                                        <td class="sorting_1">
                                            <a href="#">djimenez</a>
                                        </td>
                                        <td>David Jiménez</td>
                                        <td>djimenez@icp.ucr.ac.cr</td>
                                        <td>Activo</td>
                                        <td>
                                            <div class="btn btn-primary" style="margin:5px;"> Editar </div>
                                            <div class="btn btn-danger" style="margin:5px;min-width:96px"> Desactivar </div>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                            <div class="row">

                            </div>    
                        </div>
                    </div> --%>
                    
                    <!-- COLUMN FILTER DATA TABLE -->
                    <div class="widget widget-table">
                        <div class="widget-header">
                            <h3><i class="fa fa-group"></i> Usuarios</h3>
                            <div class="btn-group widget-header-toolbar">
                                <button class="btn btn-danger btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Eliminar</button>
                                <button class="btn btn-warning btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Editar</button>
                                <button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#myModal" style="margin-left:5px;margin-right:5px;">Agregar</button>
                            </div>
                        </div>
                        <div class="widget-content">
                            <table id="datatable-column-filter" class="table table-sorting table-striped table-hover datatable">
                                <!-- Columnas -->
                                <thead> 
                                    <tr>
                                        <th>Browser</th>
                                        <th>Operating System</th>
                                        <th>Visits</th>
                                        <th>New Visits</th>
                                        <th>Bounce Rate</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>Chrome</td>
                                        <td>Macintosh</td>
                                        <td>360</td>
                                        <td>82.78%</td>
                                        <td>87.77%</td>
                                    </tr>
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
            <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                            <h4 class="modal-title" id="myModalLabel">Recuperar Contraseña</h4>
                        </div>
                        <div class="modal-body">

                        <form class="form-horizontal" role="form" action="IniciarSesion" method="post">
                            <p class="title">Agregar Usuario</p>
                            ${mensajeError}
                            <label for="nombreUsuario" class="control-label">Nombre de Usuario</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input type="text" placeholder="Nombre de Usuario" class="form-control" name="nombreUsuario">
                                    </div>
                                </div>
                            </div>
                            <label for="nombreCompleto" class="control-label">Nombre Completo</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <input type="text" placeholder="Nombre Completo" class="form-control" name="nombreCompleto">
                                    </div>
                                </div>
                            </div>
                            <label for="fechaActivacion" class="control-label">Fecha de Activación</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" id="datepicker" class="form-control" name="fechaActivacion">
                                    </div>
                                </div>
                            </div>
                            <label for="fechaDesactivacion" class="control-label">Fecha de Desactivación</label>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <div class="input-group">
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" id="datepicker" class="form-control" name="fechaDesactivacion">
                                    </div>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-danger" data-dismiss="modal"><i class="fa fa-times-circle"></i> Cancelar</button>
                                    <button type="submit" class="btn btn-primary"><i class="fa fa-check-circle"></i> Agregar Usuario</button>
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