<%-- 
    Document   : login
    Created on : Nov 28, 2014, 10:49:05 AM
    Author     : Boga
--%>

<%

  if (session.getAttribute("usuario") != null) {
    response.sendRedirect(request.getContextPath() + "/index.jsp");
  } else {
    request.setAttribute("direccionContexto", request.getContextPath());
  }

%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE html>
<!--[if IE 9 ]><html class="ie ie9" lang="en" class="no-js"> <![endif]-->
<!--[if !(IE)]><!-->
<html lang="en" class="no-js">
  <!--<![endif]-->

  <head>
    <title>SIGIPRO - Iniciar Sesión</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta name="description" content="KingAdmin Dashboard">
    <meta name="author" content="The Develovers">
    <link rel="shortcut icon" href="../${direccion_contexto}/favicon.ico">

    <!-- CSS -->
    <link href="${direccionContexto}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen">
    <link href="${direccionContexto}/recursos/css/font-awesome.min.css" rel="stylesheet" type="text/css" media="screen">
    <link href="${direccionContexto}/recursos/css/main.css" rel="stylesheet" type="text/css" media="screen">

    <!--[if lte IE 9]>
                    <link href="${direccionContexto}/recursos/css/main-ie.css" rel="stylesheet" type="text/css" media="screen" />
                    <link href="${direccionContexto}/recursos/css/main-ie-part2.css" rel="stylesheet" type="text/css" media="screen" />
    <![endif]-->
  </head>

  <body>
    <div class="wrapper full-page-wrapper page-login text-center">
      <div class="inner-page">
        <div class="logo">
          <img src="${direccionContexto}/recursos/imagenes/141773785153470.gif" alt="" />
          <h1><strong>SIGIPRO</strong></h1>
        </div>
        <div class="login-box center-block">
          <form class="form-horizontal" role="form" action="IniciarSesion" method="post">
            <p class="title">Iniciar Sesión</p>
            ${mensaje}
            <div class="form-group">
              <label for="username" class="control-label sr-only">Usuario</label>
              <div class="col-sm-12">
                <div class="input-group">
                  <input type="text" placeholder="Usuario" class="form-control" name="usuario">
                  <span class="input-group-addon"><i class="fa fa-user"></i></span>
                </div>
              </div>
            </div>
            <label for="password" class="control-label sr-only">Contraseña</label>
            <div class="form-group">
              <div class="col-sm-12">
                <div class="input-group">
                  <input type="password" placeholder="password" class="form-control" name="contrasenna">
                  <span class="input-group-addon"><i class="fa fa-lock"></i></span>
                </div>
              </div>
            </div>
            <button type="submit" class="btn btn-primary btn-lg btn-block btn-" style="background: #3c730d;"><i class="fa fa-arrow-circle-o-right"></i> Iniciar Sesión</button>
          </form>
          <br>
          <div class="links">
            <button class="btn btn-primary btn-sm" onclick="confirmacion()">Recuperar contraseña</button>
          </div>
        </div>
      </div>
      <div class="push-sticky-footer"></div>
    </div>

    <jsp:include page="/plantillas/footer.jsp" />
    
    <jsp:include page="/plantillas/formCambiarContrasena.jsp" />

    <!-- Javascript -->
    <script src="${direccionContexto}/recursos/js/jquery/jquery-2.1.0.min.js"></script>
    <script src="${direccionContexto}/recursos/js/bootstrap/bootstrap.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/modernizr/modernizr.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/bootstrap-tour/bootstrap-tour.custom.js"></script>
    <script src="${direccionContexto}/recursos/js/king-common.js"></script>

    <script src="${direccionContexto}/recursos/js/plugins/stat/jquery.easypiechart.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/raphael/raphael-2.1.0.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/stat/flot/jquery.flot.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/stat/flot/jquery.flot.resize.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/stat/flot/jquery.flot.time.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/stat/flot/jquery.flot.pie.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/stat/flot/jquery.flot.tooltip.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/jquery-sparkline/jquery.sparkline.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/datatable/jquery.dataTables.min.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/datatable/dataTables.bootstrap.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/jquery-mapael/jquery.mapael.js"></script>
    <script src="${direccionContexto}/recursos/js/plugins/raphael/maps/usa_states.js"></script>
    <script src="${direccionContexto}/recursos/js/king-chart-stat.js"></script>
    <script src="${direccionContexto}/recursos/js/king-table.js"></script>
    <script src="${direccionContexto}/recursos/js/king-components.js"></script>
    
    <script src="${direccionContexto}/recursos/js/plugins/bootbox.js"></script>
    <script src="${direccionContexto}/recursos/js/sigipro/iniciar-sesion.js"></script>
    <script src="${direccionContexto}/recursos/js/sigipro/cambiar-contrasena.js"></script>
    
    ${caducada}
  </body>
</html>