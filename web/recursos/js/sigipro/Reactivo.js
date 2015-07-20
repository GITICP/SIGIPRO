$(document).on("click", ".certificado-Modal", function () {
    var id_reactivo = $(this).data('id');
    $("#id_reactivo_certificado").val(id_reactivo);
});

$(document).on("click", ".eliminarCertificado-Modal", function () {
    var id_certificado = $(this).data('id');
    $("#id_certificado_reactivo").val(id_certificado);
});

$("#seleccionTipo").change(function(){
    var id = $(this).val();
    $(".descargar-Machote").empty().append("<a href=\"/SIGIPRO/ControlCalidad/TipoReactivo?accion=archivo&id_tipo_reactivo="+id+"\">Descargar Machote de Preparación</a>");

});