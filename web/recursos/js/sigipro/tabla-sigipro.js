$(document).ready(function () {
  var cantidadTablas = $('.sigipro-tabla-filter').length;
  if ( cantidadTablas > 0) {
    var selectorTabla = '.sigipro-tabla-filter';
    var dtTable = $(selectorTabla).DataTable({// use DataTable, not dataTable
      sDom: // redefine sDom without lengthChange and default search box
              "t" +
              "<'row'<'col-sm-6'i><'col-sm-6'p>>"
    });
    var ths = '';
    var cantidadColumnas = $(selectorTabla + ' thead th').length / cantidadTablas; 
    
    for (i = 0; i < cantidadColumnas; i++){
      ths += '<th></th>';
    }

    $(selectorTabla + ' thead').append('<tr class="row-filter">' + ths + '</tr>');
    $(selectorTabla + ' thead .row-filter th').each(function () {
      $(this).html('<input type="text" class="form-control input-sm" placeholder="Buscar...">');
    });

    $(selectorTabla + ' .row-filter input').on('keyup change', function () {
      dtTable
              .column($(this).parent().index() + ':visible')
              .search(this.value)
              .draw();
    });
  }
});