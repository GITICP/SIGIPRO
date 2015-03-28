// Variables globales de tablas
/*


tCaballos = null;

$(document).ready(function () {
  var configuracion = {
    "paging": false,
    "ordering": false,
    "bFilter": false,
    "info": false
  };
  tCaballos = $("#caballos-grupodecaballos").DataTable(configuracion);
  
  $("#grupodecaballosForm").submit(function(){
    llenarCampoAsociacion('i', tCaballos, $("#ids-caballos"));
    alert($("#ids-caballos").val());
    return false;
  });
});

// -- Caballos -- //

function agregarCaballo() {
  var caballoSeleccionado = $("#seleccioncaballo :selected");
  caballoSeleccionado.remove();
  var botonEliminar = $("<button type='button' class='btn btn-danger btn-sm' style='margin-left:7px;margin-right:5px;' onclick=eliminarCaballo(" + caballoSeleccionado.val() + ")>");
  botonEliminar.text("Eliminar");
  
  var nuevaFila = tCaballos.row.add([caballoSeleccionado.text(), botonEliminar[0].outerHTML]).draw().node();

  $(nuevaFila).attr("id", "caballo-" + caballoSeleccionado.val());
}

function eliminarCaballo(id) {
  var fila = tCaballos.row('#caballo-' + id);
  
  var nuevaOpcion = $('<option>');
  nuevaOpcion.val(id);
  nuevaOpcion.text(fila.data()[0]);
  
  fila.remove().draw();
  
  $("#seleccioncaballo").append(nuevaOpcion);
}


function llenarCampoAsociacion(string_pivote, tabla, campo_escondido){
  asociacionCodificada = "";
  pivote = "#" + string_pivote + "#";
  alert(tabla.rows()[0].attr('id'));
  campo_escondido.val(asociacionCodificada);
}
*/

/* 
 * Funciones de JS para la funcionalidad de Catalogo Externo
 */


// Funcion que agrega el producto interno
function agregarCaballo() {
  if (!$('#form-Caballo-Grupo')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#form-Caballo-Grupo')).click().remove();
    $('#form-Caballo-Grupo').find(':submit').click();
  }else{
    permisoSeleccionado = $('#seleccioncaballo :selected');
    idPermiso = permisoSeleccionado.val();
    if (idPermiso==null) {
       $('<input type="submit">').hide().appendTo($('#form-Caballo-Grupo')).click().remove();
       $('#form-Caballo-Grupo').find(':submit').click();
    }
    else {
      $('#modalAgregarCaballo').modal('hide');

      textoPermiso = permisoSeleccionado.text();

      permisoSeleccionado.remove();

      fila = '<tr ' + 'id=' + idPermiso + '>';
      fila += '<td>' + textoPermiso + '</td>';
      fila += '<td>';
      fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarCaballo(' + idPermiso + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
      fila += '</td>';
      fila += '</tr>';

      $('#datatable-column-filter-permisos > tbody:last').append(fila);

      $('#inputGroupSeleccionCaballo').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
    }
  }
}

// Funcion que elimina el producto interno l
function eliminarCaballo(idPermiso) {
  fila = $('#' + idPermiso);
  $('#seleccioncaballo')
    .append($("<option></option>")
    .attr("value", fila.attr('id'))
    .text(fila.children('td').eq(0).text()));
  fila.remove();
}
// Funcion que agrega los productos internos
function confirmacionAgregarGrupo() {
    permisosCodificados = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    
    {
      fila = $(this);
      permisosCodificados += fila.attr('id');
      permisosCodificados += "#r#";
    });
    $('#caballos').val(permisosCodificados.slice(0, -3));
    //alert("El valor del campo escondido de permisos es: "+ $('#permisosRol').val());
    
    if (!$('#grupodecaballosForm')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#grupodecaballosForm')).click().remove();
    $('#grupodecaballosForm').find(':submit').click();
    }
    else{$('#grupodecaballosForm').submit();}
}
