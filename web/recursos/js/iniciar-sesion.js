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
  bootbox.dialog({
    title: "Reestablecer contras&ntilde;a",
    message: "<form id='reestablecerContrasena' action='ReestablecerContrasena' method='post'>\
                <div class='form-group'>\
                  <div class='col-sm-1'></div>\
                  <input type='hidden' placeholder='Contrase&ntilde;a' class='form-control' name='usuarioCaducado' value='" + usuario + "'>\
                  <div class='col-sm-10'>\
                    <label>Indique su nueva contrase&ntilde;a.</label>\
                    <div class='input-group'>\
                      <input type='password' placeholder='Contrase&ntilde;a' class='form-control' name='contrasenna'>\
                      <span class='input-group-addon'><i class='fa fa-lock'></i></span>\
                    </div>\
                  </div>\
                  <br>\
                  <br>\
                  <br>\
                  <div class='form-group'>\
                  <div class='col-sm-1'></div>\
                  <div class='col-sm-10'>\
                    <label>Confirme su contrase&ntilde;a.</label>\
                    <div class='input-group'>\
                      <input type='password' placeholder='Contrase&ntilde;a' class='form-control' name='confirmacion'>\
                      <span class='input-group-addon'><i class='fa fa-lock'></i></span>\
                    </div>\
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
          $("#reestablecerContrasena").submit();
        }
      }
    }});
}