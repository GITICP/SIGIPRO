
CONFIGURACION_TABLAS = {
    sDom:
            "<'row'<'col-xs-6'><'col-xs-6'l>>" +
            "t" +
            "<'row'<'col-sm-6'i><'col-sm-6'p>>",
    lengthMenu: [10, 25, 50, 75, 100],
    oLanguage: {"sLengthMenu": "Registros por p&aacute;gina:  _MENU_"},
    pageLength: 50
};

$(document).ready(function () {
    $.fn.dataTable.moment('DD/MM/YYYY');

    var cantidadTablas = $('.sigipro-tabla-filter').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sigipro-tabla-filter';
        $(selectorTabla).each(function () {
            crear_data_table($(this), CONFIGURACION_TABLAS);
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
            var configuracion_final = $.extend({}, configuracion_especifica, CONFIGURACION_TABLAS);

            crear_data_table($(this), configuracion_final);
        });
    }

    var cantidadTablas = $('.sigipro-no-filter').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sigipro-no-filter';
        $(selectorTabla).each(function () {
            var columna_filtro = 0;
            if ($(this).data("columna-filtro")) {
                columna_filtro = $(this).data("columna-filtro");
            }

            var configuracion_especifica = {"order": []};
            var configuracion_final = $.extend({}, configuracion_especifica, CONFIGURACION_TABLAS);

            crear_data_table($(this), configuracion_final);
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
            var configuracion_final = $.extend({}, configuracion_especifica, CONFIGURACION_TABLAS);

            crear_data_table($(this), configuracion_final);
        });
    }

    var cantidadTablas = $('.sin-paginacion').length;
    if (cantidadTablas > 0) {
        var selectorTabla = '.sin-paginacion';
        $(selectorTabla).each(function () {
            var configuracion_especifica = {"paging": false};
            var configuracion_final = $.extend({}, configuracion_especifica, CONFIGURACION_TABLAS);

            crear_data_table($(this), configuracion_final);
        });
    }
});

function crear_data_table(elemento, configuracion) {

    if (elemento.data("filas-defecto") !== undefined) {
        configuracion = $.extend({}, configuracion, {pageLength: elemento.data("filas-defecto")});
    }

    var dtTable = elemento.DataTable(configuracion);
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

}

function inicializar_tabla(selector_tabla, extra_params) {
    var elemento = $(selector_tabla);

    var params = {
        sDom:
                "t" +
                "<'row'<'col-sm-6'i><'col-sm-6'p>>"
    }

    if (extra_params) {
        params = $.extend({}, params, extra_params);
    }

    var dtTable = elemento.DataTable(params);
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
