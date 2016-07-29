$(document).ready(function () {
    $(window).keydown(function (event) {
        if (event.keyCode === 13 && event.target.localName !== "button" && event.target.localName !== "textarea") {
            event.preventDefault();
            if(event.target.classList.contains("navegable-enter")) {
                var elementos = $('.navegable-enter');
                var i = elementos.index(document.activeElement) + 1;
                if (i >= elementos.length) i = 0;
                elementos.eq(i).focus();
                elementos.eq(i).select();
                return true;
            } else {
                return false;
            }
            
        } else {
            return true;
        }
    });
});