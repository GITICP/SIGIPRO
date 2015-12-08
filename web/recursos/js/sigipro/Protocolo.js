contador = parseInt($("#contador").val())+1;;


$(document).on("click", ".aprobar-Modal", function () {
    var id_protocolo = $(this).data('id');
    var actor = $(this).data('actor'); 
    alert(actor);
    $('#class-aprobar-protocolo #id_protocolo').val(id_protocolo);
    $("#class-aprobar-protocolo #actor").val(actor);
});

$(document).on("click", ".rechazar-Modal", function () {
    var id_protocolo = $(this).data('id');
    var actor = $(this).data('actor');
    $('#class-rechazar-protocolo #id_protocolo').val(id_protocolo);
    $("#class-rechazar-protocolo #actor").val(actor);
});

$(function () {
    $("#sortable").sortable({
        placeholder: "ui-state-highlight",
        update: function () {
            var ordenElementos = $(this).sortable("toArray").toString();
            $("#orden").val(ordenElementos);
        }
    });
    $("#sortable").disableSelection();
    
    var ordenActual = $("#orden").val();
    var ordenNuevo = ordenActual.replace("]","").replace("[","").replace(/ /g,"");
    $("#orden").val(ordenNuevo);
});

function agregarPaso() {
    fila = "<div class=\'row paso paso_"+contador+"\' id=\'"+contador+"\' style=\'border: 2px solid #73AD21;width: 80%;margin:20px;\' >";
    fila += "   <div class=\"widget-content\">";
    fila += "       <div class=\"col-md-5\">";
    fila += "           <label for=\"tipo\" class=\"control-label\"> *Paso</label>";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\">";
    fila += "                       <select id=\"paso_" + contador + "\" class=\"select2\" name=\"paso_" + contador + "\"";
    fila += "                           style=\'background-color: #fff;\' required";
    fila += "                           oninvalid=\"setCustomValidity(\'Este campo es requerido\')\"";
    fila += "                           onchange=\"setCustomValidity(\'\')\">";
    fila += "                           <option value=\'\'></option>";
    fila += "                       </select>";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "       </div>";
    fila += "       <div class=\"col-md-3\">";
    fila += "           <div class=\"form-group\">";
    fila += "               <div class=\"col-sm-12\">";
    fila += "                   <div class=\"input-group\"> <br>";
    fila += "                         <input id=\"aprobar_" + contador + "\" type=\"checkbox\" name=\"aprobar_" + contador + "\" style=\"width:20px; height:20px; alignment-baseline: central\"><span> *Requiere aprobacion</span>";
    fila += "                   </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "       </div>";
    fila += "           <div class=\"col-md-2\">";
    fila += "               <div class=\"form-group\">";
    fila += "                   <div class=\"col-sm-12\">";
    fila += "                       <div class=\"input-group\"> <br>"  ;
    fila += '                               <button type="button" id="boton_eliminar" class="btn btn-danger btn-sm eliminar" onclick="eliminarPaso(\'paso_' + contador + '\')" style="margin-left:7px;margin-right:5px;">Eliminar</button>';
    fila += "                       </div>";
    fila += "                    </div>";
    fila += "               </div>";
    fila += "           </div>";
    fila += "       </div>";
    fila += "   </div>";
    fila += "</div>";
    fila += "</div>";
    var orden = $("#orden").val();
    if (orden === '') {
        $("#orden").val(contador);
    } else {
        $("#orden").val(orden + ',' + contador);
    }
    $(".campos").append(fila);
    
    var listaPasos = $("#listaPasos").val();
    var parseLista = JSON.parse(listaPasos);
    $("#paso_" + contador).append('<optgroup id="listapasos_' + contador + '" label="Pasos de Protocolo"></optgroup>');
    $.each(parseLista, function (index, value) {
        $("#paso_" + contador).append("<option value=" + value[0] + ">" + value[1] + "</option>");
    });
    
    $("#paso_" + contador).select2();
    contador++;
}

Array.prototype.remove = function (x) {
    var i;
    for (i in this) {
        if (this[i].toString() === x.toString()) {
            this.splice(i, 1)
        }
    }
};

function eliminarPaso(paso) {
    var o = $("#orden").val().split(",");
    $("div > ." + paso).remove();
    var nombres = paso.split("_");
    o.remove(nombres[1].toString());
    o = o.join();
    $("#orden").val(o);
    
}