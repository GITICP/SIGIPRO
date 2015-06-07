$("#restriccion").change(function() {
    if(this.checked) {
        $("#cantidad_minima").prop("disabled",false);
    }else{
         $("#cantidad_minima").prop("disabled",true);
    }
});