/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//Cuando se cambia la fecha de desactivacion, se verifica su validez.
$("input[name='fecha_apaf']").change(function () {
  verificarFechas('fecha_apai', 'fecha_apaf', 'mensajeFechas1', 'Fechas de Apareamiento:')
}
);
$("input[name='fecha_elimf']").change(function () {
  verificarFechas('fecha_elimi', 'fecha_elimf', 'mensajeFechas2', 'Fechas de Eliminación de Machos:')
}
);
$("input[name='fecha_elihf']").change(function () {
  verificarFechas('fecha_elihi', 'fecha_elihf', 'mensajeFechas3', 'Fechas de Eliminación de Hembras:')
}
);
$("input[name='fecha_repof']").change(function () {
  verificarFechas('fecha_repoi', 'fecha_repof', 'mensajeFechas4', 'Fechas de Reposición de Ciclo:')
}
);
$("input[name='fecha_selnf']").change(function () {
  verificarFechas('fecha_selni', 'fecha_selnf', 'mensajeFechas5', 'Fechas de Selección de Machos y Hembras:')
}
);
$("input[name='fecha_apai']").change(function () {
  var fechaact = $('#fecha_apai');
  var fechadesact = $('#fecha_apaf');
  if (fechadesact.val() ===''){
   var datenueva = addDays(fechaact.val(),4);
   fechadesact.val(datenueva);
   fechadesact.datepicker("setDate", datenueva);
  }
  verificarFechas('fecha_apai', 'fecha_apaf', 'mensajeFechas1', 'Fechas de Apareamiento:')
}
);
$("input[name='fecha_elimi']").change(function () {
  var fechaact = $('#fecha_elimi');
  var fechadesact = $('#fecha_elimf');
  if (fechadesact.val() ===''){
   var datenueva = addDays(fechaact.val(),4);
   fechadesact.val(datenueva);
   fechadesact.datepicker("setDate", datenueva);
  }
  verificarFechas('fecha_elimi', 'fecha_elimf', 'mensajeFechas2', 'Fechas de Eliminación de Machos:')
}
);
$("input[name='fecha_elihi']").change(function () {
  var fechaact = $('#fecha_elihi');
  var fechadesact = $('#fecha_elihf');
  if (fechadesact.val() ===''){
   var datenueva = addDays(fechaact.val(),4);
   fechadesact.val(datenueva);
   fechadesact.datepicker("setDate", datenueva);
  }
  verificarFechas('fecha_elihi', 'fecha_elihf', 'mensajeFechas3', 'Fechas de Eliminación de Hembras:')
}
);
$("input[name='fecha_repoi']").change(function () {
  var fechaact = $('#fecha_repoi');
  var fechadesact = $('#fecha_repof');
  if (fechadesact.val() ===''){
   var datenueva = addDays(fechaact.val(),4);
   fechadesact.val(datenueva);
   fechadesact.datepicker("setDate", datenueva);
  }
  verificarFechas('fecha_repoi', 'fecha_repof', 'mensajeFechas4', 'Fechas de Reposición de Ciclo:')
}
);
$("input[name='fecha_selni']").change(function () {
  var fechaact = $('#fecha_selni');
  var fechadesact = $('#fecha_selnf');
  if (fechadesact.val() ===''){
   var datenueva = addDays(fechaact.val(),4);
   fechadesact.val(datenueva);
   fechadesact.datepicker("setDate", datenueva);
  }
  verificarFechas('fecha_selni', 'fecha_selnf', 'mensajeFechas5', 'Fechas de Selección de Machos y Hembras:')
}
);

function addDays(strDate, days) {
    var fecha = strDate.split("/");
    var theDate = new Date(fecha[2],fecha[1] - 1,fecha[0]);
    var newDate = new Date(theDate);
    newDate.setDate(theDate.getDate()+4);
    return newDate; //newDate.getDate()+'/'+ (newDate.getMonth() +1)+'/'+ newDate.getFullYear();
}

function verificarFechas(fechai, fechaf, mensaje, headms) {
  var fechaact = document.getElementById(fechai).value.split("/");
  var fechadesact = document.getElementById(fechaf).value.split("/");
  var mensajeFechas = $('#' + mensaje);
  var flag = $("input[name='flag']").val();
  mensajeFechas.html("");
  if ((parseInt(fechadesact[0]) + parseInt(fechadesact[1]) * 100 + parseInt(fechadesact[2]) * 10000) < (parseInt(fechaact[0]) + parseInt(fechaact[1]) * 100 + parseInt(fechaact[2]) * 10000))
  {
    mensajeFechas.html(headms + " La fecha final debe ser mayor a la fecha de inicio");
    // document.getElementById(fechaf).value = "";

  }
};

function confirmar() {
  if ($('#mensajeFechas1').text() === "" &&
          $('#mensajeFechas2').text() === "" &&
          $('#mensajeFechas3').text() === "" &&
          $('#mensajeFechas4').text() === "" &&
          $('#mensajeFechas5').text() === "")
  {
    if (!$('#formCara')[0].checkValidity()) {
      $('<input type="submit">').hide().appendTo($('#formCara')).click().remove();
      $('#formCara').find(':submit').click();
    }
    else {
      $('#formCara').submit();
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
                  <h5 class='title'> Las fechas finales deben ser posteriores a las fechas de inicio.</h5>\
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