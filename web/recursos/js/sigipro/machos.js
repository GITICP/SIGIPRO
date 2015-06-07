
$("input[name='fecha_ingreso']").change(function () {
  verificarFechas('fecha_ingreso', 'fecha_retiro', 'mensajeFechas1', 'Fecha de Ingreso y Fecha de Retiro:');
  verificarFechas('fecha_ingreso', 'fecha_preseleccion', 'mensajeFechas2', 'Fecha de Ingreso y Fecha de Preselección:');
})
$("input[name='fecha_retiro']").change(function () {
  verificarFechas('fecha_ingreso', 'fecha_retiro', 'mensajeFechas1', 'Fecha de Ingreso y Fecha de Retiro:');
})
$("input[name='fecha_preseleccion']").change(function () {
  verificarFechas('fecha_ingreso', 'fecha_preseleccion', 'mensajeFechas2', 'Fecha de Ingreso y Fecha de Preselección:');
})

function verificarFechas(fechai, fechaf, mensaje, headms) {
  var fechaact = document.getElementById(fechai).value.split("/");
  var fechadesact = document.getElementById(fechaf).value.split("/");
  var mensajeFechas = $('#' + mensaje);
  var flag = $("input[name='flag']").val();
  mensajeFechas.html("");
  if ((parseInt(fechadesact[0]) + parseInt(fechadesact[1]) * 100 + parseInt(fechadesact[2]) * 10000) < (parseInt(fechaact[0]) + parseInt(fechaact[1]) * 100 + parseInt(fechaact[2]) * 10000))
  {
    mensajeFechas.html(headms + " Existe un error con las fechas, favor revisar");
    // document.getElementById(fechaf).value = "";

  }
};
function confirmar() {
  if ($('#mensajeFechas1').val() === "" &&
      $('#mensajeFechas2').text() === "")
  {
    if (!$('#form-macho')[0].checkValidity()) {
      $('<input type="submit">').hide().appendTo($('#form-macho')).click().remove();
      $('#form-macho').find(':submit').click();
    }
    else {
      $('#form-macho').submit();
    }
  }
  else
  {//    e.preventDefault();
    $('#ModalFechas').remove();
    $('body').append("<div class='modal fade' id='ModalFechas' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>\
          <div class='modal-dialog'>\
            <div class='modal-content'>\
              <div class='modal-header'>\
                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>\
                <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>\
              </div>\
              <div class='modal-body'>\
                  <h5 class='title'> Todavía existen errores con campos. Revise información faltante o erronea</h5>\
                  <br>\
                  <div class='form-group'>\
                    <div class='modal-footer'>\
                      <button type='button' class='btn btn-primary' data-dismiss='modal'><i class='fa fa-times-circle'></i> Confirmar</button>\
                    </div>\
                  </div>\
              </div>\
            </div>\
          </div>\
        </div>");
    $("#ModalFechas").modal('show');
    
  }
};