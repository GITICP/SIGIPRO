/*
 * Funciones de eventos
 */

$("#seleccion-objeto").change(function () {
        if ($(this).find("option:selected").val() === "sangria") {
            $.ajax({
                url: "/SIGIPRO/Caballeriza/Sangria",
                type: "GET",
                data: {"accion": "sangriasajax"},
                dataType: "json",
                success: function (datos) {
                    generar_select_sangria(datos);
                }
            });
        }
    });

function generar_select_sangria(datos) {

    $("#fila-select-sangria").show();

    var select_sangria = $("#seleccion-sangria");
    select_sangria.select2();

    select_sangria.change(evento_seleccionar_sangria);

    for (i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion = $("<option>");
        opcion.val(elemento.id_sangria);
        opcion.text(elemento.identificador);
        opcion.data("indice", i);
        opcion.data("fecha-1", elemento.fecha_dia1);
        opcion.data("fecha-2", elemento.fecha_dia2);
        opcion.data("fecha-3", elemento.fecha_dia3);

        select_sangria.append(opcion);
    }
}

function generar_select_caballos(datos) {

    var select_caballos = $("#seleccion-caballos");

    for (i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion = $("<option>");
        opcion.val(elemento.id_caballo);
        opcion.text(elemento.nombre + "(" + elemento.numero + ")");
        select_caballos.append(opcion);
    }

    select_caballos.select2();
}

function evento_seleccionar_sangria() {
    $("#fila-select-dia").show();

    var select_dia = $("#seleccion-dia");
    select_dia.find("option").remove();
    select_dia.append($("<option>"));
    select_dia.change(evento_seleccionar_dia);

    var opcion_seleccionada = $(this).find("option:selected");

    if (opcion_seleccionada.data("fecha-1") !== undefined) {
        var opcion = $("<option>");
        opcion.text("Día 1");
        opcion.val("1");
        select_dia.append(opcion);
    }
    if (opcion_seleccionada.data("fecha-2") !== undefined) {
        var opcion = $("<option>");
        opcion.text("Día 2");
        opcion.val("2");
        select_dia.append(opcion);
    }
    if (opcion_seleccionada.data("fecha-3") !== undefined) {
        var opcion = $("<option>");
        opcion.text("Día 3");
        opcion.val("3");
        select_dia.append(opcion);
    }

    select_dia.select2();
}

function evento_seleccionar_dia() {

    var dia = $(this).find("option:selected").val();
    var id_sangria = $("#seleccion-sangria").find("option:selected").val();

    $.ajax({
        url: "/SIGIPRO/Caballeriza/Sangria",
        type: "GET",
        data: {"accion": "caballossangriaajax", "dia": dia, "id_sangria": id_sangria},
        dataType: "json",
        success: function (datos) {
            agregar_muestra_caballos(datos);
        }
    });
}

function agregar_muestra_caballos(datos) {
    var lista_caballos = [];
    
    for (i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        lista_caballos.push(elemento.numero);
    }
    
    agregarMuestra();
    $("#identificadores_" + (contador - 1) ).select2("val", lista_caballos);    
}