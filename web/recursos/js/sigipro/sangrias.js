/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function() {
    
    $(".boton-observaciones").click(function() {
        
        var fila = $(this).parent();
        var observaciones_dia1 = validar_texto(fila.data("observaciones-dia1"));
        var observaciones_dia2 = validar_texto(fila.data("observaciones-dia2"));
        var observaciones_dia3 = validar_texto(fila.data("observaciones-dia3"));
        
        $("#campo-observaciones-dia1").text(observaciones_dia1);
        $("#campo-observaciones-dia2").text(observaciones_dia2);
        $("#campo-observaciones-dia3").text(observaciones_dia3);
        
        $("#observaciones-caballo-sangria").modal("show");
        
    });
    
});

function validar_texto(variable) {
    var resultado = "Sin observaciones.";
    if (variable !== "") {
        resultado = variable;
    }
    
    return resultado;
}