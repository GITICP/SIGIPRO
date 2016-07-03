SELECTOR_TABLA_RESULTADOS_OBTENIDOS = "#resultados-obtenidos";
SELECTOR_TABLA_RESULTADOS_POR_REPORTAR = "#resultados-por-reportar";
SELECTOR_TABLA_RESULTADOS_POR_REPORTAR_HEMOGLOBINA = "#resultados-por-reportar-hemoglobina";
SELECTOR_VALIDADOR_CABALLOS = "#caballos-numeros";

SELECTOR_MODAL = "#modal-error";

TABLA_RESULTADOS_POR_REPORTAR = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_POR_REPORTAR, {paging: false, info: false});
TABLA_RESULTADOS_OBTENIDOS = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_OBTENIDOS, {paging: false, info: false});
TABLA_RESULTADOS_POR_REPORTAR_HEMOGLOBINA = inicializar_tabla(SELECTOR_TABLA_RESULTADOS_POR_REPORTAR_HEMOGLOBINA, {paging: false, info: false});

HEMATOCRITO = "Hematocrito";
HEMOGLOBINA = "Hemoglobina";

FLAG_ELIMINAR = 1;
FLAG_REPORTAR = 2;

FLAG_ERROR = 1;
FLAG_ADVERTENCIA = 2;

