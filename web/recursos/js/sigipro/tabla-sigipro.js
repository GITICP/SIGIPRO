$(document).ready(function () {
  var cantidadTablas = $('.sigipro-tabla-filter').length;
  if (cantidadTablas > 0) {
    var selectorTabla = '.sigipro-tabla-filter';
    $(selectorTabla).each(function () {
      var dtTable = $(this).DataTable({// use DataTable, not dataTable
        sDom: // redefine sDom without lengthChange and default search box
                "t" +
                "<'row'<'col-sm-6'i><'col-sm-6'p>>"
      });
      var ths = '';
      var cantidadColumnas = $(this).find('thead th').length;

      for (i = 0; i < cantidadColumnas; i++) {
        ths += '<th></th>';
      }

      $(this).find('thead').append('<tr class="row-filter">' + ths + '</tr>');
      $(this).find('thead .row-filter th').each(function () {
        $(this).html('<input type="text" class="form-control input-sm" placeholder="Buscar...">');
      });

      $(this).find('.row-filter input').on('keyup change', function () {
        dtTable
                .column($(this).parent().index() + ':visible')
                .search(this.value)
                .draw();
      });
    });
  }
    var cantidadTablas = $('.sigipro-bitacora-filter').length;
    if (cantidadTablas > 0) {
      var selectorTabla = '.sigipro-bitacora-filter';
      $(selectorTabla).each(function () {
        var dtTable = $(this).DataTable({// use DataTable, not dataTable
          sDom: // redefine sDom without lengthChange and default search box
                  "t" +
                  "<'row'<'col-sm-6'i><'col-sm-6'p>>",
          "order": [[ 0, "desc" ]]
        });
        var ths = '';
        var cantidadColumnas = $(this).find('thead th').length;

        for (i = 0; i < cantidadColumnas; i++) {
          ths += '<th></th>';
        }

        $(this).find('thead').append('<tr class="row-filter">' + ths + '</tr>');
        $(this).find('thead .row-filter th').each(function () {
          $(this).html('<input type="text" class="form-control input-sm" placeholder="Buscar...">');
        });

        $(this).find('.row-filter input').on('keyup change', function () {
          dtTable
                  .column($(this).parent().index() + ':visible')
                  .search(this.value)
                  .draw();
        });
      });
    }
});