/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){ /* DOM ready */
    $("#id_factura").change(function () {

        var select = document.getElementById("id_factura");
        var indice = select.selectedIndex;
        var factura = select.options[indice];
        var monto_factura = factura.getAttribute('data-monto');
        var pago = document.getElementById("pago");
        var monto_pendiente = parseInt(monto_factura) - parseInt(pago.value);
        var monto_input = document.getElementById("monto_pendiente");
        monto_input.value = monto_pendiente;
        pago.placeholder = "Monto pendiente: "+monto_pendiente;
        pago.setAttribute("max",parseInt(monto_factura));


    });
    $("#pago").change(function () {

        var select = document.getElementById("id_factura");
        var indice = select.selectedIndex;
        var factura = select.options[indice];
        var monto_factura = factura.getAttribute('data-monto');
        var pago = document.getElementById("pago");
        var monto_input = document.getElementById("monto_pendiente");
        var monto_pendiente = parseInt(monto_factura) - parseInt(pago.value);
        monto_input.value = monto_pendiente;

    });
});
