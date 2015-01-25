function confirmacion(){
  bootbox.dialog({
    title: "Recuperar contrase&ntilde;a",
    message: "<form id='recuperarContrasena' action='RecuperarContrasena' method='post'>\
                <div class='row'>\
                  <div class='form-group'>\
                    <div class='col-sm-1'></div>\
                    <div class='col-sm-10'>\
                      <label>Escriba su correo electr&oacute;nico para recuperar su contrase&ntilde;a.</label>\
                      <div class='input-group'>\
                        <input type='text' placeholder='usuario@icp.ucr.ac.cr' class='form-control' name='correoElectronico'>\
                        <span class='input-group-addon' ><i class='fa fa-at'></i></span>\
                      </div>\
                    <div class='col-sm-1'></div>\
                  </div>\
                </div>\n\
              </form><br>",
    buttons:{
      danger: {
        label: "Cancelar",
        className: "btn-danger"
      },
      success: {
        label: "Confirmar",
        className: "btn-primary",
        callback: function() {
          $("#recuperarContrasena").submit();
        }
      }
    }});
}

function contrasenaCaducada(usuario){
  $('#inputUsuario').val(usuario);
  $('#modalCambiarContrasena').modal('show');
  $('#cambiarContrasena').attr('action', 'RestablecerContrasena');
}