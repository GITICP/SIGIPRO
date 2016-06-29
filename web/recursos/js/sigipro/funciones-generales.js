$(document).ready(function () {
    $(window).keydown(function (event) {
        if (event.keyCode === 13 && event.target.localName !== "button" && event.target.localName !== "textarea") {
            event.preventDefault();
            return false;
        } else {
            return true;
        }
    });
});