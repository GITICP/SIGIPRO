/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var xhttp;
var xmlDoc;

$(function(){ /* DOM ready */ //
    $("#nombre").change(function () {
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
            } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xhttp.onreadystatechange = function() {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                var resultado = xhttp.responseText;
                if (resultado === 'ambos'){
                    document.getElementById("nombreCorrecto").style.visibility  = "hidden";
                    document.getElementById("cedulaCorrecta").style.visibility  = "hidden";
                    document.getElementById("nombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "visible";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "visible";
                    //document.getElementById("nombre")
                }
                else if (resultado === 'nombre'){
                    document.getElementById("nombreCorrecto").style.visibility  = "hidden";
                    document.getElementById("nombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("cedulaCorrecta").style.visibility  = "visible";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "hidden";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "hidden";
                }
                else if (resultado === 'cedula'){
                    document.getElementById("nombreCorrecto").style.visibility  = "visible";
                    document.getElementById("nombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("cedulaCorrecta").style.visibility  = "hidden";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "visible";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "visible";
                }
                else{
                    document.getElementById("nombreCorrecto").style.visibility  = "visible";
                    document.getElementById("cedulaCorrecta").style.visibility  = "visible";
                    document.getElementById("nombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "hidden";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "hidden";
                }
            }
        };
        enviarPeticionXHTTP("AJAXCedulaNombre?nombre="+document.getElementById("nombre").value+"&cedula="+document.getElementById("cedula").value);
    }).change();
    
    $("#cedula").change(function () {
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
            } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xhttp.onreadystatechange = function() {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                var resultado = xhttp.responseText;
                if (resultado === 'ambos'){
                    document.getElementById("nombreCorrecto").style.visibility  = "hidden";
                    document.getElementById("cedulaCorrecta").style.visibility  = "hidden";
                    document.getElementById("nombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "visible";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "visible";
                    //document.getElementById("nombre")
                }
                else if (resultado === 'nombre'){
                    document.getElementById("nombreCorrecto").style.visibility  = "hidden";
                    document.getElementById("nombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("cedulaCorrecta").style.visibility  = "visible";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "hidden";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "visible";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "hidden";
                }
                else if (resultado === 'cedula'){
                    document.getElementById("nombreCorrecto").style.visibility  = "visible";
                    document.getElementById("nombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("cedulaCorrecta").style.visibility  = "hidden";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "visible";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "visible";
                }
                else{
                    document.getElementById("nombreCorrecto").style.visibility  = "visible";
                    document.getElementById("cedulaCorrecta").style.visibility  = "visible";
                    document.getElementById("nombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("cedulaInCorrecta").style.visibility  = "hidden";
                    document.getElementById("labelnombreInCorrecto").style.visibility  = "hidden";
                    document.getElementById("labelcedulaInCorrecta").style.visibility  = "hidden";
                }
            }
        };
       enviarPeticionXHTTP("AJAXCedulaNombre?nombre="+document.getElementById("nombre").value+"&cedula="+document.getElementById("cedula").value);
    }).change();
});

function enviarPeticionXHTTP(path){
    xhttp.open("GET", path, true);
    xhttp.send();
}