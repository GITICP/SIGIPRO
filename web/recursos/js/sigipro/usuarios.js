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

function confirmacionEditarUsuario() {
  rolesCodificados = "";
  $('#datatable-column-filter-roles > tbody > tr').each(function ()

  {
    fila = $(this);
    rolesCodificados += fila.attr('id');
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(1).text();
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(2).text();
    rolesCodificados += "#r#";
  });
  $('#rolesUsuario').val(rolesCodificados.slice(0, -3));

  if (!$('#editarUsuario')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#editarUsuario')).click().remove();
    $('#editarUsuario').find(':submit').click();
  }
  else {
    $('#editarUsuario').submit();
  }
}
