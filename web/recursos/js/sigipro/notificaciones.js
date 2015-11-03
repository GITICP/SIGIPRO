/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

setInterval(function(){
   revisarNotificacionesNuevas();
}, 1000 * 10);//* 60 * 5);

var xhttp;
var xmlDoc;
function revisarNotificacionesNuevas(){
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
        } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            var numeroNotificaciones = xhttp.responseText;
            if (numeroNotificaciones !== "0"){
                document.getElementById("numero_notificaciones").innerHTML = numeroNotificaciones;
                recargarNotificaciones();
                crearNotificacionPush(numeroNotificaciones + " notificaciones nuevas.");
            }
        }
    };
    enviarPeticionXHTTP("notificacionesNuevas");
}

function recargarNotificaciones(){
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
        } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            xmlDoc = xhttp.responseXML;
            var notificaciones = xmlDoc.getElementsByTagName("notificacion");
            var descripcion;
            var icono;
            var datetime;
            var redirect;
            var notificacion;
            //Quitar notificaciones anteriores 
            document.getElementById("notificaciones-dropdown").innerHTML = '';
            $("#notificaciones-dropdown").append("<li class='notification-header'><em id='notificaciones-header-dropdown'>Notificaciones.</em></li>");
            for (var i = 0; i < notificaciones.length; i++) {   
                notificacion = notificaciones[i];
                descripcion = notificacion.getElementsByTagName('descripcion')[0].firstChild.nodeValue;
                icono = notificacion.getElementsByTagName('icono')[0].firstChild.nodeValue;
                datetime = notificacion.getElementsByTagName('datetime')[0].firstChild.nodeValue;
                redirect = notificacion.getElementsByTagName('redirect')[0].firstChild.nodeValue;
                nuevaNotificacionACampana(redirect, icono, descripcion, datetime);
            }
        }
    };
    enviarPeticionXHTTP("notificaciones");
}

function marcarNotificacionesleidas(){
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
        } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            document.getElementById("numero_notificaciones").innerHTML = '';
        }
    };
    enviarPeticionXHTTP("marcarNotificaciones");
}

function enviarPeticionXHTTP(path){
    var pathArray = window.location.pathname.split( '/' );
    if (pathArray.length === 3){
        xhttp.open("GET", path, true);
        xhttp.send();
    }
    if (pathArray.length > 3){
        xhttp.open("GET", "../"+path, true);
        xhttp.send();
    }
}

function nuevaNotificacionACampana(redirect, icono, descripcion, datetime){
    $("#notificaciones-dropdown").append("<li onclick='marcarNotificacionesleidas()'><a href='/SIGIPRO" + redirect + "'>\
                <i class='" + icono + "'></i>\
                <span class='text'>" + descripcion + "</span>\
                <span class='timestamp'> - " + datetime + "</span>\
            </a>\
        </li>");
}

function crearNotificacionPush(texto){
    $("#campana_notificaciones").notify(texto,"success", {clickToHide: true, arrowSize: 10} );
}