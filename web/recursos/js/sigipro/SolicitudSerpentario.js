$(function() {
    
    validarSolicitud();
});


$(document).on("click", ".rechazar-Modal", function () {                            
                            var id_solicitud = $(this).data('id');
                            $("#id_solicitud").val(id_solicitud);                          
                            });
            
function setLote(){
    if (!$('#entregarSolicitud')[0].checkValidity()) {
        $('<input type="submit">').hide().appendTo($('#entregarSolicitud')).click().remove();
        $('#entregarSolicitud').find(':submit').click();
  }else{
        loteSeleccionado = $('#seleccionLote :selected');
        id_lote = loteSeleccionado.val();
        if (id_lote==null) {
            $('<input type="submit">').hide().appendTo($('#entregarSolicitud')).click().remove();
            $('#entregarSolicitud').find(':submit').click();
        }
        else {
            textoLote = loteSeleccionado.text();
            
            var cantidad = textoLote.split("(")[1].split(" ")[2];
            loteSeleccionado.remove();

            fila = '<tr ' + 'id=' + id_lote + '>';
            //fila += '<div class="col-md-2 ">';
            fila += '<td width=50px>' + textoLote + '</td>';
            //fila += '</div>';
            //fila += '<div class="col-md-1 ">';
            fila += '<td width=150px>';
            fila += '<input type="number" step="any" placeholder="" min="0" id="cantidadInput" max="'+cantidad+'" class="form-control" name="cantidad_'+id_lote+'" value="" required oninput="setCustomValidity(\'\')" oninvalid="setCustomValidity(\'Cantidad se excede de la actual.\')">';
            fila += '</td>';
            fila += '<td width=50px>';
            fila += '<button type="button" class="btn btn-danger btn-sm" onclick="eliminarLote(' + id_lote + ')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
            fila += '</td>';
            fila += '</tr>';

            $('#datatable-column-filter-permisos > tbody:last').append(fila);

            $('#inputGroupSeleccionLote').find('.select2-chosen').each(function(){$(this).prop('id',''); $(this).text('');});
        }
  }
}

function eliminarLote(id_lote) {
  fila = $('#' + id_lote);
  $('#seleccionLote')
    .append($("<option></option>")
    .attr("value", fila.attr('id'))
    .text(fila.children('td').eq(0).text()));
  fila.remove();
}

function confirmacionAgregarLotes(id_solicitud,usuario,especie,cantidad) {
    if (!$('#agregarLote')[0].checkValidity()) {
        $('<input type="submit">').hide().appendTo($('#agregarLote')).click().remove();
        $('#agregarLote').find(':submit').click();
  }else{
    lotesCodificadas = "";
    var cantidad_entregada = 0;
    var inputs = "";
    $('#datatable-column-filter-permisos > tbody > tr').each(function ()
    {
      fila = $(this);
      cantidad_entregada += parseFloat(fila.find("#cantidadInput").val());
      inputs += '<input hidden="true" name="cantidad_'+fila.attr('id')+'" id="cantidad_'+fila.attr('id')+'" value="'+fila.find("#cantidadInput").val()+'">';
      lotesCodificadas += fila.attr('id');
      lotesCodificadas += "#r#";
    });
    $('#lotes').val(lotesCodificadas.slice(0, -3));
    
    if (!$('#agregarLote')[0].checkValidity()) {

  }else{
        //if(cantidad_entregada>=cantidad){
        $('#form_modalautorizar').append(inputs);
          $('#id_solicitud_entregar').val(id_solicitud);
          $('#cantidad_entregada').val(cantidad_entregada);
          $('#num-sol').val(id_solicitud);
          $('#usr-sol').val(usuario);
          $('#esp').val(especie);
          $('#cnt').val(cantidad);
          $('#cntEnt').val(cantidad_entregada);
          $('.alert-dismissible').remove();
          $('#modalEntregar').modal('show');
    //}else{
       //. $('#cantidad_entregada_error').text(cantidad_entregada+" gramos");
       // $('#cantidad_solicitada_error').text(cantidad+" gramos");
       // $('#modalError').modal('show');
    //}
  }
  }
}

function validarSolicitud(){
    var cantidad = parseFloat($("#cantidad").val());
    var restriccion = parseFloat($('#restriccion').val());
    var consumido = parseFloat($('#consumido').val());
    var cantidadActual = parseFloat($('#cantidadactual').val());
    if (isNaN(cantidad)){
        $("#cantidad")[0].setCustomValidity('La cantidad solicitada debe ser un número.');
    }else if (restriccion!==0.0){
        if((consumido+cantidad)>restriccion){
            $("#cantidad")[0].setCustomValidity('No puede solicitar más de lo restringido, en el presente año.');
        }
    }else if (cantidadActual<=cantidad){
        $("#cantidad")[0].setCustomValidity('La cantidad solicitada es superior a la cantidad actual.');
    }
    else{
        $("#cantidad")[0].setCustomValidity('');
    }
}
