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
        contador = 1;
        
        $('#datatable-column-filter-productos tbody tr').remove();
        
        //ajax call
        ajax_productos(opcion_intencion);
        
    });
    
    $("#id_cotizacion").change(function () {

        var select_cotizacion = document.getElementById("id_cotizacion");
        var indice_cotizacion = select_cotizacion.selectedIndex;
        var opcion_cotizacion = select_cotizacion.options[indice_cotizacion].getAttribute("data-intencion");
        
        campoOcultoRoles = $('#listaProductos');
        campoOcultoRoles.val("");
        contador = 1;
        
        $('#datatable-column-filter-productos tbody tr').remove();
        
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
            //var lote;
            var cantidad;
            var fecha;
            var producto;
            //alert(producto1.length);
            for (var i = 0; i < producto1.length; i++) {   
                producto = producto1[i];
                id = producto.getElementsByTagName('id')[0].firstChild.nodeValue;
                nombre = producto.getElementsByTagName('nombre')[0].firstChild.nodeValue;
                cantidad = producto.getElementsByTagName('cantidad')[0].firstChild.nodeValue;
                try{fecha = producto.getElementsByTagName('fecha')[0].firstChild.nodeValue;}
                catch (exception){fecha="";}
               // el producto ya no tiene lote
               // if (producto.getElementsByTagName('lote')[0].firstChild === null){
               //     lote = "";
               // }
               // else{
               //     lote = producto.getElementsByTagName('lote')[0].firstChild.nodeValue;
               // }
                agregarProducto(id, nombre, cantidad, fecha);
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

function agregarProducto(id, producto, cantidad, fecha) {

    fila = '<tr ' + 'data-orden=' + contador+ ' id=' + id + '>';
    fila += '<td>' + producto + '</td>';
    fila += '<td>' + cantidad + '</td>';
    fila += '<td>' + fecha + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto('+contador+')"   style="margin-left:5px;margin-right:7px;">Modificar</button>';
    fila += '<button type="button" class="btn btn-primary btn-sm" onclick="duplicarProducto(' + id + ',' + contador + ')" style="margin-left:7px;margin-right:5px;">Duplicar</button>';
    fila += '</td>';
    fila += '</tr>';

    //alert("Producto añadido a la lista: "+producto);

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + id + "#c#" + producto + "#c#" + cantidad + "#c#" + fecha);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);
    contador ++;
}

function duplicarProducto(id, contador) {
  var tabla = document.getElementById("datatable-column-filter-productos");
  var productoADuplicar = tabla.rows[contador];
  
  var nombreProducto = productoADuplicar.cells[0].firstChild.nodeValue;
  var cantidad = productoADuplicar.cells[1].firstChild.nodeValue;
  try {var fecha = productoADuplicar.cells[2].firstChild.nodeValue;
  }
  catch (exception){fecha="";}
  agregarProducto(id, nombreProducto, cantidad, fecha);
}

function editarProducto(contador) {
  //contador = número de fila, está en data-orden
  var tabla = document.getElementById("datatable-column-filter-productos");
  var fila = tabla.rows[contador];
  
  document.getElementById("idProductoEditar").value = contador;
  document.getElementById("editarCantidad").value = fila.cells[1].firstChild.nodeValue;
  document.getElementById("editarPosibleFechaDespacho").value = fila.cells[2].firstChild.nodeValue;
  $('#modalEditarProducto').modal('show');
}

function confirmarEdicionProducto() {
  if (!$('#formEditarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formEditarProducto')).click().remove();
    $('#formEditarProducto').find(':submit').click();
  }
  else {
    var id = $('#idProductoEditar').val();
    //get fila que tenga data-orden = id
    var tabla = document.getElementById("datatable-column-filter-productos");
    var filaCambiada;
    for (var i = 1; i<tabla.rows.length; i++){
        var fila = tabla.rows[i];
        if (fila.getAttribute('data-orden') === id){
            filaCambiada = fila;
        }
    }
    filaCambiada.cells[1].firstChild.nodeValue = $('#editarCantidad').val();
    try{
    filaCambiada.cells[2].firstChild.nodeValue = $('#editarPosibleFechaDespacho').val();
    }
    catch(exception){}
    $('#modalEditarProducto').modal('hide');
    
    /*
     * Actualizar ListaProductos = tabla
     */
    
    listaProductos = $('#listaProductos');
    listaProductos.val(""); //limpia ListaProductos
    
    for(var i = 1; i<tabla.rows.length; i++){
        var fila = tabla.rows[i];
        var idfila = fila.getAttribute('id');
        var productofila = fila.cells[0].firstChild.nodeValue;
        var cantidadfila = fila.cells[1].firstChild.nodeValue;
        try {var fechafila = fila.cells[2].firstChild.nodeValue;
            }
        catch (exception){fechafila="";}
        listaProductos.val(listaProductos.val() + "#r#" + idfila + "#c#" + productofila + "#c#" + cantidadfila + "#c#" + fechafila);
    }
  }
}

$( document ).ready(function() {
  listaProductos = $('#listaProductos');
  listaProductos.val("");
//  alert("el valor del campo oculto es: " + listaProductos.val());
  var tabla = document.getElementById("datatable-column-filter-productos");
  for(var i = 1; i<tabla.rows.length; i++){
        var fila = tabla.rows[i];
        var idfila = fila.getAttribute('id');
        var productofila = fila.cells[0].firstChild.nodeValue;
        var cantidadfila = fila.cells[1].firstChild.nodeValue;
        try {var fechafila = fila.cells[2].firstChild.nodeValue;
            }
        catch (exception){fechafila="";}
        listaProductos.val(listaProductos.val() + "#r#" + idfila + "#c#" + productofila + "#c#" + cantidadfila + "#c#" + fechafila);
        
}
//alert("el valor del campo oculto es: " + listaProductos.val());
});
