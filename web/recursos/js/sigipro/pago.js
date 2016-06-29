/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){ /* DOM ready */
    var pagoActual = document.getElementById("pago").value;
    
    $("#id_factura").change(function () {

        var select = document.getElementById("id_factura");
        var indice = select.selectedIndex;
        var factura = select.options[indice];
        var monto_factura = factura.getAttribute('data-monto');
        
        var moneda_factura = factura.getAttribute('data-moneda');
        var texto_monto_moneda = document.getElementById("monto_moneda");
        var texto_pago_moneda = document.getElementById("pago_moneda");
        texto_monto_moneda.innerHTML = "El monto pendiente se encuentra dado en la moneda: "+moneda_factura;
        texto_pago_moneda.innerHTML = "El pago se dará en la moneda: "+moneda_factura;

        var pago = document.getElementById("pago");
        var monto_pendiente = parseInt(monto_factura) - parseInt(pago.value);
        var monto_input = document.getElementById("monto_pendiente");
        monto_input.value = monto_pendiente;
        pago.placeholder = "Monto pendiente: "+monto_pendiente;
        pago.setAttribute("max",parseInt(monto_factura));


    });
    $("#pago").change(function () {

        var accion = document.getElementById("acccion").value;
        //alert(accion);
        if (accion === "Agregar"){
            var select = document.getElementById("id_factura");
            var indice = select.selectedIndex;
            var factura = select.options[indice];
            var monto_factura = factura.getAttribute('data-monto');
            var pago = document.getElementById("pago");
            var monto_input = document.getElementById("monto_pendiente");

            var monto_pendiente = parseInt(monto_factura) - parseInt(pago.value);
            monto_input.value = monto_pendiente;
        }
        else{
            var select = document.getElementById("id_factura");
            var indice = select.selectedIndex;
            var factura = select.options[indice];
            var monto_factura = factura.getAttribute('data-monto');
            var pago = document.getElementById("pago");
            var monto_input = document.getElementById("monto_pendiente");

            var monto_pendiente = parseInt(monto_factura) + parseInt(pagoActual) - parseInt(pago.value);
            monto_input.value = monto_pendiente;
        }
    });
});
