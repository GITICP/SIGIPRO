SELECTOR_PANEL_RESULTADOS = "#panel-contenido-resultados";
DATOS = {};

$(document).ready(function () {

	$.getScript( "https://www.gstatic.com/charts/loader.js" )
	  .done(function( script, textStatus ) {
	    google.charts.load('current', {'packages':['corechart']});
	  })
	  .fail(function( jqxhr, settings, exception ) {
	    alert("Error al cargar la librería de los gráficos. Notifique al administrador del sistema.");
	});

    $("#actualizar-datos").click(function () {

        var jsonData = {};
        var fila_parametros = $("#fila-parametros");

        fila_parametros.find(":input").each(function () {
            var nombre = $(this).attr("name");
            var valor = $(this).val();
            jsonData[nombre] = valor;
        });

        jsonData['para-grafico'] = "true";

        $.get("/SIGIPRO/Reportes/Reportes?accion=ajaxdatos", jsonData)
                .done(function (data) {
                    DATOS = data;
                    
                    var panel_resultados = $(SELECTOR_PANEL_RESULTADOS);
                    
                    panel_resultados.empty();
                    
                    if (DATOS.message === "Exito") {
                        var div = $("<div id=\"div-grafico\" style=\"height:500px\">");

                        panel_resultados.append(div);

                        var datos = [];
                        var datos_crudos;
                        var datos_finales;

                        datos.push(DATOS.columnas);
                        datos_crudos = datos.concat(DATOS.data);

                        datos_finales = google.visualization.arrayToDataTable(datos_crudos);

                        var opciones = {
                        	title: "Gráfico",
                        	backgroundColor: {"fill": "transparent"}
                        };

                        var chart = new google.visualization.PieChart(document.getElementById("div-grafico"));
                        chart.draw(datos_finales, opciones);
                    } else {

                    }
                });
    });
});