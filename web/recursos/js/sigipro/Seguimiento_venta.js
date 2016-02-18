/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function(){ /* DOM ready */
    $("#id_cliente").change(function () {
        
        var select = document.getElementById("id_cliente");
        var indice = select.selectedIndex;
        var id_cliente = select.options[indice].value;
        
        var facturas = document.getElementById("id_factura");
        //se muestran todas las facturas
        for (i = 0; i<facturas.length; i++){
            facturas.options[i].disabled = false;
            //alert("factura mostrada = "+facturas.options[i].value);
        }
        
        //se esconden todas las que no tengan el id_cliente en sus atributos
        //alert("id_cliente = "+id_cliente);
        for (i = 0; i<facturas.length; i++){
            var data_cliente = facturas.options[i].getAttribute('data-cliente');
            //alert("factura = "+facturas.options[i].value +",data_cliente = "+data_cliente);
            if (data_cliente !== id_cliente)
                facturas.options[i].disabled = true;
                //alert("factura oculta = "+facturas.options[i].value);
        }
        

    });
});