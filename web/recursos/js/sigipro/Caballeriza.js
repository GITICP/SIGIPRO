// Variables globales de tablas

tCaballos = null;
T_CABALLOS_SELECTOR = "#caballos-grupo";

$(document).ready(function () {
    var configuracion = {
        "paging": false,
        "ordering": false,
        "bFilter": false,
        "info": false
    };
    tCaballos = $("#caballos-grupo").DataTable(configuracion);

    $("#grupodecaballosForm").submit(function () {
        llenarCampoAsociacion('c', T_CABALLOS_SELECTOR, $("#ids-caballos"));
    });
});

// -- Caballos -- //

function agregarCaballo() {
    var caballoSeleccionado = $("#seleccioncaballo :selected");
    caballoSeleccionado.remove();
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarCaballo(" + caballoSeleccionado.val() + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tCaballos.row.add([caballoSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "caballo-" + caballoSeleccionado.val());
}

function eliminarCaballo(id) {
    var fila = tCaballos.row('#caballo-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();

    $("#seleccioncaballo").append(nuevaOpcion);
}


function llenarCampoAsociacion(string_pivote, tabla_selector, campo_escondido) {
    var asociacionCodificada = "";
    var pivote = "#" + string_pivote + "#";
    $(tabla_selector).find("tr[id]").each(function () {
        var asociacion = pivote + $(this).attr('id').split("-")[1];
        asociacionCodificada += asociacion;
    });
    campo_escondido.val(asociacionCodificada);
}