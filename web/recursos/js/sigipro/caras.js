/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//Cuando se cambia la fecha de desactivacion, se verifica su validez.
$("input[name='fecha_apaf']").change(function () {verificarFechas('fecha_apai','fecha_apaf','mensajeFechas1', 'Fechas de Apareamiento:')}
);
$("input[name='fecha_elimf']").change(function () {verificarFechas('fecha_elimi','fecha_elimf','mensajeFechas2', 'Fechas de Eliminación de Machos:')}
);
$("input[name='fecha_elihf']").change(function () {verificarFechas('fecha_elihi','fecha_elihf','mensajeFechas3', 'Fechas de Eliminación de Hembras:')}
);
$("input[name='fecha_repof']").change(function () {verificarFechas('fecha_repoi','fecha_repof','mensajeFechas4', 'Fechas de Reposición de Ciclo:')}
);
$("input[name='fecha_selnf']").change(function () {verificarFechas('fecha_selni','fecha_selnf','mensajeFechas5', 'Fechas de Selección de Machos y Hembras:')}
);

function verificarFechas(fechai, fechaf, mensaje , headms) {
  var fechaact = document.getElementById(fechai).value.split("/");
  var fechadesact = document.getElementById(fechaf).value.split("/");
  var mensajeFechas = $('#'+mensaje);
  mensajeFechas.html("");
  if ((parseInt(fechadesact[0]) + parseInt(fechadesact[1]) * 100 + parseInt(fechadesact[2]) * 10000) < (parseInt(fechaact[0]) + parseInt(fechaact[1]) * 100 + parseInt(fechaact[2]) * 10000))
  {
    mensajeFechas.html(headms+" La fecha final debe ser mayor a la fecha de inicio");
   // document.getElementById(fechaf).value = "";
  }
};
//
//$('#formCara').submit( function (e){ 
//  if ($('#mensajeFechas1').val() === "" &&
//          $('#mensajeFechas2').val() === "" && 
//          $('#mensajeFechas3').val() === "" &&
//          $('#mensajeFechas4').val() === "" &&
//          $('#mensajeFechas5').val() === "")
//  { $('#formCara').submit()}
//  else
//  {e.preventDefault(); }
//  })