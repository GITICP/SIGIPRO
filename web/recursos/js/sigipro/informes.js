SELECTOR_TABLA_RESULTADOS_OBTENIDOS = "#resultados-obtenidos";
SELECTOR_TABLA_RESULTADOS_POR_REPORTAR = "#resultados-por-reportar";

TABLA_RESULTADOS_POR_REPORTAR = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_POR_REPORTAR);
TABLA_RESULTADOS_OBTENIDOS = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_OBTENIDOS);

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

funcion_eliminar = function () {

    $(this).unbind('click');
    $(this).click(funcion_reportar);

    $(this).text("Reportar Resultado");
    $(this).addClass("btn-primary");
    $(this).removeClass("btn-danger");

    var fila = $(this).parents("tr");
    var fila_data_table = obtener_fila(TABLA_RESULTADOS_POR_REPORTAR, fila);
    $("input[value=" + fila.attr("id") + "]").prop("checked", false);

    var nodo_fila = fila_data_table.node();
    agregar_fila(TABLA_RESULTADOS_OBTENIDOS, nodo_fila);

    fila_data_table.remove();
};

funcion_reportar = function () {
    $(this).unbind('click');
    $(this).click(funcion_eliminar);

    $(this).text("Eliminar de Informe");
    $(this).addClass("btn-danger");
    $(this).removeClass("btn-primary");

    var fila = $(this).parents("tr");
    var fila_data_table = obtener_fila(TABLA_RESULTADOS_OBTENIDOS, fila);
    $("input[value=" + fila.attr("id") + "]").prop("checked", true);

    var nodo_fila = fila_data_table.node();
    agregar_fila(TABLA_RESULTADOS_POR_REPORTAR, nodo_fila);

    fila_data_table.remove();
};

funcion_reportar_sangria = function() {
    var fila = $(this).parents("tr");
    
    id_resultado_seleccionado = fila.attr("id");
    
    $("#modal-asociar-caballo").modal("show");
};

funcion_asociar_resultado_caballos = function(){
    var fila = $("tr[id=" + id_resultado_seleccionado + "]");
    var fila_data_table = obtener_fila(TABLA_RESULTADOS_OBTENIDOS, fila);
    
    var boton = fila.find("button");
    boton.text("Eliminar de Informe");
    boton.addClass("btn-danger");
    boton.removeClass("btn-primary");
    
    var nodo_fila = fila_data_table.node();
    agregar_fila(TABLA_RESULTADOS_POR_REPORTAR, nodo_fila);
    $("input[value=" + fila.attr("id") + "]").prop("checked", true);
    
    fila_data_table.remove();
    
    var input_caballos = $("<input type='hidden'>");
    input_caballos.prop("name", "caballos_res_" + id_resultado_seleccionado);
    input_caballos.prop("value", $("#seleccion-caballos").val());
    
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
        alert(elemento.nombre);
        opcion.val(elemento.id_caballo);
        opcion.text(elemento.nombre + "(" + elemento.numero + ")");
        alert(opcion.text());
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

function asignar_eventos_botones(funcion) {
    if (funcion === "sangria") {
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