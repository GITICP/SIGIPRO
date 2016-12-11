
$(function(){ /* DOM ready */ //
    $("#flete").change(function () {
        var total_parcial = document.getElementById("total_parcial");
        var flete = document.getElementById("flete");
        var total = document.getElementById("total");
        
        total.value = parseInt(flete.value) + parseInt(total_parcial.value);
    });
});

$(function(){ /* DOM ready */ 
    $("#id_intencion").change(function () {

        var select_intencion = document.getElementById("id_intencion");
        var indice_intencion = select_intencion.selectedIndex;
        var opcion_intencion = select_intencion.options[indice_intencion].value;
        
        campoOcultoRoles = $('#listaProductos');
        campoOcultoRoles.val("");
        
        var total = document.getElementById("total_parcial");
        var total_final = document.getElementById("total");
        var flete = document.getElementById("flete");
        total.value = 0;
        total_final.value = parseInt(total.value) + parseInt(flete.value);
        
        //$('#datatable-column-filter-productos').dataTable().fnClearTable();
        $('#datatable-column-filter-productos tbody tr').remove();
        //var table = document.getElementById("datatable-column-filter-productos");
        //table.deleteRow(1);
        /*
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
        */
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
                if (producto.getElementsByTagName('lote')[0].firstChild === null){
                    lote = "";
                }
                else{
                    lote = producto.getElementsByTagName('lote')[0].firstChild.nodeValue;
                }
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
    fila += '<td>0</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto(' + id + ')"   style="margin-left:5px;margin-right:7px;">Modificar Precio</button>';
    fila += '</td>';
    fila += '</tr>';

    //alert("Producto añadido a la lista: "+producto);

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + id + "#c#" + producto + "#c#" + cantidad + "#c#0");
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);
}


function editarProducto(idRol) {
  var x = document.getElementById(idRol);
  document.getElementById("idProductoEditar").value = idRol;
  var precio = document.getElementById("editarPrecio");
  precio.value = x.children[3].innerHTML;
  
  $('#modalEditarProducto').modal('show');
  
}
/*
 * 
 * Funciones de Producto
 * 
 */
function actualizarTotal(){
    var total = document.getElementById("total_parcial");
    var tabla = document.getElementById("datatable-column-filter-productos");
    total.value = 0;
    for (var i = 1; i<tabla.rows.length; i++) {
      //alert(total.value);
      total.value = parseInt(total.value) + (parseInt(tabla.rows[i].cells[1].firstChild.nodeValue) * parseInt(tabla.rows[i].cells[3].firstChild.nodeValue));
      //alert(total.value);
    }
    
    var flete = document.getElementById("flete");
    var total_final = document.getElementById("total");
        
    total_final.value = parseInt(flete.value) + parseInt(total.value);
}

function confirmarEdicionProducto() {
  if (!$('#formEditarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formEditarProducto')).click().remove();
    $('#formEditarProducto').find(':submit').click();
  }
  else {
    var id = $('#idProductoEditar').val();
    fila = $('#' + id);
    fila.children('td').eq(3).text($('#editarPrecio').val());
    $('#modalEditarProducto').modal('hide');

    //Aqui se cambia el campo oculto para que los nuevos valores se reflejen luego en la inserción del rol
    campoOcultoRoles = $('#listaProductos');
    var a = campoOcultoRoles.val().split("#r#"); //1#c#fecha#c#fecha, 2#c#fecha#c#fecha
    var nuevoValorCampoOculto = "";
    a.splice(0, 1);
    for (var i = 0; i < a.length; i++)
    {
      var cadarol = a[i].split("#c#");
      if (cadarol[0] === id)
      {
      }
      else
      {
        nuevoValorCampoOculto = nuevoValorCampoOculto + "#r#" + a[i];
      }

    }
    campoOcultoRoles.val(nuevoValorCampoOculto + "#r#" + id + "#c#" + fila.children('td').eq(0).text() + "#c#" + fila.children('td').eq(1).text() + "#c#" + $('#editarPrecio').val());
  }
    actualizarTotal();
}
