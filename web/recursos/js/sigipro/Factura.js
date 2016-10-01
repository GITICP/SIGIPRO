/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

	
function addDays(date, days) {
    date.setTime( date.getTime() + days * 86400000 );
    var yyyy = date.getFullYear();
    var mm = date.getMonth()+1;
    if(mm<10){
        mm='0'+mm
    } 
    var dd = date.getDate();
    var newdate = dd+'/'+mm+'/'+yyyy;
    return newdate;
}

var contadorEditar = 0; //Cuando se edita la cotización, se ejecuta la función siguiente 1 vez, lo cual afecta la selección de la orden de compra
$(function(){ /* DOM ready */ //
    $("#moneda").change(function () {
        var moneda = document.getElementById("moneda").value;
        var infofundevi = document.getElementById("Info_Fundevi");
        var tipo = document.getElementById("tipo").value;
        if ((moneda === "Colones" || moneda === "Dólares") && tipo === "FUNDEVI"){
            infofundevi.style.display = 'block';
            document.getElementById("proyecto").required = true;
            document.getElementById("plazo").required = true;
        }
        else{
            infofundevi.style.display = 'none';
            document.getElementById("proyecto").required = false;
            document.getElementById("plazo").required = false;
        }
    }).change();
    
    $("#tipo").change(function () {
        var moneda = document.getElementById("moneda").value;
        var infofundevi = document.getElementById("Info_Fundevi");
        var tipo = document.getElementById("tipo").value;
        if ((moneda === "Colones" || moneda === "Dólares") && tipo === "FUNDEVI"){
            infofundevi.style.display = 'block';
            document.getElementById("proyecto").required = true;
            document.getElementById("plazo").required = true;
        }
        else{
            infofundevi.style.display = 'none';
            document.getElementById("proyecto").required = false;
            document.getElementById("plazo").required = false;
        }
    }).change();
    
    $("#fecha").change(function () {
        var fecha = document.getElementById("fecha").value;
        var fecha_vencimiento = document.getElementById("fecha_vencimiento");
        var plazo = document.getElementById("plazo").value;
        var fv = new Date(fecha.split("/")[2],fecha.split("/")[1]-1,fecha.split("/")[0],0,0,0,0);
        fecha_vencimiento.value = addDays(fv,plazo);
    }).change();
    
    $("#plazo").change(function () {
        var fecha = document.getElementById("fecha").value;
        var fecha_vencimiento = document.getElementById("fecha_vencimiento");
        var plazo = document.getElementById("plazo").value;
        
        var fv = new Date(fecha.split("/")[2],fecha.split("/")[1]-1,fecha.split("/")[0],0,0,0,0);
        
        fecha_vencimiento.value = addDays(fv,plazo);
    }).change();
    
    $("#id_cliente").change(function () {
        var ac = document.getElementById("accion").value;
        
        if(ac === "Editar" && contadorEditar < 1){
            contadorEditar += 1;
        }
        else{
            //Quitar todas las opciones del select de ordenes
            var i;
            var select_orden = document.getElementById("id_orden");
            for(i=select_orden.options.length-1;i>=0;i--)
            {
                select_orden.remove(i);
            }

            //Agregar solo las opciones que contienen el data-cliente que corresponde a id_cliente[selectedindex].value
            var select_cliente = document.getElementById("id_cliente");
            var id_cliente = select_cliente[select_cliente.selectedIndex].value;

            var select_orden_completo = document.getElementById("id_orden_completo");
            var e;
            var opt2 = document.createElement('option');
            opt2.value = "";
            opt2.innerHTML = "";
            select_orden.appendChild(opt2);
            for (e = 0; e < select_orden_completo.length; e++) {
                if (select_orden_completo[e].getAttribute("data-cliente") === id_cliente){
                    var opt = document.createElement('option');
                    opt.value = select_orden_completo[e].value;
                    opt.setAttribute('data-cliente',select_orden_completo[e].getAttribute("data-cliente"));
                    opt.innerHTML = select_orden_completo[e].innerHTML;
                    select_orden.appendChild(opt);
                }
            }

            $("#id_orden").val(" ");
        }
    }).change();
    
});