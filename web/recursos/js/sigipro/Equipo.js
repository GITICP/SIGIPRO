$(document).on("click", ".certificado-Modal", function () {
    var id_equipo = $(this).data('id');
    $("#id_equipo_certificado").val(id_equipo);
});

$(document).on("click", ".eliminarCertificado-Modal", function () {
    var id_certificado = $(this).data('id');
    $("#id_certificado_equipo").val(id_certificado);
});