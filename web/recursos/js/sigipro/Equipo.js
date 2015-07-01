$(document).on("click", ".certificado-Modal", function () {
    var id_equipo = $(this).data('id');
    $("#id_equipo_certificado").val(id_equipo);
});