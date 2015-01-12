/*
 * 
 * Funciones cookies
 * 
 */

function setCookie(name, value, expires, path, domain, secure) {
  cookieStr = name + "=" + escape(value) + "; ";

  if (expires) {
    expires = setExpiration(expires);
    cookieStr += "expires=" + expires + "; ";
  }
  if (path) {
    cookieStr += "path=" + path + "; ";
  }
  if (domain) {
    cookieStr += "domain=" + domain + "; ";
  }
  if (secure) {
    cookieStr += "secure; ";
  }

  document.cookie = cookieStr;
}

function setExpiration(cookieLife) {
  var today = new Date();
  var expr = new Date(today.getTime() + cookieLife * 24 * 60 * 60 * 1000);
  return  expr.toGMTString();
}

/*
 * 
 *  Funciones Usuarios.jsp
 * 
 */

window.valorRB = null;
$("input[name='control']").click(function () {
  valorRB = this.value;
  document.getElementById("controlID").value = valorRB;
});

function desactivarUsuario() {
  if (valorRB)
  {$('#modalDesactivarUsuario').modal('show'); }
  else
  {$('#modalError').modal('show');}
}

//Funcion para que, por defecto, la fecha de desactivación de un usuario sea la misma a la de activación
$( "input[name='fechaActivacion']" ).change(function () {
//  $("#fechaDesactivacion").value( $("#fechaActivacion").value() ) ; 
  document.getElementById("fechaDesactivacion").value = document.getElementById("fechaActivacion").value;
  var fechaact = document.getElementById("fechaActivacion").value.split("/");
  var DateAct = new Date(fechaact[2],parseInt(fechaact[1])-1,fechaact[0]);
  var fechahoy =new Date();
  if ( DateAct < fechahoy )
  { $('#modalErrorFechaDesactivacion').modal('show'); 
    document.getElementById("fechaDesactivacion").value ="";}

});

//Cuando se cambia la fecha de desactivacion, se verifica su validez.
$("input[name='fechaDesactivacion']").change(function () {
  var fechaact = document.getElementById("fechaActivacion").value.split("/");
  var fechadesact = document.getElementById("fechaDesactivacion").value.split("/");
  var DateDesact = new Date(fechadesact[2],fechadesact[1],fechadesact[0]);
 // var fechahoy =new Date();
 // if (DateDesact < fechahoy){ }
  if ( (parseInt(fechadesact[0])+parseInt(fechadesact[1])*100+parseInt(fechadesact[2])*10000) <  (parseInt(fechaact[0])+parseInt(fechaact[1])*100+parseInt(fechaact[2])*10000) )
  { $('#modalErrorFechaDesactivacion').modal('show'); 
    document.getElementById("fechaDesactivacion").value ="";}
});


//-INACTIVA//Funcion que que hace submit del form de agregar usuario después de la confirmación
function confirmarAgregarUsuario() {
  $('#formAgregarUsuario').submit();
}

//Funcion para que, por defecto, la fecha de desactivación de un usuario en Editar Usuario sea la misma a la de activación
$( "input[name='editarFechaActivacion']" ).change(function () {
  $("#agregarFechaDesactivacion").value = $("#agregarFechaActivacion").value;});

/*
 * 
 * Funciones Roles
 * 
 */

window.valorRBRol = null;
$("input[name='controlRol']").click(function () {
  valorRBRol = this.value;
  document.getElementById("controlIDRol").value = valorRBRol;
});

window.valorRBPermiso = null;
$("input[name='controlPermiso']").click(function () {
  valorRBPermiso = this.value;
  document.getElementById("controlIDPermiso").value = valorRBPermiso;
});

function agregarPermisos() {
  if (valorRBRol)
  {
    self.location = "PermisosRol?id="+valorRBRol;
  }
  else
  {
    $('#modalError').modal('show');
  }
}


