$(document).on("click", ".certificado-Modal", function () {
    var id_equipo = $(this).data('id');
    $("#id_equipo_certificado").val(id_equipo);
});

$(document).on("click", ".eliminarCertificado-Modal", function () {
    var id_certificado = $(this).data('id');
    $("#id_certificado_equipo").val(id_certificado);
});

$(document).ready(function(){
    $("#seleccionTipo").change(function(){
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
});