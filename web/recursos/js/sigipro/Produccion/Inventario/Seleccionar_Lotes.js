

function eliminarRolUsuario(idRol) {
  fila = $('#' + idRol);
  $('#seleccionLote')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
}

function editarRolUsuario(idRol) {
  $('#modalEditarLote').modal('show');
  var x = document.getElementById(idRol);
  document.getElementById("idRolUsuarioEditar").value = idRol;
  document.getElementById("editarcantidad").value = x.children[1].innerHTML;
}
/*
 * 
 * Funciones de RolUsuario
 * 
 */

function confirmarEdicion() {
  if (!$('#formEditarRolUsuario')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formEditarRolUsuario')).click().remove();
    $('#formEditarRolUsuario').find(':submit').click();
  }
  else {
    var id = $('#idRolUsuarioEditar').val();
    fila = $('#' + id);
    fila.children('td').eq(1).text($('#editarcantidad').val());
    $('#modalEditarRolUsuario').modal('hide');

    //Aqui se cambia el campo oculto para que los nuevos valores se reflejen luego en la inserción del rol
    campoOcultoRoles = $('#rolesUsuario');
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
    campoOcultoRoles.val(nuevoValorCampoOculto + "#r#" + id + "#c#" + $('#editarcantidad').val());
  }
}

// Funcion que agrega el rol seleccionado al input escondido de roles
function agregarRol() {
   if (!$('#formAgregarRolUsuario')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarRolUsuario')).click().remove();
    $('#formAgregarRolUsuario').find(':submit').click();
  }
  else {
  $('#modalAgregarRolUsuario').modal('hide');
  //$('#inputGroupSeleccionRol').find('#select2-chosen-1').text("");
  rolSeleccionado = $('#seleccionLote :selected');
  inputFechaAct = $('#agregarcantidad');
  //inputFechaDesact = $('#agregarFechaDesactivacion');

  fechaAct = inputFechaAct.val();
  idRol = rolSeleccionado.val();

  textoRol = rolSeleccionado.text();

  rolSeleccionado.remove();
  inputFechaAct.val("");

  fila = '<tr ' + 'id=' + idRol + '>';
  fila += '<td>' + textoRol + '</td>';
  fila += '<td>' + fechaAct + '</td>';
  fila += '<td>';
  fila += '<button type="button" class="btn btn-warning btn-sm" onclick="editarRolUsuario(' + idRol + ')"   style="margin-left:5px;margin-right:7px;">Editar</button>';
  fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarRolUsuario(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
  fila += '</td>';
  fila += '</tr>';

  campoOcultoRoles = $('#rolesUsuario');
  campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct);
  //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

  $('#datatable-column-filter-roles > tbody:last').append(fila);
  
  $('#inputGroupSeleccionRol').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});

  
  }
}

function confirmacion() {
  rolesCodificados = "";
  $('#datatable-column-filter-roles > tbody > tr').each(function ()

  {
    fila = $(this);
    rolesCodificados += fila.attr('id');
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(1).text();
    rolesCodificados += "#c#";
    rolesCodificados += fila.children('td').eq(2).text();
    rolesCodificados += "#r#";
  });
  $('#rolesUsuario').val(rolesCodificados.slice(0, -3));

  if (!$('#editarUsuario')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#editarUsuario')).click().remove();
    $('#editarUsuario').find(':submit').click();
  }
  else {
    $('#editarUsuario').submit();
  }
}
