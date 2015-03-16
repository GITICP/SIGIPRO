$(document).ready(function () {

    //Definición de funciones
    function agregarValidacionNumero(elemento, elementoError) {
        elemento.keyup(function () {
            validar($(this), elementoError);
        });
    }

    function validar(elementoPrincipal, elementoError) {
        var regex = /^[0-9]+$/;
        var resultado = false;
        if (elementoPrincipal.val().match(regex)) {
            $(elementoError).text('');
            resultado = true;
        } else {
            $(elementoError).text('Debe ser un numero');
        }
        return resultado;
    }

    function ensenarCampoPerecedero(pivote) {
        var campoVencimiento = $("#campo-fecha-vencimiento");
        if (pivote) {
            campoVencimiento.show();
            campoVencimiento.find('#fechaVencimiento').prop('required', true);
            $("#label-fecha-vencimiento").show();
        } else {
            campoVencimiento.hide();
            campoVencimiento.find('#fechaVencimiento').prop('required', false);
            $("#label-fecha-vencimiento").hide();
        }
    }

    $("#seleccionProducto").change(function () {
        var opcion = $(this).find(":selected");
        var perecedero;
        if (opcion.length > 0) {
            if (opcion.data("cuarentena")) {
                $("#radio-cuarentena").prop("checked", true);
            } else {
                $("#radio-disponible").prop("checked", true);
            }
            perecedero = opcion.data("perecedero");
        } else {
            perecedero = $(this).data("perecedero");
        }
        ensenarCampoPerecedero(perecedero);
    });

    agregarValidacionNumero($("#cantidad"), "#errorCantidad");
    agregarValidacionNumero($("#precio"), "#errorPrecio");

    $("#ingresoForm").submit(function () {
        var validacionCantidad = validar($("#cantidad"), $("#errorCantidad"));
        var validacionPrecio = validar($("#precio"), $("#errorPrecio"));
        var validez = validacionCantidad && validacionPrecio;
        return validez;
    });

    $('#formAprobaciones').find(':radio').each(function () {
        $(this).click(function () {
            if ($(this).prop('previousValue')) {
                $(this).prop('checked', false);
            } else {
                $('input[name=' + $(this).attr('name') + ']').prop('previousValue', false);
            }
            $(this).prop('previousValue', $(this).prop('checked'));
        });
    });

    $("#seleccionProducto").change();
    
    $("input[type=radio][value='" + $("#hiddenID").data("estado") + "']").prop('checked', true);
});
