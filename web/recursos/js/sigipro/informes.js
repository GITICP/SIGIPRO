SELECTOR_TABLA_RESULTADOS_OBTENIDOS = "#resultados-obtenidos";
SELECTOR_TABLA_RESULTADOS_POR_REPORTAR = "#resultados-por-reportar";

TABLA_RESULTADOS_POR_REPORTAR = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_POR_REPORTAR);
TABLA_RESULTADOS_OBTENIDOS = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_OBTENIDOS);


function agregar_fila(tabla, fila) {
    tabla.row.add(fila).draw();
}

function obtener_fila(tabla, selector) {
    return tabla.row(selector);
}

funcion_eliminar = function() {
    
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

funcion_reportar = function() {
    
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

$(document).ready(function () {
    $(".reportar-resultado").each(function () {
        $(this).click(funcion_reportar);
    });
    
    $(".eliminar-resultado").each(function () {
        $(this).click(funcion_eliminar);
    });
});

