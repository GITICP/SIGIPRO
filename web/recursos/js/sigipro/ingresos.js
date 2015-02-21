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
      elementoError.text('');
      resultado = true;
    } else {
      elementoError.text('Debe ser un número');
      resultado = true;
    }
    return resultado;
  }

  $("#seleccionProducto").change(function () {
    var opcion = $(this).find(":selected");
    if (opcion.data("cuarentena")) {
      $("#radio-cuarentena").prop("checked", true);
    } else {
      $("#radio-disponible").prop("checked", true);
    }
  });

  agregarValidacionNumero($("#cantidad"), $("#errorCantidad"));
  agregarValidacionNumero($("#precio"), $("#errorPrecio"));

  $("#ingresoForm").submit(function () {
    var validacionCantidad = validar($("#cantidad"), $("#errorCantidad"));
    var validacionPrecio = validar($("#precio"), $("#errorPrecio"));
    var validez = validacionCantidad && validacionPrecio;
    return validez;
  });

  $('#formAprobaciones').find(':radio').each(function () {
    $(this).click(function () {
      /*
      $(this).toggleClass('checked');
      $(this).siblings().each(function () {
        $(this).toggleClass('checked');
      });*/
      if ($(this).prop('previousValue')) {
        $(this).prop('checked', false);
      } else {
        $('input[name=' + $(this).attr('name') + ']').prop('previousValue', false);
      }
      $(this).prop('previousValue', $(this).prop('checked'));
    });
  });
});
