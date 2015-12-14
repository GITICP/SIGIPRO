
$(document).ready(function () {
  }
);

function confirmar() {
    if (!$('#form-Principal')[0].checkValidity()) {
      $('<input type="submit">').hide().appendTo($('#form-Principal')).click().remove();
      $('#form-Principal').find(':submit').click();
    }
    else {
      $('#form-Principal').submit();
    }
  
  };