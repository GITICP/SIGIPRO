$(document).ready(function () {
  if ($('.sigipro-tabla-filter').length > 0) {
    var selectorTabla = '.sigipro-tabla-filter';
    var dtTable = $(selectorTabla).DataTable({// use DataTable, not dataTable
      sDom: // redefine sDom without lengthChange and default search box
              "t" +
              "<'row'<'col-sm-6'i><'col-sm-6'p>>"
    });
    var ths = '';
    $(selectorTabla + ' thead th').each(function () {
      ths += '<th></th>';
    });

    $(selectorTabla + ' thead').append('<tr class="row-filter">' + ths + '</tr>');
    $(selectorTabla + ' thead .row-filter th').each(function () {
      $(this).html('<input type="text" class="form-control input-sm" placeholder="Buscar...">');
    });

    $(selectorTabla + ' .row-filter input').on('keyup change', function () {
      alert("assigns function");
      dtTable
              .column($(this).parent().index() + ':visible')
              .search(this.value)
              .draw();
    });
  }
});