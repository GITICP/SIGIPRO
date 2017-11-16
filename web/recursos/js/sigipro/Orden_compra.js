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
                catch (exception){fecha=" ";}
               // el producto ya no tiene lote
               // if (producto.getElementsByTagName('lote')[0].firstChild === null){
               //     lote = "";
               // }
               // else{
               //     lote = producto.getElementsByTagName('lote')[0].firstChild.nodeValue;
               // }
                agregarProductoInicio(id, nombre, cantidad, fecha);
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

function agregarProductoInicio(id, producto, cantidad, fecha) {

    fila = '<tr ' + ' id=' + id + '>';
    fila += '<td>' + producto + '</td>';
    fila += '<td>' + cantidad + '</td>';
    fila += '<td>' + fecha + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto('+id+')"   style="margin-left:5px;margin-right:7px;">Modificar</button>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + id + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + id + "#c#" + cantidad + "#c#" + fecha);

    $('#datatable-column-filter-productos > tbody:last').append(fila);

}

$( document ).ready(function() {
  var tabla = document.getElementById("datatable-column-filter-productos");
  var campoOcultoRoles = $('#listaProductos');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue);
      try {
      if (tabla.rows[i].cells[2].firstChild.nodeValue !== null)
        {
          campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + tabla.rows[i].cells[2].firstChild.nodeValue);
        }
      }
      catch (exception) {}
      $("#seleccionProducto option[value='"+id+"']").remove();
  }
});


function validarProductosYSubmit(){
    //alert($('#listaProductos').val());
    if ($('#listaProductos').val() === ""){
        $('[data-toggle="confirmar"]').tooltip({title: "Asegúrese de agregar productos", placement: "bottom"});   
        $('[data-toggle="confirmar"]').tooltip('show');   
    }
    else{
        $('#formularioProducto').submit();
    }
}

function eliminarProducto(idRol) {
  fila = $('#' + idRol);
  $('#seleccionProducto')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
  var campoOcultoRoles = $('#listaProductos');
  var tabla = document.getElementById("datatable-column-filter-productos");
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
    campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id') + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue );
      try {
      if (tabla.rows[i].cells[2].firstChild.nodeValue !== null)
        {
          campoOcultoRoles.val(campoOcultoRoles.val() + "#c#" + tabla.rows[i].cells[2].firstChild.nodeValue);
        }
      }
      catch (exception) {}}
  //alert("listaProductos = "+campoOcultoRoles.val());
}

function editarProducto(idRol) {
  var x = document.getElementById(idRol);
  document.getElementById("idProductoEditar").value = idRol;
  var cantidad = document.getElementById("editarCantidad");
  cantidad.value = x.children[1].innerHTML;
  document.getElementById("editarPosibleFechaDespacho").value = x.children[2].innerHTML;
  $('#modalEditarProducto').modal('show');
  
}
/*
 * 
 * Funciones de Producto
 * 
 */

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

// Funcion que agrega el rol seleccionado al input escondido de roles
function agregarProducto() {
   if (!$('#formAgregarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarProducto')).click().remove();
    $('#formAgregarProducto').find(':submit').click();
  }
  else {
  $('#modalAgregarProducto').modal('hide');
  //$('#inputGroupSeleccionProducto').find('#select2-chosen-1').text("");
  
  rolSeleccionado = $('#seleccionProducto :selected');
  inputFechaAct = $('#cantidad');
  inputFechaDesact = $('#posibleFechaEntrega');
  var select = document.getElementById("seleccionProducto");
  var indice = select.selectedIndex;
  
    fechaAct = inputFechaAct.val();
    fechaDesact = inputFechaDesact.val();
    idRol = rolSeleccionado.val();

    textoRol = rolSeleccionado.text();

    rolSeleccionado.remove();
    inputFechaAct.val("");
    inputFechaDesact.val("");

    fila = '<tr ' + 'id=' + idRol + '>';
    fila += '<td>' + textoRol + '</td>';
    fila += '<td>' + fechaAct + '</td>';
    fila += '<td>' + fechaDesact + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto(' + idRol + ')"   style="margin-left:5px;margin-right:7px;">Editar</button>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct + "#c#" + fechaDesact);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);

    $('#inputGroupSeleccionProducto').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
  }
}

$(function(){ /* DOM ready */
    $("#seleccionProducto").change(function () {

        var select = document.getElementById("seleccionProducto");
        var indice = select.selectedIndex;

        var cantidad = document.getElementById("cantidad");


    });
});

function confirmacion() {
  //alert("el valor del campo oculto es: " + $('#listaProductos').val());
  rolesCodificados = "";
  $('#datatable-column-filter-productos > tbody > tr').each(function ()
  
  {
    fila = $(this);
    rolesCodificados += fila.attr('id');
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(1).text();
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(2).text();
    rolesCodificados += "#r#";
  });
  $('#listaProductos').val(rolesCodificados.slice(0, -3));

  if (!$('#formularioProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formularioProducto')).click().remove();
    $('#formularioProducto').find(':submit').click();
  }
  else {
  $('#formEditarProducto').submit();
  }
}

function confirmacionAgregar() {
  rolesCodificados = "";
  $('#datatable-column-filter-productos > tbody > tr').each(function ()

  {
    fila = $(this);
    rolesCodificados += fila.attr('id');
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(1).text();
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(2).text();
    rolesCodificados += "#r#";
  });
  $('#listaProductos').val(rolesCodificados.slice(0, -3));
  //alert("el valor de roles Usuario es: "+$('#listaProductos').val() );

  if (!$('#formularioProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formularioProducto')).click().remove();
    $('#formularioProducto').find(':submit').click();
  }
  else {
    $('#formAgregarProducto').submit();
    var cantidad = document.getElementById("cantidad");
    cantidad.placeholder = "";
  }
}

