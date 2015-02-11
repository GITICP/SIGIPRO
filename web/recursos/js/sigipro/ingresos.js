$(document).ready(function () {
  alert('ingresos called 2');

  agregarValidacionNumero( $("#cantidad"), $("#errorCantidad") );
  agregarValidacionNumero( $("#precio"), $("#errorPrecio"));
  
  alert($("#cantidad"));
  alert($("#precio"));
  alert($("#errorCantidad"));
  alert($("#errorPrecio"));
  
  $("#ingresoForm").submit(function(){ 
    var validacionCantidad = validar( $("#cantidad"), $("#errorCantidad") );
    var validacionPrecio   = validar( $("#precio"), $("#errorPrecio"));
    return validacionCantidad && validacionPrecio;
  });
});

function agregarValidacionNumero(elemento, elementoError) {
  elemento.keyup(function () {
    validar($(this), elementoError);
  });
}

function validar( elementoPrincipal, elementoError ) {
  var regex = /^[0-9]+$/;
  var resultado = false;
  if ( elementoPrincipal.val().match(regex)) {
    elementoError.text('');
    resultado = true;
  } else {
    elementoError.text('Debe ser un número');
    resultado = true;
  }
  return resultado;
}