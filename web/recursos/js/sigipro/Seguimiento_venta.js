/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){ /* DOM ready */ //
    $("#id_cliente").change(function () {
        //Quitar todas las opciones del select de facturaes
        var i;
        var select_factura = document.getElementById("id_factura");
        for(i=select_factura.options.length-1;i>=0;i--)
        {
            select_factura.remove(i);
        }
        
        //Agregar solo las opciones que contienen el data-cliente que corresponde a id_cliente[selectedindex].value
        var select_cliente = document.getElementById("id_cliente");
        var id_cliente = select_cliente[select_cliente.selectedIndex].value;
        
        var select_factura_completo = document.getElementById("id_factura_completo");
        var e;
        var opt2 = document.createElement('option');
        opt2.value = "";
        opt2.innerHTML = "";
        select_factura.appendChild(opt2);
        for (e = 0; e < select_factura_completo.length; e++) {
            if (select_factura_completo[e].getAttribute("data-cliente") === id_cliente){
                var opt = document.createElement('option');
                opt.value = select_factura_completo[e].value;
                opt.setAttribute('data-cliente',select_factura_completo[e].getAttribute("data-cliente"));
                opt.innerHTML = select_factura_completo[e].innerHTML;
                select_factura.appendChild(opt);
            }
        }
        
        $("#id_factura").val(" ");


    }).change();
});