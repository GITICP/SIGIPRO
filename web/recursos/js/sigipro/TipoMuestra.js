$(document).ready(function () {
    var analisis = $("#listaAnalisis").val();
    if (analisis !== undefined) {
        if (analisis !== "") {
            $("#seleccionAnalisis").select2("val", analisis.split(","));
        }
    }
});