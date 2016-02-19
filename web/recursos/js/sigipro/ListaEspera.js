
//INICIO DE PRODUCTOS

$( document ).ready(function() {
    //Revisar la lista de productos_intencion en la tabla y cargarlos codificados en capoOcultoRoles
  var tabla = document.getElementById("datatable-column-filter-productos");
  var campoOcultoRoles = $('#listaProductos');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue);
      $("#seleccionProducto option[value='"+id+"']").remove();
  }
});

function eliminarProducto(idRol) {
  fila = $('#' + idRol);
  $('#seleccionProducto')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .attr("data-stock", fila.children('td').eq(3).text())
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
  var campoOcultoRoles = $('#listaProductos');
  var tabla = document.getElementById("datatable-column-filter-productos");
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
    campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id') + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue);
  }
  //alert("listaProductos = "+campoOcultoRoles.val());
}

function editarProducto(idRol) {
  var x = document.getElementById(idRol);
  document.getElementById("idProductoEditar").value = idRol;
  var cantidad = document.getElementById("editarCantidad");
  cantidad.value = x.children[1].innerHTML;
  var stock = x.children[3].innerHTML;
  cantidad.placeholder = "Máximo: "+stock;
  cantidad.setAttribute("max",stock);
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
    campoOcultoRoles.val(nuevoValorCampoOculto + "#r#" + id + "#c#" + $('#editarCantidad').val());
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
  var select = document.getElementById("seleccionProducto");
  var indice = select.selectedIndex;
  var stock = select.options[indice].getAttribute('data-stock');
  
    fechaAct = inputFechaAct.val();
    idRol = rolSeleccionado.val();

    textoRol = rolSeleccionado.text();

    rolSeleccionado.remove();
    inputFechaAct.val("");

    fila = '<tr ' + 'id=' + idRol + '>';
    fila += '<td>' + textoRol + '</td>';
    fila += '<td>' + fechaAct + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto(' + idRol + ')"   style="margin-left:5px;margin-right:7px;">Editar</button>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '<td hidden="true">' + stock + '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);

    $('#inputGroupSeleccionProducto').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
  }
}

$(function(){ /* DOM ready */
    $("#seleccionProducto").change(function () {

        var select = document.getElementById("seleccionProducto");
        var indice = select.selectedIndex;
        var stock = select.options[indice].getAttribute('data-stock');

        var cantidad = document.getElementById("cantidad");
        cantidad.placeholder = "Máximo: "+stock;
        cantidad.setAttribute("max",stock);


    });
});

function confirmacion() {
  rolesCodificados = "";
  $('#datatable-column-filter-productos > tbody > tr').each(function ()

  {
    fila = $(this);
    rolesCodificados += fila.attr('id');
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(1).text();
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


//CIERRE DE PRODUCTOS



//INICIO DE HISTORIALES
var contador_historiales = -1;
$( document ).ready(function() {
    //Revisar la lista de historiales_intencion en la tabla y cargarlos codificados en capoOcultoRoles
  var tabla = document.getElementById("datatable-column-filter-historiales");
  var campoOcultoRoles = $('#listaHistoriales');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id + "#c#" + tabla.rows[i].cells[0].firstChild.nodeValue);
  }
});

function eliminarHistorial(idRol) {
  var campoOcultoRoles = $('#listaHistoriales');
  var tabla = document.getElementById("datatable-column-filter-historiales");
  for (var i = 1; i<tabla.rows.length; i++) {
      if(parseInt(tabla.rows[i].getAttribute('id')) === parseInt(idRol)){
          tabla.deleteRow(i);
      }
  }
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      if(parseInt(tabla.rows[i].getAttribute('id')) > 0){
        campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id') + + "#c#" + tabla.rows[i].cells[0].firstChild.nodeValue);
      }
      else{
        campoOcultoRoles.val(campoOcultoRoles.val()+"#r##c#" + tabla.rows[i].cells[0].firstChild.nodeValue);
          
      }
  }
  //alert("listaHistoriales = "+campoOcultoRoles.val());
}

