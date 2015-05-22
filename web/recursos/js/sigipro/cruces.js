/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
