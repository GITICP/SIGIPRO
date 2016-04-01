/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function comprobarFechas(){
    
    var fecha_i = document.getElementById("fecha_inicial").value.split("/");
    var fecha_r = document.getElementById("fecha_renovacion").value.split("/");
    
    if ((parseInt(fecha_i[0]) + parseInt(fecha_i[1]) * 100 + parseInt(fecha_i[2]) * 10000) > (parseInt(fecha_r[0]) + parseInt(fecha_r[1]) * 100 + parseInt(fecha_r[2]) * 10000))
    {
        document.getElementById("fecha_renovacion").setCustomValidity("La fecha de renovación debe ser mayor que la fecha inicial. ");
    }
    
    else{
        document.getElementById("formContrato").submit();
    }
}
