$(document).ready(function () {
    $.fn.dataTable.moment('DD/MM/YYYY');

    var configuracion_tablas = {
        sDom:
                "<'row'<'col-xs-6'><'col-xs-6'l>>" +
                "t" +
                "<'row'<'col-sm-6'i><'col-sm-6'p>>",
        lengthMenu: [10, 25, 50, 75, 100],
        oLanguage: {"sLengthMenu": "Registros por p&aacute;gina:  _MENU_"}
    };

    var cantidadTablas = $('.sigipro-tabla-filter').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sigipro-tabla-filter';
        $(selectorTabla).each(function () {
            var dtTable = $(this).DataTable(configuracion_tablas);
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
            
            var configuracion_especifica = {"order": [[columna_filtro, "desc"]]};
            var configuracion_final = $.extend({}, configuracion_especifica, configuracion_tablas);

            var dtTable = $(this).DataTable(configuracion_final);
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
            
            var configuracion_especifica = {"order": [[columna_filtro, "asc"]]};
            var configuracion_final = $.extend({}, configuracion_especifica, configuracion_tablas);

            var dtTable = $(this).DataTable(configuracion_final);
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