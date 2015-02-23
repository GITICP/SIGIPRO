tIngresos = null;
tEgresos = null;
$(document).ready(function () {
  var configuracion = {
    "paging": false,
    "ordering": false,
    "bFilter": false,
    "info": false,
  }
  tIngresos = $("#ingresos-sub-bodegas").DataTable(configuracion);
  tEgresos = $("#egresos-sub-bodegas").DataTable(configuracion);
});

function agregarUsuarioIngresos() {
  var usuarioSeleccionado = $("#seleccion-usuario :selected");
  var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioIngreso(" + usuarioSeleccionado.val() + ")>");
  botonEliminar.text("Eliminar");
  var nuevaFila = tIngresos.row.add([usuarioSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();
  
  $(nuevaFila).attr("id", "ingreso-" + usuarioSeleccionado.val());
  usuarioSeleccionado.prop('selected', false);
  usuarioSeleccionado.remove();
}

function eliminarUsuarioIngreso(id){
  var filaEliminada = $("#ingreso-" + id).remove();
  var usuario = filaEliminada.find("td :first").text();
  alert(usuario);
  var usuarioSeleccionado = $("#seleccion-usuario :selected");
}