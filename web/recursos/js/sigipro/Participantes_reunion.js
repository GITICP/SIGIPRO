$( document ).ready(function() {
    //Revisar la lista de productos_intencion en la tabla y cargarlos codificados en capoOcultoRoles
  var tabla = document.getElementById("datatable-column-filter-productos");
  var campoOcultoRoles = $('#listaProductos');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id + "#c#" + id);
      $("#seleccionParticipante option[value='"+id+"']").remove();
  }
});

function eliminarProducto(idRol) {
  fila = $('#' + idRol);
  $('#seleccionParticipante')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
  var campoOcultoRoles = $('#listaProductos');
  var tabla = document.getElementById("datatable-column-filter-productos");
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
    campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id') + "#c#" + tabla.rows[i].getAttribute('id'));
  }
  //alert("listaProductos = "+campoOcultoRoles.val());
}

/*
 * 
 * Funciones de Producto
 * 
 */

// Funcion que agrega el rol seleccionado al input escondido de roles
function agregarProducto() {
   if (!$('#formAgregarProducto')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarProducto')).click().remove();
    $('#formAgregarProducto').find(':submit').click();
  }
  else {
  $('#modalAgregarProducto').modal('hide');
  //$('#inputGroupSeleccionProducto').find('#select2-chosen-1').text("");
  
  rolSeleccionado = $('#seleccionParticipante :selected');
  var select = document.getElementById("seleccionParticipante");
  var indice = select.selectedIndex;
  
    idRol = rolSeleccionado.val();

    textoRol = rolSeleccionado.text();

    rolSeleccionado.remove();

    fila = '<tr ' + 'id=' + idRol + '>';
    fila += '<td>' + textoRol + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProducto(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaProductos');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + idRol);
    //alert("listaProductos = "+campoOcultoRoles.val());
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-productos > tbody:last').append(fila);

    $('#inputGroupSeleccionProducto').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
  }
}