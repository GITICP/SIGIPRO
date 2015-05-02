<%-- 
    Document   : plantilla_general
    Created on : Nov 30, 2014, 9:06:51 PM
    Author     : Boga
--%>

<%@tag description="Plantilla general" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>

<c:if test="${sessionScope.usuario == null}">
    <c:redirect url="/Cuenta/IniciarSesion" />
</c:if>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title"%>
<%@attribute name="direccion_contexto"%>
<%@attribute name="contenido" fragment="true" required="true"%>
<%@attribute name="scripts" fragment="true" required="false"%>
<%@attribute name="css" fragment="true" required="false"%>

<!DOCTYPE html>
<html lang="es">

    <head>
        <title>SIGIPRO - ${title}</title>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
        <meta name="description" content="Instituto Clodomiro Picado - SIGIPRO">
        <meta name="author" content="ICP">

        <!-- CSS -->
        <link href="${direccion_contexto}/recursos/css/bootstrap.min.css" rel="stylesheet" type="text/css" media="screen">
        <link href="${direccion_contexto}/recursos/css/font-awesome.min.css" rel="stylesheet" type="text/css" media="screen">
        <link href="${direccion_contexto}/recursos/css/main.css" rel="stylesheet" type="text/css" media="screen">
        <link href="${direccion_contexto}/recursos/css/sigipro/sigipro.css" rel="stylesheet" type="text/css" media="screen">
        <link href="${direccion_contexto}/recursos/css/sigipro/jquery.smartmenus.bootstrap.css" rel="stylesheet" type="text/css" media="screen">

        <jsp:invoke fragment="css" />

        <!--[if lte IE 9]>
            <link href="${direccion_contexto}/recursos/css/main-ie.css" rel="stylesheet" type="text/css" media="screen" />
            <link href="${direccion_contexto}/recursos/css/main-ie-part2.css" rel="stylesheet" type="text/css" media="screen" />
        <![endif]-->

        <!-- Fav and touch icons -->
        <link rel="apple-touch-icon-precomposed" sizes="144x144" href="${direccion_contexto}/recursos/ico/kingadmin-favicon144x144.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114" href="${direccion_contexto}/recursos/ico/kingadmin-favicon114x114.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72"   href="${direccion_contexto}/recursos/ico/kingadmin-favicon72x72.png">
        <link rel="apple-touch-icon-precomposed" sizes="57x57"   href="${direccion_contexto}/recursos/ico/kingadmin-favicon57x57.png">
        <link rel="shortcut icon" href="${direccion_contexto}/favicon.ico">
    </head>

    <body>

        <div class="wrapper">

            <!-- Header -->
            <jsp:include page="/plantillas/header.jsp" />
            <!-- /Header -->

            <!-- Main -->
            <jsp:invoke fragment="contenido" />
            <!-- /Main -->

        </div>

        <!-- Footer -->
        <jsp:include page="/plantillas/footer.jsp" />
        <!-- /Footer -->

        <!-- Scripts Externos -->
        <script src="${direccion_contexto}/recursos/js/jquery/jquery-2.1.0.min.js"></script>
        <script src="${direccion_contexto}/recursos/js/bootstrap/bootstrap.js"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/modernizr/modernizr.js"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/bootstrap-tour/bootstrap-tour.custom.js"></script>
        <script src="${direccion_contexto}/recursos/js/deliswitch.js"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/bootstrap-datepicker/bootstrap-datepicker.js"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/datatable/jquery.dataTables.min.js"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/datatable/dataTables.bootstrap.js"></script>
        <script src="${direccion_contexto}/recursos/js/king-common.js"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/datatable/exts/dataTables.dateOrder.js" type="text/javascript"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/moment/moment-2.4.js" type="text/javascript"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/bootbox.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/jquery.smartmenus.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/jquery.smartmenus.bootstrap.js"></script>
        <script src="${direccion_contexto}/recursos/js/plugins/select2/select2.min.js"></script>

        <!-- 
            Se eliminaron estos scripts ya que no aportaban valor. Si algo no funciona, incluirlos.
        
        <script src="${direccion_contexto}/recursos/js/king-table.js"></script>
        <script src="${direccion_contexto}/recursos/js/king-components.js"></script>
        <script src="${direccion_contexto}/recursos/js/king-elements.js"></script>
        -->

        <script src="${direccion_contexto}/recursos/js/sigipro/sigipro.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/PermisosRol.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/cambiar-contrasena.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/CatalogoExterno.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/tabla-sigipro.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/confirmacion-eliminar.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/Serpiente.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/Extraccion.js"></script>
        <script src="${direccion_contexto}/recursos/js/sigipro/Veneno.js"></script>
        
        <script>
            $(document).ready(function () {
                if ($('.sigiproDatePicker').length > 0) {
                    $('.sigiproDatePicker').datepicker()
                            .on('changeDate', function () {
                                $(this).datepicker('hide');
                                var indice = ($(':input').index(this) + 1);
                                var proximo_elemento = $(':input:eq(' + indice + ')');
                                while (proximo_elemento.attr('hidden') === "hidden") {
                                    indice++;
                                    proximo_elemento = $(':input:eq(' + indice + ')');
                                }
                                proximo_elemento.focus();
                            });
                    $("#fechaActivacion").datepicker({startDate: 0});
                }
                if ($('.sigiproDatePickerEspecial').length > 0) {
                    $('.sigiproDatePickerEspecial').datepicker()
                            .on('changeDate', function () {
                                $(this).datepicker('hide');
                            });
                    $("#fechaActivacion").datepicker({startDate: 0});
                }

                if ($('.sigiproDatePickerSerpiente').length > 0) {
                    $('.sigiproDatePickerSerpiente').datepicker({endDate: '-0d'})
                            .on('changeDate', function () {
                                $(this).datepicker('hide');
                                var indice = ($(':input').index(this) + 1);
                                var proximo_elemento = $(':input:eq(' + indice + ')');
                                while (proximo_elemento.attr('hidden') === "hidden") {
                                    indice++;
                                    proximo_elemento = $(':input:eq(' + indice + ')');
                                }
                                proximo_elemento.focus();
                            });
                }
            });
        </script>

        <jsp:include page="/plantillas/formCambiarContrasena.jsp" />

        <jsp:invoke fragment="scripts" />

    </body>
</html>
