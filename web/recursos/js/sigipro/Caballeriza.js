// Variables globales de tablas
tEventos = null;
tCaballos = null;
T_EVENTOS_SELECTOR = "#caballos-evento";
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

    var nuevaFila = tEventos.row.add([spliter[2], spliter[3], spliter[4], botonEliminar[0].outerHTML]).draw().node();

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
function eliminarCaballoSP(id_caballo) {
    var fila = $('#' + id_caballo);
    fila.remove();
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

$(document).ready(function () {

    $("select[name='tipoevento']").on('change', function () {
        var split = $(this).val().split(",");

        $("textarea ").val(split[1]);
    });
    
    $("select[name='eventoModal']").on('change', function () {
        var split = $(this).val().split("|");

        $("textarea ").val(split[1]);
    });
    
    $("#seleccionInoculoGrupo").change(function(){
        var id_grupo = $(this).val();
        
        $(".caballos-grupo").each( function() {
            $(this).find("input").each( function() {
                $(this).attr('checked', false);
            });
            $(this).hide();
        });
        $("#grupo-" + id_grupo).show();
    });
    
    $("#seleccionInoculo").change( function() {
        var id_inoculo = $(this).val();

        $(".tabla-caballos").each( function() {
            $(this).hide();
        });
        
        $("#inoculo-" + id_inoculo).show();
    });
    
    $("#seleccion-sangria-prueba").change( function() {
        var id_sangria_prueba = $(this).val();
        
        $(".caballos-prueba").each( function() {
            $(this).find("input").each( function() {
                $(this).attr('checked', false);
            });
        });
        
        var contenedor_caballos = $("#prueba-" + id_sangria_prueba);
        $(contenedor_caballos).find("input").each( function() {
            $(this).attr('checked', true);
        });
        contenedor_caballos.show();
    });
    
    $("#form-prueba-sangria").submit( function() {
        llenar_campo_caballos();
    });
});

function llenar_campo_caballos() {
    var caballos_codificados = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    {
        var fila = $(this);
        caballos_codificados += fila.attr('id');
        caballos_codificados += "#c#";
    });
    $('#caballos').val(caballos_codificados.slice(0, -3));
}