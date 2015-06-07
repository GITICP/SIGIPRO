
$("input[name='fecha_inicio']").change(function () {
  verificarFechas('fecha_inicio', 'fecha_retiro', 'mensajeFechas1', 'Fecha de Inicio y Fecha Estimada de Retiro:');
})
$("input[name='fecha_retiro']").change(function () {
  verificarFechas('fecha_inicio', 'fecha_retiro', 'mensajeFechas1', 'Fecha de Inicio y Fecha Estimada de Retiro:');
})


function verificarFechas(fechai, fechaf, mensaje, headms) {
  var fechaact = document.getElementById(fechai).value.split("/");
  var fechadesact = document.getElementById(fechaf).value.split("/");
  var mensajeFechas = $('#' + mensaje);
  mensajeFechas.html("");
  if ((parseInt(fechadesact[0]) + parseInt(fechadesact[1]) * 100 + parseInt(fechadesact[2]) * 10000) < (parseInt(fechaact[0]) + parseInt(fechaact[1]) * 100 + parseInt(fechaact[2]) * 10000))
  {
    mensajeFechas.html(headms + " Existe un error con las fechas, favor revisar");
    // document.getElementById(fechaf).value = "";

  }
};
function confirmar() {
  if ($('#mensajeFechas1').html() === "")
  {
    if (!$('#form-asociar')[0].checkValidity()) {
      $('<input type="submit">').hide().appendTo($('#form-asociar')).click().remove();
      $('#form-asociar').find(':submit').click();
    }
    else {
      $('#form-asociar').submit();
    }
  }
  else
  {
  }
};