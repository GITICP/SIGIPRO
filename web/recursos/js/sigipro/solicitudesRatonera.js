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
  $('#observaciones').val("");
  $('#ModalRechazar').modal('show');
}
function entregarSolicitud(id_solicitud, numeroan, peso, numerocajas, sexo, cepa){
  
  $('#numero_animales').val("");
  $('#numero_cajas').val("");
  $('#id_solicitud_ent').val(id_solicitud);
  $('#ModalEntrega').modal('show');
  $('#numsol').val(id_solicitud);
  $('#numan').val(numeroan);
  $('#pesosol').val(peso);
  $('#cajassol').val(numerocajas);
  $('#sexsol').val(sexo);
  $('#cepasol').val(cepa);
}

function confirmarAuth(){
  id_solicitud = $('#id_solicitud_ent').val();
  numero_animales = $('#numero_animales').val();
  peso = $('#peso_entrega').val();
  sexo = $('#sexo').val();
  numero_cajas = $('#numero_cajas').val();
  cepa = $('#id_cepa').val();
  
  $('#id_solicitud_auth2').val(id_solicitud);
  $('#num-sol').val(id_solicitud);
  $('#usr-sol').val(numero_animales);
  $('#prd').val(peso);
  $('#cnt').val(numero_cajas);
  $('#sex').val(sexo);
  $('#cepa').val(cepa);
  $('#usr-sol1').val(numero_animales);
  $('#prd1').val(peso);
  $('#cnt1').val(numero_cajas);
  $('#sex1').val(sexo);
  $('#cepa1').val(cepa);
  
  $('.alert-dismissible').remove();
  $('#ModalEntrega').modal('hide');
  $('#ModalAutorizar').modal('show');
}

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