$(document).on("click", ".aprobar-Modal", function () {
    var id_actividad = $(this).data('id');
    var actor = $(this).data('actor');
    $('#class-aprobar-actividad #id_actividad').val(id_actividad);
    $("#class-aprobar-actividad #actor").val(actor);
});

$(document).on("click", ".cerrar-Modal", function () {
    var id_respuesta = $(this).data('id');
    var version = $(this).data('version');
    $('#class-cerrar-respuesta #id_respuesta').val(id_respuesta);
    $("#class-cerrar-respuesta #version").val(version);
});

$(document).on("click", ".aprobarrespuesta-Modal", function () {
    var id_actividad = $(this).data('id');
    var version = $(this).data('version');
    var actor = $(this).data('actor');
    $('#class-aprobar-respuesta #id_respuesta').val(id_actividad);
    $("#class-aprobar-respuesta #version").val(version);
    $("#class-aprobar-respuesta #actor").val(actor);
});

$(document).on("click", ".rechazar-Modal", function () {
    var id_actividad = $(this).data('id');
    var actor = $(this).data('actor');
    $('#class-rechazar-actividad #id_actividad').val(id_actividad);
    $("#class-rechazar-actividad #actor").val(actor);
});
