function AprobarSolicitud(id_solicitud) {
    var elementoConfirmable = $('.confirmableAprobar');
    var textoConfirmacion = elementoConfirmable.data('texto-confirmacion');
    var referencia = elementoConfirmable.data('href');
    $('#ModalEliminarGenerico').remove();
    $('body').append("<div class='modal fade' id='ModalEliminarGenerico' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>\
          <div class='modal-dialog'>\
            <div class='modal-content'>\
              <div class='modal-header'>\
                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>\
                <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>\
              </div>\
              <div class='modal-body'>\
                  <h5 class='title'>&iquest;Est&aacute; seguro que desea " + textoConfirmacion + "?</h5>\
                  <br>\
                  <div class='form-group'>\
                    <div class='modal-footer'>\
                      <button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-times-circle'></i> Cancelar</button>\
                      <a href='" + referencia + id_solicitud + "' class='btn btn-primary'><i class='fa fa-check-circle'></i> Confirmar</a>\
                    </div>\
                  </div>\
              </div>\
            </div>\
          </div>\
        </div>");
    $("#ModalEliminarGenerico").modal('show');
}

function RechazarSolicitud(id_solicitud) {
    $('#id_solicitud_rech').val(id_solicitud);
    $('#ModalRechazar').modal('show');
}

$(document).ready(function () {

    table = $('#tabladeSolicitudes').DataTable();
    table.destroy();

    table = $('#tabladeSolicitudes').DataTable({
        sDom: // redefine sDom without lengthChange and default search box
                "t" +
                "<'row'<'col-sm-6'i><'col-sm-6'p>>",
        "order": [[0, "desc"]]
    });
 
}
);

function CerrarSolicitud(id_solicitud) {
    var elementoConfirmable = $('.confirmableCerrar');
    var textoConfirmacion = elementoConfirmable.data('texto-confirmacion');
    var referencia = elementoConfirmable.data('href');
    $('#ModalCerrarGenerico').remove();
    $('body').append("<div class='modal fade' id='ModalCerrarGenerico' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' aria-hidden='true' style='display: none;'>\
          <div class='modal-dialog'>\
            <div class='modal-content'>\
              <div class='modal-header'>\
                <button type='button' class='close' data-dismiss='modal' aria-hidden='true'>&times;</button>\
                <h4 class='modal-title' id='myModalLabel'>Confirmaci&oacute;n</h4>\
              </div>\
              <div class='modal-body'>\
                  <h5 class='title'>&iquest;Est&aacute; seguro que desea " + textoConfirmacion + "?</h5>\
                  <br>\
                  <div class='form-group'>\
                    <div class='modal-footer'>\
                      <button type='button' class='btn btn-danger' data-dismiss='modal'><i class='fa fa-times-circle'></i> Cancelar</button>\
                      <a href='" + referencia + id_solicitud + "' class='btn btn-primary'><i class='fa fa-check-circle'></i> Confirmar</a>\
                    </div>\
                  </div>\
              </div>\
            </div>\
          </div>\
        </div>");
    $("#ModalCerrarGenerico").modal('show');
}

$(document).ready(function () {
    $('#btn-entregar-solicitudes').click(function () {
        var pivote = "#af#";
        var ids = "";
        $('#tabla_informacion tbody').empty();
        $('input[name=entregar]:checked').each(function () {
            var id = pivote + $(this).val();
            ids += id;
            var fila = $(this).parent().parent();
            var celdas = fila.find('td');
            var fila_html = $('<tr>');
            fila_html.attr('id', 'info-entrega-' + fila.attr('id'));
            fila_html.data('perecedero', fila.data('perecedero'));
            var id = $('<td>');
            id.text(fila.attr('id'));
            fila_html.append(id);
            var usuario = $('<td>');
            usuario.text(celdas.eq(2).text());
            fila_html.append(usuario);
            var producto = $('<td>');
            producto.text(celdas.eq(3).text());
            fila_html.append(producto);
            var cantidad = $('<td>');
            cantidad.text(celdas.eq(4).text());
            fila_html.append(cantidad);
            $('#tabla_informacion').append(fila_html);
        });

        $('#ids-por-entregar').val(ids);
        $("#ModalAutorizar").modal('show');
    });

    $("#entrega-sub-bodega").change(function () {
        var tabla = $('#tabla_informacion');

        if ($(this).prop("checked")) {

            tabla.find('th[hidden=true]').show();
            $("#select-sub-bodegas").show();
            $("#seleccion-sub-bodega").prop("required", true);

            tabla.find('tbody > tr').each(function () {
                var id = $(this).find('td:eq(0)');
                var celda_fecha = $("<td>");
                if ($(this).data('perecedero')) {
                    var input = $('<input type="text" \
                                          value="" \
                                          class="form-control tabla-entregas sigiproDatePicker" \
                                          name="fecha_vencimiento_' + id.html() + '" \
                                          data-date-format="dd/mm/yyyy" \
                                          required \>');
                    celda_fecha.append(input);
                } else {
                    celda_fecha.text('Producto No Perecedero');
                }
                
                var celda_numero_lote = $("<td>");
                var input_num_lote = $('<input type="text" value="" name="numero_lote_' + id.html() + '" class="form-control tabla-entregas" placeholder="Opcional">');
                celda_numero_lote.append(input_num_lote);

                $(this).append(celda_fecha);
                $(this).append(celda_numero_lote);

                $('.sigiproDatePicker').each(function () {
                    $(this).datepicker()
                            .on('changeDate', function () {
                                $(this).datepicker('hide');
                            });
                });
            });

        } else {

            tabla.find("tbody > tr").each(function () {
                $(this).find('td:last').remove();
                $(this).find('td:last').remove();
            });

            tabla.find('th[hidden=true]').hide();
            $("#select-sub-bodegas").hide();
            $("#seleccion-sub-bodega").prop("required", false);

        }
    });
});