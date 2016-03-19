/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(function(){ /* DOM ready */ //Filtrar el select de intenciones según el cliente escogido
    $("#id_cliente").change(function () {

        var select_cliente = document.getElementById("id_cliente");
        var indice_cliente = select_cliente.selectedIndex;
        var opcion_cliente = select_cliente[indice_cliente].val;
        

    });
});

$(function(){ /* DOM ready */ //Filtrar el select de intenciones según el cliente escogido
    $("#id_intencion").change(function () {

        var select_intencion = document.getElementById("id_intencion");
        var indice_intencion = select_intencion.selectedIndex;
        var opcion_intencion = select_intencion.options[indice_intencion].value;
        
        campoOcultoRoles = $('#listaProductos');
        campoOcultoRoles.val("");
        
        $('#datatable-column-filter-productos').dataTable().fnClearTable();
        var table = document.getElementById("datatable-column-filter-productos");
        table.deleteRow(1);
        
        var row_adicional = document.getElementById("datatable-column-filter-productos_length");
        var row_adicional2 = document.getElementById("datatable-column-filter-productos_filter");
        var row_adicional3 = document.getElementById("datatable-column-filter-productos_info");
        var row_adicional4 = document.getElementById("datatable-column-filter-productos_paginate");
        if(row_adicional !== null && row_adicional2 !== null && row_adicional3 !== null && row_adicional4 !== null){
            row_adicional.parentNode.removeChild(row_adicional);
            row_adicional2.parentNode.removeChild(row_adicional2);
            row_adicional3.parentNode.removeChild(row_adicional3);
            row_adicional4.parentNode.removeChild(row_adicional4);
        }
        
        //ajax call
        ajax_productos(opcion_intencion);
        
    });
});
var xhttp;
var xmlDoc;

function ajax_productos(id_intencion){
    if (window.XMLHttpRequest) {
        xhttp = new XMLHttpRequest();
        } else {
        // code for IE6, IE5
        xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    
    xhttp.onreadystatechange = function() {
        if (xhttp.readyState === 4 && xhttp.status === 200) {
            xmlDoc = xhttp.responseXML;
            var producto1 = xmlDoc.getElementsByTagName("producto");
            var id;
            var nombre;
            var lote;
            var cantidad;
            var producto;
            //alert(producto1.length);
            for (var i = 0; i < producto1.length; i++) {   
                producto = producto1[i];
                id = producto.getElementsByTagName('id')[0].firstChild.nodeValue;
                nombre = producto.getElementsByTagName('nombre')[0].firstChild.nodeValue;
                cantidad = producto.getElementsByTagName('cantidad')[0].firstChild.nodeValue;
                lote = producto.getElementsByTagName('lote')[0].firstChild.nodeValue;
                agregarProducto(id, nombre, cantidad, lote);
            }
        }
    };
    enviarPeticionXHTTP("productos_intencion_venta?id="+id_intencion);
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

function agregarProducto(id, producto, cantidad, lote) {

    fila = '<tr ' + 'id=' + id + '>';
    fila += '<td>' + producto + '</td>';
    fila += '<td>' + cantidad + '</td>';
    fila += '<td>' + lote + '</td>';
    fila += '</tr>';

    //alert("Producto añadido a la lista: "+producto);

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + id + "#c#" + producto + "#c#" + cantidad);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);
}
