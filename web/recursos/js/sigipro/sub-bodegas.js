// Variables globales de tablas
tIngresos = null;
tEgresos = null;

$(document).ready(function () {
  var configuracion = {
    "paging": false,
    "ordering": false,
    "bFilter": false,
    "info": false
  };
  tIngresos = $("#ingresos-sub-bodegas").DataTable(configuracion);
  tEgresos = $("#egresos-sub-bodegas").DataTable(configuracion);
  
  $("#subbodegaForm").submit(function(){
    llenarCampoAsociacion('i', tIngresos, $("#ids-ingresos"));
    llenarCampoAsociacion('e', tEgresos, $("#ids-egresos"));
    alert($("#ids-ingresos").val());
    alert($("#ids-egresos").val());
    return false;
  });
});

// -- Ingresos -- //

function agregarUsuarioIngresos() {
  var usuarioSeleccionado = $("#seleccion-usuario-ingreso :selected");
  usuarioSeleccionado.remove();
  var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioIngreso(" + usuarioSeleccionado.val() + ")>");
  botonEliminar.text("Eliminar");
  
  var nuevaFila = tIngresos.row.add([usuarioSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

  $(nuevaFila).attr("id", "ingreso-" + usuarioSeleccionado.val());
}

function eliminarUsuarioIngreso(id) {
  var fila = tIngresos.row('#ingreso-' + id);
  
  var nuevaOpcion = $('<option>');
  nuevaOpcion.val(id);
  nuevaOpcion.text(fila.data()[0]);
  
  fila.remove().draw();
  
  $("#seleccion-usuario-ingreso").append(nuevaOpcion);
}

// -- Egresos -- //

function agregarUsuarioEgresos() {
  var usuarioSeleccionado = $("#seleccion-usuario-egreso :selected");
  usuarioSeleccionado.remove();
  var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarUsuarioEgreso(" + usuarioSeleccionado.val() + ")>");
  botonEliminar.text("Eliminar");
  
  var nuevaFila = tEgresos.row.add([usuarioSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

  $(nuevaFila).attr("id", "egreso-" + usuarioSeleccionado.val());
}

function eliminarUsuarioEgreso(id) {
  var fila = tEgresos.row('#egreso-' + id);
  
  var nuevaOpcion = $('<option>');
  nuevaOpcion.val(id);
  nuevaOpcion.text(fila.data()[0]);
  
  fila.remove().draw();
  
  $("#seleccion-usuario-egreso").append(nuevaOpcion);
}

function llenarCampoAsociacion(string_pivote, tabla, campo_escondido){
  asociacionCodificada = "";
  pivote = "#" + string_pivote + "#";
  alert(tabla.rows()[0].attr('id'));
  campo_escondido.val(asociacionCodificada);
}