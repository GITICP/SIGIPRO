$(document).on("click", ".descartar-Modal", function () {
    var ids = "";

    $('input[name=descartar]:checked').each(function () {
        var id = $(this).val();
        ids += id + ",";
    });
    
    $('#id_muestras').val(ids);
});

