SELECTOR_TABLA_RESULTADOS_OBTENIDOS = "#resultados-obtenidos";
SELECTOR_TABLA_RESULTADOS_POR_REPORTAR = "#resultados-por-reportar";

TABLA_RESULTADOS_POR_REPORTAR = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_POR_REPORTAR);
TABLA_RESULTADOS_OBTENIDOS = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_OBTENIDOS);

FLAG_ELIMINAR = 1;
FLAG_REPORTAR = 2;

$(document).ready(function () {
    $(".reportar-resultado").each(function () {
        $(this).click(funcion_reportar);
    });

    $(".eliminar-resultado").each(function () {
        $(this).click(funcion_eliminar);
    });

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
});

/*
 * Funciones de acción
 */

funcion_reportar_general = function () {
    desasignar_evento_boton($(this), FLAG_ELIMINAR);
    mover_de_tabla($(this), TABLA_RESULTADOS_OBTENIDOS, TABLA_RESULTADOS_POR_REPORTAR, true);
};

funcion_eliminar_general = function () {
    desasignar_evento_boton($(this), FLAG_REPORTAR);
    mover_de_tabla($(this), TABLA_RESULTADOS_POR_REPORTAR, TABLA_RESULTADOS_OBTENIDOS, false);
};

funcion_reportar_sangria = function () {
    var fila = $(this).parents("tr");
    id_resultado_seleccionado = fila.attr("id");
    $("#modal-asociar-caballo").modal("show");
};

funcion_eliminar_sangria = function () {
    desasignar_evento_boton($(this), FLAG_REPORTAR);
    mover_de_tabla($(this), TABLA_RESULTADOS_POR_REPORTAR, TABLA_RESULTADOS_OBTENIDOS, false);
    
    var fila = $(this).parents("tr");
    var id = fila.attr("id");
    
    var caballos_crudo = $("input[name=caballos_res_" + id + "]").val();
    var caballos = caballos_crudo.split(",");
    
    for (var i = 0; i < caballos.length; i++) {
        $("option[value=" + caballos[i] + "]").removeClass("opcion-escondida");
    }
    
    $("input[name=caballos_res_" + id + "]").remove();
};

funcion_asociar_resultado_caballos = function () {
    var fila = $("tr[id=" + id_resultado_seleccionado + "]");
    
    var boton = fila.find("button");
    desasignar_evento_boton(boton, FLAG_ELIMINAR);
    mover_de_tabla(boton, TABLA_RESULTADOS_OBTENIDOS, TABLA_RESULTADOS_POR_REPORTAR, true);

    var input_caballos = $("<input type='hidden'>");
    input_caballos.prop("name", "caballos_res_" + id_resultado_seleccionado);
    input_caballos.prop("value", $("#seleccion-caballos").val());
    
    $("#modal-asociar-caballo").modal("hide");
    
    $("#seleccion-caballos :selected").each(function() {
        $(this).attr("selected", false);
        $(this).addClass("opcion-escondida");
    });
    
    $("#seleccion-caballos").select2();

    var form = $("#form-informe");
    form.prepend(input_caballos);
};

/*
 * Funciones de eventos
 */

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

    asignar_eventos_botones("sangria");
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
            datos_globales = datos;
            generar_select_caballos(datos);
        }
    });
}

/*
 *  Funciones de ayuda
 */

function agregar_fila(tabla, fila) {
    tabla.row.add(fila).draw();
}

function obtener_fila(tabla, selector) {
    return tabla.row(selector);
}

function desasignar_evento_boton(elemento, flag) {

    if (flag === FLAG_ELIMINAR) {
        elemento.unbind('click');
        elemento.click(funcion_eliminar);
        elemento.text("Eliminar de Informe");
        elemento.addClass("btn-danger");
        elemento.removeClass("btn-primary");
    } else if (flag === FLAG_REPORTAR) {
        elemento.unbind('click');
        elemento.click(funcion_reportar);
        elemento.text("Reportar Resultado");
        elemento.addClass("btn-primary");
        elemento.removeClass("btn-danger");
    }
}

function mover_de_tabla(elemento, origen, destino, valor_checkbox) {
    var fila = elemento.parents("tr");
    var fila_data_table = obtener_fila(origen, fila);

    var nodo_fila = fila_data_table.node();
    agregar_fila(destino, nodo_fila);
    fila_data_table.remove();
    
    $("input[value=" + fila.attr("id") + "]").prop("checked", valor_checkbox);
}

function asignar_eventos_botones(funcion) {
    if (funcion === "sangria") {
        funcion_eliminar = funcion_eliminar_sangria;
        funcion_reportar = funcion_reportar_sangria;

        $(".reportar-resultado").each(function () {
            $(this).unbind("click");
            $(this).click(funcion_reportar_sangria);
        });

        $(".eliminar-resultado").each(function () {
            $(this).unbind("click");
            $(this).click(funcion_eliminar_sangria);
        });
    }
}

funcion_eliminar = funcion_eliminar_general;
funcion_reportar = funcion_reportar_general;