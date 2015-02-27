$(document).ready(function () {
  if ($('.confirmable').length > 0) {
    var elementoConfirmable = $('.confirmable');
    var textoConfirmacion = elementoConfirmable.data('texto-confirmacion');
    var referencia = elementoConfirmable.data('href');
    $('body').append("<div class='modal fade' id='ModalConfirmacionGenerico' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>\
          <div class='modal-dialog'>\
            <div class='modal-content'>\
              <div class='modal-header'>\
                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>\
                <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>\
              </div>\
              <div class='modal-body'>\
                  <h5 class='title'>&iquest;Est&aacute; seguro que desea " + textoConfirmacion + "?</h5>\
                  <br>\
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
      $("#ModalConfirmacionGenerico").modal('show');
    });
  }
  
  var formsConfirmables = $('.confirmable-form');
  if (formsConfirmables.length > 0) {
    
    function modalFormConfirmable(formId, textoConfirmacion) {
      $('body').append("<div class='modal fade' id='modal-" + formId + "' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>\
          <div class='modal-dialog'>\
            <div class='modal-content'>\
              <div class='modal-header'>\
                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>\
                <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>\
              </div>\
              <div class='modal-body'>\
                  <h5 class='title'>&iquest;Est&aacute; seguro que desea " + textoConfirmacion + "?</h5>\
                  <br>\
                  <div class='form-group'>\
                    <div class='modal-footer'>\
                      <button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-times-circle'></i> Cancelar</button>\
                      <button id='submit-modal-" + formId + "' type='button' class='btn btn-primary'><i class='fa fa-check-circle'></i> Confirmar</a>\
                    </div>\
                  </div>\
              </div>\
            </div>\
          </div>\
        </div>");
      
      $("#submit-modal-" + formId).click(function() {
        $("#" + formId).submit();
      });
      $("#modal-" + formId).modal('show');
    }
    
    formsConfirmables.each(function () {
      var textoConfirmacion = $(this).data('texto-confirmacion');
      var form = $(this).data('form-id');
      $(this).click(function(){
        alert(form);
        alert(textoConfirmacion);
        modalFormConfirmable(form, textoConfirmacion, referencia);
      });
    });
  }
});