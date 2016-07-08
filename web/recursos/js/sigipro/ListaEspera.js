$(document).ready(function() {
    
    //**La tabla no tiene la clase datatable en el index.jsp**
    
    //Se ordena la tabla en relación a la fecha. Por defecto, datatable no ordena por formato de fecha dd/mm/yyyy
    //Por ello, se crea el atributo 'data-order' en la primera columna de cada fila para luego asignarle la clase datatable a la tabla
    
    var table = document.getElementById("tabla_lista_espera");
    for (var i = 1; i < table.rows.length ; i++) {
        var row = 0;
        row = table.rows[i];
        var fecha = row.cells[0].firstChild.childNodes;
        
        fecha = fecha[1].innerHTML; //obtener la fecha de la solicitud en la lista de espera
        
        var fecha_array = fecha.split("/"); 
        var dia = fecha_array[0];
        var mes = fecha_array[1];
        var ano = fecha_array[2];
        
        var fecha_formateada = ano + '-' + mes + '-' + dia;
        
        row.cells[0].setAttribute("data-order", fecha_formateada);
     }
    
    document.getElementById("tabla_lista_espera").className = "table table-sorting table-striped table-hover datatable tablaSigipro sigipro-tabla-filter";
    //$('#tabla_lista_espera').DataTable();
} );