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

function modificarCampos() {
  var x = document.getElementById(valorRB);

  document.getElementById("editarIDUsuario").value = valorRB;
  document.getElementById("editarNombreUsuario").value = x.children[1].innerHTML;
  document.getElementById("editarNombreCompleto").value = x.children[2].innerHTML;
  document.getElementById("editarCorreoElectronico").value = x.children[3].innerHTML;
  document.getElementById("editarCedula").value = x.children[4].innerHTML;
  document.getElementById("editarDepartamento").value = x.children[5].innerHTML;
  document.getElementById("editarPuesto").value = x.children[6].innerHTML;
  document.getElementById("editarFechaActivacion").value = x.children[7].innerHTML;
  document.getElementById("editarFechaDesactivacion").value = x.children[8].innerHTML;
}

function asignarCookieUsuario() {
  if (valorRB)
  {
    var x = document.getElementById(valorRB);
    var nombre = x.children[2].innerHTML;

    setCookie('idUsuario', valorRB.toString() + ';' + nombre, 1, '/');

    self.location = "RolesUsuario.jsp";
  }
  else
  {
  }
}

function asignarRoles() {
  if (valorRB)
  {
    asignarCookieUsuario();
  }
  else
  {
    $('#modalError').modal('show');
  }
}

function desactivarUsuario() {
  if (valorRB)
  {
    $('#modalDesactivarUsuario').modal('show');
  }
  else
  {
    $('#modalError').modal('show');
  }
}

function editarUsuario() {
  if (valorRB)
  {
    modificarCampos()
    $('#modalEditarUsuarios').modal('show');
  }
  else
  {
    $('#modalError').modal('show');
  }
}

/*
 * 
 * Funciones Roles
 * 
 */

function asignarCookieRol() {
  if (valorRBRol)
  {
    var x = document.getElementById(valorRBRol);
    var nombre = x.children[1].innerHTML;


    setCookie('idRol', valorRBRol.toString() + ';' + nombre, 1, '/');

    self.location = "PermisosRol.jsp";
  }
  else
  {
  }
}
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

function EditarRolJS() {
  var x = document.getElementById(valorRBRol);

  document.getElementById("editarIdRol").value = valorRBRol;
  document.getElementById("editarNombre").value = x.children[1].innerHTML;
  document.getElementById("editarDescripcion").value = x.children[2].innerHTML;

}

function agregarPermisos() {
  if (valorRBRol)
  {
    asignarCookieRol();
  }
  else
  {
    $('#modalError').modal('show');
  }
}

function editarRol() {
  if (valorRBRol)
  {
    EditarRolJS();
    $('#ModalEditarRol').modal('show');
  }
  else
  {
    $('#modalError').modal('show');
  }
}

function eliminarRol() {
  if (valorRBRol)
  {
    $('#ModalEliminarRol').modal('show');
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
  fila = $('#' + idRol)
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

function confirmarEdicion(){
  var id = $('#idRolUsuarioEditar').val();
  fila = $('#'+id);
  fila.children('td').eq(1).text($('#editarFechaActivacion').val());
  fila.children('td').eq(2).text($('#editarFechaDesactivacion').val());
  $('#modalEditarRolUsuario').modal('hide');
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

  $('#datatable-column-filter-roles > tbody:last').append(fila);
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
  $('#modalConfirmacion').modal('show');
}

function confirmarCambios() {
  $('#editarUsuario').submit();
}

function asignarCookieSeccion() {
  if (valorRBSeccion)
  {
    var x = document.getElementById(valorRBSeccion);
    var nombre = x.children[1].innerHTML;


    setCookie('id_seccion', valorRBSeccion.toString() + ';' + nombre, 1, '/');

  }
  else
  {
  }
}
window.valorRBSeccion = null;
$("input[name='controlSeccion']").click(function () {
  valorRBSeccion = this.value;
  document.getElementById("controlIDSeccion").value = valorRBSeccion;
});


function eliminarSeccion() {
  if (valorRBSeccion)
  {
    $('#modalEliminarSeccion').modal('show');
  }
  else
  {
    $('#modalError').modal('show');
  }
}