$(document).ready(function () {

  // aca se leen los json de eventos y se le mandan al calendario en la funcion en la que se inicializa
  var eventosString = $('#data');
  var eventos = eventosString.data('eventos');

 $('#calendar').fullCalendar({
    header: {
      left: 'title',
      center: ' agendaDay, agendaWeek, month',
      right: 'today prev,next'
    },
    events: eventos,
  })
});
 $('td:contains("Todo")' ).html('<span> Todo <br> el d\u00eda </span>'); // --Intento de arreglar el Todo el DÁa

//funciones de checkbox
$("#allday").change(function() {
    if(this.checked) {
        $("#end_date").prop('disabled', true);
        $("#end_time").prop('disabled', true);
        $("#start_time").prop('disabled', true);
        $("#end_date").prop('placeholder', "--");
        $("#end_time").prop('placeholder', "--");
        $("#start_time").prop('placeholder', "--");
    }
    else{
        $("#end_date").prop('disabled', false);
        $("#end_time").prop('disabled', false);
        $("#start_time").prop('disabled', false);
        $("#end_date").prop('placeholder', "Escoja la fecha");
        $("#end_time").prop('placeholder', "Ej. 14:00");
        $("#start_time").prop('placeholder', "Ej. 14:00");
    }
});

$("#shared").change(function() {
    if(this.checked) {
        $("#divquien").prop('hidden', false);
    }
    else{
        $("#divquien").prop('hidden', true);
        $("#divsecciones").prop('hidden', true);
        $("#divusuarios").prop('hidden', true);
        $("#divroles").prop('hidden', true);
    }
});

//funciones de select una opcion de compartir determinada
$("#whotoshare").change(function() {
    if($( "#myselect option:selected" ).text() === "Usuarios") {
        $("#divusuarios").prop('hidden', false);
        $("#divsecciones").prop('hidden', true);
        $("#divroles").prop('hidden', true);
    }
    if ($( "#myselect option:selected" ).text() === "Secciones"){
        $("#divusuarios").prop('hidden', true);
        $("#divsecciones").prop('hidden', false);
        $("#divroles").prop('hidden', true);
    }
    if ($( "#myselect option:selected" ).text() === "Roles"){
        $("#divusuarios").prop('hidden', true);
        $("#divsecciones").prop('hidden', true);
        $("#divroles").prop('hidden', false);
    }
    if ($( "#myselect option:selected" ).text() === "Todos"){
        $("#divusuarios").prop('hidden', true);
        $("#divsecciones").prop('hidden', true);
        $("#divroles").prop('hidden', true);
    }
});