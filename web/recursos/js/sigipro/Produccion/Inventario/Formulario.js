
$(document).ready(function () {

    $("#id_lote").select2({
        id: function (object) {
            return object.text;
        },
        data: $("#id_lote").data("lotes"),
        //Allow manually entered text in drop down.
        createSearchChoice: function (term, data) {
            if ($(data).filter(function () {
                return this.text.localeCompare(term) === 0;
            }).length === 0) {
                return {id: term, text: term};
            }
        }
    });
    
    var selected = $("#id_lote").data("selected");
    $("#id_lote").select2('val', selected);

});

function confirmar() {
    if (!$('#form-Principal')[0].checkValidity()) {
        $('<input type="submit">').hide().appendTo($('#form-Principal')).click().remove();
        $('#form-Principal').find(':submit').click();
    } else {
        $('#form-Principal').submit();
    }

}
;