// Funcion que agrega el rol seleccionado al input escondido de roles
function agregarHistorial() {
   if (!$('#formAgregarHistorial')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarHistorial')).click().remove();
    $('#formAgregarHistorial').find(':submit').click();
  }
  else {
  $('#modalAgregarHistorial').modal('hide');
  //$('#inputGroupSeleccionHistorial').find('#select2-chosen-1').text("");
  
    var rolSeleccionado = document.getElementById('seleccionHistorial');

    var textoRol = rolSeleccionado.value;

    rolSeleccionado.remove();

    fila = '<tr id='+ contador_historiales +'>';
    fila += '<td>' + textoRol + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarHistorial(' + contador_historiales + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaHistoriales');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r##c#" + textoRol);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-historiales > tbody:last').append(fila);
    
    contador_historiales = contador_historiales - 1;

    var div_historial = document.getElementById("InputSeleccionHistorial");
    var input = document.createElement("input");
    input.id = "seleccionHistorial";
    input.name = "seleccionHistorial";
    input.type = "text";
    input.style = "'background-color: #fff;'";
    input.required = "true";
    input.oninvalid = "setCustomValidity('Este campo es requerido')";
    input.onchange = "setCustomValidity('')";
    
    div_historial.appendChild(input);
    //alert("Historial agregado.");
        //$('#inputGroupSeleccionHistorial').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
  }
}
//CIERRE DE HISTORIALES
        
        
        
//INICIO DE OBSERVACIONES
var contador_observaciones = -1;
$( document ).ready(function() {
    //Revisar la lista de observaciones_intencion en la tabla y cargarlos codificados en capoOcultoRoles
  var tabla = document.getElementById("datatable-column-filter-observaciones");
  var campoOcultoRoles = $('#listaObservaciones');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id + "#c#" + tabla.rows[i].cells[0].firstChild.nodeValue);
  }
});

function eliminarObservacion(idRol) {
  var campoOcultoRoles = $('#listaObservaciones');
  var tabla = document.getElementById("datatable-column-filter-observaciones");
  for (var i = 1; i<tabla.rows.length; i++) {
      if(parseInt(tabla.rows[i].getAttribute('id')) === parseInt(idRol)){
          tabla.deleteRow(i);
      }
  }
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      if(parseInt(tabla.rows[i].getAttribute('id')) > 0){
        campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id') + + "#c#" + tabla.rows[i].cells[0].firstChild.nodeValue);
      }
      else{
        campoOcultoRoles.val(campoOcultoRoles.val()+"#r##c#" + tabla.rows[i].cells[0].firstChild.nodeValue);
          
      }
  }
  //alert("listaObservaciones = "+campoOcultoRoles.val());
}

// Funcion que agrega el rol seleccionado al input escondido de roles
function agregarObservacion() {
   if (!$('#formAgregarObservacion')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarObservacion')).click().remove();
    $('#formAgregarObservacion').find(':submit').click();
  }
  else {
  $('#modalAgregarObservacion').modal('hide');
  //$('#inputGroupSeleccionObservacion').find('#select2-chosen-1').text("");
  
    var rolSeleccionado = document.getElementById('seleccionObservacion');

    var textoRol = rolSeleccionado.value;

    rolSeleccionado.remove();

    fila = '<tr id='+ contador_observaciones +'>';
    fila += '<td>' + textoRol + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarObservacion(' + contador_observaciones + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaObservaciones');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r##c#" + textoRol);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-observaciones > tbody:last').append(fila);

    contador_observaciones = contador_observaciones - 1;

    var div_historial = document.getElementById("InputSeleccionObservacion");
    var input = document.createElement("input");
    input.id = "seleccionObservacion";
    input.name = "seleccionObservacion";
    input.type = "text";
    input.style = "'background-color: #fff;'";
    input.required = "true";
    input.oninvalid = "setCustomValidity('Este campo es requerido')";
    input.onchange = "setCustomValidity('')";
                                
    div_historial.appendChild(input);
    
    //$('#inputGroupSeleccionObservacion').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
  }
}
//CIERRE DE OBSERVACIONES
