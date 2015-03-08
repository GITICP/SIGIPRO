$(document).ready(function(){
    $('#btn-eliminar-activos-fijos').click(function(){
        var pivote = "#af#";
        var ids = "";
        
        $('input[name=eliminar]:checked').each(function(){
            var id = pivote + $(this).val();
            ids += id;
        });
        
        $('#ids-por-eliminar').val(ids);
    });
});