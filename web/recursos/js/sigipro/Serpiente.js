function previewFile(){
    if (window.File && window.FileReader && window.FileList && window.Blob) {
  // Great success! All the File APIs are supported.
       var preview = document.getElementById("img_newjourney"); //selects the query named img
       var file    = document.querySelector('input[type=file]').files[0]; //sames as here
       var imagen = document.getElementById("imagen");
       var reader  = new FileReader();
       
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