function verificarConfirmacion(){
  var contrasenna = $('#contrasenna');
  var confirmacion = $('#confirmacion');
  var mensaje = $('#mensajePassword');
  
  if(contrasenna.val().length > 6){
    if(contrasenna.val() === confirmacion.val()){
      $('#btnConfirmarContrasena').prop('disabled', false);
      mensaje.html("");
    }else{
      $('#btnConfirmarContrasena').prop('disabled', true);
      mensaje.html("Contrase&ntilde;as no coinciden");
    }
  }else{
    mensaje.html("La contrase&ntilde;a debe tener al menos 6 caracteres.");
    return false;
  }
}

function confirmarContrasena(){
  $("#cambiarContrasena").submit();
}