
$("input[name='fecha_nacimiento']").change(function () {
  verificarFechas('fecha_nacimiento', 'fecha_retiro', 'mensajeFechas11', 'Fecha de Nacimiento y Estimada de Retiro:');
  verificarFechas('fecha_nacimiento', 'fecha_ingreso', 'mensajeFechas12', 'Fecha de Nacimiento e Ingreso:');
  verificarFechas('fecha_nacimiento', 'fecha_seleccion', 'mensajeFechas14', 'Fecha de Nacimiento y Preselección:');
})

$("input[name='fecha_retiro']").change(function () {
  verificarFechas('fecha_nacimiento', 'fecha_retiro', 'mensajeFechas11', 'Fecha Estimada de Retiro y Nacimiento:');
  verificarFechas('fecha_seleccion', 'fecha_retiro', 'mensajeFechas22', 'Fecha de Preselección y Estimada de Retiro:');
  verificarFechas('fecha_ingreso', 'fecha_retiro', 'mensajeFechas23', 'Fecha de Ingreso y Estimada de Retiro:');
  })
$("input[name='fecha_ingreso']").change(function () {
  verificarFechas('fecha_nacimiento', 'fecha_ingreso', 'mensajeFechas12', 'Fecha de Ingreso y Nacimiento:');
  verificarFechas('fecha_ingreso', 'fecha_seleccion', 'mensajeFechas32', 'Fecha de Ingreso y Preselección:');
  verificarFechas('fecha_ingreso', 'fecha_retiro', 'mensajeFechas23', 'Fecha de Ingreso y Estimada de Retiro:');
})


$("input[name='fecha_seleccion']").change(function () {
  verificarFechas('fecha_nacimiento', 'fecha_seleccion', 'mensajeFechas14', 'Fecha de Preselección y Nacimiento:');
  verificarFechas('fecha_seleccion', 'fecha_retiro', 'mensajeFechas22', 'Fecha de Preselección y Estimada de Retiro:');
  verificarFechas('fecha_ingreso', 'fecha_seleccion', 'mensajeFechas32', 'Fecha de Ingreso y Preselección:');
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
  if ($('#mensajeFechas11').val() === "" &&
      $('#mensajeFechas12').text() === "" &&
      $('#mensajeFechas14').text() === "" &&
      $('#mensajeFechas22').text() === "" &&
      $('#mensajeFechas23').text() === "" &&
      $('#mensajeFechas32').text() === "")
  {
    if (!$('#formConeja')[0].checkValidity()) {
      $('<input type="submit">').hide().appendTo($('#formConeja')).click().remove();
      $('#formConeja').find(':submit').click();
    }
    else {
      $('#formConeja').submit();
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