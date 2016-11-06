function verificarConfirmacion() {
    var contrasenna = $('#contrasenna');
    var confirmacion = $('#confirmacion');
    var mensaje = $('#mensajePassword');

    if (contrasenna.val().length >= 6) {
        if (contrasenna.val() === confirmacion.val()) {
            $('#boton-agregar-usuario').prop('disabled', false);
            mensaje.html("");
        } else {
            $('#boton-agregar-usuario').prop('disabled', true);
            mensaje.html("Contrase&ntilde;as no coinciden");
        }
    } else if (contrasenna.val().length === 0 && confirmacion.val().length === 0) {
        $('#boton-agregar-usuario').prop('disabled', false);
        mensaje.html("");
    } else {
        mensaje.html("La contrase&ntilde;a debe tener al menos 6 caracteres.");
        $('#boton-agregar-usuario').prop('disabled', true);
        return false;
    }
}