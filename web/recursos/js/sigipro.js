/*function getRadioValue(groupName) {
    var radios = document.getElementsByName(groupName);
    window.rdValue; // declares the global variable 'rdValue'
    for (var i=0; i<radios.length; i++) {
        var someRadio = radios[i];
        if (someRadio.checked) {
            rdValue = someRadio.value;
            break;
        }
        else rdValue = 'noRadioChecked';
    }
    if (rdValue === 'noRadioChecked') {
        alert('no radio checked');
    }
    else{
        // alert(rdValue);
    }
    return rdValue;
}
*/

window.valorRB = null;
$("input[name='control']").click(function() {
    valorRB = this.value;
    
    document.getElementById("controlID").value=valorRB;
});


function prueba(){
    var x = document.getElementById(valorRB);
    
    document.getElementById("editarIDUsuario").value=valorRB;
    document.getElementById("editarNombreUsuario").value=x.children[1].innerHTML;
    document.getElementById("editarNombreCompleto").value=x.children[2].innerHTML;
    document.getElementById("editarCorreoElectronico").value=x.children[3].innerHTML;
    document.getElementById("editarCedula").value=x.children[4].innerHTML;
    document.getElementById("editarDepartamento").value=x.children[5].innerHTML;
    document.getElementById("editarPuesto").value=x.children[6].innerHTML;
    document.getElementById("editarFechaActivacion").value=x.children[7].innerHTML;
    document.getElementById("editarFechaDesactivacion").value=x.children[8].innerHTML;
}