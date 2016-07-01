SELECTOR_TABLA_RESULTADOS = "#tabla-resultados";
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
                    var columnas = crearColumnas(DATOS.data[0]);
                    if (DATOS.message === "Éxito") {
                        alert("Éxito");
                        $(SELECTOR_TABLA_RESULTADOS).DataTable({
                            "columns": columnas,
                            data: DATOS.data
                        });
                    } else {
                        alert(DATOS.message);
                    }
                });

    });

});

function crearColumnas(objeto) {
    var objeto_final = [];
    
    var nombre_columnas = Object.keys(objeto);
    nombre_columnas.forEach(function(item, index){
        objeto_final[index] = {"data" : item};
    });
    
    return objeto_final;
}