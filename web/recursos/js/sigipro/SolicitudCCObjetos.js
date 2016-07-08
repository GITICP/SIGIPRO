/*
 * Funciones de eventos
 */

$("#seleccion-objeto").change(function () {
    $("#fila-select-sangria").hide();
    $("#fila-select-dia").hide();
    $("#fila-select-sangria-prueba").hide();
    if ($(this).find("option:selected").val() === "sangria") {
        $.ajax({
            url: "/SIGIPRO/Caballeriza/Sangria?tipo=solicitud_cc",
            type: "GET",
            data: {"accion": "sangriasajax"},
            dataType: "json",
            success: function (datos) {
                generar_select_sangria(datos);
            }
        });
    } else if($(this).find("option:selected").val() === "sangria_prueba") {
        $.ajax({
            url: "/SIGIPRO/Caballeriza/SangriaPrueba",
            type: "GET",
            data: {"accion": "sangrias_pruebas_ajax"},
            dataType: "json",
            success: function (datos) {
                generar_select_sangria_prueba(datos);
            }
        });
    } else {
        // Meter el comportamiento de otros objetos como un else if dejar este else de último
        $("#boton-muestra").prop("disabled", false);
    }
});

function generar_select_sangria(datos) {

    $("#fila-select-sangria").show();

    var select_sangria = $("#seleccion-sangria");
    select_sangria.select2();
    
    select_sangria.unbind("change");
    select_sangria.change(evento_seleccionar_sangria);

    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\""+ elemento.id_sangria + "\"";
        if (elemento.fecha_dia1 !== undefined) {
            opcion_string += "data-fecha-1=\"" + elemento.fecha_dia1 +  "\"";
        }
        if (elemento.fecha_dia2 !== undefined) {
            opcion_string += "data-fecha-2=\"" + elemento.fecha_dia2 +  "\"";
        }
        if (elemento.fecha_dia3 !== undefined) {
            opcion_string += "data-fecha-3=\"" + elemento.fecha_dia3 +  "\"";
        }
        opcion_string += ">";
        var opcion = $(opcion_string);
        opcion.text(elemento.identificador);

        select_sangria.append(opcion);
    }
}

function generar_select_sangria_prueba(datos) {

    $("#fila-select-sangria-prueba").show();

    var select_sangria_prueba = $("#seleccion-sangria-prueba");
    select_sangria_prueba.select2();
    
    select_sangria_prueba.unbind("change");
    select_sangria_prueba.change(evento_seleccionar_sangria_prueba);

    for (var i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        var opcion_string = "<option value=\""+ elemento.id_sangria_prueba + "\">";
        var opcion = $(opcion_string);
        opcion.data("caballos", JSON.stringify(elemento.caballos));
        opcion.text(elemento.identificador);

        select_sangria_prueba.append(opcion);
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
    select_dia.unbind("change");
    select_dia.change(evento_seleccionar_dia);

    var opcion_seleccionada = $(this).find("option:selected");

    if (opcion_seleccionada.attr("data-fecha-1") !== undefined && opcion_seleccionada.attr("data-fecha-1") !== "") {
        var opcion = $("<option>");
        opcion.text("Día 1");
        opcion.val("1");
        select_dia.append(opcion);
    }
    if (opcion_seleccionada.attr("data-fecha-2") !== undefined && opcion_seleccionada.attr("data-fecha-2") !== "") {
        var opcion = $("<option>");
        opcion.text("Día 2");
        opcion.val("2");
        select_dia.append(opcion);
    }
    if (opcion_seleccionada.attr("data-fecha-3") !== undefined && opcion_seleccionada.attr("data-fecha-3") !== "") {
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

function evento_seleccionar_sangria_prueba() {
    
    var opcion_sangria_prueba = $("#seleccion-sangria-prueba").find("option:selected");
    var lista_caballos_string = opcion_sangria_prueba.data("caballos");
    if(typeof lista_caballos_string === "string") {
        agregar_muestra_caballos(JSON.parse(lista_caballos_string));
    } else {
        agregar_muestra_caballos(lista_caballos_string);
    }
    
}

function agregar_muestra_caballos(datos) {
    var lista_caballos = [];

    for (i = 0; i < datos.length; i++) {
        var elemento = datos[i];
        lista_caballos.push(elemento.numero);
    }
    
    $(".muestras").children().remove();
    agregarMuestra();
    
    var parametros = jQuery.extend(true, {}, PARAMETROS_SELECT_IDENTIFICADORES);
    parametros.createTag = function(params){return undefined;};
    parametros.tokenSeparators = [];
    
    PARAMETROS_VER = parametros;
    
    var elemento_select_identificadores = $("#identificadores_" + (contador - 1));
    
    elemento_select_identificadores.select2("destroy");
    elemento_select_identificadores.select2(parametros);
    elemento_select_identificadores.select2("val", lista_caballos);
    $("#boton-muestra").prop("disabled", true);
}

$(document).ready(function () {
    var tipo = $("#tipo-edicion").data("tipo");
    if (tipo === "sangria") {
        $("#seleccion-objeto").find("option[value=sangria]").prop("selected", true);
        $("#seleccion-objeto").select2();

        var select_sangria = $("#seleccion-sangria");
        select_sangria.select2();
        select_sangria.change(evento_seleccionar_sangria);

        var select_dia = $("#seleccion-dia");
        select_dia.select2();
        select_dia.change(evento_seleccionar_dia);
    } else if (tipo === "sangria_prueba") {
        $("#seleccion-objeto").find("option[value=sangria_prueba]").prop("selected", true);
        $("seleccion-objeto").select2();
        
        var select_sangria_prueba = $("#seleccion-sangria-prueba");
        select_sangria_prueba.select2();
        select_sangria_prueba.change(evento_seleccionar_sangria_prueba);
    }
});