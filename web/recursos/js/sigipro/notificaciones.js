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
var numeroNotificacionesNuevasActuales;
if (parseInt(document.getElementById("numero_notificaciones").innerHTML) > 0){
    numeroNotificacionesNuevasActuales = parseInt(document.getElementById("numero_notificaciones").innerHTML);
}
else{
    numeroNotificacionesNuevasActuales = 0;
}

var numeroNotificacionesNuevasActualesEnTotales;

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
                if (numeroNotificacionesNuevasActuales < parseInt(numeroNotificaciones)){
                    recargarNotificaciones(parseInt(numeroNotificaciones));
                    document.getElementById("numero_notificaciones").innerHTML = numeroNotificaciones;
                    
                    crearNotificacionPush(numeroNotificaciones + " notificaciones nuevas.");
                }
                else{
                    crearNotificacionPush(numeroNotificaciones + " notificaciones nuevas.");
                }
            }
        }
    };
    enviarPeticionXHTTP("notificacionesNuevas");
}

function recargarNotificacionesNuevasEnTotales(notificacionesACargar){
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
            var idn;
            for (var i = 0; i < notificacionesACargar - numeroNotificacionesNuevasActualesEnTotales; i++) {   
                notificacion = notificaciones[i];
                idn = notificacion.getElementsByTagName('id')[0].firstChild.nodeValue;
                descripcion = notificacion.getElementsByTagName('descripcion')[0].firstChild.nodeValue;
                icono = notificacion.getElementsByTagName('icono')[0].firstChild.nodeValue;
                datetime = notificacion.getElementsByTagName('datetime')[0].firstChild.nodeValue;
                redirect = notificacion.getElementsByTagName('redirect')[0].firstChild.nodeValue;
                nuevaNotificacionATotales(idn, redirect, icono, descripcion, datetime);
            }
        }
    };
    enviarPeticionXHTTP("notificaciones");
}

function recargarNotificaciones(notificacionesACargar){
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
            var idn;
            //Quitar las últimas notificaciones anteriores 
            $("#notificaciones-dropdown li").last().remove(); //remover el footer
            $("#notificaciones-dropdown li").first().remove(); //remover el header
            var lis = $("#notificaciones-dropdown li").length;
            while (lis + notificacionesACargar - numeroNotificacionesNuevasActuales > 10){
                $("#notificaciones-dropdown li").eq(lis-1).remove();
                lis = lis - 1;
            }
            for (var i = 0; i < notificacionesACargar - numeroNotificacionesNuevasActuales; i++) {   
                notificacion = notificaciones[i];
                idn = notificacion.getElementsByTagName('id')[0].firstChild.nodeValue;
                descripcion = notificacion.getElementsByTagName('descripcion')[0].firstChild.nodeValue;
                icono = notificacion.getElementsByTagName('icono')[0].firstChild.nodeValue;
                datetime = notificacion.getElementsByTagName('datetime')[0].firstChild.nodeValue;
                redirect = notificacion.getElementsByTagName('redirect')[0].firstChild.nodeValue;
                nuevaNotificacionACampana(idn, redirect, icono, descripcion, datetime);
            }
            
            if (window.location.pathname === '/SIGIPRO/Inicio/Notificaciones/'){
                numeroNotificacionesNuevasActualesEnTotales = numeroNotificacionesNuevasActuales;
                recargarNotificacionesNuevasEnTotales(notificacionesACargar);
            }
            
            numeroNotificacionesNuevasActuales = notificacionesACargar;
            //append footer
            $("#notificaciones-dropdown").append("<li class='notification-footer'>\
                        <center><u><a href='/SIGIPRO/Inicio/Notificaciones'>Ver todas las notificaciones</a></u></center>\
                    </li>");
            //prepend header
            $("#notificaciones-dropdown").prepend("<li class='notification-header'><center><em id='notificaciones-header-dropdown'>Notificaciones recientes</em></center></li>");
        }
    };
    enviarPeticionXHTTP("notificaciones");
}

function marcarNotificacionesleidas(id){
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
        } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            alert("Notificacion con id = "+id+", fue marcada como leida.");
        }
    };
    enviarPeticionXHTTP("marcarNotificaciones?id="+id);
}

function enviarPeticionXHTTP(path){
    var pathArray = window.location.pathname.split( '/' );
    if (pathArray[pathArray.length-1] === '/'){
        pathArray.pop();
    }
    if (pathArray.length === 3){
        xhttp.open("GET", path, true);
        xhttp.send();
    }
    if (pathArray.length > 3){
        var carpetasEnElPath = pathArray.length;
        var irAtras = "";
        while(!(carpetasEnElPath < 3)){
            irAtras += "../";
            carpetasEnElPath = carpetasEnElPath - 2;
        }
        xhttp.open("GET", irAtras + path, true);
        xhttp.send();
    }
}

function nuevaNotificacionACampana(id, redirect, icono, descripcion, datetime){
    $("#notificaciones-dropdown").prepend("<li onclick='marcarNotificacionesleidas("+id+")'><a href='/SIGIPRO" + redirect + "'>\
                <i class='" + icono + "'></i>\
                <b><span class='text'>" + descripcion + "</span></b>\
                <span class='timestamp'> - " + datetime + "</span>\
            </a>\
        </li>");
}

function nuevaNotificacionATotales(id, redirect, icono, descripcion, datetime){
    $("#notificaciones-totales").prepend("<li onclick='marcarNotificacionesleidas("+id+")'><i class='" + icono + " activity-icon pull-left'></i>\
                <p><b><a href='/SIGIPRO" + redirect + "'>" + descripcion + "</a></b>\
                <span class='timestamp'>" + datetime + "</span></p>\
        </li>");      
}

function crearNotificacionPush(texto){
    $("#campana_notificaciones").notify(texto,"success", { clickToHide: true , autoHide: false , arrowSize: 10});
}