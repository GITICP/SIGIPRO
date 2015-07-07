contador = 0;

function agregarCampo(){
    alert(contador);
    contador++;
}

$(document).ready(function() {
    
    $(".columna-especial").each(function(){
        var tabla = $(this).parents("table");
        var indice = tabla.find("th").index($(this)) + 1;

        tabla.find("tbody tr td:nth-child(" + indice + ")").each(function(){
            $(this).find("input").attr("readonly", true);
            
            var fila = $(this).parents("tr");
            var indice = fila.find("td").index($(this));
            var prueba = fila.find("td:nth-child(-n" + indice + ")");
            
        });
    });
    
    $(".fila-especial").each(function(){
        var fila = $(this);
        
        fila.find("td").each(function(){
            var fila = $(this).parent();
            var indice = fila.find("td").index($(this));
            
            fila.prevAll(":not(.fila-especial)").find("td:eq(" + indice +")").each(function(){
                $(this).find("input").val("1");
            });
            
            $(this).find("input").attr("readonly", true);
        });
    });
});