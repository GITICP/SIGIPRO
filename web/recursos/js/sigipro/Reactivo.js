$(document).on("click", ".certificado-Modal", function () {
    var id_reactivo = $(this).data('id');
    $("#id_reactivo_certificado").val(id_reactivo);
});

$(document).on("click", ".eliminarCertificado-Modal", function () {
    var id_certificado = $(this).data('id');
    $("#id_certificado_reactivo").val(id_certificado);
});

// Las líneas documentdas de esta función son por si se desea hacer que los certificados sean obligatorios dependendiendo del tipo
$("#seleccionTipo").change(function () {
    var id = $(this).val();
    
    var opcion = $(this).find("option:selected");
    //var certificable = opcion.data("certificable");
    
    var elemento_machote = $(".descargar-Machote").empty();
    if (opcion.data("machote") !== "") {
        elemento_machote.append("<a href=\"/SIGIPRO/ControlCalidad/TipoReactivo?accion=archivo&id_tipo_reactivo=" + id + "\">Descargar Machote de Curva</a>");
    }
    
    //var certificado = $("#certificado");
    //var label_certificado = $("#label-certificado");
    
    /*
    if (certificable === true) {
        label_certificado.text("*Certificado");
        certificado.prop("required", true);
    } else {
        label_certificado.text("Certificado");
        certificado.prop("required", false);
    }*/

});