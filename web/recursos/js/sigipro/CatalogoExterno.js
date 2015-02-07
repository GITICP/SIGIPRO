
/* 
 * Funciones de JS para la funcionalidad de Catalogo Externo
 */


// Funcion que agrega el producto interno
function agregarProductoInterno() {
  $('#modalAgregarCatalogoInterno').modal('hide');

  permisoSeleccionado = $('#seleccioninterno :selected');
  idPermiso = permisoSeleccionado.val();
  textoPermiso = permisoSeleccionado.text();

  permisoSeleccionado.remove();

  fila = '<tr ' + 'id=' + idPermiso + '>';
  fila += '<td>' + textoPermiso + '</td>';
  fila += '<td>';
  fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarProductoInterno(' + idPermiso + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
  fila += '</td>';
  fila += '</tr>';
  
  $('#datatable-column-filter-permisos > tbody:last').append(fila);
}

// Funcion que elimina el producto interno l
function eliminarProductoInterno(idPermiso) {
  fila = $('#' + idPermiso);
  $('#seleccioninterno')
    .append($("<option></option>")
    .attr("value", fila.attr('id'))
    .text(fila.children('td').eq(0).text()));
  fila.remove();
}
// Funcion que agrega los productos internos
function confirmacionAgregarProductoExterno() {
    permisosCodificados = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    
    {
      fila = $(this);
      permisosCodificados += fila.attr('id');
      permisosCodificados += "#r#";
    });
    $('#productosinternos').val(permisosCodificados.slice(0, -3));
    //alert("El valor del campo escondido de permisos es: "+ $('#permisosRol').val());
    
    if (!$('#formCatalogoExterno')[0].checkValidity()) {
    $('<input type="submit">').hide().appendTo($('#formCatalogoExterno')).click().remove();
    $('#formCatalogoExterno').find(':submit').click();
    }
    else{$('#formCatalogoExterno').submit();}
}