$(document).ready(function () {

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
        } else {
            // Meter el comportamiento de otros objetos como un else if dejar este else de último
            $("#fila-select-sangria").hide();
            $("#fila-select-dia").hide();
            asignar_eventos_botones(null);
        }
    });

    var tipo_asociacion = $("#flag-asociacion").data("tipo");
    if (tipo_asociacion === "sangria" || tipo_asociacion === "sangria_prueba") {
        $("#seleccion-objeto").find("option[value=sangria]").prop("selected", true);
        $("#seleccion-objeto").select2();

        var select_sangria = $("#seleccion-sangria");
        select_sangria.select2();
        select_sangria.change(evento_seleccionar_sangria);

        var select_dia = $("#seleccion-dia");
        select_dia.select2();
        select_dia.change(evento_seleccionar_dia);

        asignar_eventos_botones("sangria");
        
        funcion_validar = funcion_validar_sangria;
    }

    $(".reportar-resultado").each(function () {
        $(this).unbind("click");
        $(this).click(funcion_reportar);
    });

    $(".eliminar-resultado").each(function () {
        $(this).unbind("click");
        $(this).click(funcion_eliminar);
    });
    
    $("#form-informe").submit(function () {
        var resultado = false;
        var resultado_validacion = funcion_validar();
        if(resultado_validacion.resultado) {
            resultado = true;
        } else {
            abrir_modal(resultado_validacion.tipo);
        }
           return resultado;
    });
    
    $("#generar-de-todas-formas").click(function(){
        $("#form-informe").unbind("submit");
        $("#form-informe").submit();
    });
    
    $("#chk-cerrar").change(function(){
        var por_agregar;
        if ($(this).prop("checked")) {
            por_agregar = "Final";
        } else {
            por_agregar = "Parcial";
        }
        
        var boton = $("#btn-submit-informe span");
        var accion = boton.text().split(" ")[0];
        var texto_final;
        
        if (accion === "Generar") {
            texto_final = "Generar Informe " + por_agregar;
        } else {
            texto_final = "Editar Informe " + por_agregar;
        }
        
        boton.text(texto_final);
        
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

funcion_validar_general = function() {
    if($(SELECTOR_TABLA_RESULTADOS_POR_REPORTAR).find("tbody > tr.fila-resultado").length !== 0){
        return {resultado: true};
    } else {
        $("#label-error").text("Debe elegir al menos un resultado para generar el informe.");
        return {resultado: false, tipo: FLAG_ERROR};
    }
};

funcion_reportar_sangria = function () {
    var fila = $(this).parents("tr");
    var id_resultado_seleccionado = fila.attr("id");
    
    var columna_caballos = fila.find("td:nth-child(2) > span");
    var ids_caballos = [];
    
    columna_caballos.each( function() {
        var numero_caballo = $(this).text().split(" ")[0];
        var id_caballo = obtener_id_caballo(numero_caballo);
        ids_caballos.push( id_caballo );
    });
    
    var resultado_valido = true;
    var ids_caballos_invalidos = [];
    
    $.each(ids_caballos, function(i, id) {
        if (validar_caballo_activo(id)) {
            resultado_valido = false;
            ids_caballos_invalidos.push(id);
        }
    });
    
    if (resultado_valido) {
        
        $.each(ids_caballos, function(i, id) {
            seleccionar_caballo(id);
        });
        
        var boton = fila.find("button");
        desasignar_evento_boton(boton, FLAG_ELIMINAR);
        mover_de_tabla(boton, TABLA_RESULTADOS_OBTENIDOS, TABLA_RESULTADOS_POR_REPORTAR, true);

        var input_caballos = $("<input type='hidden'>");
        input_caballos.prop("name", "caballos_res_" + id_resultado_seleccionado);
        input_caballos.prop("value", ids_caballos.toString());

        var form = $("#form-informe");
        form.prepend(input_caballos);
    } else {
        var numeros_invalidos = [];
        $.each(ids_caballos_invalidos, function(i, id) {
            numeros_invalidos.push(obtener_numero_caballo(id));
        });
        var texto;
        if (numeros_invalidos.length === 1) {
            texto = "Resultado no se puede registrar porque el caballo con el número " + numeros_invalidos[0] + " ya tiene un resultado asignado.";
        } else {
            texto = "Resultado no se puede registrar porque los caballos con los números " + numeros_invalidos.toString() + " ya tienen un resultado asignado.";
        }
        $("#label-error").text(texto);
        $(SELECTOR_MODAL).modal();
    }
    
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
        desseleccionar_caballo(caballos[i]);
    }

    $("input[name=caballos_res_" + id + "]").remove();
};

funcion_validar_sangria = function () {
    
    if($(SELECTOR_TABLA_RESULTADOS_POR_REPORTAR).find("tbody > tr.fila-resultado").length !== 0){
        var elemento_caballos = $(SELECTOR_VALIDADOR_CABALLOS).find("div");
        var caballos_invalidos = [];
        elemento_caballos.each(function() {
            if(!$(this).data("selected")) {
                caballos_invalidos.push($(this).data("numero"));
            }
        });
        
        var texto;
        if (caballos_invalidos.length === 0) {
            return {resultado: true};
        } else if (caballos_invalidos.length === 1) {
            texto = "El caballo con el número " + caballos_invalidos[0] + " aún no tiene un resultado asignado.";
        } else {
            texto = "Los caballos con los números " + caballos_invalidos.toString() + " aún no tienen un resultado asignado.";
        }
        $("#label-error").text(texto);
        return {resultado: false, tipo: FLAG_ADVERTENCIA};
    } else {
        $("#label-error").text("Debe elegir al menos un resultado para generar el informe.");
        return {resultado: false, tipo: FLAG_ERROR};
    }
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
        var opcion_string = "<option value=\"" + elemento.id_sangria + "\"";
        if (elemento.fecha_dia1 !== undefined) {
            opcion_string += "data-fecha-1=\"" + elemento.fecha_dia1 + "\"";
        }
        if (elemento.fecha_dia2 !== undefined) {
            opcion_string += "data-fecha-2=\"" + elemento.fecha_dia2 + "\"";
        }
        if (elemento.fecha_dia3 !== undefined) {
            opcion_string += "data-fecha-3=\"" + elemento.fecha_dia3 + "\"";
        }
        opcion_string += ">";
        var opcion = $(opcion_string);
        opcion.text(elemento.identificador);

        select_sangria.append(opcion);
    }

    asignar_eventos_botones("sangria");
}

