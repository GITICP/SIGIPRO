$("#extraccion").change(function () {
    if (this.checked) {
        $("#seleccionExtraccion").prop("disabled", false);
        $("#numero_control").prop("disabled", true);
        $("#peso_comprado").prop("disabled", true);
    } else {
        $("#seleccionExtraccion").prop("disabled", true);
        $("#numero_control").prop("disabled", false);
        $("#peso_comprado").prop("disabled", false);
    }
});

$(document).ready(function () {
    var extracciones = $("#listaExtracciones").val();
    if (extracciones !== undefined) {
        if (extracciones !== "") {
            $("#seleccionExtraccion").select2("val", extracciones.split(","));
        }
    }


});