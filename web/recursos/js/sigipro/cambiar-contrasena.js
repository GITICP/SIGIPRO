function cambiarContrasena(usuario){
  bootbox.dialog({
    title: "Cambiar contrase&ntilde;a",
    message: "<form id='cambiarContrasena' action='/SIGIPRO/Cuenta/CambiarContrasena' method='post'>\
                <div class='row'>\
                  <div class='form-group'>\
                    <div class='col-sm-1'></div>\
                    <input type='hidden' placeholder='Contrase&ntilde;a' class='form-control' name='usuario' value='" + usuario + "'>\
                    <div class='col-sm-10'>\
                      <label>Indique su nueva contrase&ntilde;a.</label>\
                      <div class='input-group' style='display:table'>\
                        <input type='password' placeholder='Contrase&ntilde;a' class='form-control' name='contrasenna'>\
                        <span class='input-group-addon'><i class='fa fa-lock'></i></span>\
                      </div>\
                      <label>Confirme su contrase&ntilde;a.</label>\
                      <div class='input-group' style='display:table'>\
                        <input type='password' placeholder='Contrase&ntilde;a' class='form-control' name='confirmacion'>\
                        <span class='input-group-addon'><i class='fa fa-lock'></i></span>\
                      </div>\
                    </div>\
                    <div class='col-sm-1'></div>\
                    </div>\
                  </div>\
                </div>\
              </form>",
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