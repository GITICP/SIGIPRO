SELECTOR_PANEL_RESULTADOS = "#panel-contenido-resultados";
DATOS = {};

$(document).ready(function () {

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
                    
                    var configuracion_final = $.extend({}, configuracion_especifica, CONFIGURACION_TABLAS);
                    var panel_resultados = $(SELECTOR_PANEL_RESULTADOS);
                    var tabla = $("<table class=\"table table-sorting table-striped table-hover datatable\">");
                    
                    panel_resultados.empty();
                    panel_resultados.append(tabla);
                    
                    if (DATOS.message === "Éxito") {
                        crear_data_table(tabla, configuracion_final);
                    } else {
                        alert(DATOS.message);
                    }
                });
    });
});

function crearColumnas(objeto) {
    var objeto_final = [];

    var nombre_columnas = Object.keys(objeto);
    nombre_columnas.forEach(function (item, index) {
        objeto_final[index] = {"title": item};
    });

    return objeto_final;
}