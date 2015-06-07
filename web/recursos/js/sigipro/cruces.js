/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$("input[name='fecha_cruce']").change(function () {
  verificarFechas('fecha_cruce', 'fecha_estimada_parto', 'mensajeFechas1', 'Fecha de Cruce y Fecha Estimada de Parto:');
  verificarFechas('fecha_cruce', 'fecha_parto', 'mensajeFechas2', 'Fecha de Cruce y Fecha de Parto:');
})
$("input[name='fecha_parto']").change(function () {
  verificarFechas('fecha_cruce', 'fecha_parto', 'mensajeFechas2', 'Fecha de Cruce y Fecha de Parto:');
})
$("input[name='fecha_estimada_parto']").change(function () {
  verificarFechas('fecha_cruce', 'fecha_estimada_parto', 'mensajeFechas1', 'Fecha de Cruce y Fecha Estimada de Parto:');
})

$("input[name='fecha_cruce']").change(function () {
  var fechaact = $('#fecha_cruce');
  var fechadesact = $('#fecha_estimada_parto');
  if (fechadesact.val() ===''){
   var datenueva = addDays(fechaact.val(),30);
   fechadesact.val(datenueva);
   fechadesact.datepicker("setDate", datenueva);
  }
});

$(document).ready(function () {
  var fechaact = $('#fecha_cruce');
  var fechaparto1 = $('#fecha_estimada_parto');
  var fechaparto2 = $('#fecha_parto');
  if (fechaact.val() !==''){
    fechaparto1.datepicker({startDate:fechaact.val()});
    fechaparto2.datepicker({startDate:fechaact.val()});
  }
})

function addDays(strDate, days){
    var fecha = strDate.split("/");
    var theDate = new Date(fecha[2],fecha[1] - 1,fecha[0]);
    var newDate = new Date(theDate);
    newDate.setDate(theDate.getDate()+days);
    return newDate;
};

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
    if (!$('#formCruce')[0].checkValidity()) {
      $('<input type="submit">').hide().appendTo($('#formCruce')).click().remove();
      $('#formCruce').find(':submit').click();
    }
    else {
      $('#formCruce').submit();
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