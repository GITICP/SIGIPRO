// Función para agregar eventos al checkbox de reactivo.
// Enseña o esconde el form relativo al reactivo.
stockMinValido = false;
stockMaxValido = false;
cantBodegaValida = false;
cantLabValida = false;


$(document).ready(function () {
  $("#check-reactivo").change(function () {
    if ($("#check-reactivo").prop('checked')) {
      $("#form-reactivo").show();
      modificarInputsReactivos(true);
    } else {
      $("#form-reactivo").hide();
      modificarInputsReactivos(false);
    }
  });
  
  llenarCampoAsociacion('u', $('#datatable-column-filter-ubicaciones-formulario > tbody > tr'), $('#ubicaciones'));
  llenarCampoAsociacion('p', $('#datatable-column-filter-productos-externos > tbody > tr'), $('#productosExternos'));
  
  $('#stockMinimo').keyup(function(){
    var regex=/^[0-9]+$/;
    if( $(this).val().match(regex) ){
      $('#errorStockMinimo').text('');
      stockMinValido = true;
    } else {
      $('#errorStockMinimo').text('Debe ser un número');
      stockMinValido = true;
    }
  });
  
  $('#cantBodega').keyup(function(){
    var regex=/^[0-9]+$/;
    if( $(this).val().match(regex) ){
      $('#errorCantBodega').text('');
      cantBodegaValida = true;
    } else {
      $('#errorCantBodega').text('Debe ser un número');
      cantBodegaValida = false;
    }
  });

  $('#cantLab').keyup(function(){
    var regex=/^[0-9]+$/;
    if( $(this).val().match(regex) ){
      $('#errorCantLab').text('');
      cantLabValida = true;
    } else {
      $('#errorCantLab').text('Debe ser un número');
      cantLabValida = false;
    }
  });
  
  $('#stockMaximo').keyup(function(){
    var regex=/^[0-9]+$/;
    
    var stockMax = parseInt($(this).val());
    var stockMin = parseInt($('#stockMinimo').val());
    if( $(this).val().match(regex) ) {
      $('#errorStockMaximo').text('');
      stockMaxValido = true;
      
    } else {
      $('#errorStockMaximo').text('Debe ser un número');
      stockMaxValido = false;
    }
    
    if( $("#errorStockMaximo").text() === "" && stockMax <= stockMin ){
      $('#errorStockMaximo').text('Debe ser mayor que el stock mínimo.');
      stockMaxValido = false;
    } 
  });
  
  $("#catInternoForm").submit(function(){
    alert("se llama");
    if( stockMinValido && stockMaxValido && cantBodegaValida && cantLabValida){
      return true;
    } else {
      return false;
    }
  });
  
  $('#stockMinimo').triggerHandler( "keyup" );
  $('#cantBodega').triggerHandler( "keyup" );
  $('#cantLab').triggerHandler( "keyup" );
  $('#stockMaximo').triggerHandler( "keyup" );
  $("#check-reactivo").triggerHandler( "change" );
  
});


function llenarCampoAsociacion(string_pivote, tabla, campo_escondido){
  asociacionCodificada = "";
  pivote = "#" + string_pivote + "#";
  tabla.each(function (){
    fila = $(this);
    asociacionCodificada += pivote;
    asociacionCodificada += fila.attr('id');
  });
  campo_escondido.val(asociacionCodificada);
}

// Función para modificar el required de los campos de reactivos
// porque impedían hacer submit al form si estaba oculto.
function modificarInputsReactivos(valor) {
  if (!valor){
    cantBodegaValida = !valor;
    cantLabValida = !valor;
  } else {
    $('#cantBodega').triggerHandler( "keyup" );
    $('#cantLab').triggerHandler( "keyup" );
  }
  $(".campo-reactivo").each(function () {
    $(this).prop('required', valor);
  });
}

// Elimina la ubicación de la tbala.
function eliminarUbicacion(idUbicacion) {
  fila = $("#datatable-column-filter-ubicaciones-formulario").find('#' + idUbicacion);
  $('#seleccionUbicacion')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
  var campoOcultoUbicaciones = $("#ubicaciones");
  campoOcultoUbicaciones.val(campoOcultoUbicaciones.val().replace("#u#" + idUbicacion, ""));
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

    campoOcultoUbicaciones = $('#ubicaciones');
    campoOcultoUbicaciones.val(campoOcultoUbicaciones.val() + "#u#" + idUbicacion);
    $('#datatable-column-filter-ubicaciones-formulario > tbody:last').append(fila);
  }
}

function eliminarProductoExterno(idProductoExterno) {
  fila = $("#datatable-column-filter-productos-externos").find('#' + idProductoExterno);
  $('#seleccionProductoExterno')
          .append($("<option></option>")
                  .attr("value", fila.attr('id'))
                  .text(fila.children('td').eq(0).text()));
  fila.remove();
  var campoOcultoProductosExternos = $("#productosExternos");
  campoOcultoProductosExternos.val(campoOcultoProductosExternos.val().replace("#p#" + idProductoExterno, ""));
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

    campoOcultoProductosExternos = $('#productosExternos');
    campoOcultoProductosExternos.val(campoOcultoProductosExternos.val() + "#p#" + idProductoExterno);
    $('#datatable-column-filter-productos-externos > tbody:last').append(fila);
  }
}
