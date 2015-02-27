function AprobarSolicitud(id_solicitud){
    var elementoConfirmable = $('.confirmableAprobar');
    var textoConfirmacion = elementoConfirmable.data('texto-confirmacion');
    var referencia = elementoConfirmable.data('href');
    $('#ModalEliminarGenerico').remove();
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
                  <div class='form-group'>\
                    <div class='modal-footer'>\
                      <button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-times-circle'></i> Cancelar</button>\
                      <a href='" + referencia + id_solicitud + "' class='btn btn-primary'><i class='fa fa-check-circle'></i> Confirmar</a>\
                    </div>\
                  </div>\
              </div>\
            </div>\
          </div>\
        </div>");
    $("#ModalEliminarGenerico").modal('show');
}

function RechazarSolicitud(id_solicitud){
  $('#id_solicitud_rech').val(id_solicitud);
  $('#ModalRechazar').modal('show');
}


function confirmarAuth(id_solicitud){
  $('#id_solicitud_auth2').val(id_solicitud);
  $('.alert-dismissible').remove();
  $('#ModalAutorizar').modal('show');
}

$(document).ready(function () {
  if ($('#form_modalautorizar').data('show-auth'))
  { 
    var id_solicitud = $('#id_solicitud_auth').val();
    $('#id_solicitud_auth2').val(id_solicitud);
    $('#ModalAutorizar').modal('show');  
  }
});

$('#cantidadinput').change( function() {
  var max = $('option:selected').data('stock');
  $("input[name='cantidad']").attr("max", max);
}
        );

$(document).ready(function () {

         table = $('#tabladeSolicitudes').DataTable();
         table.destroy();

         table = $('#tabladeSolicitudes').DataTable( { 
           sDom: // redefine sDom without lengthChange and default search box
              "t" +
              "<'row'<'col-sm-6'i><'col-sm-6'p>>",
          "order": [[ 0, "desc" ]]
    } );
}
);