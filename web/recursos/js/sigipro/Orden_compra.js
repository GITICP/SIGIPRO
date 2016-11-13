var contador = 0;
$(function(){ /* DOM ready */ //Filtrar el select de intenciones según el cliente escogido
    $("#eleccion").change(function () {

        var eleccion = document.getElementById("eleccion");
        var opcion_elegida = eleccion.options[eleccion.selectedIndex].value;
        
        var id_cotizacion = document.getElementById("id_cotizacionfg");
        var id_cotizacion_input = document.getElementById("id_cotizacion");
        var id_cotizacion_label = document.getElementById("id_cotizacion_label");
        var id_intencion = document.getElementById("id_intencionfg");
        var id_intencion_input = document.getElementById("id_intencion");
        var id_intencion_label = document.getElementById("id_intencion_label");
        
        if (opcion_elegida == 1){ //cotización
            id_cotizacion_input.required = true;
            id_intencion_input.required = false;
            id_cotizacion.style.display = 'block';
            id_cotizacion_label.style.display = 'block';
            id_intencion.style.display = 'none';
            id_intencion_label.style.display = 'none';
        }
        else{ //intención de venta
            id_cotizacion_input.required = false;
            id_intencion_input.required = true;
            id_cotizacion.style.display = 'none';
            id_cotizacion_label.style.display = 'none';
            id_intencion.style.display = 'block';
            id_intencion_label.style.display = 'block';
        }
        
    });
    
    $("#id_intencion").change(function () {

        var select_intencion = document.getElementById("id_intencion");
        var indice_intencion = select_intencion.selectedIndex;
        var opcion_intencion = select_intencion.options[indice_intencion].value;
        
        campoOcultoRoles = $('#listaProductos');
        campoOcultoRoles.val("");
        
        $('#datatable-column-filter-productos tbody tr').remove();
        
        /*$('#datatable-column-filter-productos').dataTable().fnClearTable();
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
        */
        //ajax call
        ajax_productos(opcion_intencion);
        
    });
    
    $("#id_cotizacion").change(function () {

        var select_cotizacion = document.getElementById("id_cotizacion");
        var indice_cotizacion = select_cotizacion.selectedIndex;
        var opcion_cotizacion = select_cotizacion.options[indice_cotizacion].getAttribute("data-intencion");
        
        campoOcultoRoles = $('#listaProductos');
        campoOcultoRoles.val("");
        
        $('#datatable-column-filter-productos tbody tr').remove();
        
        /*$('#datatable-column-filter-productos').dataTable().fnClearTable();
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
        */
        //ajax call
        ajax_productos(opcion_cotizacion);
        
    });
});

//al editar, no carga la ListaProductos

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
            var fecha;
            var producto;
            //alert(producto1.length);
            for (var i = 0; i < producto1.length; i++) {   
                producto = producto1[i];
                id = producto.getElementsByTagName('id')[0].firstChild.nodeValue;
                nombre = producto.getElementsByTagName('nombre')[0].firstChild.nodeValue;
                cantidad = producto.getElementsByTagName('cantidad')[0].firstChild.nodeValue;
                fecha = producto.getElementsByTagName('fecha')[0].firstChild.nodeValue;
                lote = producto.getElementsByTagName('lote')[0].firstChild.nodeValue;
                agregarProducto(id, nombre, cantidad, fecha, lote);
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

function agregarProducto(id, producto, cantidad, fecha, lote) {

    fila = '<tr ' + 'data-orden=' + contador+ ' id=' + id + '>';
    fila += '<td>' + producto + '</td>';
    fila += '<td>' + cantidad + '</td>';
    fila += '<td>' + fecha + '</td>';
    fila += '<td>' + lote + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto('+contador+')"   style="margin-left:5px;margin-right:7px;">Modificar</button>';
    fila += '<button type="button" class="btn btn-primary btn-sm" onclick="duplicarProducto(' + id + ')" style="margin-left:7px;margin-right:5px;">Duplicar</button>';
    fila += '</td>';
    fila += '</tr>';

    //alert("Producto añadido a la lista: "+producto);

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + id + "#c#" + producto + "#c#" + cantidad + "#c#" + fecha);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);
    contador ++;
}

function duplicarProducto(id) {
  var productoADuplicar = document.getElementById(id);
  
  var nombreProducto = productoADuplicar.cells[0].firstChild.nodeValue;
  var cantidad = productoADuplicar.cells[1].firstChild.nodeValue;
  var fecha = productoADuplicar.cells[2].firstChild.nodeValue;
  var lote = productoADuplicar.cells[3].firstChild.nodeValue;
  
  agregarProducto(id, nombreProducto, cantidad, fecha, lote);
}

function editarProducto(contador) {
    //contador = número de fila, está en data-orden
  document.getElementById("idProductoEditar").value = id;
  var cantidad = document.getElementById("editarCantidad");
  cantidad.value = id.children[1].innerHTML;
  document.getElementById("editarPosibleFechaDespacho").value = id.children[2].innerHTML;
  $('#modalEditarProducto').modal('show');
}

function confirmarEdicionProducto() {
  if (!$('#formEditarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formEditarProducto')).click().remove();
    $('#formEditarProducto').find(':submit').click();
  }
  else {
    var id = $('#idProductoEditar').val();
    fila = $('#' + id);
    fila.children('td').eq(1).text($('#editarCantidad').val());
    fila.children('td').eq(2).text($('#editarPosibleFechaDespacho').val());
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
    campoOcultoRoles.val(nuevoValorCampoOculto + "#r#" + id + "#c#" + $('#editarCantidad').val() + "#c#" + $('#editarPosibleFechaDespacho').val());
  }
}
