// Variables globales de tablas
tIngresos = null;
tEgresos = null;
T_INGRESOS_SELECTOR = "#ingresos-sub-bodegas";
T_EGRESOS_SELECTOR = "#egresos-sub-bodegas";

$(document).ready(function() {
  
  $("#subbodegaForm").submit(function (event) {
    event.preventDefault();
    llenarCampoAsociacion('i', T_INGRESOS_SELECTOR, $("#ids-ingresos"));
    llenarCampoAsociacion('e', T_EGRESOS_SELECTOR, $("#ids-egresos"));
    alert($("#ids-ingresos").val());
    alert($("#ids-egresos").val());
  });
  
  var configuracion = {
    "paging": false,
    "ordering": false,
    "bFilter": false,
    "info": false
  };
  tIngresos = $(T_INGRESOS_SELECTOR).DataTable(configuracion);
  tEgresos = $(T_EGRESOS_SELECTOR).DataTable(configuracion);  
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
  var asociacionCodificada = "";
  var pivote = "#" + string_pivote + "#";
  $(tabla).find("tr[id]").each(function(){
    var asociacion = pivote + $(this).attr('id').split("-")[1];
    asociacionCodificada += asociacion;
  });
  campo_escondido.val(asociacionCodificada);
}