function generar_select_caballos(datos) {

    var select_caballos = $("#seleccion-caballos");

    select_caballos.find("option").each(function () {
        $(this).remove();
    });

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

    if (opcion_seleccionada.attr("data-fecha-1") !== undefined && opcion_seleccionada.attr("data-fecha-1") !== "") {
        var opcion = $("<option>");
        opcion.text("D\xEDa 1");
        opcion.val("1");
        select_dia.append(opcion);
    }
    if (opcion_seleccionada.attr("data-fecha-2") !== undefined && opcion_seleccionada.attr("data-fecha-2") !== "") {
        var opcion = $("<option>");
        opcion.text("D\xEDa 2");
        opcion.val("2");
        select_dia.append(opcion);
    }
    if (opcion_seleccionada.attr("data-fecha-3") !== undefined && opcion_seleccionada.attr("data-fecha-3") !== "") {
        var opcion = $("<option>");
        opcion.text("D\xEDa 3");
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

function desasignar_evento_boton(elemento, flag, texto) {
    
    if (texto === undefined) {
        if (flag === FLAG_ELIMINAR) {
            texto = "Eliminar de Informe";
        } else {
            texto = "Reportar Resultado";
        }
    }

    if (flag === FLAG_ELIMINAR) {
        elemento.unbind('click');
        elemento.click(funcion_eliminar);
        elemento.text(texto);
        elemento.addClass("btn-danger");
        elemento.removeClass("btn-primary");
    } else if (flag === FLAG_REPORTAR) {
        elemento.unbind('click');
        elemento.click(funcion_reportar);
        elemento.text(texto);
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
        funcion_validar = funcion_validar_sangria;
    } else if (funcion === "sangria_prueba") {
        funcion_reportar = funcion_reportar_sangria_prueba;
        funcion_eliminar = funcion_eliminar_sangria_prueba;
        funcion_validar = funcion_validar_sangria_prueba;
    } else {
        funcion_eliminar = funcion_eliminar_general;
        funcion_reportar = funcion_reportar_general;
        funcion_validar = funcion_validar_general;
    }
    
    $(".reportar-resultado").each(function () {
        $(this).unbind("click");
        $(this).click(funcion_reportar);
    });

    $(".eliminar-resultado").each(function () {
        $(this).unbind("click");
        $(this).click(funcion_eliminar);
    });
}

function obtener_numero_caballo(id) {
    var consulta = "#" + id;
    var elemento_caballo = $(SELECTOR_VALIDADOR_CABALLOS).find(consulta);
    if (elemento_caballo) {
        return elemento_caballo.data("numero");
    } else {
        alert("Error grave. Refresque la página.");
    }
}

function obtener_id_caballo(numero) {
    var consulta = "div[data-numero=" + numero + "]";
    var elemento_caballo = $(SELECTOR_VALIDADOR_CABALLOS).find(consulta);
    if (elemento_caballo) {
        return elemento_caballo.attr("id");
    } else {
        alert("Error grave. Refresque la página.");
    }
}

function validar_caballo_activo(id) {
    var consulta = "#" + id;
    var elemento_caballo = $(SELECTOR_VALIDADOR_CABALLOS).find(consulta);
    if (elemento_caballo.data("selected")) {
        return true;
    } else {
        return false;
    }
}

function seleccionar_caballo(id) {
    return toggle_caballo(id, true);
}

function desseleccionar_caballo(id) {
    return toggle_caballo(id, false);
}

function toggle_caballo(id, valor) {
    var consulta = "#" + id;
    var elemento_caballo = $(SELECTOR_VALIDADOR_CABALLOS).find(consulta);
    if (elemento_caballo) {
        elemento_caballo.data("selected", valor);
    } else {
        alert("Error grave. Refresque la página.");
    }
}

function abrir_modal(flag) {
    if (flag === FLAG_ERROR) {
        $("#advertencia-submit").hide();
        $("#error").show();
    } else if (flag === FLAG_ADVERTENCIA) {
        $("#error").hide();
        $("#advertencia-submit").show();
    }
    $("#modal-error").modal("show");
}

funcion_eliminar = funcion_eliminar_general;
funcion_reportar = funcion_reportar_general;
funcion_validar = funcion_validar_general;