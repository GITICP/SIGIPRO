/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var contadorEditar = 0; //Cuando se edita la cotización, se ejecuta la función siguiente 1 vez, lo cual afecta la selección de la orden de compra
$(function(){ /* DOM ready */ //
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