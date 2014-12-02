<%-- 
    Document   : plantilla_general
    Created on : Nov 30, 2014, 9:06:51 PM
    Author     : Boga
--%>

<%@tag description="Plantilla general" pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="title"%>
<%@attribute name="direccion_contexto"%>
<%@attribute name="contenido" fragment="true" required="true"%>

<!DOCTYPE html>
<html lang="es">
    <head>
            <meta http-equiv="content-type" content="text/html; charset=UTF-8">
            <meta charset="utf-8">
            <title>SIGIPRO - ${title}</title>
            <meta name="generator" content="Bootply" />
            <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
            <link href="${direccion_contexto}/css/bootstrap.min.css" rel="stylesheet">
            <!--[if lt IE 9]>
                    <script src="${direccion_contexto}/js/html5shim.js"></script>
            <![endif]-->
            <link href="${direccion_contexto}/css/styles.css" rel="stylesheet">
    </head>
    
    <body>
                
        <!-- Header -->
        <jsp:include page="/plantillas/header.jsp" />
        <!-- /Header -->
        
        <!-- Main -->
        <jsp:invoke fragment="contenido" />
        <!-- /Main -->

        <!-- Footer -->
        <jsp:include page="/plantillas/footer.jsp" />
        <!-- /Footer -->

        <!-- Modal -->
        <div class="modal" id="addWidgetModal">
          <div class="modal-dialog">
            <div class="modal-content">
              <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">Add Widget</h4>
              </div>
              <div class="modal-body">
                <p>Add a widget stuff here..</p>
              </div>
              <div class="modal-footer">
                <a href="#" data-dismiss="modal" class="btn">Close</a>
                <a href="#" class="btn btn-primary">Save changes</a>
              </div>
            </div><!-- /.modal-content -->
          </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->
        <!-- /Modal -->

	<!-- script references -->
        <script src="${direccion_contexto}/js/jquery.min.js"></script>
        <script src="${direccion_contexto}/js/bootstrap.min.js"></script>
        <%-- <script src="${direccion_contexto}/js/scripts.js"></script> --%>
        
    </body>
</html>
