// Variables globales de tablas
tEventos = null;
tCaballos = null;
T_EVENTOS_SELECTOR= "#caballos-evento";
T_CABALLOS_SELECTOR = "#caballos-grupo";

$(document).ready(function () {
    var configuracion = {
        "paging": false,
        "ordering": false,
        "bFilter": false,
        "info": false
    };
    tEventos = $("#caballos-evento").DataTable(configuracion);
    tCaballos = $("#caballos-grupo").DataTable(configuracion);

    $("#caballosform").submit(function () {
        llenarCampoAsociacion('c', T_EVENTOS_SELECTOR, $("#ids-eventos"));
    });
    $("#grupodecaballosForm").submit(function () {
        llenarCampoAsociacion('c', T_CABALLOS_SELECTOR, $("#ids-caballos"));
    });    
});

// -- Caballos -- //
function agregarEventoCaballo() {
    var eventoSeleccionado = $("#eventoModal :selected");
    eventoSeleccionado.remove();
    var spliter = eventoSeleccionado.val().split("|");
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm boton-accion' style='margin-left:7px;margin-right:5px;' onclick=eliminarEventoDeCaballo(" + spliter[0] + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tEventos.row.add([spliter[2],spliter[3],spliter[4], botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "evento-" + spliter[0]);
}

function agregarCaballo() {
    var caballoSeleccionado = $("#seleccioncaballo :selected");
    caballoSeleccionado.remove();
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarCaballo(" + caballoSeleccionado.val() + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tCaballos.row.add([caballoSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "caballo-" + caballoSeleccionado.val());
}

function eliminarEventoDeCaballo(id) {
    var fila = tEventos.row('#evento-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();
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

$(document).ready(function(){
     
    $("select[name='tipoevento']").on('change', function() {
        var split = $(this).val().split(",");
    
        $("textarea ").val(split[1]);
});
});
$(document).ready(function(){
     
    $("select[name='eventoModal']").on('change', function() {
        var split = $(this).val().split("|");
    
        $("textarea ").val(split[1]);
});
});