function eliminarRolPermiso() {
  if (valorRBPermiso)
  {
    $('#modalEliminarPermisoRol').modal('show');
  }
  else
  {
    $('#modalError').modal('show');
  }
}
function eliminarRolUsuario(idRol) {
  fila = $('#' + idRol);
  $('#seleccionRol')
    .append($("<option></option>")
    .attr("value", fila.attr('id'))
    .text(fila.children('td').eq(0).text()));
  fila.remove();
}

function editarRolUsuario(idRol) {
  $('#modalEditarRolUsuario').modal('show');
  var x = document.getElementById(idRol);
  document.getElementById("idRolUsuarioEditar").value = idRol;
  document.getElementById("editarFechaActivacion").value = x.children[1].innerHTML;
  document.getElementById("editarFechaDesactivacion").value = x.children[2].innerHTML;
}
/*
 * 
 * Funciones de RolUsuario
 * 
 */

function confirmarEdicion(){
  var id = $('#idRolUsuarioEditar').val();
  fila = $('#'+id);
  fila.children('td').eq(1).text($('#editarFechaActivacion').val());
  fila.children('td').eq(2).text($('#editarFechaDesactivacion').val());
  $('#modalEditarRolUsuario').modal('hide');
  
  //Aqui se cambia el campo oculto para que los nuevos valores se reflejen luego en la inserción del rol
  campoOcultoRoles = $('#rolesUsuario');
  var a = campoOcultoRoles.val().split("#r#"); //1#c#fecha#c#fecha, 2#c#fecha#c#fecha
  var nuevoValorCampoOculto = "";
  a.splice(0,1);
  for (var i=0; i<a.length; i++)
    { var cadarol = a[i].split("#c#");
      if (cadarol[0] === id )
        {}
      else 
        {
          nuevoValorCampoOculto =  nuevoValorCampoOculto + "#r#" + a[i] ;
        }
      
    }
  campoOcultoRoles.val(nuevoValorCampoOculto + "#r#" + id + "#c#" + $('#editarFechaActivacion').val() + "#c#" + $('#editarFechaDesactivacion').val());
}

function agregarRol() {
  $('#modalAgregarRolUsuario').modal('hide');

  rolSeleccionado = $('#seleccionRol :selected');
  inputFechaAct = $('#agregarFechaActivacion');
  inputFechaDesact = $('#agregarFechaDesactivacion');

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
  fila += '<button type="button" class="btn btn-primary btn-sm" onclick="editarRolUsuario(' + idRol + ')"   style="margin-left:5px;margin-right:7px;">Editar</button>';
  fila += '<button type="button" class="btn btn-primary btn-sm" onclick="eliminarRolUsuario(' + idRol + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
  fila += '</td>';
  fila += '</tr>';

  campoOcultoRoles = $('#rolesUsuario');
  campoOcultoRoles.val(campoOcultoRoles.val() + "#r#" + idRol + "#c#" + fechaAct + "#c#" + fechaDesact);
  //alert("el valor del campo oculto es: " + campoOcultoRoles.val());

  $('#datatable-column-filter-roles > tbody:last').append(fila);
}

function confirmacion() {
//  if ($('#nombreUsuario')[0].checkValidity() && $('#nombreCompleto')[0].checkValidity() && $('#correoElectronico')[0].checkValidity() &&
//          $('#cedula')[0].checkValidity() && $('#departamento')[0].checkValidity() && $('#puesto')[0].checkValidity()
//          && $('#fechaActivacion')[0].checkValidity() && $('#fechaDesactivacion')[0].checkValidity() ) {
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
    //alert("el valor de roles Usuario es: "+$('#rolesUsuario').val() );
    
    if (!$('#editarUsuario')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#editarUsuario')).click().remove();
    $('#editarUsuario').find(':submit').click();
    }
    else{$('#editarUsuario').submit();}
//  }
//  else 
//  {
//    bootbox.dialog({
//      message: "Por favor llene los campos requeridos (*) utilizando el formato indicado.",
//      title: "Error!",
//      buttons: {
//        success: {
//          label: "Aceptar",
//          className: "btn-success",
//          callback: true 
//          
//        }
//      }
//    });
//  }
}

