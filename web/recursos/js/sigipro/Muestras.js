$(document).on("click", ".descartar-Modal", function () {
    var ids = "";

    var tamano = $('input[name=descartar]:checked').length;

    if (tamano > 0) {

        $('input[name=descartar]:checked').each(function () {
            var id = $(this).val();
            ids += id + ",";
        });

        $('#id_muestras').val(ids);
    }
    else {
        $("#modalDescartarMuestras").modal("hide");
    }
});

