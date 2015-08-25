$(document).on("click", ".certificado-Modal", function () {
    var id_reactivo = $(this).data('id');
    $("#id_reactivo_certificado").val(id_reactivo);
});

$(document).on("click", ".eliminarCertificado-Modal", function () {
    var id_certificado = $(this).data('id');
    $("#id_certificado_reactivo").val(id_certificado);
});

$("#seleccionTipo").change(function () {
    var id = $(this).val();
    $(".descargar-Machote").empty().append("<a href=\"/SIGIPRO/ControlCalidad/TipoReactivo?accion=archivo&id_tipo_reactivo=" + id + "\">Descargar Machote de Curva</a>");

    var opcion = $(this).find("option:selected");
    var certificable = opcion.data("certificable");
    
    var certificado = $("#certificado");
    var label_certificado = $("#label-certificado");

    if (certificable === true) {
        label_certificado.text("*Certificado");
        certificado.prop("required", true);
    } else {
        label_certificado.text("Certificado");
        certificado.prop("required", false);
    }

});