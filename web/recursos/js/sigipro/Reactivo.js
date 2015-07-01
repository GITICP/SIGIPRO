$(document).on("click", ".certificado-Modal", function () {
    var id_reactivo = $(this).data('id');
    $("#id_reactivo_certificado").val(id_reactivo);
});