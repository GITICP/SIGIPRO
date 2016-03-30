$( document ).ready(function() {
    //Revisar la lista de acciones_intencion en la tabla y cargarlos codificados en capoOcultoRoles
  var tabla = document.getElementById("datatable-column-filter-acciones");
  var campoOcultoRoles = $('#listaAcciones');
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
      var id = tabla.rows[i].getAttribute('id');
      campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + id);
      $("#seleccionAccion option[value='"+id+"']").remove();
  }
});

function eliminarAccion(idRol) {
  fila = $('#' + idRol);
  $('#seleccionAccion')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(1).text()));
  fila.remove();
  var campoOcultoRoles = $('#listaAcciones');
  var tabla = document.getElementById("datatable-column-filter-acciones");
  campoOcultoRoles.val("");
  for (var i = 1; i<tabla.rows.length; i++) {
    campoOcultoRoles.val(campoOcultoRoles.val()+"#r#" + tabla.rows[i].getAttribute('id'));
  }
  //alert("listaAcciones = "+campoOcultoRoles.val());
}

// Funcion que agrega el rol seleccionado al input escondido de roles
function agregarAccion() {
   if (!$('#formAgregarAccion')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarAccion')).click().remove();
    $('#formAgregarAccion').find(':submit').click();
  }
  else {
  $('#modalAgregarAccion').modal('hide');
  //$('#inputGroupSeleccionAccion').find('#select2-chosen-1').text("");
  
  rolSeleccionado = $('#seleccionAccion :selected');
    idRol = rolSeleccionado.val();

    textoRol = rolSeleccionado.text();

    rolSeleccionado.remove();

    fila = '<tr ' + 'id=' + idRol + '>';
    fila += '<td>' + idRol + '</td>';
    fila += '<td>' + textoRol + '</td>';
    fila += '<td>';
    fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarAccion(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += '</td>';
    fila += '</tr>';

    campoOcultoRoles = $('#listaAcciones');
    campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol);
    //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

    $('#datatable-column-filter-acciones > tbody:last').append(fila);

    $('#inputGroupSeleccionAccion').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
  }
}