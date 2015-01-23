function cambiarContrasena(usuario){
  bootbox.dialog({
    title: "Cambiar contras&ntilde;a",
    message: "<form id='cambiarContrasena' action='/Cuenta/CambiarContrasena' method='post'>\
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
          $("#cambiarContrasena").submit();
        }
      }
    }});
}