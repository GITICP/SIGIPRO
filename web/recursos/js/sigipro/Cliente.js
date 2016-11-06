/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var xhttp;
var xmlDoc;

$(function(){ /* DOM ready */ //
    $("#nombre").blur(function () {
        alert("entró a change nombre");
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
            } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xhttp.onreadystatechange = function() {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                var resultado = xhttp.responseText;
                alert("Resultado = "+resultado);
                if (resultado === "existe"){
                    alert("Resultado = "+resultado);
                    document.getElementById("nombreCorrecto").style.display = "none";
                    document.getElementById("cedulaCorrecta").style.display = "none";
                    document.getElementById("nombreInCorrecto").style.display = "block";
                    document.getElementById("cedulaInCorrecta").style.display = "block";
                    //document.getElementById("nombre")
                }
                else{
                    alert("Resultado = "+resultado);
                    document.getElementById("nombreCorrecto").style.display = "block";
                    document.getElementById("cedulaCorrecta").style.display = "block";
                    document.getElementById("nombreInCorrecto").style.display = "none";
                    document.getElementById("cedulaInCorrecta").style.display = "none";
                }
            }
        };
        enviarPeticionXHTTP("AJAXCedulaNombre");
    }).change();
    
    $("#cedula").blur(function () {
        alert("entró a change cedula");
        if (window.XMLHttpRequest) {
            xhttp = new XMLHttpRequest();
            } else {
            // code for IE6, IE5
            xhttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        xhttp.onreadystatechange = function() {
            if (xhttp.readyState === 4 && xhttp.status === 200) {
                var resultado = xhttp.responseText;
                if (resultado === "existe"){
                    document.getElementById("nombre").style.color = "yellow";
                    document.getElementById("cedula").style.color = "yellow";
                }
            }
        };
       enviarPeticionXHTTP("AJAXCedulaNombre");
    }).change();
});

function enviarPeticionXHTTP(path){
    xhttp.open("GET", path, true);
    xhttp.send();
}