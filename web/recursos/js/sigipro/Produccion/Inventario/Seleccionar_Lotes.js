
// Funcion que agrega el permiso seleccionado el permiso seleccionado a la tabla de permisos en Agregar Rol
function agregarPermiso() {
     if (!$('#formAgregarPermisoRol')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarPermisoRol')).click().remove();
    $('#formAgregarPermisoRol').find(':submit').click();
  }
  else{
  $('#modalAgregarPermisoRol').modal('hide');

  permisoSeleccionado = $('#seleccionPermiso :selected');
  idPermiso = permisoSeleccionado.val();
  textoPermiso = permisoSeleccionado.text();

  permisoSeleccionado.remove();

  fila = '<tr ' + 'id=' + idPermiso + '>';
  fila += '<td>' + textoPermiso + '</td>';
  fila += '<td>';
  fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarPermisoRol(' + idPermiso + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
  fila += '</td>';
  fila += '</tr>';
  
  $('#datatable-column-filter-permisos > tbody:last').append(fila);
  
  $('#inputGroupSeleccionPermiso').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
  }
}

// Funcion que elimina el permiso seleccionado de la tabla de permisos en Agregar Rol
function eliminarPermisoRol(idPermiso) {
  fila = $('#' + idPermiso);
  $('#seleccionPermiso')
    .append($("<option></option>")
    .attr("value", fila.attr('id'))
    .text(fila.children('td').eq(0).text()));
  fila.remove();
}
// Funcion que agrega los permisos y usuarios seleccionados a dos campo ocultos <input> que pasan los valores al servlet de Agregar Rol
function confirmacionAgregarRol() {
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
    
    permisosCodificados = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    
    {
      fila = $(this);
      permisosCodificados += fila.attr('id');
      permisosCodificados += "#r#";
    });
    $('#permisosRol').val(permisosCodificados.slice(0, -3));
    //alert("El valor del campo escondido de permisos es: "+ $('#permisosRol').val());
    
    if (!$('#formAgregarRol')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formAgregarRol')).click().remove();
    $('#formAgregarRol').find(':submit').click();
    }
    else{$('#formAgregarRol').submit();}
}
// Funcion que agrega los permisos y usuarios seleccionados a dos campo ocultos <input> que pasan los valores al servlet de Editar Rol
function confirmacionEditarRol() {
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
    
    permisosCodificados = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    
    {
      fila = $(this);
      permisosCodificados += fila.attr('id');
      permisosCodificados += "#r#";
    });
    $('#permisosRol').val(permisosCodificados.slice(0, -3));
    //alert("El valor del campo escondido de permisos es: "+ $('#permisosRol').val());
    
    if (!$('#editarUsuario')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#editarUsuario')).click().remove();
    $('#editarUsuario').find(':submit').click();
    }
    else{$('#editarUsuario').submit();}
}