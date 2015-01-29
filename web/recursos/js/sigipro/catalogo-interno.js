// Función para agregar eventos al checkbox de reactivo.
// Enseña o esconde el form relativo al reactivo.
$(document).ready(function(){
  $("#check-reactivo").change(function(){
    if($("#check-reactivo").prop('checked')){
      $("#form-reactivo").show();
      modificarInputsReactivos(true);
    }else{
      $("#form-reactivo").hide();
      modificarInputsReactivos(false);
    }
  });
});

function modificarInputsReactivos(valor){
  $(".campo-reactivo").each(function(){
    $(this).prop('required', valor);
  });
}

function eliminarUbicacion(idUbicacion) {
  fila = $('#' + idUbicacion);
  $('#seleccionUbicacion')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
}

function agregarUbicacion() {
   if (!$('#formAgregarUbicacion')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarUbicacion')).click().remove();
    $('#formAgregarRolUsuario').find(':submit').click();
  }
  else {
  $('#modalAgregarUbicacion').modal('hide');

  ubicacionSeleccionada = $('#seleccionUbicacion :selected');
  idUbicacion = ubicacionSeleccionada.val();
  textoUbicacion = ubicacionSeleccionada.text();
  ubicacionSeleccionada.remove();

  fila = '<tr ' + 'id=' + idUbicacion + '>';
  fila += '<td>' + textoUbicacion + '</td>';
  fila += '<td>';
  fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarUbicacion(' + idUbicacion + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
  fila += '</td>';
  fila += '</tr>';

  //campoOcultoRoles = $('#rolesUsuario');
  //campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct + "#c#" + fechaDesact);
  //alert("el valor del campo oculto es: " + campoOcultoRoles.val());
  $('#datatable-column-filter-ubicaciones > tbody:last').append(fila);
  }
}

function eliminarProductoExterno(idProductoExterno) {
  fila = $('#' + idProductoExterno);
  $('#seleccionProductoExterno')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
}

function agregarProductoExterno() {
   if (!$('#formAgregarProductoExterno')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarProductoExterno')).click().remove();
    $('#formAgregarProductoExterno').find(':submit').click();
  }
  else {
  $('#modalAgregarProductoExterno').modal('hide');

  productoExternoSeleccionado = $('#seleccionProductoExterno :selected');
  idProductoExterno = productoExternoSeleccionado.val();
  textoProductoExterno = productoExternoSeleccionado.text();
  productoExternoSeleccionado.remove();

  fila = '<tr ' + 'id=' + idProductoExterno + '>';
  fila += '<td>' + textoProductoExterno + '</td>';
  fila += '<td>';
  fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProductoExterno(' + idProductoExterno + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
  fila += '</td>';
  fila += '</tr>';

  //campoOcultoRoles = $('#rolesUsuario');
  //campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct + "#c#" + fechaDesact);
  //alert("el valor del campo oculto es: " + campoOcultoRoles.val());
  $('#datatable-column-filter-productos-externos > tbody:last').append(fila);
  }
}