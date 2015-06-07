function previewFile(){
    if (window.File && window.FileReader && window.FileList && window.Blob) {
  // Great success! All the File APIs are supported.
        var preview = document.getElementById("imagenSubida"); //selects the query named img
        var file    = document.querySelector('input[type=file]').files[0]; //sames as here
        var size  = file.size;
        var imagen = document.getElementById("imagen_Serpiente");
        var reader  = new FileReader();
        if (size> 102400){
            document.getElementById("imagen_Serpiente").setCustomValidity("La imagen debe ser de 100KB o menos. ");
            document.getElementById("boton_Cancelar").style.visibility = "visible";
        }else{
            document.getElementById("imagen_Serpiente").setCustomValidity("");
            document.getElementById("boton_Cancelar").style.visibility = "visible";
        }       
        reader.onload = function (e) {
            preview.src = reader.result;
            imagen.value = reader.toString();
        };
        if (file) {
            reader.readAsDataURL(file); //reads the data as a URL
        } else {
            preview.src = "";
        }    
    }else {
        alert('The File APIs are not fully supported in this browser.');
  }
}


$(function() {
    var max = $("#fecha_ingreso").val();
    var datepicker = $("#datepickerDeceso").datepicker({startDate:max,endDate:"-0d"});
});


$(document).on("click", ".open-Modal", function () {
                            
                            var observaciones = $(this).data('id');
                            $("#observacionesModal").text(observaciones);

                            
                            });
                            
$(document).on("click", ".evento-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente").val(id_serpiente);                          
                            });
                            
$(document).on("click", ".ch-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente_coleccion_humeda").val(id_serpiente);                          
                            });

$(document).on("click", ".ct-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente_catalogo_tejido").val(id_serpiente);                          
                            });
                            
$(document).on("click", ".rCV-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente_reversar_cv").val(id_serpiente);                          
                            });

$(document).on("click", ".rDeceso-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente_reversar_deceso").val(id_serpiente);                          
                            });

$(document).on("click", ".descarte-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente_descarte").val(id_serpiente);                          
                            });


$(document).on("click", ".rDeceso-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente_reversar_deceso").val(id_serpiente);                          
                            });


$(document).on("click", ".deceso-Modal", function () {                            
                            var id_serpiente = $(this).data('id');
                            $("#id_serpiente_deceso").val(id_serpiente);                          
                            });
                            
function eliminarImagen(){
    var preview = document.getElementById("imagenSubida"); //selects the query named img
    preview.src = "";
    var imagen = document.getElementById("imagen_Serpiente");
    imagen.value = "";
    document.getElementById("imagen_Serpiente").setCustomValidity("");
    document.getElementById("boton_Cancelar").style.visibility = "hidden";
}