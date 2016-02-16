$( document ).ready(function() {
    //Revisar la lista de productos_intencion en la tabla y cargarlos codificados en capoOcultoRoles
  var tabla = document.getElementById("datatable-column-filter-productos");
  var campoOcultoRoles = $('#listaProductos');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue + "#c#" + tabla.rows[i].cells[2].firstChild.nodeValue);
      $("#seleccionProducto option[value='"+id+"']").remove();
  }
});

function eliminarProducto(idRol) {
  fila = $('#' + idRol);
  $('#seleccionProducto')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .attr("data-stock", fila.children('td').eq(5).text())
                  .attr("data-precio", fila.children('td').eq(3).text())
                  .text(fila.children('td').eq(0).text()));
          
  var total = document.getElementById("total");
  var precioI = parseInt(fila.children('td').eq(3).text());
  var cantidadI = parseInt(fila.children('td').eq(1).text());
  //alert("Producto a ser eliminado. precio="+precioI+", cantidad="+cantidadI);
  total.value = parseInt(total.value) - (precioI * cantidadI);
  fila.remove();
  var campoOcultoRoles = $('#listaProductos');
  var tabla = document.getElementById("datatable-column-filter-productos");
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
    campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id') + "#c#" + tabla.rows[i].cells[1].firstChild.nodeValue + "#c#" + tabla.rows[i].cells[2].firstChild.nodeValue);
  }
  //alert("listaProductos = "+campoOcultoRoles.val());
}

function editarProducto(idRol) {
  var x = document.getElementById(idRol);
  document.getElementById("idProductoEditar").value = idRol;
  var cantidad = document.getElementById("editarCantidad");
  cantidad.value = x.children[1].innerHTML;
  var stock = x.children[5].innerHTML;
  var precio = x.children[3].innerHTML;
  cantidad.placeholder = "Máximo: "+stock;
  cantidad.setAttribute("max",stock);
  
  var precioText = document.getElementById("precio_unitario_editar");
  precioText.innerHTML = "Precio Unitario: "+precio;
  
  
  document.getElementById("editarPosibleFechaDespacho").value = x.children[2].innerHTML;
  $('#modalEditarProducto').modal('show');
  
}
/*
 * 
 * Funciones de Producto
 * 
 */
function actualizarTotal(){
    var total = document.getElementById("total");
    var tabla = document.getElementById("datatable-column-filter-productos");
    total.value = parseInt("0");
    for (var i = 1; i<tabla.rows.length; i++) {
      //alert(total.value);
      total.value = parseInt(total.value) + (parseInt(tabla.rows[i].cells[1].firstChild.nodeValue) * parseInt(tabla.rows[i].cells[3].firstChild.nodeValue));
      //alert(total.value);
  }
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
  var cantidad = document.getElementById("cantidad");
    cantidad.placeholder = "";
    actualizarTotal();
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
  var stock = select.options[indice].getAttribute('data-stock');
  precio = select.options[indice].getAttribute('data-precio');
  
  var total = document.getElementById("total");
  var precioI = parseInt(precio);
  var cantidadI = parseInt(inputFechaAct.val());
  if ((total.value === "0") || (total.value === 0)){
    total.value = precioI * cantidadI;    
    //alert("= 0 total cambiado a: "+total.value);
  }
  else{
    total.value = parseInt(total.value) + (precioI * cantidadI);
    //alert("total cambiado a: "+total.value);
  }
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
    fila += '<td>' + precio + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarProducto(' + idRol + ')"   style="margin-left:5px;margin-right:7px;">Editar</button>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '<td hidden="true">' + stock + '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct + "#c#" + fechaDesact);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);

    $('#inputGroupSeleccionProducto').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
    var cantidad = document.getElementById("cantidad");
    cantidad.placeholder = "";
  }
}

$(function(){ /* DOM ready */
    $("#seleccionProducto").change(function () {

        var select = document.getElementById("seleccionProducto");
        var indice = select.selectedIndex;
        var stock = select.options[indice].getAttribute('data-stock');
        var precio = select.options[indice].getAttribute('data-precio');

        var cantidad = document.getElementById("cantidad");
        cantidad.placeholder = "Máximo: "+stock;
        cantidad.setAttribute("max",stock);

        var precioText = document.getElementById("precio_unitario");
        precioText.innerHTML = "Precio Unitario: "+precio;
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