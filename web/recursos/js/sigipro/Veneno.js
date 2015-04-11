$("#restriccion").change(function() {
    if(this.checked) {
        $("#cantidad_maxima").prop("disabled",false);
    }else{
         $("#cantidad_maxima").prop("disabled",true);
    }
});