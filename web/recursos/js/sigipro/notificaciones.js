/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/*
var interval = 1000 * 30; //* 5; // where X is your every X minutes

$(document).ready(function (){
    setInterval(function (){
        //$( "#navbar_content" ).load(document.URL + ' #navbar_content');
        $( "#navbar_top" ).load(document.URL + ' #navbar_top');
        //$( "#notificacion-item" ).load(document.URL + ' #notificacion-item');
        //$( "#numero_notificaciones" ).load(document.URL + ' #numero_notificaciones');        
        //document.getElementById("numero_notificaciones").
        //location.reload();
        alert("refresh done");
    }, interval);
});
*/
/*
setInterval(ajax_call, interval); 
*/
/*
function ajax_call() {
    var number_notifications = parseInt(document.getElementById("numero_notificaciones").textContent) + 1;
    document.getElementById("numero_notificaciones").textContent = number_notifications.toString();
    alert("Cambio realizado.");
    if (number_notifications < 10){
        setTimeout(ajax_call(),10000);
    }
};
*/
/*
$(document).ready( function() {
    setTimeout(ajax_call(),5000);
    ajax_call();
} ); */
/*
function doPoll() {
   
   $.get("events.php", {}, function(result) {
      $.each(result.events, function(event) { //iterate over the events
          //do something with your event
      });
      doPoll(); 
      //this effectively causes the poll to run again as
      //soon as the response comes back
   }, 'json'); 
}*/
/*
$(document).ready(function() {
    $.ajaxSetup({
       timeout: 1000*5//set a global AJAX timeout of a minute
    });
    //doPoll(); // do the first poll
    setTimeout(ajax_call(),10000);
});
*/