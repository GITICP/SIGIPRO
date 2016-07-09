SELECTOR_PANEL_RESULTADOS = "#panel-contenido-resultados";
DATOS = {};

$(document).ready(function () {
    
    $("#exportar-excel").click(function() {
        
        var url = "?accion=ajaxexcel";
        var fila_parametros = $("#fila-parametros");
        
        fila_parametros.find(":input").each(function () {
            var nombre = $(this).attr("name");
            var valor = $(this).val();
            var string_param = "&" + nombre + "=" + valor;
            url += string_param;
        });
        
        window.location.href = "/SIGIPRO/Reportes/Reportes" + url;
        
    });

    $("#actualizar-datos").click(function () {

        var jsonData = {};
        var fila_parametros = $("#fila-parametros");

        fila_parametros.find(":input").each(function () {
            var nombre = $(this).attr("name");
            var valor = $(this).val();
            jsonData[nombre] = valor;
        });

        $.get("/SIGIPRO/Reportes/Reportes?accion=ajaxdatos", jsonData)
                .done(function (data) {
                    DATOS = data;
            
                    var configuracion_especifica = {
                        columns: DATOS.columnas,
                        data: DATOS.data
                    };
                    
                    var configuracion_final = $.extend({}, configuracion_especifica, CONFIGURACION_TABLAS); // CONFIGURACION_TABLAS viene del archivo tablas-sigipro.js
                    var panel_resultados = $(SELECTOR_PANEL_RESULTADOS);
                    var tabla = $("<table class=\"table table-sorting table-striped table-hover datatable\">");
                    
                    panel_resultados.empty();
                    panel_resultados.append(tabla);
                    
                    if (DATOS.message === "Exito") {
                        $("#exportar-excel").attr("disabled", false);
                        crear_data_table(tabla, configuracion_final);
                    } else {
                        $("#exportar-excel").attr("disabled", true);
                        alert(DATOS.message);
                    }
                });
    });
});