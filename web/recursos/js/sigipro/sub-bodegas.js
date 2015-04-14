// Variables globales de tablas
tIngresos = null;
tEgresos = null;
tVer = null;

T_INGRESOS_SELECTOR = "#ingresos-sub-bodegas";
T_EGRESOS_SELECTOR = "#egresos-sub-bodegas";
T_VER_SELECTOR = "#ver-sub-bodegas";

SELECCION_INGRESOS = "#seleccion-usuario-ingreso";
SELECCION_EGRESOS = "#seleccion-usuario-egreso";
SELECCION_VER = "#seleccion-usuario-ver";

validez_form_consumir = false;

$(document).ready(function () {

    $("#subbodegaForm").submit(function () {
        llenarCampoAsociacion('i', T_INGRESOS_SELECTOR, $("#ids-ingresos"));
        llenarCampoAsociacion('e', T_EGRESOS_SELECTOR, $("#ids-egresos"));
        llenarCampoAsociacion('v', T_VER_SELECTOR, $("#ids-ver"));
        $(this).submit();
    });

    eliminarOpciones(T_INGRESOS_SELECTOR, SELECCION_INGRESOS);
    eliminarOpciones(T_EGRESOS_SELECTOR, SELECCION_EGRESOS);
    eliminarOpciones(T_VER_SELECTOR, SELECCION_VER);

    var configuracion = {
        "paging": false,
        "ordering": false,
        "bFilter": false,
        "info": false
    };

    tIngresos = $(T_INGRESOS_SELECTOR).DataTable(configuracion);
    tEgresos = $(T_EGRESOS_SELECTOR).DataTable(configuracion);
    tVer = $(T_VER_SELECTOR).DataTable(configuracion);

    // Ingresar artículos 
    var select_producto = $("#seleccionProducto");
    select_producto.change(function () {
        var opcion = $(this).find(":selected");
        var campoVencimiento = $("#campo-fecha-vencimiento");

        if (opcion.data("perecedero")) {
            campoVencimiento.show();
            campoVencimiento.find('#fechaVencimiento').prop('required', true);
            $("#label-fecha-vencimiento").show();
        } else {
            campoVencimiento.hide();
            campoVencimiento.find('#fechaVencimiento').prop('required', false);
            $("#label-fecha-vencimiento").hide();
        }
    });

    // Consumir artículos
    var select_producto_consumir = $("#seleccion-consumir-producto");
    select_producto_consumir.change(function () {
        var opcion = $(this).find(":selected");
        var campoVencimiento = $("#campo-fecha-vencimiento");

        if (opcion.data("fecha-vecimiento")) {
            campoVencimiento.val(opcion.data("fecha-vecimiento"));
        } else {
            campoVencimiento.val("");
        }
    });

    select_producto.change();

    $('#cantidad').keyup(function () {
        var regex = /^[0-9]+$/;
        if ($(this).val().match(regex)) {
            var cantidad = parseInt($(this).val());
            if( parseInt($('#seleccion-consumir-producto :selected').data('cantidad-disponible')) < cantidad ){
                $('#error-cantidad').text('Debe ser menor que la cantidad de inventario disponible.');
                validez_form_consumir = false;
            } else {
                $('#error-cantidad').text('');
            validez_form_consumir = true;
            }
        } else {
            $('#error-cantidad').text('Debe ser un número');
            validez_form_consumir = false;
        }
    });
    
    $('#form-consumir-inventario-sub-bodega').submit(function(){
        return validez_form_consumir;
    });
});

// -- Ingresos -- //

function agregarUsuarioIngresos() {
    var usuarioSeleccionado = $(SELECCION_INGRESOS + " :selected");
    usuarioSeleccionado.remove();
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioIngreso(" + usuarioSeleccionado.val() + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tIngresos.row.add([usuarioSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "ingreso-" + usuarioSeleccionado.val());
}

function eliminarUsuarioIngreso(id) {
    var fila = tIngresos.row('#ingreso-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();

    $(SELECCION_INGRESOS).append(nuevaOpcion);
}

// -- Egresos -- //

function agregarUsuarioEgresos() {
    var usuarioSeleccionado = $(SELECCION_EGRESOS + " :selected");
    usuarioSeleccionado.remove();
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioEgreso(" + usuarioSeleccionado.val() + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tEgresos.row.add([usuarioSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "egreso-" + usuarioSeleccionado.val());
}

function eliminarUsuarioEgreso(id) {
    var fila = tEgresos.row('#egreso-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();

    $(SELECCION_EGRESOS).append(nuevaOpcion);
}

// -- Ver -- //

function agregarUsuarioVer() {
    var usuarioSeleccionado = $(SELECCION_VER + " :selected");
    usuarioSeleccionado.remove();
    var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioVer(" + usuarioSeleccionado.val() + ")>");
    botonEliminar.text("Eliminar");

    var nuevaFila = tVer.row.add([usuarioSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

    $(nuevaFila).attr("id", "ver-" + usuarioSeleccionado.val());
}

function eliminarUsuarioVer(id) {
    var fila = tVer.row('#ver-' + id);

    var nuevaOpcion = $('<option>');
    nuevaOpcion.val(id);
    nuevaOpcion.text(fila.data()[0]);

    fila.remove().draw();

    $(SELECCION_VER).append(nuevaOpcion);
}

// -- Compartido -- //

function llenarCampoAsociacion(string_pivote, tabla, campo_escondido) {
    var asociacionCodificada = "";
    var pivote = "#" + string_pivote + "#";
    $(tabla).find("tr[id]").each(function () {
        var asociacion = pivote + $(this).attr('id').split("-")[1];
        asociacionCodificada += asociacion;
    });
    campo_escondido.val(asociacionCodificada);
}

function eliminarOpciones(selector_tabla, selector_seleccion) {
    var elemento_seleccion = $(selector_seleccion);
    $(selector_tabla).find('tr[id]').each(function () {
        var id = $(this).attr('id').split('-')[1];
        elemento_seleccion.find('option[value=' + id + ']').remove();
    });
}
