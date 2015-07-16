$(document).ready(function () {
    $.fn.dataTable.moment('DD/MM/YYYY');

    var cantidadTablas = $('.sigipro-tabla-filter').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sigipro-tabla-filter';
        $(selectorTabla).each(function () {
            var dtTable = $(this).DataTable({// use DataTable, not dataTable
                sDom:
                        "t" +
                        "<'row'<'col-sm-6'i><'col-sm-6'p>>",
                bRetrieve: true
            });
            var ths = '';
            var cantidadColumnas = $(this).find('thead th').not('.columna-escondida').length;

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
            var columna_filtro = 0;
            if ($(this).data("columna-filtro")) {
                columna_filtro = $(this).data("columna-filtro");
            }

            var dtTable = $(this).DataTable({
                sDom:
                        "t" +
                        "<'row'<'col-sm-6'i><'col-sm-6'p>>",
                "order": [[columna_filtro, "desc"]]
            });
            var ths = '';
            var cantidadColumnas = $(this).find('thead th').not('.columna-escondida').length;

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

    var cantidadTablas = $('.sigipro-asc-filter').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sigipro-asc-filter';
        $(selectorTabla).each(function () {
            var columna_filtro = 0;
            if ($(this).data("columna-filtro")) {
                columna_filtro = $(this).data("columna-filtro");
            }

            var dtTable = $(this).DataTable({
                sDom:
                        "t" +
                        "<'row'<'col-sm-6'i><'col-sm-6'p>>",
                "order": [[columna_filtro, "asc"]]
            });
            var ths = '';
            var cantidadColumnas = $(this).find('thead th').not('.columna-escondida').length;

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

function inicializar_tabla(selector_tabla) {
    var elemento = $(selector_tabla);
    
    var dtTable = elemento.DataTable({// use DataTable, not dataTable
        sDom:
                "t" +
                "<'row'<'col-sm-6'i><'col-sm-6'p>>"
    });
    var ths = '';
    var cantidadColumnas = elemento.find('thead th').not('.columna-escondida').length;

    for (i = 0; i < cantidadColumnas; i++) {
        ths += '<th></th>';
    }

    elemento.find('thead').append('<tr class="row-filter">' + ths + '</tr>');
    elemento.find('thead .row-filter th').each(function () {
        $(this).html('<input type="text" class="form-control input-sm" placeholder="Buscar...">');
    });

    elemento.find('.row-filter input').on('keyup change', function () {
        dtTable
                .column($(this).parent().index() + ':visible')
                .search(this.value)
                .draw();
    });

    return dtTable;
}