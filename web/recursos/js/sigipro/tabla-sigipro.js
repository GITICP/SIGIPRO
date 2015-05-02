$(document).ready(function () {
    $.fn.dataTable.moment('DD/MM/YYYY'); // Para el ordenamiento de las fehas

    var cantidadTablas = $('.sigipro-tabla-filter').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sigipro-tabla-filter';
        $(selectorTabla).each(function () {
            var dtTable = $(this).DataTable({// use DataTable, not dataTable
                sDom:
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
    var cantidadTablas = $('.sigipro-desc-filter').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sigipro-desc-filter';
        $(selectorTabla).each(function () {
            var dtTable = $(this).DataTable({
                sDom:
                        "t" +
                        "<'row'<'col-sm-6'i><'col-sm-6'p>>",
                "order": [[0, "desc"]]
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