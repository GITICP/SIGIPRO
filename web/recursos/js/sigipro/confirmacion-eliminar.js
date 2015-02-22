$(document).ready(function () {
  if ($('.confirmable').length > 0) {
    var elementoConfirmable = $('.confirmable')
    var textoConfirmacion = elementoConfirmable.data('texto-confirmacion');
    var referencia = elementoConfirmable.data('href');
    $('body').append("<div class='modal fade' id='ModalEliminarGenerico' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>\
          <div class='modal-dialog'>\
            <div class='modal-content'>\
              <div class='modal-header'>\
                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>\
                <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>\
              </div>\
              <div class='modal-body'>\
                  <h5 class='title'>&iquest;Est&aacute; seguro que desea " + textoConfirmacion + "?</h5>\
                  <br>\
                  <input hidden='false' id='controlIDRol' name='controlIDRol' value='${rol.getID()}'>\
                  <div class='form-group'>\
                    <div class='modal-footer'>\
                      <button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-times-circle'></i> Cancelar</button>\
                      <a href='" + referencia + "' class='btn btn-primary'><i class='fa fa-check-circle'></i> Confirmar</a>\
                    </div>\
                  </div>\
              </div>\
            </div>\
          </div>\
        </div>");
    elementoConfirmable.click(function () {
      $("#ModalEliminarGenerico").modal('show');
    });
  }
});